<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE htmlPUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
    <title>Recipe</title>
  </head>

  <body>
    <form:form commandName="recipe" method="post" action="/isis/save">
    <p>
      <form:hidden path="id" id="id"/>
      <label>Message</label><form:textarea path="message" id="message"/>k      
    </p>
    <p>
      <input type="submit" name="send" value="Send"/>
    </p>        
    </form:form>
  </body>

</html>
