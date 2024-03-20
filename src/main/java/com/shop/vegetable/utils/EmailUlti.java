package com.shop.vegetable.utils;


import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EmailUlti {

	@Autowired
	private JavaMailSender mailSender;

	public void sendEmail(String diaChiDen, String tieuDe, String noiDung) {
		try {
			SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
			simpleMailMessage.setTo(diaChiDen);
			simpleMailMessage.setSubject(tieuDe);
			simpleMailMessage.setText(noiDung);

			mailSender.send(simpleMailMessage);
		} catch (MailException exception) {
			exception.printStackTrace();
		}
	}

}
