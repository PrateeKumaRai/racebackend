package com.cg.test.controller;

import java.util.Arrays;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;

import com.cg.controller.UserController;
import com.cg.model.AllUser;
import com.cg.model.JwtResponse;
import com.cg.model.UserEntity;
import com.cg.model.UserModel;
import com.cg.service.JwtUserDetailsService;

@RunWith(MockitoJUnitRunner.class)
@SpringBootTest
public class UserControllerTest {

	@Mock
	JwtUserDetailsService jwtUserDetailsService;
	
	@InjectMocks
	UserController userController;
	
	@Test
	public void loadAllUserPostiveTest() throws Exception {
		Mockito.when(jwtUserDetailsService.getAllUser()).thenReturn(getAllUser());
		ResponseEntity<Object> response = userController.getAllUser();
		Assert.assertNotNull(response);
		Assert.assertEquals(200, response.getStatusCodeValue());
	}
	
	
	@Test
	public void getUserByUserIdPostiveTest() throws Exception {
		UserModel userModel = new UserModel();
		userModel.setEmailId("race@gmail.com");
		Mockito.when(jwtUserDetailsService.userDetails("race@gmail.com")).thenReturn(getUserEntity());
		ResponseEntity<?> response = userController.getUserByUserId(userModel);
		Assert.assertNotNull(response);
		Assert.assertEquals(200, response.getStatusCodeValue());
	}
	
	@Test
	public void getUserByUserIdWithNullEmailTest() throws Exception {
		ResponseEntity<?> response = userController.getUserByUserId(new UserModel());
		Assert.assertEquals(405, response.getStatusCodeValue());
		Assert.assertEquals("User Not Found", response.getBody());
	}
	
	@Test
	public void updateProfilePositiveTest() throws Exception {
		UserModel userModel = new UserModel();
		userModel.setEmailId("race@gmail.com");
		Mockito.when(jwtUserDetailsService.updateProfile(userModel)).thenReturn(true);
		ResponseEntity<?> response = userController.updateProfile(userModel);
		Assert.assertNotNull(response);
		Assert.assertEquals(200, response.getStatusCodeValue());
	}
	
	@Test
	public void updateProfileWithFalseTest() throws Exception {
		UserModel userModel = new UserModel();
		userModel.setEmailId("race@gmail.com");
		Mockito.when(jwtUserDetailsService.updateProfile(userModel)).thenReturn(false);
		ResponseEntity<?> response = userController.updateProfile(userModel);
		Assert.assertNotNull(response);
		JwtResponse jwtResponse = (JwtResponse) response.getBody();
		Assert.assertEquals(402, jwtResponse.getStatusCode());		
	}
	
	@Test
	public void updateProfileWithEmptyEmailTest() throws Exception {
		UserModel userModel = new UserModel();
		userModel.setEmailId("");
		ResponseEntity<?> response = userController.updateProfile(userModel);
		Assert.assertNotNull(response);
		JwtResponse jwtResponse = (JwtResponse) response.getBody();
		Assert.assertEquals(403, jwtResponse.getStatusCode());		
	}
	
	/*
	 * @Test (expected = Exception.class)
	 * public void updateProfileWithException() throws Exception{ 
	 * UserModel userModel = new UserModel(); 
	 * userModel.setEmailId("race@gmail.com"); 
	 * try {
	 * Mockito.doThrow(new RuntimeException()).when(jwtUserDetailsService).updateProfile(userModel);
	 * userController.updateProfile(userModel); }
	 * catch (Exception e) {} }
	 */
	
	@Test
	public void updateSeatPositiveTest() throws Exception {
		UserModel userModel = new UserModel();
		userModel.setEmailId("race@gmail.com");
		Mockito.when(jwtUserDetailsService.updateSeatDetails(userModel)).thenReturn(true);
		ResponseEntity<?> response = userController.updateSeat(userModel);
		Assert.assertNotNull(response);
		Assert.assertEquals(200, response.getStatusCodeValue());
	}
	
	@Test
	public void updateSeatWithFalseTest() throws Exception {
		UserModel userModel = new UserModel();
		userModel.setEmailId("race@gmail.com");
		Mockito.when(jwtUserDetailsService.updateSeatDetails(userModel)).thenReturn(false);
		ResponseEntity<?> response = userController.updateSeat(userModel);
		Assert.assertNotNull(response);
		JwtResponse jwtResponse = (JwtResponse) response.getBody();
		Assert.assertEquals(402, jwtResponse.getStatusCode());		
	}

	@Test
	public void updateSeatWithEmptyEmailTest() throws Exception {
		UserModel userModel = new UserModel();
		userModel.setEmailId("");
		ResponseEntity<?> response = userController.updateSeat(userModel);
		Assert.assertNotNull(response);
		JwtResponse jwtResponse = (JwtResponse) response.getBody();
		Assert.assertEquals(403, jwtResponse.getStatusCode());		
	}
	
	/*
	 * @Test public void updateSeatWithException() throws Exception{ UserModel
	 * userModel = new UserModel(); userModel.setEmailId("race@gmail.com"); try {
	 * Mockito.doThrow(new
	 * RuntimeException()).when(jwtUserDetailsService).updateSeatDetails(userModel);
	 * userController.updateSeat(userModel); } catch (Exception e) {} }
	 */
	 
	

	/* Mock Data For Tests */
	
	private List<AllUser> getAllUser(){
		AllUser allUser = new AllUser();
		allUser.setUsername("admin");
		allUser.setEmailId("race@gmail.com");
		allUser.setEmpName("Admin");
		allUser.setEmpId(1234L);
		allUser.setMobile(12345678L);
	
		return Arrays.asList(allUser);
	}
	
	private UserEntity getUserEntity() {
		UserEntity userEntity = new UserEntity();
		userEntity.setId(100);
		userEntity.setUsername("admin");
		userEntity.setPassword("admin");
		userEntity.setEmpName("Admin");
		userEntity.setEmpId(1234L);
		userEntity.setEmailId("race@gmail.com");
		userEntity.setArea("Ecospace");
		userEntity.setCity("Bengaluru");
		userEntity.setCountry("India");
		
		return userEntity;
	}
	
}
