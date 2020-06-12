package com.race.travel.model;

/**
 * <h1>TravelDetailsErrorCode class!</h1> This class is a constant class which
 * contains all constant field which is used for remove hard code value in our
 * code.
 * 
 * @author Sunil
 * @version 2.0
 * @since 2020-05-22
 */

public final class TravelDetailsErrorCode {
	private TravelDetailsErrorCode() {

	}

	public static final Integer TRAVEL_DETAILS_NOT_FOUND = 404;
	public static final Integer TRAVEL_DETAILS_INTERNAL_SERVER_ERROR = 500;
	public static final Integer TRAVEL_DETAILS_STATUS_OK = 200;
	public static final Integer TRAVEL_DETAILS_BAD_REQUEST = 400;

	public static final String GET_TRAVEL_DETAILS_BY_ID_ERROR_CODE = "ERROR_11005";
	public static final String GET_ALL_TRAVEL_DETAILS_ERROR_CODE = "ERROR_11007";
	public static final String CREATE_TRAVEL_DETAILS_ERROR_CODE = "ERROR_11006";
	public static final String DTAE_AND_LOCATION_ERROR_CODE = "ERROR_11002";
	public static final String TRAVEL_DETAILS_BY_DTAE_ERROR_CODE = "ERROR_11003";
	public static final String TRAVEL_DETAILS_BY_LOCATION_ERROR_CODE = "ERROR_11001";
	public static final String INTERNAL_SERVER_ERROR_CODE = "ERROR_11008";
	public static final String UPADTE_TRAVEL_DETAILS_ERROR_CODE = "ERROR_11004";
	public static final String DELETE_TRAVEL_DETAILS_ERROR_CODE = "ERROR_11001";
	public static final String BAD_REQUEST_ERROR_CODE = "ERROR_11400";
	public static final String TRAVEL_DETAIL_ERROR_CODE = "ERROR_11404";

	public static final String ERROR_MESSAGE = "TravelDetail not Found !!!";
	public static final String NOT_FOUND_BY_ID = "TravelDetail not Found by employee id!!!";
	public static final String GET_TRAVEL_DETAILS_BY_ID_ERROR_MESSAGE = "travelDetail not Found !!!";
	public static final String GET_ALL_TRAVEL_DETAILS_ERROR_MESSAGE = "there is no travelDetails Found !!!";
	public static final String CREATE_TRAVEL_DETAILS_ERROR_MESSAGE = "travelDetails not created !!!";
	public static final String DTAE_AND_LOCATION_ERROR_MESSAGE = "travelDetails not found By Date And Location!!";
	public static final String TRAVEL_DETAILS_BY_DTAE_ERROR_MESSAGE = "travelDetails not found By Date";
	public static final String TRAVEL_DETAILS_BY_LOCATION_ERROR_MESSAGE = "travelDetails not found By location !!!";
	public static final String INTERNAL_SERVER_ERROR_MESSAGE = "Internal Server Error !!!";
	public static final String UPADTE_TRAVEL_DETAILS_ERROR_MESSAGE = "travelDetails not Updated !!!";
	public static final String DELETE_TRAVEL_DETAILS_ERROR_MESSAGE = "travelDetails not deleted !!!";
	public static final String TRAVEL_DETAILS_NOT_CREATED_ERROR_MESSAGE = "travelDetail not created !!!";
	public static final String BAD_REQUEST_ERROR_MESSAGE = "TravelDetail not created Successful !!!";
	public static final String TRAVEL_DETAIL_NOT_FOUND_ERROR_MESSAGE = "travelDetail not found by travel id and employee id !!!";
	public static final String TRAVEL_DETAIL_DELETED = "travel detail sucessfully deleted!!";
	public static final String BAD_REQUEST_DATE_NULL = "date should not be null or empty !!!";
	public static final String VALID_DATE_REQUEST = "send proper date format string !!";
	public static final String BAD_REQUEST_DATE_AND_LOCATION_NULL = "date,location and country should not be null or empty !!!";
	public static final String BAD_REQUEST_LOCATION_NULL = "location and country should not be null or empty !!!";
	public static final String BAD_REQUEST_EMPID_NULL = "employee id should not be null or empty !!!";
	public static final String BAD_REQUEST_EMPID_AND_TRAVELID_NULL = "employee id and travel id should not be null or empty !!!";

}
