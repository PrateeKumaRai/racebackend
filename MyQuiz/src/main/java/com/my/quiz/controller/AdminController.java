package com.my.quiz.controller;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.my.quiz.constant.MessageConstant;
import com.my.quiz.domain.Admin;
import com.my.quiz.domain.AdminUserSync;
import com.my.quiz.domain.GlobalConfiguration;
import com.my.quiz.domain.QuizResultThroughJoins;
import com.my.quiz.irepository.IAdminUserSyncRepository;
import com.my.quiz.irepository.IGlobalConfigurationRepository;
import com.my.quiz.irepository.IQuizResultRepository;
import com.my.quiz.response.Response;
import com.my.quiz.utility.Utility;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/admin")
public class AdminController {
	
	@Autowired
	Utility utilty;
	
	@Autowired
	IAdminUserSyncRepository isyncRepo;
	
	@Autowired
	IQuizResultRepository iquizresultRepo;
	
	@Autowired
	IGlobalConfigurationRepository iglobalconfigRepo;
	
	@RequestMapping(value = "/login", consumes = "application/json", method = RequestMethod.POST)
	ResponseEntity<Response> adminLogin(@Valid  @RequestBody Admin admin, Errors errors) {
		HttpStatus httpstatus = HttpStatus.OK;
		Response resp = new Response();
		resp.setHttpstatus(httpstatus);
		Admin adminLogin = null;
        if (errors.hasErrors()) {
			return utilty.validateRequest(errors, httpstatus, resp);
        }
		try {
			if(null!=admin && admin.getUsername().equalsIgnoreCase("admin") && admin.getPassword().equalsIgnoreCase("admin")) {
				resp.setDescription(MessageConstant.SUCCESS_LOGIN);
				resp.setHttpstatus(HttpStatus.OK);
				adminLogin = admin;
				resp.setObj(adminLogin);
			}else {
				resp.setDescription(MessageConstant.FAILED_LOGIN);
				resp.setHttpstatus(HttpStatus.UNAUTHORIZED);
				resp.setObj(null);
			}
		} catch (Exception e) {
			resp.setDescription(MessageConstant.INTERNAL_SERVER_ERROR);
			resp.setHttpstatus(HttpStatus.INTERNAL_SERVER_ERROR);
			resp.setObj(null);
		}
		return new ResponseEntity<Response>(resp, httpstatus);
	}
	
	@RequestMapping(value = "/syncdatauser", consumes = "application/json", method = RequestMethod.POST)
	ResponseEntity<Response> syncAdminUser(@Valid @RequestBody AdminUserSync sync, Errors errors) {
		HttpStatus httpstatus = HttpStatus.OK;
		Response resp = new Response();
		resp.setHttpstatus(httpstatus);
		AdminUserSync syncSaved = null;
		Optional<AdminUserSync> adminuserUpdt = null;
        if (errors.hasErrors()) {
			return utilty.validateRequest(errors, httpstatus, resp);
        }
		try {
			adminuserUpdt = isyncRepo.findByQuizId(sync.getQuizId());
			if(adminuserUpdt.isPresent()) {
				sync.setSyncId(adminuserUpdt.get().getSyncId());
				syncSaved=isyncRepo.save(sync);
				if(null!=syncSaved) {
					resp.setDescription(MessageConstant.UPDATE_SUCCESS_ADMINUSERSYNC);
					resp.setHttpstatus(HttpStatus.OK);
					resp.setObj(syncSaved);
				}				
			}else {
				syncSaved=isyncRepo.save(sync);
				if(null!=syncSaved) {
					resp.setDescription(MessageConstant.SAVE_SUCCESS_ADMINUSERSYNC);
					resp.setHttpstatus(HttpStatus.CREATED);
					resp.setObj(syncSaved);
				}
			}
		} catch (Exception e) {
			resp.setDescription(MessageConstant.INTERNAL_SERVER_ERROR);
			resp.setHttpstatus(HttpStatus.INTERNAL_SERVER_ERROR);
			resp.setObj(null);
		}
		return new ResponseEntity<Response>(resp, httpstatus);
	}
	
