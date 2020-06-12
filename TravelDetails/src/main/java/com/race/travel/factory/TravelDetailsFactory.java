package com.race.travel.factory;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import com.race.travel.Dao.TravelRepository;
import com.race.travel.exception.TravelDetailsException;
import com.race.travel.model.DateModel;
import com.race.travel.model.TravelDetail;
import com.race.travel.model.TravelDetailsErrorCode;
import com.race.travel.model.TraveldetailsReq;
import com.race.travel.validator.TravelDetailsValidator;

/**
 * <h1>TravelDetailsFactory</h1>
 * <p>
 * The Travel Details Factory class simply receive the requset from service and
 * interacting with db to perform some business operation..
 * </p>
 * 
 * @author Sunil
 * @version 2.0
 * @since 2020-05-28
 */

@Component("TravelDetailsFactory")
public class TravelDetailsFactory {
	private static final Logger logger = LoggerFactory.getLogger(TravelDetailsFactory.class);

	@Autowired
	TravelRepository travelRepository;
	@Autowired
	TravelDetailsValidator travelDetailsValidator;

	public TravelDetail createTravelDetails(TravelDetail travelDetail) {
		TravelDetail travelDetailRes = null;
		logger.info("Inside TravelDetailsFactory,create TravelDetail, Brfore DB call");
		travelDetailRes = travelRepository.save(travelDetail);
		logger.info("Inside TravelDetailsFactory,create TravelDetail, after DB call");
		return travelDetailRes;

	}

	public List<TravelDetail> getAllTravelDeatils() throws TravelDetailsException {
		logger.info("Inside TravelDetailsFactory,get All travel API, Req: before DB callS  ");
		List<TravelDetail> travelDetails = null;
		try {
			travelDetails = travelRepository.findAll();
			logger.info("Inside TravelDetailsFactory,get All travel API, Req: after DB callS  ");
			return travelDetails;
		} catch (Exception e) {
			logger.error("Inside TravelDetails getAllTravelDeatils error:: "+e.getMessage());
			throw new TravelDetailsException(TravelDetailsErrorCode.INTERNAL_SERVER_ERROR_CODE,
					TravelDetailsErrorCode.INTERNAL_SERVER_ERROR_MESSAGE);
		}
	}

	public List<TravelDetail> getAllTravelDeatilsByLocationAndDate(String date, String sourceLocation,
			String destLocation, String srcCountry, String destCountry) throws ParseException, TravelDetailsException {

		logger.info("Inside TravelDetailsFactory,get travel details api by date and location");
		List<TravelDetail> travelDetails = null;

		DateModel dateModel = dateParsing(date);

		try {

			Optional<List<TravelDetail>> optionalTravelDetails = travelRepository.findAllByDateAndLocation(
					dateModel.getDateBefore(), dateModel.getDateAfter(), sourceLocation, destLocation, srcCountry,
					destCountry);
			travelDetails = validateOptionaltraveldetails(optionalTravelDetails);
			logger.info("Inside TravelDetailsFactory,get travel details api by date and location after DB call ");

		} catch (Exception e) {
			logger.error("Inside TravelDetailsFactory getAllTravelDeatilsByLocationAndDate error:: "+e.getMessage());
			throw new TravelDetailsException(TravelDetailsErrorCode.INTERNAL_SERVER_ERROR_CODE,
					TravelDetailsErrorCode.INTERNAL_SERVER_ERROR_MESSAGE);
		}

		logger.info("Inside TravelDetailsFactory  getAllTravelDeatils, after DB call, Req:");

		return travelDetails;
	}

