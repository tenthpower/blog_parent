package com.blog.dao;

import com.blog.pojo.SysAdmin;
import com.blog.pojo.SysRole;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * JpaRepository提供了基本的增删改查
 * JpaSpecificationExecutor用于做复杂的条件查询
 */
public interface SysRoleDao extends JpaRepository<SysRole, String>, JpaSpecificationExecutor<SysRole> {

    /**
     * 通过adminId 查询该用户的角色
     * @param adminId
     * @return
     */
    @Query("select r from SysRole r where id in(select roleId from SysRoleAdmin where adminId= :adminId ) ")
    public List<SysRole> findByAdminId(@Param("adminId") String adminId);

}
