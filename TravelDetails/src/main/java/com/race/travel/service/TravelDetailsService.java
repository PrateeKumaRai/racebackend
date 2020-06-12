package com.race.travel.service;

import java.io.IOException;
import java.text.ParseException;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import com.race.travel.exception.TravelDetailsException;
import com.race.travel.model.TravelDetail;
import com.race.travel.model.TraveldetailsReq;

public interface TravelDetailsService {
	public ResponseEntity<?> createTravelDetails(TravelDetail travelDetail) ;

	public ResponseEntity<?> getAllTravelDeatils();

	public ResponseEntity<?> getAllTravelDeatilsByLocationAndDate(String date, String sourceLocation,
			String destLocation, String srcCountry, String destCountry) ;

	public ResponseEntity<?> getTravelDetailsById(Integer employee_id)  ;

	public ResponseEntity<?> updateTraveldetail(int employee_id, TravelDetail travelDetailReq) ;

	public ResponseEntity<?> deleteTraveldetail(Integer employee_id, Integer travelId);

	public ResponseEntity<?> getAllTravelDeatilsByDate(String date) ;

	public ResponseEntity<?> getAllTravelDeatilsByLocation(String sourceLocation, String destLocation,
			String srcCountry, String destCountry);

	public ResponseEntity<?> uploadTravelDetails(TraveldetailsReq travelDetail, MultipartFile file);

}
