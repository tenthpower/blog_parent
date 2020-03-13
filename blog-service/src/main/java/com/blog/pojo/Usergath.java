package com.blog.pojo;

import com.blog.controller.dto.SqlBaseDto;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name="tb_usergath")
public class Usergath extends SqlBaseDto implements Serializable {

    private static final long serialVersionUID = 3228796878363783602L;

    @Id
    private String id;

    private String userId;

    private String gathId;

    private Date exeTime;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getGathId() {
        return gathId;
    }

    public void setGathId(String gathId) {
        this.gathId = gathId;
    }

    public Date getExeTime() {
        return exeTime;
    }

    public void setExeTime(Date exeTime) {
        this.exeTime = exeTime;
    }
}
