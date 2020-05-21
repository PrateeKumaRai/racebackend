package com.my.quiz.controller;

import java.util.List;
import java.util.Optional;
import java.util.Random;

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
import com.my.quiz.domain.Quiz;
import com.my.quiz.irepository.IAnswerRepository;
import com.my.quiz.irepository.IPlayerRepository;
import com.my.quiz.irepository.IPlayquizRepository;
import com.my.quiz.irepository.IQuestionRepository;
import com.my.quiz.irepository.IQuizRepository;
import com.my.quiz.response.Response;
import com.my.quiz.utility.Utility;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/quiz")
public class QuizController {
	
	@Autowired
	IQuizRepository iquzRepo;
	
	@Autowired
	IQuestionRepository iquestnRepo;
	
	@Autowired
	IAnswerRepository ianswrRepo;
	
	@Autowired
	IPlayerRepository iplayerRepo;
	
	@Autowired
	IPlayquizRepository iplayquizRepo;
	
	@Autowired
	Utility utilty;
	
	/**
	 * @param quz
	 * @return
	 */
	@RequestMapping(value = "/save", consumes = "application/json", method = RequestMethod.POST)
	ResponseEntity<Response> saveQuiz(@Valid @RequestBody Quiz quz, Errors errors) {
		HttpStatus httpstatus = HttpStatus.OK;
		Response resp = new Response();
		resp.setHttpstatus(httpstatus);
		Quiz quzSaved = null;
        if (errors.hasErrors()) {
			return utilty.validateRequest(errors, httpstatus, resp);
        }
		try {
			quzSaved=iquzRepo.save(quz);
			if(null!=quzSaved) {
				resp.setDescription(MessageConstant.SAVE_SUCCESS_QUIZ);
				resp.setHttpstatus(HttpStatus.CREATED);
				resp.setObj(quzSaved);
			}
		} catch (Exception e) {
			resp.setDescription(MessageConstant.INTERNAL_SERVER_ERROR);
			resp.setHttpstatus(HttpStatus.INTERNAL_SERVER_ERROR);
			resp.setObj(null);
		}
		return new ResponseEntity<Response>(resp, httpstatus);
	}

	
	/**
	 * @param quiz
	 * @return
	 */
	@RequestMapping(value = "/update", consumes = "application/json", method = RequestMethod.PUT)
	ResponseEntity<Response> updateQuiz(@Valid @RequestBody Quiz quiz, Errors errors) {
		HttpStatus httpstatus = HttpStatus.OK;
		Response resp = new Response();
		resp.setHttpstatus(httpstatus);
		Optional<Quiz> quizToUpdate = null;
		Quiz quizReturnVal =null;
        if (errors.hasErrors()) {
			return utilty.validateRequest(errors, httpstatus, resp);
        }
		try {
			quizToUpdate=iquzRepo.findById(quiz.getQuizId());
			if(quizToUpdate.isPresent()) {
				quiz.setQuizId(quiz.getQuizId());
				quiz.setTitle(quiz.getTitle());
				quiz.setDescription(quiz.getDescription());
				if(quiz.getIsPublish().equalsIgnoreCase("yes")) {
					Random random = new Random();
					String publishId = String.format("%06d", random.nextInt(999999));
					quiz.setPin(publishId);
				}
				else {
					quiz.setPin(quiz.getPin());
				}
				quiz.setIsPublish(quiz.getIsPublish());
				quizReturnVal=iquzRepo.save(quiz);
				if(null!=quizReturnVal) {
					resp.setDescription(MessageConstant.UPDATE_SUCCESS_QUIZ);
					resp.setHttpstatus(HttpStatus.OK);
					resp.setObj(quizReturnVal);
				}
			}
			else {
				resp.setDescription(MessageConstant.NOT_FOUND_QUIZ);
				resp.setHttpstatus(HttpStatus.NOT_FOUND);
				resp.setObj(quizReturnVal);
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
	@RequestMapping(value = "/get/{id}", consumes = "application/json", method = RequestMethod.GET)
	ResponseEntity<Response> getQuiz(@PathVariable(value = "id") Long quizId) {
		HttpStatus httpstatus = HttpStatus.OK;
		Response resp = new Response();
		Optional<Quiz> quizGet = null;
		try {
			quizGet=iquzRepo.findById(quizId);
			if(quizGet.isPresent()) {
				resp.setDescription(MessageConstant.GET_QUIZ_SUCCESS);
				resp.setHttpstatus(HttpStatus.OK);
				resp.setObj(quizGet.get());
			}
			else {
				resp.setDescription(MessageConstant.NOT_FOUND_QUIZ);
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
	 * http://localhost:5902/api/quiz/get/publish?isPublish=yes&pin=2076
	 * @param isPublish
	 * @param pin
	 * @return
	 */
	@RequestMapping(value = "/get/publish", consumes = "application/json", method = RequestMethod.GET)
	ResponseEntity<Response> getByIsPublishAndPin(@RequestParam("isPublish") String isPublish, @RequestParam("pin") String pin) {
		HttpStatus httpstatus = HttpStatus.OK;
		Response resp = new Response();
		Optional<Quiz> getQuiz = null;
		try {
			getQuiz=iquzRepo.findByIsPublishAndPin(isPublish,pin);
			if(getQuiz.isPresent()) {
				resp.setDescription(MessageConstant.GET_QUIZ_SUCCESS);
				resp.setHttpstatus(HttpStatus.OK);
				resp.setObj(getQuiz.get());
			}
			else {
				resp.setDescription(MessageConstant.NOT_FOUND_QUIZ);
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
	@RequestMapping(value = "/delete/{id}", consumes = "application/json", method = RequestMethod.DELETE)
	ResponseEntity<Response> deleteQuiz(@PathVariable(value = "id") Long quizId) {
		HttpStatus httpstatus = HttpStatus.OK;
		Response resp = new Response();
		Optional<Quiz> quizDelete = null;
		try {
			quizDelete=iquzRepo.findById(quizId);
			if(quizDelete.isPresent()) {
				iplayquizRepo.deleteByQuizId(quizId);
				iplayerRepo.deleteByQuizId(quizId);
				ianswrRepo.deleteByQuizId(quizId);
				iquestnRepo.deleteByQuizQuizId(quizId);
				iquzRepo.delete(quizDelete.get());
				resp.setDescription(MessageConstant.DELETE_QUIZ_SUCCESS);
				resp.setHttpstatus(HttpStatus.NO_CONTENT);
				resp.setObj(null);
			}
			else {
				resp.setDescription(MessageConstant.NOT_FOUND_QUIZ);
				resp.setHttpstatus(HttpStatus.NOT_FOUND);
				resp.setObj(quizDelete);
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
	ResponseEntity<Response> getAllQuiz() {
		HttpStatus httpstatus = HttpStatus.OK;
		Response resp = new Response();
		List<Quiz> quizGet = null;
		try {
			quizGet=iquzRepo.findAll();
			if(!quizGet.isEmpty()) {
				resp.setDescription(MessageConstant.GET_QUIZ_SUCCESS);
				resp.setHttpstatus(HttpStatus.OK);
				resp.setObj(quizGet);
			}
			else {
				resp.setDescription(MessageConstant.NOT_FOUND_QUIZ);
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
	@RequestMapping(value = "/publishquiz/{id}", consumes = "application/json", method = RequestMethod.GET)
	ResponseEntity<Response> publishQuiz(@PathVariable(value = "id") Long quizId) {
		HttpStatus httpstatus = HttpStatus.OK;
		Response resp = new Response();
		Optional<Quiz> quizToUpdate = null;
		Quiz quizReturnVal =null;
		try {
			quizToUpdate=iquzRepo.findById(quizId);
			if(quizToUpdate.isPresent()) {
				quizReturnVal = quizToUpdate.get();
				quizReturnVal.setIsPublish("yes");
				Random random = new Random();
				String publishId = String.format("%06d", random.nextInt(999999));
				if(quizReturnVal.getPin().isEmpty()) {
					quizReturnVal.setPin(publishId);
					quizReturnVal=iquzRepo.save(quizReturnVal);
				}				
				if(null!=quizReturnVal) {
					resp.setDescription(MessageConstant.UPDATE_SUCCESS_QUIZ);
					resp.setHttpstatus(HttpStatus.OK);
					resp.setObj(quizReturnVal);
				}
			}
			else {
				resp.setDescription(MessageConstant.NOT_FOUND_QUIZ);
				resp.setHttpstatus(HttpStatus.NOT_FOUND);
				resp.setObj(quizReturnVal);
			}		
		} catch (Exception e) {
			resp.setDescription(MessageConstant.INTERNAL_SERVER_ERROR);
			resp.setHttpstatus(HttpStatus.INTERNAL_SERVER_ERROR);
			resp.setObj(null);
		}
		return new ResponseEntity<Response>(resp, httpstatus);
	}
	
}
