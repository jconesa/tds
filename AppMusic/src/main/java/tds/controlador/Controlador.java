package tds.controlador;

import tds.dao.UsuarioDAO;

import java.util.Comparator;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import javax.swing.JTable;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfWriter;

import tds.dao.CancionDAO;
import tds.dao.DAOException;
import tds.dao.FactoriaDAO;
import tds.dao.ListaCancionesDAO;
import tds.dao.TDSCancionDAO;
import tds.dominio.Usuario;
import tds.dominio.Cancion;
import tds.dominio.CatalogoCanciones;
import tds.driver.FactoriaServicioPersistencia;
import tds.driver.ServicioPersistencia;
import umu.tds.componente.CargadorCanciones;
import umu.tds.componente.Canciones;
import umu.tds.componente.CancionesEvent;
import umu.tds.componente.CancionesListener;
import tds.dominio.CatalogoUsuarios;
import tds.dominio.ListaCanciones;
import tds.dominio.Repro;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;


public final class Controlador implements CancionesListener {
	private ListaCancionesDAO adaptadorLista;
	private UsuarioDAO adaptadorUsuario;
	private TDSCancionDAO adaptadorCancion;
	
	private Usuario usuarioActual;
	private static Controlador unicaInstancia;
	private FactoriaDAO factoria;
	private Repro repro = new Repro();
	private Cancion cancionActual;
	private ListaCanciones listaActual;
	private JTable tablaActual;
	
	private CargadorCanciones cargadorCanciones;
	

	private Controlador() {
		usuarioActual = null;
		cargadorCanciones = new CargadorCanciones();
		try {
			factoria = FactoriaDAO.getInstancia();
			adaptadorLista = factoria.getListaCancionesDAO();
			adaptadorUsuario = factoria.getUsuarioDAO();
			adaptadorCancion = factoria.getCancionDAO();
			
			
		} catch (DAOException e) {
			e.printStackTrace();
		}
		cargadorCanciones.addCargadorChangeListener((CancionesListener) this);
		/*String ruta = "xml/canciones.xml";
		File xml = new File(ruta);
		this.cargarCanciones(xml);*/
	}

	public static Controlador getUnicaInstancia() {
		if (unicaInstancia == null)
			unicaInstancia = new Controlador();
		return unicaInstancia;
	}

	public Usuario getUsuarioActual() {
		return usuarioActual;
	}
	
	public Cancion getCancionActual() {
		return cancionActual;
	}
	
	public ListaCanciones getListaActual() {
		return listaActual;
	}
	
	public void setListaActual(ListaCanciones lista) {
		this.listaActual = lista;
	}

	public boolean esUsuarioRegistrado(String login) {
		return CatalogoUsuarios.getUnicaInstancia().getUsuario(login) != null;
	}

	public boolean loginUsuario(String nombre, String password) {
		Usuario usuario = CatalogoUsuarios.getUnicaInstancia().getUsuario(nombre);
		if (usuario != null && usuario.getPassword().equals(password)) {
			this.usuarioActual = usuario;
			return true;
		}
		return false;
	}

	public boolean registrarUsuario(String nombre, String apellidos, String email, String login, String password,
			String fechaNacimiento, String premium) {

		if (esUsuarioRegistrado(login))
			return false;
		Usuario usuario = new Usuario(nombre, apellidos, email, login, password, fechaNacimiento, premium);

		UsuarioDAO usuarioDAO = factoria
				.getUsuarioDAO(); /* Adaptador DAO para almacenar el nuevo Usuario en la BD */
		usuarioDAO.create(usuario);

		CatalogoUsuarios.getUnicaInstancia().addUsuario(usuario);
		return true;
	}
	
	/*public boolean registrarCancion(String titulo, String interprete, String genero, String rutaFichero, int numReproducciones, String url) {

		//Checkear si ya esta metida la canción
		Cancion cancion = new Cancion(titulo, interprete, genero, rutaFichero, numReproducciones, url);

		CancionDAO cancionDAO = factoria
				.getCancionDAO(); /* Adaptador DAO para almacenar el nuevo Usuario en la BD 
		cancionDAO.create(cancion);

		CatalogoCanciones.getUnicaInstancia().addCancion(cancion);
		return true;
	}*/
	
