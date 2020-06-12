package com.race.travel.controller;

import java.io.IOException;
import java.text.ParseException;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.google.common.base.Strings;
import com.race.travel.Dao.TravelRepository;
import com.race.travel.exception.TravelDetailsException;
import com.race.travel.model.ErrorHandelModel;
import com.race.travel.model.TravelDetail;
import com.race.travel.model.TravelDetailsErrorCode;
import com.race.travel.model.TraveldetailsReq;
import com.race.travel.service.TravelDetailsService;
import com.race.travel.validator.TravelDetailsValidator;

/**
 * <h1>Travel Details!</h1>
 * <p>
 * The Travel Details Service class contains Some Services/API's that simply
 * perform create, update, search for a particular employee Travel Details and
 * return Travel Details of employee as a response.
 * </p>
 * 
 * 
 *
 * @author Sunil
 * @version 2.0
 * @since 2020-05-28
 */
@RestController
@RequestMapping("/travels")
@CrossOrigin("*")
public class TravelController {

	@Autowired
	TravelRepository travelRepository;
	@Autowired
	TravelDetailsService travelDetailsService;
	@Autowired
	TravelDetailsValidator travelDetailsValidator;
	private static final Logger logger = LoggerFactory.getLogger(TravelController.class);

	/**
	 * create Travel Detail of particular employee
	 * 
	 * @return the travelDeatil of Employee
	 * @throws ParseException
	 */
	@PostMapping("/travelDetails")
	public ResponseEntity<?> createTravelDetails(@Valid @RequestBody TravelDetail travelDetail,
			BindingResult bindingResult) throws ParseException {
		logger.info("Inside TravelController,create travel API");

		if (bindingResult.hasErrors()) {
			return validateCreateTravelDetailRequest(bindingResult);
		}

		return travelDetailsService.createTravelDetails(travelDetail);

	}

	/**
	 * create Travel Detail of particular employee
	 * 
	 * @param travelDetailReq
	 * @param file
	 * @return TravelDeatil of created Employee
	 * @throws ParseException
	 * @throws IOException
	 * @throws TravelDetailsException
	 */
	@PostMapping(value = "/travelDetails/travels", consumes = { MediaType.APPLICATION_JSON_VALUE,
			MediaType.MULTIPART_FORM_DATA_VALUE })
	public ResponseEntity<?> uploadTravelDetails(@Valid @RequestPart("traveldetail") TraveldetailsReq travelDetailReq,
			BindingResult bindingResult, @RequestPart("myfile") MultipartFile file)
			throws ParseException, IOException, TravelDetailsException {
		logger.info("Inside TravelController,create travel API, Req:  ");


		if (bindingResult.hasErrors()) {
			return validateCreateTravelDetailRequest(bindingResult);
		}

		return travelDetailsService.uploadTravelDetails(travelDetailReq, file);

	}

	/**
	 * Gets All Travel Details
	 * 
	 * @return the all TravelDeatils of employee
	 * @throws TravelDetailsException
	 */
	@GetMapping("/travelDetails")
	public ResponseEntity<?> getAllTravelDeatils() {
		logger.info("Inside TravelController,get All travel API");

		return travelDetailsService.getAllTravelDeatils();

	}

	/**
	 * <p>
	 * Search All Travel Details of based on date, and location. this simply return
	 * five days before and five days after travel details based on the requested
	 * date and source country and location and destination country and location.
	 * </p>
	 * 
	 * @param date
	 * @param sourceLocation
	 * @param destLocation
	 * @param srcCountry
	 * @param destCountry
	 * 
	 * @return List of TravelDeatils of all employee
	 * 
	 * @throws ParseException
	 */
	@GetMapping("/travelDetails/travels")
	public ResponseEntity<?> getAllTravelDeatilsByLocationAndDate(
			@RequestParam("date") @NotNull(message = "date should not be null or empty") String date,
			@RequestParam("sourceLocation") String sourceLocation, @RequestParam("destLocation") String destLocation,
			@RequestParam("srcCountry") String srcCountry, @RequestParam("destCountry") String destCountry)
			throws ParseException {

		logger.info("Inside TravelController,get travel details api by date and location");

		if (Strings.isNullOrEmpty(date) || Strings.isNullOrEmpty(sourceLocation) || Strings.isNullOrEmpty(destLocation)
				|| Strings.isNullOrEmpty(srcCountry) || Strings.isNullOrEmpty(destCountry)) {
			return travelDetailsValidator.findByDateAndLocationValidator();
		}

		return travelDetailsService.getAllTravelDeatilsByLocationAndDate(date, sourceLocation, destLocation, srcCountry,
				destCountry);

	}

	/**
	 * <p>
	 * Search All Travel Details of based on date. this simply return five days
	 * before and five days after travel details based on the requested date
	 * </p>
	 * 
	 * @param startDate
	 * 
	 * @return List of TravelDeatils of all employee
	 * @throws TravelDetailsException
	 * 
	 * @throws ParseException
	 */
	@GetMapping("travelDetails/dates")
	public ResponseEntity<?> getAllTravelDeatilsByDate(@RequestParam("startDate") String startDate) {
		logger.info("Inside TravelController,get travel details api by date and location");
		if (Strings.isNullOrEmpty(startDate) || validateDate(startDate)) {
			return travelDetailsValidator.findByDateValidator();
		}

		return travelDetailsService.getAllTravelDeatilsByDate(startDate);

	}

