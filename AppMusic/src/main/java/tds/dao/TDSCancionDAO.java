package tds.dao;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import beans.Entidad;
import beans.Propiedad;
import tds.dominio.Cancion;
import tds.dominio.Usuario;
import tds.driver.FactoriaServicioPersistencia;
import tds.driver.ServicioPersistencia;

public class TDSCancionDAO {
	private static final String CANCION = "Cancion";
	
	private static final String TITULO = "titulo";
	private static final String INTERPRETE = "interprete";
	private static final String GENERO = "genero";
	private static final String RUTA_FICHERO = "ruta fichero";
	private static final String NUM_REPRODUCCIONES = "numero de reproducciones";
	private static final String URL = "url";

	private ServicioPersistencia servPersistencia;


	public TDSCancionDAO() {
		servPersistencia = FactoriaServicioPersistencia.getInstance().getServicioPersistencia();
	}
	
	private Cancion entidadToCancion(Entidad eCancion) {
	
		String titulo = servPersistencia.recuperarPropiedadEntidad(eCancion, TITULO);
		String interprete = servPersistencia.recuperarPropiedadEntidad(eCancion, INTERPRETE);
		String genero = servPersistencia.recuperarPropiedadEntidad(eCancion, GENERO);
		String rutaFichero = servPersistencia.recuperarPropiedadEntidad(eCancion, RUTA_FICHERO);
		String numReproducciones = servPersistencia.recuperarPropiedadEntidad(eCancion, NUM_REPRODUCCIONES);
		String url = servPersistencia.recuperarPropiedadEntidad(eCancion, URL);
	
	
		Cancion cancion = new Cancion(titulo, interprete, genero, rutaFichero, Integer.parseInt(numReproducciones), url);
		cancion.setId(eCancion.getId());
		
		
		return cancion;
	}
	
	private Entidad cancionToEntidad(Cancion cancion) {
		Entidad eCancion = new Entidad();
		eCancion.setNombre(CANCION);

		eCancion.setPropiedades(new ArrayList<Propiedad>(Arrays.asList(
				new Propiedad(TITULO, cancion.getTitulo()),
				new Propiedad(INTERPRETE, cancion.getInterprete()), 
				new Propiedad(GENERO, cancion.getGenero()),
				new Propiedad(RUTA_FICHERO, cancion.getRutaFichero()), 
				new Propiedad(NUM_REPRODUCCIONES, String.valueOf(cancion.getNumReproducciones())),
				new Propiedad(URL, cancion.getUrl()))));
		return eCancion;
	}
	
	public void create(Cancion cancion) {
		Entidad eCancion = this.cancionToEntidad(cancion);
		eCancion = servPersistencia.registrarEntidad(eCancion);
		cancion.setId(eCancion.getId());
	}

	public boolean delete(Cancion cancion) {
		Entidad eCancion;
		eCancion = servPersistencia.recuperarEntidad(cancion.getId());
		
		return servPersistencia.borrarEntidad(eCancion);
	}
	
	public Cancion get(int id) {
		Entidad eCancion = servPersistencia.recuperarEntidad(id);
		
		return entidadToCancion(eCancion);
	}
	
	public List<Cancion> getAll() {
		List<Entidad> entidades = servPersistencia.recuperarEntidades(CANCION);

		List<Cancion> canciones = new LinkedList<Cancion>();
		for (Entidad eCancion : entidades) {
			canciones.add(get(eCancion.getId()));
		}
		
		return canciones;
	}

}