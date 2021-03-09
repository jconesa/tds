package tds.dominio;
import java.io.File;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
public class Reproductor {

	// activar reproductor
	try {
	com.sun.javafx.application.PlatformImpl.startup(()->{});
	} catch(Exception ex) {
	ex.printStackTrace();
	System.out.println("Exception: " + ex.getMessage());
	 }
	// reproducir una canción
	String fileName = cancion.getRutaFichero();
	File f = new File("C:\\tds\\canciones\\"+fileName);
	Media hit = new Media(f.toURI().toString());
	mediaPlayer = new MediaPlayer(hit);
	mediaPlayer.play()
}
