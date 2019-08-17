package com.blog.receiver;

import com.blog.config.EmailConfig;
import com.blog.dto.rabbitmq.EmailVo;
import com.blog.util.EmailUtils;
import com.blog.util.QueueConstant;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 发送邮件的队列消费者
 * @author 和彦鹏
 * @date 2019年8月15日
 */
@Component
@RabbitListener(queues = QueueConstant.QUEUE_EMAIL_SEND)
public class SendEmailReceiver {

    private Logger log = LoggerFactory.getLogger(SendEmailReceiver.class);

    @Autowired
    private EmailConfig emailConfig;

    @RabbitHandler
    public void process(EmailVo emailVo){
        log.info("队列名=[{}]的队列接收到消息内容=[{}]", QueueConstant.QUEUE_EMAIL_SEND, emailVo);
        log.info("邮件采用=[{}]的形式发送。", emailConfig.getProtocol());
        if (StringUtils.equalsIgnoreCase(emailConfig.getProtocol(), "SMTP")) {
            try {
                EmailUtils.sendEmailByJavax(emailVo);//TODO Jodd 发送有问题
            } catch (Exception e) {
                log.info("邮件采用=[sendEmailByJodd]的发送邮件出现异常，异常原因=[{}]。", e);
            }
        } else {
            try {
                EmailUtils.sendEmailByExchange(emailVo);
            }catch (Exception e) {
                log.info("邮件采用=[sendEmailByJodd]的发送邮件出现异常，异常原因=[{}]。", e);
            }
        }
    }
}
