package miaraguaney

import static org.junit.Assert.*

import grails.test.mixin.*
import grails.test.mixin.support.*
import org.junit.*

/**
 * See the API for {@link grails.test.mixin.support.GrailsUnitTestMixin} for usage instructions
 */
@TestMixin(GrailsUnitTestMixin)
class EtiquetaControllerTests extends grails.test.ControllerUnitTestCase {

	
	@Test 
	void consultarTodasLasEtiquetas (){
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
	   
	   assertEquals("200", connection.responseCode.toString()) 
	  
	   
	}
}
