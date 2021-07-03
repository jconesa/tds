package tds.gui;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;
import com.itextpdf.text.DocumentException;
import tds.controlador.Controlador;
import tds.dao.DAOException;
import tds.dominio.Cancion;
import tds.dominio.CatalogoCanciones;
import tds.dominio.ListaCanciones;
import tds.dominio.Usuario;
import java.awt.FlowLayout;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.EventObject;
import java.util.LinkedList;
import java.util.List;
import java.awt.event.ActionEvent;
import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JSeparator;
import javax.swing.JTable;
import java.awt.Component;
import java.awt.Color;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFileChooser;
import pulsador.IEncendidoListener;
import pulsador.Luz;

public class VentanaPrincipal {

	private JFrame frmVentanaPrincipal;
	private static JTextField txtTtulo;
	private static JTextField txtIntrprete;
	private static JComboBox<String> generos;
	private static JTextField txtTtulo2;
	private static JTextField txtIntrprete2;
	private static JComboBox<String> generos2;
	private static JTextField nombrePlaylist;

	private static Controlador controlador = Controlador.getUnicaInstancia();
	private Usuario usuarioActual = Controlador.getUnicaInstancia().getUsuarioActual();

	private DefaultListModel<String> modeloLista;
	private JList<String> listaPlaylist;
	private DefaultTableModel modelo;
	private DefaultTableModel modelo2;
	private DefaultTableModel modeloPlaylist;
	private DefaultTableModel modeloMisListas;
	private DefaultTableModel modeloRecientes;
	private DefaultTableModel modeloTop10;
	JTable tabla;
	JTable tabla2;
	JTable tablaPlaylist;
	JTable tablaMisListas;
	private JTable tablaRecientes;
	private JTable tablaTop10;

	/*------------------------PANELES-------------------*/
	private JPanel panelMedio;
	private JPanel panelBotones;
	private JPanel panelMisListas;
	private JPanel panelRecientes;
	private JPanel panelExplorar;
	private JPanel panelExplorarTabla;
	private JPanel panelTop10;
	private JPanel panelNorte;
	private JPanel panelNuevaLista;
	private JPanel panelNuevaListaSur;
	private JPanel panelNuevaListaTablas;
	private JPanel panelNuevaListaNorte;
	private JPanel panelExplorarNorte;
	private JPanel panelExplorarSur;

	public VentanaPrincipal() {
		initialize();
	}

	public static String getTitulo() {
		return txtTtulo.getName();
	}

	public static String getInterprete() {
		return txtIntrprete.getName();
	}

	public static String getGenero() {
		return generos.getSelectedItem().toString();
	}

	public void mostrarVentana() {
		frmVentanaPrincipal.setLocationRelativeTo(null);
		frmVentanaPrincipal.setVisible(true);
	}

