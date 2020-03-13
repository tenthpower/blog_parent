package com.blog.pojo;

import com.blog.controller.dto.SqlBaseDto;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * admin 和 role 的关系表
 */
@Entity
@Table(name="sysRoleAdmin")
public class SysRoleAdmin extends SqlBaseDto implements Serializable {

    private static final long serialVersionUID = -6433994498495986939L;

    @Id
    private String id;

    private String adminId;

    private String roleId;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAdminId() {
        return adminId;
    }

    public void setAdminId(String adminId) {
        this.adminId = adminId;
    }

    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }
}
