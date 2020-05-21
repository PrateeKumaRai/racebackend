package com.cg.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.cg.model.AllUser;
import com.cg.model.JwtResponse;
import com.cg.model.UserEntity;
import com.cg.model.UserModel;
import com.cg.service.JwtUserDetailsService;
import com.cg.util.SecurityConstant;

/**
 * Entry point to REST end points
 * 
 * @author praterai
 *
 */
@RestController
@CrossOrigin("*")
public class UserController {
	
	
	
	@Autowired
	private JwtUserDetailsService userDetailsService;

	
	
	@RequestMapping(value = "/updateProfile", method = RequestMethod.POST)
	public ResponseEntity<?> updateProfile(@RequestBody UserModel user) {
		JwtResponse jwtResponse = new JwtResponse();
		if (user.getEmailId() != "" ) {
			try {
		boolean status=	 userDetailsService.updateProfile(user);
			if(status==true) {
			jwtResponse.setStatusCode(200);
			return new ResponseEntity<Object>(jwtResponse, HttpStatus.OK);
			}else {
				jwtResponse.setStatusCode(402);
				return new ResponseEntity<Object>(jwtResponse, HttpStatus.OK);
			}
			} catch (Exception e) {
				jwtResponse.setStatusCode(402);
				return new ResponseEntity<Object>(jwtResponse, HttpStatus.OK);
			}
		} else {
			jwtResponse.setStatusCode(403);
			return new ResponseEntity<Object>(jwtResponse, HttpStatus.OK);
		}
	}
	
	@RequestMapping(value = "/updateSeat", method = RequestMethod.POST)
	public ResponseEntity<?> updateSeat(@RequestBody UserModel user) {
		JwtResponse jwtResponse = new JwtResponse();
		if (user.getEmailId() != "" ) {
			try {
		boolean status=	 userDetailsService.updateSeatDetails(user);
		if(status==true) {
			jwtResponse.setStatusCode(200);
			return new ResponseEntity<Object>(jwtResponse, HttpStatus.OK);
		}
		else {
			jwtResponse.setStatusCode(402);
			return new ResponseEntity<Object>(jwtResponse, HttpStatus.OK);
		}

			} catch (Exception e) {
				jwtResponse.setStatusCode(402);
				return new ResponseEntity<Object>(jwtResponse, HttpStatus.OK);
			}
		} else {
			jwtResponse.setStatusCode(403);
			return new ResponseEntity<Object>(jwtResponse, HttpStatus.OK);
		}
	}

	
	
	@RequestMapping(value = "/loadAllUser", method = RequestMethod.GET)
	public ResponseEntity<Object> getAllUser() throws Exception {

		List<AllUser> userEntity = userDetailsService.getAllUser();
		return new ResponseEntity<Object>(userEntity, HttpStatus.OK);
	}
	
	
	/**
	 * 
	 * @Purpose : Get user details by user id
	 * 
	 */

	@RequestMapping(value = "/userDetailsbyMail", method = RequestMethod.POST)
	public ResponseEntity<?> getUserByUserId(@RequestBody UserModel user) throws Exception {

		String emailId=user.getEmailId();
		UserEntity userEntity = userDetailsService.userDetails(emailId);
		if (userEntity == null) {
			return new ResponseEntity<Object>(SecurityConstant.USER_NOT_FOUND_EXCEPTION, HttpStatus.valueOf(405));
		}
		return new ResponseEntity<Object>(userEntity, HttpStatus.OK);
	}
	
	
}
