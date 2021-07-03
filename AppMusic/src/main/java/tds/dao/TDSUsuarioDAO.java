package tds.dao;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.StringTokenizer;

import tds.driver.FactoriaServicioPersistencia;
import tds.driver.ServicioPersistencia;
import tds.dominio.Cancion;
import tds.dominio.ListaCanciones;
import tds.dominio.Usuario;
import beans.Entidad;
import beans.Propiedad;

/**
 * 
 * Clase que implementa el Adaptador DAO concreto de Usuario para el tipo H2.
 * 
 */
public final class TDSUsuarioDAO implements UsuarioDAO {

	private static final String USUARIO = "Usuario";
	
	private static final String NOMBRE = "nombre";
	private static final String APELLIDOS = "apellidos";
	private static final String EMAIL = "email";
	private static final String LOGIN = "login";
	private static final String PASSWORD = "password";
	private static final String FECHA_NACIMIENTO = "fechaNacimiento";
	private static final String PREMIUM = "premium";
	private static final String RECIENTES = "recientes";
	private static final String LISTA_CANCIONES = "listacanciones";
	private static ServicioPersistencia servPersistencia;
	private static TDSUsuarioDAO unicaInstancia = null;
	public static TDSUsuarioDAO getUnicaInstancia() { // patron singleton
		if (unicaInstancia == null)
			return new TDSUsuarioDAO();
		else
			return unicaInstancia;
	}
	public TDSUsuarioDAO() {
		servPersistencia = FactoriaServicioPersistencia.getInstance().getServicioPersistencia();
	}

	private Usuario entidadToUsuario(Entidad eUsuario) {

		
		String nombre = servPersistencia.recuperarPropiedadEntidad(eUsuario, NOMBRE);
		String apellidos = servPersistencia.recuperarPropiedadEntidad(eUsuario, APELLIDOS);
		String email = servPersistencia.recuperarPropiedadEntidad(eUsuario, EMAIL);
		String login = servPersistencia.recuperarPropiedadEntidad(eUsuario, LOGIN);
		String password = servPersistencia.recuperarPropiedadEntidad(eUsuario, PASSWORD);
		String fechaNacimiento = servPersistencia.recuperarPropiedadEntidad(eUsuario, FECHA_NACIMIENTO);
		String premium = servPersistencia.recuperarPropiedadEntidad(eUsuario, PREMIUM);


		Usuario usuario = new Usuario(nombre, apellidos, email, login, password, fechaNacimiento, premium);
		usuario.setId(eUsuario.getId());
		PoolDAO.getUnicaInstancia().addObjeto(usuario.getId(), usuario);
		
		if(servPersistencia.recuperarPropiedadEntidad(eUsuario, LISTA_CANCIONES) != null)
		{
			System.out.println("NO ES NULO");
			List<ListaCanciones> listas = obtenerListasDesdeCodigos(servPersistencia.recuperarPropiedadEntidad(eUsuario, LISTA_CANCIONES));
			usuario.setListasCanciones(listas);

		}
		if(servPersistencia.recuperarPropiedadEntidad(eUsuario, RECIENTES) != null)
		{
			List<Cancion> recientes = obtenerRecientesDesdeCodigos(servPersistencia.recuperarPropiedadEntidad(eUsuario, RECIENTES));
			usuario.setListaRecientes(recientes);

		}
		return usuario;

	}
	private Entidad usuarioToEntidad(Usuario usuario) {
		
		
		TDSListaCancionesDAO adaptadorLista = TDSListaCancionesDAO.getUnicaInstancia();
		for (ListaCanciones lista : usuario.getListasCanciones())
			adaptadorLista.addLista(lista);
		Entidad eUsuario = new Entidad();
		eUsuario.setNombre(USUARIO);

		eUsuario.setPropiedades(new ArrayList<Propiedad>(Arrays.asList(
				new Propiedad(NOMBRE, usuario.getNombre()),
				new Propiedad(APELLIDOS, usuario.getApellidos()), 
				new Propiedad(EMAIL, usuario.getEmail()),
				new Propiedad(LOGIN, usuario.getLogin()), 
				new Propiedad(PASSWORD, usuario.getPassword()),
				new Propiedad(FECHA_NACIMIENTO, usuario.getFechaNacimiento()))));
				new Propiedad(PREMIUM, usuario.getPremium());
				new Propiedad(RECIENTES, obtenerCodigosRecientes(usuario.getListaRecientes()));
				new Propiedad(LISTA_CANCIONES, obtenerCodigosListasCanciones(usuario.getListasCanciones()));
		return eUsuario;
	}

	public void create(Usuario usuario) {
		Entidad eUsuario; 
		boolean existe = true;
		try {
			eUsuario = servPersistencia.recuperarEntidad(usuario.getId());
		} catch (NullPointerException e) {
			existe = false;
		}
		
		if(existe) return;
		
		eUsuario = this.usuarioToEntidad(usuario);
		eUsuario = servPersistencia.registrarEntidad(eUsuario);
		usuario.setId(eUsuario.getId());

	}

	public boolean delete(Usuario usuario) {
		Entidad eUsuario;
		eUsuario = servPersistencia.recuperarEntidad(usuario.getId());
		
		return servPersistencia.borrarEntidad(eUsuario);
	}
	
