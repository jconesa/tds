package tds.dominio;

import java.util.List;
import java.util.ArrayList;
import java.util.Collections;

public class ListaCanciones {
	private int id;
	private String nombre;
	private ArrayList<Cancion> listaCanciones;
	
	private ListaCanciones(String nombre) {
		this.id = 0;
		this.nombre = nombre;
		listaCanciones = new ArrayList<Cancion>();
	}
	
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
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


