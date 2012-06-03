<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>Modificar Comentario</title>
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
<h3 align="center" style="color: #557C12">Modificar Comentario</h3>
<g:form id="${comentario}" name="form1" method="post"  action="modificarComentario" >

<table align="center" width="50%" border="5">
  <tr>
    <td height="96%">
    	<textarea name="mensaje" cols="79%" rows="4" >${mensaje}</textarea>
    </td>
  </tr>
    
   <tr>
      <td height="23">
      <div align="center">
         <form name="form1" method="post" action="">
         <label>
               <input type="submit" name="button" id="button" value="Modificar">
         </label>
         </form>
      </div>
      </td>
   </tr>
     
</table>
  <p>&nbsp;</p>
</g:form>

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