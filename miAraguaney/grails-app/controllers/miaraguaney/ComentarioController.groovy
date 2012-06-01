package miaraguaney

import org.apache.commons.logging.*
import groovyx.net.http.HTTPBuilder
import static groovyx.net.http.Method.*
import static groovyx.net.http.ContentType.*
import grails.converters.*
import java.util.Date
import miaraguaney.ComentarioCliente
import groovy.xml.MarkupBuilder

class ComentarioController {

   	private static Log log = LogFactory.getLog("Logs."+ComentarioController.class.getName())
	
    def index() { 
		
		log.info("Ejemplo de este log")
		render (view:'consultarTodos')
	}
	
	/**
	* Invocacion al servicio de consultar todos los Comentarios
	* registrados en el sistema
	*/
   def consultarTodosLosComentarios = {
	   
	   /**
		* Se ubica la URL del servicio que lista a todos los Comentarios
		*/
	   def url = new URL("http://localhost:8080/miOrquidea/comentario/listarTodos" )
	   def listaComentario
	   
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
		   //String algo = session.nickname
		   def miXml = new XmlSlurper().parseText(connection.content.text)
		   listaComentario = procesarXmlComentario(miXml)//, algo)
	   }
	   else{
		   render "Se ha generado un error:"
		   render connection.responseCode
		   render connection.responseMessage
	   }

