package com.capgemini.travel.service;

import java.io.IOException;
import java.text.ParseException;
import java.util.List;

import javax.validation.Valid;

import org.springframework.web.multipart.MultipartFile;

import com.capgemini.travel.exception.TravelDetailsException;
import com.capgemini.travel.model.TravelDetail;
import com.capgemini.travel.model.TraveldetailsReq;

public interface TravelDetailsService {
	public TravelDetail createTravelDetails(TravelDetail travelDetail)throws ParseException, TravelDetailsException;

	public List<TravelDetail> getAllTravelDeatils();

	public List<TravelDetail> getAllTravelDeatilsByLocationAndDate(String date,String sourceLocation, String destLocation,
			String srcCountry,String destCountry) throws ParseException;

	public List<TravelDetail> getTravelDetailsById(int employee_id) throws ParseException;

	public TravelDetail updateTraveldetail(int employee_id, TravelDetail travelDetailReq);

	public String deleteTraveldetail(int employee_id, int travelId);

	public List<TravelDetail> getAllTravelDeatilsByDate(String date) throws ParseException;

	public List<TravelDetail> getAllTravelDeatilsByLocation(String sourceLocation, String destLocation,String srcCountry,String destCountry) ;

	TravelDetail uploadTravelDetails(TraveldetailsReq travelDetail, MultipartFile file) throws IOException, TravelDetailsException;


}
