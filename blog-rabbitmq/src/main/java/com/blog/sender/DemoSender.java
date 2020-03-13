package com.blog.sender;


import com.blog.controller.dto.rabbitmq.DemoVo;
import com.blog.receiver.DemoReceiver;
import com.blog.util.QueueConstant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 队列生产者
 * @author 和彦鹏
 * @date 2019年8月15日
 */
@Service
public class DemoSender {

    private Logger log = LoggerFactory.getLogger(DemoReceiver.class);

    @Autowired
    private AmqpTemplate rabbitTemplate;

    public void demoSender(String message) {
        DemoVo demo = new DemoVo();
        demo.setAge(25);
        demo.setName("yenrocHo");
        demo.setMessage(message);
        rabbitTemplate.convertAndSend(QueueConstant.QUEUE_DEMO, demo);
        log.info("队列名=[{}]的队列发送消息内容=[{}]", QueueConstant.QUEUE_DEMO, demo);
    }
}
