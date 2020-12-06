package tds.dominio;

public class DescuentoJoven implements Descuento {

	@Override
	public double calcDescuento(double precio) {
		return precio * 0.8;
	}

}
