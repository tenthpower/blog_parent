package com.blog.service;

import com.blog.dao.AdminDao;
import com.blog.dto.user.AdminVo;
import com.blog.pojo.SysAdmin;
import com.blog.util.BeanCopierEx;
import com.blog.util.IdWorker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AdminService {

    @Autowired
    private AdminDao adminDao;

    @Autowired
    private IdWorker idWorker;

    @Autowired
    private BCryptPasswordEncoder encoder;

    /**
     * 查询所有的
     */
    public List<AdminVo> findAll() throws Exception {
        List<AdminVo> result = new ArrayList<AdminVo>();
        List<SysAdmin> sqlResult = adminDao.findAll();
        result = BeanCopierEx.copy(sqlResult,AdminVo.class);
        return result;
    }

    /**
     * 通过id 查询
     */
    public AdminVo findById(String id){
        AdminVo result = new AdminVo();
        SysAdmin sqlResult = adminDao.findById(id).get();
        BeanCopierEx.copy(sqlResult,result);
        return result;
    }

    /**
     * 添加
     */
    public void add(AdminVo adminVo){
        SysAdmin admin = new SysAdmin();
        BeanCopierEx.copy(adminVo, admin);
        admin.setId(idWorker.nextId());//设置ID
        admin.setPassword(encoder.encode(admin.getPassword()));// 加密密码
        adminDao.save(admin);
    }

    /**
     * 更新
     */
    public void update(AdminVo adminVo){
        SysAdmin admin = new SysAdmin();
        BeanCopierEx.copy(adminVo, admin);
        adminDao.save(admin);
    }

    /**
     * 删除
     */
    public void deleteById(String id){
        adminDao.deleteById(id);
    }

    /**
     * 根据登陆名和密码查询
     * @param loginName
     * @param password
     * @return
     */
    public AdminVo findByLoginnameAndPassword(String loginName, String
            password){
        SysAdmin admin = adminDao.findByLoginName(loginName);
        if (admin != null && encoder.matches(password, admin.getPassword())) {
            AdminVo result = new AdminVo();
            BeanCopierEx.copy(admin,result);
            return result;
        } else {
            return null;
        }
    }
}
