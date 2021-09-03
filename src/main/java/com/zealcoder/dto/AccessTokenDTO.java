package com.zealcoder.dto;

import lombok.Data;

/**
 * @author : zwy
 * @version : 1.0
 * @createTime : 2021/8/29 21:02
 * @Description : TODO
 */
@Data
public class AccessTokenDTO {

    private String code;
    private String state;
    private String redirect_uri;
    private String clientI_id;
    private String client_secret;
}
