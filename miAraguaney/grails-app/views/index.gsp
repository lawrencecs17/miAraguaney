<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>Iniciar Session</title>
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
  </div>
  <!-- end of header -->
</div>
<!-- end of header_wrapper -->
<div id="content_wrapper">
    <form id="form1" name="formLogin" method="post" action="usuario/iniciarSesion">
        <table width="400" border="0" align="center">
          <tr>
            <td colspan="2" align="center"><p><span>Iniciar Sesion</span></p>
            <p>&nbsp;</p></td>
          </tr>
          <tr>
            <td><label>email</label>&nbsp;</td>
            <td align="right"><label>
              <input type="text" name="email" id="email" width="200px" />
            </label></td>
          </tr>
          <tr>
            <td><label>password</label>&nbsp;</td>
            <td align="right"><label>
              <input type="password" name="password" id="password" width="200px" />
            </label></td>
          </tr>
          <tr>
            <td align="center" width="200px">
            <p>             
                <input type="submit" name="aceptar" id="aceptar" value="aceptar" />
            </p>
            </td>
            <td align="center">
            <g:link controller="usuario" action="vistaRegistroUsuario">registrate!</g:link>            
            </td>
            </tr>
        </table>
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
