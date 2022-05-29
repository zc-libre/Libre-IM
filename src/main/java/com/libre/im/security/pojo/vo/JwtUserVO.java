package com.libre.im.security.pojo.vo;
import com.libre.im.security.jwt.JwtUser;
import lombok.Data;

/**
 * @author Libre
 * @date 2021/7/12 18:00
 */
@Data
public class JwtUserVO {

    private JwtUser userInfo;

    private String publicKey;

    private String token;
}
