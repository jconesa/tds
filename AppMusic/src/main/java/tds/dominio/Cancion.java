package tds.dominio;

public class Cancion {

	private String titulo;
	private String interprete;
	private String genero;
	private String rutaFichero;
	private int numReproducciones;
	
	private Cancion(String titulo, 	String interprete, String genero, String rutaFichero) {
		this.titulo = titulo;
		this.interprete = interprete;
		this.genero = genero;
		this.rutaFichero = rutaFichero;
		this.numReproducciones = 0;
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

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}
	
	
	
}
