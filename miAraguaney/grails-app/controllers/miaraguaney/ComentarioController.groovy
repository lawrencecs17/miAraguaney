package miaraguaney

import org.apache.commons.logging.*
import groovyx.net.http.HTTPBuilder
import static groovyx.net.http.Method.*
import static groovyx.net.http.ContentType.*
import grails.converters.*
import java.util.Date
import miaraguaney.ComentarioCliente

class ComentarioController {

   	private static Log log = LogFactory.getLog("Logs."+UsuarioController.class.getName())
	
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
		   def miXml = new XmlSlurper().parseText(connection.content.text)
		   listaComentario = procesarXmlComentario(miXml)
	   }
	   else{
		   render "Se ha generado un error:"
		   render connection.responseCode
		   render connection.responseMessage
	   }

	   render (view:"consultarComentarios", model:[comentarios:listaComentario])
   }
   
   /**
   * Metodo encargado de procesar el archivo XML recibido del
   * servicio miOrquidea app
   * @param xml
   * @return
   */
  def procesarXmlComentario(def xml)
  {
	  ArrayList<ComentarioCliente> listaComentario = new ArrayList<ComentarioCliente>()
	  
	  for (int i=0;i< xml.comentario.size();i++)
	  {
		  ComentarioCliente comentario = new ComentarioCliente()
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
		  if (comentario.cantidadComentados == '')
			 comentario.cantidadComentados = '0'
		  
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
* Invocacion al servicio de registrar comentario
*/
def crearComentario = {
   
   
   
  
   
    def prueba= session.nickname 
    render prueba
   
   }

}












































































































































































