package com.blog.pojo;

import com.blog.dto.SqlBaseDto;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * 后台管理账号
 */
@Entity
@Table(name="sysAdmin")
public class SysAdmin extends SqlBaseDto implements Serializable {

    private static final long serialVersionUID = 7081912784857107857L;

    @Id
    private String id;

    private String loginName;

    private String password;

    private String state;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
}
