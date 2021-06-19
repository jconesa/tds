package tds.dao;

import tds.dao.FactoriaDAO;
import tds.dao.TDSUsuarioDAO;

/** 
 * Factoria concreta DAO para el Servidor de Persistencia de la asignatura TDS.
 * 
 */

public final class TDSFactoriaDAO extends FactoriaDAO {
	
	public TDSFactoriaDAO() {	}
	
	@Override
	public TDSUsuarioDAO getUsuarioDAO() {	
		return new TDSUsuarioDAO(); 
	}

	@Override
	public TDSCancionDAO getCancionDAO() {
		// TODO Auto-generated method stub
		return new TDSCancionDAO();
	}

}
