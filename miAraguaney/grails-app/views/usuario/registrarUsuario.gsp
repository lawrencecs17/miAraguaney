<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>Registrar Usuario</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
 <link rel="stylesheet" href="${resource(dir: 'css', file: 'style.css')}" type="text/css">
 <link rel="stylesheet" href="${resource(dir: 'css', file: 'grid_12.css')}" type="text/css">
 <link rel="stylesheet" href="${resource(dir: 'css', file: 'menu.css')}" type="text/css">

<!-- JavaScript Calendario -->
    <link type="text/css" rel="stylesheet" href="${resource(dir: 'css', file: 'jscal2.css')}" />  
    <script src="${resource(dir: 'js', file: 'jscal2.js')}"></script> 
    <!-- this must stay last so that English is the default one -->
    <script src="${resource(dir: 'js', file: 'es.js')}"></script>
    
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
    <td>Nombre: </td>
    <td colspan="2"><input type="text" name="nombre" id="nombre" required="required"/></td>
  </tr>
  <tr>
    <td>Apellido: </td>
    <td colspan="2" ><input type="text" name="apellido" id="apellido" required="required" /></td>
    </tr>
  <tr>
    <td><p>Biografia:</p></td>
    <td colspan="2" ><label>
      <textarea name="biografia" id="biografia" height="48px" required="required"></textarea>
    </label></td>
    </tr>
  <tr>
    <td>Email: </td>
    <td colspan="2"><label>
      <input name="email" type="email" id="email" value="" required="required"/>
    </label></td>
    </tr><tr>
    <td>Fecha Nac:</td>
    <td colspan="2">
    <span>
        <input id="fechaRegistro" name="fechaRegistro" type="text" required="required" class="field text" value="" size="10" tabindex="1"  />
                <button id="f_rangeStart_trigger">...</button>
                <script type="text/javascript">
                  RANGE_CAL_1 = new Calendar({
                          inputField: "fechaRegistro",
                          dateFormat: "%Y-%m-%d",
                          trigger: "f_rangeStart_trigger",
                          bottomBar: false,
                          onSelect: function() {
                                  var date = Calendar.intToDate(this.selection.get());
                                  LEFT_CAL.args.min = date;
                                  LEFT_CAL.redraw();
                                  this.hide();
                          }
                  });
                </script>
                </span>
    </td>
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
      <select id="pais" name="pais">
		<option value="Afghanistan">Afghanistan</option>
    	<option value="Albania">Albania</option>
		<option value="Algeria">Algeria</option>
        <option value="American Samoa">American Samoa</option>
        <option value="Andorra">Andorra</option>
        <option value="Angola">Angola</option>
        <option value="Anguilla">Anguilla</option>
        <option value="Antigua and Barbuda">Antigua and Barbuda</option>
        <option value="Argentina">Argentina</option>
        <option value="Armenia">Armenia</option>
        <option value="Aruba">Aruba</option>
        <option value="Ascension Island">Ascension Island</option>
        <option value="Australia">Australia</option>
        <option value="Austria">Austria</option>
        <option value="Azerbaijan">Azerbaijan</option>
        <option value="Bahrain">Bahrain</option>
        <option value="Bangladesh">Bangladesh</option>
        <option value="Barbados">Barbados</option>
        <option value="Belarus">Belarus</option>
        <option value="Belgium">Belgium</option>
        <option value="Belize">Belize</option>
        <option value="Benin">Benin</option>
        <option value="Bermuda">Bermuda</option>
        <option value="Bhutan">Bhutan</option>
        <option value="Bolivia">Bolivia</option>
        <option value="Bosnia and Herzegovina">Bosnia and Herzegovina</option>
        <option value="Botswana">Botswana</option>
        <option value="Brazil">Brazil</option>
        <option value="Brit Indian Ocean Terr">Brit Indian Ocean Terr</option>
        <option value="Brunei Darussalam">Brunei Darussalam</option>
        <option value="Bulgaria">Bulgaria</option>
        <option value="Burkina Faso">Burkina Faso</option>
        <option value="Burundi">Burundi</option>
        <option value="Cambodia">Cambodia</option>
        <option value="Cameroon">Cameroon</option>
        <option value="Canada">Canada</option>
        <option value="Cape Verde">Cape Verde</option>
        <option value="Cayman Islands">Cayman Islands</option>
        <option value="Central African Republic">Central African Republic</option>
        <option value="Chad">Chad</option>
        <option value="Chile">Chile</option>
        <option value="China">China</option>
        <option value="Colombia">Colombia</option>
        <option value="Comoros">Comoros</option>
        <option value="Congo">Congo</option>
        <option value="Cook Islands">Cook Islands</option>
        <option value="Costa Rica">Costa Rica</option>
        <option value="Cote D Ivoire">Cote D Ivoire</option>
        <option value="Croatia">Croatia</option>
        <option value="Cuba">Cuba</option>an</option>
        <option value="Kenya">Kenya</option>
        <option value="Kiribati">Kiribati</option>
        <option value="Kuwait">Kuwait</option>
        <option value="Kyrgyzstan">Kyrgyzstan</option>
        <option value="Laos">Laos</option>
        <option value="Latvia">Latvia</option>
        <option value="Lebanon">Lebanon</option>
        <option value="Lesotho">Lesotho</option>
        <option value="Liberia">Liberia</option>
        <option value="Libya">Libya</option>
        <option value="Liechtenstein">Liechtenstein</option>
        <option value="Lithuania">Lithuania</option>
        <option value="Luxembourg">Luxembourg</option>
        <option value="Macau">Macau</option>
        <option value="Macedonia">Macedonia</option>
        <option value="Madagascar">Madagascar</option>
        <option value="Malawi">Malawi</option>
        <option value="Malaysia">Malaysia</option>
        <option value="Maldives">Maldives</option>
        <option value="Mali">Mali</option>
        <option value="Malta">Malta</option>
        <option value="Marshall Islands">Marshall Islands</option>
        <option value="Martinique">Martinique</option>
        <option value="Mauritania">Mauritania</option>
        <option value="Mauritius">Mauritius</option>
        <option value="Mayotte">Mayotte</option>
        <option value="Mexico">Mexico</option>
        <option value="Moldova">Moldova</option>
        <option value="Monaco">Monaco</option>
        <option value="Mongolia">Mongolia</option>
        <option value="Montenegro">Montenegro</option>
        <option value="Montserrat">Montserrat</option>
        <option value="Morocco">Morocco</option>
        <option value="Mozambique">Mozambique</option>
        <option value="Myanmar">Myanmar</option>
        <option value="Namibia">Namibia</option>
        <option value="Nauru">Nauru</option>
        <option value="Nepal">Nepal</option>
        <option value="Netherlands">Netherlands</option>
        <option value="Netherlands Antilles">Netherlands Antilles</option>
        <option value="New Caledonia">New Caledonia</option>
        <option value="New Zealand">New Zealand</option>
        <option value="Nicaragua">Nicaragua</option>
        <option value="Niger">Niger</option>
        <option value="Nigeria">Nigeria</option>
        <option value="Niue">Niue</option>
        <option value="Norfolk Island">Norfolk Island</option>
        <option value="Trinidad and Tobag">Trinidad and Tobag</option>
        <option value="Tunisia">Tunisia</option>
        <option value="Turkey">Turkey</option>
        <option value="Turkmenistan">Turkmenistan</option>
        <option value="Turks/Caicos Islands">Turks/Caicos Islands</option>
        <option value="Tuvalu">Tuvalu</option>
        <option value="Uganda">Uganda</option>
        <option value="Ukraine">Ukraine</option>
        <option value="United Arab Emirates">United Arab Emirates</option>
        <option value="United Kingdom">United Kingdom</option>
        <option value="United States of America">United States of America</option>
        <option value="Uruguay">Uruguay</option>
        <option value="Uzbekistan">Uzbekistan</option>
        <option value="Vanuatu">Vanuatu</option>
        <option value="Venezuela">Venezuela</option>
        <option value="Vietnam">Vietnam</option>
        <option value="Virgin Islands (U.K)">Virgin Islands (U.K)</option>
        <option value="Virgin Islands (U.S)">Virgin Islands (U.S)</option>
        <option value="Wallis/Futuna Islands">Wallis/Futuna Islands</option>
        <option value="Western Samoa">Western Samoa</option>
        <option value="Yemen">Yemen</option>
        <option value="Zambia">Zambia</option>
        <option value="Zimbabwe">Zimbabwe</option>
    </select>
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
