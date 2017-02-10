package it.spittr.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import it.spittr.data.SpittleRepository;
import it.spittr.exceptions.Error;
import it.spittr.exceptions.SpittleNotFoundException;
import it.spittr.model.Spittle;

@RestController
@RequestMapping("/spittles")
public class SpittleController {

	private static final String MAX_LONG_AS_STRING="9223372036854775807";
	private SpittleRepository repo;
	
	@Autowired
	public SpittleController(SpittleRepository repo) {

		this.repo = repo;
	}
	
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public Spittle spittleById(@PathVariable long id){
		
		Spittle spittle = repo.findOne(id);
		if(null == spittle){
			
			throw new SpittleNotFoundException(id);
		}
		
		return spittle;
	}

	@RequestMapping(method = RequestMethod.POST, consumes = "application/json")
	public Spittle saveSpittle(@RequestBody Spittle spittle){
		
		return repo.save(spittle);
	}
	
	@RequestMapping(method = RequestMethod.GET)
	public List<Spittle> spittles(@RequestParam(value = "max", defaultValue = MAX_LONG_AS_STRING)
									long max, 
									@RequestParam(value = "count", defaultValue = "20")
									int count){
		
		return repo.findSpittles(max, count);
	}
	
	@ExceptionHandler(SpittleNotFoundException.class)
	@ResponseStatus(HttpStatus.NOT_FOUND)
	public Error spittleNotFound(SpittleNotFoundException e){
		
		return new Error(4, "Spittle [" + e.getSpittleId() + "] not found");
	}
}
