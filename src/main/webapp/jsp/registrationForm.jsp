
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="sf"%>

<style>
div.errors {
	background-color: #ffcccc;
	border: 2px solid red;
}

label.errors {
	color: red;
}

input.errors {
	background-color: #ffcccc;
}
</style>

	<h1>Registration Form</h1>

	<sf:form method="post" commandName="spittler" >

		<sf:errors path="*" element="div" cssClass="errors" />

		<sf:label path="firstname" cssErrorClass="errors">
			First Name:
		</sf:label>

		<sf:input path="firstname" placeholder="First Name"
			cssErrorClass="errors" />

		<br />
		<sf:label path="lastname" cssErrorClass="errors">
			Last Name:
		</sf:label>
		<sf:input path="lastname" placeholder="Last Name"
			cssErrorClass="errors" />

		<br />
		<sf:label path="email" cssErrorClass="errors">
			Email:
		</sf:label>
		<sf:input type="email" path="email" placeholder="Email"
			cssErrorClass="errors" />

		<br />
		<sf:label path="username" cssErrorClass="errors">
			Username: 
		</sf:label>
		<sf:input path="username" placeholder="Username"
			cssErrorClass="errors" />

		<br />
		<sf:label path="password" cssErrorClass="errors">
			Password:
		</sf:label>
		<sf:password path="password" placeholder="Password"
			cssErrorClass="errors" />

		<br />
		<input type="submit" value="Submit" />
	</sf:form>
