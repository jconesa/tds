package tds.dao;

import java.util.List;

import tds.dominio.Cancion;
import tds.dominio.ListaCanciones;

public interface ListaCancionesDAO {
	void create(Cancion cancion);
	boolean delete(Cancion cancion);
	Cancion get(int id);
	List<Cancion> getAll();
	void addLista(ListaCanciones listaCanciones);
	
	//Entidad cancionToEntidad(Cancion cancion);
}
