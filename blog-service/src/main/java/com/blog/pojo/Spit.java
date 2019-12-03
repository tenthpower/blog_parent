package com.blog.pojo;

import com.blog.dto.SqlBaseDto;
import org.springframework.data.annotation.Id;

import java.io.Serializable;
import java.util.Date;

/**
 * 吐槽
 */
public class Spit extends SqlBaseDto implements Serializable {

    private static final long serialVersionUID = 5514349943977680370L;

    @Id
    private String _id;

    private String content;

    private Date publishTime;

    private String userId;

    private String nickname;

    private Integer visits;

    private Integer tHumBup;

    private Integer share;

    private Integer comment;

    private Integer state;

    private String parentId;

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getPublishTime() {
        return publishTime;
    }

    public void setPublishTime(Date publishTime) {
        this.publishTime = publishTime;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Integer gettHumBup() {
        return tHumBup;
    }

    public void settHumBup(Integer tHumBup) {
        this.tHumBup = tHumBup;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public Integer getVisits() {
        return visits;
    }

    public void setVisits(Integer visits) {
        this.visits = visits;
    }


    public Integer getShare() {
        return share;
    }

    public void setShare(Integer share) {
        this.share = share;
    }

    public Integer getComment() {
        return comment;
    }

    public void setComment(Integer comment) {
        this.comment = comment;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

}
