package com.blog.config;

import com.blog.consts.WebSocketConsts;
import com.blog.util.DateUtil;
import com.blog.util.WSMessageUtil;
import com.blog.util.vo.WSMessageVo;
import com.blog.vo.IPrincipal;
import com.blog.websocket.SocketSessionRegistry;
import com.blog.websocket.StompWS;
import com.blog.websocket.vo.UserInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.config.ChannelRegistration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.messaging.support.ChannelInterceptorAdapter;
import org.springframework.messaging.support.MessageHeaderAccessor;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.config.annotation.AbstractWebSocketMessageBrokerConfigurer;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.server.HandshakeInterceptor;
import org.springframework.web.socket.server.support.DefaultHandshakeHandler;

import javax.servlet.http.HttpSession;
import java.security.Principal;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

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
 * 参考: https://segmentfault.com/a/1190000009038991
 *
 */
@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig extends AbstractWebSocketMessageBrokerConfigurer {

    private final Logger log = LoggerFactory.getLogger(WebSocketConfig.class);

    @Autowired
    private WSMessageUtil wsMessageUtil;

    /**
     * 这个方法的作用是添加一个服务端点，来接收客户端的连接。
     *      registry.addEndpoint("/socket")表示添加了一个/socket端点，客户端就可以通过这个端点来进行连接。
     * withSockJS()的作用是开启SockJS支持
     * @param registry
     */
    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/socket")
                .setHandshakeHandler(defaultHandshakeHandler())
                .setAllowedOrigins("*")
                .withSockJS()
                .setInterceptors(httpSessionHandshakeInterceptor());
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

                    WSMessageVo wsMessageVo =  new WSMessageVo();
                    wsMessageVo.setMessageType(WebSocketConsts.TYPE_SYSTEM);
                    String name = SocketSessionRegistry.registryUserInfoMap.get(username).getName();
                    wsMessageVo.setSendMessage(MessageFormat.format("[{0}]连接到服务器。", name));
                    wsMessageVo.setSendSid(WebSocketConsts.TYPE_SYSTEM);
                    wsMessageVo.setSendUserName(WebSocketConsts.TYPE_SYSTEM);
                    wsMessageVo.setSendDate(DateUtil.toString(DateUtil.getCurDate(),DateUtil.DATE_PATTERN_YYYYMMDDHHmmSS));
                    wsMessageUtil.sendMessage(wsMessageVo);// 发送系统消息
                    wsMessageUtil.sendOnlineChangeStatus(username);// 发送人员变动


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

    private DefaultHandshakeHandler defaultHandshakeHandler() {
//        return new DefaultHandshakeHandler() {
//            @Override
//            protected Principal determineUser(ServerHttpRequest request, WebSocketHandler wsHandler, Map<String, Object> attributes) {
//                Principal principal = request.getPrincipal();
//                if (principal == null) {
//                    Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
//                    authorities.add(new SimpleGrantedAuthority(AuthoritiesConstants.ANONYMOUS));
//                    principal = new AnonymousAuthenticationToken("WebsocketConfiguration", "anonymous", authorities);
//                }
//                return principal;
//            }
//        };
        return new DefaultHandshakeHandler(){
            @Override
            protected Principal determineUser(ServerHttpRequest request, WebSocketHandler wsHandler, Map<String, Object> attributes) {
                IPrincipal iPrincipal = (IPrincipal)request.getPrincipal();
                iPrincipal.setUserInfo((UserInfo)attributes.get(WebSocketConsts.TYPE_USER));
                return iPrincipal;
            }
        };
    }

    @Bean
    public HandshakeInterceptor httpSessionHandshakeInterceptor() {
        return new HandshakeInterceptor() {
            @Override
            public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler, Map<String, Object> attributes) throws Exception {
                if (request instanceof ServletServerHttpRequest) {
                    ServletServerHttpRequest servletRequest = (ServletServerHttpRequest) request;
                    HttpSession session = servletRequest.getServletRequest().getSession(false);
                    if (session == null || session.getAttribute(WebSocketConsts.TYPE_USER) == null) {
                        log.error("websocket权限拒绝");
//                        return false;
                    }
//                    attributes.put(WebSocketConsts.TYPE_USER,session.getAttribute(WebSocketConsts.TYPE_USER));
                    attributes.put(WebSocketConsts.IP_ADDRESS, servletRequest.getRemoteAddress());
                }
                return true;
            }

            @Override
            public void afterHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler, Exception exception) {
            }
        };
    }
}
