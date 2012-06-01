<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>Perfil Usuario</title>
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
      	  <li><g:link controller="usuario" action="cerrarSesion"> Salir</g:link>
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
    <h3 align="left">Bienvenido ${usuario}</h3>
  </div>
  <!-- end of header -->
</div>

<!-- end of header_wrapper -->
<div id="content_wrapper">
<form id="form1" name="form1" method="post" action="modificarUsuario">
  
  <p>&nbsp;</p>
  <table width="491" border="0" align="center">
  <tr>
    <td colspan="3" align="center"><p>Introduce los siguientes datos:</p></td>
    </tr>
  
    
  <tr>
    <td colspan="3"><p>&nbsp;</p></td>
    </tr>
  <tr>
    <td width="93">Email: </td>
    <td colspan="2"><input type="text" name="email" id="email"  /></td>
  </tr>
  <tr>
    <td>Password: </td>
    <td colspan="2"><input type="password" name="password" id="password" /></td>
    </tr>
  <tr>
    <td height="54">&nbsp;</td>
    <td width="215" align="center"><label>
      <input type="submit" name="btnentrar" id="btnentrar" value="Buscar" />
    </label>
      <div></div></td>
    <td width="169" align="center"><input type="button" name="btncancelar" id="btncancelar" value="Cancelar" /></td>
    </tr>
  </table>
  <p>&nbsp;</p>
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