	public List<TravelDetail> getAllTravelDeatilsByDate(String date) throws TravelDetailsException, ParseException {
		logger.info("Inside TravelDetailsFactory,get travel details api by date , Req:  ");

		List<TravelDetail> travelDetails = null;

		DateModel dateModel = dateParsing(date);

		try {

			Optional<List<TravelDetail>> optionalTravelDetails = travelRepository
					.findAllByDate(dateModel.getDateBefore(), dateModel.getDateAfter());
			travelDetails = validateOptionaltraveldetails(optionalTravelDetails);

		} catch (Exception e) {
			logger.error("Inside TravelDetailsFactory getAllTravelDeatilsByDate error:: "+e.getMessage());
			throw new TravelDetailsException(TravelDetailsErrorCode.INTERNAL_SERVER_ERROR_CODE,
					TravelDetailsErrorCode.INTERNAL_SERVER_ERROR_MESSAGE);
		}

		logger.info("Inside TravelDetailsFactory  getAllTravelDeatilsByDate, after DB call, Req:");

		return travelDetails;
	}

	
	public List<TravelDetail> getAllTravelDeatilsByLocation(String sourceLocation, String destLocation,
			String srcCountry, String destCountry) throws TravelDetailsException {
		logger.info("Inside TravelDetailsFactory,get travel details api by  location");
		List<TravelDetail> travelDetails = null;

		try {

			Optional<List<TravelDetail>> optionalTravelDetails = travelRepository.findAllByLocation(sourceLocation,
					destLocation, srcCountry, destCountry);
			travelDetails = validateOptionaltraveldetails(optionalTravelDetails);

		} catch (Exception e) {
			logger.error("Inside TravelDetailsFactory getAllTravelDeatilsByLocation error:: "+e.getMessage());
			throw new TravelDetailsException(TravelDetailsErrorCode.INTERNAL_SERVER_ERROR_CODE,
					TravelDetailsErrorCode.INTERNAL_SERVER_ERROR_MESSAGE);
		}

		logger.info("Inside TravelDetailsFactory getAllTravelDeatilsByLocation, after DB call");

		return travelDetails;
	}

	public List<TravelDetail> getTravelDetailsById(Integer empid) throws TravelDetailsException {

		List<TravelDetail> travelDetails = null;
		logger.info("Inside TravelDetailsFactory,get travel details api by empId ,before DB call");
		try {
			Optional<List<TravelDetail>> optionalTravelDetails = travelRepository.findByEmployeeID(empid);
			travelDetails = validateOptionaltraveldetails(optionalTravelDetails);
		} catch (Exception ex) {
			logger.error("Inside TravelDetailsFactory getTravelDetailsById error:: "+ex.getMessage());
			throw new TravelDetailsException(TravelDetailsErrorCode.INTERNAL_SERVER_ERROR_CODE,
					TravelDetailsErrorCode.INTERNAL_SERVER_ERROR_MESSAGE);
		}
		logger.info("Inside TravelDetailsFactory,get travel details api by empId ,after DB call");
		return travelDetails;

	}

	public TravelDetail updateTraveldetail(int employee_id, TravelDetail travelDetailReq)
			throws TravelDetailsException {
		TravelDetail travelDetail = null;

		logger.info(
				"Inside TravelDetailsFactory,update travel details by employee_id, Req: before DB call ");

		Optional<TravelDetail> optionalTravelDetail = travelRepository.findByEmployeeIdAndTravelId(employee_id,
				travelDetailReq.getTravel_id());

		if (!(optionalTravelDetail.isPresent())) {
			throw new TravelDetailsException(TravelDetailsErrorCode.TRAVEL_DETAIL_ERROR_CODE,
					TravelDetailsErrorCode.TRAVEL_DETAIL_NOT_FOUND_ERROR_MESSAGE);
		}
		travelDetail = optionalTravelDetail.get();
		travelDetail.setEmp_id(employee_id);
		travelDetail.setSrc_location(travelDetailReq.getSrc_location());
		travelDetail.setDest_location(travelDetailReq.getDest_location());
		travelDetail.setSrc_country(travelDetailReq.getSrc_country());
		travelDetail.setDest_country(travelDetailReq.getDest_country());

		travelDetail.setStart_date(travelDetailReq.getStart_date());
		travelDetail.setReturn_date(travelDetailReq.getReturn_date());
		travelDetail.setMob_num(travelDetailReq.getMob_num());

		TravelDetail updatedTraveldetail = travelRepository.saveAndFlush(travelDetail);

		logger.info("Inside TravelDetailsFactory,after DB call , travel details updated !!");

		return updatedTraveldetail;

	}

