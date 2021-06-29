package tds.dao;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.StringTokenizer;

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
	private static final String USUARIO = "usuario";

	private static ServicioPersistencia servPersistencia;
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
		
		
	

	public void addLista(ListaCanciones listaCanciones) {
		Entidad eLista;
		boolean existe = true;
		try {
			eLista = servPersistencia.recuperarEntidad(listaCanciones.getId());
		} catch (NullPointerException e) {
			existe = false;
		}
		
		if(existe) return;
		// registrar primero los atributos que son objetos
		/*// registrar lineas de venta
		TDSCancionDAO adaptadorCancion = TDSCancionDAO.getUnicaInstancia();
		for (Cancion c : listaCanciones.getListaCanciones())
			adaptadorCancion.create(c);*/
		// Crear lista canciones
		eLista = new Entidad();
		eLista.setNombre(NOMBRE);
		eLista.setPropiedades(new ArrayList<Propiedad>(
				Arrays.asList(new Propiedad(USUARIO, String.valueOf(listaCanciones.getUsuario().getId())),
						new Propiedad(NOMBRE, String.valueOf(listaCanciones.getNombre())),
						new Propiedad(LISTA_CANCIONES, obtenerIdCanciones(listaCanciones.getListaCanciones())))));
		// registrar entidad lista
		eLista = servPersistencia.registrarEntidad(eLista);
		// asignar identificador unico
		// Se aprovecha el que genera el servicio de persistencia
		listaCanciones.setId(eLista.getId()); 
		
	}
	
	public void borrarLista(ListaCanciones listaCanciones) {
		// No se comprueban restricciones de integridad con Cliente
		Entidad eLista;
		TDSCancionDAO adaptadorCancion = TDSCancionDAO.getUnicaInstancia();

		for (Cancion c : listaCanciones.getListaCanciones()) {
			adaptadorCancion.delete(c);
		}
		eLista = servPersistencia.recuperarEntidad(listaCanciones.getId());
		servPersistencia.borrarEntidad(eLista);

	}
	
	public void modificarLista(ListaCanciones listaCanciones) {
		Entidad eLista;

		eLista = servPersistencia.recuperarEntidad(listaCanciones.getId());
		servPersistencia.eliminarPropiedadEntidad(eLista, USUARIO);
		servPersistencia.anadirPropiedadEntidad(eLista, USUARIO, String.valueOf(listaCanciones.getUsuario().getId()));
		servPersistencia.eliminarPropiedadEntidad(eLista, NOMBRE);
		servPersistencia.anadirPropiedadEntidad(eLista, NOMBRE, listaCanciones.getNombre());

		String canciones = obtenerIdCanciones(listaCanciones.getListaCanciones());
		servPersistencia.eliminarPropiedadEntidad(eLista, LISTA_CANCIONES);
		servPersistencia.anadirPropiedadEntidad(eLista, LISTA_CANCIONES, canciones);

	}
	
	public ListaCanciones recuperarListaCanciones(int id) {
		// Si la entidad estï¿½ en el pool la devuelve directamente
		if (PoolDAO.getUnicaInstancia().contiene(id))
			return (ListaCanciones) PoolDAO.getUnicaInstancia().getObjeto(id);

		// si no, la recupera de la base de datos
		// recuperar entidad
		Entidad eLista = servPersistencia.recuperarEntidad(id);

		String nombre = null;
		nombre = servPersistencia.recuperarPropiedadEntidad(eLista, NOMBRE);
		ListaCanciones lista = new ListaCanciones(nombre);
		lista.setId(id);

		// IMPORTANTE:añadir la venta al pool antes de llamar a otros
		// adaptadores
		PoolDAO.getUnicaInstancia().addObjeto(id, lista);

		// recuperar propiedades que son objetos llamando a adaptadores
		// cliente
		TDSUsuarioDAO adaptadorUsuario = TDSUsuarioDAO.getUnicaInstancia();
		int idUsuario = Integer.parseInt(servPersistencia.recuperarPropiedadEntidad(eLista, USUARIO));
		
		Usuario usuario  = adaptadorUsuario.recuperarCl(codigoCliente);
		lista.setUsuario(usuario);
		// Canciones
		List<Cancion> canciones = obtenerIdCanciones(servPersistencia.recuperarPropiedadEntidad(eLista, LISTA_CANCIONES));

		for (LineaVenta lv : lineasVenta)
			venta.addLineaVenta(lv);

		// devolver el objeto venta
		return venta;
	}
	
// -------------------Funciones auxiliares-----------------------------
	private String obtenerIdCanciones(List<Cancion> listaCanciones) {
		String canciones = "";
		for (Cancion c : listaCanciones) {
			canciones += c.getId() + " ";
		}
		return canciones.trim();

	}
	
	private List<Cancion> obtenerLineasVentaDesdeCodigos(String canciones) {

		List<Cancion> listaCanciones = new LinkedList<Cancion>();
		StringTokenizer strTok = new StringTokenizer(canciones, " ");
		TDSCancionDAO adaptadorCancion = TDSCancionDAO.getUnicaInstancia();
		while (strTok.hasMoreTokens()) {
			listaCanciones.add(adaptadorCancion.recuperarLineaVenta(Integer.valueOf((String) strTok.nextElement())));
		}
		return lineasVenta;
	}

}
