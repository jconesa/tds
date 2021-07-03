package tds.dominio;

import java.util.List;

import tds.controlador.Controlador;

import java.util.ArrayList;
import java.util.Collections;

public class ListaCanciones {
	private int id;
	private String nombre;
	private ArrayList<Cancion> listaCanciones;
	private Usuario usuario;
	
	
	public ListaCanciones() {
		this.id = 0;
		listaCanciones = new ArrayList<Cancion>();
	}
	public ListaCanciones(String nombre) {
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
	
	public Usuario getUsuario() {
		return usuario;
	}
	
	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}
	
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	
	public boolean addCancion(Cancion cancion) {
		return listaCanciones.add(cancion);
	}
	
	public void removeCancion(Cancion cancion) {
		for(Cancion c : listaCanciones) {
			if(cancion.getTitulo().equals(c.getTitulo())) {
				listaCanciones.remove(c);
			}
		}
	}
	
	public List<Cancion> getListaCanciones() {
		return Collections.unmodifiableList(listaCanciones);
	}
	
	public boolean contiene(Cancion cancion) {
		for(Cancion c : listaCanciones) {
			if(cancion.getTitulo().equals(c.getTitulo()))
				return true;
		}
		return false;
	}
	public Cancion getCancion(int id) {
		return listaCanciones.get(id);
	}
	
	public Cancion getCancionSiguiente() {
		Cancion cancionActual = Controlador.getUnicaInstancia().getCancionActual();
		Cancion cancionSiguiente = null;
		for(int i = 0; i < listaCanciones.size(); i++) {
			if(listaCanciones.get(i).getId() == cancionActual.getId()) {
				if(i + 1 == listaCanciones.size())
					cancionSiguiente = listaCanciones.get(0);
				cancionSiguiente = listaCanciones.get(i+1);
			}
		}
		return cancionSiguiente;
	}
	
}