	public String deleteTraveldetail(Integer empid, Integer travelId) throws TravelDetailsException {
		String message = null;
		TravelDetail travelDetail = null;
		logger.info("Inside TravelDetailsFactory");
		Optional<TravelDetail> optionalTravelDetail = travelRepository.findByEmployeeIdAndTravelId(empid, travelId);
		if (!(optionalTravelDetail.isPresent())) {
			throw new TravelDetailsException(TravelDetailsErrorCode.TRAVEL_DETAIL_ERROR_CODE,
					TravelDetailsErrorCode.TRAVEL_DETAIL_NOT_FOUND_ERROR_MESSAGE);
		}

		travelDetail = optionalTravelDetail.get();
		if (Objects.nonNull(travelDetail)) {
			logger.info("Inside TravelDetailsFactory,delete travel details, brfore DB call" );
			travelRepository.delete(travelDetail);
			logger.info("Inside TravelDetailsFactory,delete travel details, after DB call");
			message = TravelDetailsErrorCode.TRAVEL_DETAIL_DELETED;
			return message;

		}
		return message;

	}

	public TravelDetail uploadTravelDetails(TraveldetailsReq travelDetail, MultipartFile file)
			throws IOException, TravelDetailsException {
		logger.info("Inside TravelDetailsFactory,create TravelDetail, before DB call, Req:  ");
		TravelDetail travelDetailRes = null;

		TravelDetail travelDetailDao = createTravelDetailDaoRequest(travelDetail, file);

		travelDetailRes = travelRepository.save(travelDetailDao);
		logger.info("Inside TravelDetailsFactory,create TravelDetail, after DB call, Req:  ");
		return travelDetailRes;

	}

	private TravelDetail createTravelDetailDaoRequest(TraveldetailsReq travelDetail, MultipartFile file)
			throws IOException {
		TravelDetail travelDetailDao = new TravelDetail();
		travelDetailDao.setEmp_id(travelDetail.getEmp_id());
		travelDetailDao.setSrc_location(travelDetail.getSrc_location());
		travelDetailDao.setDest_location(travelDetail.getDest_location());
		travelDetailDao.setSrc_country(travelDetail.getSrc_country());
		travelDetailDao.setDest_country(travelDetail.getDest_country());
		travelDetailDao.setStart_date(travelDetail.getStart_date());
		travelDetailDao.setReturn_date(travelDetail.getReturn_date());
		travelDetailDao.setImgName(file.getOriginalFilename());
		travelDetailDao.setImgPic(file.getBytes());
		travelDetailDao.setImgType(file.getContentType());
		travelDetailDao.setMob_num(travelDetail.getMob_num());
		return travelDetailDao;
	}

	private List<TravelDetail> validateOptionaltraveldetails(Optional<List<TravelDetail>> optionalTravelDetails) {
		List<TravelDetail> travelDetails = null;

		if (!(optionalTravelDetails.isPresent())) {
			return travelDetails;
		} else {
			return optionalTravelDetails.get();
		}
	}
	private DateModel dateParsing(String date) throws ParseException {
		DateModel dateModel = new DateModel();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

		Date travelDate = formatter.parse(date);
		long afterDate = travelDate.getTime() + 5 * 24 * 60 * 60 * 1000;
		long beforeDate = travelDate.getTime() - 5 * 24 * 60 * 60 * 1000;
		dateModel.setDateAfter(new Date(afterDate));
		dateModel.setDateBefore(new Date(beforeDate));

		return dateModel;
	}

}
