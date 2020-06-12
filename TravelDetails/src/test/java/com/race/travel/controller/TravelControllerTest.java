package com.race.travel.controller;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.race.travel.Dao.TravelRepository;
import com.race.travel.model.DeleteModel;
import com.race.travel.model.ErrorHandelModel;
import com.race.travel.model.TravelDetail;
import com.race.travel.service.TravelDetailsServiceImpl;

@SpringBootTest
@RunWith(SpringRunner.class)
public class TravelControllerTest {

	final String baseUrl = "http://localhost:8081/travels/travelDetails";
	final String getAllTravelDetailsbUrl = "http://localhost:8086/travels/travelDetails";
	final String getAllTravelDetailsByLocationAndDatebUrl = "http://localhost:8082/travels/travelDetails/travels?date=2020-02-28&sourceLocation=Gujurat&destLocation=Assam&srcCountry=India&destCountry=India";
	final String getAllTravelDetailsDateParserErrorUrl = "http://localhost:8082/travels/travelDetails/travels?date=feb-28-2020&sourceLocation=Gujurat&destLocation=Assam&srcCountry=India&destCountry=India";
	final String getTravelDetailsByDateUrl = "http://localhost:8083/travels/travelDetails/dates?startDate=2020-02-28";
	final String getTravelDetailsByLocation = "http://localhost:8084/travels/travelDetails/locations?sourceLocation=Gujurat&destLocation=Assam&srcCountry=India&destCountry=India";
	final String getTravelDetailsByIdurl = "http://localhost:8085/travels/travelDetails/149673";
	final String updateTravelUrl = "http://localhost:8086/travels/travelDetails";
	final String deleteTravelDetailUrl = "http://localhost:8086/travels/travelDetails/149673/101";
	final String deleteTravelDetailBadUrl = "http://localhost:8086/travels/travelDetails/149673/0";
	final String getTravelDetailsByIdBadurl = "http://localhost:8085/travels/travelDetails/0";
	final String getTravelDetailsByLocationBadReq = "http://localhost:8084/travels/travelDetails/locations?sourceLocation=&destLocation=Assam&srcCountry=India&destCountry=";
	final String getTravelDetailsByDateBadUrl = "http://localhost:8083/travels/travelDetails/dates?startDate=";
	final String getAllTravelDetailsByLocationAndDatebBadUrl = "http://localhost:8082/travels/travelDetails/travels?date=&sourceLocation=&destLocation=Assam&srcCountry=India&destCountry=India";
	final String getTravelDetailsByDateParseErrorUrl = "http://localhost:8083/travels/travelDetails/dates?startDate=feb-28-2020";
	final String uploadTravelDetailsUrl = "http://localhost:8086/travels/travelDetails/travels";

	@Autowired
	public TravelDetailsServiceImpl travelDetailsServiceImplMock;

	private MockMvc mockMvc;

	@Autowired
	private WebApplicationContext context;

	@Autowired
	ObjectMapper objectMapper;

	@MockBean
	private TravelRepository travelRepository;

	@Before
	public void setup() throws Exception {
		mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
	}

	@Test
	public void uploadTravelDetailsTest() throws Exception {

		TravelDetail travelDetailsRes = null;
		TravelDetail travelDetail1 = travelDetailsDTO1();

		MockMultipartFile jsonFile = new MockMultipartFile("traveldetail", "", "application/json",
				"{\"emp_id\": \"12345\",\"start_date\": \"2020-03-21\",\"return_date\": \"2020-03-23\",\"src_country\": \"India\",\"dest_country\": \"India\",\"src_location\": \"Gujurat\",\"dest_location\": \"Assam\",\"address\": \"Ahmd\",\"travel_type\": \"null\",\"mob_num\": \"6545676543\"}"
						.getBytes());
		MockMultipartFile uploadFile = new MockMultipartFile("myfile", new byte[1]);

		when(travelRepository.save(Mockito.any())).thenReturn(travelDetail1);
		MvcResult MvcResult = mockMvc.perform(MockMvcRequestBuilders.multipart(uploadTravelDetailsUrl).file(jsonFile)
				.file(uploadFile).characterEncoding("UTF-8")).andExpect(status().isOk()).andReturn();

		String result = MvcResult.getResponse().getContentAsString();
		int status = MvcResult.getResponse().getStatus();

		travelDetailsRes = objectMapper.readValue(result, TravelDetail.class);
		assertNotNull(travelDetailsRes);
		assertEquals(200, status);
	}

