<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>Documento sin título</title>
</head>

<body>

<h1 align="center"> Usuarios del Cliente miAraguaney app </h1>


<table border="0" align="center">
		<tr  >
			<td width="50px"><strong>Activo</strong></td>
			<td width="100px" ><strong>Nombre</strong></td>
			<td width="100px"><strong>Apellido</strong></td>
			<td width="100px"><strong>Pais</strong></td>
			<td width="200px"><strong>Email</strong></td>			
			<td width="400px"><strong>Biografia</strong></td>
		</tr>
	<g:each in="${usuarios}" var="usuario">
		<tr>
			<td>${usuario.activo }</td>
			<td> ${usuario.nombre}</td>
			<td>${usuario.apellido}</td>
			<td>${usuario.pais }</td>
			<td>${usuario.email }</td>			
			<td>${usuario.biografia }</td>			
		</tr>
	</g:each>
</table>
</body>
</html>