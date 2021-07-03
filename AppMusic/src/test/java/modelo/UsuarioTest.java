package modelo;

import static org.junit.Assert.*;
import java.util.LinkedList;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import tds.dominio.ListaCanciones;
import tds.dominio.Usuario;
import tds.dominio.Cancion;

public class UsuarioTest {

	Usuario u;
	ListaCanciones playList;
	Cancion cancion;
	List<ListaCanciones> listasCanciones;
	
	@Before
	public void inicializarValores() {
		u = new Usuario("Joaquin", "Conesa", "j@gmail.com", "JCM", "test", "22-10-1998", "false");
		cancion = new Cancion("Money", "Pink Floyd", "Rock", "C:\\recursos\\songs\\Rock\\Pink Floyd - The Run", 0);
		playList = new ListaCanciones("test");
		listasCanciones = new LinkedList<ListaCanciones>();
	}
	@Test
	public void testGetId() {
		u.setId(1);
		assertEquals("Resultado getId", 1, u.getId());
	}

	@Test
	public void testSetId() {
		u.setId(1);
		assertEquals("Resultado setId", 1, u.getId());
	}

	@Test
	public void testGetNombre() {
		assertEquals("Resultado testGetNombre", "Joaquin", u.getNombre());
	}

	@Test
	public void testSetNombre() {
		u.setNombre("Paco");
		assertEquals("Resultado testSetNombre", "Paco", u.getNombre());
	}

	@Test
	public void testGetApellidos() {
		assertEquals("Resultado testGetApellidos", "Conesa", u.getApellidos());
	}

	@Test
	public void testSetApellidos() {
		u.setApellidos("Garcia");
		assertEquals("Resultado testSetApellidos", "Garcia", u.getApellidos());
	}

	@Test
	public void testGetEmail() {
		assertEquals("Resultado testGetEmail", "j@gmail.com", u.getEmail());
	}

	@Test
	public void testSetEmail() {
		u.setEmail("pepe@gmail.com");
		assertEquals("Resultado testSetEmail", "pepe@gmail.com", u.getEmail());
	}

	@Test
	public void testGetLogin() {
		assertEquals("Resultado testGetLogin", "JCM", u.getLogin());
	}

	@Test
	public void testSetLogin() {
		u.setLogin("joaquin");
		assertEquals("Resultado testSetLogin", "joaquin", u.getLogin());
	}

	@Test
	public void testGetPassword() {
		assertEquals("Resultado testGetPassword", "test", u.getPassword());
	}

	@Test
	public void testSetPassword() {
		u.setPassword("contraseña");
		assertEquals("Resultado testSetPassword", "contraseña", u.getPassword());
	}

	@Test
	public void testGetFechaNacimiento() {
		assertEquals("Resultado testGetFechaNacimiento", "22-10-1998", u.getFechaNacimiento());
	}

	@Test
	public void testSetFechaNacimiento() {
		u.setFechaNacimiento("23-10-1998");
		assertEquals("Resultado testSetFechaNacimiento", "23-10-1998", u.getFechaNacimiento());
	}

	@Test
	public void testGetPremium() {
		assertEquals("Resultado testGetPremium", "false", u.getPremium());
	}

	@Test
	public void testSetPremium() {
		u.setPremium("true");
		assertEquals("Resultado testSetPremium", "true", u.getPremium());
	}

	@Test
	public void testGetListasCanciones() {
		playList.addCancion(cancion);
		u.addListaCanciones(playList);
		
		assertEquals("Resultado testGetListasCanciones", 1, u.getListasCanciones().size());          


	}

	@Test
	public void testGetListaRecientes() {
		u.addCancionReciente(cancion);
		assertEquals("Resultado testGetListaRecientes", true, u.getListaRecientes().contains(cancion));

	}

	@Test
	public void testGetListaCancionesTitulo() {
		u.addListaCanciones(playList);
		assertEquals("Resultado testGetListaCancionesTitulo", playList, u.getListaCancionesTitulo(playList.getNombre()));
	}
	
	@Test
	public void testSetListasCanciones() {
		listasCanciones.add(playList);
		u.addListaCanciones(playList);
		u.setListasCanciones(listasCanciones);
		assertEquals("Resultado testSetListasCanciones", listasCanciones.get(0), u.getListasCanciones().get(0));
	}

	@Test
	public void testSetListaRecientes() {
		List<Cancion> recientes = new LinkedList<Cancion>();
		recientes.add(cancion);
		u.setListaRecientes(recientes);
		assertEquals("Resultado testSetListaRecientes", recientes, u.getListaRecientes());
	}

	@Test
	public void testAddListaCanciones() {
		listasCanciones.add(playList);
		u.addListaCanciones(playList);
		assertEquals("Resultado testAddListaCanciones", listasCanciones, u.getListasCanciones());
	}

	@Test
	public void testAddCancionReciente() {
		List<Cancion> recientes = new LinkedList<Cancion>();
		recientes.add(cancion);
		u.addCancionReciente(cancion);
		assertEquals("Resultado testAddCancionReciente", recientes, u.getListaRecientes());
	}


}
