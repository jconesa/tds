package tds.gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
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
	
	public VentanaPrincipal() {
		initialize();
	}


	public void mostrarVentana() {
		frmVentanaPrincipal.setLocationRelativeTo(null);
		frmVentanaPrincipal.setVisible(true);
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
		
		JPanel panel = new JPanel();
		panel.setBackground(Color.WHITE);
		frmVentanaPrincipal.getContentPane().add(panel, BorderLayout.NORTH);
		panel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		JPanel panelMedio = new JPanel();
		frmVentanaPrincipal.getContentPane().add(panelMedio, BorderLayout.CENTER);
		
		JPanel panelExplorar = new JPanel();
		frmVentanaPrincipal.getContentPane().add(panelExplorar);
		panelExplorar.setLayout(new BorderLayout(0, 0));
		
		JPanel panel_2 = new JPanel();
		panelExplorar.add(panel_2, BorderLayout.NORTH);
		
		txtIntrprete = new JTextField();
		txtIntrprete.setText("Intérprete");
		panel_2.add(txtIntrprete);
		txtIntrprete.setColumns(10);
		
		txtTtulo = new JTextField();
		txtTtulo.setText("Título");
		panel_2.add(txtTtulo);
		txtTtulo.setColumns(10);
		
		JComboBox comboBox = new JComboBox();
		panel_2.add(comboBox);
		
		JLabel lblNewLabel_1 = new JLabel("New label");
		panelExplorar.add(lblNewLabel_1, BorderLayout.CENTER);

		
		
		
		
		
		
		JLabel lblNewLabel = new JLabel("Hola jesus");
		panel.add(lblNewLabel);
		
		JButton btnNewButton = new JButton("Mejora tu cuenta");
		panel.add(btnNewButton);
		
		JButton btnNewButton_1 = new JButton("Logout");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		panel.add(btnNewButton_1);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBackground(Color.WHITE);
		frmVentanaPrincipal.getContentPane().add(panel_1, BorderLayout.WEST);
		panel_1.setLayout(new BoxLayout(panel_1, BoxLayout.Y_AXIS));
		
		
		ImageIcon icnExplorar = new ImageIcon(getClass().getResource("/lupa.png"));
		JButton btnExplorar = new JButton("Explorar", icnExplorar);
		btnExplorar.setMinimumSize(new Dimension(100, 20));
		btnExplorar.setAlignmentX(Component.CENTER_ALIGNMENT);
		btnExplorar.setBackground(Color.WHITE);
		btnExplorar.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				panelMedio.removeAll();
				panelMedio.add(panelExplorar);
				panelMedio.revalidate();
				panelMedio.repaint();
				}
		});
	
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
		contentPane.setBorder(new EmptyBorder(10, 10, 10, 10));
		
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
		

		/*JLabel labelCompartirCoche = new JLabel("Bienvenidos a AppMusic");
		labelCompartirCoche.setFont(new Font("Arial", Font.PLAIN, 30));
		labelCompartirCoche.setHorizontalAlignment(SwingConstants.CENTER);
		contentPane.add(labelCompartirCoche, BorderLayout.CENTER);*/
		
		
		frmVentanaPrincipal.pack();
	}

}
