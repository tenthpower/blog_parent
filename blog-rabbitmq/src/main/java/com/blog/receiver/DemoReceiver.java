package com.blog.receiver;

import com.blog.dto.rabbitmq.DemoVo;
import com.blog.util.QueueConstant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * 队列消费者
 * @author 和彦鹏
 * @date 2019年8月15日
 */
@Component
@RabbitListener(queues = QueueConstant.QUEUE_DEMO)
public class DemoReceiver {

    private Logger log = LoggerFactory.getLogger(DemoReceiver.class);

    @RabbitHandler
    public void process(DemoVo demo){
        log.info("队列名=[{}]的队列接收到消息内容=[{}]", QueueConstant.QUEUE_DEMO, demo);
    }
}
