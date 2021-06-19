package tds.dominio;
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
	  /* @Override
	   public void start(Stage stage) throws Exception {
	       Media media = new Media("file:///Movies/test.mp3"); //replace /Movies/test.mp3 with your file
	       MediaPlayer player = new MediaPlayer(media); 
	       player.play();
	   } */
	private void Cancion playSong(String url) {
		com.sun.javafx.application.PlatformImpl.startup(() -> {});
		uri = new URL(url);
		System.setProperty("java.io.tmpdir", tempPath);
		Path mp3 = Files.createTempFile("now-playing", ".mp3");
		System.out.println(mp3.getFileName());
		try (InputStream stream = uri.openStream()) {
			Files.copy(stream, mp3, StandardCopyOption.REPLACE_EXISTING);
		 }
		System.out.println("finished-copy: " + mp3.getFileName());
		Media media = new Media(mp3.toFile().toURI().toString());
		mediaPlayer = new MediaPlayer(media);

		mediaPlayer.play(); 
	}
	

}}
