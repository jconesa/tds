package tds.AppMusic;

import java.awt.EventQueue;

import tds.gui.LoginView;
import tds.componente.Cancion;
import tds.componente.Canciones;
import tds.componente.MapperCancionesXMLtoJava;
import tds.controlador.Controlador;

public class Lanzador {
	public static void main(final String[] args){
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					// CREO QUE SE DEBERIA PONER EN UN SITIO QUE SE INICIALIZA AL LOGUEARSE
					Canciones canciones = MapperCancionesXMLtoJava
							.cargarCanciones("xml/canciones.xml"); //Obtener fichero a cargar mediante JFileChooser en Swing
				
					for (Cancion c : canciones.getCancion()) {
						//tds.dominio.Cancion cancion = new tds.dominio.Cancion(c.getTitulo(), c.getInterprete(),c.getEstilo(), 0, c.getURL());
						/*
						System.out.println("Titulo: " + c.getTitulo());
						System.out.println(" Interprete : " + c.getInterprete());
						System.out.println(" Estilo : " + c.getEstilo());
						System.out.println(" URL : " + c.getURL());
						System.out.println("***** ***** *****");
						*/
						Controlador.getUnicaInstancia().registrarCancion(c.getTitulo(), c.getInterprete(), c.getEstilo(), 0, c.getURL());
						
					}
					LoginView ventana = new LoginView();
					ventana.mostrarVentana();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
}