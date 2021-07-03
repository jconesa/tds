package tds.dominio;
import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLDecoder;
import java.nio.file.Path;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
public class Repro {
	
	private MediaPlayer mediaPlayer;
	private String binPath;
	private String tempPath;
	
	public Repro() {
		initialize();
	}

	public void playCancion(String ruta) {
		System.out.println(ruta);
			com.sun.javafx.application.PlatformImpl.startup(() -> {
			});
			
			File file = new File(ruta);
			Path mp3 = file.toPath();
			
			System.out.println("--------------------------------");
			System.out.println("Repro");
			System.out.println(mp3);
			System.out.println(mp3);
			Media media = new Media(mp3.toFile().toURI().toString());
			mediaPlayer = new MediaPlayer(media);
			
			mediaPlayer.play();
		

			
	}
	public void stopCancion() {
		if(mediaPlayer != null)
			mediaPlayer.stop();
	}

	private void initialize() {
		mediaPlayer = null;
		binPath = Repro.class.getClassLoader().getResource(".").getPath();
		URL resource = ClassLoader.getSystemResource(".");
		try {
			binPath = URLDecoder.decode(resource.getFile(), "UTF-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		binPath = binPath.replaceFirst("/", "");
		binPath = binPath.replace("/", "\\");
		// quitar "/" añadida al inicio del path en plataforma Windows
		tempPath = binPath.replace("\bin", "\temp");
		

		System.out.println(binPath);
		System.out.println(tempPath);
	}

}
