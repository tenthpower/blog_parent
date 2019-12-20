package com.blog.vo;

import com.blog.websocket.vo.UserInfo;
import lombok.Data;

import javax.security.auth.Subject;
import java.security.Principal;

@Data
public class IPrincipal implements Principal {

    private UserInfo userInfo;

    public IPrincipal(){

    }

    public IPrincipal(UserInfo userInfo){
        this.userInfo = userInfo;
    }

    @Override
    public String getName() {
        return userInfo.getName();
    }

    @Override
    public boolean implies(Subject subject) {
        return false;
    }
}
