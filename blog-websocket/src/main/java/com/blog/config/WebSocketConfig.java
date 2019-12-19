package com.blog.config;

import com.blog.websocket.StompWS;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.config.ChannelRegistration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.messaging.support.ChannelInterceptorAdapter;
import org.springframework.messaging.support.MessageHeaderAccessor;
import org.springframework.web.socket.config.annotation.AbstractWebSocketMessageBrokerConfigurer;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;

import java.security.Principal;

/**
 * 参考： https://blog.csdn.net/huiyunfei/article/details/90719351
 * 开启webSocket 支持
 * registry.enableSimpleBroker("/topic")表示客户端订阅地址的前缀信息，
 *      也就是客户端接收服务端消息的地址的前缀信息（比较绕，看完整个例子，大概就能明白了）
 * registry.setApplicationDestinationPrefixes("/app")指服务端接收地址的前缀，
 *      意思就是说客户端给服务端发消息的地址的前缀
 * 2019年12月3日
 *
 * 参考: https://www.cnblogs.com/jmcui/p/8999998.html
 *
 */
@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig extends AbstractWebSocketMessageBrokerConfigurer {

    private final Logger log = LoggerFactory.getLogger(WebSocketConfig.class);


    /**
     * 这个方法的作用是添加一个服务端点，来接收客户端的连接。
     *      registry.addEndpoint("/socket")表示添加了一个/socket端点，客户端就可以通过这个端点来进行连接。
     * withSockJS()的作用是开启SockJS支持
     *
     * 将"/socket"注册为一个 STOMP 端点。这个路径与之前发送和接收消息的目的地路径有所不同。
     *      这是一个端点，客户端在订阅或发布消息到目的地路径前，要连接到该端点。
     * @param registry
     */
    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/socket").withSockJS();
    }

    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        //表示客户端订阅地址的前缀信息，也就是客户端接收服务端消息的地址的前缀信息
        // 订阅在线人数变更广播[/topic/online]
        // 订阅公聊消息广播[/topic/notice]
        registry.enableSimpleBroker("/queue", "/topic");

        //指服务端接收地址的前缀，意思就是说客户端给服务端发消息的地址的前缀
        registry.setApplicationDestinationPrefixes("/app");

        registry.setUserDestinationPrefix("/user");
    }

    @Override
    public void configureClientInboundChannel(ChannelRegistration registration) {
        log.debug("configureClientInboundChannel start..");
        registration.interceptors(new ChannelInterceptorAdapter() {
            @Override
            public Message<?> preSend(Message<?> message, MessageChannel channel) {
                //return super.preSend(message, channel);
                log.debug("configureClientInboundChannel preSend..");
                StompHeaderAccessor accessor = MessageHeaderAccessor.getAccessor(message,
                        StompHeaderAccessor.class);
                //1、判断是否首次连接
                if (StompCommand.CONNECT.equals(accessor.getCommand())) {
                    log.debug("configureClientInboundChannel 第一次 connect..");
                    // 判断用户名sid，密码telNo
                    String username = accessor.getNativeHeader("username").get(0);
                    String password = accessor.getNativeHeader("password").get(0);
                    log.debug("configureClientInboundChannel username=[{}],password=[{}]..", username, password);
                    Principal principal = new Principal() {
                        @Override
                        public String getName() {
                            return username;
                        }
                    };
                    accessor.setUser(principal);
                    log.debug("configureClientInboundChannel return message=[{}]..", message);
                    return message;
                } else {
                    log.debug("configureClientInboundChannel 不是第一次 connect..");
                }
                log.debug("configureClientInboundChannel return message=[{}]..", message);
                return message;
            }
        });
    }
}
