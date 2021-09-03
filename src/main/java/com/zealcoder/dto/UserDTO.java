package com.zealcoder.dto;

import lombok.Data;

/**
 * @author : zwy
 * @version : 1.0
 * @createTime : 2021/8/29 15:55
 * @Description : TODO
 */
@Data
public class UserDTO {

    private Long id;
    private String account;
    private String avatar;
    private String jwtToken;


}
