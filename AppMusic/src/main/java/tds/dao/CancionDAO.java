package tds.dao;
import java.util.List;

import tds.dominio.Cancion;
import tds.dominio.Usuario;
public interface CancionDAO {
	void create(Cancion cancion);
	boolean delete(Cancion cancion);
	Cancion get(int id);
	List<Cancion> getAll();
	
}
