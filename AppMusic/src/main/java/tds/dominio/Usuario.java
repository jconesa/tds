package tds.dominio;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import tds.controlador.Controlador;


public class Usuario {
	
	private int id;
	private String nombre;
	private String apellidos;
	private String email;
	private String login;
	private String password;
	private String fechaNacimiento;
	private String premium;
	private List<ListaCanciones> listasCanciones;
	private List<Cancion> listaRecientes;

	public Usuario(String nombre, String apellidos, String email, String login, String password,
			String fechaNacimiento, String premium) {
		this.id = 0;
		this.nombre = nombre;
		this.apellidos = apellidos;
		this.email = email;
		this.login = login;
		this.password = password;
		this.fechaNacimiento = fechaNacimiento;
		this.premium = premium;
		this.listasCanciones = new LinkedList<ListaCanciones>();
		this.listaRecientes = new LinkedList<Cancion>();
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

	public String getApellidos() {
		return apellidos;
	}

	public void setApellidos(String apellidos) {
		this.apellidos = apellidos;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getFechaNacimiento() {
		return fechaNacimiento;
	}

	public void setFechaNacimiento(String fechaNacimiento) {
		this.fechaNacimiento = fechaNacimiento;
	}
	
	public String getPremium() {
		return this.premium;
	}
	
	public void setPremium(String premium) {
		this.premium = premium;
	}
	public void realizarPago() {
		setPremium("true");
		Controlador.getUnicaInstancia().hacerPremium();
	}
	public double getDescuento() {
		Descuento descuento;
		double precio = Descuento.precio;
		Random rand = new Random();
		int maximo = 100;
		int aleatorio = rand.nextInt(maximo);
		
		if(aleatorio > 60 && aleatorio < 85) {
			descuento = new DescuentoAfortunado();
			precio = descuento.calcDescuento();
		} else if(aleatorio >= 85) {
			descuento = new DescuentoSuperAfortunado();
			precio = descuento.calcDescuento();
		}
		
		return precio;

	}
	public List<ListaCanciones> getListasCanciones() {
		return listasCanciones;
	}
	
	public List<Cancion> getListaRecientes() {
		return listaRecientes;
	}
	
	public ListaCanciones getListaCancionesTitulo(String titulo) {
		ListaCanciones playList = null;
		for(ListaCanciones lista : listasCanciones) {
			if(lista.getNombre().equals(titulo)) {
				playList = lista;
				break;
			}
		}
		return playList;
	}
	
	public void setListasCanciones(List<ListaCanciones> listas) {
		for(ListaCanciones lista : listas) {
			listasCanciones.add(lista);
		}
	}
	
	public void setListaRecientes(List<Cancion> recientes) {
		for(Cancion cancion : recientes)
			listaRecientes.add(cancion);
	}
	
	public void addListaCanciones(ListaCanciones lista) {
		listasCanciones.add(lista);
	}
	
	public void addCancionReciente(Cancion cancion) {
		if(listaRecientes.contains(cancion))
			return;
		if(listaRecientes.size() < 10) {
			listaRecientes.add(cancion);
		} else {
			listaRecientes.remove(0);
			listaRecientes.add(cancion);
		}
	}
	


}