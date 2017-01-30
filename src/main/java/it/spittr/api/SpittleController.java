package it.spittr.api;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import it.spittr.data.SpittleRepository;
import it.spittr.model.Spittle;

@Controller
@RequestMapping("/spittles")
public class SpittleController {

	private static final String MAX_LONG_AS_STRING="9223372036854775807";
	private SpittleRepository repo;
	
	public SpittleController(SpittleRepository repo) {

		this.repo = repo;
	}
	
	@RequestMapping(method = RequestMethod.GET)
	public List<Spittle> spittles(@RequestParam(value = "max", defaultValue = MAX_LONG_AS_STRING)
									long max, 
									@RequestParam(value = "count", defaultValue = "20")
									int count){
		
		return repo.findSpittles(max, count);
	}
}
