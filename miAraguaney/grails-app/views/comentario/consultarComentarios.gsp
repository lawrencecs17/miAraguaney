<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>Documento sin título</title>
</head>

<body>

<h1 align="center"> Comentarios miAraguaney app </h1>

<table align="center" width="50%" border="11">
<g:each in="${comentarios}" var="comentario">
  <tr>
    <td>${comentario.autor} ${comentario.fecha}</td>
  </tr>
  <tr>
    <td><div align="left">
            <a href="" title="Like">Me Gusta</a>
            <a href="" title="Dislike">No Me Gusta</a>
        </div>
    </td>
  </tr>
  <tr>
    <td height="96"><textarea name="textarea" cols="79%" rows="8" disabled="disabled">${comentario.mensaje}</textarea></td>
  </tr>
  <tr>
    <td height="23"><div align="center">
            <a href="" title="Responder">Responder</a>
        </div>
     </td>
  </tr>
    <tr>
    <td height="4">&nbsp;</td>
  </tr>
  
</g:each>
</table>

</body>
</html>
