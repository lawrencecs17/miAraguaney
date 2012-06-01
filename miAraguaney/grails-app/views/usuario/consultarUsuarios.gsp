<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>Consultar Usuarios</title>
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
          <li><a href="../token/consultarTokens.gsp">Token</a></li> 
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

<form id="form1" name="form1" method="post" action="consultarTodosLosTokens">

<h1 align="center"> Usuarios del Cliente miAraguaney app </h1>


<table border="0" align="center">
		<tr  >
			<td width="116" align="center"><strong>Activo</strong></td>
			<td width="116" align="center"><strong>Nombre</strong></td>
			<td width="117"align="center" ><strong>Apellido</strong></td>
			<td width="112" align="center"><strong>Pais</strong></td>
			<td width="138"align="center"><strong>Email</strong></td>			
			<td width="449"align="center"><strong>Biografia</strong></td>
		</tr>
	<g:each in="${usuarios}" var="usuario">
		<tr>
			<td align="center">${usuario.activo }</td>
			<td align="center"> ${usuario.nombre}</td>
			<td align="center">${usuario.apellido}</td>
			<td align="center">${usuario.pais }</td>
			<td align="center" >${usuario.email }</td>			
			<td align="center">${usuario.biografia }</td>			
		</tr>
	</g:each>
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

