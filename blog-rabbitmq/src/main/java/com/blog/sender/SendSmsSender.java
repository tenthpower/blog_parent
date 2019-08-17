package com.blog.sender;


import com.blog.util.QueueConstant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 发送短信验证码的队列生产者
 * @author 和彦鹏
 * @date 2019年8月16日
 */
@Service
public class SendSmsSender {

    private Logger log = LoggerFactory.getLogger(SendSmsSender.class);

    @Autowired
    private AmqpTemplate rabbitTemplate;

    public String sendSmsSender(String telNo) {
        // 验证手机号,前端校验，后端也需要校验
        /*if (telNo.length() != 11) {
            log.info("手机号码=[{}]应为11位数。", telNo);
            return "手机号应为11位数";
        } else {
            Pattern p = Pattern.compile(Contant.TELNO_REGEX);
            Matcher m = p.matcher(telNo);
            boolean isMatch = m.matches();
            if (!isMatch) {
                System.out.println("请填入正确的手机号");
                log.info("手机号码=[{}]不正确。", telNo);
                return "请填入正确的手机号";
            }
        }*/
        rabbitTemplate.convertAndSend(QueueConstant.QUEUE_SMS_SEND, telNo);
        log.info("队列名=[{}]的队列发送消息内容=[{}]", QueueConstant.QUEUE_SMS_SEND, telNo);
        return "短信验证码发送成功！";
    }
}
