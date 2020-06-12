package com.race.travel.service;

import java.util.List;
import java.util.Objects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.race.travel.Dao.TravelRepository;
import com.race.travel.exception.TravelDetailsException;
import com.race.travel.factory.TravelDetailsFactory;
import com.race.travel.model.DeleteModel;
import com.race.travel.model.ErrorHandelModel;
import com.race.travel.model.TravelDetail;
import com.race.travel.model.TravelDetailsErrorCode;
import com.race.travel.model.TraveldetailsReq;
import com.race.travel.validator.TravelDetailsValidator;

/**
 * <h1>TravelDetailsServiceImpl</h1>
 * <p>
 * The Travel Details Service class simply receive the requset from controller 
 * and propagate it to TravelDetailsFactory to perform some business operation
 * and also handling the exception for send proper error message to end user.
 * </p>
 * 
 * 
 *
 * @author Sunil
 * @version 2.0
 * @since 2020-05-28
 */

@Service("TravelDetailsService")
public class TravelDetailsServiceImpl implements TravelDetailsService {
	private static final Logger logger = LoggerFactory.getLogger(TravelDetailsServiceImpl.class);

	@Autowired
	TravelRepository travelRepository;
	@Autowired
	TravelDetailsValidator travelDetailsValidator;
	@Autowired
	TravelDetailsFactory travelDetailsFactory;

