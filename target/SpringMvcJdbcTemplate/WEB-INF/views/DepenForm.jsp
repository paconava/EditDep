 <%@ page language="java" contentType="text/html; charset=UTF-8"
     pageEncoding="UTF-8"%>

<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" 
	"http://www.w3.org/TR/html4/loose.dtd">
<html>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<head>

<title>Nueva/editar Subdependencia</title>
</head>
<body>
	<div align="center">
		<h1>New/Edit Subependencia</h1>
		<form:form action="saveDepen" method="post" modelAttribute="depen" accept-charset="UTF-8">
		<table>
			<form:hidden path="clave_comp"/>
			<tr>
				<td>Descripcion:</td>
				<td><form:input path="desc" /></td>
			</tr>
			<tr>
				<td colspan="2" align="center"><input type="submit" value="Save"></td>
			</tr>
		</table>
		</form:form>
	</div>
</body>
</html>