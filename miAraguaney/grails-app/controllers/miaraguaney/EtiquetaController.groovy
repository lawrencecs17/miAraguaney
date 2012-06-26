package miaraguaney

import org.apache.commons.logging.*
import groovyx.net.http.HTTPBuilder
import static groovyx.net.http.Method.*
import static groovyx.net.http.ContentType.*
import grails.converters.*
import java.util.Date

class EtiquetaController {

	private static Log log = LogFactory.getLog("Logs2."+UsuarioController.class.getName())
	//static String bandera = "miOrquidea"
	static String bandera = "Spring"
	static String urlSpring =  "172.16.59.82"
	
	    def index() { 
			
			log.info("index Etiqueta")
			render (view:'consultarEtiquetas')
		}
	
		/**
		* Invocacion al servicio de consultar todas las Etiquetas
		* registrados en el sistema
		*/
	   def consultarTodasLasEtiquetas = {
		   
	   try
	   {
		   def listaEtiqueta
		   if (bandera.equals("miOrquidea"))
		   {
			   /**
				* Se ubica la URL del servicio que lista a todas las Etiquetas
				*/
			   def url = new URL("http://localhost:8080/miOrquidea/etiqueta/listarTodos" )
			   
			   /**
				* Se establece la conexion con el servicio
				* Se determina el tipo de peticion (GET) y
				* el contenido de la misma (Archivo plano XML)
				*/
			   def connection = url.openConnection()
			   connection.setRequestMethod("GET" )
			   connection.setRequestProperty("Content-Type" ,"application/xml" )
			   
			   if(connection.responseCode == 200)
			   {
				   def miXml = new XmlSlurper().parseText(connection.content.text)
				   listaEtiqueta = procesarXmlEtiqueta(miXml)
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
			   /**
			   * Se ubica la URL del servicio que lista a todas las Etiquetas
			   */
			  def url = new URL("http://" + urlSpring + ":8084/SPRINGDESESPERADO/rest/etiquetas" )
			  log.info ("" + bandera  + " : http://" + urlSpring + ":8084/SPRINGDESESPERADO/rest/etiquetas")
			  
			  /**
			   * Se establece la conexion con el servicio
			   * Se determina el tipo de peticion (GET) y
			   * el contenido de la misma (Archivo plano XML)
			   */
			  def connection = url.openConnection()
			  connection.setRequestMethod("GET" )
			  connection.setRequestProperty("Content-Type" ,"application/xml" )
			  
			  if(connection.responseCode == 200)
			  {
				  def miXml = new XmlSlurper().parseText(connection.content.text)
				  listaEtiqueta = procesarXmlEtiqueta(miXml)
			  }
			  else
			  {
				  render "Se ha generado un error:"
				  render connection.responseCode
				  render connection.responseMessage
			  }
		   }
		   
		   render (view:"consultarEtiquetas", model:[etiquetas:listaEtiqueta, usuario:session.nickname, servicio:bandera])   
	   }
	   catch(Exception)
	   {
		   def miAlerta = "Ha ocurrido un error en el servidor " + bandera + ", intente luego."
		   log.error (miAlerta)
		   render(view:"errorEtiqueta",model:[email:session.email,usuario:session.nickname,alerta:miAlerta])
	   }
	   }// fin consultarTodasLasEtiquetas
	   
	   /**
	   * Metodo encargado de procesar el archivo XML recibido del
	   * servicio miOrquidea app
	   * @param xml
	   * @return
	   */
	  def procesarXmlEtiqueta(def xml)
	  {
		  ArrayList<Etiqueta> listaEtiqueta = new ArrayList<Etiqueta>()
		  
		  for (int i=0;i< xml.etiqueta.size();i++)
		  {
			  Etiqueta etiqueta = new Etiqueta()
			  etiqueta.nombre = xml.etiqueta[i].nombre
			  listaEtiqueta.add(etiqueta.nombre)
		  }
		  return listaEtiqueta
	  }
  
}
