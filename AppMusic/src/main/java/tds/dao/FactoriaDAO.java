package tds.dao;

import tds.dao.FactoriaDAO;

/**
 * Factoria abstracta DAO.
 */

public abstract class FactoriaDAO {
	
	public static final String DAO_TDS = "tds.dao.TDSFactoriaDAO";

	private static FactoriaDAO unicaInstancia = null;
	
	/** 
	 * Crea un tipo de factoria DAO.
	 * Solo existe el tipo TDSFactoriaDAO
	 */
	@SuppressWarnings("deprecation")
	public static FactoriaDAO getInstancia(String tipo) throws DAOException{
		if (unicaInstancia == null)
			try { 
				unicaInstancia=(FactoriaDAO) Class.forName(tipo).newInstance();
			} catch (Exception e) {	
				throw new DAOException(e.getMessage());
		} 
		return unicaInstancia;
	}
	

	public static FactoriaDAO getInstancia() throws DAOException{
		if(unicaInstancia == null)
			return getInstancia(FactoriaDAO.DAO_TDS);
		return unicaInstancia;
	}

	protected FactoriaDAO (){}
	
	// Metodos factoria para obtener adaptadores
	
	public abstract UsuarioDAO getUsuarioDAO();	
	public abstract TDSCancionDAO getCancionDAO();	
	public abstract TDSListaCancionesDAO getListaCancionesDAO();
}
