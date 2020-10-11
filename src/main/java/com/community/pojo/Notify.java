package com.community.pojo;

public class Notify {
    private String code;
    private String notifyCode;
    private String notifyName;
    private String receiverName;
    private String receiverCode;
    private String outerCode;
    private Integer type;
    private Integer status;
    private Long createTime;
    private String questionCode;
    private String questionTitle;
    private User user;

    @Override
    public String toString() {
        return "Notify{" +
                "code='" + code + '\'' +
                ", notifyCode='" + notifyCode + '\'' +
                ", notifyName='" + notifyName + '\'' +
                ", receiverName='" + receiverName + '\'' +
                ", receiverCode='" + receiverCode + '\'' +
                ", outerCode='" + outerCode + '\'' +
                ", type=" + type +
                ", status=" + status +
                ", createTime=" + createTime +
                ", questionCode='" + questionCode + '\'' +
                ", questionTitle='" + questionTitle + '\'' +
                ", user=" + user +
                '}';
    }

    public String getQuestionTitle() {
        return questionTitle;
    }

    public void setQuestionTitle(String questionTitle) {
        this.questionTitle = questionTitle;
    }

    public String getQuestionCode() {
        return questionCode;
    }

    public void setQuestionCode(String questionCode) {
        this.questionCode = questionCode;
    }

    public Long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Long createTime) {
        this.createTime = createTime;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getNotifyCode() {
        return notifyCode;
    }

    public void setNotifyCode(String notifyCode) {
        this.notifyCode = notifyCode;
    }

    public String getNotifyName() {
        return notifyName;
    }

    public void setNotifyName(String notifyName) {
        this.notifyName = notifyName;
    }

    public String getReceiverName() {
        return receiverName;
    }

    public void setReceiverName(String receiverName) {
        this.receiverName = receiverName;
    }

    public String getReceiverCode() {
        return receiverCode;
    }

    public void setReceiverCode(String receiverCode) {
        this.receiverCode = receiverCode;
    }

    public String getOuterCode() {
        return outerCode;
    }

    public void setOuterCode(String outerCode) {
        this.outerCode = outerCode;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
