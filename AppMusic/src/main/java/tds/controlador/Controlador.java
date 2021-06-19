package tds.controlador;

import tds.dao.UsuarioDAO;
import tds.dao.CancionDAO;
import tds.dao.DAOException;
import tds.dao.FactoriaDAO;
import tds.dao.TDSCancionDAO;
import tds.dominio.Usuario;
import tds.dominio.Cancion;
import tds.dominio.CatalogoCanciones;
import tds.driver.FactoriaServicioPersistencia;
import tds.driver.ServicioPersistencia;
import tds.dominio.CatalogoUsuarios;

public final class Controlador {

	private Usuario usuarioActual;
	private static Controlador unicaInstancia;
	private FactoriaDAO factoria;

	private Controlador() {
		usuarioActual = null;
		try {
			factoria = FactoriaDAO.getInstancia();
		} catch (DAOException e) {
			e.printStackTrace();
		}
	}

	public static Controlador getUnicaInstancia() {
		if (unicaInstancia == null)
			unicaInstancia = new Controlador();
		return unicaInstancia;
	}

	public Usuario getUsuarioActual() {
		return usuarioActual;
	}

	public boolean esUsuarioRegistrado(String login) {
		return CatalogoUsuarios.getUnicaInstancia().getUsuario(login) != null;
	}

	public boolean loginUsuario(String nombre, String password) {
		Usuario usuario = CatalogoUsuarios.getUnicaInstancia().getUsuario(nombre);
		if (usuario != null && usuario.getPassword().equals(password)) {
			this.usuarioActual = usuario;
			return true;
		}
		return false;
	}

	public boolean registrarUsuario(String nombre, String apellidos, String email, String login, String password,
			String fechaNacimiento, String premium) {

		if (esUsuarioRegistrado(login))
			return false;
		Usuario usuario = new Usuario(nombre, apellidos, email, login, password, fechaNacimiento, premium);

		UsuarioDAO usuarioDAO = factoria
				.getUsuarioDAO(); /* Adaptador DAO para almacenar el nuevo Usuario en la BD */
		usuarioDAO.create(usuario);

		CatalogoUsuarios.getUnicaInstancia().addUsuario(usuario);
		return true;
	}
	
	/*public boolean registrarCancion(String titulo, String interprete, String genero, String rutaFichero, int numReproducciones, String url) {

		//Checkear si ya esta metida la canción
		Cancion cancion = new Cancion(titulo, interprete, genero, rutaFichero, numReproducciones, url);

		CancionDAO cancionDAO = factoria
				.getCancionDAO(); /* Adaptador DAO para almacenar el nuevo Usuario en la BD 
		cancionDAO.create(cancion);

		CatalogoCanciones.getUnicaInstancia().addCancion(cancion);
		return true;
	}*/
	
	public boolean registrarCancion(String titulo, String interprete, String genero, int numReproducciones, String url) {

		//Checkear si ya esta metida la canción
		Cancion cancion = new Cancion(titulo, interprete, genero, numReproducciones, url);

		TDSCancionDAO cancionDAO = factoria
				.getCancionDAO(); /* Adaptador DAO para almacenar el nuevo Usuario en la BD */
		cancionDAO.create(cancion);

		CatalogoCanciones.getUnicaInstancia().addCancion(cancion);
		return true;
	}
	public void registrarCancion2(String titulo, String interprete, String genero, int numReproducciones, String url) {

		System.out.println(titulo);
		System.out.println(genero);
		System.out.println(numReproducciones);
		System.out.println(url);
	}
	


	public boolean borrarUsuario(Usuario usuario) {
		if (!esUsuarioRegistrado(usuario.getLogin()))
			return false;

		UsuarioDAO usuarioDAO = factoria.getUsuarioDAO(); /* Adaptador DAO para borrar el Usuario de la BD */
		usuarioDAO.delete(usuario);

		CatalogoUsuarios.getUnicaInstancia().removeUsuario(usuario);
		return true;
	}
	
	public void hacerPremium() {
		UsuarioDAO usuarioDAO = factoria.getUsuarioDAO();
		usuarioDAO.updatePremium(usuarioActual);
		
	}

}
