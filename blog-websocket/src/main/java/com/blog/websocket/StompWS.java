package com.blog.websocket;

import com.blog.consts.WebSocketConsts;
import com.blog.util.DateUtil;
import com.blog.util.ShaEncodeUtil;
import com.blog.util.WSMessageUtil;
import com.blog.util.vo.WSMessageVo;
import com.blog.vo.CustomMessage;
import com.blog.websocket.vo.UserInfo;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageExceptionHandler;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Controller;

import java.security.Principal;
import java.text.MessageFormat;

/**
 * STOMP 的消息根据前缀的不同分为三种。如下，
 *  以 /app 开头的消息都会被路由到带有@MessageMapping或 @SubscribeMapping 注解的方法中；
 *  以/topic 或 /queue 开头的消息都会发送到STOMP代理中，根据你所选择的STOMP代理不同，目的地的可选前缀也会有所限制；
 *  以/user开头的消息会将消息重路由到某个用户独有的目的地上。
 */
@Controller
public class StompWS {

    private final Logger log = LoggerFactory.getLogger(StompWS.class);

    @Autowired
    private WSMessageUtil WSMessageUtil;


    /**
     * @Description:这个方法是接收客户端发送功的WebSocket请求，使用的是@MessageMapping
     * @Author: 和彦鹏
     * @Date: 2019年12月5日
     */
    @MessageMapping("/change-notice")
    @SendTo("/topic/online")
    public void greeting(CustomMessage message,StompHeaderAccessor stompHeaderAccessor,Principal principal) throws Exception {
        log.debug("服务端接收到连接信息：", message);
        // 连接加入到在线map中
        UserInfo userInfo = new UserInfo();
        userInfo.setTelNo(message.getTelNo());
        userInfo.setSid(message.getSid());

        // 加入人员信息
        SocketSessionRegistry.addUserInfo(userInfo);

        // 广播系统消息
        WSMessageVo wsMessageVo =  new WSMessageVo();
        wsMessageVo.setMessageType(WebSocketConsts.TYPE_SYSTEM);
        wsMessageVo.setSendMessage(MessageFormat.format("[{0}]连接到服务器。", message.getName()));
        wsMessageVo.setSendSid(WebSocketConsts.TYPE_SYSTEM);
        wsMessageVo.setSendUserName(WebSocketConsts.TYPE_SYSTEM);
        wsMessageVo.setSendDate(DateUtil.toString(DateUtil.getCurDate(),DateUtil.DATE_PATTERN_YYYYMMDDHHmmSS));
        WSMessageUtil.sendMessage(wsMessageVo);

        // 广播在线人员信息
        WSMessageUtil.sendOnlineInfo();
    }

    @MessageMapping("/shout")
    @SendToUser("/queue/notifications")
    public CustomMessage userStomp(Principal principal, CustomMessage customMessage) {
        String name = principal.getName();
        String message = customMessage.getMessage();
        log.info("认证的名字是：{}，收到的消息是：{}", name, message);
        return customMessage;
    }

    @MessageMapping("/singleShout")
    public void singleUser(CustomMessage customMessage, StompHeaderAccessor stompHeaderAccessor) {
        String message = customMessage.getMessage();
        log.info("接收到消息：" + message);
        Principal user = stompHeaderAccessor.getUser();
        WSMessageVo wsMessageVo = new WSMessageVo();
        wsMessageVo.setSendDate(DateUtil.toString(DateUtil.getCurDate(),DateUtil.DATE_PATTERN_YYYYMMDDHHmmSS));
        wsMessageVo.setMessageType(WebSocketConsts.TYPE_USER);
        wsMessageVo.setSendMessage(customMessage.getMessage());
        wsMessageVo.setSendSid(customMessage.getSid());
        wsMessageVo.setSendUserName(SocketSessionRegistry.registryUserInfoMap.get(customMessage.getSid()).getName());
        WSMessageUtil.singleSendMessage(wsMessageVo, user.getName());
    }

    /**
     * 在处理消息的时候，有可能会出错并抛出异常。因为STOMP消息异步的特点，
     * 发送者可能永远也不会知道出现了错误。@MessageExceptionHandler标注的方法能够处理消息方法中所抛出的异常。
     * 我们可以把错误发送给用户特定的目的地上，然后用户从该目的地上订阅消息，从而用户就能知道自己出现了什么错误
     * @param t
     * @return
     */
    @MessageExceptionHandler(Exception.class)
    @SendToUser("/queue/errors")
    public Exception handleExceptions(Exception t){
        t.printStackTrace();
        return t;
    }

}
