package com.demo.entity;

import java.io.Serializable;
import java.util.Date;

public class FirstComments implements Serializable {
    private Long id;

    private Long memberId;

    private String memberName;

    private String memberUrl;

    private String commentsContent;

    private Long articleId;

    private Integer articleType;

    private Integer placedAtTheTop;

    private Long thumbUpNumber;

    private Date createTime;

    private static final long serialVersionUID = 1L;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getMemberId() {
        return memberId;
    }

    public void setMemberId(Long memberId) {
        this.memberId = memberId;
    }

    public String getMemberName() {
        return memberName;
    }

    public void setMemberName(String memberName) {
        this.memberName = memberName == null ? null : memberName.trim();
    }

    public String getMemberUrl() {
        return memberUrl;
    }

    public void setMemberUrl(String memberUrl) {
        this.memberUrl = memberUrl == null ? null : memberUrl.trim();
    }

    public String getCommentsContent() {
        return commentsContent;
    }

    public void setCommentsContent(String commentsContent) {
        this.commentsContent = commentsContent == null ? null : commentsContent.trim();
    }

    public Long getArticleId() {
        return articleId;
    }

    public void setArticleId(Long articleId) {
        this.articleId = articleId;
    }

    public Integer getArticleType() {
        return articleType;
    }

    public void setArticleType(Integer articleType) {
        this.articleType = articleType;
    }

    public Integer getPlacedAtTheTop() {
        return placedAtTheTop;
    }

    public void setPlacedAtTheTop(Integer placedAtTheTop) {
        this.placedAtTheTop = placedAtTheTop;
    }

    public Long getThumbUpNumber() {
        return thumbUpNumber;
    }

    public void setThumbUpNumber(Long thumbUpNumber) {
        this.thumbUpNumber = thumbUpNumber;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}