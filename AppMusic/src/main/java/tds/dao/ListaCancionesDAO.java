package tds.dao;

import tds.dominio.ListaCanciones;

public interface ListaCancionesDAO {
	void addLista(ListaCanciones listaCanciones);
	void borrarLista(ListaCanciones listaCanciones);
	void modificarLista(ListaCanciones listaCanciones);
	ListaCanciones recuperarListaCanciones(int id);
	
}
