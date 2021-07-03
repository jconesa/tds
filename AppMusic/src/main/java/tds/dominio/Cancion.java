package tds.dominio;

public class Cancion {
	
	private int id;
	private String titulo;
	private String interprete;
	private String genero;
	private String rutaFichero;
	private int numReproducciones;
	
	public Cancion(String titulo, String interprete, String genero, String rutaFichero, int numReproducciones) {
		this.id = 0;
		this.titulo = titulo;
		this.interprete = interprete;
		this.genero = genero;
		this.rutaFichero = rutaFichero;
		this.numReproducciones = numReproducciones;
	}
	
	public Cancion(String titulo, String interprete, String genero, int numReproducciones) {
		this.id = 0;
		this.titulo = titulo;
		this.interprete = interprete;
		this.genero = genero;
		this.numReproducciones = numReproducciones;
	}
	
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	public String getTitulo() {
		return titulo;
	}

	public String getInterprete() {
		return interprete;
	}

	public void setInterprete(String interprete) {
		this.interprete = interprete;
	}

	public String getGenero() {
		return genero;
	}

	public void setGenero(String genero) {
		this.genero = genero;
	}

	public String getRutaFichero() {
		return rutaFichero;
	}

	public void setRutaFichero(String rutaFichero) {
		this.rutaFichero = rutaFichero;
	}

	public int getNumReproducciones() {
		return numReproducciones;
	}

	public void setNumReproducciones(int numReproducciones) {
		this.numReproducciones = numReproducciones;
	}
	
	public void aumentarReproducciones() {
		this.numReproducciones++;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

}