	private void crearPanelNorte() {
		panelNorte = new JPanel();
		panelNorte.setBackground(Color.WHITE);
		frmVentanaPrincipal.getContentPane().add(panelNorte, BorderLayout.NORTH);
		panelNorte.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));

		JLabel lblNewLabel = new JLabel("Hola " + usuarioActual.getNombre());
		panelNorte.add(lblNewLabel);

		JButton btnPremium = new JButton("Mejora tu cuenta");
		btnPremium.setBackground(Color.WHITE);
		btnPremium.setMinimumSize(new Dimension(100, 20));
		btnPremium.setAlignmentX(Component.CENTER_ALIGNMENT);
		panelNorte.add(btnPremium);
		


		JButton btnTop10 = new JButton("Top 10");
		btnTop10.setBackground(Color.WHITE);
		btnTop10.setMinimumSize(new Dimension(100, 20));
		btnTop10.setAlignmentX(Component.CENTER_ALIGNMENT);
		
		JButton btnExportarPdf = new JButton("Exportar PDF");
		btnExportarPdf.setBackground(Color.WHITE);
		btnExportarPdf.setMinimumSize(new Dimension(100, 20));
		btnExportarPdf.setAlignmentX(Component.CENTER_ALIGNMENT);
		if (usuarioActual.getPremium() == "false") {
			btnExportarPdf.setVisible(false);
			
		} else {
			btnExportarPdf.setVisible(true);
			panelNorte.remove(btnPremium);
			panelNorte.add(btnExportarPdf);
			panelNorte.add(btnTop10);
			System.out.println("ES INVISIBLE");
		}



		JButton btnLogout = new JButton("Logout");
		btnLogout.setBackground(Color.WHITE);
		btnLogout.setMinimumSize(new Dimension(100, 20));
		btnLogout.setAlignmentX(Component.CENTER_ALIGNMENT);
		panelNorte.add(btnLogout);
		
		addManejadorBotonTop10(btnTop10);
		addManejadorBotonPremium(btnPremium, btnExportarPdf, btnTop10);
		addManejadorBotonExportar(btnExportarPdf);
		addManejadorBotonLogout(btnLogout);
	}

	private void crearTabla() {
		tabla = new JTable();
		// tabla.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		tabla.setCellSelectionEnabled(true);
		tabla.setShowGrid(true);
		tabla.setShowVerticalLines(true);
		tabla.setGridColor(Color.gray);

		modelo = new DefaultTableModel() {
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;
			private String[] columnNames = { "Titulo", "Interprete", "Genero", "Reproducciones" };

			public String getColumnName(int column) {
				return columnNames[column];
			}

			public int getColumnCount() {
				return 4;
			}

			public boolean isCellEditable(int row, int col) {
				return false;
			}
		};
		tabla.setModel(modelo);

		try {
			for (Cancion cancion : CatalogoCanciones.getUnicaInstancia().getCanciones()) {
				System.out.println(cancion.getId());
				System.out.println(cancion.getTitulo());
				System.out.println(cancion.getInterprete());
				modelo.addRow(new Object[] { cancion.getTitulo(), cancion.getInterprete(), cancion.getGenero(),
						cancion.getNumReproducciones() });
			}
		} catch (DAOException e) {
			e.printStackTrace();
		}


	}

	private void crearTabla2() {
		tabla2 = new JTable();
		tabla2.setCellSelectionEnabled(true);
		tabla2.setShowGrid(true);
		tabla2.setShowVerticalLines(true);
		tabla2.setGridColor(Color.gray);

		modelo2 = new DefaultTableModel() {
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;
			private String[] columnNames = { "Titulo", "Interprete", "Genero", "Reproducciones" };

			public String getColumnName(int column) {
				return columnNames[column];
			}

			public int getColumnCount() {
				return 4;
			}

			public boolean isCellEditable(int row, int col) {
				return false;
			}
		};
		tabla2.setModel(modelo2);

	}

	private void crearLista() {
		listaPlaylist = new JList<String>();
		modeloLista = new DefaultListModel<String>();
		listaPlaylist.setVisibleRowCount(1);
		listaPlaylist.setModel(modeloLista);
		listaPlaylist.setSelectedIndex(1);
		listaPlaylist.addMouseListener(new MouseAdapter() {
			@SuppressWarnings("unchecked")
			public void mouseClicked(MouseEvent evt) {
				JList<String> source = (JList<String>) evt.getSource();
				listaPlaylist = source;
				if (evt.getClickCount() == 1) {

					// Double-click detected
					String selected = listaPlaylist.getSelectedValue().toString();
					controlador.setTablaActual(tablaMisListas);
					showLista(selected);

				}
			}
		});

	}

	private void showLista(String titulo) {
		clearTablaMisListas();
		for (Cancion cancion : usuarioActual.getListaCancionesTitulo(titulo).getListaCanciones()) {
			modeloMisListas.addRow(new Object[] { cancion.getTitulo(), cancion.getInterprete() });
		}
		panelMedio.removeAll();
		panelMedio.add(panelMisListas);
		panelMedio.revalidate();
		panelMedio.repaint();
	}

	private void crearTablaPlaylist() {
		tablaPlaylist = new JTable();
		// tabla.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		tablaPlaylist.setCellSelectionEnabled(true);
		tablaPlaylist.setShowGrid(true);
		tablaPlaylist.setShowVerticalLines(true);
		tablaPlaylist.setGridColor(Color.gray);

		modeloPlaylist = new DefaultTableModel() {
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;
			private String[] columnNames = { "Titulo", "Interprete", "Genero" };

			public String getColumnName(int column) {
				return columnNames[column];
			}

			public int getColumnCount() {
				return 3;
			}

			public boolean isCellEditable(int row, int col) {
				return false;
			}
		};
		tablaPlaylist.setModel(modeloPlaylist);

	}

	private void crearTablaMisListas() {
		tablaMisListas = new JTable();
		tablaMisListas.setCellSelectionEnabled(true);
		tablaMisListas.setShowGrid(true);
		tablaMisListas.setShowVerticalLines(true);
		tablaMisListas.setGridColor(Color.gray);

		modeloMisListas = new DefaultTableModel() {
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;
			private String[] columnNames = { "Titulo", "Interprete" };

			public String getColumnName(int column) {
				return columnNames[column];
			}

			public int getColumnCount() {
				return 2;
			}

			public boolean isCellEditable(int row, int col) {
				return false;
			}
		};
		tablaMisListas.setModel(modeloMisListas);


	}

	private void crearTablaRecientes() {
		tablaRecientes = new JTable();
		tablaRecientes.setCellSelectionEnabled(true);
		tablaRecientes.setShowGrid(true);
		tablaRecientes.setShowVerticalLines(true);
		tablaRecientes.setGridColor(Color.gray);

		modeloRecientes = new DefaultTableModel() {
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;
			private String[] columnNames = { "Titulo", "Interprete" };

			public String getColumnName(int column) {
				return columnNames[column];
			}

			public int getColumnCount() {
				return 2;
			}

			public boolean isCellEditable(int row, int col) {
				return true;
			}
		};
		tablaRecientes.setModel(modeloRecientes);

	}

	private void crearTablaTop10() {
		tablaTop10 = new JTable();
		tablaTop10.setCellSelectionEnabled(false);
		tablaTop10.setShowGrid(true);
		tablaTop10.setShowVerticalLines(true);
		tablaTop10.setGridColor(Color.gray);

		modeloTop10 = new DefaultTableModel() {
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;
			private String[] columnNames = { "Titulo", "Interprete", "Reproducciones" };

			public String getColumnName(int column) {
				return columnNames[column];
			}

			public int getColumnCount() {
				return 3;
			}

			public boolean isCellEditable(int row, int col) {
				return false;
			}
		};
		tablaTop10.setModel(modeloTop10);

	}
	private JPanel crearPanelMedio() {
		panelMedio = new JPanel();
		frmVentanaPrincipal.getContentPane().add(panelMedio, BorderLayout.CENTER);
		panelMedio.setLayout(new BorderLayout(0, 0));

		return panelMedio;
	}

	private void crearPanelBotones() {
		panelBotones = new JPanel();
		panelBotones.setBackground(Color.WHITE);
		frmVentanaPrincipal.getContentPane().add(panelBotones, BorderLayout.WEST);
		panelBotones.setLayout(new BoxLayout(panelBotones, BoxLayout.Y_AXIS));

		Luz luz = new Luz();
		luz.setMaximumSize(new Dimension(70, 70));
		luz.setPreferredSize(new Dimension(70, 70));
		luz.addEncendidoListener(new IEncendidoListener() {

			@Override
			public void enteradoCambioEncendido(EventObject arg0) {
				JFileChooser fileChooser = new JFileChooser(".");
				fileChooser.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {

						if (e.getActionCommand().equals(JFileChooser.APPROVE_SELECTION)) {
							try {
								System.out.println("IMPORTAR");
								importar(fileChooser.getSelectedFile());
							} catch (IOException e1) {
								e1.printStackTrace();
							}
						}
					}
				});
				fileChooser.showOpenDialog(frmVentanaPrincipal.getContentPane());

			}
		});
		panelBotones.add(luz);

		JSeparator separatorLuz = new JSeparator();
		separatorLuz.setForeground(Color.WHITE);
		separatorLuz.setOrientation(SwingConstants.VERTICAL);
		separatorLuz.setBorder(null);
		separatorLuz.setMinimumSize(new Dimension(20, 20));
		separatorLuz.setMaximumSize(new Dimension(20, 20));
		panelBotones.add(separatorLuz);
		
		ImageIcon icnExplorar = new ImageIcon(getClass().getResource("/lupa.png"));
		JButton btnExplorar = new JButton("Explorar", icnExplorar);
		btnExplorar.setMinimumSize(new Dimension(100, 20));
		btnExplorar.setAlignmentX(Component.CENTER_ALIGNMENT);
		btnExplorar.setBackground(Color.WHITE);
		panelBotones.add(btnExplorar);

		JSeparator separator = new JSeparator();
		separator.setForeground(Color.WHITE);
		separator.setOrientation(SwingConstants.VERTICAL);
		separator.setBorder(null);
		separator.setMinimumSize(new Dimension(20, 20));
		separator.setMaximumSize(new Dimension(20, 20));
		panelBotones.add(separator);

		ImageIcon icnNuevaLista = new ImageIcon(getClass().getResource("/plus.png"));
		JButton btnNuevaLista = new JButton("Nueva lista", icnNuevaLista);
		btnNuevaLista.setMinimumSize(new Dimension(100, 20));
		btnNuevaLista.setAlignmentX(Component.CENTER_ALIGNMENT);

		btnNuevaLista.setBackground(Color.WHITE);
		panelBotones.add(btnNuevaLista);

		JSeparator separator2 = new JSeparator();
		separator2.setForeground(Color.WHITE);
		separator2.setOrientation(SwingConstants.VERTICAL);
		separator2.setBorder(null);
		separator2.setMinimumSize(new Dimension(20, 20));
		separator2.setMaximumSize(new Dimension(20, 20));
		panelBotones.add(separator2);

		ImageIcon icnRecientes = new ImageIcon(getClass().getResource("/recientes.png"));
		JButton btnRecientes = new JButton("Recientes", icnRecientes);
		btnRecientes.setBackground(Color.WHITE);
		btnRecientes.setAlignmentX(Component.CENTER_ALIGNMENT);
		btnRecientes.setMinimumSize(new Dimension(100, 20));
		panelBotones.add(btnRecientes);

		JSeparator separator3 = new JSeparator();
		separator3.setForeground(Color.WHITE);
		separator3.setOrientation(SwingConstants.VERTICAL);
		separator3.setBorder(null);
		separator3.setMinimumSize(new Dimension(20, 20));
		separator3.setMaximumSize(new Dimension(20, 20));
		panelBotones.add(separator3);

		ImageIcon icnListas = new ImageIcon(getClass().getResource("/listas.png"));
		JButton btnListas = new JButton("Mis listas", icnListas);
		btnListas.setBackground(Color.WHITE);
		btnListas.setMinimumSize(new Dimension(100, 20));
		btnListas.setAlignmentX(Component.CENTER_ALIGNMENT);
		panelBotones.add(btnListas);

		addManejadorBotonExplorar(btnExplorar, panelMedio, panelExplorar);
		addManejadorBotonNuevaLista(btnNuevaLista, panelMedio, panelNuevaLista);
		addManejadorBotonMisListas(btnListas);
		addManejadorBotonRecientes(btnRecientes);
		crearLista();
		listaPlaylist.setVisible(false);
		panelBotones.add(listaPlaylist);

	}

	private void importar(File selectedFile) throws IOException {
		controlador.cargarCanciones(selectedFile);

	}

	public JPanel crearPanelExplorarNorte() {
		panelExplorarNorte = new JPanel();
		
		JPanel panelExplorarNorteN = new JPanel();
		JPanel panelExplorarNorteC = new JPanel();
		panelExplorarNorte.setLayout(new BorderLayout(0, 0));

		JLabel interprete = new JLabel("Interprete");
		panelExplorarNorteN.add(interprete);
		txtIntrprete = new JTextField();
		panelExplorarNorteN.add(txtIntrprete);
		txtIntrprete.setColumns(8);

		JLabel titulo = new JLabel("Titulo");
		panelExplorarNorteN.add(titulo);
		txtTtulo = new JTextField();
		panelExplorarNorteN.add(txtTtulo);
		txtTtulo.setColumns(8);

		generos = new JComboBox<String>();
		generos.setEditable(true);
		generos.addItem("");
		generos.addItem("Bolero");
		generos.addItem("Cantautor");
		generos.addItem("Clásica");
		generos.addItem("Flamenco");
		generos.addItem("Jazz");
		generos.addItem("Opera");
		generos.addItem("Pop");
		generos.addItem("Rock");
		generos.addItem("Romántica");
		generos.addItem("Folk");
		generos.addItem("Tango");
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

		addManejadorBotonBuscar(btnBuscar);
		return panelExplorarNorte;
	}

	public void crearPanelExplorarNorte2() {
		panelNuevaListaNorte = new JPanel();
		JPanel panelExplorarNorteN = new JPanel();
		JPanel panelExplorarNorteC = new JPanel();
		panelNuevaListaNorte.setLayout(new BorderLayout(0, 0));

		JLabel interprete = new JLabel("Interprete");
		panelExplorarNorteN.add(interprete);
		txtIntrprete2 = new JTextField();
		panelExplorarNorteN.add(txtIntrprete2);
		txtIntrprete2.setColumns(8);

		JLabel titulo = new JLabel("Titulo");
		panelExplorarNorteN.add(titulo);
		txtTtulo2 = new JTextField();
		panelExplorarNorteN.add(txtTtulo2);
		txtTtulo2.setColumns(8);

		generos2 = new JComboBox<String>();
		generos2.setEditable(true);
		generos2.addItem("");
		generos2.addItem("Bolero");
		generos2.addItem("Cantautor");
		generos2.addItem("Clásica");
		generos2.addItem("Flamenco");
		generos2.addItem("Jazz");
		generos2.addItem("Opera");
		generos2.addItem("Pop");
		generos2.addItem("Rock");
		generos2.addItem("Romántica");
		generos2.addItem("Folk");
		generos2.addItem("Tango");
		panelExplorarNorteN.add(generos2, BorderLayout.NORTH);

		JButton btnBuscarr = new JButton("Buscar");
		btnBuscarr.setMinimumSize(new Dimension(100, 20));
		btnBuscarr.setBackground(Color.WHITE);
		panelExplorarNorteC.add(btnBuscarr);

		JButton btnCancelar = new JButton("Cancelar");
		btnCancelar.setMinimumSize(new Dimension(100, 20));
		btnCancelar.setBackground(Color.WHITE);
		panelExplorarNorteC.add(btnCancelar);

		panelNuevaListaNorte.add(panelExplorarNorteN, BorderLayout.NORTH);
		panelNuevaListaNorte.add(panelExplorarNorteC, BorderLayout.CENTER);

		addManejadorBotonBuscar2(btnBuscarr);

	}

	public void crearPanelNuevaListaTablas() {
		panelNuevaListaTablas = new JPanel();
		JPanel panelNuevaListaIzquierda = new JPanel();
		JPanel panelNuevaListarPlaylist = new JPanel();
		JPanel panelCrearPlaylist = new JPanel();

		JPanel botones = new JPanel();
		botones.setLayout(new BoxLayout(botones, BoxLayout.Y_AXIS));
		
		JButton crearPlaylist = new JButton("Crear");
		crearPlaylist.setMinimumSize(new Dimension(100, 20));
		crearPlaylist.setBackground(Color.WHITE);

		panelCrearPlaylist.add(crearPlaylist);
		
		nombrePlaylist = new JTextField();
		panelCrearPlaylist.add(nombrePlaylist);
		nombrePlaylist.setColumns(8);

		JButton addCancion = new JButton(">>");
		addCancion.setMinimumSize(new Dimension(100, 20));
		addCancion.setBackground(Color.WHITE);
		botones.add(addCancion);

		JButton quitarCancion = new JButton("<<");
		quitarCancion.setMinimumSize(new Dimension(100, 20));
		quitarCancion.setBackground(Color.WHITE);
		botones.add(quitarCancion);

		crearTabla2();
		JScrollPane tablaConScroll = new JScrollPane(tabla2);
		panelNuevaListaIzquierda.add(tablaConScroll);

		crearTablaPlaylist();
		JScrollPane tablaPlaylistConScroll = new JScrollPane(tablaPlaylist);
		panelNuevaListarPlaylist.add(tablaPlaylistConScroll);

		panelNuevaListaTablas.add(panelNuevaListaIzquierda, BorderLayout.WEST);
		panelNuevaListaTablas.add(botones, BorderLayout.CENTER);

		panelNuevaListaTablas.add(panelNuevaListarPlaylist, BorderLayout.EAST);
		panelNuevaListaTablas.add(panelCrearPlaylist, BorderLayout.SOUTH);
		
		addManejadorBotonCrearPlaylist(crearPlaylist, tablaConScroll, tablaPlaylistConScroll, panelNuevaListaIzquierda, botones, panelNuevaListarPlaylist, panelCrearPlaylist );
		addManejadorBotonAddCancionPlaylist(addCancion);
		addManejadorBotonRemoveCancionPlaylist(quitarCancion);

	}

	public void crearPanelNorteCentro() {
		panelNuevaLista = new JPanel();
		panelNuevaLista.setLayout(new BorderLayout(0, 0));
		panelNuevaLista.add(panelNuevaListaNorte, BorderLayout.NORTH);
		panelNuevaLista.add(panelNuevaListaTablas, BorderLayout.CENTER);
		panelNuevaLista.add(panelNuevaListaSur, BorderLayout.SOUTH);


	}

	private void fixedSize(JComponent c, int x, int y) {
		c.setMinimumSize(new Dimension(x, y));
		c.setMaximumSize(new Dimension(x, y));
		c.setPreferredSize(new Dimension(x, y));
	}

	public void crearPanelExplorarTabla() {
		panelExplorarTabla = new JPanel();
		panelExplorarTabla.setLayout(new BorderLayout(0, 0));
		crearTabla();
		JScrollPane tablaConScroll = new JScrollPane(tabla);
		panelExplorarTabla.add(tablaConScroll);
		fixedSize(tablaConScroll, 100, 100);


	}

	public void crearPanelExploraSur() {

		panelExplorarSur = new JPanel();
		panelExplorarSur.setLayout(new BorderLayout(0, 0));

		ImageIcon icnPlay = new ImageIcon(getClass().getResource("/play.png"));

		JButton btnPlay = new JButton(icnPlay);
		btnPlay.setMinimumSize(new Dimension(100, 20));
		btnPlay.setMaximumSize(new Dimension(100, 20));

		panelExplorarSur.add(btnPlay, BorderLayout.NORTH);

		ImageIcon icnPause = new ImageIcon(getClass().getResource("/pausa.png"));
		JButton btnPause = new JButton(icnPause);
		btnPause.setMinimumSize(new Dimension(100, 20));
		btnPause.setAlignmentX(Component.BOTTOM_ALIGNMENT);
		panelExplorarSur.add(btnPause);

		ImageIcon icnAnterior = new ImageIcon(getClass().getResource("/anterior.png"));

		JButton btnAnterior = new JButton(icnAnterior);
		btnAnterior.setMinimumSize(new Dimension(100, 20));

		panelExplorarSur.add(btnAnterior, BorderLayout.WEST);

		ImageIcon icnSiguiente = new ImageIcon(getClass().getResource("/siguiente.png"));

		JButton btnSiguiente = new JButton(icnSiguiente);
		btnSiguiente.setMinimumSize(new Dimension(100, 20));

		panelExplorarSur.add(btnSiguiente, BorderLayout.EAST);
		addManejadorBotonPlay(btnPlay);
		addManejadorBotonStop(btnPause);
		addManejadorBotonSiguiente(btnSiguiente);
		addManejadorBotonAnterior(btnAnterior);

	}

	public void crearPanelNuevaListaSur() {

		panelNuevaListaSur = new JPanel();;
		panelNuevaListaSur.setLayout(new FlowLayout());

		JButton btnAceptar = new JButton("Aceptar");
		btnAceptar.setMinimumSize(new Dimension(20, 20));
		btnAceptar.setMaximumSize(new Dimension(20, 20));
		// btnAceptar.setAlignmentX(Component.BOTTOM_ALIGNMENT);

		panelNuevaListaSur.add(btnAceptar);

		JButton btnCancelar = new JButton("Cancelar");
		btnCancelar.setMinimumSize(new Dimension(20, 20));
		btnCancelar.setMaximumSize(new Dimension(20, 20));

		// btnCancelar.setAlignmentX(Component.BOTTOM_ALIGNMENT);

		panelNuevaListaSur.add(btnCancelar);

		addManejadorBotonAceptarPlaylist(btnAceptar);
	}

	public JPanel crearPanelMisListas() {
		panelMisListas = new JPanel();
		panelMisListas.setLayout(new BorderLayout(0, 0));
		crearTablaMisListas();
		JScrollPane tablaConScroll = new JScrollPane(tablaMisListas);
		panelMisListas.add(tablaConScroll);
		fixedSize(tablaConScroll, 100, 100);

		JPanel panelSurMisListas = new JPanel();
		panelSurMisListas.setLayout(new BorderLayout(0, 0));

		ImageIcon icnPlay = new ImageIcon(getClass().getResource("/play.png"));

		JButton btnPlay = new JButton(icnPlay);
		btnPlay.setMinimumSize(new Dimension(100, 20));
		btnPlay.setMaximumSize(new Dimension(100, 20));

		panelSurMisListas.add(btnPlay, BorderLayout.NORTH);

		ImageIcon icnPause = new ImageIcon(getClass().getResource("/pausa.png"));
		JButton btnPause = new JButton(icnPause);
		btnPause.setMinimumSize(new Dimension(100, 20));
		btnPause.setAlignmentX(Component.BOTTOM_ALIGNMENT);
		panelSurMisListas.add(btnPause);

		ImageIcon icnAnterior = new ImageIcon(getClass().getResource("/anterior.png"));

		JButton btnAnterior = new JButton(icnAnterior);
		btnAnterior.setMinimumSize(new Dimension(100, 20));

		panelSurMisListas.add(btnAnterior, BorderLayout.WEST);

		ImageIcon icnSiguiente = new ImageIcon(getClass().getResource("/siguiente.png"));

		JButton btnSiguiente = new JButton(icnSiguiente);
		btnSiguiente.setMinimumSize(new Dimension(100, 20));

		panelSurMisListas.add(btnSiguiente, BorderLayout.EAST);

		addManejadorBotonPlay(btnPlay);
		addManejadorBotonStop(btnPause);
		addManejadorBotonSiguiente(btnSiguiente);
		addManejadorBotonAnterior(btnAnterior);

		panelMisListas.add(panelSurMisListas, BorderLayout.SOUTH);
		return panelMisListas;
	}

	public JPanel crearPanelRecientes() {
		panelRecientes = new JPanel();
		panelRecientes.setLayout(new BorderLayout(0, 0));
		crearTablaRecientes();
		JScrollPane tablaConScroll = new JScrollPane(tablaRecientes);
		panelRecientes.add(tablaConScroll);
		fixedSize(tablaConScroll, 100, 100);

		JPanel panelSurRecientes = new JPanel();
		panelSurRecientes.setLayout(new BorderLayout(0, 0));

		ImageIcon icnPlay = new ImageIcon(getClass().getResource("/play.png"));

		JButton btnPlay = new JButton(icnPlay);
		btnPlay.setMinimumSize(new Dimension(100, 20));
		btnPlay.setMaximumSize(new Dimension(100, 20));

		panelSurRecientes.add(btnPlay, BorderLayout.NORTH);

		ImageIcon icnPause = new ImageIcon(getClass().getResource("/pausa.png"));
		JButton btnPause = new JButton(icnPause);
		btnPause.setMinimumSize(new Dimension(100, 20));
		btnPause.setAlignmentX(Component.BOTTOM_ALIGNMENT);
		panelSurRecientes.add(btnPause);

		ImageIcon icnAnterior = new ImageIcon(getClass().getResource("/anterior.png"));

		JButton btnAnterior = new JButton(icnAnterior);
		btnAnterior.setMinimumSize(new Dimension(100, 20));

		panelSurRecientes.add(btnAnterior, BorderLayout.WEST);

		ImageIcon icnSiguiente = new ImageIcon(getClass().getResource("/siguiente.png"));

		JButton btnSiguiente = new JButton(icnSiguiente);
		btnSiguiente.setMinimumSize(new Dimension(100, 20));

		panelSurRecientes.add(btnSiguiente, BorderLayout.EAST);

		addManejadorBotonPlay(btnPlay);
		addManejadorBotonStop(btnPause);
		addManejadorBotonSiguiente(btnSiguiente);
		addManejadorBotonAnterior(btnAnterior);
		panelRecientes.add(panelSurRecientes, BorderLayout.SOUTH);
		return panelRecientes;
	}

	public JPanel crearPanelTop10() {
		panelTop10 = new JPanel();
		panelTop10.setLayout(new BorderLayout(0, 0));
		crearTablaTop10();
		JScrollPane tablaConScroll = new JScrollPane(tablaTop10);
		panelTop10.add(tablaConScroll);
		fixedSize(tablaConScroll, 100, 100);

		JPanel panelSurTop10 = new JPanel();
		panelSurTop10.setLayout(new BorderLayout(0, 0));

		ImageIcon icnPlay = new ImageIcon(getClass().getResource("/play.png"));

		JButton btnPlay = new JButton(icnPlay);
		btnPlay.setMinimumSize(new Dimension(100, 20));
		btnPlay.setMaximumSize(new Dimension(100, 20));

		panelSurTop10.add(btnPlay, BorderLayout.NORTH);

		ImageIcon icnPause = new ImageIcon(getClass().getResource("/pausa.png"));
		JButton btnPause = new JButton(icnPause);
		btnPause.setMinimumSize(new Dimension(100, 20));
		btnPause.setAlignmentX(Component.BOTTOM_ALIGNMENT);
		panelSurTop10.add(btnPause);

		ImageIcon icnAnterior = new ImageIcon(getClass().getResource("/anterior.png"));

		JButton btnAnterior = new JButton(icnAnterior);
		btnAnterior.setMinimumSize(new Dimension(100, 20));

		panelSurTop10.add(btnAnterior, BorderLayout.WEST);

		ImageIcon icnSiguiente = new ImageIcon(getClass().getResource("/siguiente.png"));

		JButton btnSiguiente = new JButton(icnSiguiente);
		btnSiguiente.setMinimumSize(new Dimension(100, 20));

		panelSurTop10.add(btnSiguiente, BorderLayout.EAST);

		panelTop10.add(panelSurTop10, BorderLayout.SOUTH);
		
		addManejadorBotonPlay(btnPlay);
		addManejadorBotonStop(btnPause);
		addManejadorBotonSiguiente(btnSiguiente);
		addManejadorBotonAnterior(btnAnterior);

		return panelRecientes;
	}

	public LinkedList<Cancion> aplicarFiltro(LinkedList<Cancion> listaCanciones) {
		LinkedList<Cancion> listaCancionesFiltrada = new LinkedList<Cancion>();

		/*
		 * // Interprete y titulo vacios if(txtIntrprete.getText().isEmpty() &&
		 * txtTtulo.getText().isEmpty()) { String genero =
		 * generos.getSelectedItem().toString(); for(Cancion cancion : listaCanciones) {
		 * if(genero.equals(cancion.getGenero())) { listaCancionesFiltrada.add(cancion);
		 * } } }
		 */

		// Solo título
		if (txtIntrprete.getText().isEmpty() && !txtTtulo.getText().isEmpty() && generos.getSelectedItem().equals("")) {
			String titulo = txtTtulo.getText();
			for (Cancion cancion : listaCanciones) {
				if (titulo.equals(cancion.getTitulo())) {
					listaCancionesFiltrada.add(cancion);
				}
			}
		}

		// Solo intérprete
		if (!txtIntrprete.getText().isEmpty() && txtTtulo.getText().isEmpty() && generos.getSelectedItem().equals("")) {
			String interprete = txtIntrprete.getText();
			for (Cancion cancion : listaCanciones) {
				if (interprete.equals(cancion.getInterprete())) {
					listaCancionesFiltrada.add(cancion);
				}
			}
		}

		// Solo intérprete
		if (txtIntrprete.getText().isEmpty() && txtTtulo.getText().isEmpty() && !generos.getSelectedItem().equals("")) {
			String genero = generos.getSelectedItem().toString();
			for (Cancion cancion : listaCanciones) {
				if (genero.equals(cancion.getGenero())) {
					listaCancionesFiltrada.add(cancion);
				}
			}
		}
		// Sin interprete, con titulo y género
		if (txtIntrprete.getText().isEmpty() && !txtTtulo.getText().isEmpty()) {
			String genero = generos.getSelectedItem().toString();
			String titulo = txtTtulo.getText();
			for (Cancion cancion : listaCanciones) {
				if (genero.equals(cancion.getGenero()) && titulo.equals(cancion.getTitulo())) {
					listaCancionesFiltrada.add(cancion);
				}
			}
		}

		// Con titulo e interprete
		if (!txtIntrprete.getText().isEmpty() && !txtTtulo.getText().isEmpty()
				&& generos.getSelectedItem().equals("")) {
			String titulo = txtTtulo.getText();
			String interprete = txtIntrprete.getText();
			for (Cancion cancion : listaCanciones) {
				if (titulo.equals(cancion.getTitulo()) && interprete.equals(cancion.getInterprete())) {
					listaCancionesFiltrada.add(cancion);
				}
			}
		}

		// Con interprete y genero, titulo vacio
		if (!txtIntrprete.getText().isEmpty() && txtTtulo.getText().isEmpty()) {
			String genero = generos.getSelectedItem().toString();
			String interprete = txtIntrprete.getText();
			for (Cancion cancion : listaCanciones) {
				if (genero.equals(cancion.getGenero()) && interprete.equals(cancion.getInterprete())) {
					listaCancionesFiltrada.add(cancion);
				}
			}
		}

		// Con todo
		if (!txtIntrprete.getText().isEmpty() && !txtTtulo.getText().isEmpty()) {
			String genero = generos.getSelectedItem().toString();
			String titulo = txtTtulo.getText();
			String interprete = txtIntrprete.getText();
			for (Cancion cancion : listaCanciones) {
				if (genero.equals(cancion.getGenero()) && interprete.equals(cancion.getInterprete())
						&& titulo.equals(cancion.getTitulo())) {
					listaCancionesFiltrada.add(cancion);
				}
			}
		}

		if (txtIntrprete.getText().isEmpty() && txtTtulo.getText().isEmpty() && generos.getSelectedItem().equals("")) {
			return listaCanciones;
		}

		return listaCancionesFiltrada;

	}

	public LinkedList<Cancion> aplicarFiltro2(LinkedList<Cancion> listaCanciones) {
		LinkedList<Cancion> listaCancionesFiltrada = new LinkedList<Cancion>();

		/*
		 * // Interprete y titulo vacios if(txtIntrprete.getText().isEmpty() &&
		 * txtTtulo.getText().isEmpty()) { String genero =
		 * generos.getSelectedItem().toString(); for(Cancion cancion : listaCanciones) {
		 * if(genero.equals(cancion.getGenero())) { listaCancionesFiltrada.add(cancion);
		 * } } }
		 */

		// Solo título
		if (txtIntrprete2.getText().isEmpty() && !txtTtulo2.getText().isEmpty()
				&& generos2.getSelectedItem().equals("")) {
			String titulo = txtTtulo.getText();
			for (Cancion cancion : listaCanciones) {
				if (titulo.equals(cancion.getTitulo())) {
					listaCancionesFiltrada.add(cancion);
				}
			}
		}

		// Solo intérprete
		if (!txtIntrprete2.getText().isEmpty() && txtTtulo2.getText().isEmpty()
				&& generos2.getSelectedItem().equals("")) {
			String interprete = txtIntrprete2.getText();
			for (Cancion cancion : listaCanciones) {
				if (interprete.equals(cancion.getInterprete())) {
					listaCancionesFiltrada.add(cancion);
				}
			}
		}

		// Solo intérprete
		if (txtIntrprete2.getText().isEmpty() && txtTtulo2.getText().isEmpty()
				&& !generos2.getSelectedItem().equals("")) {
			String genero = generos2.getSelectedItem().toString();
			for (Cancion cancion : listaCanciones) {
				if (genero.equals(cancion.getGenero())) {
					listaCancionesFiltrada.add(cancion);
				}
			}
		}
		// Sin interprete, con titulo y género
		if (txtIntrprete2.getText().isEmpty() && !txtTtulo2.getText().isEmpty()) {
			String genero = generos2.getSelectedItem().toString();
			String titulo = txtTtulo2.getText();
			for (Cancion cancion : listaCanciones) {
				if (genero.equals(cancion.getGenero()) && titulo.equals(cancion.getTitulo())) {
					listaCancionesFiltrada.add(cancion);
				}
			}
		}

		// Con titulo e interprete
		if (!txtIntrprete2.getText().isEmpty() && !txtTtulo2.getText().isEmpty()
				&& generos2.getSelectedItem().equals("")) {
			String titulo = txtTtulo2.getText();
			String interprete = txtIntrprete2.getText();
			for (Cancion cancion : listaCanciones) {
				if (titulo.equals(cancion.getTitulo()) && interprete.equals(cancion.getInterprete())) {
					listaCancionesFiltrada.add(cancion);
				}
			}
		}

		// Con interprete y genero, titulo vacio
		if (!txtIntrprete2.getText().isEmpty() && txtTtulo2.getText().isEmpty()) {
			String genero = generos2.getSelectedItem().toString();
			String interprete = txtIntrprete2.getText();
			for (Cancion cancion : listaCanciones) {
				if (genero.equals(cancion.getGenero()) && interprete.equals(cancion.getInterprete())) {
					listaCancionesFiltrada.add(cancion);
				}
			}
		}

		// Con todo
		if (!txtIntrprete2.getText().isEmpty() && !txtTtulo2.getText().isEmpty()) {
			String genero = generos2.getSelectedItem().toString();
			String titulo = txtTtulo2.getText();
			String interprete = txtIntrprete2.getText();
			for (Cancion cancion : listaCanciones) {
				if (genero.equals(cancion.getGenero()) && interprete.equals(cancion.getInterprete())
						&& titulo.equals(cancion.getTitulo())) {
					listaCancionesFiltrada.add(cancion);
				}
			}
		}

		if (txtIntrprete2.getText().isEmpty() && txtTtulo2.getText().isEmpty()
				&& generos2.getSelectedItem().equals("")) {
			return listaCanciones;
		}

		return listaCancionesFiltrada;

	}

	/*
	 * public JPanel crearPanelExplorarCentro() { JPanel panelExplorarCentro = new
	 * JPanel(); ImageIcon icnPause = new
	 * ImageIcon(getClass().getResource("/pause.png")); JButton btnPause = new
	 * JButton("Pause", icnPause); btnPause.setBackground(Color.WHITE);
	 * btnPause.setAlignmentX(Component.CENTER_ALIGNMENT);
	 * btnPause.setMinimumSize(new Dimension(100, 20));
	 * //panelExplorarCentro.add(btnPause); JComboBox comboBox = new JComboBox();
	 * panelExplorarCentro.add(comboBox);
	 * 
	 * 
	 * return panelExplorarCentro;
	 * 
	 * 
	 * }
	 */
	private void clearLista() {
		modeloLista.removeAllElements();
	}

	private void clearTable() {
		modelo.setRowCount(0);
	}

	private void clearTable2() {
		modelo2.setRowCount(0);
	}

	private void clearTablePlayList() {
		modeloPlaylist.setRowCount(0);
	}

	private void clearTablaMisListas() {
		modeloMisListas.setRowCount(0);
	}

	private void clearTablaRecientes() {
		modeloRecientes.setRowCount(0);
	}

	private void clearTablaTop10() {
		modeloTop10.setRowCount(0);
	}
	public void revalidarPanelMedio(JPanel panel) {
		panelMedio.removeAll();
		panelMedio.add(panel);
		panelMedio.revalidate();
		panelMedio.repaint();
	}
	
	public void crearPanelExplorar() {
		panelExplorar = new JPanel();
		panelExplorar.setLayout(new BorderLayout(0, 0));
		panelExplorar.add(panelExplorarNorte, BorderLayout.NORTH);
		panelExplorar.add(panelExplorarSur, BorderLayout.SOUTH);
		panelExplorar.add(panelExplorarTabla, BorderLayout.CENTER);
	}

	public void addManejadorBotonExplorar(JButton btnExplorar, JPanel panelMedio, JPanel panelExplorar) {
		btnExplorar.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				controlador.setTablaActual(tabla);
				revalidarPanelMedio(panelExplorar);
			}
		});
	}

	public void addManejadorBotonNuevaLista(JButton btnNuevaLista, JPanel panelMedio, JPanel panelNuevaLista) {
		btnNuevaLista.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				revalidarPanelMedio(panelNuevaLista);
			}
		});
	}

	public void addManejadorBotonExportar(JButton btnExportar) {
		btnExportar.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					controlador.exportarPDF();
				} catch (FileNotFoundException | DocumentException e1) {
					e1.printStackTrace();
				}
			}
		});
	}

	public void addManejadorBotonPremium(JButton btnPremium, JButton btnExportarPdf, JButton btnTop10) {
		btnPremium.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				
				int respuesta = JOptionPane.showConfirmDialog(null, "¿Desea hacerse premium por " + usuarioActual.getDescuento() + " euros?",
						"Hazte premium", JOptionPane.YES_NO_OPTION);
				if (respuesta == JOptionPane.YES_OPTION)
					usuarioActual.realizarPago();
				
				if (usuarioActual.getPremium() == "false") {
					btnExportarPdf.setVisible(false);
					panelNorte.remove(btnExportarPdf);
					
				} else {
					btnExportarPdf.setVisible(true);
					btnTop10.setVisible(true);
					panelNorte.add(btnTop10, 1);
					panelNorte.add(btnExportarPdf, 2);

					panelNorte.remove(btnPremium);
					System.out.println("ES INVISIBLE");
				}
				panelNorte.revalidate();
				panelNorte.repaint();
			}

		});
	}

	public void addManejadorBotonPlay(JButton btnPlay) {
		btnPlay.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				JTable tabla = Controlador.getUnicaInstancia().getTablaActual();
				if(tabla.getSelectedRow() == -1)
					tabla.setRowSelectionInterval(0, 0);
				String titulo = (String) tabla.getValueAt(tabla.getSelectedRow(), 0);
				String interprete = (String) tabla.getValueAt(tabla.getSelectedRow(), 1);
				String ruta = interprete + "-" + titulo;
				System.out.println("MANEJADOR BOTON PLAY");
				System.out.println(ruta);
				controlador.playSong(titulo);
			}
		});
	}

	public void addManejadorBotonStop(JButton btnPlay) {
		btnPlay.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				controlador.stopSong();
			}
		});
	}

	public void addManejadorBotonSiguiente(JButton btnSiguiente) {
		btnSiguiente.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				controlador.nextSong();
			}
		});
	}
	
	public void addManejadorBotonAnterior(JButton btnAnterior) {
		btnAnterior.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				controlador.previousSong();
			}
		});
	}

	public void addManejadorBotonBuscar(JButton btnBuscar) {
		btnBuscar.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				clearTable();
				LinkedList<Cancion> canciones;
				try {
					canciones = (LinkedList<Cancion>) CatalogoCanciones.getUnicaInstancia().getCanciones();
					LinkedList<Cancion> cancionesFiltradas = aplicarFiltro(canciones);
					for (Cancion cancion : cancionesFiltradas) {
						modelo.addRow(new Object[] { cancion.getTitulo(), cancion.getInterprete(), cancion.getGenero(),
								cancion.getNumReproducciones() });
					}

				} catch (DAOException e1) {
					e1.printStackTrace();
				}
			}
		});
	}

	public void addManejadorBotonBuscar2(JButton btnBuscar) {
		btnBuscar.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				clearTable2();
				LinkedList<Cancion> canciones;
				try {
					canciones = (LinkedList<Cancion>) CatalogoCanciones.getUnicaInstancia().getCanciones();
					LinkedList<Cancion> cancionesFiltradas = aplicarFiltro2(canciones);
					for (Cancion cancion : cancionesFiltradas) {
						modelo2.addRow(new Object[] { cancion.getTitulo(), cancion.getInterprete(), cancion.getGenero(),
								cancion.getNumReproducciones() });
					}

				} catch (DAOException e1) {
					e1.printStackTrace();
				}
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

	public void addManejadorBotonCrearPlaylist(JButton btnCrear,JScrollPane tablaConScroll, JScrollPane tablaPlaylistConScroll, JPanel panelNuevaListaIzquierda, JPanel botones, JPanel panelNuevaListarPlaylist, JPanel panelCrearPlaylist ) {
		btnCrear.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {

				// Hacerlo con objetos o con la bd?
				boolean existe = false;
				List<ListaCanciones> listasUsuario = usuarioActual.getListasCanciones();
				for (ListaCanciones lista : listasUsuario) {
					if (lista.getNombre().equals(nombrePlaylist.getText())) {
						existe = true;
						controlador.setListaActual(lista);
						break;
					}
				}

				if (existe) {
					ListaCanciones listaActual = controlador.getListaActual();
					try {
						for (Cancion cancion : CatalogoCanciones.getUnicaInstancia().getCanciones()) {
							System.out.println(cancion.getId());
							System.out.println(cancion.getTitulo());
							System.out.println(cancion.getInterprete());
							if(!listaActual.contiene(cancion))
								modelo2.addRow(new Object[] { cancion.getTitulo(), cancion.getInterprete(), cancion.getGenero(),
									cancion.getNumReproducciones() });
						}
					} catch (DAOException e1) {
						e1.printStackTrace();
					}
					for (Cancion cancion : listaActual.getListaCanciones()) {
						modeloPlaylist.addRow(
								new Object[] { cancion.getTitulo(), cancion.getInterprete(), cancion.getGenero() });
					}

				} else {
					int reply = JOptionPane.showConfirmDialog(null, "¿Desea crear una nueva lista?",
							"Crear nueva lista", JOptionPane.YES_NO_OPTION);
					if (reply == JOptionPane.YES_OPTION) {
						JOptionPane.showMessageDialog(frmVentanaPrincipal, "Lista creada correctamente",
								"Crear lista", JOptionPane.PLAIN_MESSAGE);


						usuarioActual.getListaCancionesTitulo(getTitulo());
						controlador.crearListaCanciones();
						controlador.getListaActual().setNombre(nombrePlaylist.getText());
						controlador.getListaActual().setUsuario(usuarioActual);


					} else {
						// JOptionPane.showMessageDialog(null, "GOODBYE");
					}
				}
				nombrePlaylist.setText("");

			}
		});
	}

	public void addManejadorBotonAddCancionPlaylist(JButton btnAdd) {
		btnAdd.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				ListaCanciones playList = controlador.getListaActual();
				int indice = tabla2.getSelectedRow();
				String titulo = (String) tabla2.getValueAt(tabla2.getSelectedRow(), 0);
				Cancion cancion = CatalogoCanciones.getUnicaInstancia().getCancion(titulo);

				modelo2.removeRow(indice);
				modeloPlaylist
						.addRow(new Object[] { cancion.getTitulo(), cancion.getInterprete(), cancion.getGenero() });
				playList.addCancion(cancion);

			}
		});
	}

