package com.my.quiz.controller;

import java.util.LinkedList;
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
import com.my.quiz.domain.Answer;
import com.my.quiz.irepository.IAnswerRepository;
import com.my.quiz.response.Response;
import com.my.quiz.utility.Utility;
import com.my.quiz.utility.ValidList;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/answer")
public class AnswerController {
	
	@Autowired
	IAnswerRepository ianswrRepo;
	
	@Autowired
	Utility utilty;
	
	/**
	 * @param answr
	 * @return
	 */
	@RequestMapping(value = "/save", consumes = "application/json", method = RequestMethod.POST)
	ResponseEntity<Response> saveAnswer(@Valid @RequestBody Answer answr, Errors errors) {
		HttpStatus httpstatus = HttpStatus.OK;
		Response resp = new Response();
		resp.setHttpstatus(httpstatus);
		Answer answrSaved = null;
        if (errors.hasErrors()) {
			return utilty.validateRequest(errors, httpstatus, resp);
        }
		try {
			answrSaved=ianswrRepo.save(answr);
			if(null!=answrSaved) {
				resp.setDescription(MessageConstant.SAVE_SUCCESS_ANSWER);
				resp.setHttpstatus(HttpStatus.CREATED);
				resp.setObj(answrSaved);
			}
		} catch (Exception e) {
			resp.setDescription(MessageConstant.INTERNAL_SERVER_ERROR);
			resp.setHttpstatus(HttpStatus.INTERNAL_SERVER_ERROR);
			resp.setObj(null);
		}
		return new ResponseEntity<Response>(resp, httpstatus);
	}
	
	
	/**
	 * @param answr
	 * @return
	 */
	@RequestMapping(value = "/save/list", consumes = "application/json", method = RequestMethod.POST)
	ResponseEntity<Response> saveListAnswer(@RequestBody @Valid ValidList<Answer> answr, Errors errors) {
		HttpStatus httpstatus = HttpStatus.OK;
		Response resp = new Response();
		resp.setHttpstatus(httpstatus);
		List<Answer> answrList = new LinkedList<>();
        if (errors.hasErrors()) {
			return utilty.validateRequest(errors, httpstatus, resp);
        }
		try {
			answr.stream().forEach(anwr ->{
				Answer answrSaved = ianswrRepo.save(anwr);
				answrList.add(answrSaved);
				
			});
			if(!answrList.isEmpty()) {
				resp.setDescription(MessageConstant.SAVE_SUCCESS_ANSWER_LIST);
				resp.setHttpstatus(HttpStatus.CREATED);
				resp.setObj(answrList);
			}
		} catch (Exception e) {
			resp.setDescription(MessageConstant.INTERNAL_SERVER_ERROR);
			resp.setHttpstatus(HttpStatus.INTERNAL_SERVER_ERROR);
			resp.setObj(null);
		}
		return new ResponseEntity<Response>(resp, httpstatus);
	}
	
	/**
	 * /api/answer?quizId=1&questionId=2
	 * @param quizId
	 * @param questionId
	 * @return
	 */
	@RequestMapping(value = "/get", consumes = "application/json", method = RequestMethod.GET)
	ResponseEntity<Response> getAnswrByQuizIdAndQustnId(@RequestParam("quizId") Long quizId, @RequestParam("questionId") Long questionId) {
		HttpStatus httpstatus = HttpStatus.OK;
		Response resp = new Response();
		List<Answer> answrGet = null;
		try {
			answrGet=ianswrRepo.findByQuizIdAndQuestnQuestionId(quizId,questionId);
			if(!answrGet.isEmpty()) {
				resp.setDescription(MessageConstant.GET_ANSWER_SUCCESS);
				resp.setHttpstatus(HttpStatus.OK);
				resp.setObj(answrGet);
			}
			else {
				resp.setDescription(MessageConstant.NOT_FOUND_ANSWER);
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
	 * @param answr
	 * @return
	 */
	@RequestMapping(value = "/update", consumes = "application/json", method = RequestMethod.PUT)
	ResponseEntity<Response> updateAnswer(@Valid @RequestBody Answer answr, Errors errors) {
		HttpStatus httpstatus = HttpStatus.OK;
		Response resp = new Response();
		resp.setHttpstatus(httpstatus);
		Optional<Answer> answrToUpdate = null;
		Answer answrReturnVal =null;
        if (errors.hasErrors()) {
			return utilty.validateRequest(errors, httpstatus, resp);
        }
		try {
			answrToUpdate=ianswrRepo.findById(answr.getAnswerId());
			if(answrToUpdate.isPresent()) {
				answr.setAnswerId(answr.getAnswerId());
				answr.setQuestn(answr.getQuestn());
				answr.setQuizId(answr.getQuizId());
				answr.setDescription(answr.getDescription());
				answr.setIsTrueAnswr(answr.getIsTrueAnswr());
				answrReturnVal=ianswrRepo.save(answr);
				if(null!=answrReturnVal) {
					resp.setDescription(MessageConstant.UPDATE_SUCCESS_ANSWER);
					resp.setHttpstatus(HttpStatus.OK);
					resp.setObj(answrReturnVal);
				}
			}
			else {
				resp.setDescription(MessageConstant.NOT_FOUND_ANSWER);
				resp.setHttpstatus(HttpStatus.NOT_FOUND);
				resp.setObj(answrReturnVal);
			}
		} catch (Exception e) {
			resp.setDescription(MessageConstant.INTERNAL_SERVER_ERROR);
			resp.setHttpstatus(HttpStatus.INTERNAL_SERVER_ERROR);
			resp.setObj(null);
		}
		return new ResponseEntity<Response>(resp, httpstatus);
	}

}
