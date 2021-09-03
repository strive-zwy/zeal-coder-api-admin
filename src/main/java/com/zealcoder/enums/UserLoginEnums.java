package com.zealcoder.enums;

/**
 * @author : zwy
 * @version : 1.0
 * @createTime : 2021/8/19 23:18
 * @Description : 用户登录类型枚举
 */
public enum UserLoginEnums {

    LOGIN_GITHUB(0,"github用户登录"),
    LOGIN_USER(1,"普通用户登录");

    private Integer type;
    private String des;

    public int getType() {
        return type;
    }

    public String getDes() {
        return des;
    }

    UserLoginEnums(int type, String des) {
        this.type = type;
        this.des = des;
    }

    public static String desOfType(int type){
        for (UserLoginEnums u : UserLoginEnums.values()) {
            if (u.getType() == type){
                return u.getDes();
            }
        }
        return "";
    }
}
