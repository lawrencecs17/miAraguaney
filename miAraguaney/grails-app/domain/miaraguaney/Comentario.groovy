package miaraguaney

import java.util.Date;

import miaraguaney.Comentario;
import miaraguaney.Etiqueta;
import miaraguaney.Usuario;

/**
*
* @author Ricardo Portela 
* Clase destinada a gestionar todas las funcionalidades de Comentario
*
*/

class Comentario {

	
	static hasMany = [tag:Etiqueta,comentado:Comentario]
	
	String mensaje
	String fecha
	String autor
	boolean principal
	List<String> adjuntos = new ArrayList<String>()
	
	static belongsTo = [comentado:Comentario]
	
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
