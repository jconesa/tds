package tds.dominio;

public class DescuentoSuperAfortunado implements Descuento {	
	@Override
	public double calcDescuento() {
		return precio * 0.7;
	}
}
