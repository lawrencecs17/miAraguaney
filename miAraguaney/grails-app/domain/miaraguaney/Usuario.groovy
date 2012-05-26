package miaraguaney

/** 
 * Clase destinada a gestionar la data de cada usuario de manera
 * temporal, apoyandose en los servicios ofrecidos por miOrquidea app
 * 
 * @author Lawrence.
 *
 */

class Usuario {
	
	String nombre
	String apellido
	String nickname
	String password
	String email
	String fechaRegistro
	String biografia
	String pais
	boolean activo

    static constraints = {
    }
}
