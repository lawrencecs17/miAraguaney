package miaraguaney

import groovyx.net.http.HTTPBuilder
import static groovyx.net.http.Method.*
import static groovyx.net.http.ContentType.*
import grails.converters.*
import java.util.Date
import org.apache.commons.logging.*

/**
 * 
 * @author Lawrence Cermeño
 * @author Sara Villarreal
 * @author Ricardo Portela
 *
 */

class UsuarioController {
	
	private static Log log = LogFactory.getLog("Logs."+UsuarioController.class.getName())

    def index = { 		
		
		log.info ("Ejemplo de este log")
		//render (view:'consultarTodos')
		redirect  (action:'vistaRegistroUsuario')
		}
	
	/* 
	 * Action encarga de mostrar el formulario de
	 * registro de usuarios
	 */
	def vistaRegistroUsuario={
		
		render (view:'registrarusuario')
		
		}
	
	
	/**
	* Invocacion al servicio de resgistrar usuario de 
	* miOrquide app
	*/
   def registrarUsuario ={
	   
	   def serviceResponse = "No hay respuesta"
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
		   def restResponse = connection.content.text
		   serviceResponse = "Registro Exitoso, usuario creado!"
	   }
	   else
	   {
		   serviceResponse = "No hay respuesta por parte del servidor!"
	   }
	   
	  
	   
	   
	   render (view :'registroExitoso', model:[aviso:serviceResponse])

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
	
	def construirHTML(def listUsuario)
	{
		for (Usuario usuario:listUsuario)
		{
			render usuario.nombre
		}
		
		return listUsuario
	}
}
