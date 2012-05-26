package miaraguaney

/**
*
* @author Ricardo Portela
* Clase destinada a gestionar todas las funcionalidades de Etiqueta
*
*/
 
class Etiqueta {
	
	String nombre

	/**
	* Restricciones b�sicas de la clase
	*/
	
    static constraints = {  
		nombre(nombre:true, blank:false,unique:true)
    }
	/**
	* Persistencia almacenada en una base de datos MongoDB
	*/
   
   static mapWith = "mongo"
}
