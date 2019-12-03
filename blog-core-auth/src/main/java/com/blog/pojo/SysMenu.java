package com.blog.pojo;

import com.blog.dto.SqlBaseDto;
import org.apache.commons.lang3.StringUtils;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * 菜单表
 */
@Entity
@Table(name="sysMenu")
public class SysMenu extends SqlBaseDto implements Serializable {

    private static final long serialVersionUID = -6433994498495986939L;

    @Id
    private String id;

    private String menuName;

    private String description;

    private String menuType;

    private String icon;

    private String openType;

    private String viewUrl;

    private String parentId;

    private String idPath;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMenuName() {
        return menuName;
    }

    public void setMenuName(String menuName) {
        this.menuName = menuName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getMenuType() {
        return menuType;
    }

    public void setMenuType(String menuType) {
        this.menuType = menuType;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getOpenType() {
        return openType;
    }

    public void setOpenType(String openType) {
        this.openType = openType;
    }

    public String getViewUrl() {
        return viewUrl;
    }

    public void setViewUrl(String viewUrl) {
        this.viewUrl = viewUrl;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        if (StringUtils.isBlank(parentId)) {
            this.parentId = "00000000-0000-0000-0000-000000000000";
        } else {
            this.parentId = parentId;
        }
    }

    public String getIdPath() {
        return idPath;
    }

    public void setIdPath(String idPath) {
        this.idPath = idPath;
    }
}
