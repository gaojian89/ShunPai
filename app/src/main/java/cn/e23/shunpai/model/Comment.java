package cn.e23.shunpai.model;

import java.io.Serializable;

/**
 * Created by jian on 2016/5/12.
 */
public class Comment implements Serializable {

    /**
     * id : 56
     * commentid : content_9-38-1
     * siteid : 1
     * userid : 0
     * username :
     * creat_at : 1460684844
     * ip : 124.133.5.218
     * status : 1
     * content : 你说
     * direction : 0
     * support : 0
     * reply : 0
     */

    private String id;
    private String commentid;
    private String siteid;
    private String userid;
    private String username;
    private String creat_at;
    private String ip;
    private String status;
    private String content;
    private String direction;
    private String support;
    private String reply;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCommentid() {
        return commentid;
    }

    public void setCommentid(String commentid) {
        this.commentid = commentid;
    }

    public String getSiteid() {
        return siteid;
    }

    public void setSiteid(String siteid) {
        this.siteid = siteid;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getCreat_at() {
        return creat_at;
    }

    public void setCreat_at(String creat_at) {
        this.creat_at = creat_at;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getDirection() {
        return direction;
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }

    public String getSupport() {
        return support;
    }

    public void setSupport(String support) {
        this.support = support;
    }

    public String getReply() {
        return reply;
    }

    public void setReply(String reply) {
        this.reply = reply;
    }
}
