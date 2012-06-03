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
            <li><g:link controller="usuario" action="index">MiAraguaney</g:link></li><!-- se le coloca una clase al primro y al ultimo para trabajar los bordes-->
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
  </div>
  <!-- end of header -->
</div>
<!-- end of header_wrapper -->
<div id="content_wrapper">
<h3 align="center" style="color: #557C12">Registrar Usuario</h3>

<form id="form1" name="form1" method="post" action="registrarUsuario">
  
  <p>&nbsp;</p>
  <table width="400px" border="0" align="center">
  <tr>
    <td colspan="3" align="center"><p><strong>Registrar Usuario</strong></p>
      <p>&nbsp;</p></td>
    </tr>
  <tr>
    <td width="184">Nombre: </td>
    <td colspan="2"><input type="text" name="nombre" id="nombre" required="required"/></td>
  </tr>
  <tr>
    <td>Apellido: </td>
    <td colspan="2"><input type="text" name="apellido" id="apellido" required="required" /></td>
    </tr>
  <tr>
    <td><p>Biografia:</p></td>
    <td colspan="2"><label>
      <textarea name="biografia" id="biografia" height="48px" required="required"></textarea>
    </label></td>
    </tr>
  <tr>
    <td>Email: </td>
    <td colspan="2"><label>
      <input name="email" type="text" id="email" value="" required="required"/>
    </label></td>
    </tr><tr>
    <td>Fecha Nac:</td>
    <td colspan="2"><label>
      <input name="fecha" type="text" id="fecha" value="" required="required"/>
    </label></td>
    </tr>
    
  <tr>
    <td>Nickname: </td>
    <td colspan="2"><label>
      <input type="text" name="nickname" id="nickname" required="required" />
    </label></td>
    </tr>
  <tr>
    <td>Pais: </td>
    <td colspan="2"><label>
      <input type="text" name="pais" id="pais" required="required"/>
    </label></td>
    </tr>
  <tr>
    <td>Password: </td>
    <td colspan="2"><input type="password" name="password" id="password" required="required"/></td>
    </tr>
  <tr>
    <td  colspan="2" align="center"><label>
      <input type="submit" name="btnregistrar" id="btnregistrar" value="Registrar" />
      </label>
      </td>
    <td   align="center">
    <input type="button" name="btnregresar" id="btnregresar" value="regresar"  onclick=" location.href='../' " /></td>    
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
