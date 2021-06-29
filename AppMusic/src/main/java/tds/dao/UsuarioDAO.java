package tds.dao;

import java.util.List;

import tds.dominio.Usuario;

public interface UsuarioDAO {
	
	void create(Usuario asistente);
	boolean delete(Usuario asistente);
	void updatePerfil(Usuario asistente);
	void updatePremium(Usuario asistente);
	void modificarUsuario(Usuario usuario);
	Usuario get(int id);
	List<Usuario> getAll();
	
}
