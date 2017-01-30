package it.spittr.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

import it.spittr.data.SpittlerRepository;
import it.spittr.security.SpittlerUserDetailsService;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	// request filtering security configurations
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		
		http
			.formLogin()
				.loginPage("/jsp/login.jsp")
				.and()
				.rememberMe()
				.tokenValiditySeconds(2419200)
				.key("spitterSecurityKey")// private security key (default is SpringSecured)
			.and()
			.logout()
				.logoutSuccessUrl("/")
			.and()
			.httpBasic()
				.realmName("Spittr")
			.and()
			.authorizeRequests()
				.antMatchers("/spittler/me").authenticated()//hasRole("SPITTLER")
				.antMatchers(HttpMethod.POST, "/spittles").hasRole("SPITTLER")
				.anyRequest().permitAll()
			.and()
			.requiresChannel()
				.antMatchers("/spittler/form").requiresSecure()
			;
			
	}
	
	// ----------------- custom (decoupled) user store ---------------------
	@Autowired
	SpittlerRepository spittlerRepo;
	
	/**
	 * configuration for authentication with a custom UserServiceDetails implemenation
	 * this configuration should be preferred because 
	 * it decouples the authentication process from the underlying storage mechanism (LDAP, SQL_DB, NO_SQL_DB)
	 */
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		
		auth.userDetailsService(new SpittlerUserDetailsService(spittlerRepo));
	}
	
	// -----------------  sql database user store ---------------------
//	@Autowired
//	DataSource dataSource;
	
	/* configuration for authentication with DB user store
	 * spring security default authentication queries 
	 *	public static final String DEF_USERS_BY_USERNAME_QUERY =
	 *		"select username,password,enabled " +
	 *		"from users " +
	 *		"where username = ?";
	 *	public static final String DEF_AUTHORITIES_BY_USERNAME_QUERY =
	 *		"select username,authority " +
	 *		"from authorities " +
	 *		"where username = ?";
	 *	public static final String DEF_GROUP_AUTHORITIES_BY_USERNAME_QUERY =
	 *		"select g.id, g.group_name, ga.authority " +
	 *		"from groups g, group_members gm, group_authorities ga " +
	 *		"where gm.username = ? " +
	 *		"and g.id = ga.group_id " +
	 *		"and g.id = gm.group_id";
	*/
//	@Override
//	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//
//		auth.jdbcAuthentication()
//			.dataSource(dataSource)
//			.usersByUsernameQuery("select username, password, true"
//									+ " from Spittler where username = ?")
//			.authoritiesByUsernameQuery("select username, 'ROLE_USER' "
//										+ "	from Spittler where username = ?")
//			.passwordEncoder(new StandardPasswordEncoder("my_secret_pass_encoding_token"));
//	}
	
	//--------------------------- in memory user store -------------------------------
	// config for authentication with in memory user store
//	@Override
//	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//
//		auth.inMemoryAuthentication()
//				.withUser("user").password("pass").roles("USER")
//				.and()
//				.withUser("admin").password("pass").roles("USER", "ADMIN");
//	}
	
}
