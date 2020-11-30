package tds.dominio;

import java.util.List;
import java.util.ArrayList;
import java.util.Collections;

public class ListaCanciones {
	
	private String nombre;
	private ArrayList<Cancion> listaCanciones;
	
	private ListaCanciones(String nombre) {
		this.nombre = nombre;
		listaCanciones = new ArrayList<Cancion>();
	}
	
	public String getNombre() {
		return nombre;
	}
	
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	
	public boolean addCancion(Cancion cancion) {
		return listaCanciones.add(cancion);
	}
	
	public List<Cancion> getListaCanciones() {
		return Collections.unmodifiableList(listaCanciones);
	}
	
	public Cancion getCancion(int id) {
		return listaCanciones.get(id);
	}
	
}