	@Test
	public void uploadTravelDetails_BadRequest_Test() throws Exception {

		ErrorHandelModel errorHandelModel = null;
		TravelDetail travelDetail1 = travelDetailsDTO1();
		MockMultipartFile jsonFile = new MockMultipartFile("traveldetail", "", "application/json",
				"{\"emp_id\": \"12345\",\"start_date\": \"2020-03-21\",\"return_date\": \"2020-03-23\",\"src_country\":null,\"dest_country\": \"India\",\"src_location\": \"Gujurat\",\"dest_location\": \"Assam\",\"address\": \"Ahmd\",\"travel_type\": \"null\",\"mob_num\": \"6545676543\"}"
						.getBytes());
		MockMultipartFile uploadFile = new MockMultipartFile("myfile", new byte[1]);

		when(travelRepository.save(Mockito.any())).thenReturn(travelDetail1);
		MvcResult MvcResult = mockMvc.perform(MockMvcRequestBuilders.multipart(uploadTravelDetailsUrl).file(jsonFile)
				.file(uploadFile).characterEncoding("UTF-8")).andExpect(status().isBadRequest()).andReturn();

		String result = MvcResult.getResponse().getContentAsString();
		int status = MvcResult.getResponse().getStatus();

		errorHandelModel = objectMapper.readValue(result, ErrorHandelModel.class);
		assertNotNull(errorHandelModel);
		assertEquals(400, status);
	}

	@Test
	public void uploadTravelDetails_InternalServer_Test() throws Exception {

		ErrorHandelModel errorHandelModel = null;

		MockMultipartFile jsonFile = new MockMultipartFile("traveldetail", "", "application/json",
				"{\"emp_id\": \"12345\",\"start_date\": \"2020-03-21\",\"return_date\": \"2020-03-23\",\"src_country\": \"India\",\"dest_country\": \"India\",\"src_location\": \"Gujurat\",\"dest_location\": \"Assam\",\"address\": \"Ahmd\",\"travel_type\": \"null\",\"mob_num\": \"6545676543\"}"
						.getBytes());
		MockMultipartFile uploadFile = new MockMultipartFile("myfile", new byte[1]);

		when(travelRepository.save(Mockito.any())).thenThrow(new NullPointerException());

		MvcResult MvcResult = mockMvc.perform(MockMvcRequestBuilders.multipart(uploadTravelDetailsUrl).file(jsonFile)
				.file(uploadFile).characterEncoding("UTF-8")).andExpect(status().isInternalServerError()).andReturn();

		String result = MvcResult.getResponse().getContentAsString();
		int status = MvcResult.getResponse().getStatus();

		errorHandelModel = objectMapper.readValue(result, ErrorHandelModel.class);
		assertNotNull(errorHandelModel);
		assertEquals(500, status);
	}

	@Test
	public void uploadTravelDetails_Data_Not_Saved_Test() throws Exception {

		ErrorHandelModel errorHandelModel = null;

		TravelDetail travelDetailsRes = null;

		MockMultipartFile jsonFile = new MockMultipartFile("traveldetail", "", "application/json",
				"{\"emp_id\": \"12345\",\"start_date\": \"2020-03-21\",\"return_date\": \"2020-03-23\",\"src_country\": \"India\",\"dest_country\": \"India\",\"src_location\": \"Gujurat\",\"dest_location\": \"Assam\",\"address\": \"Ahmd\",\"travel_type\": \"null\",\"mob_num\": \"6545676543\"}"
						.getBytes());
		MockMultipartFile uploadFile = new MockMultipartFile("myfile", new byte[1]);

		when(travelRepository.save(Mockito.any())).thenReturn(travelDetailsRes);

		MvcResult MvcResult = mockMvc.perform(MockMvcRequestBuilders.multipart(uploadTravelDetailsUrl).file(jsonFile)
				.file(uploadFile).characterEncoding("UTF-8")).andExpect(status().isBadRequest()).andReturn();

		String result = MvcResult.getResponse().getContentAsString();
		int status = MvcResult.getResponse().getStatus();

		errorHandelModel = objectMapper.readValue(result, ErrorHandelModel.class);
		assertNotNull(errorHandelModel);
		assertEquals(400, status);
	}

	@Test
	public void createTravelDetailsTest() throws Exception {

		TravelDetail travelDetailsRes = null;
		TravelDetail travelDetail = travelDetailsDTO();
		TravelDetail travelDetail1 = travelDetailsDTO1();
		when(travelRepository.save(Mockito.any())).thenReturn(travelDetail1);
		MvcResult MvcResult = mockMvc.perform(post(baseUrl).contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(travelDetail))).andExpect(status().isOk()).andReturn();

		String result = MvcResult.getResponse().getContentAsString();
		int status = MvcResult.getResponse().getStatus();

