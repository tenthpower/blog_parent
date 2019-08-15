package com.tenthpower.rabbitmq.receiver;

import com.tenthpower.controller.DemoController;
import com.tenthpower.util.QueueConstant;
import com.tenthpower.vo.DemoVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.util.Map;

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