	/**
	 * /api/admin/getsync?quizId=1
	 * @param quizId
	 * @return
	 */
	@RequestMapping(value = "/getsync", consumes = "application/json", method = RequestMethod.GET)
	ResponseEntity<Response> getAdminUserSyncByQuizId(@RequestParam("quizId") Long quizId) {
		HttpStatus httpstatus = HttpStatus.OK;
		Response resp = new Response();
		Optional<AdminUserSync> syncGet = null;
		try {
			syncGet=isyncRepo.findByQuizId(quizId);
			if(syncGet.isPresent()) {
				resp.setDescription(MessageConstant.GET_ADMINUSERSYNC_SUCCESS);
				resp.setHttpstatus(HttpStatus.OK);
				resp.setObj(syncGet);
			}
			else {
				resp.setDescription(MessageConstant.NOT_FOUND_ADMINUSERSYNC);
				resp.setHttpstatus(HttpStatus.NOT_FOUND);
				resp.setObj(null);
			}
		} catch (Exception e) {
			resp.setDescription(MessageConstant.INTERNAL_SERVER_ERROR);
			resp.setHttpstatus(HttpStatus.INTERNAL_SERVER_ERROR);
			resp.setObj(null);
		}
		return new ResponseEntity<Response>(resp, httpstatus);
	}
	
	/**
	 * @param quizId
	 * @return
	 */
	@RequestMapping(value = "/deletesync/{id}", consumes = "application/json", method = RequestMethod.DELETE)
	ResponseEntity<Response> deleteQuiz(@PathVariable(value = "id") Long quizId) {
		HttpStatus httpstatus = HttpStatus.OK;
		Response resp = new Response();
		Optional<AdminUserSync>  syncDelete = null;
		try {
			syncDelete=isyncRepo.findByQuizId(quizId);
			if(syncDelete.isPresent()) {
				isyncRepo.deleteByQuizId(quizId);
				resp.setDescription(MessageConstant.DELETE_ADMINUSERSYNC_SUCCESS);
				resp.setHttpstatus(HttpStatus.NO_CONTENT);
				resp.setObj(null);
			}
			else {
				resp.setDescription(MessageConstant.NOT_FOUND_ADMINUSERSYNC);
				resp.setHttpstatus(HttpStatus.NOT_FOUND);
				resp.setObj(syncDelete);
			}
		} catch (Exception e) {
			resp.setDescription(MessageConstant.INTERNAL_SERVER_ERROR);
			resp.setHttpstatus(HttpStatus.INTERNAL_SERVER_ERROR);
			resp.setObj(null);
		}
		return new ResponseEntity<Response>(resp, httpstatus);
	}
	
