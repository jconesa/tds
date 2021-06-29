package tds.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;

import tds.controlador.Controlador;
//import tds.controlador.Controlador;
import tds.dao.DAOException;
import tds.dominio.Cancion;
import tds.dominio.CatalogoCanciones;
import tds.dominio.ListaCanciones;
import tds.dominio.Usuario;

public class PanelPlaylist extends JPanel {

    private static JTextField nombrePlaylist;

    private JFrame frmVentanaPrincipal;
	private DefaultTableModel modelo, modeloPlaylist;
	JTable tabla, tablaPlaylist, panelNuevaListaPlaylist;
    private VentanaPrincipal ventana;
    JPanel panelNuevaListaTablas, panelNuevaListaIzquierda, panelNuevaListarPlaylist, panelCrearPlaylist, botones;
    JButton crearPlaylist, addCancion, quitarCancion;
    JScrollPane tablaConScroll, tablaPlaylistConScroll;
	private Usuario usuarioActual = Controlador.getUnicaInstancia().getUsuarioActual();

    
    
    public void PanelCrearPlaylist(VentanaPrincipal v) {
    	ventana= v;
    	crearPantalla();
    }
	
    private JPanel crearPantalla() {
    	botones = new JPanel();
	    botones.setLayout(new BoxLayout(botones, BoxLayout.Y_AXIS));
    	
	    panelCrearPlaylist = new JPanel();
	    nombrePlaylist= new JTextField();
        nombrePlaylist.setColumns(8);
 
        crearPlaylist = new JButton("Crear");
        crearPlaylist.setMinimumSize(new Dimension(100, 20));
        crearPlaylist.setBackground(Color.WHITE);
       
        panelCrearPlaylist.add(crearPlaylist);

        addCancion = new JButton(">>");
        addCancion.setMinimumSize(new Dimension(100, 20));
        addCancion.setBackground(Color.WHITE);
        botones.add(addCancion);
        
        quitarCancion = new JButton("<<");
        quitarCancion.setMinimumSize(new Dimension(100, 20));
        quitarCancion.setBackground(Color.WHITE);
        botones.add(quitarCancion);
        
        crearTabla();
        tablaConScroll= new JScrollPane(tabla);
        panelNuevaListaIzquierda = new JPanel();
		panelNuevaListaIzquierda.add(tablaConScroll);
		
		crearTablaPlaylist();
		tablaPlaylistConScroll= new JScrollPane(tablaPlaylist);
		panelNuevaListaPlaylist = new JTable();
		panelNuevaListarPlaylist.add(tablaPlaylistConScroll);
		
		panelNuevaListaTablas = new JPanel();
		panelNuevaListaTablas.add(panelNuevaListaIzquierda, BorderLayout.WEST);
        panelNuevaListaTablas.add(botones, BorderLayout.CENTER);
		panelNuevaListaTablas.add(panelNuevaListarPlaylist, BorderLayout.EAST);
        panelNuevaListaTablas.add(panelCrearPlaylist, BorderLayout.SOUTH);
        
        addManejadorBotonCrearPlaylist(crearPlaylist);
		return panelNuevaListaTablas;
    }
    
	public void crearTablaPlaylist() {
		tablaPlaylist= new JTable();
		//tabla.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		tablaPlaylist.setCellSelectionEnabled(false);
		tablaPlaylist.setShowGrid(true);
		tablaPlaylist.setShowVerticalLines(true);
		tablaPlaylist.setGridColor(Color.gray);

		modeloPlaylist = new DefaultTableModel() {
			private String[] columnNames = {"Titulo","Interprete", "Genero"};
			public String getColumnName(int column) {
			    return columnNames[column];
			}
		    public int getColumnCount() {return 3;}
		    public boolean isCellEditable(int row, int col){ return false;}
		};
		tablaPlaylist.setModel(modeloPlaylist);
	
		//Se inicializa por defecto con todas las canciones, no con las deseadas
		
			try {
				for(Cancion cancion : CatalogoCanciones.getUnicaInstancia().getCanciones()) {;
				modeloPlaylist.addRow(new Object[]{cancion.getTitulo(), cancion.getInterprete(), cancion.getGenero()});
				}
			} catch (DAOException e) {
			// TODO Auto-generated catch block
				e.printStackTrace();
			}
	

		}
	
	private void crearTabla() {
		tabla= new JTable();
		//tabla.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		tabla.setCellSelectionEnabled(false);
		tabla.setShowGrid(true);
		tabla.setShowVerticalLines(true);
		tabla.setGridColor(Color.gray);

		modelo = new DefaultTableModel() {
			private String[] columnNames = {"Titulo","Interprete", "Genero", "Reproducciones"};
			public String getColumnName(int column) {
			    return columnNames[column];
			}
		    public int getColumnCount() {return 4;}
		    public boolean isCellEditable(int row, int col){ return false;}
		};
		tabla.setModel(modelo);

		try {
			for(Cancion cancion : CatalogoCanciones.getUnicaInstancia().getCanciones()) {
				System.out.println(cancion.getId());
				System.out.println(cancion.getTitulo());
				System.out.println(cancion.getInterprete());
				modelo.addRow(new Object[]{cancion.getTitulo(), cancion.getInterprete(), cancion.getGenero(), cancion.getNumReproducciones()});
			}
		} catch (DAOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		TableColumn columna = tabla.getColumn("Titulo"); 

		columna = tabla.getColumn("Interprete"); 

		columna = tabla.getColumn("Genero"); 

		columna = tabla.getColumn("Reproducciones"); 


	}
	
	public void cargarPlaylist() {
	//	List<Playlist> listaPlaylist = Controlador.getUnicaInstancia().getPlaylist();
	//for
	}
	
	public void addManejadorBotonCrearPlaylist(JButton btnCrear) {
        btnCrear.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	// Hacerlo con objetos o con la bd?
            	boolean existe = false;
            	List<ListaCanciones> listasUsuario = usuarioActual.getListasCanciones();
            	for(ListaCanciones lista : listasUsuario) {
            		if(lista.getNombre().equals(nombrePlaylist.getText())){
            			existe = true;
            		}
            	}
            	
            	if(existe) {
            		
            	} else {
            		int reply = JOptionPane.showConfirmDialog(null, "¿Desea crear una nueva lista?", "Crear nueva lista", JOptionPane.YES_NO_OPTION);
            		if (reply == JOptionPane.YES_OPTION) {
            			Controlador.getUnicaInstancia().registrarListaCanciones(nombrePlaylist.getText(), usuarioActual.getLogin());
     //       			JOptionPane.showMessageDialog(frmVentanaPrincipal,
     //       					"Venta registrada correctamente",
     //       					"Crear venta",JOptionPane.PLAIN_MESSAGE);
            			//while(modelo.getRowCount()>0) modelo.removeRow(0);
            			//unidades.setText("");
            			//dni.setText("");
            			Controlador.getUnicaInstancia().crearListaCanciones();
            		} else {
            		    //JOptionPane.showMessageDialog(null, "GOODBYE");
            		}
            	}
                
            }
        });
    }
}
