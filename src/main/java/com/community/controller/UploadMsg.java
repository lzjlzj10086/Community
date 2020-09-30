package com.community.controller;

public class UploadMsg {
    public int status;
    public String msg;
    public String path;

    public UploadMsg(){
        super();
    }

    public UploadMsg(int status, String msg, String path) {
        this.status = status;
        this.msg = msg;
        this.path = path;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}
