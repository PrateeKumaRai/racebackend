package com.cg.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cg.config.EmailConfig;
import com.cg.model.UserEntity;
import com.cg.repo.UserRepo;

/**
 * @author praterai
 *
 */
@Service
public class OtpService {

	private OtpGenerator otpGenerator;
	private EmailConfig emailService;
	private JwtUserDetailsService userService;

	@Autowired
	private UserRepo userRepository;

	public OtpService(OtpGenerator otpGenerator, EmailConfig emailService, JwtUserDetailsService userService) {
		this.otpGenerator = otpGenerator;
		this.emailService = emailService;
		this.userService = userService;
	}

	public Boolean generateOtp(String key) {
		Integer otpValue = otpGenerator.generateOTP(key);
		if (otpValue == -1) {
			System.out.println("OTP generator is not working...");
			return false;
		}
		System.out.println("Generated OTP: {}" + otpValue);
		UserEntity userEntity = userService.userDetails(key);
		if (userEntity != null) {
			String message = ("OTP Password: " + otpValue);
			userEntity.setOtp(otpValue);
			userRepository.save(userEntity);
			String userEmail = userEntity.getEmailId();
			emailService.sendEmail(userEmail, "Race 2.0 OTP Service", message, null);
			return true;
		}
		return false;

	}

	public Boolean validateOTP(String key, Integer otpNumber) {
		Integer cacheOTP = otpGenerator.getOPTByKey(key);
		if (cacheOTP.equals(otpNumber)) {
			otpGenerator.clearOTPFromCache(key);
			return true;
		}
		return false;
	}
}