package tds.dominio;

public class DescuentoJoven implements Descuento {

	@Override
	public double calcDescuento() {
		return precio * 0.8;
	}

}
