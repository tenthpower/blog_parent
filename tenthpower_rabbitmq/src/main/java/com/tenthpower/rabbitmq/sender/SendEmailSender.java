package com.tenthpower.rabbitmq.sender;


import com.tenthpower.rabbitmq.receiver.DemoReceiver;
import com.tenthpower.util.QueueConstant;
import com.tenthpower.vo.DemoVo;
import com.tenthpower.vo.EmailVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 发送邮件的队列生产者
 * @author 和彦鹏
 * @date 2019年8月15日
 */
@Service
public class SendEmailSender {

    private Logger log = LoggerFactory.getLogger(DemoReceiver.class);

    @Autowired
    private AmqpTemplate rabbitTemplate;

    public void sendEmailSender(EmailVo emailVo) {
        rabbitTemplate.convertAndSend(QueueConstant.QUEUE_EMAIL_SEND, emailVo);
        log.info("队列名=[{}]的队列发送消息内容=[{}]", QueueConstant.QUEUE_EMAIL_SEND, emailVo);
    }
}
