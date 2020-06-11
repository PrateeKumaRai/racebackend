package com.cg.test.service;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.given;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.cg.exception.DBException;
import com.cg.model.AllUser;
import com.cg.model.CubicalDetails;
import com.cg.model.UserEntity;
import com.cg.model.UserModel;
import com.cg.repo.CubicalRepo;
import com.cg.repo.UserRepo;
import com.cg.service.JwtUserDetailsService;

@RunWith(MockitoJUnitRunner.class)
@SpringBootTest
public class JwtUserDetailsServiceTest {

	@Mock
	UserRepo userRepo;
	
	@Mock
	PasswordEncoder bcryptEncoder;
	
	@Mock
	CubicalRepo cubicalRepo;
	
	@InjectMocks
	JwtUserDetailsService JwtUserDetailsService;
	
	@Test
	public void loadUserByUsernameTestPositive() {
		Mockito.when(userRepo.findByEmailIdIgnoreCase(Mockito.anyString())).thenReturn(getUserEntity());
		UserDetails userDetails = JwtUserDetailsService.loadUserByUsername(Mockito.anyString());
		Assert.assertEquals("race@gmail.com", userDetails.getUsername());
		Assert.assertEquals("admin", userDetails.getPassword());
		
	}
	
	@Test
	public void loadUserByUsernameTestForException() {
		assertThrows(UsernameNotFoundException.class, () -> JwtUserDetailsService.loadUserByUsername("ikea@gmail.com"),
				"User not found with username: ikea@gmail.com");
	}
	
	@Test
	public void saveTestPositive() throws Exception {
		Mockito.when(bcryptEncoder.encode("admin")).thenReturn("admin");
		Mockito.when(userRepo.save(Mockito.any(UserEntity.class))).thenReturn(getUserEntity());
		UserEntity userEntity = JwtUserDetailsService.save(getUserModel());
		Assert.assertEquals("race@gmail.com", userEntity.getEmailId());
		Assert.assertEquals("Admin", userEntity.getEmpName());
		Assert.assertEquals(new Long(12345), userEntity.getEmpId());
	}
	
	@Test
	public void saveTestWithException() {
		given(userRepo.save(Mockito.any(UserEntity.class))).willAnswer(invocation -> { throw new DBException("405", "User Alreday Present");});
		assertThrows(DBException.class, () -> JwtUserDetailsService.save(getUserModel()),"User Alreday Present");
	}
	
	@Test
	public void updateProfileTestPositive() throws Exception {
		boolean status= JwtUserDetailsService.updateProfile(getUserModel());
		Assert.assertEquals(true, status);
	}
	
	@Test
	public void updateProfileTestWithEmptyUsername() throws Exception {
		UserModel userModel = getUserModel();
		userModel.setUsername("");
		boolean status= JwtUserDetailsService.updateProfile(userModel);
		Assert.assertEquals(false, status);
	}

	@Test
	public void updateSeatDetailsForNewSeatTestPositive() throws Exception {
		Mockito.when(cubicalRepo.getbyEmailId("race@gmail.com")).thenReturn(Optional.empty());
		boolean status = JwtUserDetailsService.updateSeatDetails(getUserModel());
		Assert.assertEquals(true, status);
	}

	@Test
	public void updateSeatDetailsForUpdateTestPositive() throws Exception {
		Mockito.when(cubicalRepo.getbyEmailId("race@gmail.com")).thenReturn(getCubicalDetails());
		boolean status = JwtUserDetailsService.updateSeatDetails(getUserModel());
		Assert.assertEquals(true, status);

	}
	
	@Test
	public void userDetailsTestPositive() {
		Mockito.when(userRepo.getbyUserByMail(Mockito.any(String.class))).thenReturn(Optional.of(getUserEntity()));
		UserEntity userEntity = JwtUserDetailsService.userDetails("race@gmail.com");
		Assert.assertEquals("race@gmail.com", userEntity.getEmailId());
		Assert.assertEquals("Admin", userEntity.getEmpName());
		Assert.assertEquals(new Long(12345), userEntity.getEmpId());		
	}

	@Test
	public void userDetailsTestWithEmptyUserEntity() {
		Mockito.when(userRepo.getbyUserByMail(Mockito.any(String.class))).thenReturn(Optional.empty());
		UserEntity userEntity = JwtUserDetailsService.userDetails("race@gmail.com");
		Assert.assertNull(userEntity);
	}
	
	@Test
	public void findEmailByUsernameTestPositive() {
		Mockito.when(userRepo.getbyUserByMail(Mockito.any(String.class))).thenReturn(Optional.of(getUserEntity()));
		String userByEmail = JwtUserDetailsService.findEmailByUsername("admin");
		Assert.assertEquals("race@gmail.com", userByEmail);
	}
	
	@Test
	public void findEmailByUsernameTestWithEmptryResylt() {
		Mockito.when(userRepo.getbyUserByMail(Mockito.any(String.class))).thenReturn(Optional.empty());
		String userByEmail = JwtUserDetailsService.findEmailByUsername("admin");
		Assert.assertNull(userByEmail);
	}
	
	@Test
	public void deleteByIdTestPositive() {
		Mockito.doNothing().when(userRepo).deleteById(Mockito.any(Long.class));
		JwtUserDetailsService.deleteById(123L);
		Mockito.verify(userRepo, Mockito.times(1)).deleteById(Mockito.any(Long.class));
	}
	
	@Test
	public void findallUserTestPositive() {
		Mockito.when(userRepo.findAll()).thenReturn(Arrays.asList(getUserEntity()));
		List<AllUser> allUser = JwtUserDetailsService.getAllUser();
		Assert.assertEquals(1, allUser.size());
	}

	/* Mock Data*/
	
	private UserEntity getUserEntity() {
		UserEntity userEntity = new UserEntity();
		userEntity.setId(100);
		userEntity.setUsername("admin");
		userEntity.setPassword("admin");
		userEntity.setEmpName("Admin");
		userEntity.setEmpId(12345L);
		userEntity.setEmailId("race@gmail.com");
		userEntity.setMobile(99999L);
		
		return userEntity;
	}
	
	private UserModel getUserModel() {
		UserModel userModel = new UserModel();
		userModel.setUsername("admin");
		userModel.setPassword("admin");
		userModel.setEmailId("race@gmail.com");
		userModel.setEmpName("Admin");
		userModel.setId(100);
		userModel.setEmpId(12345L);
		userModel.setMobile(99999L);
		userModel.setHouseNo("House-1");
		userModel.setArea("EcoSpace");
		userModel.setCity("Bengaluru");
		userModel.setCountry("India");
		userModel.setZip(560001L);
		userModel.setCubical("CG101");
		userModel.setCubicalLocation("Ecospace");
		
		return userModel;
	}
	
	private Optional<CubicalDetails> getCubicalDetails(){
		return Optional.of(new CubicalDetails());
	}
	
}
