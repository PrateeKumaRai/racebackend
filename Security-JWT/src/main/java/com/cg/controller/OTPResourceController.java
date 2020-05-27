package com.cg.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Description;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.cg.config.JwtTokenUtil;
import com.cg.model.JwtResponse;
import com.cg.model.UserEntity;
import com.cg.model.UserModel;
import com.cg.service.JwtUserDetailsService;
import com.cg.service.OtpService;

/**
 * Entry point to REST end points
 * 
 * @author praterai
 *
 */
@Description(value = "Resource for generating and validating OTP requests.")
@RestController
@CrossOrigin("*")
public class OTPResourceController {

	private OtpService otpService;

	@Autowired
	private JwtUserDetailsService jwtUserDetailsService;

	@Autowired
	private JwtTokenUtil jwtTokenUtil;

	public OTPResourceController(OtpService otpService) {
		this.otpService = otpService;
	}

	@PostMapping(value = "/generateOtp", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> generateOTP(@RequestBody UserModel userModel) {

		JwtResponse jwtResponse = new JwtResponse();
		UserEntity userEntity = jwtUserDetailsService.userDetails(userModel.getEmailId());
		if (userEntity == null) {
			jwtResponse.setStatusCode(405);
			return new ResponseEntity<Object>(jwtResponse, HttpStatus.OK);
		}
		String username = userEntity.getEmailId();
		boolean isEnabled = userEntity.getIsEnabled();
		if (username == null || isEnabled == false) {
			jwtResponse.setStatusCode(403);
			return new ResponseEntity<Object>(jwtResponse, HttpStatus.OK);
		}
		Boolean isGenerated = otpService.generateOtp(username);
		if (!isGenerated) {
			jwtResponse.setStatusCode(404);
			return new ResponseEntity<Object>(jwtResponse, HttpStatus.OK);
		}

		jwtResponse.setStatusCode(200);
		return new ResponseEntity<Object>(jwtResponse, HttpStatus.OK);
	}

	@PostMapping(value = "/validateOtp", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> validateOTP(@RequestBody UserModel userModel) {

		String response = null;
		JwtResponse jwtResponse = new JwtResponse();
		if (userModel == null) {
			return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		}
		Boolean isValid = otpService.validateOTP(userModel.getEmailId(), userModel.getOtp());
		if (!isValid) {
			jwtResponse.setStatusCode(400);
			return new ResponseEntity<Object>(jwtResponse, HttpStatus.OK);
		}
		final UserDetails userDetails = jwtUserDetailsService.loadUserByUsername(userModel.getEmailId());
		response = jwtTokenUtil.generateToken(userDetails);
		jwtResponse.setToken(response);
		return new ResponseEntity<Object>(jwtResponse, HttpStatus.OK);
	}

}