package miaraguaney

import org.apache.commons.logging.*
import groovyx.net.http.HTTPBuilder
import static groovyx.net.http.Method.*
import static groovyx.net.http.ContentType.*
import grails.converters.*
import java.util.Date

class EtiquetaController {

	private static Log log = LogFactory.getLog("Logs."+UsuarioController.class.getName())
	
	    def index() { 
			
			log.info("Ejemplo de este log")
			render (view:'consultarTodos')
		}
	
		/**
		* Invocacion al servicio de consultar todas las Etiquetas
		* registrados en el sistema
		*/
	   def consultarTodasLasEtiquetas = {
		   
	   try
	   {
		   /**
			* Se ubica la URL del servicio que lista a todas las Etiquetas
			*/
		   def url = new URL("http://localhost:8080/miOrquidea/etiqueta/listarTodos" )
		   def listaEtiqueta
		   
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
			   listaEtiqueta = procesarXmlEtiqueta(miXml)
		   }
		   else{
			   render "Se ha generado un error:"
			   render connection.responseCode
			   render connection.responseMessage
		   }
		   
		   render (view:"consultarEtiquetas", model:[etiquetas:listaEtiqueta, usuario:session.nickname])   
	   }
	   catch(Exception)
	   {
		   def miAlerta = "Ha ocurrido un error en el servidor, intente luego."
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
