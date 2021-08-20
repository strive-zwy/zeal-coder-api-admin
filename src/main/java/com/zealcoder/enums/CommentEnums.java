package com.zealcoder.enums;

/**
 * @author : zwy
 * @version : 1.0
 * @createTime : 2021/8/19 23:25
 * @Description : 评论类型枚举
 */
public enum CommentEnums {

    COMMENT_TO_BLOG(0,"评论给博客"),
    COMMENT_TO_COMMENT(1,"评论给评论");

    private int type;
    private String des;

    public int getType() {
        return type;
    }

    public String getDes() {
        return des;
    }

    CommentEnums(int type, String des) {
        this.type = type;
        this.des = des;
    }

    public static String desOfType(int type){
        for (CommentEnums u : CommentEnums.values()) {
            if (u.getType() == type){
                return u.getDes();
            }
        }
        return "";
    }
}
