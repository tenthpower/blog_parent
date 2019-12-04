package com.blog.controller;

import com.blog.vo.CustomMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Controller;

import java.security.Principal;

@Controller
public class STOMPWebSocket {

    @Autowired
    private SimpMessageSendingOperations messagingTemplate;


    /**
     * @Description:这个方法是接收客户端发送功公告的WebSocket请求，使用的是@MessageMapping
     * @Author:hui.yunfei@qq.com
     * @Date: 2019/5/31
     */
    @MessageMapping("/change-notice")//客户端访问服务端的时候config中配置的服务端接收前缀也要加上 例：/app/change-notice
    @SendTo("/topic/notice")//config中配置的订阅前缀记得要加上
    public void greeting(CustomMessage message,
                         StompHeaderAccessor stompHeaderAccessor,
                         Principal principal){
        System.out.println("服务端接收到消息："+message);
        //我们使用这个方法进行消息的转发发送！
        //this.simpMessagingTemplate.convertAndSend("/topic/notice", value);(可以使用定时器定时发送消息到客户端)
        //        @Scheduled(fixedDelay = 1000L)
        //        public void time() {
        //            messagingTemplate.convertAndSend("/system/time", new Date().toString());
        //        }
        //也可以使用sendTo发送

        message.setResponseMessage(message.getName() + "连接上了服务器。");
        messagingTemplate.convertAndSend("/topic/notice", message);
    }
}
