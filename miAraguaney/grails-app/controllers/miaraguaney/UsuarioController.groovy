package miaraguaney

import groovyx.net.http.HTTPBuilder
import static groovyx.net.http.Method.*
import static groovyx.net.http.ContentType.*
import grails.converters.*
import java.util.Date
import org.apache.commons.logging.*
import groovy.xml.MarkupBuilder

/**
 *
 * @author Lawrence Cermeño
 * @author Sara Villarreal
 * @author Ricardo Portela
 *
 */

class UsuarioController {
	
	private static Log log = LogFactory.getLog("Logs."+UsuarioController.class.getName())
	String bandera = "miOrquidea"
	//String bandera = "Spring"

		def index = {								 
			
			if(session.usuario)
			{
				redirect (action :'vistaPerfil', model:[usuario:session.nickname])
			}
			else
			{
				render(view:"../index")
			}
		}
		
		/*
		 * Accion para consumir servicios de iniciar sesion
		 */
		def iniciarSesion = {
		
				/**
				 * Se inicializa la variable que recibe la respuesta del servidor
				 */
				def serviceResponse = "No hay respuesta"
				/**
				 * Se establece la URL de la ubicacion
				 * del servicio
				 */
				def url = new URL("http://localhost:8080/miOrquidea/token/iniciarSesion" )
				/**
				 * Se extraen los parametros y convierte a formato
				 * XML para luego ser enviada a la aplicacion miOrquidea
				 */
				
				def gXml = new StringWriter()
				def xml = new MarkupBuilder(gXml)
				def redireccion = "../"
				/**
				 * Se construye el XML que se enviara al servicio
				 */
				xml.usuario() {
					email(params.email)
					password(params.password)
				}
				/**
				 * Se establece el tipo de conexion
				 */
				/*****************************************************************/
				def connection = url.openConnection()
				connection.setRequestMethod("POST")
				connection.setRequestProperty("Content-Type" ,"text/xml" )
				connection.doOutput=true
				Writer writer = new OutputStreamWriter(connection.outputStream)
				writer.write(gXml.toString())
				writer.flush()
				writer.close()
				connection.connect()
				/*****************************************************************/
				
				/**
				 * Se recoge la respuesta por parte del servidor
				 */
				def miXml = new XmlSlurper().parseText(connection.content.text)
				serviceResponse = miXml.mensaje
				/**
				 * Caso de exito de incio de sesion
				 */
				if(serviceResponse == "")
				{
					serviceResponse = "El usuario $params.email ha inciado sesion correctamente"
					def nickname = buscarUsuarioPorCorreo (params.email)
					//session.nickname = params.nickname
					session.email = params.email
					session.password = params.password
					obtenerUsuario()
					redirect (action :'vistaPerfil', model:[usuario:session.nickname, miUsuario:session.usuario.nickname])
				}
				/**
				 * Ha fallado el incio el de sesion
				 */
				else
				{
					if(params.email=="" || params.password=="")
					{
						serviceResponse ="Debe ingresar email y password para acceder a su perfil."
						render (view :'avisoServidor', model:[aviso:serviceResponse, miLink:redireccion])
					}
					else if(serviceResponse =="Login y/o Password invalidos")
					{
						render (view :'avisoServidor', model:[aviso:serviceResponse, miLink:redireccion])
					}
					else
					{
						redireccion="vistaPerfil"
						render (view :'avisoServidor', model:[aviso:serviceResponse, miLink:redireccion])
					}
								
				}
			
			}
		
		/**
		 * Se obtiene el usuario que acaba de loguearse y se almacena en una variable de sesion
		 */
		def obtenerUsuario = {
			
			def url = new URL("http://localhost:8080/miOrquidea/usuario/consultaUnUsuario?email=$session.email&password=$session.password" )
			def connection = url.openConnection()
			connection.setRequestMethod("GET")
			connection.setDoOutput(true)
			connection.connect()
			def miXml = new XmlSlurper().parseText(connection.content.text)
			session.nickname = miXml.nickname
			Usuario usuario = procesarUnXml(miXml)
			session.usuario = usuario
		}
		
