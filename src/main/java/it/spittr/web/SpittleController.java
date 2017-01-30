package it.spittr.web;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import it.spittr.data.SpittleRepository;
import it.spittr.model.Spittle;

@Controller
@RequestMapping("/spittles")
public class SpittleController {

	private SpittleRepository repo;
	
	@Autowired
	public SpittleController(SpittleRepository repo) {

		this.repo = repo;
	}
	
	@RequestMapping(method = RequestMethod.GET)
	public String recentSpittles(@RequestParam(defaultValue="9223372036854775807") long max,
								@RequestParam(defaultValue="20") int count,
								Model model) {
		
		List<Spittle> spittlesList = new ArrayList<>();//repo.findSpittles(max, count);
		spittlesList.add(new Spittle("first spittle", new Date()));
		
		model.addAttribute(spittlesList);
		
		return "spittlesList";
	}
	
	@RequestMapping(value="/{spittleId}", method=RequestMethod.GET)
	public String showSpittle(@PathVariable long spittleId, Model model) {

		Spittle spittle = repo.findOne(spittleId);
		model.addAttribute(spittle);

		return "spittleDetails";
	}

	@RequestMapping(method = RequestMethod.POST)
	public String saveSpittle(Spittle spittle, Model model){
		
		repo.save(spittle);
		
		return "redirect:/spittles";
	}
	
}
