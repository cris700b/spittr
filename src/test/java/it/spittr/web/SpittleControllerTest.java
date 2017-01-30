package it.spittr.web;

import static org.hamcrest.CoreMatchers.hasItems;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.servlet.view.InternalResourceView;

import it.spittr.data.SpittleRepository;
import it.spittr.exceptions.SpittleNotFoundException;
import it.spittr.model.Spittle;

public class SpittleControllerTest {

	SpittleController ctrl;
	SpittleRepository mockRepo;
	MockMvc mockMvc;
	
	@Before
	public void setup() {
		
		mockRepo = mock(SpittleRepository.class);
		ctrl = new SpittleController(mockRepo);
		mockMvc = MockMvcBuilders.standaloneSetup(ctrl).build();
	}
	
	@Test
	public void shouldShowRecentSpittles() throws Exception {
		
		List<Spittle> expectedSpittles = createSpittleList(20);
//		SpittleRepository mockRepo = mock(SpittleRepository.class);
		
		when(mockRepo.findSpittles(Long.MAX_VALUE, 20))
			.thenReturn(expectedSpittles);
		
//		SpittleController ctrl = new SpittleController(mockRepo);
		mockMvc = MockMvcBuilders.standaloneSetup(ctrl)
										.setSingleView(new InternalResourceView("/jsp/spittlesList.jsp"))
										.build();
		
		mockMvc.perform(get("/spittles"))
				.andExpect(view().name("spittlesList"))
				.andExpect(model().attributeExists("spittleList"))
				.andExpect(model().attribute("spittleList", hasItems(expectedSpittles.toArray())));
	}
	
	@Test
	public void shouldShowPagedSpittles() throws Exception {

		List<Spittle> expectedSpittles = createSpittleList(50);
//		SpittleRepository mockRepo = mock(SpittleRepository.class);
		
		when(mockRepo.findSpittles(238900, 50))
			.thenReturn(expectedSpittles);
		
//		SpittleController ctrl = new SpittleController(mockRepo);
		
		mockMvc = MockMvcBuilders.standaloneSetup(ctrl)
										.setSingleView(new InternalResourceView("/jsp/spittlesList.jsp"))
										.build();
		
		mockMvc.perform(get("/spittles?max=238900&count=50"))
				.andExpect(view().name("spittlesList"))
				.andExpect(model().attributeExists("spittleList"))
				.andExpect(model().attribute("spittleList", hasItems(expectedSpittles.toArray())));
		
	}

	@Test
	public void shouldShowSpittle() throws Exception {

		Spittle expectedSpittle = new Spittle("Hello", new Date());
//		SpittleRepository mockRepo = mock(SpittleRepository.class);
		
		when(mockRepo.findOne(12345))
			.thenReturn(expectedSpittle);
		
//		SpittleController ctrl = new SpittleController(mockRepo);
//		mockMvc = MockMvcBuilders.standaloneSetup(ctrl).build();
								
		mockMvc.perform(get("/spittles/12345"))
				.andExpect(view().name("spittleDetails"))
				.andExpect(model().attributeExists("spittle"))
				.andExpect(model().attribute("spittle", expectedSpittle));
	}
	
	@Test
	public void shouldThrowSpittleNotFoundException() throws Exception {
		
		when(mockRepo.findOne(123))
			.thenThrow(SpittleNotFoundException.class);
		mockMvc.perform(get("/spittles/123"))
				.andExpect(view().name("error/notFound"))
				.andExpect(model().attributeDoesNotExist("spittle"));
		
		verify(mockRepo, atLeastOnce()).findOne(123);
	}
	
	@Test
	public void shouldSaveSpittle() throws Exception {
		
		Spittle unsavedSpittle = new Spittle(null, "Hello", new Date(), 1D, 2D);
		Spittle savedSpittle = new Spittle(1234L, "Hello", new Date(), 1D, 2D);
		
		when(mockRepo.save(unsavedSpittle))
			.thenReturn(savedSpittle);
		
		mockMvc.perform(post("/spittles/save")
						.param("message", unsavedSpittle.getMessage())
						.param("longitude", unsavedSpittle.getLongitude().toString())
						.param("latitude", unsavedSpittle.getLatitude().toString()))
				.andExpect(redirectedUrl("/spittles"));
				
		verify(mockRepo, atLeastOnce()).save(unsavedSpittle);
	}
	
	
	
	private List<Spittle> createSpittleList(int spittleNum) {

		List<Spittle> listSpittles = new ArrayList<>();
		for(int index = 0; index < spittleNum; index++){
			
			listSpittles.add(new Spittle("spittle " + index, new Date()));
		}
		
		return listSpittles;
	}
}