		/**
		 * Se procesa el XML que retornara un objeto tipo Usuario
		 * Usuario que ha iniciado sesion
		 * @param xml
		 * @return
		 */
		def procesarUnXml(def xml)
		{
				Usuario usuario = new Usuario()
				usuario.nombre = xml.nombre
				usuario.apellido = xml.apellido
				usuario.biografia = xml.biografia
				usuario.email = xml.email
				usuario.nickname = xml.nickname
				usuario.pais = xml.pais
				usuario.password = xml.password
				usuario.activo = 	(boolean)xml.activo
				usuario.fechaRegistro = xml.fechaRegistro
			
			return usuario
		}
		
		def cerrarSesion = {
			
				def serviceResponse = "No hay respuesta"
				/**
				 * Se establece la URL de la ubicacion
				 * del servicio
				 */
				def url = new URL("http://localhost:8080/miOrquidea/token/anularToken" )
				/**
				 * Se extraen los parametros y convierte a formato
				 * XML para luego ser enviada a la aplicacion miOrquidea
				 */
				def gXml = new StringWriter()
				def xml = new MarkupBuilder(gXml)
				
				xml.usuario() {
					email(session.email)
					password(session.password)
				}
				
				def connection = url.openConnection()
				connection.setRequestMethod("POST")
				connection.setRequestProperty("Content-Type" ,"text/xml" )
				connection.doOutput=true
				   
				Writer writer = new OutputStreamWriter(connection.outputStream)
				writer.write(gXml.toString())
				writer.flush()
				writer.close()
				connection.connect()
							
				def miXml = new XmlSlurper().parseText(connection.content.text)
				
				serviceResponse = miXml.mensaje
				
				if(serviceResponse == "")
				{
					serviceResponse = "El usuario $params.email ha cerrado sesion correctamente"
					session.removeAttribute("usuario")
					session.removeAttribute("nickname")
					session.removeAttribute("email")
					session.removeAttribute("password")
					render (view :'../index')
				}
				else
				{
					render (view :'registroExitoso', model:[aviso:serviceResponse])
				}
		}
		
		def destruirSesion()
		{
			session.removeAttribute("usuario")
			session.removeAttribute("nickname")
			session.removeAttribute("email")
			session.removeAttribute("password")
			render(view:"../index")
		}
		
		def vistaIniciarSesion =	{
			render (view:'iniciarSesion')
		}
		
		def vistaCerrarSesion =	{
			render (view:'cerrarSesion')
		}
		
		def vistaPerfil = {
			
			if(session.usuario)
			{
			
				if(Token.tokenVigente(session.usuario.email))
				{
					redirect (controller:"comentario",  action:"consultarComentarioPorUsuario") 
				}
				else
				{
					destruirSesion()
				}
			}
			else
			{
				render(view:"../index")
			}
		}
		
		/*
		 * Accion para consumir servivios de activar usuario
		 */
		def activarUsuario = {		
				
					
					def serviceResponse = "No hay respuesta"
					/**
					 * Se establece la URL de la ubicacion
					 * del servicio
					 */
					def url = new URL("http://localhost:8080/miOrquidea/usuario/activarUsuario" )
					/**
					 * Se extraen los parametros y convierte a formato
					 * XML para luego ser enviada a la aplicacion miOrquidea
					 *
					 */
					def parametro = new Usuario (params) as XML
					
					
					def connection = url.openConnection()
					connection.setRequestMethod("PUT")
					connection.setRequestProperty("Content-Type" ,"text/xml" )
					connection.doOutput=true
					
						Writer writer = new OutputStreamWriter(connection.outputStream)
						writer.write(parametro.toString())
						writer.flush()
						writer.close()
						connection.connect()
						
							if(connection.responseCode == 200)
							{
								serviceResponse = "El usuario tiene su cuenta activa en este momento"							
								redirect(action:'vistaIniciarSesion')													
							}
							else
							{
								serviceResponse = "No aplica proceso de activacion para este usuario"
								render (view :'registroExitoso', model:[aviso:serviceResponse,usuario:session.usuario.nickname])
							}
									
				
		}
		
		def vistaActivarUsuario =	{
			render (view:'activarUsuario',model:[usuario:session.nickname])
		}
			
