package tds.gui;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;

import beans.Entidad;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import tds.controlador.Controlador;
import tds.dominio.Cancion;
import tds.dominio.CatalogoUsuarios;
import tds.dominio.Usuario;
import tds.driver.ServicioPersistencia;
import tds.dominio.Reproductor;

import java.awt.FlowLayout;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.LinkedList;
import java.awt.event.ActionEvent;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JSeparator;
import javax.swing.JTable;

import java.awt.Component;
import java.awt.Color;
import javax.swing.JTextPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JComboBox;

import java.io.File;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;


public class VentanaPrincipal {

	private JFrame frmVentanaPrincipal;
	private JTextField txtTtulo;
	private JTextField txtIntrprete;
	private JComboBox<String> generos;
	private Usuario usuarioActual = Controlador.getUnicaInstancia().getUsuarioActual();
	JTable tabla;
	DefaultTableModel modelo;

	public VentanaPrincipal() {
		initialize();
	}


	public void mostrarVentana() {
		frmVentanaPrincipal.setLocationRelativeTo(null);
		frmVentanaPrincipal.setVisible(true);
	}
	
	private JPanel crearPanel1() {
		JPanel panel1 = new JPanel();
		panel1.setBackground(Color.WHITE);
		frmVentanaPrincipal.getContentPane().add(panel1, BorderLayout.NORTH);
		panel1.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		

		JLabel lblNewLabel = new JLabel("Hola " + usuarioActual.getNombre());
		panel1.add(lblNewLabel);
		

		if(usuarioActual.getPremium() == "false")
		{
			JButton btnPremium = new JButton("Mejora tu cuenta");
			panel1.add(btnPremium);	
			addManejadorBotonPremium(btnPremium);
		}

		
		JButton btnLogout = new JButton("Logout");
		panel1.add(btnLogout);
		
		addManejadorBotonLogout(btnLogout);
		return panel1;
	}
	

	/*private JPanel crearPanelCancion(JPanel panelMedio, JPanel panelBotones) {
		JPanel panelCancion = new JPanel();
		panelCancion.setLayout(new BorderLayout(0, 0));
		ImageIcon icnPlay = new ImageIcon(getClass().getResource("/pause.png"));

		JButton btnPlay = new JButton("play", icnPlay);
		btnPlay.setMinimumSize(new Dimension(100,20));

		panelCancion.add(btnPlay, BorderLayout.NORTH);
		
		
	
		ImageIcon icnPause = new ImageIcon(getClass().getResource("/pause.png"));
		JButton btnPause = new JButton("pause", icnPause);
		btnPause.setMinimumSize(new Dimension(100,20));
		btnPause.setAlignmentX(Component.BOTTOM_ALIGNMENT);
		btnPause.setBackground(Color.WHITE);
		panelCancion.add(btnPause);
		
	
		
		ImageIcon icnAnterior= new ImageIcon(getClass().getResource("/pause.png"));

		JButton btnAnterior = new JButton("Anterior", icnAnterior);
		btnAnterior.setMinimumSize(new Dimension(100,20));

		panelCancion.add(btnAnterior, BorderLayout.WEST);
		
	
		
		ImageIcon icnSiguiente= new ImageIcon(getClass().getResource("/pause.png"));

		JButton btnSiguiente = new JButton("Siguiente", icnSiguiente);
		btnSiguiente.setMinimumSize(new Dimension(100,20));

		panelCancion.add(btnSiguiente, BorderLayout.EAST);
		return panelCancion;
	}
	*/
	private void crearTabla() {
		tabla = new JTable();
		tabla.setCellSelectionEnabled(false);
		tabla.setShowGrid(true);
		tabla.setShowVerticalLines(true);
		tabla.setGridColor(Color.gray); 
		modelo = new DefaultTableModel() {
			
			private static final long serialVersionUID = 1L;
			private String[] columnNames = {"Título", "Intérprete"};
			
			 public String getColumnName(int column) {
				 return columnNames[column];
			 }
			 
			public int getColumnCount() {return 2;}
			
			public boolean isCellEditable(int row, int col){
				return false;
			}
		 };
		tabla.setModel(modelo);
		 TableColumn columna = tabla.getColumn("Título");
		columna.setPreferredWidth(70);
		columna.setMinWidth(70);
		columna.setMaxWidth(70);
		columna = tabla.getColumn("Intérprete");
		columna.setPreferredWidth(250);
		columna.setMinWidth(250);
		columna.setMaxWidth(250);
		} 
	
