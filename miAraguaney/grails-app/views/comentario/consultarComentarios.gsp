<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>Consultar Comentarios</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
 <link rel="stylesheet" href="${resource(dir: 'css', file: 'style.css')}" type="text/css">
 <link rel="stylesheet" href="${resource(dir: 'css', file: 'grid_12.css')}" type="text/css">
 <link rel="stylesheet" href="${resource(dir: 'css', file: 'menu.css')}" type="text/css">

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
      <h1>MiAraguaney</h1>
    </div>
<!-- NUEVO MENU-->    

<div class="container_12">
      <ul class="menuh grid_10"><!-- ordenada o desordenada-->
          <li><a href="#" class="first">Token</a>
            <ul class="submenu">
                  <li><a href="#" class="first">Iniciar Sesion</a></li>
                  <li><g:link controller="usuario" action="cerrarSesion"> Salir</g:link></li>
                  <li><a href="#" class="last">Consultar Token</a></li>
            </ul>
          </li>
            <li>
                <a href="#" class="first">HashTag</a>
                <ul class="submenu">
                    <li>
                    	<g:link controller="etiqueta" action="consultarTodasLasEtiquetas">Consultar Todos..</g:link>
                    </li>
                </ul>
            </li>
            <li>
                <a href="#" class="meddle">Usuario</a>
                <ul class="submenu">
                    <li><a href="#" class="first">Registar</a></li>
                    <li><a href="#">Consultar</a></li>
                    <li><a href="#">Modificar</a></li>
                    <li><a href="#">Activar</a></li>
                    <li><a href="#" class="last">Desactivar</a></li>
                </ul>
            </li>
            <li>
                <a href="#" class="meddle">Comentarios</a>
                <ul class="submenu">
                    <li><g:link controller="comentario" action="consultarTodosLosComentarios">Consultar Todos</g:link></li>
                    <li><g:link controller="comentario" action="busquedaEtiqueta">Consultar C.Tag</g:link></li>
                    <li><a href="#">Consultar S.Tag</a></li>
                </ul>
            </li>
            <li><g:link class="last" controller="comentario" action="consultarComentarioPorUsuario">MiAraguaneys</g:link></li>
           <!-- se le coloca una clase al primro y al ultimo para trabajar los bordes-->
        </ul>
    </div>
<!-- FIN NUEVO MENU-->

    <!-- end of menu -->
    <div id="search_box">
      <form action="#" method="get">
        <input type="text" value="Enter a keyword" name="q" size="10" id="searchfield" onfocus="clearText(this)" onblur="clearText(this)" />
      </form>
    </div>
    <div class="cleaner"></div>
   <h3 align="left"><img src="${resource( dir:'images/fotoPerfil',file:"${usuario}.png")}" width="80" height="60"  /> Bienvenido ${usuario}</h3>
  </div>
  <!-- end of header -->
</div>
<!-- end of header_wrapper -->
       
<div id="content_wrapper">
<h3 align="center" style="color: #557C12">Comentarios Generales</h3>

<g:each in="${comentarios}" var="comentario">
<table align="center" width="50%" border="12" class="law">
  	<tr style="color: black" bgcolor="#557C12">
    	<td>${comentario.autor} ${comentario.fecha}</td>
  	</tr>
  	<tr style="color: black">
    	<td>
    	<div align="left">
    
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
    	<td height="76%"><textarea name="textarea" cols="79%" rows="4" disabled="disabled">${comentario.mensaje}</textarea></td>
  	</tr>
  	<tr style="color: black">
  
  	<g:if test = "${ session.nickname.equals(comentario.autor) }">
    	<td height="23">
    	<div align="center">
            <g:link id="${comentario.idComentario},${comentario.mensaje}" controller="comentario" action="modificarComentarioUsuario">Modificar</g:link> 
            <g:link id="${comentario.idComentario}" controller="comentario" action="responderComentarioUsuario">Responder</g:link> 
            <g:link id="${comentario.idComentario}" controller="comentario" action="eliminarComentario">Eliminar</g:link> 
        </div>
     	</td>
  	</g:if>
  	<g:else>
        <td height="23"><div align="center">
            <g:link id="${comentario.idComentario}" controller="comentario" action="responderComentarioUsuario">Responder</g:link> 
            </div>
        </td>
  	</g:else>
  
  	</tr>
  	
    <tr>
    	<td height="4">&nbsp;</td>
  	</tr>
  	
	<g:each in="${comentados}" var="comentario2">

		<g:if test = "${ comentario2.idComentarioComentado == comentario.idComentario }">
    
		  	<tr style="color: black" bgcolor="#9FC740">
		    	<td>${comentario2.autor} ${comentario2.fecha}</td>
		  	</tr>
		  	<tr style="color: black">
		    	<td>
		    	<div align="left">
		    
		    	<g:if test = "${ comentario2.calificacionLike == 'false' && comentario2.calificacionDislike == 'false'}">
		            ${comentario2.cantidadLike} <g:link id="${comentario2.idComentario}" controller="comentario" action="crearComentarioLike">MeGusta</g:link>
		            ${comentario2.cantidadDislike} <g:link id="${comentario2.idComentario}" controller="comentario" action="crearComentarioDislike">NoMeGusta</g:link>
		    	</g:if>
		  
		    	<g:elseif test = "${ comentario2.calificacionLike == 'true' && comentario2.calificacionDislike == 'false'}">
		            ${comentario2.cantidadLike} MeGusta<a href="" title="Like" ></a>
		            ${comentario2.cantidadDislike} <g:link id="${comentario2.idComentario}" controller="comentario" action="modificarComentarioDislike">NoMeGusta</g:link> 
		    	</g:elseif>
		    
		    	<g:elseif test = "${ comentario2.calificacionLike == 'false' && comentario2.calificacionDislike == 'true'}">
		            ${comentario2.cantidadLike} <g:link id="${comentario2.idComentario}" controller="comentario" action="modificarComentarioLike">MeGusta</g:link> 
		            ${comentario2.cantidadDislike} NoMeGusta<a href="" title="Dislike"></a>
		    	</g:elseif>
		        
		        </div>
		    	</td>
		  	</tr>
		  	<tr>
		    	<td align="center" height="76%"><textarea name="textarea" cols="79%" rows="4" disabled="disabled">${comentario2.mensaje}</textarea></td>
		  	</tr>
		  	<tr style="color: black">
		  
		  		<g:if test = "${ session.nickname.equals(comentario2.autor) }">
		    		<td height="23">
		    		<div align="center">
		            	<g:link id="${comentario2.idComentario},${comentario2.mensaje}" controller="comentario" action="modificarComentarioUsuario">Modificar</g:link> 
		            	<g:link id="${comentario2.idComentario}" controller="comentario" action="eliminarComentario">Eliminar</g:link> 
		        	</div>
		     		</td>
		  		</g:if>

		  	</tr>
		    <tr>
		    	<td height="4">&nbsp;</td>
		  	</tr>
		 		
		</g:if>
		
	</g:each>
</table>
</g:each>

<p>&nbsp;</p>
	<form id="form1" name="form1" method="post" action="consultarComentarioPorUsuario">
		 <div align="center">
		     <label>
		        <input type="submit" name="button" id="button" value="Volver">
		     </label>
		 </div>
	</form>

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