		travelDetailsRes = objectMapper.readValue(result, TravelDetail.class);
		assertNotNull(travelDetailsRes);
		assertEquals(200, status);
	}

	@Test
	public void createTravelDetails_BadRequest_Test() throws Exception {

		TravelDetail travelDetail = travelDetailsDTO();
		travelDetail.setSrc_location(null);
		ErrorHandelModel errorHandelModel = null;

		MvcResult MvcResult = mockMvc
				.perform(post(baseUrl).contentType(MediaType.APPLICATION_JSON)
						.content(objectMapper.writeValueAsString(travelDetail)))
				.andExpect(status().isBadRequest()).andReturn();

		String result = MvcResult.getResponse().getContentAsString();
		int status = MvcResult.getResponse().getStatus();

		errorHandelModel = objectMapper.readValue(result, ErrorHandelModel.class);
		assertNotNull(errorHandelModel);
		assertEquals(400, status);
	}

	@Test
	public void createTravelDetails_BadDbResponse_Test() throws Exception {
		TravelDetail travelDetail = travelDetailsDTO();
		TravelDetail travelDetailsRes = null;
		ErrorHandelModel errorHandelModel = null;

		when(travelRepository.save(Mockito.any())).thenReturn(travelDetailsRes);

		MvcResult MvcResult = mockMvc
				.perform(post(baseUrl).contentType(MediaType.APPLICATION_JSON)
						.content(objectMapper.writeValueAsString(travelDetail)))
				.andExpect(status().isBadRequest()).andReturn();

		String result = MvcResult.getResponse().getContentAsString();
		int status = MvcResult.getResponse().getStatus();

		errorHandelModel = objectMapper.readValue(result, ErrorHandelModel.class);
		assertNotNull(errorHandelModel);
		assertEquals(400, status);
	}

	@Test
	public void createTravelDetails_InternalError_Test() throws Exception {

		TravelDetail travelDetail = travelDetailsDTO();
		ErrorHandelModel errorHandelModel = null;

		when(travelRepository.save(Mockito.any())).thenThrow(new NullPointerException());

		MvcResult MvcResult = mockMvc
				.perform(post(baseUrl).contentType(MediaType.APPLICATION_JSON)
						.content(objectMapper.writeValueAsString(travelDetail)))
				.andExpect(status().isInternalServerError()).andReturn();

		String result = MvcResult.getResponse().getContentAsString();
		int status = MvcResult.getResponse().getStatus();

		errorHandelModel = objectMapper.readValue(result, ErrorHandelModel.class);
		assertNotNull(errorHandelModel);
		assertEquals(500, status);
	}

	@Test
	public void getAllTravelDetailsTest() throws Exception {

		List<TravelDetail> travelDetailsRes = null;

		List<TravelDetail> travelDetails = travelDetailsResponseList();

		when(travelRepository.findAll()).thenReturn(travelDetails);
		MvcResult MvcResult = mockMvc.perform(get(getAllTravelDetailsbUrl).contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk()).andReturn();

		String result = MvcResult.getResponse().getContentAsString();
		int status = MvcResult.getResponse().getStatus();

		travelDetailsRes = Arrays.asList(objectMapper.readValue(result, TravelDetail[].class));

		assertNotNull(travelDetailsRes);
		assertEquals(200, status);
	}

	
	@Test
	public void getAllTravelDetails_InternalServer_error_Test() throws Exception {
		ErrorHandelModel errorHandelModel = null;

		when(travelRepository.findAll()).thenThrow(new NullPointerException());
		MvcResult MvcResult = mockMvc.perform(get(getAllTravelDetailsbUrl).contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isInternalServerError()).andReturn();

		String result = MvcResult.getResponse().getContentAsString();
		int status = MvcResult.getResponse().getStatus();

		errorHandelModel = objectMapper.readValue(result, ErrorHandelModel.class);

		assertNotNull(errorHandelModel);
		assertEquals(500, status);
	}

	@Test
	public void getAllTravelDetails_Not_Found_error_Test() throws Exception {
		ErrorHandelModel errorHandelModel = null;

		List<TravelDetail> travelDetailsRes = new ArrayList<TravelDetail>();

		when(travelRepository.findAll()).thenReturn(travelDetailsRes);
		MvcResult MvcResult = mockMvc.perform(get(getAllTravelDetailsbUrl).contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isNotFound()).andReturn();

		String result = MvcResult.getResponse().getContentAsString();
		int status = MvcResult.getResponse().getStatus();

		errorHandelModel = objectMapper.readValue(result, ErrorHandelModel.class);

		assertNotNull(errorHandelModel);
		assertEquals(404, status);
	}

	@Test
	public void getAllTravelDeatilsByLocationAndDate_Test() throws Exception {

		List<TravelDetail> travelDetailsRes = null;

		List<TravelDetail> travelDetails = travelDetailsResponseList();

		when(travelRepository.findAllByDateAndLocation(Mockito.any(), Mockito.any(), Mockito.anyString(),
				Mockito.anyString(), Mockito.anyString(), Mockito.anyString())).thenReturn(Optional.of(travelDetails));
		MvcResult MvcResult = mockMvc
				.perform(get(getAllTravelDetailsByLocationAndDatebUrl).contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk()).andReturn();

		String result = MvcResult.getResponse().getContentAsString();
		int status = MvcResult.getResponse().getStatus();

		travelDetailsRes = Arrays.asList(objectMapper.readValue(result, TravelDetail[].class));

		assertNotNull(travelDetailsRes);
		assertEquals(200, status);
	}

	@Test
	public void getAllTravelDeatilsByLocationAndDate_DateParserError_Test() throws Exception {
		ErrorHandelModel errorHandelModel = null;

		List<TravelDetail> travelDetails = travelDetailsResponseList();

		when(travelRepository.findAllByDateAndLocation(Mockito.any(), Mockito.any(), Mockito.anyString(),
				Mockito.anyString(), Mockito.anyString(), Mockito.anyString())).thenReturn(Optional.of(travelDetails));
		MvcResult MvcResult = mockMvc
				.perform(get(getAllTravelDetailsDateParserErrorUrl).contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isBadRequest()).andReturn();

		String result = MvcResult.getResponse().getContentAsString();
		int status = MvcResult.getResponse().getStatus();

		errorHandelModel = objectMapper.readValue(result, ErrorHandelModel.class);

		assertNotNull(errorHandelModel);
		assertEquals(400, status);
	}

	@Test
	public void getAllTravelDeatilsByLocationAndDate_Bad_Req_Test() throws Exception {
		ErrorHandelModel errorHandelModel = null;

		MvcResult MvcResult = mockMvc
				.perform(get(getAllTravelDetailsByLocationAndDatebBadUrl).contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isBadRequest()).andReturn();

		String result = MvcResult.getResponse().getContentAsString();
		int status = MvcResult.getResponse().getStatus();

		errorHandelModel = objectMapper.readValue(result, ErrorHandelModel.class);

		assertNotNull(errorHandelModel);
		assertEquals(400, status);
	}

	@Test
	public void getAllTravelDeatilsByLocationAndDate_InternalServer_error_Test() throws Exception {
		ErrorHandelModel errorHandelModel = null;

		when(travelRepository.findAllByDateAndLocation(Mockito.any(), Mockito.any(), Mockito.anyString(),
				Mockito.anyString(), Mockito.anyString(), Mockito.anyString())).thenThrow(new NullPointerException());
		MvcResult MvcResult = mockMvc
				.perform(get(getAllTravelDetailsByLocationAndDatebUrl).contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isInternalServerError()).andReturn();

		String result = MvcResult.getResponse().getContentAsString();
		int status = MvcResult.getResponse().getStatus();

		errorHandelModel = objectMapper.readValue(result, ErrorHandelModel.class);

		assertNotNull(errorHandelModel);
		assertEquals(500, status);
	}

	@Test
	public void getAllTravelDeatilsByLocationAndDate_Not_Found_error_Test() throws Exception {
		ErrorHandelModel errorHandelModel = null;

		when(travelRepository.findAllByDateAndLocation(Mockito.any(), Mockito.any(), Mockito.anyString(),
				Mockito.anyString(), Mockito.anyString(), Mockito.anyString())).thenReturn(Optional.empty());
		MvcResult MvcResult = mockMvc
				.perform(get(getAllTravelDetailsByLocationAndDatebUrl).contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isNotFound()).andReturn();

		String result = MvcResult.getResponse().getContentAsString();
		int status = MvcResult.getResponse().getStatus();

		errorHandelModel = objectMapper.readValue(result, ErrorHandelModel.class);

		assertNotNull(errorHandelModel);
		assertEquals(404, status);
	}

	@Test
	public void getAllTravelDeatilsByDate_Test() throws Exception {

		List<TravelDetail> travelDetailsRes = null;

		List<TravelDetail> travelDetails = travelDetailsResponseList();

		when(travelRepository.findAllByDate(Mockito.any(), Mockito.any())).thenReturn(Optional.of(travelDetails));
		MvcResult MvcResult = mockMvc.perform(get(getTravelDetailsByDateUrl).contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk()).andReturn();

		String result = MvcResult.getResponse().getContentAsString();
		int status = MvcResult.getResponse().getStatus();

		travelDetailsRes = Arrays.asList(objectMapper.readValue(result, TravelDetail[].class));

		assertNotNull(travelDetailsRes);
		assertEquals(200, status);
	}

	@Test
	public void getAllTravelDeatilsByDateParseError_Test() throws Exception {
		ErrorHandelModel errorHandelModel = null;

		List<TravelDetail> travelDetails = travelDetailsResponseList();

		when(travelRepository.findAllByDate(Mockito.any(), Mockito.any())).thenReturn(Optional.of(travelDetails));
		MvcResult MvcResult = mockMvc
				.perform(get(getTravelDetailsByDateParseErrorUrl).contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isBadRequest()).andReturn();

		String result = MvcResult.getResponse().getContentAsString();
		int status = MvcResult.getResponse().getStatus();

		errorHandelModel = objectMapper.readValue(result, ErrorHandelModel.class);

		assertNotNull(errorHandelModel);
		assertEquals(400, status);
	}

	@Test
	public void getAllTravelDeatilsByDate_Bad_Req_Test() throws Exception {
		ErrorHandelModel errorHandelModel = null;

		List<TravelDetail> travelDetails = travelDetailsResponseList();

		when(travelRepository.findAllByDate(Mockito.any(), Mockito.any())).thenReturn(Optional.of(travelDetails));
		MvcResult MvcResult = mockMvc.perform(get(getTravelDetailsByDateBadUrl).contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isBadRequest()).andReturn();

		String result = MvcResult.getResponse().getContentAsString();
		int status = MvcResult.getResponse().getStatus();

		errorHandelModel = objectMapper.readValue(result, ErrorHandelModel.class);

		assertNotNull(errorHandelModel);
		assertEquals(400, status);
	}

	@Test
	public void getAllTravelDeatilsByDate_InternalServer_error_Test() throws Exception {

		ErrorHandelModel errorHandelModel = null;

		when(travelRepository.findAllByDate(Mockito.any(), Mockito.any())).thenThrow(new NullPointerException());
		MvcResult MvcResult = mockMvc.perform(get(getTravelDetailsByDateUrl).contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isInternalServerError()).andReturn();
		String result = MvcResult.getResponse().getContentAsString();
		int status = MvcResult.getResponse().getStatus();

		errorHandelModel = objectMapper.readValue(result, ErrorHandelModel.class);

		assertNotNull(errorHandelModel);
		assertEquals(500, status);
	}

	@Test
	public void getAllTravelDeatilsByDate_Not_Found_error_Test() throws Exception {
		ErrorHandelModel errorHandelModel = null;

		when(travelRepository.findAllByDate(Mockito.any(), Mockito.any())).thenReturn(Optional.empty());
		MvcResult MvcResult = mockMvc.perform(get(getTravelDetailsByDateUrl).contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isNotFound()).andReturn();

		String result = MvcResult.getResponse().getContentAsString();
		int status = MvcResult.getResponse().getStatus();

		errorHandelModel = objectMapper.readValue(result, ErrorHandelModel.class);

		assertNotNull(errorHandelModel);
		assertEquals(404, status);
	}

	@Test
	public void getTravelDeatilsByLocation_Test() throws Exception {

		List<TravelDetail> travelDetailsRes = null;

		List<TravelDetail> travelDetails = travelDetailsResponseList();

		when(travelRepository.findAllByLocation(Mockito.anyString(), Mockito.anyString(), Mockito.anyString(),
				Mockito.anyString())).thenReturn(Optional.of(travelDetails));
		MvcResult MvcResult = mockMvc.perform(get(getTravelDetailsByLocation).contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk()).andReturn();

		String result = MvcResult.getResponse().getContentAsString();
		int status = MvcResult.getResponse().getStatus();

		travelDetailsRes = Arrays.asList(objectMapper.readValue(result, TravelDetail[].class));

		assertNotNull(travelDetailsRes);
		assertEquals(200, status);
	}

	@Test
	public void getTravelDeatilsByLocation_Bad_Req_Test() throws Exception {
		ErrorHandelModel errorHandelModel = null;

		MvcResult MvcResult = mockMvc
				.perform(get(getTravelDetailsByLocationBadReq).contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isBadRequest()).andReturn();

		String result = MvcResult.getResponse().getContentAsString();
		int status = MvcResult.getResponse().getStatus();

		errorHandelModel = objectMapper.readValue(result, ErrorHandelModel.class);

		assertNotNull(errorHandelModel);
		assertEquals(400, status);
	}

	@Test
	public void getTravelDeatilsByLocation_InternalServer_error_Test() throws Exception {
		ErrorHandelModel errorHandelModel = null;

		when(travelRepository.findAllByLocation(Mockito.anyString(), Mockito.anyString(), Mockito.anyString(),
				Mockito.anyString())).thenThrow(new NullPointerException());
		MvcResult MvcResult = mockMvc.perform(get(getTravelDetailsByLocation).contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isInternalServerError()).andReturn();
		String result = MvcResult.getResponse().getContentAsString();
		int status = MvcResult.getResponse().getStatus();

		errorHandelModel = objectMapper.readValue(result, ErrorHandelModel.class);

		assertNotNull(errorHandelModel);
		assertEquals(500, status);
	}

	@Test
	public void getTravelDeatilsByLocation_Not_Found_error_Test() throws Exception {
		ErrorHandelModel errorHandelModel = null;

		when(travelRepository.findAllByLocation(Mockito.anyString(), Mockito.anyString(), Mockito.anyString(),
				Mockito.anyString())).thenReturn(Optional.empty());
		MvcResult MvcResult = mockMvc.perform(get(getTravelDetailsByLocation).contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isNotFound()).andReturn();
		String result = MvcResult.getResponse().getContentAsString();
		int status = MvcResult.getResponse().getStatus();

		errorHandelModel = objectMapper.readValue(result, ErrorHandelModel.class);

		assertNotNull(errorHandelModel);
		assertEquals(404, status);
	}

	@Test
	public void getTravelDeatilsByEmpId_Test() throws Exception {

		List<TravelDetail> travelDetailsRes = null;

		List<TravelDetail> travelDetails = travelDetailsResponseList();

		when(travelRepository.findByEmployeeID(Mockito.anyInt())).thenReturn(Optional.of(travelDetails));
		MvcResult MvcResult = mockMvc.perform(get(getTravelDetailsByIdurl).contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk()).andReturn();

		String result = MvcResult.getResponse().getContentAsString();
		int status = MvcResult.getResponse().getStatus();

		travelDetailsRes = Arrays.asList(objectMapper.readValue(result, TravelDetail[].class));

		assertNotNull(travelDetailsRes);
		assertEquals(200, status);
	}

	@Test

	public void getTravelDeatilsByEmpId_InternalServer_error_Test() throws Exception {
		ErrorHandelModel errorHandelModel = null;

		when(travelRepository.findByEmployeeID(Mockito.anyInt())).thenThrow(new NullPointerException());
		MvcResult MvcResult = mockMvc.perform(get(getTravelDetailsByIdurl).contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isInternalServerError()).andReturn();

		String result = MvcResult.getResponse().getContentAsString();
		int status = MvcResult.getResponse().getStatus();

		errorHandelModel = objectMapper.readValue(result, ErrorHandelModel.class);

		assertNotNull(errorHandelModel);
		assertEquals(500, status);

	}

	@Test
	public void getTravelDeatilsByEmpId_data_Not_Found_error_Test() throws Exception {
		ErrorHandelModel errorHandelModel = null;

		when(travelRepository.findByEmployeeID(Mockito.anyInt())).thenReturn(Optional.empty());
		MvcResult MvcResult = mockMvc.perform(get(getTravelDetailsByIdurl).contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isNotFound()).andReturn();

		String result = MvcResult.getResponse().getContentAsString();
		int status = MvcResult.getResponse().getStatus();

		errorHandelModel = objectMapper.readValue(result, ErrorHandelModel.class);

		assertNotNull(errorHandelModel);
		assertEquals(404, status);

	}

	@Test
	public void getTravelDeatilsByEmpId_Null_Test() throws Exception {
		ErrorHandelModel errorHandelModel = null;

		MvcResult MvcResult = mockMvc.perform(get(getTravelDetailsByIdBadurl).contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isBadRequest()).andReturn();

		String result = MvcResult.getResponse().getContentAsString();
		int status = MvcResult.getResponse().getStatus();

		errorHandelModel = objectMapper.readValue(result, ErrorHandelModel.class);

		assertNotNull(errorHandelModel);
		assertEquals(400, status);
	}

	@Test
	public void updateTravelDeatilsByEmpId_TravelId_Test() throws Exception {

		TravelDetail travelDetailsRes = null;

		TravelDetail travelDetail1 = travelDetailsDTO1();
		travelDetail1.setTravel_id(35266);
		TravelDetail updateTravel = travelDetailsRes();

		when(travelRepository.findByEmployeeIdAndTravelId(Mockito.anyInt(), Mockito.anyInt()))
				.thenReturn(Optional.of(updateTravel));
		when(travelRepository.saveAndFlush(Mockito.any())).thenReturn(updateTravel);
		MvcResult MvcResult = mockMvc
				.perform(put(updateTravelUrl).contentType(MediaType.APPLICATION_JSON)
						.content(objectMapper.writeValueAsString(travelDetail1)))
				.andExpect(status().isOk()).andReturn();

		String result = MvcResult.getResponse().getContentAsString();
		int status = MvcResult.getResponse().getStatus();

		travelDetailsRes = objectMapper.readValue(result, TravelDetail.class);

		assertNotNull(travelDetailsRes);
		assertEquals(200, status);
	}

	@Test
	public void updateTravelDeatils_Not_Done_By_EmpId_TravelIdbbb_Test() throws Exception {
		ErrorHandelModel errorHandelModel = null;
		TravelDetail travelDetailsRes = null;

		TravelDetail travelDetail1 = travelDetailsDTO1();
		travelDetail1.setTravel_id(35266);
		TravelDetail updateTravel = travelDetailsRes();

		when(travelRepository.findByEmployeeIdAndTravelId(Mockito.anyInt(), Mockito.anyInt()))
				.thenReturn(Optional.of(updateTravel));
		when(travelRepository.saveAndFlush(Mockito.any())).thenReturn(travelDetailsRes);
		MvcResult MvcResult = mockMvc
				.perform(put(updateTravelUrl).contentType(MediaType.APPLICATION_JSON)
						.content(objectMapper.writeValueAsString(travelDetail1)))
				.andExpect(status().isNotFound()).andReturn();

		String result = MvcResult.getResponse().getContentAsString();
		int status = MvcResult.getResponse().getStatus();

		errorHandelModel = objectMapper.readValue(result, ErrorHandelModel.class);

		assertNotNull(errorHandelModel);
		assertEquals(404, status);
	}

	@Test
	public void updateTravelDeatilsByEmpId_Null_Test() throws Exception {

		ErrorHandelModel errorHandelModel = null;

		TravelDetail travelDetail1 = travelDetailsDTO1();
		travelDetail1.setTravel_id(0);
		travelDetail1.setEmp_id(0);

		MvcResult MvcResult = mockMvc
				.perform(put(updateTravelUrl).contentType(MediaType.APPLICATION_JSON)
						.content(objectMapper.writeValueAsString(travelDetail1)))
				.andExpect(status().isBadRequest()).andReturn();

		String result = MvcResult.getResponse().getContentAsString();
		int status = MvcResult.getResponse().getStatus();

		errorHandelModel = objectMapper.readValue(result, ErrorHandelModel.class);

		assertNotNull(errorHandelModel);
		assertEquals(400, status);
	}

	@Test
	public void updateTravelDeatils_Dest_Country_Null_Test() throws Exception {

		ErrorHandelModel errorHandelModel = null;

		TravelDetail travelDetail1 = travelDetailsDTO1();
		travelDetail1.setTravel_id(354);
		travelDetail1.setDest_country(null);

		MvcResult MvcResult = mockMvc
				.perform(put(updateTravelUrl).contentType(MediaType.APPLICATION_JSON)
						.content(objectMapper.writeValueAsString(travelDetail1)))
				.andExpect(status().isBadRequest()).andReturn();

		String result = MvcResult.getResponse().getContentAsString();
		int status = MvcResult.getResponse().getStatus();

		errorHandelModel = objectMapper.readValue(result, ErrorHandelModel.class);

		assertNotNull(errorHandelModel);
		assertEquals(400, status);
	}

	@Test
	public void updateTravelDeatils_InternalServer_error_Test() throws Exception {

		ErrorHandelModel errorHandelModel = null;
		TravelDetail travelDetail1 = travelDetailsDTO1();
		travelDetail1.setTravel_id(35266);

		when(travelRepository.findByEmployeeIdAndTravelId(Mockito.anyInt(), Mockito.anyInt())).thenReturn(null);

		MvcResult MvcResult = mockMvc
				.perform(put(updateTravelUrl).contentType(MediaType.APPLICATION_JSON)
						.content(objectMapper.writeValueAsString(travelDetail1)))
				.andExpect(status().isInternalServerError()).andReturn();

		String result = MvcResult.getResponse().getContentAsString();
		int status = MvcResult.getResponse().getStatus();

		errorHandelModel = objectMapper.readValue(result, ErrorHandelModel.class);

		assertNotNull(errorHandelModel);
		assertEquals(500, status);
	}

	@Test
	public void updateTravelDeatils_NotFound_By_Id_AND_TravelId_Test() throws Exception {
		ErrorHandelModel errorHandelModel = null;
		TravelDetail travelDetail1 = travelDetailsDTO1();
		travelDetail1.setTravel_id(35266);
		when(travelRepository.findByEmployeeIdAndTravelId(Mockito.anyInt(), Mockito.anyInt()))
				.thenReturn(Optional.empty());

		MvcResult MvcResult = mockMvc
				.perform(put(updateTravelUrl).contentType(MediaType.APPLICATION_JSON)
						.content(objectMapper.writeValueAsString(travelDetail1)))
				.andExpect(status().isNotFound()).andReturn();

		String result = MvcResult.getResponse().getContentAsString();
		int status = MvcResult.getResponse().getStatus();

		errorHandelModel = objectMapper.readValue(result, ErrorHandelModel.class);

		assertNotNull(errorHandelModel);
		assertEquals(404, status);
	}

	@Test
	public void deleteTravelDeatilsByEmpId_TravelId_Test() throws Exception {

		DeleteModel deleteModel = null;

		TravelDetail travelDetail1 = travelDetailsDTO1();
		travelDetail1.setTravel_id(35266);
		TravelDetail updateTravel = travelDetailsRes();

		when(travelRepository.findByEmployeeIdAndTravelId(Mockito.anyInt(), Mockito.anyInt()))
				.thenReturn(Optional.of(updateTravel));

		MvcResult MvcResult = mockMvc.perform(delete(deleteTravelDetailUrl).contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk()).andReturn();

		String result = MvcResult.getResponse().getContentAsString();
		int status = MvcResult.getResponse().getStatus();
		deleteModel = objectMapper.readValue(result, DeleteModel.class);

		assertNotNull(deleteModel);
		assertEquals(200, status);
	}

	@Test
	public void deleteTravelDeatils_BadRequest_Test() throws Exception {

		ErrorHandelModel errorHandelModel = null;
		TravelDetail travelDetail1 = travelDetailsDTO1();
		travelDetail1.setTravel_id(35266);
		MvcResult MvcResult = mockMvc.perform(delete(deleteTravelDetailBadUrl).contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isBadRequest()).andReturn();

		String result = MvcResult.getResponse().getContentAsString();
		int status = MvcResult.getResponse().getStatus();

		errorHandelModel = objectMapper.readValue(result, ErrorHandelModel.class);

		assertNotNull(errorHandelModel);
		assertEquals(400, status);
	}

	@Test
	public void deleteTravelDeatils_InternalServer_error_Test() throws Exception {

		ErrorHandelModel errorHandelModel = null;
		TravelDetail travelDetail1 = travelDetailsDTO1();
		travelDetail1.setTravel_id(35266);

		when(travelRepository.findByEmployeeIdAndTravelId(Mockito.anyInt(), Mockito.anyInt())).thenReturn(null);

		MvcResult MvcResult = mockMvc.perform(delete(deleteTravelDetailUrl).contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isInternalServerError()).andReturn();

		String result = MvcResult.getResponse().getContentAsString();
		int status = MvcResult.getResponse().getStatus();

		errorHandelModel = objectMapper.readValue(result, ErrorHandelModel.class);

		assertNotNull(errorHandelModel);
		assertEquals(500, status);
	}

	@Test
	public void deleteTravelDeatils_NotFound_By_Id_AND_TravelId_Test() throws Exception {

		ErrorHandelModel errorHandelModel = null;
		TravelDetail travelDetail1 = travelDetailsDTO1();
		travelDetail1.setTravel_id(35266);

		when(travelRepository.findByEmployeeIdAndTravelId(Mockito.anyInt(), Mockito.anyInt()))
				.thenReturn(Optional.empty());

		MvcResult MvcResult = mockMvc.perform(delete(deleteTravelDetailUrl).contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isNotFound()).andReturn();

		String result = MvcResult.getResponse().getContentAsString();
		int status = MvcResult.getResponse().getStatus();

		errorHandelModel = objectMapper.readValue(result, ErrorHandelModel.class);

		assertNotNull(errorHandelModel);
		assertEquals(404, status);
	}

	public TravelDetail travelDetailsDTO() throws ParseException {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

		Date startDate = formatter.parse("2020-03-21");
		Date returnDate = formatter.parse("2020-03-23");

		TravelDetail travelDetail = new TravelDetail();
		travelDetail.setEmp_id(12345);
		travelDetail.setDest_country("India");
		travelDetail.setSrc_country("India");
		travelDetail.setSrc_location("Gujurat");
		travelDetail.setDest_location("Assam");
		travelDetail.setStart_date(startDate);
		travelDetail.setReturn_date(returnDate);
		travelDetail.setMob_num(22365367372L);
		return travelDetail;
	}

	private TravelDetail travelDetailsDTO1() throws ParseException {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

		Date startDate = formatter.parse("2020-03-21");
		Date returnDate = formatter.parse("2020-03-23");

		TravelDetail travelDetail = new TravelDetail();
		travelDetail.setEmp_id(12345);
		travelDetail.setDest_country("India");
		travelDetail.setSrc_country("India");
		travelDetail.setSrc_location("Gujurat");
		travelDetail.setDest_location("Assam");
		travelDetail.setStart_date(startDate);
		travelDetail.setReturn_date(returnDate);
		travelDetail.setMob_num(22365367372L);
		return travelDetail;
	}

	private TravelDetail travelDetailsRes() throws ParseException {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

		Date startDate = formatter.parse("2020-03-21");
		Date returnDate = formatter.parse("2020-03-23");

		TravelDetail travelDetail = new TravelDetail();
		travelDetail.setEmp_id(7788);
		travelDetail.setEmp_id(12345);
		travelDetail.setDest_country("India");
		travelDetail.setSrc_country("India");
		travelDetail.setSrc_location("Gujurat");
		travelDetail.setDest_location("Assam");
		travelDetail.setStart_date(startDate);
		travelDetail.setReturn_date(returnDate);
		travelDetail.setMob_num(22365367372L);
		return travelDetail;
	}

	private List<TravelDetail> travelDetailsResponseList() throws ParseException {
		List<TravelDetail> travelDetails = new ArrayList<TravelDetail>();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

		Date startDate = formatter.parse("2020-03-21");
		Date returnDate = formatter.parse("2020-03-23");

		TravelDetail travelDetail = new TravelDetail();
		travelDetail.setTravel_id(566);
		travelDetail.setEmp_id(12345);
		travelDetail.setDest_country("India");
		travelDetail.setSrc_country("India");
		travelDetail.setSrc_location("Gujurat");
		travelDetail.setDest_location("Assam");
		travelDetail.setStart_date(startDate);
		travelDetail.setReturn_date(returnDate);
		travelDetail.setMob_num(22365367372L);
		travelDetails.add(travelDetail);
		return travelDetails;
		
	}

}