	private JPanel crearPanelMedio() {
		JPanel panelMedio = new JPanel();
		frmVentanaPrincipal.getContentPane().add(panelMedio, BorderLayout.CENTER);
		panelMedio.setLayout(new BorderLayout(0, 0));
	
		
		return panelMedio;
	}
	private JPanel crearPanelBotones(JPanel panelMedio, JPanel panelExplorar) {
		JPanel panel_1 = new JPanel();
		panel_1.setBackground(Color.WHITE);
		frmVentanaPrincipal.getContentPane().add(panel_1, BorderLayout.WEST);
		panel_1.setLayout(new BoxLayout(panel_1, BoxLayout.Y_AXIS));
		
		ImageIcon icnExplorar = new ImageIcon(getClass().getResource("/lupa.png"));
		JButton btnExplorar = new JButton("Explorar", icnExplorar);
		btnExplorar.setMinimumSize(new Dimension(100, 20));
		btnExplorar.setAlignmentX(Component.CENTER_ALIGNMENT);
		btnExplorar.setBackground(Color.WHITE);
		panel_1.add(btnExplorar);
		
		
		JSeparator separator = new JSeparator();
		separator.setForeground(Color.WHITE);
		separator.setOrientation(SwingConstants.VERTICAL);
		separator.setBorder(null);
		separator.setMinimumSize(new Dimension(20, 20));
		separator.setMaximumSize(new Dimension(20, 20));
		panel_1.add(separator);
		
		ImageIcon icnNuevaLista = new ImageIcon(getClass().getResource("/plus.png"));
		JButton btnNuevaLista = new JButton("Nueva lista", icnNuevaLista);
		btnNuevaLista.setMinimumSize(new Dimension(100, 20));
		btnNuevaLista.setAlignmentX(Component.CENTER_ALIGNMENT);
		
		btnNuevaLista.setBackground(Color.WHITE);
		panel_1.add(btnNuevaLista);
		
		JSeparator separator2 = new JSeparator();
		separator2.setForeground(Color.WHITE);
		separator2.setOrientation(SwingConstants.VERTICAL);
		separator2.setBorder(null);
		separator2.setMinimumSize(new Dimension(20, 20));
		separator2.setMaximumSize(new Dimension(20, 20));
		panel_1.add(separator2);
		
		ImageIcon icnRecientes = new ImageIcon(getClass().getResource("/recientes.png"));
		JButton btnRecientes = new JButton("Recientes", icnRecientes);
		btnRecientes.setBackground(Color.WHITE);
		btnRecientes.setAlignmentX(Component.CENTER_ALIGNMENT);
		btnRecientes.setMinimumSize(new Dimension(100, 20));
		panel_1.add(btnRecientes);
		//contentPane.setBorder(new EmptyBorder(10, 10, 10, 10));
		
		JSeparator separator3 = new JSeparator();
		separator3.setForeground(Color.WHITE);
		separator3.setOrientation(SwingConstants.VERTICAL);
		separator3.setBorder(null);
		separator3.setMinimumSize(new Dimension(20, 20));
		separator3.setMaximumSize(new Dimension(20, 20));
		panel_1.add(separator3);

		
		ImageIcon icnListas = new ImageIcon(getClass().getResource("/listas.png"));
		JButton btnListas = new JButton("Mis listas", icnListas);
		btnListas.setBackground(Color.WHITE);
		btnListas.setMinimumSize(new Dimension(100, 20));
		btnListas.setAlignmentX(Component.CENTER_ALIGNMENT);
		panel_1.add(btnListas);
		
		addManejadorBotonExplorar(btnExplorar, panelMedio, panelExplorar);
				
		return panel_1;
		
		
	}
	
