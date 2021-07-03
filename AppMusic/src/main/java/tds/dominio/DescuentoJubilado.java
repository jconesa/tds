package tds.dominio;

public class DescuentoJubilado implements Descuento {	
	@Override
	public double calcDescuento() {
		return precio * 0.7;
	}

}
