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
	ArrayList<ComentarioCliente> listaComentado = new ArrayList<ComentarioCliente>()
	ArrayList<ComentarioCliente> listaReply = new ArrayList<ComentarioCliente>()
	static String urlVista  
	static String mensaje
	static String nombreEtiqueta = ""
	static String nombreTag
	static String nombreComentario1
	static String nombreCom1
	static String bandera = "miOrquidea"
	//static String bandera = "Spring"
	   
    def index() { 
		redirect (action:'consultarTodosLosComentarios')
	}
	
	/**
	* Invocacion al servicio de consultar todos los Comentarios
	* registrados en el sistema
	*/
	def consultarTodosLosComentarios = {
	   
		try
		{
				   urlVista = "consultarComentarios"
				   /**
				   * Se ubica la URL del servicio que lista a todos los Comentarios
				   */
				   if (bandera.equals("miOrquidea"))
				   {
					   def url = new URL("http://localhost:8080/miOrquidea/comentario/listarTodos" )
				   }
				   else
				   {
					   def url = new URL("http://localhost:8084/SPRINGDESESPERADO/rest/comentarios" )
				   }
				   def listaComentario
				   
				   /**
				   * Se establece la conexion con el servicio
				   * Se determina el tipo de peticion (GET) y
				   * el contenido de la misma (Archivo plano XML)
				   */
				   def connection = url.openConnection()
				   connection.setRequestMethod("GET" )
				   connection.setRequestProperty("Content-Type" ,"text/xml" )
				   
				   if (bandera.equals("miOrquidea"))
				   {
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
				   }
				   else
				   {
					   def miXml = new XmlSlurper().parseText(connection.content.text)
					   listaComentario = procesarXmlComentarioSpring(miXml)
				   }

			   render (view: urlVista, model:[comentarios:listaComentario, comentados: listaComentado, usuario:session.nickname, servicio: bandera])
		}
		catch(Exception)
		{
			def miAlerta = "Ha ocurrido un error en el servidor, intente luego."
			render(view:"perfil",model:[email:session.email,usuario:session.nickname,alerta:miAlerta])
		}
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
	  listaComentario.clear()
	  listaComentado.clear()
	  String nickname = session.nickname
	  
	  /**
	  * recorro toda el xml de los comentarios registrados en el sistema 
	  */
	  for (int i=0;i< xml.comentario.size();i++)
	  {
		  ComentarioCliente comentario = new ComentarioCliente()  
		  comentario.idComentario = xml.comentario[i].@id.text()
		  comentario.mensaje = xml.comentario[i].mensaje
		  comentario.fecha = xml.comentario[i].fecha
		  comentario.principal = xml.comentario[i].principal
		  
		  /**
		  * busca el nickname del usuario por el idUsuario del xml
		  */
		  def nombreUsuario = buscarUsuario(xml.comentario[i].autor.@id.text())
		  if (nombreUsuario)
		  {
			  comentario.autor = nombreUsuario
		  }
		  
		  /**
		  * busca la cantidad de like que tiene un comentario por el idComentario del xml
		  */
		  def cantidadLike = buscarLike(xml.comentario[i].@id.text())
		  if (cantidadLike)
		  {
			  comentario.cantidadLike = cantidadLike
		  }
		  if (comentario.cantidadLike == '')
		      comentario.cantidadLike = '0'

		  /**
	      * busca la cantidad de dislike que tiene un comentario por el idComentario del xml
		  */
		  def cantidadDislike = buscarDislike(xml.comentario[i].@id.text())
		  if (cantidadDislike)
		  {
			  comentario.cantidadDislike = cantidadDislike
		  }
		  if (comentario.cantidadDislike == '')
		  	  comentario.cantidadDislike = '0'
				
		  /**
		  * busca la cantidad de respuestas que tiene un comentario por el idComentario del xml
		  */
		  def cantidadComentados = buscarComentados(xml.comentario[i].@id.text())
		  if (cantidadComentados)
		  {
			 comentario.cantidadComentados = cantidadComentados
		  }
		  
		  /**
		  * busca si el usuario califico like en el comentario por el idComentario del xml
		  */
		  String like = buscarCalificacionLike(xml.comentario[i].@id.text(), nickname)
		  comentario.calificacionLike = like
		  
		  /**
		  * busca si el usuario califico dislike en el comentario por el idComentario del xml
		  */
		  String dislike = buscarCalificacionDislike(xml.comentario[i].@id.text(), nickname)
		  comentario.calificacionDislike = dislike
		  
		  /**
		  * lista de las respuestas que tiene un comentario
		  */		  
		  xml.comentario[i].comentado.comentario.each { p ->
		  
			  	ComentarioCliente comentarioComentado = new ComentarioCliente()
				def xmlComentado = xmlComentado(p.@id.text())
				def nombreUsuarioComentado = buscarUsuario(xmlComentado.autor.@id.text())
						
				if (nombreUsuarioComentado)
				{
					comentarioComentado.autor = nombreUsuarioComentado
				}
						
				comentarioComentado.idComentarioComentado = xml.comentario[i].@id.text()
				comentarioComentado.idComentario = xmlComentado.@id.text()
				comentarioComentado.fecha = xmlComentado.fecha.text()
				comentarioComentado.mensaje = xmlComentado.mensaje.text()
				comentarioComentado.principal = xmlComentado.principal.toString()
						
				String likeRespuesta = buscarCalificacionLike(xmlComentado.@id.text(), nickname)
				comentarioComentado.calificacionLike = likeRespuesta
						
				String dislikeRespuesta = buscarCalificacionDislike(xmlComentado.@id.text(), nickname)
				comentarioComentado.calificacionDislike = dislikeRespuesta
						
				def cantidadLikeRespuesta = buscarLike(xmlComentado.@id.text())
				if (cantidadLikeRespuesta)
				{
					comentarioComentado.cantidadLike = cantidadLikeRespuesta
				}
				if (comentarioComentado.cantidadLike == '')
					comentarioComentado.cantidadLike = '0'
			  
				def cantidadDislikeRespuesta = buscarDislike(xmlComentado.@id.text())
				if (cantidadDislikeRespuesta)
				{
					comentarioComentado.cantidadDislike = cantidadDislikeRespuesta
				}
				if (comentarioComentado.cantidadDislike == '')
					comentarioComentado.cantidadDislike = '0'
						
				listaComentado.add(comentarioComentado)
			}
		   listaComentario.add(comentario)
	  }
	  return listaComentario
  }
  
  /**
  * Metodo encargado de buscar un comentario en especifico por
  *  el id del Comentario
  */
  def xmlComentado (String idComentario)
  {
	  try
	  {
		 /**
		 * Se ubica la URL del servicio que lista los comentario por idComentario
		 */
		 def url = new URL("http://localhost:8080/miOrquidea/comentario/listarPorComentario?idComentario=" +  idComentario)
		 def xmlComentario
		 
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
			 xmlComentario = miXml
		 }
		 else{
			 render "Se ha generado un error:"
			 render connection.responseCode
			 render connection.responseMessage
		 }
		 
		 return xmlComentario 
	  }
	  catch(Exception)
	  {
		  def miAlerta = "Ha ocurrido un error en el servidor, intente luego."
		  render(view:"perfil",model:[email:session.usuario.email, usuario:session.usuario.nickname, alerta:miAlerta])
	  }
  }
  
  /**
  * Metodo encargado de buscar un usuario en especifico por el idUsuario
  *  y retornar el nickname del usuario
  */
  def buscarUsuario (String Usuario)
  {
	 /**
	 * Se ubica la URL del servicio que lista a todas los Usuarios
	 */
	  try
	  {
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
	 catch(Exception)
	 {
		 def miAlerta = "Ha ocurrido un error en el servidor, intente luego."
		 render(view:"perfil",model:[email:session.usuario.email, usuario:session.usuario.nickname, alerta:miAlerta])
	 }
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
     * Metodo encargado de buscar la cantidad de calificaciones de like que tiene un comentario 
   * registrados en el sistema por id del Comentario
  */
  def buscarLike (String comentario)
  {
	/**
	* Se ubica la URL del servicio que lista a todas las Calificaciones
	*/
	  try
	  {
		  def cantidadLike
		  if (bandera.equals("miOrquidea"))
		  {
				def url = new URL("http://localhost:8080/miOrquidea/calificacion/consultarLikeDislile?idComentario=" + comentario)
				
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
		  }
		  else
		  {
			  def url = new URL("http://localhost:8084/SPRINGDESESPERADO/rest/comentario/puntuacion/megusta/" + comentario)
			  
			  /**
			  * Se establece la conexion con el servicio
			  * Se determina el tipo de peticion (GET) y
			  * el contenido de la misma (Archivo plano XML)
			  */
			 def connection = url.openConnection()
			 connection.setRequestMethod("GET" )
			 connection.setRequestProperty("Content-Type" ,"text/xml" )
			 
			 def miXml = new XmlSlurper().parseText(connection.content.text)
			 cantidadLike = miXml.count.text()
		  }
			
		  return cantidadLike
	  }
	  catch(Exception)
	  {
		  def miAlerta = "Ha ocurrido un error en el servidor, intente luego."
		  render(view:"perfil",model:[email:session.usuario.email, usuario:session.usuario.nickname, alerta:miAlerta])
	  }
  }
  
   /**
   * Metodo encargado de buscar la cantidad de calificaciones de Dislike que tiene un comentario 
   * registrados en el sistema por id del Comentario
   */
   def buscarDislike (String comentario)
   {
	 /**
	 * Se ubica la URL del servicio que lista a todas las Calificaciones
	 */
	   try
	   {
		   def cantidadDislike
		   if (bandera.equals("miOrquidea"))
		   {
				 def url = new URL("http://localhost:8080/miOrquidea/calificacion/consultarLikeDislile?idComentario=" + comentario)
				  
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
		   }
		   else
		   {
			   def url = new URL("http://localhost:8084/SPRINGDESESPERADO/rest/comentario/puntuacion/nomegusta/" + comentario)
			   
			  /**
			  * Se establece la conexion con el servicio
			  * Se determina el tipo de peticion (GET) y
			  * el contenido de la misma (Archivo plano XML)
			  */
			  def connection = url.openConnection()
			  connection.setRequestMethod("GET" )
			  connection.setRequestProperty("Content-Type" ,"text/xml" )
			  
			  def miXml = new XmlSlurper().parseText(connection.content.text)
			  cantidadDislike = miXml.count.text()
		   }
			 
		   return cantidadDislike
			 
		}
	    catch(Exception)
	    {
		   def miAlerta = "Ha ocurrido un error en el servidor, intente luego."
		   render(view:"perfil",model:[email:session.usuario.email, usuario:session.usuario.nickname, alerta:miAlerta])
	    }
   }
   
    /**
    * Metodo encargado de buscar la cantidad de respuesta que tiene un comentario 
    * registrados en el sistema por id del Comentario
    */
	def buscarComentados (String comentario)
	{
	  /**
	  * Se ubica la URL del servicio que lista a todas los Comentarios comentados
	  */
		try
		{
			def cantidadComentados
			if (bandera.equals("miOrquidea"))
			{
				  def url = new URL("http://localhost:8080/miOrquidea/comentario/contarComentados?idComentario=" + comentario)
				  
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
			}
			else
			{
				def url = new URL("http://localhost:8084/SPRINGDESESPERADO/rest/replyComentarios/" + comentario)
				
				/**
				 * Se establece la conexion con el servicio
				* Se determina el tipo de peticion (GET) y
				* el contenido de la misma (Archivo plano XML)
				*/
				def connection = url.openConnection()
				connection.setRequestMethod("GET" )
				connection.setRequestProperty("Content-Type" ,"text/xml" )
				
				def miXml = new XmlSlurper().parseText(connection.content.text)
				cantidadComentados = miXml.count.text()
			}
			  
		    return cantidadComentados
		}
		catch(Exception)
		{
		   def miAlerta = "Ha ocurrido un error en el servidor, intente luego."
		   render(view:"perfil",model:[email:session.usuario.email, usuario:session.usuario.nickname, alerta:miAlerta])
		}
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
		 try
		 {
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
		 catch(Exception)
		 {
			def miAlerta = "Ha ocurrido un error en el servidor, intente luego."
			render(view:"perfil",model:[email:session.usuario.email, usuario:session.usuario.nickname, alerta:miAlerta])
		 }
	 }
	 
	 /**
	 * Metodo encargado de procesar el archivo XML recibido del
	 * servicio miOrquidea app y retorna el valor de like de la calificacion 
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
		  try
		  {
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
		  catch(Exception)
		  {
			 def miAlerta = "Ha ocurrido un error en el servidor, intente luego."
			 render(view:"perfil",model:[email:session.usuario.email, usuario:session.usuario.nickname, alerta:miAlerta])
		  }
	  }
	  
	  /**
	  * Metodo encargado de procesar el archivo XML recibido del
	  * servicio miOrquidea app y retorna el valor de dislike de la calificacion
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
	* metodo que llama a la vista crear comentario y le paso el nickname de usuario
	* para que lo muestre en la vista
	*/
	def crearComentario = { 
	    render (view: 'crearComentario',  model:[usuario:session.nickname])
	}

	/**
	* Invocacion al servicio de registrar comentario
	*/	
	def agregarComentario = {
		
		try
		{
				if(Token.tokenVigente(session.usuario.email))
				{
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
				    * Creando el XML Comentario para pasarlo al servicio
				    */
					def listaEtiquetas = null
					listaEtiquetas = params.etiquetas
				    xml.comentario() {
						   autor (nick)
						   mensaje(params.mensaje)
						   if (listaEtiquetas != '')
						   {
							   def arrayetiquetas = listaEtiquetas.split(",")
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
							serviceResponse = "Comentario creado"
						}
						
						redirect (action: 'consultarComentarioPorUsuario', model:[usuario:session.nickname])
		
		
					}
					else
					{
						destruirSesion()
					}
		}
		catch(Exception)
		{
			def miAlerta = "Ha ocurrido un error en el servidor, intente luego."
			render(view:"perfil",model:[email:session.usuario.email, usuario:session.usuario.nickname, alerta:miAlerta])
		}
							
	} //fin metodo agregar comentario

	/**
	* Metodo que se encarga de crear una calificacion like en el comentario 
	*/
	def crearComentarioLike () {
		
	try
	{
		if(Token.tokenVigente(session.usuario.email))
		{
			def serviceResponse = "No hay respuesta"
			
			/**
			* Se establece la URL de la ubicacion
			* del servicio
			*/
				def url = new URL("http://localhost:8080/miOrquidea/calificacion/crearCalificacion" )
				
				def nick = session.nickname
			   
			    /**
			    * Con estas funciones creamos el XML  	
			    */
			    def gXml = new StringWriter()
			    def xml = new MarkupBuilder(gXml)
			    
			    /**
			    * Creando el XML Calificacion like para pasarlo al servicio
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
						serviceResponse = "Calificacion like creado"
					}
			   
					if (urlVista == "consultarComentarios")
					{
						redirect (action: 'consultarTodosLosComentarios')
					}
					else
					{
						if (urlVista == "perfilUsuario")
						{
							redirect (action: 'consultarComentarioPorUsuario')
						}
						else
						{
							if (urlVista == "consultarComentarioTag")
							{
								redirect (action: 'buscarEtiqueta')
							}
							else
							{
								if (urlVista == "consultarComentarioId")
								{
									redirect (action: 'buscarPorId')
								}
								else
								{
									if (urlVista == "consultarComentarioSinTag")
									{
										redirect (action: 'buscarSinEtiqueta')
									}
								}
							}
						}
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
		render(view:"perfil",model:[email:session.usuario.email, usuario:session.usuario.nickname, alerta:miAlerta])
	}
	} //fin metodo crear calificacion like
	
	/**
	* Metodo que se encarga de crear una calificacion dislike en el comentario
	*/
	def crearComentarioDislike () {
		
	try
	{
		if(Token.tokenVigente(session.usuario.email))
		{
			def serviceResponse = "No hay respuesta"
			
			/**
			* Se establece la URL de la ubicacion
			* del servicio
			*/
				def url = new URL("http://localhost:8080/miOrquidea/calificacion/crearCalificacion" )
		
				def nick = session.nickname
			   
			    /**
				* Con estas funciones creamos el XML
				*/
			    def gXml = new StringWriter()
			    def xml = new MarkupBuilder(gXml)
				
			    /**
				* Creando el XML Calificacion dislike para pasarlo al servicio
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
						serviceResponse = "Calificacion dislike creado"
					}
			   
					if (urlVista == "consultarComentarios")
					{
						redirect (action: 'consultarTodosLosComentarios')
					}
					else
					{
						if (urlVista == "perfilUsuario")
						{
							redirect (action: 'consultarComentarioPorUsuario')
						}
						else
						{
							if (urlVista == "consultarComentarioTag")
							{
								redirect (action: 'buscarEtiqueta')
							}
							else
							{
								if (urlVista == "consultarComentarioId")
								{
									redirect (action: 'buscarPorId')
								}
								else
								{
									if (urlVista == "consultarComentarioSinTag")
									{
										redirect (action: 'buscarSinEtiqueta')
									}
								}
							}
						}
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
		render(view:"perfil",model:[email:session.usuario.email, usuario:session.usuario.nickname, alerta:miAlerta])
	}
	} //fin metodo crear calificacion dislike
	
	/**
	* Metodo que se encarga de modificar una calificacion like en la calificacion del comentario
	*/
	def modificarComentarioLike () {
		
		try
		{
			if(Token.tokenVigente(session.usuario.email))
			{
				def serviceResponse = "No hay respuesta"
				
				/**
				* Se establece la URL de la ubicacion
				* del servicio
				*/
					def url = new URL("http://localhost:8080/miOrquidea/calificacion/modificarCalificacion" )
			
					def nick = session.nickname
				   
				    /**
					* Con estas funciones creamos el XML
					*/
				    def gXml = new StringWriter()
				    def xml = new MarkupBuilder(gXml)
					
				    /**
					* Creando el XML calificacion like modificado para pasarlo al servicio
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
							serviceResponse = "Calificacion like modificado"
						}
				   
						if (urlVista == "consultarComentarios")
						{
							redirect (action: 'consultarTodosLosComentarios')
						}
						else
						{
							if (urlVista == "perfilUsuario")
							{
								redirect (action: 'consultarComentarioPorUsuario')
							}
							else
							{
								if (urlVista == "consultarComentarioTag")
								{
									redirect (action: 'buscarEtiqueta')
								}
								else
								{
									if (urlVista == "consultarComentarioId")
									{
										redirect (action: 'buscarPorId')
									}
									else
									{
										if (urlVista == "consultarComentarioSinTag")
										{
											redirect (action: 'buscarSinEtiqueta')
										}
									}
								}
							}
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
			render(view:"perfil",model:[email:session.usuario.email, usuario:session.usuario.nickname, alerta:miAlerta])
		}
	} //fin metodo modificar calificacion like
	
	/**
	* Metodo que se encarga de modificar una calificacion dislike en el comentario
	*/
	def modificarComentarioDislike () {
		
		try
		{
			if(Token.tokenVigente(session.usuario.email))
			{
				def serviceResponse = "No hay respuesta"
				/**
				* Se establece la URL de la ubicacion
				* del servicio
				*/
					def url = new URL("http://localhost:8080/miOrquidea/calificacion/modificarCalificacion" )
			
					def nick = session.nickname
				   
				    /**
					* Con estas funciones creamos el XML
					*/
				    def gXml = new StringWriter()
				    def xml = new MarkupBuilder(gXml)
					
				    /**
					* Creando el XML calificacion dislike modificado para pasarlo al servicio
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
							serviceResponse = "Calificacion dislike modificado"
						}
				   
						if (urlVista == "consultarComentarios")
						{
							redirect (action: 'consultarTodosLosComentarios')
						}
						else
						{
							if (urlVista == "perfilUsuario")
							{
								redirect (action: 'consultarComentarioPorUsuario')
							}
							else
							{
								if (urlVista == "consultarComentarioTag")
								{
									redirect (action: 'buscarEtiqueta')
								}
								else
								{
									if (urlVista == "consultarComentarioId")
									{
										redirect (action: 'buscarPorId')
									}
									else
									{
										if (urlVista == "consultarComentarioSinTag")
										{
											redirect (action: 'buscarSinEtiqueta')
										}
									}
								}
							}
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
				render(view:"perfil",model:[email:session.usuario.email, usuario:session.usuario.nickname, alerta:miAlerta])
			}
	} //fin metodo modificar calificacion dislike
	
	/**
	* Metodo que se encarga de redireccionar a la vista modificar comentario
	*/
	def modificarComentarioUsuario = { 
		try
		{
			if(Token.tokenVigente(session.usuario.email))
			{
				def comentarioMensaje = params.id.split(",")
				render (view:'modificarComentarioVista', model:[comentario:comentarioMensaje[0], mensaje:comentarioMensaje[1], usuario:session.nickname])
			}
			else
			{
				destruirSesion()
			}
		}
		catch(Exception)
		{
			def miAlerta = "Ha ocurrido un error en el servidor, intente luego."
			render(view:"perfil",model:[email:session.usuario.email, usuario:session.usuario.nickname, alerta:miAlerta])
		}
	}
	
	/**
	* Metodo que se encarga de modificar un comentario
	*/
	def modificarComentario = {
		try
		{
			if(Token.tokenVigente(session.usuario.email))
			{
				def serviceResponse = "No hay respuesta"
				/**
				* Se establece la URL de la ubicacion
				* del servicio
				*/
					def url = new URL("http://localhost:8080/miOrquidea/comentario/modificarComentario" )
					def nick = session.nickname
				   
				    /**
					* Con estas funciones creamos el XML
					*/
				    def gXml = new StringWriter()
				    def xml = new MarkupBuilder(gXml)
					
				    /**
					* Creando el XML Comentario modificado para pasarlo al servicio
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
							serviceResponse = "Comentario modificado"
						}
				   
		                if (urlVista == "consultarComentarios")
						{
							redirect (action: 'consultarTodosLosComentarios')
						}
						else
						{
							if (urlVista == "perfilUsuario")
							{
								redirect (action: 'consultarComentarioPorUsuario')
							}
							else
							{
								if (urlVista == "consultarComentarioTag")
								{
									redirect (action: 'buscarEtiqueta')
								}
								else
								{
									if (urlVista == "consultarComentarioId")
									{
										redirect (action: 'buscarPorId')
									}
									else
									{
										if (urlVista == "consultarComentarioSinTag")
										{
											redirect (action: 'buscarSinEtiqueta')
										}
									}
								}
							}
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
			render(view:"perfil",model:[email:session.usuario.email, usuario:session.usuario.nickname, alerta:miAlerta])
		}
	} //fin metodo modificar comentario
	
	/**
	* Metodo que se encarga de eliminar un comentario por nickname y idComentario
	*/
	def eliminarComentario = {

		try
		{
			if(Token.tokenVigente(session.usuario.email))
	        {
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
						mensaje = miXml.mensaje 
						if(serviceResponse == "")
						{
							serviceResponse = "Comentario eliminado"
						}
					}
					if (urlVista == "consultarComentarios")
					{
							redirect (action: 'consultarTodosLosComentarios')
					}
					else
					{
							if (urlVista == "perfilUsuario")
							{
								redirect (action: 'consultarComentarioPorUsuario')
							}
							else
							{
								if (urlVista == "consultarComentarioTag")
								{
									redirect (action: 'buscarEtiqueta')
								}
								else
								{
									if (urlVista == "consultarComentarioId")
									{
										redirect (action: 'busquedaPorId')
									}
									else
									{
										if (urlVista == "consultarComentarioSinTag")
										{
											redirect (action: 'buscarSinEtiqueta')
										}
									}
								}
							}
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
			render(view:"perfil",model:[email:session.usuario.email, usuario:session.usuario.nickname, alerta:miAlerta])
		}
	}// fin metodo eliminar comentario
	
	/**
	* Metodo que se encarga de redireccionar a la vista responder comentario
	*/
	def responderComentarioUsuario = {
		
		try
		{
			if(Token.tokenVigente(session.usuario.email))
			{
				def idComentario = params.id
				render (view:'responderComentarioVista', model:[comentario:idComentario, usuario:session.nickname])
			}
			else
			{
				destruirSesion()
			}
		}
		catch(Exception)
		{
		   def miAlerta = "Ha ocurrido un error en el servidor, intente luego."
		   render(view:"error",model:[email:session.usuario.email, usuario:session.usuario.nickname, alerta:miAlerta])
		}
	}
	
	/**
	* Invocacion al servicio de registrar comentario
	*/
	def responderComentario = {
		try
		{
			if(Token.tokenVigente(session.usuario.email))
			{
				def serviceResponse = "No hay respuesta"
				/**
				* Se establece la URL de la ubicacion
				* del servicio
				*/
				
					def url = new URL("http://localhost:8080/miOrquidea/comentario/crearComentado" )
			
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
						   comentario (id:params.id)
						   autorComentado(nick)
						   mensaje(params.mensaje)
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
							serviceResponse = "Respuesta exitosa"
						}
						
						if (urlVista == "consultarComentarios")
						{
							redirect (action: 'consultarTodosLosComentarios')
						}
						else
						{
							if (urlVista == "perfilUsuario")
							{
								redirect (action: 'consultarComentarioPorUsuario')
							}
							else
							{
								if (urlVista == "consultarComentarioTag")
								{
									redirect (action: 'buscarEtiqueta')
								}
								else
								{
									if (urlVista == "consultarComentarioId")
									{
										redirect (action: 'buscarPorId')
									}
									else
									{
										if (urlVista == "consultarComentarioSinTag")
										{
											redirect (action: 'buscarSinEtiqueta')
										}
									}
								}
							}
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
			 render(view:"usuario/perfil",model:[email:session.usuario.email, usuario:session.usuario.nickname, alerta:miAlerta])
		}
		
	} //fin metodo agregar comentario
	
	/**
	* Invocacion al servicio de consultar todos los Comentarios
	* registrados en el sistema
	*/
	def consultarComentarioPorUsuario = {
	   
	   urlVista = "perfilUsuario"
	   /**
	   * Se ubica la URL del servicio que lista a todos los Comentarios
	   */
	   try
	   {
		   def url = new URL("http://localhost:8080/miOrquidea/comentario/listarPorUsuario?usuario=" + session.nickname )
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
		   render (view:urlVista, model:[comentarios:listaComentario, comentados: listaComentado, usuario:session.nickname])
	   }
	   catch(Exception)
	   {
		  def miAlerta = "Ha ocurrido un error en el servidor, intente luego."
		  render(view:"usuario/perfil",model:[email:session.usuario.email, usuario:session.usuario.nickname, alerta:miAlerta])
	   }
   }
	
	/*
	* metodo que destruye todas las variables de session del sistema
	*/
	def destruirSesion()
	{
		session.removeAttribute("usuario")
		session.removeAttribute("nickname")
		session.removeAttribute("email")
		session.removeAttribute("password")
		render(view:"../index")
	}
	
	/**
	* Metodo que se encarga de redireccionar a la vista seleccionar etiqueta
	*/
	def busquedaEtiqueta = {
		
	    render (view:'consultarTag', model:[usuario:session.nickname])
	}
	
	/**
	* Metodo que se encarga de listar los comentarios por etiqueta
	*/
	def buscarEtiqueta = {
		
		urlVista = "consultarComentarioTag"
		nombreEtiqueta = null
		nombreEtiqueta = params.etiqueta
		
		if (nombreEtiqueta != null) 
		{
			nombreTag = nombreEtiqueta
		}
		if (nombreEtiqueta == null)
		{
			nombreEtiqueta = nombreTag
		}

		/**
		* Se ubica la URL del servicio que lista a todos los Comentarios
		*/
		try
		{
			def url = new URL("http://localhost:8080/miOrquidea/comentario/listarPorEtiqueta?nombre=" + nombreEtiqueta )
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
				mensaje = ""
				def miXml = new XmlSlurper().parseText(connection.content.text)
				if (miXml.mensaje == "La etiqueta no existe")
				{
					mensaje = miXml.mensaje 
			    }
				else
				{
					listaComentario = procesarXmlComentario(miXml)
				}
			}
			else{
				render "Se ha generado un error:"
				render connection.responseCode
				render connection.responseMessage
			}
	 
			render (view:urlVista, model:[comentarios:listaComentario, comentados: listaComentado, usuario:session.nickname, etiqueta:nombreEtiqueta, error: mensaje])
		}
		catch(Exception)
		{
		   def miAlerta = "Ha ocurrido un error en el servidor, intente luego."
		   render(view:"usuario/perfil",model:[email:session.usuario.email, usuario:session.usuario.nickname, alerta:miAlerta])
		}
	}
	
	
	/**
	* Metodo que se encarga de listar los comentarios sin etiqueta
	*/
	def buscarSinEtiqueta = {
		
		urlVista = "consultarComentarioSinTag"
		
		/**
		* Se ubica la URL del servicio que lista a todos los Comentarios
		*/
		try
		{
			def url = new URL("http://localhost:8080/miOrquidea/comentario/listarSinEtiqueta")
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
				mensaje = ""
				def miXml = new XmlSlurper().parseText(connection.content.text)
				if (miXml.mensaje == "La etiqueta no existe")
				{
					mensaje = miXml.mensaje
				}
				else
				{
					listaComentario = procesarXmlComentario(miXml)
				}
			}
			else{
				render "Se ha generado un error:"
				render connection.responseCode
				render connection.responseMessage
			}
	 
			render (view:urlVista, model:[comentarios:listaComentario, comentados: listaComentado, usuario:session.nickname, error: mensaje])
		}
		catch(Exception)
		{
		   def miAlerta = "Ha ocurrido un error en el servidor, intente luego."
		   render(view:"usuario/perfil",model:[email:session.usuario.email, usuario:session.usuario.nickname, alerta:miAlerta])
		}
	}
	
	/**
	* Metodo que se encarga de redireccionar a la vista seleccionar id comentario
	*/
	def busquedaPorId = {
		
		render (view:'seleccionarIdComentario', model:[usuario:session.nickname])
	}
	
	/**
	* Metodo que se encarga de listar los comentarios por id
	*/
	def buscarPorId  = {
		
		urlVista = "consultarComentarioId"
		nombreComentario1 = null
		nombreComentario1 = params.idcomentario
		
		if (nombreComentario1 != null)
		{
			nombreCom1 = nombreComentario1
		}
		if (nombreComentario1 == null)
		{
			nombreComentario1 = nombreCom1
		}
		
		/**
		* Se ubica la URL del servicio que lista a todos los Comentarios
		*/
		try
		{
			def url = new URL("http://localhost:8080/miOrquidea/comentario/listarPorComentario?idComentario=" + nombreComentario1)
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
				mensaje = ""
				def miXml = new XmlSlurper().parseText(connection.content.text)
				
				if (miXml.mensaje == "El comentario no existe")
				{
					mensaje = miXml.mensaje
				}
				else
				{
					listaComentario = procesarXmlIdComentario(miXml)
				}
			}
			else{
				render "Se ha generado un error:"
				render connection.responseCode
				render connection.responseMessage
			}
	 
			render (view:urlVista, model:[comentarios:listaComentario, comentados: listaComentado, usuario:session.nickname, idComentario:nombreComentario1, error: mensaje])
		}
		catch(Exception)
		{
		   def miAlerta = "Ha ocurrido un error en el servidor, intente luego."
		   render(view:"usuario/perfil",model:[email:session.usuario.email, usuario:session.usuario.nickname, alerta:miAlerta])
		}
	}
	
	
	/**
	* Metodo encargado de procesar el archivo XML recibido del
	* servicio miOrquidea app
	* @param xml
	* @return
	*/
	def procesarXmlIdComentario(def xml)
	{
	   ArrayList<ComentarioCliente> listaComentario = new ArrayList<ComentarioCliente>()
	   listaComentario.clear()
	   listaComentado.clear()
	   String nickname = session.nickname
	   
	   /**
	   * recorro toda el xml de los comentarios registrados en el sistema
	   */
		   ComentarioCliente comentario = new ComentarioCliente()
		   comentario.idComentario = xml.@id.text()
		   comentario.mensaje = xml.mensaje
		   comentario.fecha = xml.fecha
		   comentario.principal = xml.principal
		   
		   /**
		   * busca el nickname del usuario por el idUsuario del xml
		   */
		   def nombreUsuario = buscarUsuario(xml.autor.@id.text())
		   if (nombreUsuario)
		   {
			   comentario.autor = nombreUsuario
		   }
		   
		   /**
		   * busca la cantidad de like que tiene un comentario por el idComentario del xml
		   */
		   def cantidadLike = buscarLike(xml.@id.text())
		   if (cantidadLike)
		   {
			   comentario.cantidadLike = cantidadLike
		   }
		   if (comentario.cantidadLike == '')
			   comentario.cantidadLike = '0'
 
		   /**
		   * busca la cantidad de dislike que tiene un comentario por el idComentario del xml
		   */
		   def cantidadDislike = buscarDislike(xml.@id.text())
		   if (cantidadDislike)
		   {
			   comentario.cantidadDislike = cantidadDislike
		   }
		   if (comentario.cantidadDislike == '')
				 comentario.cantidadDislike = '0'
				 
		   /**
		   * busca la cantidad de respuestas que tiene un comentario por el idComentario del xml
		   */
		   def cantidadComentados = buscarComentados(xml.@id.text())
		   if (cantidadComentados)
		   {
			  comentario.cantidadComentados = cantidadComentados
		   }
		   
		   /**
		   * busca si el usuario califico like en el comentario por el idComentario del xml
		   */
		   String like = buscarCalificacionLike(xml.@id.text(), nickname)
		   comentario.calificacionLike = like
		   
		   /**
		   * busca si el usuario califico dislike en el comentario por el idComentario del xml
		   */
		   String dislike = buscarCalificacionDislike(xml.@id.text(), nickname)
		   comentario.calificacionDislike = dislike
		   
		   /**
		   * lista de las respuestas que tiene un comentario
		   */
		   xml.comentado.comentario.each { p ->
		   
				 ComentarioCliente comentarioComentado = new ComentarioCliente()
				 def xmlComentado = xmlComentado(p.@id.text())
				 def nombreUsuarioComentado = buscarUsuario(xmlComentado.autor.@id.text())
						 
				 if (nombreUsuarioComentado)
				 {
					 comentarioComentado.autor = nombreUsuarioComentado
				 }
						 
				 comentarioComentado.idComentarioComentado = xml.@id.text()
				 comentarioComentado.idComentario = xmlComentado.@id.text()
				 comentarioComentado.fecha = xmlComentado.fecha.text()
				 comentarioComentado.mensaje = xmlComentado.mensaje.text()
				 comentarioComentado.principal = xmlComentado.principal.toString()
						 
				 String likeRespuesta = buscarCalificacionLike(xmlComentado.@id.text(), nickname)
				 comentarioComentado.calificacionLike = likeRespuesta
						 
				 String dislikeRespuesta = buscarCalificacionDislike(xmlComentado.@id.text(), nickname)
				 comentarioComentado.calificacionDislike = dislikeRespuesta
						 
				 def cantidadLikeRespuesta = buscarLike(xmlComentado.@id.text())
				 if (cantidadLikeRespuesta)
				 {
					 comentarioComentado.cantidadLike = cantidadLikeRespuesta
				 }
				 if (comentarioComentado.cantidadLike == '')
					 comentarioComentado.cantidadLike = '0'
			   
				 def cantidadDislikeRespuesta = buscarDislike(xmlComentado.@id.text())
				 if (cantidadDislikeRespuesta)
				 {
					 comentarioComentado.cantidadDislike = cantidadDislikeRespuesta
				 }
				 if (comentarioComentado.cantidadDislike == '')
					 comentarioComentado.cantidadDislike = '0'
						 
				 listaComentado.add(comentarioComentado)
			 }
			listaComentario.add(comentario)
	 
	   return listaComentario
   }
	
	
	/**
	* Metodo encargado de procesar el archivo XML recibido del
	* servicio Spring app
	* @param xml
	* @return
	*/
	def procesarXmlComentarioSpring(def xml)
	{
	   ArrayList<ComentarioCliente> listaComentario = new ArrayList<ComentarioCliente>()
	   listaComentario.clear()
	   listaComentado.clear()
	   String nickname = session.nickname
	   
	   /**
	   * recorro toda el xml de los comentarios registrados en el sistema spring
	   */
	   for (int i=0;i< xml.comentario.size();i++)
	   {
		   ComentarioCliente comentario = new ComentarioCliente()
		   comentario.idComentario = xml.comentario[i].id.text()
		   comentario.mensaje = xml.comentario[i].mensaje
		   comentario.fecha = xml.comentario[i].fecha_creacion
		   if (xml.reply == "0")
		   {
			   comentario.principal = true
		   }
		   else
		   {
			   comentario.principal = false
		   }
		   comentario.autor = xml.nickName
		   
		   /**
		   * busca la cantidad de like que tiene un comentario por el idComentario del xml
		   */
		   def cantidadLike = buscarLike(xml.comentario[i].id.text())
		   if (cantidadLike)
		   {
			   comentario.cantidadLike = cantidadLike
		   }
		   if (comentario.cantidadLike == '')
			   comentario.cantidadLike = '0'
 
		   /**
		   * busca la cantidad de dislike que tiene un comentario por el idComentario del xml
		   */
		   def cantidadDislike = buscarDislike(xml.comentario[i].id.text())
		   if (cantidadDislike)
		   {
			   comentario.cantidadDislike = cantidadDislike
		   }
		   if (comentario.cantidadDislike == '')
				 comentario.cantidadDislike = '0'
				 
		   /**
		   * busca la cantidad de respuestas que tiene un comentario por el idComentario del xml
		   */
		   def cantidadComentados = buscarComentados(xml.comentario[i].id.text())
		   if (cantidadComentados)
		   {
			  comentario.cantidadComentados = cantidadComentados
		   }
		   
		   /**
		   * lista de las respuestas que tiene un comentario
		   */
		   
		   def listaRespuesta = buscarRespuestaSpring(xml.comentario[i].id.text())
		   listaComentario.add(comentario)
	   }
	   return listaComentario
   }
	
	/**
	* Metodo encargado de buscar todos las respuestas que tiene un comentario en
	* el servicio spring
	*/
	def buscarRespuestaSpring(String idComentario)
	{
		/**
		* Se ubica la URL del servicio que lista a todas los Usuarios
		*/
		 try
		 {
			def url = new URL("http://localhost:8084/SPRINGDESESPERADO/rest/replyComentarios/" + idComentario )
			def lista
			/**
			 * Se establece la conexion con el servicio
			 * Se determina el tipo de peticion (GET) y
			 * el contenido de la misma (Archivo plano XML)
			 */
			def connection = url.openConnection()
			connection.setRequestMethod("GET" )
			connection.setRequestProperty("Content-Type" ,"text/xml" )
			
			def miXml = new XmlSlurper().parseText(connection.content.text)
			lista = procesarXmlRespuestaSpring(miXml, idComentario)

			return lista
		 }
		catch(Exception)
		{
			def miAlerta = "Ha ocurrido un error en el servidor, intente luego."
			render(view:"perfil",model:[email:session.usuario.email, usuario:session.usuario.nickname, alerta:miAlerta])
		}
	}
	
	/**
	* Metodo encargado de listar todas las respuertas por id comentario registrada en
	* el servicio spring y retorna la lista de respuesta
	*/
	def procesarXmlRespuestaSpring(def xml, String idComentarioPrincipial)
	{
		/**
		* recorro toda el xml de las respuesta registradas en el sistema spring
		*/
		for (int i=0;i< xml.comentario.size();i++)
		{
			ComentarioCliente comentarioRespuesta = new ComentarioCliente()
			comentarioRespuesta.fecha = xml.comentario[i].fecha_creacion.text()
			comentarioRespuesta.mensaje = xml.comentario[i].mensaje.text()
			comentarioRespuesta.idComentarioComentado = idComentarioPrincipial
			
			listaComentado.add(comentarioRespuesta)
		}
		
		return listaComentado
	}
	
} // fin Comentario Controller









































































































































