	public boolean registrarCancion(String titulo, String interprete, String genero, int numReproducciones, String ruta) {

		//Checkear si ya esta metida la canción
		Cancion cancion = new Cancion(titulo, interprete, genero, ruta, numReproducciones);

		TDSCancionDAO cancionDAO = factoria
				.getCancionDAO(); /* Adaptador DAO para almacenar el nuevo Usuario en la BD */
		cancionDAO.create(cancion);

		CatalogoCanciones.getUnicaInstancia().addCancion(cancion);
		return true;
	}
	
	public JTable getTablaActual() {
		return tablaActual;
	}
	public void registrarListaCanciones() {
		//Usuario usuario = CatalogoUsuarios.getUnicaInstancia().getUsuario(login);
		//listaActual.setUsuario(usuarioActual);
		//listaActual.setNombre(nombreLista);
		System.out.println("------------------------------------------------");
		System.out.println(listaActual.getNombre());
		System.out.println("registrar lista canciones");
		adaptadorLista.addLista(listaActual);
		usuarioActual.addListaCanciones(listaActual);
		
		adaptadorUsuario.modificarUsuario(usuarioActual);
		for(ListaCanciones lista : Controlador.getUnicaInstancia().getUsuarioActual().getListasCanciones()) {
			System.out.println(lista.getNombre());
			for(Cancion cancion : lista.getListaCanciones()) {
				System.out.println(cancion.getTitulo());
			}
		}
	}
	
	public void crearListaCanciones() {
		listaActual = new ListaCanciones();
	}
	
	public void actualizarListaCanciones() {
		adaptadorLista.modificarLista(listaActual);
	}
	
	public void crearListaCanciones(String nombre) {
		listaActual = new ListaCanciones(nombre);
	}
	
	public void setTablaActual(JTable tabla) {
		this.tablaActual = tabla;
		if(tabla.getRowCount() > 0)
			tabla.setRowSelectionInterval(0, 0);
	}
	
	public void playSong(String titulo) {
		System.out.println("---------------------------------ROW---------------");
		System.out.println(tablaActual.getSelectedRow());
		if(tablaActual.getSelectedRow() != -1)
		{
			Cancion cancion = CatalogoCanciones.getUnicaInstancia().getCancion(titulo);
			cancionActual = cancion;
			cancionActual.aumentarReproducciones();
			adaptadorCancion.updateReproducciones(cancion);
			usuarioActual.addCancionReciente(cancion);
			adaptadorUsuario.updateRecientes(usuarioActual);
			
			System.out.println("LA CANCION ES");
			System.out.println(cancion.getTitulo());

			System.out.println(cancion.getRutaFichero());
			repro.playCancion(cancion.getRutaFichero());
		} else {
			tablaActual.setRowSelectionInterval(0, 0);
		}


	}
	
	public void stopSong() {

		repro.stopCancion();
		cancionActual = null;
	}
	
	/*public void nextSongPlaylist() throws DAOException {
		System.out.println(cancionActual.getId());
		Cancion cancionSiguiente = listaActual.getCancionSiguiente();
		repro.playCancion(cancionSiguiente.getRutaFichero());
		
		// PONER IF CON -1
		if(this.tablaActual != null) {
			int indiceFila = tablaActual.getSelectedRow();
			if(indiceFila + 1 <= tablaActual.getRowCount() - 1) {
				String titulo = tablaActual.getValueAt(indiceFila + 1, 0).toString();
				tablaActual.setRowSelectionInterval(indiceFila + 1, indiceFila + 1);
				this.playSong(titulo);
				
			}
		}

	}*/
	
	public void nextSong() {
		if(this.tablaActual != null) {
			int indiceFila = tablaActual.getSelectedRow();
			if(indiceFila + 1 <= tablaActual.getRowCount() - 1) {
				String titulo = tablaActual.getValueAt(indiceFila + 1, 0).toString();
				tablaActual.setRowSelectionInterval(indiceFila + 1, indiceFila + 1);
				this.playSong(titulo);
				
			}
		}

	}
	
	public void previousSong() {
		if(this.tablaActual != null) {
			int indiceFila = tablaActual.getSelectedRow();
			if(indiceFila - 1 >= 0) {
				String titulo = tablaActual.getValueAt(indiceFila - 1, 0).toString();
				tablaActual.setRowSelectionInterval(indiceFila - 1, indiceFila - 1);
				this.playSong(titulo);
				
			}
		}

	}
	

