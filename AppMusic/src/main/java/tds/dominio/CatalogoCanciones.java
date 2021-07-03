package tds.dominio;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import tds.dao.DAOException;
import tds.dao.FactoriaDAO;

public class CatalogoCanciones {
	private static CatalogoCanciones unicaInstancia;
	private FactoriaDAO factoria;

	private HashMap<Integer, Cancion> asistentesPorID;
	private HashMap<String, Cancion> asistentesPorTitulo;

	public static CatalogoCanciones getUnicaInstancia() {
		if (unicaInstancia == null) unicaInstancia = new CatalogoCanciones();
		return unicaInstancia;
	}

	private CatalogoCanciones (){
		asistentesPorID = new HashMap<Integer, Cancion>();
		asistentesPorTitulo = new HashMap<String, Cancion>();
		
		try {
			factoria = FactoriaDAO.getInstancia();
			
			List<Cancion> listaAsistentes = factoria.getCancionDAO().getAll();
			for (Cancion cancion : listaAsistentes) {
				asistentesPorID.put(cancion.getId(), cancion);
				asistentesPorTitulo.put(cancion.getTitulo(), cancion);
			}
		} catch (DAOException eDAO) {
			   eDAO.printStackTrace();
		}
	}
	
	public List<Cancion> getCanciones() throws DAOException {
		return new LinkedList<Cancion>(asistentesPorTitulo.values());
	}
	

	public Cancion getCancion(String titulo) {
		return asistentesPorTitulo.get(titulo);
	}

	public Cancion getCancion(int id) {
		return asistentesPorID.get(id);
	}
	
	public void addCancion(Cancion cancion) {
		asistentesPorID.put(cancion.getId(), cancion);
		asistentesPorTitulo.put(cancion.getTitulo(), cancion);
	}
	
	public void removeCancion(Cancion cancion) {
		asistentesPorID.remove(cancion.getId());
		asistentesPorTitulo.remove(cancion.getTitulo());
	}

	
}
