package tds.dominio;

public class DescuentoAfortunado implements Descuento {

	@Override
	public double calcDescuento() {
		return precio * 0.8;
	}

}
