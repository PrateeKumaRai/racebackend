package com.my.quiz.controller;

import java.util.List;
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
import com.my.quiz.domain.Playquiz;
import com.my.quiz.irepository.IPlayquizRepository;
import com.my.quiz.response.Response;
import com.my.quiz.utility.Utility;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/playquiz")
public class PlayquizController {
	
	@Autowired
	IPlayquizRepository iplayquizRepo;
	
	@Autowired
	Utility utilty;
	
	/**
	 * @param playQuiz
	 * @return
	 */
	@RequestMapping(value = "/save", consumes = "application/json", method = RequestMethod.POST)
	ResponseEntity<Response> savePlayQuiz(@Valid @RequestBody Playquiz playQuiz, Errors errors) {
		HttpStatus httpstatus = HttpStatus.OK;
		Response resp = new Response();
		resp.setHttpstatus(httpstatus);
		Playquiz playQuizSaved = null;
		long levelMaxTime=0;
		int timeSpent;
		int levelScore = 10;
        if (errors.hasErrors()) {
			return utilty.validateRequest(errors, httpstatus, resp);
        }
		try {
			levelMaxTime = playQuiz.getConfigureTime();
			if(playQuiz.getState().equals("right")) {
				timeSpent = playQuiz.getAnswerTime();
				long score = (levelMaxTime + timeSpent) * levelScore;
				playQuiz.setScore(score);
			}else if(playQuiz.getState().equals("wrong")) {
				playQuiz.setScore(Long.valueOf(0));
			}
			playQuizSaved=iplayquizRepo.save(playQuiz);
			if(null!=playQuizSaved) {
				resp.setDescription(MessageConstant.SAVE_SUCCESS_PLAYQUIZ);
				resp.setHttpstatus(HttpStatus.CREATED);
				resp.setObj(playQuizSaved);
			}
		} catch (Exception e) {
			resp.setDescription(MessageConstant.INTERNAL_SERVER_ERROR);
			resp.setHttpstatus(HttpStatus.INTERNAL_SERVER_ERROR);
			resp.setObj(null);
		}
		return new ResponseEntity<Response>(resp, httpstatus);
	}
	
	/**
	 * /api/playquiz/get?quizId=1&questionId=3&playerId=17
	 * @param quizId
	 * @param questionId
	 * @param playerId
	 * @return
	 */
	@RequestMapping(value = "/get", consumes = "application/json", method = RequestMethod.GET)
	ResponseEntity<Response> getByQuizIdAndQustnIdAndPlyrId(@RequestParam("quizId") Long quizId, @RequestParam("questionId") Long questionId,@RequestParam("playerId") Long playerId) {
		HttpStatus httpstatus = HttpStatus.OK;
		Response resp = new Response();
		Optional<Playquiz> playQuiz = null;
		try {
			playQuiz=iplayquizRepo.findByQuizIdAndQuestionIdAndPlayerPlayerId(quizId,questionId,playerId);
			if(playQuiz.isPresent()) {
				resp.setDescription(MessageConstant.GET_QUESTION_PLAYQUIZ);
				resp.setHttpstatus(HttpStatus.OK);
				resp.setObj(playQuiz.get());
			}
			else {
				resp.setDescription(MessageConstant.NOT_FOUND_PLAYQUIZ);
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
	 * /api/playquiz/getresult?quizId=1
	 * @param quizId
	 * @return
	 */
	@RequestMapping(value = "/getresult", consumes = "application/json", method = RequestMethod.GET)
	ResponseEntity<Response> getByQuizId(@RequestParam("quizId") Long quizId) {
		HttpStatus httpstatus = HttpStatus.OK;
		Response resp = new Response();
		List<Playquiz> playQuiz = null;
		try {
			playQuiz=iplayquizRepo.findByQuizId(quizId);
			if(!playQuiz.isEmpty()) {
				resp.setDescription(MessageConstant.GET_QUESTION_PLAYQUIZ);
				resp.setHttpstatus(HttpStatus.OK);
				resp.setObj(playQuiz);
			}
			else {
				resp.setDescription(MessageConstant.NOT_FOUND_PLAYQUIZ);
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
	
	@RequestMapping(value = "/delete/{id}", consumes = "application/json", method = RequestMethod.DELETE)
	ResponseEntity<Response> deletePlayQuiz(@PathVariable(value = "id") Long quizId) {
		HttpStatus httpstatus = HttpStatus.OK;
		Response resp = new Response();
		List<Playquiz> playQuizDelete = null;
		try {
			playQuizDelete=iplayquizRepo.findByQuizId(quizId);
			if(!playQuizDelete.isEmpty()) {
				iplayquizRepo.deleteByQuizId(quizId);
				resp.setDescription(MessageConstant.DELETE_PLAY_QUIZ_SUCCESS);
				resp.setHttpstatus(HttpStatus.NO_CONTENT);
				resp.setObj(null);
			}
			else {
				resp.setDescription(MessageConstant.NOT_FOUND_PLAY_QUIZ);
				resp.setHttpstatus(HttpStatus.NOT_FOUND);
				resp.setObj(playQuizDelete);
			}
		} catch (Exception e) {
			resp.setDescription(MessageConstant.INTERNAL_SERVER_ERROR);
			resp.setHttpstatus(HttpStatus.INTERNAL_SERVER_ERROR);
			resp.setObj(null);
		}
		return new ResponseEntity<Response>(resp, httpstatus);
	}

}
