package com.blog.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.AbstractWebSocketMessageBrokerConfigurer;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;

/**
 * 参考： https://blog.csdn.net/huiyunfei/article/details/90719351
 * 开启webSocket 支持
 * registry.enableSimpleBroker("/topic")表示客户端订阅地址的前缀信息，
 *      也就是客户端接收服务端消息的地址的前缀信息（比较绕，看完整个例子，大概就能明白了）
 * registry.setApplicationDestinationPrefixes("/app")指服务端接收地址的前缀，
 *      意思就是说客户端给服务端发消息的地址的前缀
 * 2019年12月3日
 */
@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig extends AbstractWebSocketMessageBrokerConfigurer {

    /**
     * 这个方法的作用是添加一个服务端点，来接收客户端的连接。
     * registry.addEndpoint("/socket")表示添加了一个/socket端点，客户端就可以通过这个端点来进行连接。
     * withSockJS()的作用是开启SockJS支持，
     * @param registry
     */
    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/socket").withSockJS();
    }
    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        //表示客户端订阅地址的前缀信息，也就是客户端接收服务端消息的地址的前缀信息
        registry.enableSimpleBroker("/topic");
        //指服务端接收地址的前缀，意思就是说客户端给服务端发消息的地址的前缀
        registry.setApplicationDestinationPrefixes("/app");
    }
}