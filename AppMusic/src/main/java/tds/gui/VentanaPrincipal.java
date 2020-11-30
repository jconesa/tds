package tds.gui;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import beans.Entidad;
import tds.controlador.Controlador;
import tds.dominio.CatalogoUsuarios;
import tds.dominio.Usuario;
import tds.driver.ServicioPersistencia;

import java.awt.FlowLayout;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JSeparator;
import java.awt.Component;
import java.awt.Color;
import javax.swing.JTextPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JComboBox;


public class VentanaPrincipal {

	private JFrame frmVentanaPrincipal;
	private JTextField txtTtulo;
	private JTextField txtIntrprete;
	private Usuario usuarioActual = Controlador.getUnicaInstancia().getUsuarioActual();

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
		

		JButton btnNewButton = new JButton("Mejora tu cuenta");
		panel1.add(btnNewButton);
		
		JButton btnNewButton_1 = new JButton("Logout");
		panel1.add(btnNewButton_1);
		
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
		txtIntrprete = new JTextField();
		txtIntrprete.setText("Intérprete");
		panelExplorarNorte.add(txtIntrprete);
		txtIntrprete.setColumns(8);
		
		txtTtulo = new JTextField();
		txtTtulo.setText("Título");
		panelExplorarNorte.add(txtTtulo);
		txtTtulo.setColumns(8);
		
		JComboBox comboBox = new JComboBox();
		panelExplorarNorte.add(comboBox);
		
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
		
		return panelSurPlay;
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
	
	
	public void addManejadorBotonLogout(JButton btnLogout, JPanel panel1) {
		btnLogout.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
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
