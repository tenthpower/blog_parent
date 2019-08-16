package com.tenthpower.service;

import com.tenthpower.dao.UserDao;
import com.tenthpower.dto.user.UserVo;
import com.tenthpower.pojo.User;
import com.tenthpower.util.BeanCopierEx;
import com.tenthpower.util.DateUtil;
import com.tenthpower.util.IdWorker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserDao userDao;

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private IdWorker idWorker;

    /**
     *  增加用户
     * @param userVo 用户信息
     * @param code 注册的验证码
     */
    public void add(UserVo userVo, String code) {
        //判断验证码是否正确
        String syscode =(String)redisTemplate.opsForValue().get("smscode_" + userVo.getTelNo());
        if(syscode==null){
            throw new RuntimeException("请点击获取短信验证码");
        }
        if(!syscode.equals(code)){
            throw new RuntimeException("验证码输入不正确");
        }

        userVo.setId(idWorker.nextId());
        userVo.setFollowcount(0);//关注数
        userVo.setFanscount(0);//粉丝数
        userVo.setOnline(0L);//在线时长
        userVo.setRegdate(DateUtil.getCurDate());//注册日期
        userVo.setUpdatedate(DateUtil.getCurDate());//更新日期
        userVo.setLastdate(DateUtil.getCurDate());//最后登陆日期
        User user = new User();
        BeanCopierEx.copy(userVo, user);
        userDao.save(user);
    }
}
