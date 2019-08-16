package com.tenthpower.util;

import java.net.URI;
import java.net.URISyntaxException;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import javax.annotation.PostConstruct;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMessage.RecipientType;

import com.tenthpower.config.EmailConfig;
import com.tenthpower.vo.EmailVo;
import com.tenthpower.vo.ReceiverPeoperVo;
import microsoft.exchange.webservices.data.core.ExchangeService;
import microsoft.exchange.webservices.data.core.enumeration.misc.ExchangeVersion;
import microsoft.exchange.webservices.data.credential.ExchangeCredentials;
import microsoft.exchange.webservices.data.credential.WebCredentials;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jodd.mail.Email;
import jodd.mail.EmailMessage;
import jodd.mail.MailServer;
import jodd.mail.MailServer.Builder;
import jodd.mail.SendMailSession;
import jodd.mail.SmtpServer;
import jodd.net.MimeTypes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * java发送邮件，参考地址：http://blog.csdn.net/xietansheng/article/details/51673073
 * @author 刘运兵
 * @date 2018-6-22 10:51:16
 * @version 1.0
 */
@Component
public class EmailUtils {   
    /**
     * 日志
     */
    private static final Logger log = LoggerFactory.getLogger(EmailUtils.class);

    @Autowired
    private EmailConfig config;

    @Autowired
    private static EmailConfig emailConfig=new EmailConfig();;

