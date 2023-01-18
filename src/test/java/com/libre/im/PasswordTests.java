package com.libre.im;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * @author: Libre
 * @Date: 2022/5/30 11:08 PM
 */
@SpringBootTest
public class PasswordTests {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Test
    void test() {
        String encode = passwordEncoder.encode("123456");
        System.out.println(encode);
    }

    @Test
    void passwordBase64() {
//        String s = RsaUtil.encryptToBase64("MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQC9iBNqpPdFxYlCnjpqYvwFIq8GIBGsNsX2zCVY5AUBgY9+QD5Q6+kwVOWvaQX3/WPBlxDtyKP080Th+jZvMGQmJtRtQkTVIXdQps5QSaujuXoOAQ0cRW1zpXNHxAts" +
//                "YvZTroZrFYZvn1bJl85Ur91DJVfOggPB+g/9DtgcgMVJSQIDAQAB", "123456");
//        System.out.println(s);
    }
}