		/*
		 * Accion para consumir servicio de eliminar usuario
		 */
		def eliminarUsuario = {
			
				if(Token.tokenVigente(session.usuario.email))
				{
					def url = new URL("http://localhost:8080/miOrquidea/usuario/eliminarUsuario?email=$params.email&password=$params.password" )
					def connection = url.openConnection()
					connection.setRequestMethod("DELETE")
					connection.setDoOutput(true)
					connection.connect()
					def serviceResponse = "Datos incorrectos!"
					def miXml
					
						if(connection.responseCode == 200)
						{
											
							miXml = new XmlSlurper().parseText(connection.content.text)
							serviceResponse = miXml.mensaje 
							
							if(serviceResponse == "")
							{
								serviceResponse = "El usuario "+miXml.email+" ha desactivado su cuenta exitosamente.</br> "+miXml.fechaRegistro							
							}
							
						}
						render (view :'registroExitoso', model:[aviso:serviceResponse,usuario:session.nickname])
				}
				else
				{
					destruirSesion()
				}
			
		}
		
		def vistaEliminarUsuario ={
			render (view:'eliminarUsuario',model:[usuario:session.nickname])
			
		}
		
		def miPerfil = {
			render (view :'perfil', model:[usuario:session.nickname])
		}
		
		/*
		 * Action encarga de mostrar el formulario de
		 * registro de usuarios
		 */
		def vistaRegistroUsuario={
			
			render (view:'registrarUsuario')
		}
		
		
		/**
		* Invocacion al servicio de resgistrar usuario de
		* miOrquide app
		*/
	   def registrarUsuario ={
		   
		   if (bandera.equals("miOrquidea"))
		   {
				   def serviceResponse = "No hay respuesta"
				   def redireccion ="../"
				   
				   /**
					* Se establece la URL de la ubicacion
					* del servicio
					*/
				   def url = new URL("http://localhost:8080/miOrquidea/usuario/registrarUsuario" )
				   
				   /**
					* Se extraen los parametros y convierte a formato
					* XML para luego ser enviada a la aplicacion miOrquidea
					*
					*/
				   def parametro = new Usuario (params) as XML
				   def connection = url.openConnection()
				   connection.setRequestMethod("POST")
				   connection.setRequestProperty("Content-Type" ,"text/xml" )
				   connection.doOutput=true
				   
					   Writer writer = new OutputStreamWriter(connection.outputStream)
					   writer.write(parametro.toString())
					   writer.flush()
					   writer.close()
					   connection.connect()
					  
					   if(connection.responseCode == 201)
					   {
						   //El usuario fue registrado
						   def miXml = new XmlSlurper().parseText(connection.content.text)
						   serviceResponse  = "Usuario Registrado Exitosamente!"
						   redireccion = "vistaIniciarSesion"
					   }
					   else
					   {
						   //Ha ocurrido un error,  posiblemente datos duplicados
						   // XML vacío ó error en formato de datos entrada
						   if(connection.responseCode == 200)
						   {
							   def miXml = new XmlSlurper().parseText(connection.content.text)
							   serviceResponse = miXml.mensaje
							   redireccion = "vistaRegistroUsuario"				  
						   }
						}
				   render (view :'avisoServidor', model:[aviso:serviceResponse, miLink:redireccion])
		   }
		   else
		   {
			   def serviceResponse = "No hay respuesta"
			   def redireccion ="../"
			   
			   /**
			   * Con estas funciones creamos el XML
			   */
			   def gXml = new StringWriter()
			   def xml = new MarkupBuilder(gXml)
			   def concatenarFecha = params.fechaRegistro.split("-")
			   String fecha = concatenarFecha[2] + "/" + concatenarFecha[1] + "/" + concatenarFecha[0]
			   /**
				* Se establece la URL de la ubicacion
				* del servicio Spring
				*/
			   def url = new URL("http://localhost:8084/SPRINGDESESPERADO/rest/insertarUsuario" )
			   
			   xml.Usuario() {
					  nombre (params.nombre)
					  apellido(params.apellido)
					  clave(params.password)
					  correo(params.email)
					  nickname(params.nickname)
					  fecha_nac(fecha)
					  pais(params.pais)
					  biografia(params.biografia)
					  foto("")
			   }
			   
			   def connection = url.openConnection()
			   connection.setRequestMethod("POST")
			   connection.setRequestProperty("Content-Type" ,"text/xml" )
			   connection.doOutput=true
			   
				   Writer writer = new OutputStreamWriter(connection.outputStream)
				   writer.write(gXml.toString())
				   writer.flush()
				   writer.close()
				   connection.connect()
				   
			  def miXml = new XmlSlurper().parseText(connection.content.text)
			  
			  if (miXml.nickname != "ERROR 007")
			  {
				  serviceResponse  = "Usuario Registrado Exitosamente!"
				  redireccion = "vistaIniciarSesion"
			  }
			  else
			  {
				  serviceResponse = "ERROR 007"
				  redireccion = "vistaRegistroUsuario"
			  }
			  render (view :'avisoServidor', model:[aviso:serviceResponse, miLink:redireccion])
		   }
	
		}
	   
