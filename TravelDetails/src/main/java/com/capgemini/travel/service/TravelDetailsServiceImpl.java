 package com.capgemini.travel.service;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.capgemini.travel.Dao.TravelRepository;
import com.capgemini.travel.exception.ResourceNotFoundException;
import com.capgemini.travel.exception.TravelDetailsException;
import com.capgemini.travel.model.ImageModel;
import com.capgemini.travel.model.TravelDetail;
import com.capgemini.travel.model.TraveldetailsReq;
@Service("TravelDetailsService") 
public class TravelDetailsServiceImpl implements TravelDetailsService{
	private static final Logger logger = LoggerFactory.getLogger(TravelDetailsServiceImpl.class);
	
	@Autowired
	TravelRepository travelRepository;

	@Override
	public TravelDetail createTravelDetails(TravelDetail travelDetail) throws TravelDetailsException {
		TravelDetail travelDetailRes=null;
		logger.info("Inside TravelDetailsServiceImpl,create TravelDetail, Brfore DB call, Req:  "+travelDetail);
		try {
		 travelDetailRes=travelRepository.save(travelDetail);
		 logger.info("Inside TravelDetailsServiceImpl,create TravelDetail, after DB call, Req:  ");
		return travelDetailRes;
		
		}catch(Exception e) {
			throw new TravelDetailsException("ERROR_11006","TravelDetail not created Successful");
		}
		
		
	}

	@Override
	public List<TravelDetail> getAllTravelDeatils() {
		logger.info("Inside ravelDetailsServiceImpl,get All travel API, Req: before DB callS  ");
		return travelRepository.findAll();
	}

	@Override
	public List<TravelDetail> getAllTravelDeatilsByLocationAndDate(String date,String sourceLocation, String destLocation,
			String srcCountry,String destCountry) throws ParseException {
		
		logger.info("Inside TravelDetailsServiceImpl,get travel details api by date and location , Req:  "+date+" , "+destCountry+" , "+sourceLocation+" , "+destLocation);
		List<TravelDetail> travelDetails=null;
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

		Date travelDate=null;
		Date dateAfter=null;
		Date dateBefore=null;
		
		if(!(date.equals("null"))){
			travelDate = formatter.parse(date);
			long ltime=travelDate.getTime()+5*24*60*60*1000;
			long ltime1=travelDate.getTime()-5*24*60*60*1000;
			dateAfter	=new Date(ltime);
			dateBefore=new Date(ltime1);
		
		}
		if(date.equals("null")  && !(sourceLocation.equals("null")) && !(destLocation.equals("null") )) {
			Optional<List<TravelDetail>> optionalTravelDetails=travelRepository.findAllByLocation(sourceLocation, destLocation,srcCountry,destCountry);
			travelDetails=	validateOptionaltraveldetails(optionalTravelDetails);
		}
		else if(!(date.equals("null")) && !(sourceLocation.equals("null")) && !(destLocation.equals("null") )) {
			Optional<List<TravelDetail>> optionalTravelDetails= travelRepository.findAllByDateAndLocation(dateBefore,dateAfter, sourceLocation, destLocation,srcCountry,destCountry);
			travelDetails=	validateOptionaltraveldetails(optionalTravelDetails);
		}
	  logger.info("Inside TravelDetailsServiceImpl  getAllTravelDeatils, after DB call, Req:");
		/*
		 * if(Objects.isNull(travelDetails)) { new
		 * ResourceNotFoundException("TravelDetail"); }
		 */
		return travelDetails;
	}
	
	@Override
	public List<TravelDetail> getAllTravelDeatilsByDate(String date) throws ParseException {
		logger.info("Inside TravelDetailsServiceImpl,get travel details api by date , Req:  "+date);
		
		List<TravelDetail> travelDetails=null;
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

		Date travelDate=null;
		Date dateAfter=null;
		Date dateBefore=null;
		
		if(!(date.equals("null"))){
			travelDate = formatter.parse(date);
			long ltime=travelDate.getTime()+5*24*60*60*1000;
			long ltime1=travelDate.getTime()-5*24*60*60*1000;
			dateAfter	=new Date(ltime);
			dateBefore=new Date(ltime1);
		
		}
		if(!(date.equals("null"))) {
			Optional<List<TravelDetail>> optionalTravelDetails=travelRepository.findAllByDate(dateBefore, dateAfter);
			travelDetails=	validateOptionaltraveldetails(optionalTravelDetails);
		}
		
	  logger.info("Inside TravelDetailsServiceImpl  getAllTravelDeatilsByDate, after DB call, Req:");
		/*
		 * if(Objects.isNull(travelDetails)) { new
		 * ResourceNotFoundException("TravelDetail"); }
		 */
		return travelDetails;
	}
	
	@Override
	public List<TravelDetail> getAllTravelDeatilsByLocation(String sourceLocation, String destLocation,String srcCountry,String destCountry) {
		logger.info("Inside TravelDetailsServiceImpl,get travel details api by  location , Req:  "+sourceLocation+" , "+destLocation);
		List<TravelDetail> travelDetails=null;
		
		if(!(sourceLocation.equals("null")) && !(destLocation.equals("null") )) {
			
			Optional<List<TravelDetail>> optionalTravelDetails= travelRepository.findAllByLocation(sourceLocation, destLocation,srcCountry, destCountry);
			travelDetails=	validateOptionaltraveldetails(optionalTravelDetails);
			
		}
		
	  logger.info("Inside TravelDetailsServiceImpl  getAllTravelDeatilsByLocation, after DB call, Req:");
		
		return travelDetails;
	}
	

