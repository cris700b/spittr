package it.spittr.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

@Entity(name="users")
public class Spittler {
	
	@Id
	@GeneratedValue
	private Long id;
	
	@NotNull
	@Size(min = 2, max = 30, message="{firstname.size}")
	private String firstname;

	@NotNull
	@Size(min = 2, max = 30, message="{lastname.size}")
	private String lastname;
	
	@NotNull
	@Pattern(regexp=".+@.+\\.[a-z]+", message="{email.valid}")
	private String email;
	
	@NotNull
	@Size(min = 5, max = 16, message="{username.size}")
	private String username;
	
	@NotNull
	@Size(min = 5, max = 25, message="{password.size}")
	private String password;

	public Spittler(){
		
	}
	
	public Spittler(String firstname, String lastname, String email, 
					String username, String password) {

		this(0L, firstname, lastname, email, username, password);
	}

	public Spittler(Long id, String firstname, String lastname, String email, 
					String username, String password) {

		this.id = id;
		this.firstname = firstname;
		this.lastname = lastname;
		this.email = email;
		this.username = username;
		this.password = password;
	}

	public Long getId() {

		return id;
	}

	public void setId(Long id) {

		this.id = id;
	}

	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Override
	public boolean equals(Object that) {

		return EqualsBuilder.reflectionEquals(this, that, "id", "age");
	}

	@Override
	public int hashCode() {

		return HashCodeBuilder.reflectionHashCode(this, "id", "age");
	}
}
