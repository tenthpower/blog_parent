package com.blog.service.impl;

import com.blog.consts.WebSocketConsts;
import com.blog.controller.dto.LoginInReqt;
import com.blog.controller.dto.LoginInResp;
import com.blog.dao.UserDao;
import com.blog.pojo.User;
import com.blog.service.UserInfoService;
import com.blog.util.BeanCopierEx;
import com.blog.util.DateUtil;
import com.blog.util.ShaEncodeUtil;
import com.blog.websocket.vo.UserInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.beans.BeanCopier;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service("UserInfoService")
@Transactional(rollbackFor = Exception.class)
public class UserInfoServiceImpl implements UserInfoService {

    private final Logger log = LoggerFactory.getLogger(UserInfoServiceImpl.class);

    @Autowired
    private UserDao userDao;

    @Override
    public LoginInResp loginIn(LoginInReqt params) throws Exception {
        log.info("loginIn 开始，请求参数=[{}]", params);
        LoginInResp result = new LoginInResp();
        // TODO 验证手机号

        String sid = ShaEncodeUtil.shaEncode(params.getTelNo());
        // 查询用户是否存在数据库
        if (!userDao.existsById(sid)) {
            log.info("第一次登陆，创建用户信息。");
            User user = new User();
            user.setId(sid);
            user.setName(params.getName());
            user.setTelNo(params.getTelNo());
            user.setIsOnline(WebSocketConsts.STATUS_ONLINE);
            user.setCreateDate(DateUtil.getCurDate());
            user.setIsDel(0);
            userDao.save(user);
            log.info("用户信息创建成功。");
        } else {
            User user = userDao.findById(sid).get();
            user.setIsOnline(WebSocketConsts.STATUS_ONLINE);
            user.setName(params.getName());
            userDao.save(user);
            log.info("用户信息状态更新成功。");
        }

        // 获取所有在线的用户列表
        List<UserInfo> onlinePublicUsers = new ArrayList<>();
        List<User> userList = userDao.findAll();
        if (userList != null && userList.size() > 0){
            userList.stream().filter(u -> u.getIsOnline() == WebSocketConsts.STATUS_ONLINE)
                    .forEach(u ->
                    {
                        UserInfo u2 = new UserInfo();
                        BeanCopierEx.copy(u,u2);
                        u2.setSid(u.getId());
                        onlinePublicUsers.add(u2);
                    });
        }
        result.setSid(sid);// 当前sid
        result.setPublicCount(userList.size());// 总人数
        result.setOnlinePublicCount(onlinePublicUsers.size());// 在线总数
        result.setOnlinePublicUsers(onlinePublicUsers);// 在线人数列表

        log.info("loginIn 结束，返回结果=[{}]", result);
        return result;
    }

    @Override
    public UserInfo getUserInfo(UserInfo params) {
        return null;
    }

    @Override
    public UserInfo updateUserInfo(UserInfo params) {
        return null;
    }
}
