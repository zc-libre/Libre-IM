package com.libre.im.tookit.moudle.email.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.libre.im.tookit.moudle.email.pojo.MailConfig;

/**
 * @author: Libre
 * @Date: 2023/1/12 1:31 AM
 */
public interface EmailService extends IService<MailConfig> {

	MailConfig findByUsername(String username);

	void sendEmail(String address, String content);

}