    /**
     * 于工具类是使用静态方式调用
     */
    @PostConstruct
    public void init() {
        log.info("EmailUtils init..");
        emailConfig = config;
    }

//    public static void main(String[] args) throws Exception {
//        emailConfig.setSmtpFromAccount("17621421619@163.com");
//        emailConfig.setSmtpFromPwd("SFlQNzY5ODgyMC4u");
//        emailConfig.setSmtpFromUseName("和彦鹏");
//        emailConfig.setSmtpFromHost("smtp.163.com");
//        emailConfig.setSmtpFromPort("465");
//        emailConfig.setSmtpIsFromSsl(false);
//        emailConfig.setSmtpIsAnnoymous(false);
//        EmailVo emailVo = new EmailVo();
//        List<ReceiverPeoperVo> receiverPeoperVos = new ArrayList<>();
//        ReceiverPeoperVo peo = new ReceiverPeoperVo();
//        peo.setReceiverAccount("heyanpeng@k2workflow.com.cn");
//        peo.setReceiverNickName("yenrocHo");
//        receiverPeoperVos.add(peo);
//        emailVo.setReceiverPeoperVos(receiverPeoperVos);
//        emailVo.setSign("html");
//        emailVo.setContent("邮件的正文内容");
//        emailVo.setTitle("邮件的标题");
//        sendEmailByJavax(emailVo);
//    }

    
    /**
     * javax.mail：发送邮件
     */
    public static void sendEmailByJavax(EmailVo emailVo) throws Exception {
        // PS: 某些邮箱服务器为了增加邮箱本身密码的安全性，给 SMTP 客户端设置了独立密码（有的邮箱称为“授权码”）, 
        //     对于开启了独立密码的邮箱, 这里的邮箱密码必需使用这个独立密码（授权码）。
        // 1. 创建参数配置, 用于连接邮件服务器的参数配置
        Properties props = new Properties();                    // 参数配置
        props.setProperty("mail.transport.protocol", "smtp");   // 使用的协议（JavaMail规范要求）
        props.setProperty("mail.smtp.host", emailConfig.getSmtpFromHost());   // 发件人的邮箱的 SMTP 服务器地址
        props.setProperty("mail.smtp.auth", "true");            // 需要请求认证

        // PS: 某些邮箱服务器要求 SMTP 连接需要使用 SSL 安全认证 (为了提高安全性, 邮箱支持SSL连接, 也可以自己开启),
        //     如果无法连接邮件服务器, 仔细查看控制台打印的 log, 如果有有类似 “连接失败, 要求 SSL 安全连接” 等错误,
        //     打开下面 /* ... */ 之间的注释代码, 开启 SSL 安全连接。
        /*
        // SMTP 服务器的端口 (非 SSL 连接的端口一般默认为 25, 可以不添加, 如果开启了 SSL 连接,
        //                  需要改为对应邮箱的 SMTP 服务器的端口, 具体可查看对应邮箱服务的帮助,
        //                  QQ邮箱的SMTP(SLL)端口为465或587, 其他邮箱自行去查看)
        */
        props.setProperty("mail.smtp.port", emailConfig.getSmtpFromPort());
        props.setProperty("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        props.setProperty("mail.smtp.socketFactory.fallback", "false");
        props.setProperty("mail.smtp.socketFactory.port", emailConfig.getSmtpFromPort());
        // 2. 根据配置创建会话对象, 用于和邮件服务器交互
        Session session = Session.getInstance(props);
        // 设置为debug模式, 可以查看详细的发送 log
        session.setDebug(true);                                

        // 3. 创建一封邮件
        MimeMessage message = createMimeMessage(session, emailVo);

        // 4. 根据 Session 获取邮件传输对象
        Transport transport = session.getTransport();

        // 5. 使用 邮箱账号 和 密码 连接邮件服务器, 这里认证的邮箱必须与 message 中的发件人邮箱一致, 否则报错
        //    PS_01: 成败的判断关键在此一句, 如果连接服务器失败, 都会在控制台输出相应失败原因的 log,
        //           仔细查看失败原因, 有些邮箱服务器会返回错误码或查看错误类型的链接, 根据给出的错误
        //           类型到对应邮件服务器的帮助网站上查看具体失败原因。
        //    PS_02: 连接失败的原因通常为以下几点, 仔细检查代码:
        //           (1) 邮箱没有开启 SMTP 服务;
        //           (2) 邮箱密码错误, 例如某些邮箱开启了独立密码;
        //           (3) 邮箱服务器要求必须要使用 SSL 安全连接;
        //           (4) 请求过于频繁或其他原因, 被邮件服务器拒绝服务;
        //           (5) 如果以上几点都确定无误, 到邮件服务器网站查找帮助。
        //
        //    PS_03: 仔细看log, 认真看log, 看懂log, 错误原因都在log已说明。
        transport.connect(emailConfig.getSmtpFromAccount(), emailConfig.getSmtpFromPwd());

        // 6. 发送邮件, 发到所有的收件地址, message.getAllRecipients() 获取到的是在创建邮件对象时添加的所有收件人, 抄送人, 密送人
        transport.sendMessage(message, message.getAllRecipients());

        // 7. 关闭连接
        transport.close();
    }
    /**
     * 创建一封简单邮件
     *
     * @param session 和服务器交互的会话
     * @param emailVo 收件人邮箱
     * @return
     * @throws Exception
     */
    public static MimeMessage createMimeMessage(Session session, EmailVo emailVo) throws Exception {
        // 1. 创建一封邮件
        MimeMessage message = new MimeMessage(session);
        
        // 2. From: 发件人（昵称有广告嫌疑，避免被邮件服务器误认为是滥发广告以至返回失败，请修改昵称）
        message.setFrom(new InternetAddress(emailConfig.getSmtpFromAccount(), emailConfig.getSmtpFromUseName(), "UTF-8"));
        
        // 3. To: 收件人（可以增加多个收件人、抄送、密送）
        if (emailVo.getReceiverPeoperVos() != null && emailVo.getReceiverPeoperVos().size() > 0) {
            List<ReceiverPeoperVo> peopers = emailVo.getReceiverPeoperVos();
            InternetAddress[] sendTo = new InternetAddress[peopers.size()];
            for (int i = 0; i < peopers.size(); i++) {
                sendTo[i] = new InternetAddress(peopers.get(i).getReceiverAccount(),peopers.get(i).getReceiverNickName(), "UTF-8");
            }  
            message.setRecipients(RecipientType.TO, sendTo);
        } else {
            log.error("收件人不可为空");
        }
        // 4. Subject: 邮件主题（标题有广告嫌疑，避免被邮件服务器误认为是滥发广告以至返回失败，请修改标题）
        message.setSubject(emailVo.getTitle(), "UTF-8");
        // 5. Content: 邮件正文（可以使用html标签）（内容有广告嫌疑，避免被邮件服务器误认为是滥发广告以至返回失败，请修改发送内容）
        message.setContent(emailVo.getContent(), "text/html;charset=UTF-8");
        // 6. 设置发件时间
        message.setSentDate(DateUtil.getCurDate());
        // 7. 保存设置
        message.saveChanges();
        return message;
    }
    
    /**
     * jodd 发送邮件
     */
    public static String sendEmailByJodd(EmailVo emailVo) throws Exception {
        log.info(MessageFormat.format("EmailUtils 开始发送邮件，邮件配置是{0},邮件信息是{1}", emailConfig, emailVo));
        String result = "";
        SendMailSession session = null;
        try {
            Email email = new Email();
            //设置邮件发送者
            email.from(emailConfig.getSmtpFromUseName());
            List<ReceiverPeoperVo> receives = emailVo.getReceiverPeoperVos();
            //发送给多个邮件接收者
            if (receives!= null && receives.size() > 0) {
               for (ReceiverPeoperVo receivePeopleVo : receives) {
                  log.info(MessageFormat.format("EmailUtils 收件人邮箱是{0}",  receivePeopleVo.getReceiverAccount()));
                  email.to(receivePeopleVo.getReceiverAccount());
               }
            }
            
            // 判断邮件内容
            if (StringUtils.isBlank(emailVo.getContent()) || StringUtils.isBlank(emailVo.getTitle())){
            	log.error("EmailUtils 邮件正文内容或主题为空，不进行邮件发送.");
            	return null;
            }
            //设置邮件主题
            email.subject(emailVo.getTitle(),"UTF-8");
            if (StringUtils.isNotBlank(emailVo.getSign()) && "html".equalsIgnoreCase(emailVo.getSign())) {
                //设置html文本信息
                EmailMessage htmlMessage = new EmailMessage(emailVo.getContent(), MimeTypes.MIME_TEXT_HTML, "UTF-8");
                log.info(MessageFormat.format("EmailUtils 配置html邮件，邮件内容是{0}", htmlMessage));
                email.message(htmlMessage);
            } else {
            	EmailMessage textMessage = new EmailMessage(emailVo.getContent(), MimeTypes.MIME_TEXT_PLAIN, "UTF-8");
                log.info(MessageFormat.format("EmailUtils 配置text邮件，邮件内容是{0}", textMessage));
                email.message(textMessage); 
            }

            //添加验证 
            Builder builder = MailServer.create();
            builder.host(emailConfig.getSmtpFromHost());
            builder.port(Integer.parseInt(emailConfig.getSmtpFromPort()));
            //是否添加ssl协议，注意 如果添加ssl，则port未对应ssl协议端口
            if (emailConfig.getSmtpIsFromSsl()) {
                builder.ssl(true);
            }
            //是否匿名发送   true 匿名发送
            if (!emailConfig.getSmtpIsAnnoymous()) {
                builder.auth(emailConfig.getSmtpFromAccount(), emailConfig.getSmtpFromPwd());
            }
            SmtpServer smtpServer = builder.buildSmtpMailServer();
            
            // 创建session
            session = smtpServer.createSession();
            session.open();
            result = session.sendMail(email);
            log.info("EmailUtils 发送邮件成功。");
            return result; 
         } catch (Exception e) {
             log.error("发送无附件邮件失败: {}", e);
             return null;
         } finally {
              if (null != session) {
                   session.close();
              }
         }
    }

    /**
     * Exchange 发送邮件
     * @return
     */
    public static String sendEmailByExchange(EmailVo emailVo) throws Exception {
        return null;
    }
    private ExchangeService getExchangeService() {
        ExchangeService service = new ExchangeService(ExchangeVersion.Exchange2007_SP1);
        //用户认证信息
        ExchangeCredentials credentials;
        if (config.getEwsDomain() == null) {
            credentials = new WebCredentials(config.getEwsUser(), config.getEwsPassword());
        } else {
            credentials = new WebCredentials(config.getEwsUser(), config.getEwsPassword(), config.getEwsDomain());
        }
        service.setCredentials(credentials);
        try {
            service.setUrl(new URI(config.getEwsMailServer()));
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        return service;
    }

    /**
     * jodd 发送邮件带附件
     */
//    public static String sendEmailByJobbWithAttachments(EmailF02ReqtM01 emailF02ReqtM01, EmailConfig config) throws BizLogicException{
//        //判断是否有配置邮件的环境，根据不同环境确定邮件接收人,带附件的接收人是单人，如果在config中的receive配置多人则默认取第一个人
//        //add by 丁畅 20190525 避免测试环境邮件发送到生成环境，对邮件的接收人做控制
//        
//        perSendEmailValidateWithAttachments(config,emailF02ReqtM01);
//        
//        
//        SendMailSession session = null;
//        try {
//            Email email = new Email();
//            
//            //设置邮件发送者
//            email.from(config.getSmtpFromUseName(),emailF02ReqtM01.getFromAddress());
//            //设置邮件接收者
//            email.to(emailF02ReqtM01.getToAddress());
//           
//            //设置邮件主题
//            email.subject(emailF02ReqtM01.getSubject(),"UTF-8");
//            
//            if (!FormatUtils.isContainsHtmlTag(emailF02ReqtM01.getContent())) {
//                //设置文本信息
//                if (!StringUtils.isBlank(emailF02ReqtM01.getContent())) {
//                    EmailMessage textMessage = new EmailMessage(emailF02ReqtM01.getContent(), MimeTypes.MIME_TEXT_PLAIN, "UTF-8");
//                    email.message(textMessage); 
//                }
//            }else{
//                //设置html文本信息
//                if (!StringUtils.isBlank(emailF02ReqtM01.getContent())) {
//                    EmailMessage htmlMessage = new EmailMessage(
//                            "<html><META http-equiv=Content-Type content=\"text/html; charset=utf-8\">" +
//                            "<body><h1>"+emailF02ReqtM01.getContent()+"</h1></body></html>",
//                        MimeTypes.MIME_TEXT_HTML, "UTF-8");
//                        email.message(htmlMessage);
//                }
//            }
//            
//            //发送附件
//            if (emailF02ReqtM01.getAttachFileNames() != null && emailF02ReqtM01.getAttachFileNames().length > 0) {
//                for (String attachment : emailF02ReqtM01.getAttachFileNames()) {
//                    email = email.attachment(EmailAttachment.with().content(attachment));
//                }
//            }
//
//            //添加验证 
//            Builder builder = MailServer.create();
//            builder.host(config.getSmtpFromHost());
//            builder.port(Integer.parseInt(config.getSmtpFromPort()));
//            //是否添加ssl协议，注意 如果添加ssl，则port未对应ssl协议端口
//            if (config.getSmtpIsFromSsl()) {
//                builder.ssl(true);
//            }
//            //是否匿名发送   true 匿名发送
//            if (!config.getSmtpIsAnnoymous()) {
//                builder.auth(emailF02ReqtM01.getFromAddress(), emailF02ReqtM01.getPassword());  
//            }
//            SmtpServer smtpServer = builder.buildSmtpMailServer();
//            
//            // 创建session
//            session = smtpServer.createSession();
//            session.open();
//            String result = session.sendMail(email);
//            
//            return result;                  
//         } catch (Exception e) {
//                log.error("发送附件邮件失败:" + e.getMessage(), e);
//                throw new BizLogicException(
//                        new I18nMessage("EmailUtilsSendEmailWithAttachment", new Object[]{e.getMessage()}));
//   
//         } finally {
//              if (null != session) {
//                   session.close();
//              }
//         }
//    }
    
    /**
     * @author 接收邮件
     */
//    public static EmailF03RespM01 receiveMail(EmailConfig myEmailConfig,String subject,String strDate ) throws BizLogicException{
//        EmailF03RespM01 emailF03RespM01 = new EmailF03RespM01();     
//        ReceiveMailSession session = null;
//        MailServer server = null;
//        try {
//            Builder builder = MailServer.create();
//            //添加 接受端协议
//            builder.host(myEmailConfig.getSmtpReceiveHost());
//            //添加接受端 端口 如果设置ssl协议，端口未ssl对应端口
//            builder.port(Integer.parseInt(myEmailConfig.getSmtpReceivePort()));
//            if (myEmailConfig.getSmtpIsReceiveSsl()) {
//                builder.ssl(true);
//            }
//            //是否匿名，true 匿名
//            if (!myEmailConfig.getSmtpIsAnnoymous()) {
//                builder.auth(myEmailConfig.getSmtpFromAccount(), myEmailConfig.getSmtpFromPwd());  
//            }
//            //POP3协议
//            if ("POP3".equals(myEmailConfig.getSmtpReceiveProtocolType())) {
//                server =  builder.buildPop3MailServer(); 
//            //IMAP协议
//            }else if ("IMAP".equals(myEmailConfig.getSmtpReceiveProtocolType())) {                        
//                server =  builder.buildImapMailServer();
//            }
//                       
//            session = (ReceiveMailSession) server.createSession();
//            session.open();
//            
//            //pop3协议只有一个名为INBOX的folder有效    
//            String[] folders = session.getAllFolders();
//            for (String folder : folders) {
//                if ("INBOX".equals(folder)) {
//                                            
//                  //对邮件进行过滤
//                  EmailFilter emailFilter = new EmailFilter();
//                  //设置邮件过滤条件
//                  if (!StringUtils.isBlank(subject)) {
//                     emailFilter = emailFilter.subject(subject);  //主题
//                  }
//                  if (!StringUtils.isBlank(strDate)) {
//                      emailFilter.sentDate(Operator.GE, FormatUtils.dateToLong(strDate)); //邮件发送时间
//                  }
//                  ReceivedEmail[] receiveEmail = session.receiveEmail(emailFilter);
//                                 
//                  if (receiveEmail != null && receiveEmail.length > 0){
//                      List<EmailF03RespS01> emailF03RespS01List = new ArrayList<>();
//                      for(ReceivedEmail email:receiveEmail){
//                          
//                          EmailF03RespS01 emailF03RespS01 = new EmailF03RespS01();
//                          
//                          emailF03RespS01.setMessageId(email.messageId());
//                          emailF03RespS01.setSubject(email.subject());
//                          emailF03RespS01.setSentDate(email.sentDate());
//
//                          //消息处理
//                          List<EmailMessage> messages = email.messages();
//                          //创建消息集合
//                          List<EmailF03RespS02> msgList = new ArrayList<>();
//                          
//                          for (EmailMessage message : messages) {
//                              
//                              EmailF03RespS02 respS02 = new EmailF03RespS02();
//                              respS02.setContent(message.getContent());
//                              
//                              msgList.add(respS02);
//                          }
//                          emailF03RespS01.setMessages(msgList);
//                          
//                          emailF03RespS01List.add(emailF03RespS01);
//                      } 
//                      
//                      emailF03RespM01.setEmilList(emailF03RespS01List);
//                 }
//                }
//            }
//            return emailF03RespM01;
//        } catch (Exception e) {
//            log.error("邮件接收失败:" + e.getMessage(), e);
//            throw new BizLogicException(
//                    new I18nMessage("EmailUtilsReceiveEmail", new Object[]{e.getMessage()}));
//        }finally {
//            if (null != session) {
//                session.close();
//            }
//        } 
//    }
    
}
