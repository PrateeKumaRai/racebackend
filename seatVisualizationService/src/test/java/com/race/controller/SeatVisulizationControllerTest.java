package com.race.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.race.entity.CubicalEntity;
import com.race.model.CubicalResponse;
import com.race.repo.CubicalRepo;
import com.race.restModel.UpadteSeatStatus;
import com.race.service.impl.SeatServiceImpl;

/**
 * Test Case for Seat Service
 * 
 * @author praterai
 *
 */

@SpringBootTest
@RunWith(SpringRunner.class)
public class SeatVisulizationControllerTest {

	private MockMvc mockMvc;

	@Autowired
	private WebApplicationContext context;

	@MockBean
	private CubicalRepo cubicalRepo;

	@Before
	public void setup() throws Exception {
		mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
	}

	protected String mapToJson(Object obj) throws JsonProcessingException {
		ObjectMapper objectMapper = new ObjectMapper();
		return objectMapper.writeValueAsString(obj);
	}

	@Mock
	private SeatServiceImpl seatServiceImpl;

	@InjectMocks
	private SeatServiceImpl seatServiceImplMock;

	
	/**
	 * @Test Get updated seat
	 * @Senerio Positive
	 * @param empty { @link empty}
	 * @return { @link CubicalResponse}
	 * 
	 */
	@Test
	public void getUpdatedSeatTest() throws Exception {
		String URI = "/race/api/v1/seats/getAll";
		List<CubicalResponse> cubicalResponseList = new ArrayList<CubicalResponse>();
		CubicalResponse cubicalResponse = new CubicalResponse();
		cubicalResponse.setEmailId("prateek");
		cubicalResponse.setCubicalNumber("2333");
		cubicalResponseList.add(cubicalResponse);

		List<CubicalEntity> cubicalEntityList = new ArrayList<CubicalEntity>();
		CubicalEntity cubicalEntity = new CubicalEntity();
		cubicalEntity.setEmailId("prateek");
		cubicalEntity.setCubicalNumber("2333");
		cubicalEntityList.add(cubicalEntity);

		when(cubicalRepo.findByCubicalStatus(Mockito.anyBoolean(), Mockito.any())).thenReturn(cubicalEntityList);
		Mockito.when(seatServiceImpl.getUpdatedSeat()).thenReturn(cubicalResponseList);
		RequestBuilder requestBuilder = MockMvcRequestBuilders.get(URI).accept(MediaType.APPLICATION_JSON);
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		String expectedJson = this.mapToJson(cubicalResponseList);
		String outputInJson = result.getResponse().getContentAsString();
		assertThat(outputInJson).isEqualTo(expectedJson);
	}

	/**
	 * 
	 * @Test Get updated seat by email
	 * @Senerio Positive
	 * @param emailId { @link String emailId }
	 * @return { @link CubicalResponse }
	 * 
	 */
	@Test
	public void getSeatByMailTest() throws Exception {
		String URI = "/race/api/v1/seats/get";

		CubicalEntity cubicalEntity = new CubicalEntity();
		cubicalEntity.setSeatMailId("prateek");
		cubicalEntity.setSeatNumberTemp("2333");

		CubicalResponse cubicalResponse = new CubicalResponse();
		cubicalResponse.setSeatMailId(cubicalEntity.getSeatMailId());
		cubicalResponse.setSeatNumberTemp(cubicalEntity.getSeatNumberTemp());

		UpadteSeatStatus upadteSeatStatus = new UpadteSeatStatus();
		upadteSeatStatus.setEmailId("prateek");

		when(cubicalRepo.findByEmailId(Mockito.anyString())).thenReturn(cubicalEntity);
		Mockito.when(seatServiceImpl.getUpdatedSeatByMail(Mockito.anyString())).thenReturn(cubicalResponse);
		RequestBuilder requestBuilder = MockMvcRequestBuilders.post(URI).accept(MediaType.APPLICATION_JSON)
				.content(mapToJson(upadteSeatStatus)).contentType(MediaType.APPLICATION_JSON);
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		String expectedJson = this.mapToJson(cubicalResponse);
		String outputInJson = result.getResponse().getContentAsString();
		System.out.println(expectedJson);
		System.out.println(outputInJson);
		assertThat(outputInJson).isEqualTo(expectedJson);
	}
	
