package com.blog.pojo;

import com.blog.dto.SqlBaseDto;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Table(name="tb_channel")
public class Channel extends SqlBaseDto implements Serializable {

    private static final long serialVersionUID = 6173950204798263682L;

    @Id
    private String id;

    private String name;

    private Integer state;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }
}
