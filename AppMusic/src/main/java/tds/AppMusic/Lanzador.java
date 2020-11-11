package tds.AppMusic;

import java.awt.EventQueue;

import tds.gui.LoginView;

public class Lanzador {
	public static void main(final String[] args){
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					LoginView ventana = new LoginView();
					ventana.mostrarVentana();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
}