package com.blog.receiver;

import com.blog.util.QueueConstant;
import com.blog.util.SmsUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.Random;
import java.util.concurrent.TimeUnit;

/**
 * 发送短信验证码的队列消费者
 * @author 和彦鹏
 * @date 2019年8月16日
 */
@Component
@RabbitListener(queues = QueueConstant.QUEUE_SMS_SEND)
public class SendSmsReceiver {

    private Logger log = LoggerFactory.getLogger(SendSmsReceiver.class);

    @Autowired
    private RedisTemplate redisTemplate;

    @RabbitHandler
    public void process(String telNo) {
        log.info("队列名=[{}]的队列接收到消息内容=[{}]", QueueConstant.QUEUE_SMS_SEND, telNo);
        // 1.生成验证码
        Random random = new Random();
        int max = 999999;
        int min = 000000;
        int codeInt = random.nextInt(max);
        if (codeInt < min) {
            codeInt = codeInt + min;
        }
        String code = codeInt + "";
        log.info("发送给手机号=[{}]得验证码是：[{}]", telNo, code);

        // 2. 发送短信
        try {
            SmsUtils.sendSms(telNo, code);
            // 3. 验证码存入redis中
            redisTemplate.opsForValue().set("smscode_" + telNo, code + "" ,30, TimeUnit.MINUTES);
        } catch (Exception e){
            log.error("发送给手机号=[{}]的验证码短信发送失败，异常原因=[{}]", telNo, e);
        }
        return;
    }
}
