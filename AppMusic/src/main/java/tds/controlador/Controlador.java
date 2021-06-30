package tds.controlador;

import tds.dao.UsuarioDAO;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import tds.dao.CancionDAO;
import tds.dao.DAOException;
import tds.dao.FactoriaDAO;
import tds.dao.ListaCancionesDAO;
import tds.dao.TDSCancionDAO;
import tds.dominio.Usuario;
import tds.dominio.Cancion;
import tds.dominio.CatalogoCanciones;
import tds.driver.FactoriaServicioPersistencia;
import tds.driver.ServicioPersistencia;
import tds.dominio.CatalogoUsuarios;
import tds.dominio.ListaCanciones;
import tds.dominio.Repro;
import tds.dominio.Reproductor;

public final class Controlador {

	private ListaCancionesDAO adaptadorLista;
	private UsuarioDAO adaptadorUsuario;
	
	private Usuario usuarioActual;
	private static Controlador unicaInstancia;
	private FactoriaDAO factoria;
	private Repro repro = new Repro();
	private Cancion cancionActual;
	private ListaCanciones listaActual;
	

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
	
	public ListaCanciones getListaActual() {
		return listaActual;
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
	
	public void registrarListaCanciones() {
		//Usuario usuario = CatalogoUsuarios.getUnicaInstancia().getUsuario(login);
		//listaActual.setUsuario(usuarioActual);
		//listaActual.setNombre(nombreLista);
		adaptadorLista.addLista(listaActual);
		usuarioActual.addListaCanciones(listaActual);
		
		adaptadorUsuario.modificarUsuario(usuarioActual);
		for(ListaCanciones lista : Controlador.getUnicaInstancia().getUsuarioActual().getListasCanciones()) {
			System.out.println(lista.getNombre());
		}
	}
	
	public void crearListaCanciones() {
		listaActual = new ListaCanciones();
	}
	
	public void crearListaCanciones(String nombre) {
		listaActual = new ListaCanciones(nombre);
	}
	
	public void playSong(String titulo) {
		Cancion cancion = CatalogoCanciones.getUnicaInstancia().getCancion(titulo);
		cancionActual = cancion;
		System.out.println("LA CANCION ES");
		System.out.println(cancion.getUrl());

		repro.playCancion(cancion.getUrl());
	}
	
	public void stopSong() {

		repro.stopCancion();
	}
	
	public void nextSong() throws DAOException {
		System.out.println(cancionActual.getId());
		List<Cancion> canciones = CatalogoCanciones.getUnicaInstancia().getCanciones();
		int indice = canciones.indexOf(cancionActual);
		if(canciones.size() <= indice+1) {
			indice = 0;
			this.playSong(canciones.get(indice).getTitulo());
			return;
		}
		this.playSong(canciones.get(indice+1).getTitulo());
		return;
		/*for(int i = 0; i < canciones.size(); i++)
		{
			if(canciones.get(i).equals(cancionActual)) {
				if(canciones.size() <= i+1)
				{
					i = 0;
					this.playSong(canciones.get(i).getTitulo());
					return;
				}
				this.playSong(canciones.get(i+1).getTitulo());
				repro.playCancion(canciones.get(i+1).getTitulo());
				return;
			}
		}*/
		//repro.playCancion(cancionSiguiente.getUrl());
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
		// Darle funcion a usuario y asignarselo localmente
		UsuarioDAO usuarioDAO = factoria.getUsuarioDAO();
		usuarioDAO.updatePremium(usuarioActual);
		
	}

}
