package tds.dao;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import tds.driver.FactoriaServicioPersistencia;
import tds.driver.ServicioPersistencia;
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
	
	private ServicioPersistencia servPersistencia;

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
		
		return usuario;
	}

	private Entidad usuarioToEntidad(Usuario usuario) {
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
		return eUsuario;
	}

	public void create(Usuario usuario) {
		Entidad eUsuario = this.usuarioToEntidad(usuario);
		eUsuario = servPersistencia.registrarEntidad(eUsuario);
		usuario.setId(eUsuario.getId());
	}

	public boolean delete(Usuario usuario) {
		Entidad eUsuario;
		eUsuario = servPersistencia.recuperarEntidad(usuario.getId());
		
		return servPersistencia.borrarEntidad(eUsuario);
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

	public Usuario get(int id) {
		Entidad eUsuario = servPersistencia.recuperarEntidad(id);
		
		return entidadToUsuario(eUsuario);
	}

	public List<Usuario> getAll() {
		List<Entidad> entidades = servPersistencia.recuperarEntidades(USUARIO);

		List<Usuario> usuarios = new LinkedList<Usuario>();
		for (Entidad eUsuario : entidades) {
			usuarios.add(get(eUsuario.getId()));
		}
		
		return usuarios;
	}

}