	public JPanel crearPanelExplorarNorte() {
		JPanel panelExplorarNorte = new JPanel();
		JPanel panelExplorarNorteN = new JPanel();
		JPanel panelExplorarNorteC = new JPanel();
		panelExplorarNorte.setLayout(new BorderLayout(0, 0));
		
		txtIntrprete = new JTextField();
		txtIntrprete.setText("Intérprete");
		panelExplorarNorteN.add(txtIntrprete);
		txtIntrprete.setColumns(8);
		
		txtTtulo = new JTextField();
		txtTtulo.setText("Título");
		panelExplorarNorteN.add(txtTtulo);
		txtTtulo.setColumns(8);
		

		generos = new JComboBox<String>();
		generos.setEditable(true);
		generos.addItem("Bolero");
		generos.addItem("Cantautor");
		generos.addItem("Clásica");
		generos.addItem("Flamenco");
		generos.addItem("Jazz");
		generos.addItem("Opera");
		generos.addItem("Pop");
		generos.addItem("Rock");
		generos.addItem("Romántica");
		panelExplorarNorteN.add(generos, BorderLayout.NORTH);
		
		JButton btnBuscar = new JButton("Buscar");
		btnBuscar.setMinimumSize(new Dimension(100, 20));
		btnBuscar.setBackground(Color.WHITE);
		panelExplorarNorteC.add(btnBuscar);
		
		JButton btnCancelar = new JButton("Cancelar");
		btnCancelar.setMinimumSize(new Dimension(100, 20));
		btnCancelar.setBackground(Color.WHITE);
		panelExplorarNorteC.add(btnCancelar);
		
		
		panelExplorarNorte.add(panelExplorarNorteN, BorderLayout.NORTH);
		panelExplorarNorte.add(panelExplorarNorteC, BorderLayout.CENTER);
		return panelExplorarNorte;
	}
	
	public JPanel crearPanelExploraSur() {
		
		JPanel panelSurPlay = new JPanel();
		panelSurPlay.setLayout(new BorderLayout(0, 0));
		
		ImageIcon icnPlay = new ImageIcon(getClass().getResource("/play.png"));

		JButton btnPlay = new JButton(icnPlay);
		btnPlay.setMinimumSize(new Dimension(100,20));
		btnPlay.setMaximumSize(new Dimension(100,20));

		panelSurPlay.add(btnPlay, BorderLayout.NORTH);
		
		
	
		ImageIcon icnPause = new ImageIcon(getClass().getResource("/pausa.png"));
		JButton btnPause = new JButton(icnPause);
		btnPause.setMinimumSize(new Dimension(100,20));
		btnPause.setAlignmentX(Component.BOTTOM_ALIGNMENT);
		panelSurPlay.add(btnPause);
		
	
		
		ImageIcon icnAnterior= new ImageIcon(getClass().getResource("/anterior.png"));

		JButton btnAnterior = new JButton(icnAnterior);
		btnAnterior.setMinimumSize(new Dimension(100,20));

		panelSurPlay.add(btnAnterior, BorderLayout.WEST);
		
	
		
		ImageIcon icnSiguiente= new ImageIcon(getClass().getResource("/siguiente.png"));

		JButton btnSiguiente = new JButton( icnSiguiente);
		btnSiguiente.setMinimumSize(new Dimension(100,20));

		panelSurPlay.add(btnSiguiente, BorderLayout.EAST);
		addManejadorBotonPlay(btnPlay);
		
		return panelSurPlay;
	}
	
	public LinkedList<Cancion> aplicarFiltro(LinkedList<Cancion> listaCanciones){
		LinkedList<Cancion> listaCancionesFiltrada = new LinkedList<Cancion>();
		
		// Interprete y titulo vacios
		if(txtIntrprete.getText().isEmpty() && txtTtulo.getText().isEmpty()) {
			String genero = generos.getSelectedItem().toString();
			for(Cancion cancion : listaCanciones) {
				if(genero.equals(cancion.getGenero())) {
					listaCancionesFiltrada.add(cancion);
				}
			}
		}
		// Con interprete y genero, titulo vacio
		if(!txtIntrprete.getText().isEmpty() && txtTtulo.getText().isEmpty()) {
			String genero = generos.getSelectedItem().toString();
			String interprete = txtIntrprete.getText();
			for(Cancion cancion : listaCanciones) {
				if(genero.equals(cancion.getGenero()) && interprete.equals(cancion.getInterprete())) {
					listaCancionesFiltrada.add(cancion);
				}
			}
		}
		
		// Sin interprete, con titulo y género
		if(txtIntrprete.getText().isEmpty() && !txtTtulo.getText().isEmpty()) {
			String genero = generos.getSelectedItem().toString();
			String titulo = txtTtulo.getText();
			for(Cancion cancion : listaCanciones) {
				if(genero.equals(cancion.getGenero()) && titulo.equals(cancion.getTitulo())) {
					listaCancionesFiltrada.add(cancion);
				}
			}
		}
		// Con todo
		if(!txtIntrprete.getText().isEmpty() && !txtTtulo.getText().isEmpty()) {
			String genero = generos.getSelectedItem().toString();
			String titulo = txtTtulo.getText();
			String interprete = txtIntrprete.getText();
			for(Cancion cancion : listaCanciones) {
				if(genero.equals(cancion.getGenero()) && interprete.equals(cancion.getInterprete()) && titulo.equals(cancion.getTitulo()) ) {
					listaCancionesFiltrada.add(cancion);
				}
			}
		}
		
		return listaCancionesFiltrada;
		
	}
/*	public JPanel crearPanelExplorarCentro() {
		JPanel panelExplorarCentro = new JPanel();
		ImageIcon icnPause = new ImageIcon(getClass().getResource("/pause.png"));
		JButton btnPause = new JButton("Pause", icnPause);
		btnPause.setBackground(Color.WHITE);
		btnPause.setAlignmentX(Component.CENTER_ALIGNMENT);
		btnPause.setMinimumSize(new Dimension(100, 20));
		//panelExplorarCentro.add(btnPause);
		JComboBox comboBox = new JComboBox();
		panelExplorarCentro.add(comboBox);
		
		
		return panelExplorarCentro;


	}*/
	
