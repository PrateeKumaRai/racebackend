package com.capgemini.travel.Dao;

import java.util.Date;
import java.util.List;

import com.capgemini.travel.model.TravelDetail;

public interface TravelDetailsCustomrepo {
	List<TravelDetail> findAllByDateAndLocation(Date start_date,Date return_date,String Location);
	
}
