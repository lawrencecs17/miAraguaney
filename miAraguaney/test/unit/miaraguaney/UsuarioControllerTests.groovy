package miaraguaney

import static org.junit.Assert.*

import grails.converters.XML
import grails.test.mixin.*
import grails.test.mixin.support.*
import groovy.xml.MarkupBuilder
import org.junit.*

/**
 * See the API for {@link grails.test.mixin.support.GrailsUnitTestMixin} for usage instructions
 */
@TestMixin(GrailsUnitTestMixin)
class UsuarioControllerTests extends grails.test.ControllerUnitTestCase {

	/**
	 * Prueba que indica si el servidor responde
	 */
	@Test
    void testRespuestaServidor()
	{
		
	  def miXml = iniciarSesion()	   
	  /**
	   * Debe responder
	   */
	  assertNotNull(miXml)	  
	  cerrarSesion()	
	}
	
	@Test
	void testIniciarSesion()
	{
	  cerrarSesion()
	  def miXml = iniciarSesion()
	  def serviceResponse = miXml.mensaje.toString()
	  assertEquals("",serviceResponse.toString())
	  cerrarSesion()
		
	}
	
	@Test
	void testCerrarSesion()
	{
		iniciarSesion()
		def miXml = cerrarSesion()
		def serviceResponse = miXml.mensaje.toString()
		assertEquals("",serviceResponse.toString())
		cerrarSesion()
		
	}
	
	@Test
	void testDestruirSesion()
	{
		mockSession.usuario = "usuario"
		mockSession.nickname = "nickname"
		mockSession.email = "email"
		mockSession.password = "password"
		
		destruirSesion()
		
		assertNull(mockSession.usuario)
		assertNull(mockSession.nickname)
		assertNull(mockSession.email)
		assertNull(mockSession.password)
		
	}
	
	@Test
	void testActivarCuenta()
	{
		iniciarSesion()
		def serviceResponse = activarUsuario()
		def yoEspero = "El usuario tiene su cuenta activa en este momento"
		assertEquals(serviceResponse.toString(),yoEspero)
		cerrarSesion()
	}
	
	@Test
	void testDesactivarCuenta()
	{
		iniciarSesion()
		def serviceResponse = eliminarUsuario()
		def yoEspero ="El usuario ha desactivado su cuenta exitosamente"
		assertEquals(serviceResponse.toString(),yoEspero)
		activarUsuario()
		cerrarSesion()
	}
	
	
	
	def eliminarUsuario ()
	 {		
		 	String email = "law@gmail.com"
			String password = "1234"
			def serviceResponse = "Datos incorrectos"
			mockSession.usuario = "usuario"
			mockSession.nickname = "nickname"
			mockSession.email = "email"
			mockSession.password = "password"
			
			if(Token.tokenVigente(email))
			{
				def url = new URL("http://localhost:8080/miOrquidea/usuario/eliminarUsuario?email=$email&password=$password" )
				def connection = url.openConnection()
				connection.setRequestMethod("DELETE")
				connection.setDoOutput(true)
				connection.connect()				
				def miXml				
					if(connection.responseCode == 200)
					{
										
						miXml = new XmlSlurper().parseText(connection.content.text)
						serviceResponse = miXml.mensaje
						
						if(serviceResponse == "")
						{
							serviceResponse = "El usuario ha desactivado su cuenta exitosamente"
						}						
					}
			}
			else
			{
				destruirSesion()
			}
			return serviceResponse		
		}
	
	def activarUsuario () 
	{			
			def serviceResponse = ""
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
			mockParams.email = "law@gmail.com"
			mockParams.password = "1234"
		   /**
			* Se construye un XML
			*/
		   def gXml = new StringWriter()
		   def xml = new MarkupBuilder(gXml)
		   
		   xml.usuario() {
			   email(mockParams.email)
			   password(mockParams.password)
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
				
			if(connection.responseCode == 200)
			{
				serviceResponse = "El usuario tiene su cuenta activa en este momento"
			}
			else
			{
				serviceResponse = "No aplica proceso de activacion para este usuario"
			}
			
			return serviceResponse		
	}
	
	def destruirSesion()
	{
		mockSession.removeAttribute("usuario")
		mockSession.removeAttribute("nickname")
		mockSession.removeAttribute("email")
		mockSession.removeAttribute("password")
	}
	def iniciarSesion()
	{
			/**
			* Se inicializa la variable que recibe la respuesta del servidor
			*/
		   def serviceResponse = ""
		   /**
			* Se establece la URL de la ubicacion
			* del servicio
			*/
		   def url = new URL("http://localhost:8080/miOrquidea/token/iniciarSesion")
		   /**
			* Se simulan los datos de entrada
			*/
		   mockParams.email = "law@gmail.com"
		   mockParams.password = "1234"
		   /**
			* Se construye un XML
			*/
		   def gXml = new StringWriter()
		   def xml = new MarkupBuilder(gXml)
		   
		   xml.usuario() {
			   email(mockParams.email)
			   password(mockParams.password)
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
		  println miXml
		  return miXml
	}
	
	def cerrarSesion () 
	{		
			def serviceResponse = ""
			/**
			 * Se establece la URL de la ubicacion
			 * del servicio
			 */
			def url = new URL("http://localhost:8080/miOrquidea/token/anularToken" )
			/**
			* Se simulan los datos de entrada
			*/
		    mockParams.email = "law@gmail.com"
		    mockParams.password = "1234"
			/**
			 * Se extraen los parametros y convierte a formato
			 * XML para luego ser enviada a la aplicacion miOrquidea
			 */
			def gXml = new StringWriter()
			def xml = new MarkupBuilder(gXml)
			
			xml.usuario() {
				email(mockParams.email)
				password(mockParams.password)
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
			
			return miXml
			
			
		}
	
}