	/**
	 * 
	 * @Test Get updated seat by email
	 * @Senerio Negative
	 * @param emailId { @link String emailId }
	 * @return { @link CubicalResponse }
	 */
	@Test
	public void getSeatByMailTestNegative() throws Exception {
		String URI = "/race/api/v1/seats/get";

		CubicalEntity cubicalEntity = new CubicalEntity();
		cubicalEntity.setSeatMailId("prateek");
		cubicalEntity.setSeatNumberTemp("2333");

		CubicalResponse cubicalResponse = new CubicalResponse();
		
		UpadteSeatStatus upadteSeatStatus = new UpadteSeatStatus();
		upadteSeatStatus.setEmailId("prateek");

		when(cubicalRepo.findByEmailId(Mockito.anyString())).thenReturn(null);
		Mockito.when(seatServiceImpl.getUpdatedSeatByMail(Mockito.anyString())).thenReturn(cubicalResponse);
		RequestBuilder requestBuilder = MockMvcRequestBuilders.post(URI).accept(MediaType.APPLICATION_JSON)
				.content(mapToJson(upadteSeatStatus)).contentType(MediaType.APPLICATION_JSON);
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		String expectedJson = this.mapToJson(cubicalResponse);
		String outputInJson = result.getResponse().getContentAsString();
		System.out.println(expectedJson);
		System.out.println(outputInJson);
		assertThat(outputInJson).isEqualTo(expectedJson);
	}

	/**
	 * 
	 * @Test Updated seat status
	 * @Senerio Positive
	 * @param emailId,startDate,returnDate,seatStatus { @link String emailId, Date
	 *                                                startDate, Date returnDate,
	 *                                                Boolean seatStatus}
	 * @return { @link Long}
	 */

	@Test
	public void updateSeatStatusTest() throws Exception {
		String URI = "/race/api/v1/seats/post";

		CubicalEntity cubicalEntity = new CubicalEntity();
		cubicalEntity.setEmailId("prateek");
		cubicalEntity.setCubicalNumber("2333");

		CubicalResponse cubicalResponse = new CubicalResponse();
		cubicalResponse.setSeatMailId(cubicalEntity.getSeatMailId());
		cubicalResponse.setSeatNumberTemp(cubicalEntity.getSeatNumberTemp());

		UpadteSeatStatus upadteSeatStatus = new UpadteSeatStatus();
		upadteSeatStatus.setCubicalStatus("a");
		upadteSeatStatus.setEmailId("prateek");
		upadteSeatStatus.setStartDate(new Date());
		upadteSeatStatus.setReturnDate(new Date());
		Long response = 200l;

		when(cubicalRepo.findByEmailId(Mockito.anyString())).thenReturn(cubicalEntity);
		cubicalRepo.updateSeatStatus(Mockito.anyBoolean(), Mockito.any(), Mockito.any(), Mockito.anyString());
		Mockito.when(seatServiceImpl.updateSeatStatus(Mockito.anyString(), Mockito.any(), Mockito.any(),
				Mockito.anyBoolean())).thenReturn(response);
		RequestBuilder requestBuilder = MockMvcRequestBuilders.post(URI).accept(MediaType.APPLICATION_JSON)
				.content(mapToJson(upadteSeatStatus)).contentType(MediaType.APPLICATION_JSON);
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		String expectedJson = this.mapToJson(response);
		String outputInJson = result.getResponse().getContentAsString();
		System.out.println(expectedJson);
		System.out.println(outputInJson);
		assertThat(outputInJson).isEqualTo(expectedJson);
	}
	
