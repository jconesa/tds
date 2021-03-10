/*package tds.dominio;
import java.io.File;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

public class Reproductor{{

	new Reproductor() {
		
	};
	// activar reproductor
	try {
	com.sun.javafx.application.PlatformImpl.startup(()->{});
	} catch(Exception ex) {
	ex.printStackTrace();
	System.out.println("Exception: " + ex.getMessage());
	 }

	// reproducir una canción
	   @Override
	   public void start(Stage stage) throws Exception {
	       Media media = new Media("file:///Movies/test.mp3"); //replace /Movies/test.mp3 with your file
	       MediaPlayer player = new MediaPlayer(media); 
	       player.play();
	   } 
	public Cancion playSong() {
		//String fileName = cancion.getRutaFichero();
		File f = new File("D:\\cancionesTDS\\Coldplay-Viva_La_Vida");
		Media hit = new Media(f.toURI().toString());
		MediaPlayer mediaPlayer = new MediaPlayer(hit);
		mediaPlayer.play();
	}
	

}}*/