	public boolean borrarUsuario(Usuario usuario) {
		if (!esUsuarioRegistrado(usuario.getLogin()))
			return false;

		UsuarioDAO usuarioDAO = factoria.getUsuarioDAO(); /* Adaptador DAO para borrar el Usuario de la BD */
		usuarioDAO.delete(usuario);

		CatalogoUsuarios.getUnicaInstancia().removeUsuario(usuario);
		return true;

	}
	
	public void hacerPremium() {
		// Darle funcion a usuario y asignarselo localmente
		usuarioActual.getDescuento();
		UsuarioDAO usuarioDAO = factoria.getUsuarioDAO();
		usuarioDAO.updatePremium(usuarioActual);
		
	}
	
	public void exportarPDF() throws FileNotFoundException, DocumentException  {
        String path = "C:/recursos/Listas.pdf";
        File f = new File(path);
        if (f.exists()) f.delete();
        FileOutputStream file = new FileOutputStream(path);
        Document doc = new Document();
        PdfWriter.getInstance(doc, file);
        doc.open();
        doc.newPage();
        doc.add(new Phrase("Usuario: ".concat(usuarioActual.getLogin().concat("\n\n")),FontFactory.getFont(FontFactory.COURIER,25,Font.BOLD, new BaseColor(0,0,0))));
        for(ListaCanciones l: usuarioActual.getListasCanciones()) {
            doc.add(new Phrase("Playlist: ".concat(l.getNombre().concat("\n")),FontFactory.getFont(FontFactory.COURIER,25,Font.BOLD, new BaseColor(0,0,0))));

            for(Cancion c: l.getListaCanciones()) {
                doc.add(new Phrase("\tNombre: ".concat(c.getTitulo().concat("\n")),FontFactory.getFont(FontFactory.COURIER,25,Font.BOLD, new BaseColor(0,0,0))));
                doc.add(new Phrase("\tAutor: ".concat(c.getInterprete().concat("\n")),FontFactory.getFont(FontFactory.COURIER,25,Font.BOLD, new BaseColor(0,0,0))));

            }
            doc.add(new Phrase("\n",FontFactory.getFont(FontFactory.COURIER,25,Font.BOLD, new BaseColor(0,0,0))));
        }
        doc.close();
    }
	
	public List<Cancion> getCancionesTop(){
		List<Cancion> cancionesCatalogo = null;
		try {
			cancionesCatalogo = CatalogoCanciones.getUnicaInstancia().getCanciones();
		} catch (DAOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		List<Cancion> cancionesTop = cancionesCatalogo.stream()
					.sorted(Comparator.comparingInt(Cancion::getNumReproducciones).reversed()).limit(10).collect(Collectors.toList());
		if(cancionesTop == null)
			System.out.println("canciones top es nulo");
		return cancionesTop;
	}
	
	@Override
	public void enteradoCambioFichero(CancionesEvent evento) {
		System.out.println("Importando");
		Canciones canciones = evento.getcancionesNuevas();
		for(umu.tds.componente.Cancion cancion : canciones.getCancion()) {
			String ruta = "C:/recursos/songs";
			Path rutaFichero = Paths.get(ruta + "/" + cancion.getEstilo() + "/" + cancion.getInterprete() + "-" + cancion.getTitulo() + ".mp3");
			System.out.println("--------------------------------------");
			System.out.println(rutaFichero);
			if(!rutaFichero.toFile().exists()) {
				File directorioGenero = new File(ruta + "/" + cancion.getEstilo());
				if(!directorioGenero.exists()) {
					directorioGenero.mkdirs();
				}
				
				InputStream inputStream;
				try {
					inputStream = new URL(cancion.getURL()).openStream();
					Files.copy(inputStream, rutaFichero);

				} catch (MalformedURLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}
			//String rutaUTF = rutaFichero.toString().replaceAll(" ", "%20");
			Controlador.getUnicaInstancia().registrarCancion(cancion.getTitulo(), cancion.getInterprete(), cancion.getEstilo(), 0, rutaFichero.toString());
		}
		
	}
	
	public void cargarCanciones(File ruta) {
		cargadorCanciones.setArchivoCanciones(ruta);
	}

	
	
	
	
	
}
