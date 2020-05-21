package com.cg.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.cg.exception.DBException;
import com.cg.model.AllUser;
import com.cg.model.CubicalDetails;
import com.cg.model.UserEntity;
import com.cg.model.UserModel;
import com.cg.repo.CubicalRepo;
import com.cg.repo.UserRepo;

/**
 * @author praterai
 *
 */
@Service
public class JwtUserDetailsService implements UserDetailsService {

	@Autowired
	private UserRepo userRepo;

	@Autowired
	private PasswordEncoder bcryptEncoder;
	
	@Autowired
	private CubicalRepo cubicalRepo;
	
	
	@Override
	public UserDetails loadUserByUsername(String emailId) throws UsernameNotFoundException {
		UserEntity user = userRepo.findByEmailIdIgnoreCase(emailId);
		if (user == null) {
			throw new UsernameNotFoundException("User not found with username: " + emailId);
		}
		return new org.springframework.security.core.userdetails.User(user.getEmailId(), user.getPassword(),
				new ArrayList<>());
	}

	public UserEntity save(UserModel user) throws Exception {
		UserEntity newUser = new UserEntity();
		newUser.setUsername(user.getUsername());
		newUser.setPassword(bcryptEncoder.encode(user.getPassword()));
		newUser.setEmailId(user.getEmailId());
		newUser.setEmpName(user.getEmpName());
		newUser.setId(user.getId());
		newUser.setEmpId(user.getEmpId());

		try {
			
			UserEntity userEntity = userRepo.save(newUser);
			return userEntity;

		} catch (Exception e) {
			throw new DBException("405", "User Alreday Present");
		}
	}

	public boolean updateProfile(UserModel user) throws Exception {
		boolean status = false;
		try {
			if(user.getUsername()!="" & user.getEmpId()!=null & user.getMobile()!=null & user.getHouseNo()!="" & user.getArea()!="" & user.getCity()!="" & user.getCountry()!="" & user.getZip()!=null & user.getEmailId() !="") {
				userRepo.updateUser(user.getUsername(), user.getEmpId(), user.getMobile(), user.getHouseNo(),user.getArea(),user.getCity(),user.getCountry(),user.getZip(), user.getEmailId());
				status = true;
			}
			else {
				status = false;
			}
		} catch (Exception e) {
			status = false;
			return status;
		}
		return status;
	}
	
	public boolean updateSeatDetails(UserModel user) throws Exception {
		boolean status=false;
		
		CubicalDetails cd=	new CubicalDetails();
		cd.setCubicalNumber(user.getCubical());
		cd.setCubicalStatus(false);
		cd.setCubicalNumberTemp(true);
		cd.setEmailId(user.getEmailId());
		cd.setCubicalLocation(user.getCubicalLocation());
		cd.setCity(user.getCity());
		cd.setFloor(user.getFloor());
		cd.setCountry(user.getCountry());
		cd.setFloor(user.getFloor());
		cd.setZip(user.getZip());
		try {
			String email =user.getEmailId();
			Optional<CubicalDetails> optionalUserEntity=	cubicalRepo.getbyEmailId( email);
			if (optionalUserEntity.equals(null) || optionalUserEntity == null || !(optionalUserEntity.isPresent())) {
				cubicalRepo.save(cd);
				status=true;
			} else {
				cubicalRepo.updateSeatFromProfile(user.getCubical(),user.getCubicalLocation(),user.getCity(),user.getCountry(),user.getZip(),user.getFloor(),cd.getCubicalNumberTemp(), email);
				status=true;
			}
		} catch (Exception e) {
			status=false;
			return status;
		}
		return status;	
	}
	
	
	public UserEntity userDetails(String emailId) {
		UserEntity userEntity = null;
		Optional<UserEntity> optionalUserEntity = userRepo.getbyUserByMail(emailId);
		if (optionalUserEntity.equals(null) || optionalUserEntity == null || !(optionalUserEntity.isPresent())) {
			return userEntity;
		} else {
			return optionalUserEntity.get();
		}
	}

	public String findEmailByUsername(String username) {
		Optional<UserEntity> user = userRepo.getbyUserByMail(username);
		if (user.isPresent()) {
			return user.get().getEmailId();
		}
		return null;
	}

	public void deleteById(Long id) {
		userRepo.deleteById(id);

	}

	public List<AllUser> getAllUser() {
		List<AllUser> userModelList= new ArrayList<AllUser>();
		List<UserEntity> userList=	userRepo.findAll();
		for(UserEntity ue:userList) {
			AllUser userModel= new AllUser();
			userModel.setEmailId(ue.getEmailId());
			userModel.setEmpId(ue.getEmpId());
			userModel.setEmpName(ue.getEmpName());
			userModel.setMobile(ue.getMobile());
			userModel.setUsername(ue.getUsername());
			userModelList.add(userModel);
		}
		return userModelList;
	}

}
