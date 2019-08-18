package com.blog.pojo;

import com.blog.dto.SqlBaseDto;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/**
 *  api资源表 的关系表
 */
@Entity
@Table(name="SysApiresources")
public class SysApiresources extends SqlBaseDto implements Serializable {

    private static final long serialVersionUID = -6433994498495986939L;

    @Id
    private String id;

    private String apiDescription;

    private String apiUrl;

    private String apiReqtType;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getApiDescription() {
        return apiDescription;
    }

    public void setApiDescription(String apiDescription) {
        this.apiDescription = apiDescription;
    }

    public String getApiUrl() {
        return apiUrl;
    }

    public void setApiUrl(String apiUrl) {
        this.apiUrl = apiUrl;
    }

    public String getApiReqtType() {
        return apiReqtType;
    }

    public void setApiReqtType(String apiReqtType) {
        this.apiReqtType = apiReqtType;
    }
}
