<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>Modificar Usuario</title>
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
    <h3 align="left">Bienvenido ${usuario.nickname}</h3>
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
    <td colspan="2"><input type="text" name="nombre" id="nombre" value="${usuario.nombre}" /></td>
    </tr>
  <tr>
    <td>&nbsp;</td>
    <td>Apellido: </td>
    <td colspan="2"><input type="text" name="apellido" id="apellido" value="${usuario.apellido}"/></td>
    </tr>
  <tr>
    <td>&nbsp;</td>
    <td><p>Biografia:</p></td>
    <td colspan="2"><label>
      <textarea name="biografia" id="biografia">${usuario.biografia}</textarea>
    </label></td>
    </tr>
  <tr>
    <td>&nbsp;</td>
    <td>Email: </td>
    <td colspan="2"><label>
      <input name="email2" type="text" id="email2" value="${usuario.email2}"/>
    </label></td>
    </tr><tr>
    <td>&nbsp;</td>
    <td>Fecha Nac:</td>
    <td colspan="2"><label>
      <input name="fecha" type="text" id="fecha" value="${usuario.fechaRegistro}" />
    </label></td>
    </tr>
    
  <tr>
    <td>&nbsp;</td>
    <td>Nickname: </td>
    <td colspan="2"><label>
      <input type="text" name="nickname" id="nickname" value="${usuario.nickname}" />
    </label></td>
    </tr>
  <tr>
    <td>&nbsp;</td>
    <td>Pais: </td>
    <td colspan="2"><label>
      <input type="text" name="pais" id="pais"  value="${usuario.pais}"/>
    </label></td>
    </tr>
  <tr>
    <td>&nbsp;</td>
    <td>Password: </td>
    <td colspan="2"><input type="password" name="password" id="password" value="${usuario.password}"/></td>
    </tr>
  <tr>
    <td>&nbsp;</td>
    <td><input type="hidden" name="email" id="email" value="${usuario.email}" /></td>
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

