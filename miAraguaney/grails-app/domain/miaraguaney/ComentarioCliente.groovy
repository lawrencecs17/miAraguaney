package miaraguaney

import java.util.List;
import java.util.Date;
import miaraguaney.Comentario;
import miaraguaney.Etiqueta;
import miaraguaney.Usuario;

/**
*
* @author Ricardo Portela
* Clase destinada a gestionar todas las funcionalidades de ComentarioCliente 
* con respecto al servidor
*
*/
class ComentarioCliente {

	String idComentario
	String mensaje
	String fecha
	String autor
	String cantidadLike
	String cantidadDislike
	String cantidadComentados
	String calificacionLike
	String calificacionDislike
	boolean principal
	
	List<String> adjuntos = new ArrayList<String>()
	List<String> tag = new ArrayList<String>()
	List<ComentarioCliente> comentado = new ArrayList<ComentarioCliente>()
		
	/**
	* Restricciones básicas de la clase
	*/
	
    static constraints = {
		mensaje(blank:false)
    }
	
	static mapping = {
		sort fecha:   "asc"
	}
	/**
	* Persistencia almacenada en una base de datos MongoDB
	*/
   
   static mapWith = "mongo"
}