	/**
	 * 
	 * @Test Updated seat status
	 * @Senerio Negative
	 * @param emailId,startDate,returnDate,seatStatus { @link String emailId, Date
	 *                                                startDate, Date returnDate,
	 *                                                Boolean seatStatus}
	 * @return { @link Long}
	 * 
	 */
	@Test
	public void updateSeatStatusTestNegative() throws Exception {
		String URI = "/race/api/v1/seats/post";

		CubicalEntity cubicalEntity = new CubicalEntity();
		cubicalEntity.setEmailId("prateek");
		cubicalEntity.setCubicalNumber("2333");

		CubicalResponse cubicalResponse = new CubicalResponse();
		cubicalResponse.setSeatMailId(cubicalEntity.getSeatMailId());
		cubicalResponse.setSeatNumberTemp(cubicalEntity.getSeatNumberTemp());

		UpadteSeatStatus upadteSeatStatus = new UpadteSeatStatus();
		upadteSeatStatus.setCubicalStatus("a");

		Long response = 201l;

		when(cubicalRepo.findByEmailId(Mockito.anyString())).thenReturn(cubicalEntity);
		cubicalRepo.updateSeatStatus(Mockito.anyBoolean(), Mockito.any(), Mockito.any(), Mockito.anyString());
		Mockito.when(seatServiceImpl.updateSeatStatus(Mockito.anyString(), Mockito.any(), Mockito.any(),
				Mockito.anyBoolean())).thenReturn(response);
		RequestBuilder requestBuilder = MockMvcRequestBuilders.post(URI).accept(MediaType.APPLICATION_JSON)
				.content(mapToJson(upadteSeatStatus)).contentType(MediaType.APPLICATION_JSON);
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		String expectedJson = this.mapToJson(response);
		String outputInJson = result.getResponse().getContentAsString();
		System.out.println(expectedJson);
		System.out.println(outputInJson);
		assertThat(outputInJson).isEqualTo(expectedJson);
	}

	/**
	 * 
	 * @TestUpdated seat status after seat booking
	 * @Senerio Positive
	 * @param emailId,loginEmail,seatNo { @link String emailId,String loginEmail,
	 *                                  String seatNo}
	 * @return { @link Long }
	 */
	@Test
	public void updateSeatStatusAfterBookingTest() throws Exception {

		String URI = "/race/api/v1/seats/update";

		CubicalEntity cubicalEntity = new CubicalEntity();
		cubicalEntity.setEmailId("prateek");

		cubicalEntity.setCubicalNumberTemp(false);

		CubicalResponse cubicalResponse = new CubicalResponse();
		cubicalResponse.setSeatMailId(cubicalEntity.getSeatMailId());
		cubicalResponse.setSeatNumberTemp(cubicalEntity.getSeatNumberTemp());

		UpadteSeatStatus upadteSeatStatus = new UpadteSeatStatus();
		upadteSeatStatus.setEmailId("prateek");
		upadteSeatStatus.setLoginMail("prateek");
		upadteSeatStatus.setCubicalNumber("2333");
		upadteSeatStatus.setCubicalStatus("a");
		Long response = 200l;

		when(cubicalRepo.findByEmailId(Mockito.anyString())).thenReturn(cubicalEntity);
		cubicalRepo.updateSeatStatusAfterBookingOwner(Mockito.anyBoolean(), Mockito.anyString());
		cubicalRepo.updateSeatStatusAfterBookingTemp(Mockito.anyBoolean(), Mockito.anyString(), Mockito.anyString(),
				Mockito.anyString());
		cubicalRepo.updateSeatStatusAfterBookingOwner(Mockito.anyBoolean(), Mockito.anyString());
		Mockito.when(seatServiceImpl.updateSeatStatusAfterBooking(Mockito.anyString(), Mockito.anyString(),
				Mockito.anyString())).thenReturn(response);
		RequestBuilder requestBuilder = MockMvcRequestBuilders.post(URI).accept(MediaType.APPLICATION_JSON)
				.content(mapToJson(upadteSeatStatus)).contentType(MediaType.APPLICATION_JSON);
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		String expectedJson = this.mapToJson(response);
		String outputInJson = result.getResponse().getContentAsString();
		System.out.println(expectedJson);
		System.out.println(outputInJson);
		assertThat(outputInJson).isEqualTo(expectedJson);

	}

