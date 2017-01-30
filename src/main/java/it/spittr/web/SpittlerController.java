package it.spittr.web;

import java.io.IOException;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import it.spittr.data.SpittlerRepository;
import it.spittr.model.Spittler;

@Controller
@RequestMapping("/spittler")
public class SpittlerController {

	SpittlerRepository repo;
	
	@Autowired
	public SpittlerController(SpittlerRepository repo) {

		this.repo = repo;
	}

	@RequestMapping(value = "/register", method = RequestMethod.GET)
	public String showRegistrationForm(Model model) {

		model.addAttribute(new Spittler());
		
		return "registrationForm";
	}

	@RequestMapping(value = "/register", method = RequestMethod.POST)
	public String processRegistration(@Valid Spittler spittler,
										Errors errors, 
										RedirectAttributes model) throws Exception, IOException {

		String strView = "registrationForm";
		if (!errors.hasErrors()) {
		
			repo.save(spittler);
			model.addAttribute("username", spittler.getUsername());
			model.addFlashAttribute(spittler);
			
			strView = "redirect:/spittler/{username}";
		}
		
		return strView;
	}

	@RequestMapping(value = "/{username}", method = RequestMethod.GET)
	public String showSpittlerProfile(@PathVariable String username, Model model) {

		if(!model.containsAttribute("spittler")){
			
			Spittler spittler = repo.findByUsername(username);
			model.addAttribute(spittler);
		}
		
		return "profile";
	}
}
