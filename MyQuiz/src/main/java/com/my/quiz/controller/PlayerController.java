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
import com.my.quiz.domain.Player;
import com.my.quiz.irepository.IPlayerRepository;
import com.my.quiz.irepository.IPlayquizRepository;
import com.my.quiz.response.Response;
import com.my.quiz.utility.Utility;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/player")
public class PlayerController {
	
	@Autowired
	IPlayerRepository iplayerRepo;
	
	@Autowired
	IPlayquizRepository iplayquizRepo;
	
	@Autowired
	Utility utilty;
	
	/**
	 * @param plyr
	 * @return
	 */
	@RequestMapping(value = "/save", consumes = "application/json", method = RequestMethod.POST)
	ResponseEntity<Response> savePlayer(@Valid @RequestBody Player plyr, Errors errors) {
		HttpStatus httpstatus = HttpStatus.OK;
		Response resp = new Response();
		resp.setHttpstatus(httpstatus);
		Player plyrSaved = null;
		Optional<Player> optnlPlyr = null;
        if (errors.hasErrors()) {
			return utilty.validateRequest(errors, httpstatus, resp);
        }
		try {
			optnlPlyr = iplayerRepo.findByNickNameAndQuizId(plyr.getNickName(),plyr.getQuizId());
			if(optnlPlyr.isPresent()) {
				resp.setDescription(MessageConstant.PLAYER_ALREADY_EXIST);
				resp.setHttpstatus(HttpStatus.CONFLICT);
				resp.setObj(null);
			}
			else {
				plyrSaved=iplayerRepo.save(plyr);
				if(null!=plyrSaved) {
					resp.setDescription(MessageConstant.SAVE_SUCCESS_PLAYER);
					resp.setHttpstatus(HttpStatus.CREATED);
					resp.setObj(plyrSaved);
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
	 * /api/player/get?quizId=16&playerId=17
	 * @param quizId
	 * @param playerId
	 * @return
	 */
	@RequestMapping(value = "/get", consumes = "application/json", method = RequestMethod.GET)
	ResponseEntity<Response> getPlayerByQuizIdAndPlyrId(@RequestParam("quizId") Long quizId, @RequestParam("playerId") Long playerId) {
		HttpStatus httpstatus = HttpStatus.OK;
		Response resp = new Response();
		List<Player> playrGet = null;
		try {
			playrGet=iplayerRepo.findByQuizIdAndPlayerId(quizId,playerId);
			if(!playrGet.isEmpty()) {
				resp.setDescription(MessageConstant.GET_QUESTION_PLAYER);
				resp.setHttpstatus(HttpStatus.OK);
				resp.setObj(playrGet);
			}
			else {
				resp.setDescription(MessageConstant.NOT_FOUND_PLAYER);
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
	 * /api/player/getbyquizid?quizId=16
	 * @param quizId
	 * @return
	 */
	@RequestMapping(value = "/getbyquizid", consumes = "application/json", method = RequestMethod.GET)
	ResponseEntity<Response> getPlayerByQuizId(@RequestParam("quizId") Long quizId) {
		HttpStatus httpstatus = HttpStatus.OK;
		Response resp = new Response();
		List<Player> playrGet = null;
		try {
			playrGet=iplayerRepo.findByQuizId(quizId);
			if(!playrGet.isEmpty()) {
				resp.setDescription(MessageConstant.GET_QUESTION_PLAYER);
				resp.setHttpstatus(HttpStatus.OK);
				resp.setObj(playrGet);
			}
			else {
				resp.setDescription(MessageConstant.NOT_FOUND_PLAYER);
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
	ResponseEntity<Response> deletePlayers(@PathVariable(value = "id") Long quizId) {
		HttpStatus httpstatus = HttpStatus.OK;
		Response resp = new Response();
		List<Player> playerDelete = null;
		try {
			playerDelete=iplayerRepo.findByQuizId(quizId);
			if(!playerDelete.isEmpty()) {
				iplayquizRepo.deleteByQuizId(quizId);
				iplayerRepo.deleteByQuizId(quizId);
				resp.setDescription(MessageConstant.DELETE_PLAYER_SUCCESS);
				resp.setHttpstatus(HttpStatus.NO_CONTENT);
				resp.setObj(null);
			}
			else {
				resp.setDescription(MessageConstant.NOT_FOUND_PLAYER);
				resp.setHttpstatus(HttpStatus.NOT_FOUND);
				resp.setObj(playerDelete);
			}
		} catch (Exception e) {
			resp.setDescription(MessageConstant.INTERNAL_SERVER_ERROR);
			resp.setHttpstatus(HttpStatus.INTERNAL_SERVER_ERROR);
			resp.setObj(null);
		}
		return new ResponseEntity<Response>(resp, httpstatus);
	}

}
