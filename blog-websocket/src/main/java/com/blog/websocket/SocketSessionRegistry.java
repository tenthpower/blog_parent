package com.blog.websocket;

import com.blog.consts.WebSocketConsts;
import com.blog.util.ShaEncodeUtil;
import com.blog.websocket.vo.UserInfo;
import com.blog.websocket.vo.GroupInfo;
import com.blog.websocket.vo.FriendInfo;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 连接信息工具类：模拟数据库
 */
@Component
public class SocketSessionRegistry {

    private final static Logger log = LoggerFactory.getLogger(SocketSessionRegistry.class);


    /**
     * 当前总现在人数信息
     */
    public static ConcurrentHashMap<String, UserInfo> registryUserInfoMap = new ConcurrentHashMap();

    /**
     * 讨论组信息
     */
    public static ConcurrentHashMap<String, GroupInfo> groupInfoMap = new ConcurrentHashMap();

    /**
     * 私聊信息
     */
    public static ConcurrentHashMap<String, FriendInfo> friendInfoMap = new ConcurrentHashMap();

    /**
     * 加入当前在线人数
     * @param userInfo
     */
    public static void addUserInfo(UserInfo userInfo){
        userInfo.setIsOnline(WebSocketConsts.STATUS_ONLINE);// 在线
        registryUserInfoMap.put(userInfo.getSid(), userInfo);// sid 唯一，如果以前存在这个人，则put会更新
    }

    /**
     * 下线
     */
    public static void unOnline(String sid){
        if (registryUserInfoMap.containsKey(sid)) {
            registryUserInfoMap.get(sid).setIsOnline(WebSocketConsts.STATUS_UN_ONLINE);
        }
    }

    /**
     * 产生讨论组
     */
    public static String createGroup(GroupInfo groupInfo){
        if (StringUtils.isBlank(groupInfo.getGroupId())) {
            groupInfo.setGroupId(UUID.randomUUID().toString());
        }
        groupInfoMap.put(groupInfo.getGroupId(), groupInfo);
        return groupInfo.getGroupId();
    }

    /**
     * User 加入讨论组
     */
    public static boolean joinGroup(String groupId, String userSId) {
        if (groupInfoMap.contains(groupId)) {
            groupInfoMap.get(groupId).getUserSIds().add(userSId);
            return true;
        } else {
            log.error("讨论组=[{}]不存在", groupId);
            return false;
        }
    }

    /**
     * 退出讨论组
     */
    public static boolean exitGroup(String groupId, String userSId){
        if (groupInfoMap.contains(groupId)) {
            groupInfoMap.get(groupId).getUserSIds().remove(userSId);
            return true;
        } else {
            log.error("讨论组=[{}]不存在", groupId);
            return false;
        }
    }

    /**
     * 解散讨论组
     */
    public static boolean deleteGroup(String groupId){
        if (groupInfoMap.contains(groupId)) {
            groupInfoMap.remove(groupId);
            return true;
        } else {
            log.error("讨论组=[{}]不存在", groupId);
            return false;
        }
    }

    /**
     * 创建私聊链接:privatelyId = sidA + sidB
     */
    public static boolean createFriendInfo(FriendInfo friendInfo) throws Exception {
        if (StringUtils.isBlank(friendInfo.getASid()) || StringUtils.isBlank(friendInfo.getBSid())) {
            log.error("创建好友信息无效");
            return false;
        }
        if (StringUtils.isBlank(friendInfo.getFId())) {
            String fId = ShaEncodeUtil.shaEncode(friendInfo.getASid(), friendInfo.getBSid());
            friendInfo.setFId(fId);
        }
        friendInfoMap.put(friendInfo.getFId(), friendInfo);
        return true;
    }

    public static boolean deleteFriendInfo(String fId){
        if (friendInfoMap.containsKey(fId)) {
            friendInfoMap.remove(fId);
            return true;
        } else {
            log.error("删除好友信息无效");
            return false;
        }
    }

}