	public void modificarUsuario(Usuario usuario) {
		
		Entidad eUsuario = servPersistencia.recuperarEntidad(usuario.getId());

		servPersistencia.eliminarPropiedadEntidad(eUsuario, NOMBRE);
		servPersistencia.anadirPropiedadEntidad(eUsuario, NOMBRE, usuario.getNombre());
		servPersistencia.eliminarPropiedadEntidad(eUsuario, APELLIDOS);
		servPersistencia.anadirPropiedadEntidad(eUsuario, APELLIDOS, usuario.getApellidos());
		servPersistencia.eliminarPropiedadEntidad(eUsuario, EMAIL);
		servPersistencia.anadirPropiedadEntidad(eUsuario, EMAIL, usuario.getEmail());
		servPersistencia.eliminarPropiedadEntidad(eUsuario, LOGIN);
		servPersistencia.anadirPropiedadEntidad(eUsuario, LOGIN, usuario.getLogin());
		servPersistencia.eliminarPropiedadEntidad(eUsuario, PASSWORD);
		servPersistencia.anadirPropiedadEntidad(eUsuario, PASSWORD, usuario.getPassword());
		servPersistencia.eliminarPropiedadEntidad(eUsuario, FECHA_NACIMIENTO);
		servPersistencia.anadirPropiedadEntidad(eUsuario, FECHA_NACIMIENTO, usuario.getFechaNacimiento());
		servPersistencia.eliminarPropiedadEntidad(eUsuario, PREMIUM);
		servPersistencia.anadirPropiedadEntidad(eUsuario, PREMIUM, usuario.getPremium());
		
		String listas = obtenerCodigosListasCanciones(usuario.getListasCanciones());
		servPersistencia.eliminarPropiedadEntidad(eUsuario, LISTA_CANCIONES);
		servPersistencia.anadirPropiedadEntidad(eUsuario, LISTA_CANCIONES, listas);
		
		String recientes = obtenerCodigosRecientes(usuario.getListaRecientes());
		servPersistencia.eliminarPropiedadEntidad(eUsuario, RECIENTES);
		servPersistencia.anadirPropiedadEntidad(eUsuario, RECIENTES, recientes);

	}

	/**
	 * Permite que un Usuario modifique su perfil: password y email
	 */
	public void updatePerfil(Usuario usuario) {
		Entidad eUsuario = servPersistencia.recuperarEntidad(usuario.getId());
		servPersistencia.eliminarPropiedadEntidad(eUsuario, PASSWORD);
		servPersistencia.anadirPropiedadEntidad(eUsuario, PASSWORD, usuario.getPassword());
		servPersistencia.eliminarPropiedadEntidad(eUsuario, EMAIL);
		servPersistencia.anadirPropiedadEntidad(eUsuario, EMAIL, usuario.getEmail());
	}
	
	public void updatePremium(Usuario usuario) {
		Entidad eUsuario = servPersistencia.recuperarEntidad(usuario.getId());
		servPersistencia.eliminarPropiedadEntidad(eUsuario, PREMIUM);
		servPersistencia.anadirPropiedadEntidad(eUsuario, PREMIUM, usuario.getPremium());

	}
	
	public void updateRecientes(Usuario usuario) {
		Entidad eUsuario;

		eUsuario = servPersistencia.recuperarEntidad(usuario.getId());

		String canciones = obtenerCodigosRecientes(usuario.getListaRecientes());
		servPersistencia.eliminarPropiedadEntidad(eUsuario, RECIENTES);
		servPersistencia.anadirPropiedadEntidad(eUsuario, RECIENTES, canciones);

	}
	

	public Usuario recuperar(int id) {
		 if (!PoolDAO.getUnicaInstancia().contiene(id)){
		 Entidad eUsuario = servPersistencia.recuperarEntidad(id);
		 return entidadToUsuario(eUsuario);
		 }
		 else
		 return (Usuario) PoolDAO.getUnicaInstancia().getObjeto(id);
		} 	

	public Usuario get(int id) {
		if ( !PoolDAO.getUnicaInstancia().contiene(id)){
			Entidad eUsuario = servPersistencia.recuperarEntidad(id);
			return entidadToUsuario(eUsuario);
		}
		else
			return (Usuario) PoolDAO.getUnicaInstancia().getObjeto(id);
	}

	public List<Usuario> getAll() {
		List<Entidad> entidades = servPersistencia.recuperarEntidades(USUARIO);

		List<Usuario> usuarios = new LinkedList<Usuario>();
		for (Entidad eUsuario : entidades) {
			usuarios.add(get(eUsuario.getId()));
		}
		
		return usuarios;
	}
	
	private String obtenerCodigosRecientes(List<Cancion> lista) {
		String aux = "";
		for (Cancion cancion : lista) {
			aux += cancion.getId() + " ";
		}
		return aux.trim();
	}
	
	private List<Cancion> obtenerRecientesDesdeCodigos(String lista) {

		List<Cancion> recientes= new LinkedList<Cancion>();
		StringTokenizer strTok = new StringTokenizer(lista, " ");
		TDSCancionDAO adaptadorCancion = TDSCancionDAO.getUnicaInstancia();
		while (strTok.hasMoreTokens()) {
			recientes.add(adaptadorCancion.get(Integer.valueOf((String) strTok.nextElement())));
		}
		return recientes;
	}
	private String obtenerCodigosListasCanciones(List<ListaCanciones> listas) {
		String aux = "";
		for (ListaCanciones lista : listas) {
			aux += lista.getId() + " ";
		}
		return aux.trim();
	}
	private List<ListaCanciones> obtenerListasDesdeCodigos(String listas) {

		List<ListaCanciones> listasCanciones = new LinkedList<ListaCanciones>();
		StringTokenizer strTok = new StringTokenizer(listas, " ");
		TDSListaCancionesDAO adaptadorLista = TDSListaCancionesDAO.getUnicaInstancia();
		while (strTok.hasMoreTokens()) {
			listasCanciones.add(adaptadorLista.recuperarListaCanciones(Integer.valueOf((String) strTok.nextElement())));
		}
		return listasCanciones;
	}

}
