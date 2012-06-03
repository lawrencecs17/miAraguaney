<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>Perfil Usuario</title>
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
                    <li class="first"><g:link controller="usuario" action="vistaModificarUsuario">Modificar</g:link></li>
                    <li><g:link controller="usuario" action="vistaActivarUsuario">Activar</g:link></li>
                    <li class="last"><g:link controller="usuario" action="vistaEliminarUsuario">Desactivar</g:link></li>
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
            <li><a href="#" class="last">MiAraguaney</a></li><!-- se le coloca una clase al primro y al ultimo para trabajar los bordes-->
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
   <h3 align="left"><img src="${resource( dir:'images/fotoPerfil',file:"${usuario.nickname}.png")}" width="80" height="60"  /> Bienvenido ${usuario.nickname}</h3>
  </div>
  <!-- end of header -->
</div>
<!-- end of header_wrapper -->
<div id="content_wrapper">
<form id="form1" name="form1" method="post" action="modificarDatosUsuario">
  
  <p>&nbsp;</p>
  <table width="632" border="0" align="center">
  <tr>
    <td width="50">&nbsp;</td>
    <td width="93">&nbsp;</td>
    <td colspan="2" align="center">Modificar Usuario</td>
  </tr>
  <tr>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td colspan="2">&nbsp; </td>
    </tr>
  <tr>
    <td>&nbsp;</td>
    <td>Nombre: </td>
    <td colspan="2"><input type="text" name="nombre" id="nombre" value="${usuario.nombre}" required="required" /></td>
    </tr>
  <tr>
    <td>&nbsp;</td>
    <td>Apellido: </td>
    <td colspan="2"><input type="text" name="apellido" id="apellido" value="${usuario.apellido}"  required="required"/></td>
    </tr>
  <tr>
    <td>&nbsp;</td>
    <td><p>Biografia:</p></td>
    <td colspan="2"><label>
      <textarea name="biografia" id="biografia"  required="required">${usuario.biografia} </textarea>
    </label></td>
    </tr>
  <tr>
    <td>&nbsp;</td>
    <td>Email: </td>
    <td colspan="2"><label>
      <input name="email2" type="text" id="email2" value="${usuario.email2}"  required="required"/>
    </label></td>
    </tr><tr>
    <td>&nbsp;</td>
    <td>Fecha Nac:</td>
    <td colspan="2"><label>
      <input name="fecha" type="text" id="fecha" value="${usuario.fechaRegistro}"   required="required"/>
    </label></td>
    </tr>
    
  <tr>
    <td>&nbsp;</td>
    <td>Nickname: </td>
    <td colspan="2"><label>
      <input type="text" name="nickname" id="nickname" value="${usuario.nickname}"  required="required"/>
    </label></td>
    </tr>
  <tr>
    <td>&nbsp;</td>
    <td>Pais: </td>
    <td colspan="2"><label>
      <input type="text" name="pais" id="pais"  value="${usuario.pais}"  required="required"/>
    </label></td>
    </tr>
  <tr>
    <td>&nbsp;</td>
    <td>Password: </td>
    <td colspan="2"><input type="password" name="password" id="password" value="${usuario.password}"  required="required"/></td>
    </tr>
  <tr>
    <td>&nbsp;</td>
    <td><input type="hidden" name="email" id="email" value="${usuario.email}"  /></td>
    <td colspan="2">&nbsp;</td>
    </tr>
  <tr>
    <td height="54">&nbsp;</td>
    <td>&nbsp;</td>
    <td width="215" align="center"><label>
      <input type="submit" name="btnmodificar" id="btnmodificar" value="Modificar" />
    </label>
      <div></div></td>
    <td width="256" align="center"><input type="button" name="btncancelar" id="btncancelar" value="Cancelar" /></td>
    </tr>
  <tr>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td colspan="2">&nbsp;</td>
    </tr>
  </table>
  <p>&nbsp;</p>
</form></label>

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
