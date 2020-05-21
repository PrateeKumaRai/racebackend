package com.my.quiz.controller;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.my.quiz.constant.MessageConstant;
import com.my.quiz.domain.Question;
import com.my.quiz.irepository.IQuestionRepository;
import com.my.quiz.response.Response;
import com.my.quiz.utility.Utility;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/question")
public class QuestionController {
	
	@Autowired
	IQuestionRepository iquestnRepo;
	
	@Autowired
	Utility utilty;
	
	/**
	 * @param questn
	 * @return
	 */
	@RequestMapping(value = "/save", consumes = "application/json", method = RequestMethod.POST)
	ResponseEntity<Response> saveQuestion(@Valid @RequestBody Question questn, Errors errors) {
		HttpStatus httpstatus = HttpStatus.OK;
		Response resp = new Response();
		resp.setHttpstatus(httpstatus);
		Question questnSaved = null;
        if (errors.hasErrors()) {
			return utilty.validateRequest(errors, httpstatus, resp);
        }
		try {
			questnSaved=iquestnRepo.save(questn);
			if(null!=questnSaved) {
				resp.setDescription(MessageConstant.SAVE_SUCCESS_QUESTION);
				resp.setHttpstatus(HttpStatus.CREATED);
				resp.setObj(questnSaved);
			}
		} catch (Exception e) {
			resp.setDescription(MessageConstant.INTERNAL_SERVER_ERROR);
			resp.setHttpstatus(HttpStatus.INTERNAL_SERVER_ERROR);
			resp.setObj(null);
		}
		return new ResponseEntity<Response>(resp, httpstatus);
	}
	
	/**
	 * not tested..
	 * @param questn
	 * @return
	 */
	@RequestMapping(value = "/update", consumes = "application/json", method = RequestMethod.PUT)
	ResponseEntity<Response> updateQuestion(@Valid @RequestBody Question questn, Errors errors) {
		HttpStatus httpstatus = HttpStatus.OK;
		Response resp = new Response();
		resp.setHttpstatus(httpstatus);
		Optional<Question> questnToUpdate = null;
		Question questnReturnVal =null;
        if (errors.hasErrors()) {
			return utilty.validateRequest(errors, httpstatus, resp);
        }
		try {
			questnToUpdate=iquestnRepo.findById(questn.getQuestionId());
			if(questnToUpdate.isPresent()) {
				questn.setQuestionId(questn.getQuestionId());
				questn.setQuiz(questn.getQuiz());
				questn.setQuestnDescp(questn.getQuestnDescp());
				questnReturnVal=iquestnRepo.save(questn);
				if(null!=questnReturnVal) {
					resp.setDescription(MessageConstant.UPDATE_SUCCESS_QUESTION);
					resp.setHttpstatus(HttpStatus.OK);
					resp.setObj(questnReturnVal);
				}
			}
			else {
				resp.setDescription(MessageConstant.NOT_FOUND_QUESTION);
				resp.setHttpstatus(HttpStatus.NOT_FOUND);
				resp.setObj(questnReturnVal);
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
	@RequestMapping(value = "/get", consumes = "application/json", method = RequestMethod.GET)
	ResponseEntity<Response> getAnswrByQuizId(@RequestParam("quizId") Long quizId) {
		HttpStatus httpstatus = HttpStatus.OK;
		Response resp = new Response();
		List<Question> questnGet = null;
		try {
			questnGet=iquestnRepo.findByQuizQuizId(quizId);
			if(!questnGet.isEmpty()) {
				resp.setDescription(MessageConstant.GET_QUESTION_SUCCESS);
				resp.setHttpstatus(HttpStatus.OK);
				resp.setObj(questnGet);
			}
			else {
				resp.setDescription(MessageConstant.NOT_FOUND_QUESTION);
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
	 * @return
	 */
	@RequestMapping(value = "/getall", consumes = "application/json", method = RequestMethod.GET)
	ResponseEntity<Response> getAllQuestion() {
		HttpStatus httpstatus = HttpStatus.OK;
		Response resp = new Response();
		List<Question> questnGet = null;
		try {
			questnGet=iquestnRepo.findAll();
			if(!questnGet.isEmpty()) {
				resp.setDescription(MessageConstant.GET_QUESTION_SUCCESS);
				resp.setHttpstatus(HttpStatus.OK);
				resp.setObj(questnGet);
			}
			else {
				resp.setDescription(MessageConstant.NOT_FOUND_QUESTION);
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

}
