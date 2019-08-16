package com.tenthpower.service;

import com.tenthpower.dao.AdminDao;
import com.tenthpower.dto.user.AdminVo;
import com.tenthpower.pojo.Admin;
import com.tenthpower.util.BeanCopierEx;
import com.tenthpower.util.IdWorker;
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
        List<Admin> sqlResult = adminDao.findAll();
        result = BeanCopierEx.copy(sqlResult,AdminVo.class);
        return result;
    }

    /**
     * 通过id 查询
     */
    public AdminVo findById(String id){
        AdminVo result = new AdminVo();
        Admin sqlResult = adminDao.findById(id).get();
        BeanCopierEx.copy(sqlResult,result);
        return result;
    }

    /**
     * 添加
     */
    public void add(AdminVo adminVo){
        Admin admin = new Admin();
        BeanCopierEx.copy(adminVo, admin);
        admin.setId(idWorker.nextId());//设置ID
        admin.setPassword(encoder.encode(admin.getPassword()));// 加密密码
        adminDao.save(admin);
    }

    /**
     * 更新
     */
    public void update(AdminVo adminVo){
        Admin admin = new Admin();
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
     * @param loginname
     * @param password
     * @return
     */
    public AdminVo findByLoginnameAndPassword(String loginname, String
            password){
        Admin admin = adminDao.findByLoginname(loginname);
        if (admin != null && encoder.matches(password, admin.getPassword())) {
            AdminVo result = new AdminVo();
            BeanCopierEx.copy(admin,result);
            return result;
        } else {
            return null;
        }
    }
}
