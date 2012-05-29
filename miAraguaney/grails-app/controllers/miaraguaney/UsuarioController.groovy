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

    def index = { 		
		
		log.info ("Ejemplo de este log")
		//render (view:'consultarTodos')
		redirect  (action:'vistaRegistroUsuario')
		}
	
	/*
	 * Accion para consumir servicios de iniciar sesion 
	 */
	
	def iniciarSesion = {
		
		def serviceResponse = "No hay respuesta"
		/**
		 * Se establece la URL de la ubicacion
		 * del servicio
		 */
		def url = new URL("http://localhost:8080/miOrquidea/token/iniciarSesion" )
		/**
		 * Se extraen los parametros y convierte a formato
		 * XML para luego ser enviada a la aplicacion miOrquidea
		 *
		 */	
		
		def gXml = new StringWriter()
		def xml = new MarkupBuilder(gXml)
		
		xml.usuario() {
			email(params.email)
			password(params.password)
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
			serviceResponse = "El usuario $params.email ha inciado sesion correctamente"
		}
		
		
		
		render (view :'registroExitoso', model:[aviso:serviceResponse])
		
		}
	def vistaIniciarSesion =	{
		render (view:'iniciarSesion')
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
			def serviceResponse2 = "No hay respuesta!"
			
				if(connection.responseCode == 200)
				{
									
					
					serviceResponse = "El usuario ha activado su cuenta correctamente"
					
				}
				
			
				render (view :'registroExitoso', model:[aviso:serviceResponse])
		   
	
		}
	
	def vistaActivarUsuario =	{
		render (view:'activarUsuario')
	}
		
		
	
	/*
	 * Accion para consumir servicio de eliminar usuario
	 */
	def eliminarUsuario = {
		def url = new URL("http://localhost:8080/miOrquidea/usuario/eliminarUsuario?email=$params.email&password=$params.password" )			
		def connection = url.openConnection()
		connection.setRequestMethod("DELETE")
		connection.setDoOutput(true)
		connection.connect()
		def serviceResponse = "No hay respuesta!"		
		
			if(connection.responseCode == 200)
			{
								
				def miXml = new XmlSlurper().parseText(connection.content.text)
				serviceResponse = miXml.mensaje
				
				if(serviceResponse == "")
				{
					serviceResponse = "El usuario "+miXml.email+" ha desactivado su cuenta exitosamente.</br> "+miXml.fechaRegistro
				}
				
			}
			
			render (view :'registroExitoso', model:[aviso:serviceResponse])
		
		}
	
	def vistaEliminarUsuario ={
		render (view:'eliminarUsuario')
		
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
		render (view:'modificarUsuario')
	}
	
	def modificarUsuario = {
		
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
	
}
