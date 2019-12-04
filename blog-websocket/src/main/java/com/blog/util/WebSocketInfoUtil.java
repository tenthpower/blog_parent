package com.blog.util;

import com.blog.vo.ChatInfoVo;
import com.blog.vo.GroupChatInfoVo;
import com.blog.vo.PrivatelyChatInfoVo;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 连接信息工具类
 */
@Component
public class WebSocketInfoUtil {

    /**
     * 当前总现在人数信息
     */
    public static ConcurrentHashMap<String, ChatInfoVo> chatInfoMap = new ConcurrentHashMap();

    /**
     * 讨论组信息
     */
    public static ConcurrentHashMap<String, GroupChatInfoVo> groupChatInfoMap = new ConcurrentHashMap();

    /**
     * 私聊信息
     */
    public static ConcurrentHashMap<String, PrivatelyChatInfoVo> privatelyChatInfoMap = new ConcurrentHashMap();

    /**
     * 加入当前在线人数
     * @param chatInfoVo
     */
    public static void addChatInfo(ChatInfoVo chatInfoVo){
        chatInfoMap.put(chatInfoVo.getSid(), chatInfoVo);
    }

    /**
     * 下线
     */
    public static void subChatInfo(String sid){
        if (chatInfoMap.containsKey(sid)) {
            chatInfoMap.remove(sid);
        }
    }

    /**
     * 产生讨论组
     */
    public static void createGroupChatInfo(GroupChatInfoVo groupChatInfoVo){
        groupChatInfoMap.put(groupChatInfoVo.getGroupId(), groupChatInfoVo);
    }

    /**
     * 聊天人加入讨论组
     */
    public static void addGroupChatInfo(String groupId, ChatInfoVo chatInfoVo) {
        if (groupChatInfoMap.contains(groupId)) {
            List<ChatInfoVo> chatInfoList = groupChatInfoMap.get(groupId).getChatInfoList();
            if (null == chatInfoList) {
                chatInfoList = new ArrayList<>();
            }
            for (int i = 0; i < chatInfoList.size(); i++) {
                if (chatInfoVo.getSid().equalsIgnoreCase(chatInfoList.get(i).getSid())){
                    // 发送消息
                    return;
                }
            }
            chatInfoList.add(chatInfoVo);
        }
    }

    /**
     * 退出讨论组
     */
    public static void exitGroupChatInfo(String groupId, ChatInfoVo chatInfoVo){

    }

    /**
     * 解散讨论组
     */
    public static void closeGroupChatInfo(String groupId){

    }

    /**
     * 创建私聊链接:privatelyId = sidA + sidB
     */
    public static void createPrivatelyChat(String privatelyId, PrivatelyChatInfoVo privatelyChatInfoVo){
        privatelyChatInfoMap.put(privatelyId, privatelyChatInfoVo);
    }

    public static void closePrivatelyChat(String privatelyId){
        if (privatelyChatInfoMap.containsKey(privatelyId)) {
            privatelyChatInfoMap.remove(privatelyId);
        }
    }

    /**
     * sidA + sidB 返回唯一 字符串
     */
    public static String privatelyId(String sidA,String sidB) {
        if (sidA.compareTo(sidB) < 0){
            return sidA + sidB;
        } else {
            return sidB + sidA;
        }
    }
}