	/**
	 * <p>
	 * Search All Travel Details of based on location. source country and location
	 * and destination country and location. this simply return travel details based
	 * source country and location and destination country and location.
	 * </p>
	 * 
	 * @param sourceLocation
	 * @param destLocation
	 * @param srcCountry
	 * @param destCountry
	 * 
	 * @return List of TravelDeatils of all employee
	 */
	@GetMapping("/travelDetails/locations")
	public ResponseEntity<?> getAllTravelDeatilsByLocation(@RequestParam("sourceLocation") String sourceLocation,
			@RequestParam("destLocation") String destLocation, @RequestParam("srcCountry") String srcCountry,
			@RequestParam("destCountry") String destCountry) {
		logger.info("Inside TravelController,get travel details api by date and location");
		if (Strings.isNullOrEmpty(sourceLocation) || Strings.isNullOrEmpty(destLocation)
				|| Strings.isNullOrEmpty(srcCountry) || Strings.isNullOrEmpty(destCountry)) {
			return travelDetailsValidator.validateLocations();
		}

		return travelDetailsService.getAllTravelDeatilsByLocation(sourceLocation, destLocation, srcCountry,
				destCountry);

	}

	/**
	 * Search Travel Details by employee id
	 * 
	 * @param employee_id
	 * @return list of travel details
	 * @throws ParseException
	 */
	@GetMapping("/travelDetails/{employee_id}")
	public ResponseEntity<?> getTravelDetailsById(@PathVariable(value = "employee_id") Integer employee_id) {
		logger.info("Inside TravelController,get travel details api by employee id");
		List<TravelDetail> travelDetails;
		if (employee_id.equals(null) || employee_id == 0) {
			return travelDetailsValidator.validateEmpID();
		}

		return travelDetailsService.getTravelDetailsById(employee_id);

	}

	/**
	 * <p>
	 * this API is used to update travel details of particular employee based on
	 * employee id and travel id
	 * </p>
	 * 
	 * @param travelDetailReq
	 * @return updated travel detail of employee
	 */
	@PutMapping("/travelDetails")
	public ResponseEntity<?> updateTraveldetail(@Valid @RequestBody TravelDetail travelDetailReq,
			BindingResult bindingResult) {
		logger.info("Inside TravelController,update travel details api by id");
		if (travelDetailReq.getEmp_id().equals(null) || travelDetailReq.getEmp_id() == 0
				|| travelDetailReq.getTravel_id().equals(null) || travelDetailReq.getTravel_id() == 0) {
			return travelDetailsValidator.validateEmpIdAndTravelId();
		}
		if (bindingResult.hasErrors()) {
			return validateCreateTravelDetailRequest(bindingResult);
		}

		return travelDetailsService.updateTraveldetail(travelDetailReq.getEmp_id(), travelDetailReq);

	}

	/**
	 * *
	 * <p>
	 * this API is used to delete travel details of particular employee based on
	 * employee id and travel id
	 * </p>
	 * 
	 * @param employee_id
	 * @param travelId
	 * @return String message
	 */
	@DeleteMapping("/travelDetails/{employee_id}/{id}")
	public ResponseEntity<?> deleteTraveldetail(@PathVariable(value = "employee_id") Integer employee_id,
			@PathVariable(value = "id") Integer travelId) {
		logger.info("Inside TravelController,delete travel details api by id");

		if (employee_id.equals(null) || employee_id == 0 || travelId.equals(null) || travelId == 0) {
			return travelDetailsValidator.validateEmpIdAndTravelId();
		}

		return travelDetailsService.deleteTraveldetail(employee_id, travelId);

	}

	/**
	 * <p>
	 * this method is used to set error message,error code and status code based on
	 * the exception we are getting
	 * </P>
	 * 
	 * @param errorMessage
	 * @param statusCode
	 * @param errorCode
	 * @return ErrorHandelModel
	 */
	private ErrorHandelModel createErrorHandelModel(String errorMessage, Integer statusCode, String errorCode) {
		ErrorHandelModel errorHandelModel = new ErrorHandelModel();
		errorHandelModel.setErrorMessage(errorMessage);
		errorHandelModel.setStatusCode(statusCode);
		errorHandelModel.setErrorCode(errorCode);
		return errorHandelModel;

	}

	/**
	 * <p>
	 * this method is used to validate the request and send the error messsage as
	 * Response
	 * </P>
	 * 
	 * @param errors
	 * @return ResponseEntity
	 */
	private ResponseEntity<Object> validateCreateTravelDetailRequest(BindingResult errors) {
		logger.error("inside validateCreateTravelDetailRequest : Request element Validation for null check");
		String errormsg = errors.getAllErrors().stream().map(x -> x.getDefaultMessage())
				.collect(Collectors.joining(","));

		ErrorHandelModel errorHandelModel = createErrorHandelModel(errormsg,
				TravelDetailsErrorCode.TRAVEL_DETAILS_BAD_REQUEST, TravelDetailsErrorCode.BAD_REQUEST_ERROR_CODE);

		return new ResponseEntity<Object>(errorHandelModel, HttpStatus.BAD_REQUEST);

	}

	private boolean validateDate(String startDate) {
		if (!(StringUtils.hasText(startDate)) || startDate.equalsIgnoreCase("null")) {
			return Boolean.TRUE;
		}
		return Boolean.FALSE;
	}

}
