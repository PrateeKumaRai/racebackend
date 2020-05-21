package com.capgemini.travel.controller;

import java.io.IOException;
import java.text.ParseException;
import java.util.List;
import java.util.Objects;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
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

import com.capgemini.travel.Dao.TravelRepository;
import com.capgemini.travel.exception.TravelDetailsException;
import com.capgemini.travel.model.DeleteModel;
import com.capgemini.travel.model.TravelDetail;
import com.capgemini.travel.model.TravelDetailsReq1;
import com.capgemini.travel.model.TraveldetailsReq;
import com.capgemini.travel.service.TravelDetailsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
* <h1>Travel Details!</h1>
* <p>The Travel Details Service class contains Some Services/API's that
* simply perform create, update, search for a particular employee Travel Details
* and return Travel Details of employee as a response.</p>
* 
* 
*
* @author  Sunil 
* @version 1.0
* @since   2020-01-21 
*/
@RestController
@RequestMapping("/travel")
@CrossOrigin("*")
public class TravelController {

	@Autowired
	TravelRepository travelRepository;
	@Autowired
	TravelDetailsService travelDetailsService;
	private static final Logger logger = LoggerFactory.getLogger(TravelController.class);
	
	
	/**
	 * create Travel Detail of particular employee @return the travelDeatil of
	 * Employee
	 * @throws ParseException 
	 */
	@PostMapping("/travels")
	public ResponseEntity createTravelDetails(@Valid @RequestBody TravelDetail travelDetail) throws ParseException {
		logger.info("Inside TravelController,create travel API, Req:  "+ travelDetail);
		TravelDetail createTravelDetail;
		try {
			createTravelDetail = travelDetailsService.createTravelDetails(travelDetail);
			if(Objects.nonNull(createTravelDetail)) {
				return new ResponseEntity(createTravelDetail,HttpStatus.OK);
				}
		} catch (TravelDetailsException e) {
			return new ResponseEntity(e,HttpStatus.NOT_FOUND);
		}
		return null;
		
	}
	
	
	@PostMapping(value = "/travels/travel",
			consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE})
	public ResponseEntity uploadTravelDetails(@Valid @RequestPart("traveldetail") TraveldetailsReq travelDetailReq, @RequestPart("myfile") MultipartFile file) throws ParseException, IOException, TravelDetailsException {
		logger.info("Inside TravelController,create travel API, Req:  "+ travelDetailReq);
		
		
		TravelDetail createTravelDetail=null;
		try {
			createTravelDetail = travelDetailsService.uploadTravelDetails(travelDetailReq,file);
			if(Objects.nonNull(createTravelDetail)) {
				return new ResponseEntity(createTravelDetail,HttpStatus.OK);
				}
		} catch (TravelDetailsException e) {
			return new ResponseEntity(e,HttpStatus.NOT_FOUND);
		}
		return null;
		//===============================
		
		
	}
	
	

	/**
	 * Gets All Travel Details @return the entire travelDeatilsList
	 */
	@GetMapping("/travels")
	public ResponseEntity getAllTravelDeatils() {
		logger.info("Inside TravelController,get All travel API, Req:  ");
		List<TravelDetail> travelDetails= travelDetailsService.getAllTravelDeatils();
		if(!(travelDetails.isEmpty())) {
			return new ResponseEntity(travelDetails,HttpStatus.OK);
			}
			return new ResponseEntity(new TravelDetailsException("ERROR_11006","Data not Found"),HttpStatus.NOT_FOUND);
		
	}
	

	/**
	 * Gets All Travel Details based on startdate,returndate and
	 * location 
	 * @return the entire travelDeatilsList
	 * 
	 * @throws ParseException
	 */

	@GetMapping("/travels/travelDetails")
	public ResponseEntity getAllTravelDeatilsByLocationAndDate(@RequestParam("date") String date,
		    @RequestParam("sourceLocation") String sourceLocation,  @RequestParam("destLocation") String destLocation,
			@RequestParam("srcCountry") String srcCountry,  @RequestParam("destCountry") String destCountry)
			throws ParseException {
		logger.info("Inside TravelController,get travel details api by date and location , Req:  "+date+" , "+sourceLocation+" , "+destLocation+","+srcCountry+","+destCountry);
		List<TravelDetail> travelDetails=  travelDetailsService.getAllTravelDeatilsByLocationAndDate(date, sourceLocation, destLocation,srcCountry,destCountry);
		if(Objects.nonNull(travelDetails)) {
			return new ResponseEntity(travelDetails,HttpStatus.OK);
			}
			return new ResponseEntity(new TravelDetailsException("ERROR_11002","TravelDetails not Found By Location and Date"),HttpStatus.NOT_FOUND);
		
	}
	
	/**
	 * Gets All Travel Details based on startdate,returndate
	 * 
	 * @return the entire travelDeatilsList
	 * 
	 * @throws ParseException
	 */

	@GetMapping("travelDetails")
	public ResponseEntity getAllTravelDeatilsByDate(@RequestParam("startDate") String startDate)
			throws ParseException {
		logger.info("Inside TravelController,get travel details api by date and location , Req:  "+startDate);
		List<TravelDetail> travelDetails= travelDetailsService.getAllTravelDeatilsByDate(startDate);
		if(Objects.nonNull(travelDetails)) {
			return new ResponseEntity(travelDetails,HttpStatus.OK);
			}
			return new ResponseEntity(new TravelDetailsException("ERROR_11002","TravelDetails not Found By Date"),HttpStatus.NOT_FOUND);
		

	}
	
	/**
	 * Gets All Travel Details based on location
	 * 
	 * @return the entire travelDeatilsList
	 * 
	 * @throws ParseException
	 */

	@GetMapping("/travelDetails/travels")
	public ResponseEntity getAllTravelDeatilsByLocation(@RequestParam("sourceLocation") String sourceLocation
			,  @RequestParam("destLocation") String destLocation,@RequestParam("srcCountry") String srcCountry, 
			@RequestParam("destCountry") String destCountry) {
		logger.info("Inside TravelController,get travel details api by date and location , Req:  "+sourceLocation+" , "+destLocation);
		List<TravelDetail> travelDetails= travelDetailsService.getAllTravelDeatilsByLocation(sourceLocation, destLocation,srcCountry,destCountry);
		if(Objects.nonNull(travelDetails)) {
		return new ResponseEntity(travelDetails,HttpStatus.OK);
		}
		return new ResponseEntity(new TravelDetailsException("ERROR_11001","TravelDetails not Found By Location"),HttpStatus.NOT_FOUND);
	}

	/**
	 * Gets Travel Details by id
	 * 
	 * @return the travelDeatil
	 * @throws ParseException 
	 */

	@GetMapping("/travelDetail/{employee_id}")
	public ResponseEntity getTravelDetailsById(@PathVariable(value = "employee_id") int employee_id) throws ParseException {
		logger.info("Inside TravelController,get travel details api by id, Req: "  +employee_id);
		List<TravelDetail> travelDetails=  travelDetailsService.getTravelDetailsById(employee_id);
		if(Objects.nonNull(travelDetails)) {
			return new ResponseEntity(travelDetails,HttpStatus.OK);
			}
			return new ResponseEntity(new TravelDetailsException("ERROR_11005","TravelDetails not Found !!!"),HttpStatus.NOT_FOUND);
		}
	
	/**
	 * / * Update Travel Details by id
	 * 
	 * @return the travelDeatil
	 */
	@PutMapping("/travels")
	public ResponseEntity updateTraveldetail(@Valid @RequestBody TravelDetail travelDetailReq) {
		logger.info("Inside TravelController,update travel details api by id, Req: "  +travelDetailReq.getEmployee_id());
		TravelDetail updatedTraveldetail =	travelDetailsService.updateTraveldetail(travelDetailReq.getEmployee_id(), travelDetailReq);
		if(Objects.nonNull(updatedTraveldetail)) {
			return new ResponseEntity(updatedTraveldetail,HttpStatus.OK);
			}
			return new ResponseEntity(new TravelDetailsException("ERROR_11003","TravelDetail not updated"),HttpStatus.NOT_FOUND);
				
	}

	@DeleteMapping("/travels/{employee_id}/{id}")
	public ResponseEntity deleteTraveldetail(@PathVariable(value = "employee_id") int employee_id,@PathVariable(value = "id") int travelId) {
		logger.info("Inside TravelController,delete travel details api by id, Req: " +employee_id);
		String responseMsg=travelDetailsService.deleteTraveldetail(employee_id,travelId);
		if(!(responseMsg.equalsIgnoreCase(null)) || !(responseMsg.equalsIgnoreCase("null")) || !(responseMsg == null)) {
			DeleteModel deleteModel=new DeleteModel();
			deleteModel.setMessage(responseMsg);
			deleteModel.setStatusCode(200);
			return new ResponseEntity(deleteModel,HttpStatus.OK);
			}
			return new ResponseEntity(new TravelDetailsException("ERROR_11004","TravelDetail not deleted by empid and travelId"),HttpStatus.NOT_FOUND);
	}

}