	/**
	 * 
	 * @Test Updated seat status after seat booking
	 * @Senerio Negative
	 * @param emailId,loginEmail,seatNo { @link String emailId,String loginEmail,
	 *                                  String seatNo}
	 * @return { @link Long }
	 */
	@Test
	public void updateSeatStatusAfterBookingTestNegative() throws Exception {

		String URI = "/race/api/v1/seats/update";

		CubicalEntity cubicalEntity = new CubicalEntity();
		cubicalEntity.setCubicalNumberTemp(false);

		CubicalEntity cubicalEntitytest = new CubicalEntity();
		cubicalEntitytest.setEmailId("prateek");
		cubicalEntitytest.setCubicalNumberTemp(false);
		cubicalEntitytest.setSeatNumberTemp("2333");
		cubicalEntitytest.setSeatMailId("prateek");
		cubicalRepo.save(cubicalEntity);

		CubicalResponse cubicalResponse = new CubicalResponse();
		cubicalResponse.setSeatMailId(cubicalEntity.getSeatMailId());
		cubicalResponse.setSeatNumberTemp(cubicalEntity.getSeatNumberTemp());

		UpadteSeatStatus upadteSeatStatus = new UpadteSeatStatus();
		upadteSeatStatus.setEmailId("prateek");
		upadteSeatStatus.setLoginMail("prateek");
		upadteSeatStatus.setCubicalNumber("2333");
		upadteSeatStatus.setCubicalStatus("a");
		Long response = 200l;

		when(cubicalRepo.findByEmailId(Mockito.anyString())).thenReturn(null);
		cubicalRepo.updateSeatStatusAfterBookingOwner(Mockito.anyBoolean(), Mockito.anyString());
		cubicalRepo.updateSeatStatusAfterBookingTemp(Mockito.anyBoolean(), Mockito.anyString(), Mockito.anyString(),
				Mockito.anyString());
		cubicalRepo.updateSeatStatusAfterBookingOwner(Mockito.anyBoolean(), Mockito.anyString());
		when(cubicalRepo.save(Mockito.anyObject())).thenReturn(cubicalEntity);
		Mockito.when(seatServiceImpl.updateSeatStatusAfterBooking(Mockito.anyString(), Mockito.anyString(),
				Mockito.anyString())).thenReturn(response);
		RequestBuilder requestBuilder = MockMvcRequestBuilders.post(URI).accept(MediaType.APPLICATION_JSON)
				.content(mapToJson(upadteSeatStatus)).contentType(MediaType.APPLICATION_JSON);
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		String expectedJson = this.mapToJson(response);
		String outputInJson = result.getResponse().getContentAsString();
		System.out.println(expectedJson);
		System.out.println(outputInJson);
		assertThat(outputInJson).isEqualTo(expectedJson);

	}
	
	/**
	 * @Test Get updated seat
	 * @Senerio Positive
	 * @param empty { @link empty}
	 * @return { @link CubicalResponse}
	 * 
	 */
	@Test
	public void filterTest() throws Exception{
		
		String URI = "/race/api/v1/seats/filter";
		
		List<CubicalEntity> cubicalEntityList = new ArrayList<CubicalEntity>();
		CubicalEntity cubicalEntity = new CubicalEntity();
		cubicalEntity.setCity("Bangalore");
		cubicalEntity.setCountry("India");
		cubicalEntity.setCubicalLocation("Ecospace");
		cubicalEntity.setCubicalStatus(true);
		cubicalEntityList.add(cubicalEntity);
		
		List<CubicalResponse> cubicalResponseList2 = new ArrayList<CubicalResponse>();
		CubicalResponse cubicalResponseRequest = new CubicalResponse();
		cubicalResponseRequest.setCity("Bangalore");
		cubicalResponseRequest.setCountry("India");
		cubicalResponseRequest.setCubicalLocation("Ecospace");
		cubicalResponseList2.add(cubicalResponseRequest);
		
		List<CubicalResponse> cubicalResponseList1 = new ArrayList<CubicalResponse>();
		CubicalResponse cubicalResponseRequest1 = new CubicalResponse();
		cubicalResponseRequest.setCity("Bangalore");
		cubicalResponseRequest.setCountry("India");
		cubicalResponseRequest.setCubicalLocation("Ecospace");
		cubicalResponseList1.add(cubicalResponseRequest1);
		 
		when(cubicalRepo.filter(Mockito.anyString(),Mockito.anyString(),Mockito.anyString(),Mockito.anyBoolean(), Mockito.any())).thenReturn(cubicalEntityList);
		Mockito.when(seatServiceImpl.filterSeat(Mockito.anyString(),Mockito.anyString(),Mockito.anyString())).thenReturn(cubicalResponseList1);
		RequestBuilder requestBuilder = MockMvcRequestBuilders.post(URI).accept(MediaType.APPLICATION_JSON)
				.content(mapToJson(cubicalResponseRequest)).contentType(MediaType.APPLICATION_JSON);
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		String expectedJson = this.mapToJson(cubicalResponseList2);
		String outputInJson = result.getResponse().getContentAsString();
		assertThat(outputInJson).isEqualTo(expectedJson);
		
	}
	
