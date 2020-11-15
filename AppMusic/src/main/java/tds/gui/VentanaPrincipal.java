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

public class VentanaPrincipal {

	private JFrame frmVentanaPrincipal;
	
	public VentanaPrincipal() {
		initialize();
	}


	public void mostrarVentana() {
		frmVentanaPrincipal.setLocationRelativeTo(null);
		frmVentanaPrincipal.setVisible(true);
	}
	
	public void initialize() {
		frmVentanaPrincipal = new JFrame();
		frmVentanaPrincipal.setTitle("AppMusic - Ventana Principal");
		frmVentanaPrincipal.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		frmVentanaPrincipal.setSize(400, 400);
		frmVentanaPrincipal.setMinimumSize(new Dimension(400, 400));

		JPanel contentPane = (JPanel) frmVentanaPrincipal.getContentPane();
		frmVentanaPrincipal.getContentPane().setLayout(new BorderLayout(0, 0));
		
		JPanel panel = new JPanel();
		frmVentanaPrincipal.getContentPane().add(panel, BorderLayout.NORTH);
		panel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
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
		frmVentanaPrincipal.getContentPane().add(panel_1, BorderLayout.WEST);
		panel_1.setLayout(new BoxLayout(panel_1, BoxLayout.Y_AXIS));
		
		JLabel lblNewLabel_1 = new JLabel("Explorar");
		lblNewLabel_1.setIcon(new ImageIcon(getClass().getResource("/raro.png")));
		lblNewLabel_1.setMaximumSize(new Dimension(40,40));
		lblNewLabel_1.setMinimumSize(new Dimension(40,40));
		panel_1.add(lblNewLabel_1);
		
		JLabel lblNewLabel_2 = new JLabel("Nueva lista");
		panel_1.add(lblNewLabel_2);
		
		JLabel lblNewLabel_3 = new JLabel("New label");
		panel_1.add(lblNewLabel_3);
		contentPane.setBorder(new EmptyBorder(10, 10, 10, 10));
		

		
		
		/*JLabel labelCompartirCoche = new JLabel("Bienvenidos a AppMusic");
		labelCompartirCoche.setFont(new Font("Arial", Font.PLAIN, 30));
		labelCompartirCoche.setHorizontalAlignment(SwingConstants.CENTER);
		contentPane.add(labelCompartirCoche, BorderLayout.CENTER);*/
		
		
		frmVentanaPrincipal.pack();
	}

}
