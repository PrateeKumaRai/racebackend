package com.race.travel.validator;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import com.race.travel.model.ErrorHandelModel;
import com.race.travel.model.TravelDetailsErrorCode;

@Component
public class TravelDetailsValidator {

	public ResponseEntity<Object> findByDateValidator() {

		ErrorHandelModel errorHandelModel = createErrorHandelModel(TravelDetailsErrorCode.BAD_REQUEST_DATE_NULL,
				TravelDetailsErrorCode.TRAVEL_DETAILS_BAD_REQUEST, TravelDetailsErrorCode.BAD_REQUEST_ERROR_CODE);

		return new ResponseEntity<Object>(errorHandelModel, HttpStatus.BAD_REQUEST);

	}

	public ResponseEntity<Object> dateParserValidate(Exception e) {

		ErrorHandelModel errorHandelModel = createErrorHandelModel(TravelDetailsErrorCode.VALID_DATE_REQUEST,
				TravelDetailsErrorCode.TRAVEL_DETAILS_BAD_REQUEST, TravelDetailsErrorCode.BAD_REQUEST_ERROR_CODE);
		return new ResponseEntity<Object>(errorHandelModel, HttpStatus.BAD_REQUEST);
	}

	public ResponseEntity<?> travelDetailsNotFoundByDate() {
		ErrorHandelModel errorHandelModel = createErrorHandelModel(
				TravelDetailsErrorCode.TRAVEL_DETAILS_BY_DTAE_ERROR_MESSAGE,
				TravelDetailsErrorCode.TRAVEL_DETAILS_NOT_FOUND,
				TravelDetailsErrorCode.TRAVEL_DETAILS_BY_DTAE_ERROR_CODE);
		return new ResponseEntity<Object>(errorHandelModel, HttpStatus.NOT_FOUND);
	}

	public ResponseEntity<?> findByDateAndLocationValidator() {

		ErrorHandelModel errorHandelModel = createErrorHandelModel(
				TravelDetailsErrorCode.BAD_REQUEST_DATE_AND_LOCATION_NULL,
				TravelDetailsErrorCode.TRAVEL_DETAILS_BAD_REQUEST, TravelDetailsErrorCode.BAD_REQUEST_ERROR_CODE);

		return new ResponseEntity<Object>(errorHandelModel, HttpStatus.BAD_REQUEST);
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

	public ResponseEntity<?> travelDetailsNotFoundByDateAndLocation() {
		ErrorHandelModel errorHandelModel = createErrorHandelModel(
				TravelDetailsErrorCode.DTAE_AND_LOCATION_ERROR_MESSAGE, TravelDetailsErrorCode.TRAVEL_DETAILS_NOT_FOUND,
				TravelDetailsErrorCode.DTAE_AND_LOCATION_ERROR_CODE);
		return new ResponseEntity<Object>(errorHandelModel, HttpStatus.NOT_FOUND);

	}

	public ResponseEntity<?> travelDetailsNotFound() {
		ErrorHandelModel errorHandelModel = createErrorHandelModel(
				TravelDetailsErrorCode.GET_ALL_TRAVEL_DETAILS_ERROR_MESSAGE,
				TravelDetailsErrorCode.TRAVEL_DETAILS_NOT_FOUND,
				TravelDetailsErrorCode.GET_ALL_TRAVEL_DETAILS_ERROR_CODE);
		return new ResponseEntity<Object>(errorHandelModel, HttpStatus.NOT_FOUND);
	}

	public ResponseEntity<?> travelDetailsNotFoundByLocation() {
		ErrorHandelModel errorHandelModel = createErrorHandelModel(
				TravelDetailsErrorCode.TRAVEL_DETAILS_BY_LOCATION_ERROR_MESSAGE,
				TravelDetailsErrorCode.TRAVEL_DETAILS_NOT_FOUND,
				TravelDetailsErrorCode.TRAVEL_DETAILS_BY_LOCATION_ERROR_CODE);
		return new ResponseEntity<Object>(errorHandelModel, HttpStatus.NOT_FOUND);
	}

	public ResponseEntity<?> validateLocations() {

		ErrorHandelModel errorHandelModel = createErrorHandelModel(TravelDetailsErrorCode.BAD_REQUEST_LOCATION_NULL,
				TravelDetailsErrorCode.TRAVEL_DETAILS_BAD_REQUEST, TravelDetailsErrorCode.BAD_REQUEST_ERROR_CODE);

		return new ResponseEntity<Object>(errorHandelModel, HttpStatus.BAD_REQUEST);
	}

	public ResponseEntity<?> travelDetailsByEmpid() {
		ErrorHandelModel errorHandelModel = createErrorHandelModel(TravelDetailsErrorCode.NOT_FOUND_BY_ID,
				TravelDetailsErrorCode.TRAVEL_DETAILS_NOT_FOUND,
				TravelDetailsErrorCode.GET_TRAVEL_DETAILS_BY_ID_ERROR_CODE);
		return new ResponseEntity<Object>(errorHandelModel, HttpStatus.NOT_FOUND);
	}

	public ResponseEntity<?> travelDetailsNotUpdatedByEmpidAndTravelId() {
		ErrorHandelModel errorHandelModel = createErrorHandelModel(
				TravelDetailsErrorCode.UPADTE_TRAVEL_DETAILS_ERROR_MESSAGE,
				TravelDetailsErrorCode.TRAVEL_DETAILS_NOT_FOUND, TravelDetailsErrorCode.UPADTE_TRAVEL_DETAILS_ERROR_CODE

		);

		return new ResponseEntity<Object>(errorHandelModel, HttpStatus.NOT_FOUND);

	}

	public ResponseEntity<?> validateEmpID() {

		ErrorHandelModel errorHandelModel = createErrorHandelModel(TravelDetailsErrorCode.BAD_REQUEST_EMPID_NULL,
				TravelDetailsErrorCode.TRAVEL_DETAILS_BAD_REQUEST, TravelDetailsErrorCode.BAD_REQUEST_ERROR_CODE);
		return new ResponseEntity<Object>(errorHandelModel, HttpStatus.BAD_REQUEST);

	}

	public ResponseEntity<?> validateEmpIdAndTravelId() {

		ErrorHandelModel errorHandelModel = createErrorHandelModel(
				TravelDetailsErrorCode.BAD_REQUEST_EMPID_AND_TRAVELID_NULL,
				TravelDetailsErrorCode.TRAVEL_DETAILS_BAD_REQUEST, TravelDetailsErrorCode.BAD_REQUEST_ERROR_CODE);

		return new ResponseEntity<Object>(errorHandelModel, HttpStatus.BAD_REQUEST);
	}

}
