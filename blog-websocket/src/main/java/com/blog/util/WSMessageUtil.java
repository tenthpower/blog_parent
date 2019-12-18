package com.blog.util;

import com.blog.consts.WebSocketConsts;
import com.blog.util.vo.OnlineInfoVo;
import com.blog.util.vo.WSMessageVo;
import com.blog.websocket.SocketSessionRegistry;
import com.blog.websocket.vo.UserInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 消息广播工具类
 */
@Component
public class WSMessageUtil {

    @Autowired
    private SimpMessageSendingOperations messagingTemplate;

    /**
     * 公聊消息：消息推送
     * @param wsMessageVo
     */
    public void sendMessage(WSMessageVo wsMessageVo){
        messagingTemplate.convertAndSend("/topic/notice", wsMessageVo);
    }

    /**
     * 公聊人员信息：消息推送
     */
    public void sendOnlineInfo() {
        OnlineInfoVo onLineInfoVo = new OnlineInfoVo();
        Collection<UserInfo> userInfos = SocketSessionRegistry.registryUserInfoMap.values();
        long onlineCount =
                userInfos.stream().filter(userInfo -> userInfo.getIsOnline() == WebSocketConsts.STATUS_ONLINE).count();
        long count = userInfos.stream().count();
        List<UserInfo> userList = userInfos.stream().collect(Collectors.toList());
        Collections.sort(userList, new Comparator<UserInfo>(){
            public int compare(UserInfo u1,UserInfo u2){
                return u1.getIsOnline() - u2.getIsOnline();
            }
        });
        onLineInfoVo.setCount(count);
        onLineInfoVo.setOnlineCount(onlineCount);
        onLineInfoVo.setUserInfos(userList);
        messagingTemplate.convertAndSend("/topic/online", onLineInfoVo);
    }

}
