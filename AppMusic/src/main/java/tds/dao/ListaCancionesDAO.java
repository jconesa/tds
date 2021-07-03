package tds.dao;

import tds.dominio.ListaCanciones;

public interface ListaCancionesDAO {
	//void addCancion(Cancion cancion);
	void addLista(ListaCanciones listaCanciones);
	void borrarLista(ListaCanciones listaCanciones);
	void modificarLista(ListaCanciones listaCanciones);
	ListaCanciones recuperarListaCanciones(int id);
	
	//Entidad cancionToEntidad(Cancion cancion);
}
