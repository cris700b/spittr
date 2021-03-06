package it.spittr.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "Spittle not found")
public class SpittleNotFoundException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3475229467801081017L;
	
	private long spittleId;
	
	public SpittleNotFoundException(long spittleId) {
		
		this.spittleId = spittleId;
	}
	
	public long getSpittleId(){
		
		return this.spittleId;
	}

}
