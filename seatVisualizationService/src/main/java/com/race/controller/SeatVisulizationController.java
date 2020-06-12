package com.race.controller;

import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.race.constant.Response;
import com.race.model.CubicalResponse;
import com.race.restModel.UpadteSeatStatus;
import com.race.service.SeatService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

/**
 * Entry point to REST end points
 * 
 * @author praterai
 *
 */

@RestController
@CrossOrigin("*")
@Api("Manage Seat Visualization")
//@RequestMapping("/seatVisulization")
@RequestMapping("/race/api/v1/seats")
public class SeatVisulizationController {

	@Autowired
	private SeatService seatService;

	private static final Logger LOGGER = LoggerFactory.getLogger(SeatVisulizationController.class);

	/**
	 * Fetch Available Cubical's Data
	 * 
	 * @param empty {@link empty}
	 * @return {@link Response}<{@link CubicalResponse}
	 *
	 */
	@ApiOperation(value = "Get all Available Cubical's")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Successfully cubical data are fetched"),
			@ApiResponse(code = 401, message = "You are not authorized to view the resource"),
			@ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
			@ApiResponse(code = 404, message = "The resource you were trying to reach is not found") })
	// @GetMapping("/getUpdatedSeat")
	@GetMapping(value = "/getAll")
	public List<CubicalResponse> getUpdatedSeat() {
		LOGGER.info("Inside Controller getUpdatedSeat() method");
		return seatService.getUpdatedSeat();
	}

	@ApiOperation(value = "Get Available Cubical's based on mail")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Successfully cubical data are fetched"),
			@ApiResponse(code = 401, message = "You are not authorized to view the resource"),
			@ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
			@ApiResponse(code = 404, message = "The resource you were trying to reach is not found") })
	// @PostMapping("/getUpdatedSeatBymail")
	@PostMapping("/get")
	public CubicalResponse getSeatByMail(@RequestBody UpadteSeatStatus upadteSeatStatus) {
		LOGGER.info("Inside Controller getUpdatedSeat() method");
		CubicalResponse response = null;
		String mail = upadteSeatStatus.getEmailId();
		if (mail != null) {
			response = seatService.getUpdatedSeatByMail(mail);
		}
		return response;

	}

	@ApiOperation(value = "Update Seat status")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Successfully cubical data are fetched"),
			@ApiResponse(code = 401, message = "You are not authorized to view the resource"),
			@ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
			@ApiResponse(code = 404, message = "The resource you were trying to reach is not found") })
	// @PostMapping("/updatedSeatStatus")
	@PostMapping("/post")
	public Long updateSeatStatus(@RequestBody UpadteSeatStatus upadteSeatStatus) {
		LOGGER.info("Inside Controller updateSeatStatus() method");
		Long response = null;
		String cubical = upadteSeatStatus.getCubicalStatus();
		if (cubical.equals("a") || cubical.equals("b") || (cubical.equals("c"))) {
			Boolean seatStatus = true;
			Date startDate = upadteSeatStatus.getStartDate();
			Date returnDate = upadteSeatStatus.getReturnDate();
			response = seatService.updateSeatStatus(upadteSeatStatus.getEmailId(), startDate, returnDate, seatStatus);
		}
		return response;
	}

	@ApiOperation(value = "Update Seat status flag  after booking")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Successfully cubical data are fetched"),
			@ApiResponse(code = 401, message = "You are not authorized to view the resource"),
			@ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
			@ApiResponse(code = 404, message = "The resource you were trying to reach is not found") })
	// @PostMapping("/updatedSeatStatusAfterBooking")
	@PostMapping("/update")
	public Long updateSeatStatusAfterBooking(@RequestBody UpadteSeatStatus upadteSeatStatus) {
		LOGGER.info("Inside Controller updateSeatStatusAfterBooking() method");
		Long response = null;
		String loginEmail = upadteSeatStatus.getLoginMail();
		String emailId = upadteSeatStatus.getEmailId();
		String seatNo = upadteSeatStatus.getCubicalNumber();
		if (loginEmail != null & emailId != null & seatNo != null) {
			response = seatService.updateSeatStatusAfterBooking(emailId, loginEmail, seatNo);
		}
		return response;

	}

	@ApiOperation(value = "Filter seat based on city, state, Country")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Successfully cubical data are fetched"),
			@ApiResponse(code = 401, message = "You are not authorized to view the resource"),
			@ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
			@ApiResponse(code = 404, message = "The resource you were trying to reach is not found") })
	@PostMapping(path = "/filter", consumes = "application/json", produces = "application/json")
	public List<CubicalResponse> filter(@RequestBody CubicalResponse cubicalResponse) {
		LOGGER.info("Inside Controller filter() method");
		List<CubicalResponse> response = null;
		if (cubicalResponse.getCountry() == null || cubicalResponse.getCity() == null
				|| cubicalResponse.getCubicalLocation() == null) {
			return response;
		} else {
			response = seatService.filterSeat(cubicalResponse.getCountry(), cubicalResponse.getCity(),
					cubicalResponse.getCubicalLocation());
		}
		return response;
	}

	@ApiOperation(value = "Unbook seat after booking")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Successfully cubical data are fetched"),
			@ApiResponse(code = 401, message = "You are not authorized to view the resource"),
			@ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
			@ApiResponse(code = 404, message = "The resource you were trying to reach is not found") })
	// @PostMapping("/cancleSeatAfterBooking")
	@PostMapping("/cancle")
	public Long cancleSeatAfterBooking(@RequestBody UpadteSeatStatus upadteSeatStatus) {
		LOGGER.info("Inside Controller updateSeatStatusAfterBooking() method");
		Long response = null;
		String emailId = upadteSeatStatus.getEmailId();
		String loginMail = upadteSeatStatus.getLoginMail();
		if (emailId != null && loginMail != null) {
			response = seatService.cancleSeatAfterBooking(emailId, loginMail);
		}
		return response;

	}

	@ApiOperation(value = "Reset seat status based on job time interval")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Successfully cubical data are fetched"),
			@ApiResponse(code = 401, message = "You are not authorized to view the resource"),
			@ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
			@ApiResponse(code = 404, message = "The resource you were trying to reach is not found") })
	// @GetMapping("/resetStatus")
	@GetMapping("/reset")
	public Boolean jobToResetStatus() {
		LOGGER.info("Inside Controller getUpdatedSeat() method");
		return seatService.resetStatus();

	}

}
