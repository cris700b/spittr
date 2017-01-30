<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@ taglib uri="http://www.springframework.org/tags/form" prefix="sf"%>

<h1>Spittler Profile</h1>

<sf:form commandName="spittler">

	<sf:input path="firstname" placeholder="First Name" /> 
	
	<br />
	<sf:input path="lastname" placeholder="Last Name" />

	<br />
	<sf:input path="email" placeholder="Email" />
	
	<br /> 
	<sf:input path="username" placeholder="Username" />

<!-- 	<br />  -->
<%-- 	<sf:password path="password" placeholder="Password" /> --%>

	<br />
	<button type="submit">Submit</button>
</sf:form>
