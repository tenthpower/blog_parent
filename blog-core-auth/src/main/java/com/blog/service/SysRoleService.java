package com.blog.service;

import com.blog.dao.SysAdminDao;
import com.blog.dao.SysRoleDao;
import com.blog.dto.user.SysAdminVo;
import com.blog.dto.user.SysRoleVo;
import com.blog.pojo.SysAdmin;
import com.blog.pojo.SysRole;
import com.blog.util.BeanCopierEx;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * 角色信息
 */
@Service
public class SysRoleService {

    @Autowired
    private SysRoleDao sysRoleDao;

    /**
     * 通过用户名获取所有的角色信息信息
     * @param adminId
     * @return
     */
    public List<SysRoleVo> findByAdminId(String adminId) {
        List<SysRole> roles = sysRoleDao.findByAdminId(adminId);
        if (roles != null && roles.size() > 0) {
            List<SysRoleVo> result = new ArrayList<>();
            try {
                result = BeanCopierEx.copy(roles, SysRoleVo.class);
            } catch (Exception e){
                e.printStackTrace();
            }
            return result;
        }
        return null;
    }

}