	private List<TravelDetail> validateOptionaltraveldetails(Optional<List<TravelDetail>> optionalTravelDetails) {
		List<TravelDetail> travelDetails=null;
		if(optionalTravelDetails.equals("null") || optionalTravelDetails==null || !(optionalTravelDetails.isPresent())) {
			return  travelDetails;
		}else {
	     return	optionalTravelDetails.get();	
		}
	}

	@Override
	public List<TravelDetail> getTravelDetailsById(int empid) throws ParseException {
		
		
		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
		Date date = new Date();

		String cuDate = formatter.format(date);

		Date currentDate = formatter.parse(cuDate);
		 
		List<TravelDetail> travelDetails=null;
		logger.info("Inside TravelDetailsServiceImpl,get travel details api by empId ,before DB call, Req: "  +empid);
		Optional<List<TravelDetail>> optionalTravelDetails=  travelRepository.findByEmployeeID(empid);
		travelDetails=	validateOptionaltraveldetails(optionalTravelDetails);
		
		return travelDetails;
		
	}

	@Override
	public TravelDetail updateTraveldetail(int employee_id, TravelDetail travelDetailReq) {
		TravelDetail travelDetail=null;
		TravelDetail updatedTraveldetail=null;
		logger.info("Inside TravelDetailsServiceImpl,update travel details by employee_id, Req: before DB call "  +employee_id);
		Optional<TravelDetail> optionalTravelDetail = travelRepository.findByEmployeeIdAndTravelId(employee_id,travelDetailReq.getTravel_id());
			
		if(optionalTravelDetail.equals("null") || optionalTravelDetail==null || !(optionalTravelDetail.isPresent())) {
			return  travelDetail;
		}else {
			travelDetail= optionalTravelDetail.get();	
		}
		
		
		travelDetail.setEmployee_id(employee_id);
		travelDetail.setSource_location(travelDetailReq.getSource_location());
		travelDetail.setDest_location(travelDetailReq.getDest_location());
		travelDetail.setSrc_country(travelDetailReq.getSrc_country());
		travelDetail.setDest_country(travelDetailReq.getDest_country());
		
		travelDetail.setStart_date(travelDetailReq.getStart_date());
		travelDetail.setReturn_date(travelDetailReq.getReturn_date());
		travelDetail.setMob_num(travelDetailReq.getMob_num());

		 updatedTraveldetail = travelRepository.saveAndFlush(travelDetail);
		
		return updatedTraveldetail;

	}

	@Override
	public String deleteTraveldetail(int empid,int travelId) {
		TravelDetail travelDetail=null;
		logger.info("Inside TravelDetailsServiceImpl,delete travel details api by id, Req: " +empid);
		Optional<TravelDetail> optionalTravelDetail = travelRepository.findByEmployeeIdAndTravelId(empid,travelId);
		
		if(optionalTravelDetail.equals("null") || optionalTravelDetail==null || !(optionalTravelDetail.isPresent())) {
			return null;
		}else {
			travelDetail= optionalTravelDetail.get();	
		}
		if(Objects.nonNull(travelDetail)) {
			logger.info("Inside TravelDetailsServiceImpl,delete travel details, brfore DB call" +empid);
		travelRepository.delete(travelDetail);
		return "sucessfully deleted!!";
		}
		return null;
	}

	@Override
	public TravelDetail uploadTravelDetails(TraveldetailsReq travelDetail, MultipartFile file) throws IOException, TravelDetailsException {
		TravelDetail travelDetailRes=null;
		
		System.out.println(file.getOriginalFilename());
		TravelDetail travelDetailDao=new TravelDetail();
		travelDetailDao.setEmployee_id(travelDetail.getEmployee_id());
		travelDetailDao.setSource_location(travelDetail.getSource_location());
		travelDetailDao.setDest_location(travelDetail.getDest_location());
		travelDetailDao.setSrc_country(travelDetail.getSrc_country());
		travelDetailDao.setDest_country(travelDetail.getDest_country());
		travelDetailDao.setStart_date(travelDetail.getStart_date());
		travelDetailDao.setReturn_date(travelDetail.getReturn_date());
		travelDetailDao.setImgName(file.getOriginalFilename());
		travelDetailDao.setImgPic(file.getBytes());
		travelDetailDao.setImgType(file.getContentType());
		travelDetailDao.setMob_num(travelDetail.getMob_num());
		
		try {
			 travelDetailRes=travelRepository.save(travelDetailDao);
			 logger.info("Inside TravelDetailsServiceImpl,create TravelDetail, after DB call, Req:  ");
			return travelDetailRes;
			
			}catch(Exception e) {
				throw new TravelDetailsException("ERROR_11006","TravelDetail not created Successful");
			}
		//return travelDetailDao;
	}

	
	

	

}
