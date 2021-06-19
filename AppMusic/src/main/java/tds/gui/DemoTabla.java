package tds.gui;

import java.awt.Dimension;
import java.util.LinkedList;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

import tds.dao.DAOException;
import tds.dominio.Cancion;
import tds.dominio.CatalogoCanciones;

public class DemoTabla extends JPanel {
	private boolean DEBUG = true;

	public DemoTabla() {
		// Crea una JTable
		JTable tabla = new JTable(new MiTableModel());
		DefaultTableModel model = (DefaultTableModel) tabla.getModel();
		tabla.setPreferredScrollableViewportSize(new Dimension(1000, 400));
		tabla.setFillsViewportHeight(true);
		// Crea un scroll y le añade la tabla.
		JScrollPane scrollPane = new JScrollPane(tabla);
		model.addColumn("Col1");
		model.addColumn("Col2");

		try {
			for(Cancion cancion : CatalogoCanciones.getUnicaInstancia().getCanciones()) {
				model.addRow(new Object[]{cancion.getTitulo(), cancion.getInterprete()});
			}
		} catch (DAOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		add(scrollPane);
	}

	// Clase interna que implementa AbstractTableModel
	class MiTableModel extends DefaultTableModel {
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		private String[] columnNames = { "Título", "Apellido"};
		/*private Object[][] data = {
	 {"Cati", "Molina",
	 "Esqui", new Integer(18), new Boolean(false)},
	 {"Juan", "Diaz",
	 "Futbol", new Integer(22), new Boolean(true)},
	 {"Susana", "Blanco",
	 "Voleibol", new Integer(21), new Boolean(false)},
	 {"Juana", "Pérez",
	 "Natación", new Integer(22), new Boolean(true)},
	 {"Luis", "Moreno",
	 "Tenis", new Integer(19), new Boolean(false)}
	 };*/
	
		public int getColumnCount() {
		 return columnNames.length;
		}
	
		/*public int getRowCount() {
			return data.length;
		}*/

		public String getColumnName(int col) {
			return columnNames[col];
		}
	
		public Object getValueAt(int row, int col) {
			return data[row][col];
		}
		@Override
		public Object getValueAt(int row, int column)
		{
		    Cancion cancion = getPerson(row);
		 
		    switch (column)
		    {
		        case 0: return person.getFirstName();
		        case 1: return person.getLastName();
		        case 2: return person.getBirthDate();
		        default: return null;
		    }
		}
		/*
		 * JTable usa este método para determinar el editor por defecto para cada celda.
		 * Si no se implementa la última columna mostrará true/false en vez de un check
		 * box.
		 */
		public Class getColumnClass(int c) {
			return getValueAt(0, c).getClass();
		}
	
		/*
		 * Este método no se implementa salvo que la tabla sea editable
		 */
		public boolean isCellEditable(int fila, int col) {
			if (col < 2) {
				return false;
			} else {
				return true;
			}
		}

	
		/*
		 * Este método no se implementa salvo que el contenido de la tabla pueda
		 * cambiar, lo que se notifica.
		 */
		public void setValueAt(Object valor, int fila, int col) {
				 if (DEBUG) {
					 System.out.println("Cambiar valor en " + fila + "," + col
					 + " a " + valor
					 + " (una instancia de "
					 + valor.getClass() + ")");
					 }
					 data[fila][col] = valor;
					 fireTableCellUpdated(fila, col);
				 if (DEBUG) {
					 System.out.println("Nuevo valor:");
					 printDebugData();
				 }
			}
		private void printDebugData() {
			 int numRows = getRowCount();
			 int numCols = getColumnCount();
			 for (int i=0; i < numRows; i++) {
				 System.out.print(" fila " + i + ":");
				 for (int j=0; j < numCols; j++) {
					 System.out.print(" " + data[i][j]);
				 }
				 System.out.println();
			 }
			 System.out.println("--------------------------");
		}

		@Override
		public int getRowCount() {
			// TODO Auto-generated method stub
			return 0;
		}
		}
}
	
/*public class ModeloTablaCanciones extends AbstractTableModel {
	private LinkedList<Cancion> listaCancionesFiltradas;
	private String[] nombreColumnas = {"Título", "Intérprete"};
	private Object[][] datos;
	
	for(Cancion cancion: listaCancionesFiltradas) {
		
	}
		

		@Override
		public int getColumnCount() {
			return nombreColumnas.length;
		}
	}
}*/
