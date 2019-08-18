package com.blog.service;

import com.blog.dao.UserDao;
import com.blog.dto.user.UserVo;
import com.blog.pojo.User;
import com.blog.util.BeanCopierEx;
import com.blog.util.DateUtil;
import com.blog.util.IdWorker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserDao userDao;

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private IdWorker idWorker;

    @Autowired
    private BCryptPasswordEncoder encoder;

    /**
     *  增加用户
     * @param userVo 用户信息
     * @param code 注册的验证码
     */
    public void add(UserVo userVo, String code) {
        //判断验证码是否正确
        String syscode =(String)redisTemplate.opsForValue().get("smscode_" + userVo.getTelNo());
//        if(syscode==null){
//            throw new RuntimeException("请点击获取短信验证码");
//        }
//        if(!syscode.equals(code)){
//            throw new RuntimeException("验证码输入不正确");
//        }

        userVo.setId(idWorker.nextId());
        userVo.setFollowCount(0);//关注数
        userVo.setFansCount(0);//粉丝数
        userVo.setOnLine(0L);//在线时长
        userVo.setRegTime(DateUtil.getCurDate());//注册日期
        userVo.setUpdateTime(DateUtil.getCurDate());//更新日期
        userVo.setLastTime(DateUtil.getCurDate());//最后登陆日期
        User user = new User();
        BeanCopierEx.copy(userVo, user);
        user.setPassword(encoder.encode(user.getPassword()));// 加密密码
        userDao.save(user);
    }

    /**
     * 根据手机号和密码查询用户
     * @param telNo
     * @param password
     * @return
     */
    public UserVo findByTelNoAndPassword(String telNo,String password){
        User user = userDao.findByTelNo(telNo);
        if(user!=null && encoder.matches(password,user.getPassword())){
            UserVo userVo = new UserVo();
            BeanCopierEx.copy(user, userVo);
            return userVo;
        } else {
            return null;
        }
    }

}
