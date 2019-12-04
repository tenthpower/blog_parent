package com.blog.controller;

import com.blog.consts.WebSocketConsts;
import com.blog.util.MessageSendUtil;
import com.blog.util.WebSocketInfoUtil;
import com.blog.vo.ChatInfoVo;
import com.blog.vo.ChatMessage;
import com.blog.vo.CustomMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

import java.util.UUID;

@Controller
public class STOMPWebSocket {

    private final Logger log = LoggerFactory.getLogger(STOMPWebSocket.class);

    @Autowired
    private MessageSendUtil messageSendUtil;


    /**
     * @Description:这个方法是接收客户端发送功公告的WebSocket请求，使用的是@MessageMapping
     * @Author:hui.yunfei@qq.com
     * @Date: 2019/5/31
     */
    @MessageMapping("/change-notice")//客户端访问服务端的时候config中配置的服务端接收前缀也要加上 例：/app/change-notice
    @SendTo("/topic/notice")//config中配置的订阅前缀记得要加上
    public void greeting(CustomMessage message){
        //                         StompHeaderAccessor stompHeaderAccessor,
        //                         Principal principal
        log.debug("服务端接收到消息：", message);

        // 连接加入到在线map中
        ChatInfoVo chatInfoVo = new ChatInfoVo();
        chatInfoVo.setSid(UUID.randomUUID().toString().replace("-",""));
        chatInfoVo.setName(message.getName());
        WebSocketInfoUtil.addChatInfo(chatInfoVo);

        ChatMessage chatMessage = new ChatMessage();
        chatMessage.setMessageType(WebSocketConsts.MESSAGE_TYPE_SYSTEM);
        chatMessage.setSendTargetType(WebSocketConsts.SEND_MESSAGE_TYPE_PUBLIC);
        chatMessage.setMessage(message.getName() + "连接上了服务器。");
        messageSendUtil.sendMessage(chatMessage);
    }
}
