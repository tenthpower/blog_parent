package com.blog.pojo;

import com.blog.dto.SqlBaseDto;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/**
 *  api资源表 和 菜单 的关系表 N:N
 */
@Entity
@Table(name="SysApiresourcesMenu")
public class SysApiresourcesMenu extends SqlBaseDto implements Serializable {

    private static final long serialVersionUID = -6433994498495986939L;

    @Id
    private String id;

    private String apiresourcesaId;

    private String menuId;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getApiresourcesaId() {
        return apiresourcesaId;
    }

    public void setApiresourcesaId(String apiresourcesaId) {
        this.apiresourcesaId = apiresourcesaId;
    }

    public String getMenuId() {
        return menuId;
    }

    public void setMenuId(String menuId) {
        this.menuId = menuId;
    }
}