		def guardarXML  = {
			
		}
		
		/**
		 * Invocacion al servicio de consultar todos los usuarios
		 * registrados en el sistema
		 */
		def consultarTodosLosUsuarios = {
			
			/**
			 * Se ubica la URL del servicio que lista a todos los usuarios
			 */
			def url = new URL("http://localhost:8080/miOrquidea/usuario/" )
			def listUsuario
			/**
			 * Se establece la conexion con el servicio
			 * Se determina el tipo de peticion (GET) y
			 * el contenido de la misma (Archivo plano XML)
			 */
			def connection = url.openConnection()
			connection.setRequestMethod("GET" )
			connection.setRequestProperty("Content-Type" ,"text/xml" )
			
			if(connection.responseCode == 200)
			{
				def miXml = new XmlSlurper().parseText(connection.content.text)
				listUsuario = procesarXML(miXml)
			}
			else{
				render "Se ha generado un error:"
				render connection.responseCode
				render connection.responseMessage
			}
			render (view:"consultarUsuarios", model:[usuarios:listUsuario])
		}
		
		/**
		 * Metodo encargado de procesar el archivo XML recibido del
		 * servicio miOrquidea app
		 * @param xml
		 * @return
		 */
		def procesarXML(def xml)
		{
			ArrayList<Usuario> listUsuario = new ArrayList<Usuario>()
			
			for (int i=0;i< xml.usuario.size();i++)
			{
				Usuario usuario = new Usuario()
				usuario.nombre = xml.usuario[i].nombre
				usuario.apellido = xml.usuario[i].apellido
				usuario.biografia = xml.usuario[i].biografia
				usuario.email = xml.usuario[i].email
				usuario.nickname = xml.usuario[i].nickname
				usuario.pais = xml.usuario[i].pais
				usuario.password = xml.usuario[i].password
				usuario.activo = 	(boolean)xml.usuario[i].activo
				usuario.fechaRegistro = xml.usuario[i].fechaRegistro
				listUsuario.add(usuario)
			}
			return listUsuario
		}
		
		/**
		 * Metodo utilizado para pruebas sobre la recepcion y lectura del archivo
		 * XML enviado por el servicio de miOrquidea app
		 * @param xml
		 * @return
		 */
		def procesarXMLTest(def xml)
		{
			render "Usuarios del Sistema</br>"
			render "<strong>--------------------</strong></br>"
			for (int i=0;i< xml.usuario.size();i++)
			{
				render "<strong>ID </strong> "+xml.usuario[i].@id+"</br>"
				render "<strong>Nombre</strong> "+xml.usuario[i].nombre+"</br>"
				render "<strong>Nombre</strong> "+xml.usuario[i].nombre+"</br>"
				render "<strong>Apellido</strong> "+xml.usuario[i].apellido+"</br>"
				render "<strong>Biografia</strong> "+xml.usuario[i].biografia+"</br>"
				render "<strong>Email</strong> "+xml.usuario[i].email+"</br>"
				render "<strong>Nickname</strong> "+xml.usuario[i].nickname+"</br>"
				render "<strong>Pais</strong>"+xml.usuario[i].pais+"</br>"
				render "<strong>Password</strong> "+xml.usuario[i].password+"</br>"
				render "<strong>Fecha Registro </strong>"+xml.usuario[i].fechaRegistro+"</br>"
				render "<strong>Activo</strong> "+xml.usuario[i].activo+"</br>"
				render "<strong>--------------------</strong></br>"
			}
		}
		
		def vistaModificarUsuario =	{
			render (view:'modificarUsuario',model:[usuario:session.nickname])
		}
		
