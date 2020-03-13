package com.blog.controller.dto.user;

import lombok.Data;

import java.io.Serializable;

/**
 * 权限表
 */
@Data
public class SysRoleVo implements Serializable {

    private static final long serialVersionUID = -6433994498495986939L;

    private String id;

    private String roleName;

    private String description;

}
