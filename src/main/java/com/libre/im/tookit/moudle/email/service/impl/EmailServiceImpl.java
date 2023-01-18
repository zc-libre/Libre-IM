package com.libre.im.tookit.moudle.email.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.common.base.Charsets;
import com.libre.boot.autoconfigure.SpringContext;
import com.libre.im.common.security.support.SecurityUtil;
import com.libre.im.tookit.moudle.email.mapper.EMailConfigMapper;
import com.libre.im.tookit.moudle.email.pojo.MailConfig;
import com.libre.im.tookit.moudle.email.service.EmailService;
import com.libre.toolkit.exception.LibreException;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.Objects;

/**
 * @author: Libre
 * @Date: 2023/1/12 1:31 AM
 */
@Service
@RequiredArgsConstructor
public class EmailServiceImpl extends ServiceImpl<EMailConfigMapper, MailConfig> implements EmailService {

	@Override
	public MailConfig findByUsername(String username) {
		return this.getOne(Wrappers.<MailConfig>lambdaQuery().eq(MailConfig::getGmtCreateName, username));
	}

	@Override
	public void sendEmail(String address, String content) {
		String userName = SecurityUtil.getUserName();
		EmailServiceImpl emailService = SpringContext.getCurrentProxy();
		MailConfig mailConfig = emailService.findByUsername(userName);
		if (Objects.isNull(mailConfig)) {
			throw new LibreException("mailConfig is not exist, username: {}", userName);
		}

		JavaMailSender javaMailSender = buildJavaMailSender(mailConfig);
		try {
			MimeMessage mimeMessage = javaMailSender.createMimeMessage();
			MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, false);
			helper.setFrom(mailConfig.getUsername());
			helper.setTo(address);
			helper.setText(content);
			javaMailSender.send(mimeMessage);
		}
		catch (MessagingException e) {
			throw new LibreException(e);
		}

	}

	private static JavaMailSender buildJavaMailSender(MailConfig mailConfig) {
		JavaMailSenderImpl javaMailSender = new JavaMailSenderImpl();
		javaMailSender.setHost(mailConfig.getHost());
		javaMailSender.setPort(mailConfig.getPort());
		javaMailSender.setUsername(mailConfig.getUsername());
		javaMailSender.setPassword(mailConfig.getPassword());
		javaMailSender.setProtocol(mailConfig.getProtocol());
		javaMailSender.setDefaultEncoding(Charsets.UTF_8.name());
		return javaMailSender;
	}

}
