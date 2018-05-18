<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Manejador de Subdependencias</title>
    </head>
    <body>
    	<div align="center">
	        <h1>Lista de Subependencias</h1>
	        <h3><a href="newDepen">Nueva Subdependencia</a></h3>
	        <table border="1">
	        	<th>Clave</th>
	        	<th>ClaveComp</th>
	        	<th>Nombre</th>
	        	<th>Acción</th>
	        	
				<c:forEach var="depen" items="${listDepen}" varStatus="status">
	        	<tr>
	        		<td>${depen.clave_ds}</td>
	        		<td>${depen.clave_comp}</td>
					<td>${depen.desc}</td>
					<td>
						<a href="editDepen?clave_comp=${depen.clave_comp}">Editar</a>
						&nbsp;&nbsp;&nbsp;&nbsp;
						<a href="deleteDepen?clave_comp=${depen.clave_comp}">Borrar</a>
					</td>
							
	        	</tr>
				</c:forEach>	        	
			</table>
    	</div>
    </body>
</html>