// xd
	public void addManejadorBotonRemoveCancionPlaylist(JButton btnRemove) {
		btnRemove.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// LinkedList<Cancion> canciones =
				// CatalogoCanciones.getUnicaInstancia().getCanciones();

				//LinkedList<Cancion> playList = new LinkedList<Cancion>();
				ListaCanciones playList = controlador.getListaActual();
				
				int indice = tablaPlaylist.getSelectedRow();
				if(indice != -1)
				{
					String titulo = (String) tablaPlaylist.getValueAt(tablaPlaylist.getSelectedRow(), 0);
					Cancion cancion = CatalogoCanciones.getUnicaInstancia().getCancion(titulo);

					modeloPlaylist.removeRow(indice);
					modelo2.addRow(new Object[] { cancion.getTitulo(), cancion.getInterprete(), cancion.getGenero(),
							cancion.getNumReproducciones() });
					playList.removeCancion(cancion);
				}


			}
		});
	}

	public void addManejadorBotonAceptarPlaylist(JButton btnAceptar) {
		btnAceptar.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				ListaCanciones listaActual = controlador.getListaActual();
				
				if(usuarioActual.getListasCanciones().contains(listaActual)) {
					controlador.actualizarListaCanciones();
				}
				else {
					controlador.registrarListaCanciones();
				}
				clearTablePlayList();
				clearTable2();
				

			}
		});
	}


	public void addManejadorBotonTop10(JButton btnTop10) {
		btnTop10.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				clearTablaTop10();
				List<Cancion> listaTop10 = controlador.getCancionesTop();
				for (Cancion cancion : listaTop10) {
					System.out.println("MANEJADOR TOP 10");
					System.out.println(cancion.getTitulo());
					System.out.println(cancion.getInterprete());
					System.out.println(cancion.getNumReproducciones());
					modeloTop10.addRow(new Object[] { cancion.getTitulo(), cancion.getInterprete(),
							cancion.getNumReproducciones() });
				}
				
				controlador.setTablaActual(tablaTop10);
				revalidarPanelMedio(panelTop10);
			}
		});
	}

	public void addManejadorBotonMisListas(JButton btnMisListas) {
		btnMisListas.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (modeloLista.getSize() == 0 || modeloLista.getSize() < usuarioActual.getListasCanciones().size()) {
					clearLista();
					listaPlaylist.setVisible(true);
					for (ListaCanciones lista : usuarioActual.getListasCanciones()) {
						modeloLista.addElement(lista.getNombre());
					}
				}

			}
		});
	}


	public void addManejadorBotonRecientes(JButton btnRecientes) {
		btnRecientes.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				clearTablaRecientes();
				for (Cancion cancion : usuarioActual.getListaRecientes()) {
					modeloRecientes.addRow(new Object[] { cancion.getTitulo(), cancion.getInterprete() });
				}

				controlador.setTablaActual(tablaRecientes);
				revalidarPanelMedio(panelRecientes);
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
		contentPane.setLayout(new BorderLayout(0, 0));

		crearPanelNorte();

		crearPanelMedio();

		crearPanelExplorarNorte();

		crearPanelExplorarNorte2();
		crearPanelExplorarTabla();
		crearPanelNuevaListaTablas(); // Dos listas juntas
		crearPanelExploraSur();

		// JPanel panelExplorarCentro = crearPanelExplorarCentro();
		crearPanelExplorar();
		crearPanelNuevaListaSur();
		crearPanelNorteCentro(); // panel nueva lista
		// JPanel panelExplorar2 = crearPanelExplorar(panelExplorarCentro);
		// Cambiar
		crearPanelBotones();
		
		crearPanelMisListas();
		crearPanelRecientes();
		crearPanelTop10();

		frmVentanaPrincipal.pack();
	}

}