	/**
	 * /api/admin/getquizresult?quizId=1
	 * @param quizId
	 * @return
	 */
	@RequestMapping(value = "/getquizresult", consumes = "application/json", method = RequestMethod.GET)
	ResponseEntity<Response> getQuizResult(@RequestParam("quizId") Long quizId) {
		HttpStatus httpstatus = HttpStatus.OK;
		Response resp = new Response();
		List<QuizResultThroughJoins> resultGet = null;
		Map<String,Long> map= new LinkedHashMap<>();
        Map<String, Long> result2 = new LinkedHashMap<>();
		try {
			resultGet=iquizresultRepo.findByQuizId(quizId);
			if(!resultGet.isEmpty()) {
				resp.setDescription(MessageConstant.GET_QUIZ_RESULT);
				resp.setHttpstatus(HttpStatus.OK);

				resultGet.stream().forEach(data -> {
					Long score = map.containsKey(data.getNick_name()) ? map.get(data.getNick_name()) : Long.valueOf(0);
					score = score + data.getScore();
					map.put(data.getNick_name(), score);
				});
				  
				  map.entrySet().stream() .sorted(Map.Entry.<String,
				  Long>comparingByValue().reversed()) .forEachOrdered(x ->
				  result2.put(x.getKey(), x.getValue()));
				  resp.setObj(result2);
			}
			else {
				resp.setDescription(MessageConstant.NOT_FOUND_QUIZ_RESULT);
				resp.setHttpstatus(HttpStatus.NOT_FOUND);
				resp.setObj(null);
			}
		} catch (Exception e) {
			resp.setDescription(MessageConstant.INTERNAL_SERVER_ERROR);
			resp.setHttpstatus(HttpStatus.INTERNAL_SERVER_ERROR);
			resp.setObj(null);
		}
		return new ResponseEntity<Response>(resp, httpstatus);
	}
	
	
	/**
	 * /api/admin/getconfig?configName=QUIZ_TIMER
	 * @param quizId
	 * @return
	 */
	@RequestMapping(value = "/getconfig", consumes = "application/json", method = RequestMethod.GET)
	ResponseEntity<Response> getConfigurationByName(@RequestParam("configName") String configName) {
		HttpStatus httpstatus = HttpStatus.OK;
		Response resp = new Response();
		GlobalConfiguration getConfig = null;
		try {
			getConfig=iglobalconfigRepo.findByConfigName(configName);
			if(null != getConfig) {
				resp.setDescription(MessageConstant.GET_GLOBALCONFIG_SUCCESS);
				resp.setHttpstatus(HttpStatus.OK);
				resp.setObj(getConfig);
			}
			else {
				resp.setDescription(MessageConstant.NOT_FOUND_GLOBALCONFIG);
				resp.setHttpstatus(HttpStatus.NOT_FOUND);
				resp.setObj(null);
			}
		} catch (Exception e) {
			resp.setDescription(MessageConstant.INTERNAL_SERVER_ERROR);
			resp.setHttpstatus(HttpStatus.INTERNAL_SERVER_ERROR);
			resp.setObj(null);
		}
		return new ResponseEntity<Response>(resp, httpstatus);
	}
	
	
	/**
	 * /api/admin/updateconfig
	 * @param config
	 * @param errors
	 * @return
	 */
	@RequestMapping(value = "/updateconfig", consumes = "application/json", method = RequestMethod.PUT)
	ResponseEntity<Response> updateConfig(@Valid @RequestBody GlobalConfiguration config, Errors errors) {
		HttpStatus httpstatus = HttpStatus.OK;
		Response resp = new Response();
		resp.setHttpstatus(httpstatus);
		GlobalConfiguration configToUpdate = null;
		GlobalConfiguration configReturnVal =null;
        if (errors.hasErrors()) {
			return utilty.validateRequest(errors, httpstatus, resp);
        }
		try {
			configToUpdate=iglobalconfigRepo.findByConfigName(config.getConfigName());
			if(null != configToUpdate) {
				configToUpdate.setConfigId(configToUpdate.getConfigId());
				configToUpdate.setConfigName(config.getConfigName());
				configToUpdate.setConfigValue(config.getConfigValue());
				configReturnVal=iglobalconfigRepo.save(configToUpdate);
				if(null!=configReturnVal) {
					resp.setDescription(MessageConstant.UPDATE_SUCCESS_GLOBALCONFIG);
					resp.setHttpstatus(HttpStatus.OK);
					resp.setObj(configReturnVal);
				}
			}
			else {
				resp.setDescription(MessageConstant.NOT_FOUND_GLOBALCONFIG);
				resp.setHttpstatus(HttpStatus.NOT_FOUND);
				resp.setObj(configReturnVal);
			}
		} catch (Exception e) {
			resp.setDescription(MessageConstant.INTERNAL_SERVER_ERROR);
			resp.setHttpstatus(HttpStatus.INTERNAL_SERVER_ERROR);
			resp.setObj(null);
		}
		return new ResponseEntity<Response>(resp, httpstatus);
	}

}
