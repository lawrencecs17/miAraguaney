package miaraguaney

import java.util.Date;





class Token {


	String fechaCreacion
	boolean validez
	String ip
	String host
	static constraints = {
	}
	
	static mapping = {
		id index:true
	}
	
	
	/**
	* Metodo para consumir servicio token vigente
	* para verificar si el token se vencio o no
	*/
   public static boolean tokenVigente (def email)
   {
   
	   def serviceResponse = "No hay respuesta"
	   
	   /**
	   * Se ubica la URL del servicio que lista a todos los usuarios
	   */
	  
	   def url = new URL("http://localhost:8080/miOrquidea/token/verificarValidezToken?email=$email")
	   def connection = url.openConnection()
	   connection.setRequestMethod("GET")
	   connection.setDoOutput(true)
	   def miXml
	   if(connection.responseCode == 200)
	   {
						   
		   miXml = new XmlSlurper().parseText(connection.content.text)
		   serviceResponse = miXml.mensaje
		   
		   if(serviceResponse == "")
		   {
			   serviceResponse = miXml.valido
			   return analizarRespuesta (miXml.valido)
		   }
	   }
	   return false
	}
   
   /**
	* Metodo para analizar la respuesta del servicio
	* si el token es valido o no
	* @param respuesta
	* @return
	*/
   public static boolean analizarRespuesta (def respuesta)
   {
	   if (respuesta== "Si")
	   {
		   return true
	   }
	   else
	   {
		   return false
	   }
   }   
}
