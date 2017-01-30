package it.spittr.security;

import java.util.ArrayList;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import it.spittr.data.SpittlerRepository;
import it.spittr.model.Spittler;

public class SpittlerUserDetailsService implements UserDetailsService {

	private final SpittlerRepository spittlerRepo;
	
	public SpittlerUserDetailsService(SpittlerRepository spittlerRepo) {

		this.spittlerRepo = spittlerRepo;
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

		Spittler spittler = spittlerRepo.findByUsername(username);
		if(null != spittler){
			
			List<GrantedAuthority> listUserAuthorities = new ArrayList<GrantedAuthority>();
			listUserAuthorities.add(new SimpleGrantedAuthority("ROLE_SPITTLER"));
			
			return new User(spittler.getUsername(), 
							spittler.getPassword(), 
							listUserAuthorities);
		}
		else {
			
			throw new UsernameNotFoundException("User '" + username + "' not found.");
		}
	}

}
