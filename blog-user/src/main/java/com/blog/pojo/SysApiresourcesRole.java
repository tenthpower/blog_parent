package com.blog.pojo;

import com.blog.dto.SqlBaseDto;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/**
 *  api资源表 和 角色 的关系表N:N
 */
@Entity
@Table(name="SysApiresourcesRole")
public class SysApiresourcesRole extends SqlBaseDto implements Serializable {

    private static final long serialVersionUID = -6433994498495986939L;

    @Id
    private String id;

    private String apiresourcesaId;

    public String getApiresourcesaId() {
        return apiresourcesaId;
    }

    public void setApiresourcesaId(String apiresourcesaId) {
        this.apiresourcesaId = apiresourcesaId;
    }

    private String roleId;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }



    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }
}
