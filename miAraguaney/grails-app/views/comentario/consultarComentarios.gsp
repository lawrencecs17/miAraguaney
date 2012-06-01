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

<table align="center" width="50%" border="4">
<g:each in="${comentarios}" var="comentario">

  <tr style="color: black" >
    <td>${comentario.autor} ${comentario.fecha} ${comentario.idComentario}</td>
  </tr>
  <tr style="color: black">
    <td><div align="left">
    
    <g:if test = "${ comentario.calificacionLike == 'false' && comentario.calificacionDislike == 'false'}">
            ${comentario.cantidadLike} <g:link id="${comentario.idComentario}" controller="comentario" action="crearComentarioLike">MeGusta</g:link>
            ${comentario.cantidadDislike} <g:link id="${comentario.idComentario}" controller="comentario" action="crearComentarioDislike">NoMeGusta</g:link>
            <a>${comentario.cantidadComentados} Comentados</a>
    </g:if>
  
    <g:elseif test = "${ comentario.calificacionLike == 'true' && comentario.calificacionDislike == 'false'}">
            ${comentario.cantidadLike} MeGusta<a href="" title="Like" ></a>
            ${comentario.cantidadDislike} <g:link id="${comentario.idComentario}" controller="comentario" action="modificarComentarioDislike">NoMeGusta</g:link> 
            <a>${comentario.cantidadComentados} Comentados</a>
    </g:elseif>
    
    <g:elseif test = "${ comentario.calificacionLike == 'false' && comentario.calificacionDislike == 'true'}">
            ${comentario.cantidadLike} <g:link id="${comentario.idComentario}" controller="comentario" action="modificarComentarioLike">MeGusta</g:link> 
            ${comentario.cantidadDislike} NoMeGusta<a href="" title="Dislike"></a>
            <a>${comentario.cantidadComentados} Comentados</a>
    </g:elseif>
        
        </div>
    </td>
  </tr>
  <tr>
    <td height="96"><textarea name="textarea" cols="79%" rows="5" disabled="disabled">${comentario.mensaje}</textarea></td>
  </tr>
  <tr style="color: black">
  
  <g:if test = "${ session.nickname.equals(comentario.autor) }">
    <td height="23"><div align="center">
            <g:link id="${comentario.idComentario},${comentario.mensaje}" controller="comentario" action="modificarComentarioUsuario">Modificar</g:link> 
            <a href="" title="Responder">Responder</a>
            <g:link id="${comentario.idComentario}" controller="comentario" action="eliminarComentario">Eliminar</g:link> 
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
  

<g:each in="${comentario.comentado}" var="comentario2">

  <tr style="color: black" >
    <td>${comentario2.autor} ${comentario2.fecha} ${comentario2.principal}</td>
  </tr>
  <tr style="color: black">
    <td><div align="left">
    
    <g:if test = "${ comentario2.calificacionLike == 'false' && comentario2.calificacionDislike == 'false'}">
            <g:link id="${comentario2.idComentario}" controller="comentario" action="crearComentarioLike">MeGusta</g:link>
            <g:link id="${comentario2.idComentario}" controller="comentario" action="crearComentarioDislike">NoMeGusta</g:link>
    </g:if>
  
    <g:elseif test = "${ comentario2.calificacionLike == 'true' && comentario2.calificacionDislike == 'false'}">
            MeGusta<a href="" title="Like" ></a>
            <g:link id="${comentario2.idComentario}" controller="comentario" action="modificarComentarioDislike">NoMeGusta</g:link> 
    </g:elseif>
    
    <g:elseif test = "${ comentario.calificacionLike == 'false' && comentario2.calificacionDislike == 'true'}">
            <g:link id="${comentario.idComentario}" controller="comentario" action="modificarComentarioLike">MeGusta</g:link> 
            NoMeGusta<a href="" title="Dislike"></a>
    </g:elseif>
        
        </div>
    </td>
  </tr>
  <tr>
    <td align="center" height="96%"><textarea name="textarea" cols="65%" rows="5" disabled="disabled">${comentario2.mensaje}</textarea></td>
  </tr>
  <tr style="color: black">
  
  <g:if test = "${ session.nickname.equals(comentario2.autor) }">
    <td height="23"><div align="center">
            <g:link id="${comentario2.idComentario},${comentario2.mensaje}" controller="comentario" action="modificarComentarioUsuario">Modificar</g:link> 
            <g:link id="${comentario2.idComentario}" controller="comentario" action="eliminarComentario">Eliminar</g:link> 
        </div>
     </td>
  </g:if>
  
  </tr>
    <tr>
    <td height="4">&nbsp;</td>
  </tr>
  
</g:each>

  
  
  
  
  
  
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

