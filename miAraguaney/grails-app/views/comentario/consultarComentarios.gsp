<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>Consultar Comentario</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link rel="stylesheet" href="${resource(dir: 'css', file: 'style.css')}" type="text/css">

<script language="javascript" type="text/javascript">
function clearText(field) {
    if (field.defaultValue == field.value) field.value = '';
    else if (field.value == '') field.value = field.defaultValue;
}
</script>
</head>
<body>
<div id="header_wrapper">
  <div id="header">
    <div id="site_title">
      <h1>miAraguaney</h1>
    </div>
    <div id="menu">
      <ul>
          <li><a href="#">Token</a></li> 
          <li><a href="#">Usuario</a></li>  
          <li><a href="#">Hashtag</a></li>  
          <li><a href="#">Comentar</a>
          <li><a href="#" class="current">MiAraguaney</a></li>        
      </ul>

    </div>
    <!-- end of menu -->
    <div id="search_box">
      <form action="#" method="get">
        <input type="text" value="Enter a keyword" name="q" size="10" id="searchfield" onfocus="clearText(this)" onblur="clearText(this)" />
      </form>
    </div>
    
    <div class="cleaner"></div>
    <h2 align="left">Bienvenido ${usuario}</h2>
  </div>
  <!-- end of header -->
</div>
<!-- end of header_wrapper -->
       
<div id="content_wrapper">

<table align="center" width="50%" border="5">
<g:each in="${comentarios}" var="comentario">
  <tr style="color: black">
    <td>${comentario.autor} ${comentario.fecha}</td>
  </tr>
  <tr style="color: black">
    <td><div align="left">
            ${comentario.cantidadLike} <a href="" title="Like">Me Gusta</a>
            ${comentario.cantidadDislike} <a href="" title="Dislike">No Me Gusta</a>
            <a>${comentario.cantidadComentados} Comentados</a>
        </div>
    </td>
  </tr>
  <tr>
    <td height="96"><textarea name="textarea" cols="79%" rows="8" disabled="disabled">${comentario.mensaje}</textarea></td>
  </tr>
  <tr style="color: black">
  <g:if test = "${ session.nickname.equals(comentario.autor) }">
    <td height="23"><div align="center">
            <a href="" title="Modificar">Modificar</a>
            <a href="" title="Responder">Responder</a>
            <a href="" title="Eliminar">Eliminar</a>
        </div>
     </td>
  </g:if>
  <g:else>
        <td height="23"><div align="center">
            <a href="" title="Responder">Responder</a>
            </div>
        </td>
  </g:else>
  </tr>
    <tr>
    <td height="4">&nbsp;</td>
  </tr>
  
</g:each>
</table>

  <!-- end of content -->
  <!-- end of sidebar -->
  <div class="cleaner"></div>
</div>
<div id="content_wrapper_bottom"></div>
<!-- end of content_wrapper -->
<div id="footer">
  <nav id="nav">
      <ul class="footer_menu">
        <li><a href="#miaraguaney">MiAraguaney</a></li>
        <li><a href="#">Comentar</a></li>
        <li><a href="#">Hashtag</a></li>
      </ul>
    
  
        <ul class="footer_credit">
          
          <li>Diseñado y Desarrollado por:</li>
          <li>Lawrence Cermeño | Sara Villarreal | Ricardo Portela</li>
          <li>Copyright &copy; 2012</li>
          
        </ul>
        
  </nav>
  </div>
<!-- end of footer -->
</body>
</html>