	@Override
	public ResponseEntity<?> createTravelDetails(TravelDetail travelDetail) {
		logger.info("Inside TravelDetailsServiceImpl,create travel API ");

		TravelDetail createTravelDetail;
		try {
			createTravelDetail = travelDetailsFactory.createTravelDetails(travelDetail);
			if (Objects.nonNull(createTravelDetail)) {
				return new ResponseEntity<Object>(createTravelDetail, HttpStatus.OK);
			}
			ErrorHandelModel errorHandelModel = createErrorHandelModel(TravelDetailsErrorCode.BAD_REQUEST_ERROR_MESSAGE,
					TravelDetailsErrorCode.TRAVEL_DETAILS_BAD_REQUEST, TravelDetailsErrorCode.BAD_REQUEST_ERROR_CODE);
			return new ResponseEntity<Object>(errorHandelModel, HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			logger.error("Inside TravelController createTravelDetails error: "+e.getMessage());
			ErrorHandelModel errorHandelModel = createErrorHandelModel(
					TravelDetailsErrorCode.TRAVEL_DETAILS_NOT_CREATED_ERROR_MESSAGE,
					TravelDetailsErrorCode.TRAVEL_DETAILS_INTERNAL_SERVER_ERROR,
					TravelDetailsErrorCode.CREATE_TRAVEL_DETAILS_ERROR_CODE);
			return new ResponseEntity<Object>(errorHandelModel, HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	@Override
	public ResponseEntity<?> getAllTravelDeatils() {
		logger.info("Inside ravelDetailsServiceImpl,get All travel API, Req: before DB callS  ");

		List<TravelDetail> travelDetails;
		try {
			travelDetails = travelDetailsFactory.getAllTravelDeatils();
			if (!(travelDetails.isEmpty())) {
				return new ResponseEntity<Object>(travelDetails, HttpStatus.OK);
			}
			return travelDetailsValidator.travelDetailsNotFound();

		} catch (TravelDetailsException e) {
			logger.error("Inside TravelController getAllTravelDeatils error:: "+e.getErrorMessage());
			ErrorHandelModel errorHandelModel = createErrorHandelModel(e.getErrorMessage(),
					TravelDetailsErrorCode.TRAVEL_DETAILS_INTERNAL_SERVER_ERROR, e.getErrorCode());
			return new ResponseEntity<Object>(errorHandelModel, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@Override
	public ResponseEntity<?> getAllTravelDeatilsByLocationAndDate(String date, String sourceLocation,
			String destLocation, String srcCountry,
			String destCountry) /* throws ParseException, TravelDetailsException */ {

		logger.info("Inside TravelDetailsServiceImpl,get travel details api by date and location");
		try {

			List<TravelDetail> travelDetails = travelDetailsFactory.getAllTravelDeatilsByLocationAndDate(date,
					sourceLocation, destLocation, srcCountry, destCountry);
			if (Objects.nonNull(travelDetails)) {
				return new ResponseEntity<Object>(travelDetails, HttpStatus.OK);
			}
			return travelDetailsValidator.travelDetailsNotFoundByDateAndLocation();

		} catch (TravelDetailsException e) {
			logger.error("Inside TravelController getAllTravelDeatilsByLocationAndDate error:: "+e.getErrorMessage());
			ErrorHandelModel errorHandelModel = createErrorHandelModel(e.getErrorMessage(),
					TravelDetailsErrorCode.TRAVEL_DETAILS_INTERNAL_SERVER_ERROR, e.getErrorCode());
			return new ResponseEntity<Object>(errorHandelModel, HttpStatus.INTERNAL_SERVER_ERROR);
		} catch (Exception e) {
			logger.error("Inside TravelController getAllTravelDeatilsByLocationAndDate error:: "+e.getMessage());
			return travelDetailsValidator.dateParserValidate(e);
		}

	}

	@Override
	public ResponseEntity<?> getAllTravelDeatilsByDate(String startDate) {
		logger.info("Inside TravelDetailsServiceImpl,get travel details api by date ");

		try {
			List<TravelDetail> travelDetails = travelDetailsFactory.getAllTravelDeatilsByDate(startDate);
			if (Objects.nonNull(travelDetails)) {
				return new ResponseEntity<Object>(travelDetails, HttpStatus.OK);
			}

			return travelDetailsValidator.travelDetailsNotFoundByDate();

		} catch (TravelDetailsException e) {
			logger.error("Inside TravelController getAllTravelDeatilsByDate error:: "+e.getErrorMessage());
			ErrorHandelModel errorHandelModel = createErrorHandelModel(e.getErrorMessage(),
					TravelDetailsErrorCode.TRAVEL_DETAILS_INTERNAL_SERVER_ERROR, e.getErrorCode());
			return new ResponseEntity<Object>(errorHandelModel, HttpStatus.INTERNAL_SERVER_ERROR);
		} catch (Exception e) {
			logger.error("Inside TravelController getAllTravelDeatilsByDate error:: "+e.getMessage());
			return travelDetailsValidator.dateParserValidate(e);
		}

	}

	@Override
	public ResponseEntity<?> getAllTravelDeatilsByLocation(String sourceLocation, String destLocation,
			String srcCountry, String destCountry) {
		logger.info("Inside TravelDetailsServiceImpl,get travel details api by  location");
		try {
			List<TravelDetail> travelDetails = travelDetailsFactory.getAllTravelDeatilsByLocation(sourceLocation,
					destLocation, srcCountry, destCountry);
			if (Objects.nonNull(travelDetails)) {
				return new ResponseEntity<Object>(travelDetails, HttpStatus.OK);
			}
			return travelDetailsValidator.travelDetailsNotFoundByLocation();

		} catch (TravelDetailsException e) {
			logger.error("Inside TravelController getAllTravelDeatilsByLocation error:: "+e.getErrorMessage());
			ErrorHandelModel errorHandelModel = createErrorHandelModel(e.getErrorMessage(),
					TravelDetailsErrorCode.TRAVEL_DETAILS_INTERNAL_SERVER_ERROR, e.getErrorCode());
			return new ResponseEntity<Object>(errorHandelModel, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@Override
	public ResponseEntity<?> getTravelDetailsById(Integer empid) {
		logger.info("Inside TravelDetailsServiceImpl,get travel Details by Id ");
		List<TravelDetail> travelDetails;

		try {
			travelDetails = travelDetailsFactory.getTravelDetailsById(empid);
			if (Objects.nonNull(travelDetails)) {
				return new ResponseEntity<Object>(travelDetails, HttpStatus.OK);
			}
			return travelDetailsValidator.travelDetailsByEmpid();

		} catch (TravelDetailsException e) {

			ErrorHandelModel errorHandelModel = createErrorHandelModel(e.getErrorMessage(),
					TravelDetailsErrorCode.TRAVEL_DETAILS_INTERNAL_SERVER_ERROR, e.getErrorCode());
			return new ResponseEntity<Object>(errorHandelModel, HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	@Override
	public ResponseEntity<?> updateTraveldetail(int employee_id, TravelDetail travelDetailReq) {

		logger.info("Inside TravelDetailsServiceImpl,update travel details by employee_id, Req: before DB call ");


		try {
			TravelDetail updatedTraveldetail = travelDetailsFactory.updateTraveldetail(travelDetailReq.getEmp_id(),
					travelDetailReq);
			if (Objects.nonNull(updatedTraveldetail)) {
				return new ResponseEntity<Object>(updatedTraveldetail, HttpStatus.OK);
			}
			return travelDetailsValidator.travelDetailsNotUpdatedByEmpidAndTravelId();

		} catch (TravelDetailsException e) {
			logger.error("Inside TravelController updateTraveldetail error:: "+e.getErrorMessage());

			ErrorHandelModel errorHandelModel = createErrorHandelModel(e.getErrorMessage(),
					TravelDetailsErrorCode.TRAVEL_DETAILS_NOT_FOUND, e.getErrorCode());
			return new ResponseEntity<Object>(errorHandelModel, HttpStatus.NOT_FOUND);

		} catch (Exception e) {
			logger.error("Inside TravelController updateTraveldetail error:: "+e.getMessage());
			ErrorHandelModel errorHandelModel = createErrorHandelModel(
					TravelDetailsErrorCode.INTERNAL_SERVER_ERROR_MESSAGE,
					TravelDetailsErrorCode.TRAVEL_DETAILS_INTERNAL_SERVER_ERROR,
					TravelDetailsErrorCode.INTERNAL_SERVER_ERROR_CODE);
			return new ResponseEntity<Object>(errorHandelModel, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@Override
	public ResponseEntity<?> deleteTraveldetail(Integer empid, Integer travelId) {

		logger.info("Inside TravelDetailsServiceImpl,delete travel details api by id, Req: ");
		try {
			String responseMsg = travelDetailsFactory.deleteTraveldetail(empid, travelId);
			DeleteModel deleteModel = new DeleteModel();
			deleteModel.setMessage(responseMsg);
			deleteModel.setStatusCode(200);
			return new ResponseEntity<Object>(deleteModel, HttpStatus.OK);

		} catch (TravelDetailsException e) {
			logger.error("Inside TravelController deleteTraveldetail error:: "+e.getErrorMessage());
			ErrorHandelModel errorHandelModel = createErrorHandelModel(e.getErrorMessage(),
					TravelDetailsErrorCode.TRAVEL_DETAILS_NOT_FOUND, e.getErrorCode());
			return new ResponseEntity<Object>(errorHandelModel, HttpStatus.NOT_FOUND);

		} catch (Exception e) {
			logger.error("Inside TravelController deleteTraveldetail error:: "+e.getMessage());
			ErrorHandelModel errorHandelModel = createErrorHandelModel(
					TravelDetailsErrorCode.INTERNAL_SERVER_ERROR_MESSAGE,
					TravelDetailsErrorCode.TRAVEL_DETAILS_INTERNAL_SERVER_ERROR,
					TravelDetailsErrorCode.INTERNAL_SERVER_ERROR_CODE);
			return new ResponseEntity<Object>(errorHandelModel, HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	@Override
	public ResponseEntity<?> uploadTravelDetails(TraveldetailsReq travelDetailReq, MultipartFile file) {

		TravelDetail createTravelDetail;
		try {
			createTravelDetail = travelDetailsFactory.uploadTravelDetails(travelDetailReq, file);
			if (Objects.nonNull(createTravelDetail)) {
				return new ResponseEntity<Object>(createTravelDetail, HttpStatus.OK);
			}
			ErrorHandelModel errorHandelModel = createErrorHandelModel(TravelDetailsErrorCode.BAD_REQUEST_ERROR_MESSAGE,
					TravelDetailsErrorCode.TRAVEL_DETAILS_BAD_REQUEST, TravelDetailsErrorCode.BAD_REQUEST_ERROR_CODE);
			return new ResponseEntity<Object>(errorHandelModel, HttpStatus.BAD_REQUEST);

		} catch (Exception e) {
			logger.error("Inside TravelController uploadTravelDetails error:: "+e.getMessage());
			ErrorHandelModel errorHandelModel = createErrorHandelModel(
					TravelDetailsErrorCode.TRAVEL_DETAILS_NOT_CREATED_ERROR_MESSAGE,
					TravelDetailsErrorCode.TRAVEL_DETAILS_INTERNAL_SERVER_ERROR,
					TravelDetailsErrorCode.CREATE_TRAVEL_DETAILS_ERROR_CODE);
			return new ResponseEntity<Object>(errorHandelModel, HttpStatus.INTERNAL_SERVER_ERROR);

		}

	}

	private ErrorHandelModel createErrorHandelModel(String errorMessage, Integer statusCode, String errorCode) {
		ErrorHandelModel errorHandelModel = new ErrorHandelModel();
		errorHandelModel.setErrorMessage(errorMessage);
		errorHandelModel.setStatusCode(statusCode);
		errorHandelModel.setErrorCode(errorCode);
		return errorHandelModel;

	}

}