		def modificarUsuario = {
			
			if(params.email == session.usuario.email && params.password == session.usuario.password)
			{
			
					def email = params.email
					def password = params.password
					def serviceResponse = "No hay respuesta"
					
					/**
					* Se ubica la URL del servicio que lista a todos los usuarios
					*/
				   def url = new URL("http://localhost:8080/miOrquidea/usuario/" )
				   def listUsuario
				   /**
					* Se establece la conexion con el servicio
					* Se determina el tipo de peticion (GET) y
					* el contenido de la misma (Archivo plano XML)
					*/
				   def connection = url.openConnection()
				   connection.setRequestMethod("GET" )
				   connection.setRequestProperty("Content-Type" ,"text/xml" )
				   
				   if(connection.responseCode == 200)
				   {
					   def miXml = new XmlSlurper().parseText(connection.content.text)
					   listUsuario = procesarXML(miXml)
					   Usuario miUsuario = buscarUsuario(listUsuario,email,password)
					   if(miUsuario!=null)
					   {			  
						   miUsuario.email2 = email
						   render (view:'actualizarUsuario',model:[usuario:miUsuario])
					   }
				   }
				   else
				   {
					   render "Se ha generado un error:"
					   render connection.responseCode
					   render connection.responseMessage
				   }
			}
			else
			{
				def miAlerta = "Usuario y/o password invalidos"
				render(view:"modificarUsuario",model:[alerta:miAlerta])
			}
			
		}
		
		def buscarUsuario(def listUsuario, def email, def password)
		{
			for (Usuario usuario: listUsuario)
			{
				if(usuario.email == email)
				{
					return usuario
				}
			}
			return null
		}
		
		def modificarDatosUsuario ={
			
			if (bandera.equals("miOrquidea"))
		    { 
				if(Token.tokenVigente(session.usuario.email))
				{
					def serviceResponse = "No hay respuesta"
					
					/**
					 * Se establece la URL de la ubicacion
					 * del servicio
					 */
					def url = new URL("http://localhost:8080/miOrquidea/usuario/modificarUsuario" )
					
					/**
					 * Se extraen los parametros y convierte a formato
					 * XML para luego ser enviada a la aplicacion miOrquidea
					 *
					 */
					def parametro = new Usuario (params) as XML
					
					
					def connection = url.openConnection()
					connection.setRequestMethod("PUT")
					connection.setRequestProperty("Content-Type" ,"text/xml" )
					connection.doOutput=true
					
						Writer writer = new OutputStreamWriter(connection.outputStream)
						writer.write(parametro.toString())
						writer.flush()
						writer.close()
						connection.connect()
					   
						if(connection.responseCode == 201)
						{
							//El usuario fue registrado
							def miXml = new XmlSlurper().parseText(connection.content.text)
							serviceResponse  = "Usuario Registrado Exitosamente!"
						}
						else
						{
							//Ha ocurrido un error,  posiblemente datos duplicados
							// XML vacío ó error en formato de datos entrada
							if(connection.responseCode == 200)
							{
								def miXml = new XmlSlurper().parseText(connection.content.text)
								serviceResponse = miXml.mensaje
							}
						 }
						render (view :'registroExitoso', model:[aviso:serviceResponse])
				}
				else
				{
					destruirSesion()
				}
		    }
			else
			{
				def serviceResponse = "No hay respuesta"
				
				/**
				 * Se establece la URL de la ubicacion
				 * del servicio
				 */
				def url = new URL("http://localhost:8084/SPRINGDESESPERADO/rest/updateUsuario" )
				
				/**
				* Con estas funciones creamos el XML
				*/
				def gXml = new StringWriter()
				def xml = new MarkupBuilder(gXml)
				def concatenarFecha = params.fechaRegistro.split("-")
				String fecha = concatenarFecha[2] + "/" + concatenarFecha[1] + "/" + concatenarFecha[0]
				
				xml.Usuario() {
					   nombre (params.nombre)
					   apellido(params.apellido)
					   clave(params.password)
					   correo(params.email2)
					   nickname(params.nickname)
					   fecha_nac(fecha)
					   pais(params.pais)
					   biografia(params.biografia)
					   foto("")
				}
					
				def connection = url.openConnection()
				connection.setRequestMethod("POST")
				connection.setRequestProperty("Content-Type" ,"text/xml" )
				connection.doOutput=true
				
					Writer writer = new OutputStreamWriter(connection.outputStream)
					writer.write(gXml.toString())
					writer.flush()
					writer.close()
					connection.connect()
				   
					def miXml = new XmlSlurper().parseText(connection.content.text)
					if (miXml.nickname != "ERROR 007")
					{
						//El usuario fue registrado
						serviceResponse  = "Usuario Registrado Exitosamente!"
					}
					else
					{
						serviceResponse = miXml.nickname
					 }
					render (view :'registroExitoso', model:[aviso:serviceResponse])
			}
		}
		