	   render (view:'consultarComentarios', model:[comentarios:listaComentario, usuario:session.nickname])
   }
   
   /**
   * Metodo encargado de procesar el archivo XML recibido del
   * servicio miOrquidea app
   * @param xml
   * @return
   */
  def procesarXmlComentario(def xml)//, String nick)
  {
	  ArrayList<ComentarioCliente> listaComentario = new ArrayList<ComentarioCliente>()
	  String nickname = session.nickname
	  for (int i=0;i< xml.comentario.size();i++)
	  {
		  ComentarioCliente comentario = new ComentarioCliente()
		  comentario.idComentario = xml.comentario[i].@id.text()
		  comentario.mensaje = xml.comentario[i].mensaje
		  comentario.fecha = xml.comentario[i].fecha
		  comentario.principal = xml.comentario[i].principal
		  
		  def nombreUsuario = buscarUsuario(xml.comentario[i].autor.@id.text())
		  if (nombreUsuario)
		  {
			  comentario.autor = nombreUsuario
		  }
		  
		  def cantidadLike = buscarLike(xml.comentario[i].@id.text())
		  if (cantidadLike)
		  {
			  comentario.cantidadLike = cantidadLike
		  }
		  if (comentario.cantidadLike == '')
		      comentario.cantidadLike = '0'

		  def cantidadDislike = buscarDislike(xml.comentario[i].@id.text())
		  if (cantidadDislike)
		  {
			  comentario.cantidadDislike = cantidadDislike
		  }
		  if (comentario.cantidadDislike == '')
		  	  comentario.cantidadDislike = '0'
				
		  def cantidadComentados = buscarComentados(xml.comentario[i].@id.text())
		  if (cantidadComentados)
		  {
			 comentario.cantidadComentados = cantidadComentados
		  }
		  
		  String like = buscarCalificacionLike(xml.comentario[i].@id.text(), nickname)
		  comentario.calificacionLike = like
		  
		  String dislike = buscarCalificacionDislike(xml.comentario[i].@id.text(), nickname)
		  comentario.calificacionDislike = dislike
		  
		  listaComentario.add(comentario)
	  }
	  
	  return listaComentario
  }
  
  /**
  * Metodo encargado de buscar los nombre de las Usuarios registrados en el sistema
  *  por id Usuario
  */
  def buscarUsuario (String Usuario)
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
		 nombreUsuario = procesarXmlUsuario(miXml , Usuario)
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
  def procesarXmlUsuario(def xml, String idUsuario)
  {
	 def nombreUsuario = null

	 for (int i=0;i< xml.usuario.size();i++)
	 {
        if (xml.usuario[i].@id.equals(idUsuario))
		{
			nombreUsuario = xml.usuario[i].nickname
			return nombreUsuario 
		}
	}
	return nombreUsuario
 }
 
 /**
 * Metodo encargado de buscar las calificaciones de like registrados en el sistema
 *  por id del Comentario
 */
  def buscarLike (String comentario)
  {
	 /**
	 * Se ubica la URL del servicio que lista a todas las Calificaciones
	 */
	def url = new URL("http://localhost:8080/miOrquidea/calificacion/consultarLikeDislile?idComentario=" + comentario)
	def cantidadLike
	
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
		cantidadLike = miXml.like
	}
	else{
		render "Se ha generado un error:"
		render connection.responseCode
		render connection.responseMessage
	}
	
	return cantidadLike
  }
  
  /**
  * Metodo encargado de buscar las calificaciones de Dislike registrados en el sistema
  *  por id del Comentario
  */
   def buscarDislike (String comentario)
   {
	  /**
	  * Se ubica la URL del servicio que lista a todas las Calificaciones
	  */
	 def url = new URL("http://localhost:8080/miOrquidea/calificacion/consultarLikeDislile?idComentario=" + comentario)
	 def cantidadDislike
	 
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
		 cantidadDislike = miXml.dislike
	 }
	 else{
		 render "Se ha generado un error:"
		 render connection.responseCode
		 render connection.responseMessage
	 }
	 
	 return cantidadDislike
   }
   
   /**
   * Metodo encargado de buscar las Respuestas de los Comentarios registrados en el sistema
   *  por id del Comentario
   */
	def buscarComentados (String comentario)
	{
	   /**
	   * Se ubica la URL del servicio que lista a todas los Comentarios comentados
	   */
	  def url = new URL("http://localhost:8080/miOrquidea/comentario/contarComentados?idComentario=" + comentario)
	  def cantidadComentados
	  
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
		  cantidadComentados = miXml.comentados
	  }
	  else{
		  render "Se ha generado un error:"
		  render connection.responseCode
		  render connection.responseMessage
	  }
	  
	  return cantidadComentados
	}
	
	/**
	* Metodo encargado de buscar las calificacion que tengan like de los Comentarios registrados en el sistema
	*  por id del Comentario y nickname del usuario
	*/
	 def buscarCalificacionLike(String idComentario, String nickname)
	 {
		/**
		* Se ubica la URL del servicio que lista a todas los Comentarios comentados
		*/
	   def url = new URL("http://localhost:8080/miOrquidea/calificacion/listarPorUsuarioComentario?usuario=" + nickname + "&comentario=" + idComentario)
	   
	   String like = "false"
	   
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
		   like = procesarXmlLike(miXml)
	   }
	   else{
		   render "Se ha generado un error:"
		   render connection.responseCode
		   render connection.responseMessage
	   }
	   
	   return like
	 }
	 
	 /**
	 * Metodo encargado de procesar el archivo XML recibido del
	 * servicio miOrquidea app y retorna el el valos de like de la calificacion 
	 * del comentario
	 * @param xml
	 * @return
	 */
	 def procesarXmlLike(def xml)
	 {
		String like = "false"
   
		if (!xml.calificacion.like.equals(""))
		{
			like = xml.calificacion.like.text()
			return like
		}
	    return like
	}
	 
	 /**
	 * Metodo encargado de buscar las calificacion que tengan Dislike de los Comentarios registrados en el sistema
	 *  por id del Comentario y nickname del usuario
	 */
	  def buscarCalificacionDislike(String idComentario, String nickname)
	  {
		 /**
		 * Se ubica la URL del servicio que lista a todas los Comentarios comentados
		 */
		def url = new URL("http://localhost:8080/miOrquidea/calificacion/listarPorUsuarioComentario?usuario=" + nickname + "&comentario=" + idComentario)
		String dislike = "false"
		
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
			dislike = procesarXmlDislike(miXml)
		}
		else{
			render "Se ha generado un error:"
			render connection.responseCode
			render connection.responseMessage
		}
		
		return dislike
	  }
	  
	  /**
	  * Metodo encargado de procesar el archivo XML recibido del
	  * servicio miOrquidea app y retorna el el valos de dislike de la calificacion
	  * del comentario
	  * @param xml
	  * @return
	  */
	  def procesarXmlDislike(def xml)
	  {
		 String dislike = "false"
	
		 if (!xml.calificacion.dislike.equals(""))
		 {
			 dislike = xml.calificacion.dislike
			 return dislike
		 }
		 return dislike
	 }

	/**
	 * Llamo a la vista crear comentario y le paso el nickname de usuario
	 * para que lo muestre en la vista
	 */
	def crearComentario = {
	   
	    render (view: 'crearComentario',  model:[usuario:session.nickname])
	 
	}


	/**
	* Invocacion al servicio de registrar comentario
	*/
	
	def agregarComentario = {
		
		def serviceResponse = "No hay respuesta"
		/**
		 * Se establece la URL de la ubicacion
		 * del servicio
		 */
		def url = new URL("http://localhost:8080/miOrquidea/comentario/crearComentario" )
		/**
		* Se extraen los parametros y convierte a formato
		* XML para luego ser enviada a la aplicacion miOrquidea
		*
		*/
		def nick = session.nickname
	   
	   /**
	    * Con estas funciones creamos el XML  	
	    */
	   def gXml = new StringWriter()
	   def xml = new MarkupBuilder(gXml)
	    
	   /**
	    * Creando el XML para pasarlo al servicio
	    */
	   xml.comentario() {
			   autor (nick)
			   mensaje(params.mensaje)
			   def arrayetiquetas = params.etiquetas.split(",")
			   def sizearray= arrayetiquetas.size()
			   	if (sizearray >0)
				   {
					   for (int i=0;i< sizearray ;i++)
					   {
						   tag{	  
							    etiqueta(arrayetiquetas[i])
						   	  }
					   }
				   }
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
			
			/**
			 * Lo que me responde el servidor 
			 */
			if(serviceResponse == "")
			{
			
				serviceResponse = "El usuario $params.mensaje ha inciado sesion correctamente"
			}
	   
			render (view: 'crearComentario', model:[usuario:session.nickname])
	   
	   } //fin metodo agregar comentario

	/**
	* Metodo que se encarga de crear una calificacion like en el comentario 
	*/
	def crearComentarioLike () {
		
		def serviceResponse = "No hay respuesta"
		/**
		 * Se establece la URL de la ubicacion
		 * del servicio
		 */
		def url = new URL("http://localhost:8080/miOrquidea/calificacion/crearCalificacion" )
		/**
		* Se extraen los parametros y convierte a formato
		* XML para luego ser enviada a la aplicacion miOrquidea
		*
		*/
		def nick = session.nickname
	   
	   /**
	    * Con estas funciones creamos el XML  	
	    */
	   def gXml = new StringWriter()
	   def xml = new MarkupBuilder(gXml)
	    
	   /**
	    * Creando el XML para pasarlo al servicio
	    */
	   xml.calificacion() {
			   comentario (id:params.id)
			   dislike(false)
			   like(true)
			   persona(nick)
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
			
			/**
			 * Lo que me responde el servidor 
			 */
			if(serviceResponse == "")
			{
			
				serviceResponse = "El usuario $params.mensaje ha inciado sesion correctamente"
			}
	   
			redirect (action: 'consultarTodosLosComentarios')
	   
	   } //fin metodo crear calificacion like
	
	/**
	* Metodo que se encarga de crear una calificacion dislike en el comentario
	*/
	def crearComentarioDislike () {

		def serviceResponse = "No hay respuesta"
		/**
		 * Se establece la URL de la ubicacion
		 * del servicio
		 */
		def url = new URL("http://localhost:8080/miOrquidea/calificacion/crearCalificacion" )
		/**
		* Se extraen los parametros y convierte a formato
		* XML para luego ser enviada a la aplicacion miOrquidea
		*
		*/
		def nick = session.nickname
	   
	   /**
		* Con estas funciones creamos el XML
		*/
	   def gXml = new StringWriter()
	   def xml = new MarkupBuilder(gXml)
		
	   /**
		* Creando el XML para pasarlo al servicio
		*/
	   xml.calificacion() {
			   comentario (id:params.id)
			   dislike(true)
			   like(false)
			   persona(nick)
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
			
			/**
			 * Lo que me responde el servidor
			 */
			if(serviceResponse == "")
			{
			
				serviceResponse = "El usuario $params.mensaje ha inciado sesion correctamente"
			}
	   
			redirect (action: 'consultarTodosLosComentarios')
	   
	   } //fin metodo crear calificacion dislike
	
	/**
	* Metodo que se encarga de modificar una calificacion like en el comentario
	*/
	def modificarComentarioLike () {
		
		def serviceResponse = "No hay respuesta"
		/**
		 * Se establece la URL de la ubicacion
		 * del servicio
		 */
		def url = new URL("http://localhost:8080/miOrquidea/calificacion/modificarCalificacion" )
		/**
		* Se extraen los parametros y convierte a formato
		* XML para luego ser enviada a la aplicacion miOrquidea
		*
		*/
		def nick = session.nickname
	   
	   /**
		* Con estas funciones creamos el XML
		*/
	   def gXml = new StringWriter()
	   def xml = new MarkupBuilder(gXml)
		
	   /**
		* Creando el XML para pasarlo al servicio
		*/
	   xml.calificacion() {
			   comentario (id:params.id)
			   dislike(false)
			   like(true)
			   persona(nick)
	   }
	   
		def connection = url.openConnection()
		connection.setRequestMethod("PUT")
		connection.setRequestProperty("Content-Type" ,"text/xml" )
		connection.doOutput=true
			Writer writer = new OutputStreamWriter(connection.outputStream)
			writer.write(gXml.toString())
			writer.flush()
			writer.close()
			connection.connect()
			
			def miXml = new XmlSlurper().parseText(connection.content.text)
			serviceResponse = miXml.mensaje
			
			/**
			 * Lo que me responde el servidor
			 */
			if(serviceResponse == "")
			{
			
				serviceResponse = "El usuario $params.mensaje ha inciado sesion correctamente"
			}
	   
			redirect (action: 'consultarTodosLosComentarios')
	   
	   } //fin metodo modificar calificacion like
	
	/**
	* Metodo que se encarga de modificar una calificacion dislike en el comentario
	*/
	def modificarComentarioDislike () {
		
		def serviceResponse = "No hay respuesta"
		/**
		 * Se establece la URL de la ubicacion
		 * del servicio
		 */
		def url = new URL("http://localhost:8080/miOrquidea/calificacion/modificarCalificacion" )
		/**
		* Se extraen los parametros y convierte a formato
		* XML para luego ser enviada a la aplicacion miOrquidea
		*
		*/
		def nick = session.nickname
	   
	   /**
		* Con estas funciones creamos el XML
		*/
	   def gXml = new StringWriter()
	   def xml = new MarkupBuilder(gXml)
		
	   /**
		* Creando el XML para pasarlo al servicio
		*/
	   xml.calificacion() {
			   comentario (id:params.id)
			   dislike(true)
			   like(false)
			   persona(nick)
	   }
	   
		def connection = url.openConnection()
		connection.setRequestMethod("PUT")
		connection.setRequestProperty("Content-Type" ,"text/xml" )
		connection.doOutput=true
			Writer writer = new OutputStreamWriter(connection.outputStream)
			writer.write(gXml.toString())
			writer.flush()
			writer.close()
			connection.connect()
			
			def miXml = new XmlSlurper().parseText(connection.content.text)
			serviceResponse = miXml.mensaje
			
			/**
			 * Lo que me responde el servidor
			 */
			if(serviceResponse == "")
			{
			
				serviceResponse = "El usuario $params.mensaje ha inciado sesion correctamente"
			}
	   
			redirect (action: 'consultarTodosLosComentarios')
	   
	   } //fin metodo modificar calificacion dislike
	
	/**
	* Metodo que se encarga de redireccionar a la vista modificar comentario
	*/
	def modificarComentarioUsuario = { 
		
		def arrayetiquetas = params.id.split(",")
		println("comentarito = " )
		render (view:'modificarComentarioVista', model:[comentario:arrayetiquetas[0], mensaje:arrayetiquetas[1], usuario:session.nickname])
	}
	
	/**
	* Metodo que se encarga de modificar un comentario
	*/
	def modificarComentario = {
		
		def serviceResponse = "No hay respuesta"
		/**
		 * Se establece la URL de la ubicacion
		 * del servicio
		 */
		def url = new URL("http://localhost:8080/miOrquidea/comentario/modificarComentario" )
		/**
		* Se extraen los parametros y convierte a formato
		* XML para luego ser enviada a la aplicacion miOrquidea
		*
		*/
		def nick = session.nickname
	   
	   /**
		* Con estas funciones creamos el XML
		*/
	   def gXml = new StringWriter()
	   def xml = new MarkupBuilder(gXml)
		
	   /**
		* Creando el XML para pasarlo al servicio
		*/
	   xml.comentario() {
			   idComentario (id:params.id)
			   mensaje(params.mensaje)
			   usuario(nick)
	   }
	   
		def connection = url.openConnection()
		connection.setRequestMethod("PUT")
		connection.setRequestProperty("Content-Type" ,"text/xml" )
		connection.doOutput=true
			Writer writer = new OutputStreamWriter(connection.outputStream)
			writer.write(gXml.toString())
			writer.flush()
			writer.close()
			connection.connect()
			
			def miXml = new XmlSlurper().parseText(connection.content.text)
			serviceResponse = miXml.mensaje
			
			/**
			 * Lo que me responde el servidor
			 */
			if(serviceResponse == "")
			{
			
				serviceResponse = "El usuario $params.mensaje ha inciado sesion correctamente"
			}
	   
			redirect (action: 'consultarTodosLosComentarios')
	   
	   } //fin metodo modificar comentario
	
	/**
	* Metodo que se encarga de eliminar un comentario
	*/
	def eliminarComentario = {
		
		def nick = session.nickname
	 	def url = new URL("http://localhost:8080/miOrquidea/comentario/eliminarComentario?idComentario=" + params.id + "&usuario=" +  nick)			
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
			
			redirect (action :'consultarTodosLosComentarios')
	}// fin metodo eliminar comentario
	
	
} // fin Comentario Controller









































































































































































