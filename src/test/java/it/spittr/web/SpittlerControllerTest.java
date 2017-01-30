package it.spittr.web;


import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import org.junit.Test;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.servlet.view.InternalResourceView;

import it.spittr.data.SpittlerRepository;
import it.spittr.model.Spittler;

public class SpittlerControllerTest {

	@Test
	public void shouldShowRegistrationForm() throws Exception {
		
		SpittlerRepository mockRepo = mock(SpittlerRepository.class);
		
		SpittlerController ctrl = new SpittlerController(mockRepo);
		MockMvc mockMvc = MockMvcBuilders.standaloneSetup(ctrl)
										.setSingleView(new InternalResourceView("/jsp/registrationForm.jsp"))
										.build();
		
		mockMvc.perform(get("/spittler/register"))
				.andExpect(view().name("registrationForm"));
	}
	
	@Test
	public void shouldProcessRegistration() throws Exception {

		SpittlerRepository mockRepo = mock(SpittlerRepository.class);
		Spittler unsaved = new Spittler("Jack", "Bauer", "jbauer@gmail.com", "jbauer", "secret");
		Spittler saved = new Spittler(24L, "Jack", "Bauer", "jbauer@gmail.com", "jbauer", "secret");
		
		when(mockRepo.save(saved)).thenReturn(unsaved);
		
		SpittlerController ctrl = new SpittlerController(mockRepo);
		
		MockMvc mockMvc = MockMvcBuilders.standaloneSetup(ctrl).build();
		
		mockMvc.perform(post("/spittler/register")
							.contentType("multipart/form-data")
							.param("firstname", unsaved.getFirstname())
							.param("lastname", unsaved.getLastname())
							.param("email", unsaved.getEmail())
							.param("username", unsaved.getUsername())
							.param("password", unsaved.getPassword()))
				.andExpect(redirectedUrl("/spittler/" + saved.getUsername()));
		
		verify(mockRepo, atLeastOnce()).save(unsaved);
	}

	@Test
	public void shouldShowSpittler() throws Exception {

		SpittlerRepository mockRepo = mock(SpittlerRepository.class);
		Spittler expectedSpittler = new Spittler(24L, "Jack", "Bauer", "jbauer@gmail.com", "jbauer", "secret");
		
		when(mockRepo.findByUsername(expectedSpittler.getUsername()))
			.thenReturn(expectedSpittler);
		
		SpittlerController ctrl = new SpittlerController(mockRepo);
		
		MockMvc mockMvc = MockMvcBuilders.standaloneSetup(ctrl).build();
								
		mockMvc.perform(get("/spittler/" + expectedSpittler.getUsername()))
				.andExpect(view().name("profile"))
				.andExpect(model().attributeExists("spittler"))
				.andExpect(model().attribute("spittler", expectedSpittler));
	}
	
}