		/**
		* Metodo encargado de buscar los nombre de las Usuarios registrados en el sistema
		*  por correo Usuario
		*/
		def buscarUsuarioPorCorreo (String correoUsuario)
		{
			/**
			* Se ubica la URL del servicio que lista a todas los Usuarios
			*/
		   def url = new URL("http://localhost:8080/miOrquidea/usuario/" )
		   def nombreUsuario
		   
		   /**
			* Se establece la conexion con el servicio
			* Se determina el tipo de peticion (GET) y
			* el contenido de la misma (Archivo plano XML)
			*/
		   def connection = url.openConnection()
		   connection.setRequestMethod("GET" )
		   connection.setRequestProperty("Content-Type" ,"text/xml" )
		   
		   if(connection.responseCode == 200)
		   {
			   def miXml = new XmlSlurper().parseText(connection.content.text)
			   nombreUsuario = procesarXmlUsuarioCorreo(miXml , correoUsuario)
		   }
		   else{
			   render "Se ha generado un error:"
			   render connection.responseCode
			   render connection.responseMessage
		   }
		   return nombreUsuario
		}
		
		/**
		* Metodo encargado de procesar el archivo XML recibido del
		* servicio miOrquidea app y retorna el nickname del usuario
		* @param xml
		* @return
		*/
		def procesarXmlUsuarioCorreo(def xml, String correoUsuario)
		{
		   def nombreUsuario = null
		
		   for (int i=0;i< xml.usuario.size();i++)
		   {
			  if (xml.usuario[i].email.equals(correoUsuario))
			  {
				  nombreUsuario = xml.usuario[i].nickname
				  return nombreUsuario
			  }
		  }
		  return nombreUsuario
		}
		
		def vistaSubirFoto = {
			if(Token.tokenVigente(session.usuario.email))
			{
				if(session.usuario)
				{
					def miArchivo = "archivo"
					def miPath= "../../miAraguaney/miAraguaney/web-app/images/fotoPerfil"
					render(view:"fotoPerfil",model:[email:session.usuario.email,path:miPath,archivo:miArchivo,usuario:session.usuario.nickname])
				}
				else
				{
					render(view:"../index")
				}
			}
			else
			{
				destruirSesion()
			}		
		}
		
		def save = {		
				
				try
				{
					if(Token.tokenVigente(session.usuario.email))
					{
						
					
						def fotoPerfil = request.getFile('adjunto')
						
						if (!fotoPerfil.isEmpty()) {							
								
								/*def url = new URL("http://localhost:8080/miOrquidea/usuario/uploadFile" )
								def connection = url.openConnection()
								connection.setRequestMethod("POST")
								connection.setRequestProperty("Content-Type" ,"multipart/form-data" )
								connection.doOutput=true
								Writer writer = new OutputStreamWriter(connection.outputStream)
								writer.write(params)
								writer.flush()
								writer.close()
								connection.connect()*/
								/************Respuesta*************/
								//def miXml = new XmlSlurper().parseText(connection.content.text)
							File miPath = new File("../miAraguaney/web-app/images/fotoPerfil")
							miPath.mkdirs()
							def limpiarDir = new File("../miAraguaney/web-app/images/fotoPerfil/${session.usuario.nickname}.png")
							boolean limpio = true
							if(limpiarDir.exists())
							{
								limpio = limpiarDir.delete()
							}
							
							if(limpio)
							{
								fotoPerfil.transferTo(new File("../miAraguaney/web-app/images/fotoPerfil/${session.usuario.nickname}.png"))				
								redirect(action:"index")
							}
							else
							{
								def miAlerta = "Ha ocurrido un error en el servidor, intente luego."
								render(view:"fotoPerfil",model:[email:session.usuario.email,path:miPath,archivo:miArchivo,usuario:session.usuario.nickname,alerta:miALerta])
							}
							
						}
						else
						{
							redirect(action:"vistaSubirFoto")
						} 
					}
					else
					{
						destruirSesion()
					}
				}
				catch(Exception)
				{
					def miAlerta = "Ha ocurrido un error en el servidor, intente luego."
					render(view:"fotoPerfil",model:[email:session.usuario.email,path:miPath,archivo:miArchivo,usuario:session.usuario.nickname,alerta:miALerta])					
				}		
		}
	
}