	/**
	 * 
	 * @Test Cancel seat after booking
	 * @Senerio Positive
	 * @param emailId,loginEmail { @link String emailId, String loginMail }
	 * @return {@link Long}
	 */
	@Test
	public void cancleSeatAfterBookingTest() throws Exception {
		
		String URI = "/race/api/v1/seats/cancle";
		
		UpadteSeatStatus upadteSeatStatus = new UpadteSeatStatus();
		upadteSeatStatus.setEmailId("prateek");
		upadteSeatStatus.setLoginMail("prateek");
		Long response = 200l;

		cubicalRepo.updateSeatStatusAfterBookingOwner(Mockito.anyBoolean(), Mockito.anyString());
		cubicalRepo.clearSeatDataAfterUnbook( Mockito.anyString(), Mockito.anyString(),Mockito.anyBoolean(),
				Mockito.anyString());
		Mockito.when(seatServiceImpl.cancleSeatAfterBooking(Mockito.anyString(), Mockito.anyString())).thenReturn(response);
		RequestBuilder requestBuilder = MockMvcRequestBuilders.post(URI).accept(MediaType.APPLICATION_JSON)
				.content(mapToJson(upadteSeatStatus)).contentType(MediaType.APPLICATION_JSON);
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		String expectedJson = this.mapToJson(response);
		String outputInJson = result.getResponse().getContentAsString();
		System.out.println(expectedJson);
		System.out.println(outputInJson);
		assertThat(outputInJson).isEqualTo(expectedJson);
	
	}
	
	/**
	 * 
	 * @Test Reset seat status
	 * @Senerio Positive
	 * @param empty { @link empty }
	 * @return { @link Boolean }
	 * 
	 */
	@Test
	public void jobToResetStatusTest() throws Exception {
		String URI = "/race/api/v1/seats/reset";
		
		Boolean response=true;
		
		cubicalRepo.jobResetSeatStatus(Mockito.anyBoolean(), Mockito.any());
		cubicalRepo.jobResetTempBooking(Mockito.anyBoolean(),Mockito.anyBoolean());
		seatServiceImpl.resetStatus();
		RequestBuilder requestBuilder = MockMvcRequestBuilders.get(URI).accept(MediaType.APPLICATION_JSON);
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		String expectedJson = this.mapToJson(response);
		String outputInJson = result.getResponse().getContentAsString();
		assertThat(outputInJson).isEqualTo(expectedJson);
	}
	
	/**
	 * 
	 * @Test Reset seat status
	 * @Senerio Negative
	 * @param empty { @link empty }
	 * @return { @link Boolean }
	 * 
	 */
	@Test
	public void jobToResetStatusTestNegative() throws Exception {
		String URI = "/race/api/v1/seats/reset";
		
		
		Boolean response=true;

		cubicalRepo.jobResetSeatStatus(Mockito.anyBoolean(), Mockito.any());
		cubicalRepo.jobResetTempBooking(Mockito.anyBoolean(),Mockito.anyBoolean());
		seatServiceImpl.resetStatus();
		RequestBuilder requestBuilder = MockMvcRequestBuilders.get(URI).accept(MediaType.APPLICATION_JSON);
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		String expectedJson = this.mapToJson(response);
		String outputInJson = result.getResponse().getContentAsString();
		assertThat(outputInJson).isEqualTo(expectedJson);
	}

}