	public JPanel crearPanelExplorar(JPanel panelExplorarNorte, JPanel panelExplorarSur) {
		JPanel panelExplorar = new JPanel();
		panelExplorar.setLayout(new BorderLayout(0, 0));
		panelExplorar.add(panelExplorarNorte, BorderLayout.NORTH);
		panelExplorar.add(panelExplorarSur, BorderLayout.SOUTH);
		return panelExplorar;
	}
	
	
	
	public void addManejadorBotonExplorar(JButton btnExplorar, JPanel panelMedio, JPanel panelExplorar) {
		btnExplorar.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				panelMedio.removeAll();
				panelMedio.add(panelExplorar);
				panelMedio.revalidate();
				panelMedio.repaint();
				}
		});
	}
	
	public void addManejadorBotonPremium(JButton btnPremium) {
		btnPremium.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int respuesta = JOptionPane.showConfirmDialog(null, "Desea hacerse premium por x dinero?", "Hazte premium", 
						JOptionPane.YES_NO_OPTION);
				if(respuesta == JOptionPane.YES_OPTION)
					usuarioActual.realizarPago();
			}
			
		});
	}
	
	public void addManejadorBotonPlay(JButton btnPlay) {
		btnPlay.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// activar reproductor
				try {
				com.sun.javafx.application.PlatformImpl.startup(()->{});
				} catch(Exception ex) {
				ex.printStackTrace();
				System.out.println("Exception: " + ex.getMessage());
				 }

				// reproducir una canción

					//String fileName = cancion.getRutaFichero();
					File f = new File("D:\\cancionesTDS\\Coldplay-Viva_La_Vida.mp3");
					Media hit = new Media(f.toURI().toString());
					MediaPlayer mediaPlayer = new MediaPlayer(hit);
					mediaPlayer.play();
				}
		});
	}
	public void addManejadorBotonLogout(JButton btnLogout) {
		btnLogout.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				LoginView loginView = new LoginView();
				loginView.mostrarVentana();
				
				frmVentanaPrincipal.dispose();
			}
		});
	}
	

	
	public void initialize() {
		frmVentanaPrincipal = new JFrame();
		frmVentanaPrincipal.getContentPane().setBackground(Color.WHITE);
		frmVentanaPrincipal.setTitle("AppMusic - Ventana Principal");
		frmVentanaPrincipal.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		frmVentanaPrincipal.setSize(400, 400);
		frmVentanaPrincipal.setMinimumSize(new Dimension(400, 400));

		JPanel contentPane = (JPanel) frmVentanaPrincipal.getContentPane();
		frmVentanaPrincipal.getContentPane().setLayout(new BorderLayout(0, 0));
		
		JPanel panel1 = crearPanel1();
		
		JPanel panelMedio = crearPanelMedio();
		
		JPanel panelExplorarNorte = crearPanelExplorarNorte();
		JPanel panelExplorarSur = crearPanelExploraSur();
		//JPanel panelExplorarCentro = crearPanelExplorarCentro();
		JPanel panelExplorar = crearPanelExplorar(panelExplorarNorte, panelExplorarSur);
		//JPanel panelExplorar2 = crearPanelExplorar(panelExplorarCentro);
		JPanel panelBotones = crearPanelBotones(panelMedio, panelExplorar);
		//JPanel panelBotones2 = crearPanelBotones(panelMedio, panelExplorar2);

		frmVentanaPrincipal.pack();
	}

}
