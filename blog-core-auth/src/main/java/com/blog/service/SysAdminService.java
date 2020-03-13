package com.blog.service;

import com.blog.dao.SysAdminDao;
import com.blog.controller.dto.user.SysAdminVo;
import com.blog.pojo.SysAdmin;
import com.blog.util.BeanCopierEx;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 系统用户
 */
@Service
public class SysAdminService {

    @Autowired
    private SysAdminDao sysAdminDao;

    /**
     * 通过用户名获取用户信息
     * @param userName
     * @return
     */
    public SysAdminVo findByName(String userName) {
        SysAdmin sysAdmin = sysAdminDao.findByLoginName(userName);
        SysAdminVo result = null;
        if (sysAdmin != null) {
            result = new SysAdminVo();
            BeanCopierEx.copy(sysAdmin, result);
        }
        return result;
    }

}
