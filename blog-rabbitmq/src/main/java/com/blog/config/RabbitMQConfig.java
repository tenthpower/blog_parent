package com.blog.config;

import com.blog.util.QueueConstant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 描述： rabbitmq消息队列的初始化队列信息
 * 参考：https://www.cnblogs.com/boshen-hzb/p/6841982.html
 * @author 和彦鹏
 * @date 2019年8月15日
 */
@Configuration
public class RabbitMQConfig {

    private Logger log = LoggerFactory.getLogger(RabbitMQConfig.class);

    @Bean
    public Queue demoQueue() {
        log.info("初始化Queue=[{}]...", QueueConstant.QUEUE_DEMO);
        return new Queue(QueueConstant.QUEUE_DEMO);
    }

    @Bean
    public Queue sendEmailQueue() {
        log.info("初始化Queue=[{}]...", QueueConstant.QUEUE_EMAIL_SEND);
        return new Queue(QueueConstant.QUEUE_EMAIL_SEND);
    }

    @Bean
    public Queue sendSmsQueue() {
        log.info("初始化Queue=[{}]...", QueueConstant.QUEUE_SMS_SEND);
        return new Queue(QueueConstant.QUEUE_SMS_SEND);
    }
}
