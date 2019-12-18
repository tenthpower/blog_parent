package com.blog.websocket;

import com.blog.consts.WebSocketConsts;
import com.blog.util.ShaEncodeUtil;
import com.blog.util.WSMessageUtil;
import com.blog.util.vo.WSMessageVo;
import com.blog.vo.CustomMessage;
import com.blog.websocket.vo.UserInfo;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

import java.text.MessageFormat;

@Controller
public class StompWS {

    private final Logger log = LoggerFactory.getLogger(StompWS.class);

    @Autowired
    private WSMessageUtil WSMessageUtil;


    /**
     * @Description:这个方法是接收客户端发送功公告的WebSocket请求，使用的是@MessageMapping
     * @Author: 和彦鹏
     * @Date: 2019年12月5日
     */
    @MessageMapping("/change-notice")
    @SendTo("/topic/online")
    public void greeting(CustomMessage message) throws Exception {
        log.debug("服务端接收到连接信息：", message);
        // 连接加入到在线map中
        UserInfo userInfo = new UserInfo();
        userInfo.setTelNo(message.getTelNo());
        if (!StringUtils.equals(message.getSid(), ShaEncodeUtil.shaEncode(message.getTelNo()))) {
            return;
        }
        userInfo.setSid(message.getSid());

        // 加入人员信息
        SocketSessionRegistry.addUserInfo(userInfo);

        // 广播系统消息
        WSMessageVo wsMessageVo =  new WSMessageVo();
        wsMessageVo.setMessageType(WebSocketConsts.TYPE_SYSTEM);
        wsMessageVo.setSendMessage(MessageFormat.format("[{0}]连接到服务器。", message.getName()));
        wsMessageVo.setSendSid(WebSocketConsts.TYPE_SYSTEM);
        wsMessageVo.setSendUserName(WebSocketConsts.TYPE_SYSTEM);
        WSMessageUtil.sendMessage(wsMessageVo);

        // 广播在线人员信息
        WSMessageUtil.sendOnlineInfo();
    }
}
