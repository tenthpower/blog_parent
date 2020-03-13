package com.blog.controller.dto;

import com.blog.util.DateUtil;

import java.io.Serializable;
import java.util.Date;

public class SqlBaseDto implements Serializable {
    /**
     *
     */
    private static final long serialVersionUID = 8288622251267723189L;

    /**
     * 最后修改时间
     */
    private Date lastDate;

    /**
     * 创建时间
     */
    private Date createDate;

    /**
     * 创建人
     */
    private String createBy;

    /**
     * 是否删除
     */
    private Integer isDel = 0;

    public Date getLastDate() {
        return lastDate;
    }


    public void setLastDate(Date lastDate) {
        if (lastDate == null) {
            this.lastDate = DateUtil.getCurDate();
        } else {
            this.lastDate = lastDate;
        }
    }


    public Date getCreateDate() {
        return createDate;
    }


    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }


    public String getCreateBy() {
        return createBy;
    }


    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }


    public Integer getIsDel() {
        return isDel;
    }

    public void setIsDel(Integer isDel) {
        this.isDel = isDel;
    }
}
