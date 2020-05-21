package com.capgemini.travel.Dao;

import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.data.repository.NoRepositoryBean;

import com.capgemini.travel.model.TravelDetail;
@NoRepositoryBean
public class TravelDetailsRepositoryImpl implements TravelDetailsCustomrepo {
	 @PersistenceContext
	    EntityManager entityManager;

	@Override
	public List<TravelDetail> findAllByDateAndLocation(Date start_date, Date return_date, String location) {
		
		Query query = entityManager.createNativeQuery("SELECT * FROM travel_details td WHERE td.start_date=?1 AND td.return_date=?2 AND td.location=?3", TravelDetail.class);
        query.setParameter(1, start_date);
        query.setParameter(2, return_date);
        query.setParameter(3, location);
        return query.getResultList();
		
	}
	
	

}
