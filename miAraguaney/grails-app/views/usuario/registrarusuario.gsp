<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>Documento sin título</title>
<style type="text/css">
<!--
#form2 h1 {
	text-align: center;
}
#form1 table {
	text-align: left;
}
#form1 table tr td {
	font-family: "Times New Roman", Times, serif;
	font-size: 18px;
}
-->
</style>
</head>

<body>
<label>
<p>&nbsp;</p>
<form id="form1" name="form1" method="post" action="">
  
  <p>&nbsp;</p>
  <table width="632" border="0" align="center">
  <tr>
    <td width="50">&nbsp;</td>
    <td width="93">&nbsp;</td>
    <td colspan="2" align="center" bgcolor="#996600">Registrar Usuario</td>
  </tr>
  <tr>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td colspan="2">&nbsp; ${usuarios.get(0).nombre}</td>
    </tr>
  <tr>
    <td>&nbsp;</td>
    <td>Nombre: </td>
    <td colspan="2"><input type="text" name="textfield2" id="textfield2" /></td>
    </tr>
  <tr>
    <td>&nbsp;</td>
    <td>Apellido: </td>
    <td colspan="2"><input type="text" name="textfield3" id="textfield3" /></td>
    </tr>
  <tr>
    <td>&nbsp;</td>
    <td><p>Biografia:</p></td>
    <td colspan="2"><label>
      <textarea name="textbio" id="textbio"></textarea>
    </label></td>
    </tr>
  <tr>
    <td>&nbsp;</td>
    <td>Email: </td>
    <td colspan="2"><label>
      <input name="textemail" type="text" id="textemail" value="" />
    </label></td>
    </tr>
  <tr>
    <td>&nbsp;</td>
    <td>Nickname: </td>
    <td colspan="2"><label>
      <input type="text" name="textnick" id="textnick" />
    </label></td>
    </tr>
  <tr>
    <td>&nbsp;</td>
    <td>Pais: </td>
    <td colspan="2"><label>
      <input type="text" name="textpais" id="textpais" />
    </label></td>
    </tr>
  <tr>
    <td>&nbsp;</td>
    <td>Password: </td>
    <td colspan="2"><input type="password" name="textfield" id="textfield" /></td>
    </tr>
  <tr>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td colspan="2">&nbsp;</td>
    </tr>
  <tr>
    <td height="54">&nbsp;</td>
    <td>&nbsp;</td>
    <td width="215" align="center"><label>
      <input type="submit" name="btnregistrar" id="btnregistrar" value="Registrar" />
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
</body>
</html>
