package tds.dao;

import java.util.ArrayList;
import java.util.Arrays;

import beans.Entidad;
import beans.Propiedad;
import tds.dominio.Cancion;
import tds.dominio.ListaCanciones;
import tds.dominio.Usuario;
import tds.driver.FactoriaServicioPersistencia;
import tds.driver.ServicioPersistencia;

public class TDSListaCancionesDAO {

	private static final String NOMBRE = "nombre";
	private static final String LISTA_CANCIONES = "listaCanciones";

	private ServicioPersistencia servPersistencia;
	private static TDSListaCancionesDAO unicaInstancia;
	
	public static TDSListaCancionesDAO getUnicaInstancia() { // patrón singleton
		if(unicaInstancia == null)
			return new TDSListaCancionesDAO();
		else
			return unicaInstancia;
	}
	
	private TDSListaCancionesDAO() {
		servPersistencia = FactoriaServicioPersistencia.getInstance().getServicioPersistencia();
	}
	

	
	public void addCancion(Cancion cancion) {
		Entidad eCancion = new Entidad();
		eCancion = servPersistencia.recuperarEntidad(cancion.getId());
		TDSListaCancionesDAO lcDao = TDSListaCancionesDAO.getUnicaInstancia();
		
		}
		
		
	}

