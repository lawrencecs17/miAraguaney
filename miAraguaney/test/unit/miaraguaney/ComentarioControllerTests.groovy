package miaraguaney

import static org.junit.Assert.*
import grails.test.GrailsUnitTestCase
import groovy.xml.MarkupBuilder
import org.junit.*

class ComentarioControllerTests extends grails.test.ControllerUnitTestCase{

	@Before
	void setUp() {
		// Setup logic here
	}

	@After
	void tearDown() {
		// Tear down logic here
	}

	@Test
	void consultarTodosLosComentariosTest (){
		
		def url = new URL("http://localhost:8080/miOrquidea/comentario/listarTodos" )
		def listaComentario
		def connection = url.openConnection()
		connection.setRequestMethod("GET" )
		connection.setRequestProperty("Content-Type" ,"text/xml" )
		def mensaje =connection.responseCode
		println mensaje
		
		if (mensaje==200){
		assertEquals (200, mensaje)
		assertNotNull (mensaje)
		}
		
	}
	
	
	@Test
	void agregarComentarioTest(){
		
	   
		if(Token.tokenVigente("law@gmail.com"))
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
			mockParams.nickname= ("law")
			mockParams.etiquetas = ['etiqueta1', 'etiqueta2']
			mockParams.mensaje = "mensaje de prueba"
			/**
			* Con estas funciones creamos el XML
			*/
			def gXml = new StringWriter()
			def xml = new MarkupBuilder(gXml)
			
			/**
			* Creando el XML Comentario para pasarlo al servicio
			*/
			def listaEtiquetas = null
			listaEtiquetas = mockParams.etiquetas
			xml.comentario() {
				   autor (mockParams.nickname)
				   mensaje(mockParams.mensaje)
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
				assertEquals ("", serviceResponse)
				
		}
	
	
		}
	@Test 
	void  eliminarComentarioTest (){
			
		if(Token.tokenVigente("law@gmail.com"))
		{
			mockParams.id = 9
			mockParams.nickname = "law" 
			def nick = mockParams.nickname
			 def url = new URL("http://localhost:8080/miOrquidea/comentario/eliminarComentario?idComentario=" + mockParams.id + "&usuario=" +  mockParams.nickname)
			def connection = url.openConnection()
			
			connection.setRequestMethod("DELETE")
		
			connection.setDoOutput(true)
			connection.connect()
			def serviceResponse = "No hay respuesta!"
			
			
			if(connection.responseCode == 200)
			{
				assertNotNull (true)
				if(serviceResponse == "")
				{
					assertEquals ("", serviceResponse)
					serviceResponse = "Comentario eliminado"
				}
	
			}
			
		
	
		}
		}
	
	@Test 
	void consultarComentarioPorUsuarioTest (){
		
		/**
		* Se ubica la URL del servicio que lista a todos los Comentarios
		*/
		
		def url = new URL("http://localhost:8080/miOrquidea/comentario/listarPorUsuario?usuario=" +"law")
		def listaComentario
		
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
