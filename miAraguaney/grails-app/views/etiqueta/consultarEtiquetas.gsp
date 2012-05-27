<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>Documento sin título</title>
</head>

<body>

<h1 align="center"> Etiquetas del Cliente miAraguaney app </h1>


<table border="0" align="center">
		<tr>
			<td width="100px" ><strong>Nombre</strong></td>
		</tr>
	<g:each in="${etiquetas}" var="etiqueta">
		<tr>
			<td>${etiqueta}</td>		
		</tr>
	</g:each>
</table>
</body>
</html>