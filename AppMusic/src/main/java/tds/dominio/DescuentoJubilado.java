package tds.dominio;

public class DescuentoJubilado implements Descuento {	
	@Override
	public double calcDescuento(double precio) {
		return precio * 0.5;
	}

}
