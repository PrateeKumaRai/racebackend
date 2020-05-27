package com.cg.config;
import java.io.IOException;
import java.io.InputStream;

import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamSource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;


/**
 * @author praterai
 *
 */
@Component
public class EmailConfig {

	@Autowired
	private JavaMailSender mailSender;

	/**
	 * @apiNote Util Method to send Email to end user 
	 *
	 */
	public boolean sendEmail(String to,String sub,String txt,final MultipartFile file){
		boolean falg=false;
		try {
			MimeMessage message=mailSender.createMimeMessage();
			MimeMessageHelper helper=new MimeMessageHelper(message,true);
			helper.setSubject(sub);
			helper.setText(txt);
			helper.setFrom("application.race@gmail.com");
			helper.setTo(to);
			
			
			if(file!=null){
				helper.addAttachment(file.getOriginalFilename(),new InputStreamSource() {
					
					@Override
					public InputStream getInputStream() throws IOException {
						return file.getInputStream();
					}
				});
			}
			System.out.println(txt);
			mailSender.send(message);
			falg=true;
		} catch (Exception e) {
			falg=false;
		}	
		return falg;
	}
}