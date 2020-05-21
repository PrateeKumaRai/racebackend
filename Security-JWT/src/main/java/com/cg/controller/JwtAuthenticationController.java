package com.cg.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.cg.config.EmailConfig;
import com.cg.config.JwtTokenUtil;
import com.cg.model.ConfirmationToken;
import com.cg.model.JwtRequest;
import com.cg.model.JwtResponse;
import com.cg.model.UserEntity;
import com.cg.model.UserModel;
import com.cg.repo.ConfirmationTokenRepository;
import com.cg.repo.UserRepo;
import com.cg.service.JwtUserDetailsService;

/**
 * Entry point to REST end points
 * 
 * @author praterai
 *
 */
@RestController
@CrossOrigin("*")
public class JwtAuthenticationController {

	@Autowired
	private EmailConfig email;

	@Autowired
	private UserRepo userRepository;

	@Autowired
	private ConfirmationTokenRepository confirmationTokenRepository;

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private JwtTokenUtil jwtTokenUtil;

	@Autowired
	private JwtUserDetailsService userDetailsService;

	BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);

	/**
	 * @Purpose : Authenticate User based on User Name and Password
	 * 
	 */
	@RequestMapping(value = "/authenticate", method = RequestMethod.POST)
	public ResponseEntity<?> createAuthenticationToken(@RequestBody JwtRequest authenticationRequest) throws Exception {
		String response = null;
		JwtResponse jwtResponse = new JwtResponse();

		if (authenticationRequest.getPassword() != "" && authenticationRequest.getEmailId() != "") {
			UserEntity user = userRepository.findByEmailIdIgnoreCase(authenticationRequest.getEmailId());

			if (user != null) {
				Boolean sucess = authenticate(authenticationRequest.getEmailId(), authenticationRequest.getPassword());
				if (user.getIsEnabled() == true) {
					if (sucess == false) {
						jwtResponse.setStatusCode(403);
						return new ResponseEntity<Object>(jwtResponse, HttpStatus.OK);
					} else {
						final UserDetails userDetails = userDetailsService
								.loadUserByUsername(authenticationRequest.getEmailId());
						response = jwtTokenUtil.generateToken(userDetails);
						jwtResponse.setToken(response);
						jwtResponse.setStatusCode(200);
						return new ResponseEntity<Object>(jwtResponse, HttpStatus.OK);
					}
				} else {
					jwtResponse.setStatusCode(402);
					return new ResponseEntity<Object>(jwtResponse, HttpStatus.OK);
				}

			} else {
				jwtResponse.setStatusCode(404);
				return new ResponseEntity<Object>(jwtResponse, HttpStatus.OK);
			}
		} else {
			jwtResponse.setStatusCode(406);
			return new ResponseEntity<Object>(jwtResponse, HttpStatus.OK);
		}

	}

	/**
	 * @Purpose : Helper method for authenticate User
	 * 
	 */
	private boolean authenticate(String email, String password) throws Exception {
		boolean success = false;
		try {
			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email, password));
			success = true;
		} catch (Exception e) {
			success = false;
		}
		return success;
	}

	/**
	 * @Purpose : User registration Method
	 * 
	 */
	@RequestMapping(value = "/register", method = RequestMethod.POST)
	public ResponseEntity<?> saveUser(@RequestBody UserModel user) {
		JwtResponse jwtResponse = new JwtResponse();
		UserEntity userEntity;
		if (user.getEmailId() != "" && user.getEmpName() != "" && user.getPassword() != "") {
			try {

				userEntity = userDetailsService.save(user);
				ConfirmationToken confirmationToken = new ConfirmationToken(userEntity);
				confirmationToken.setEmailId(userEntity.getEmailId());
				confirmationTokenRepository.save(confirmationToken);
				String message = ("To Verify your account , please click here: "
						+ "http://localhost:8082/confirm-account?token=" + confirmationToken.getConfirmationToken());
				boolean sucess = email.sendEmail(user.getEmailId(), "Verify Account", message, null);
				if (sucess == false) {
					Long id = userEntity.getId();
					String email = userEntity.getEmailId();
					confirmationTokenRepository.deleteByEmailId(email);
					userDetailsService.deleteById(id);
					jwtResponse.setStatusCode(406);
					return new ResponseEntity<Object>(jwtResponse, HttpStatus.OK);
				}
				jwtResponse.setStatusCode(200);
				return new ResponseEntity<Object>(jwtResponse, HttpStatus.OK);

			} catch (Exception e) {
				jwtResponse.setStatusCode(402);
				return new ResponseEntity<Object>(jwtResponse, HttpStatus.OK);
			}
		} else {
			jwtResponse.setStatusCode(403);
			return new ResponseEntity<Object>(jwtResponse, HttpStatus.OK);

		}

	}

	/**
	 * @Purpose : Confirm registration
	 * 
	 */

	@RequestMapping(value = "/confirm-account", method = { RequestMethod.GET, RequestMethod.POST })
	public ModelAndView confirmUserAccount(ModelAndView modelAndView, @RequestParam("token") String confirmationToken) {
		ConfirmationToken token = confirmationTokenRepository.findByConfirmationToken(confirmationToken);

		if (token != null) {
			UserEntity user = userRepository.findByEmailIdIgnoreCase(token.getUser().getEmailId());
			user.setEnabled(true);
			userRepository.save(user);
			modelAndView.setViewName("sucess");
		} else {
			modelAndView.setViewName("fail");
		}
		return modelAndView;
	}

	/**
	 * @Purpose : Receive email of the user, create token and send it via email to
	 *          the user
	 * 
	 */

	@RequestMapping(value = "/forgot-password", method = RequestMethod.POST)
	public ResponseEntity<?> forgotUserPassword(@RequestBody UserModel user) {

		JwtResponse jwtResponse = new JwtResponse();
		UserEntity existingUser = userRepository.findByEmailIdIgnoreCase(user.getEmailId());
		if (existingUser != null) {

			ConfirmationToken confirmationToken = new ConfirmationToken(existingUser);
			confirmationToken.setEmailId(user.getEmailId());
			confirmationTokenRepository.save(confirmationToken);

			SimpleMailMessage mailMessage = new SimpleMailMessage();
			mailMessage.setTo(existingUser.getEmailId());
			mailMessage.setSubject("Complete Password Reset!");

			String message = ("To complete the password reset process, please click here: "
					+ "http://localhost:8082/confirm-reset?token=" + confirmationToken.getConfirmationToken());
			email.sendEmail(existingUser.getEmailId(), "Complete Password Reset!", message, null);
			jwtResponse.setStatusCode(200);
			return new ResponseEntity<Object>(jwtResponse, HttpStatus.OK);
		} else {
			jwtResponse.setStatusCode(403);
			return new ResponseEntity<Object>(jwtResponse, HttpStatus.OK);
		}
	}

	/**
	 * 
	 * @Purpose : End point to confirm the token
	 * 
	 */

	@RequestMapping(value = "/confirm-reset", method = { RequestMethod.GET, RequestMethod.POST })
	public ModelAndView validateResetToken(ModelAndView modelAndView, @RequestParam("token") String confirmationToken) {
		ConfirmationToken token = confirmationTokenRepository.findByConfirmationToken(confirmationToken);

		if (token != null) {
			UserEntity user = userRepository.findByEmailIdIgnoreCase(token.getUser().getEmailId());
			user.setEnabled(true);
			userRepository.save(user);
			modelAndView.addObject("user", user);
			modelAndView.addObject("emailId", user.getEmailId());
			modelAndView.setViewName("resetPassword");
		} else {
			modelAndView.addObject("message", "The link is invalid or broken!");
			modelAndView.setViewName("error");
		}
		return modelAndView;
	}

	/**
	 * 
	 * @Purpose : End point to update a user's password
	 * 
	 */

	@RequestMapping(value = "/reset-password", method = RequestMethod.POST)
	public ModelAndView resetUserPassword(ModelAndView modelAndView, UserEntity user) {
		if (user.getEmailId() != null) {

			UserEntity tokenUser = userRepository.findByEmailIdIgnoreCase(user.getEmailId());
			tokenUser.setPassword(encoder.encode(user.getPassword()));
			userRepository.save(tokenUser);
			modelAndView.addObject("message",
					"Password successfully reset. You can now log in with the new credentials.");
			modelAndView.setViewName("sucessResetPassword");
		} else {
			modelAndView.addObject("message", "The link is invalid or broken!");
			modelAndView.setViewName("error");
		}
		return modelAndView;
	}

	/*
	*//**
		 * 
		 * @Purpose : Get user details by user id
		 * 
		 *//*
			 * 
			 * @RequestMapping(value = "/userDetailsbyMail/{emailId}", method =
			 * RequestMethod.GET) public ResponseEntity<?>
			 * getUserByUserId(@PathVariable(value = "emailId") String emailId) throws
			 * Exception {
			 * 
			 * UserEntity userEntity = userDetailsService.userDetails(emailId); if
			 * (userEntity == null) { return new
			 * ResponseEntity<Object>(SecurityConstant.USER_NOT_FOUND_EXCEPTION,
			 * HttpStatus.valueOf(405)); } return new ResponseEntity<Object>(userEntity,
			 * HttpStatus.OK); }
			 * 
			 * 
			 * 
			 * @RequestMapping(value = "/updateProfile", method = RequestMethod.POST) public
			 * ResponseEntity<?> updateProfile(@RequestBody UserModel user) { JwtResponse
			 * jwtResponse = new JwtResponse(); if (user.getEmailId() != "" ) { try {
			 * boolean status= userDetailsService.update(user); if(status==true)
			 * jwtResponse.setStatusCode(200); return new
			 * ResponseEntity<Object>(jwtResponse, HttpStatus.OK);
			 * 
			 * } catch (Exception e) { jwtResponse.setStatusCode(402); return new
			 * ResponseEntity<Object>(jwtResponse, HttpStatus.OK); } } else {
			 * jwtResponse.setStatusCode(403); return new
			 * ResponseEntity<Object>(jwtResponse, HttpStatus.OK);
			 * 
			 * }
			 * 
			 * }
			 */
}