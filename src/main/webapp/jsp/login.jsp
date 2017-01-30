
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

	<h1>My Login Form</h1>

	<sf:form method="post" action="login">

		<sf:errors element="div" cssClass="errors" />

		<label for="username">
			Username: 
		</label>
		<input type="text" name="username" placeholder="Username" />

		<br />
		<label for="password" >
			Password:
		</label>
		<input type="password" name="password" placeholder="Password" />

		
<!-- 		<input type="hidden" name="_csrf" /> -->
		<br />
		<input type="checkbox" name="remember-me" /> 
		<label for="remember-me"> Rememeber me</label>
		
		<br />
		<input type="submit" value="Submit" />
	</sf:form>
