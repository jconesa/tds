package tds.dominio;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLDecoder;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JTextField;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
public class Repro {
	
	private JFrame frmReproductorDeCanciones;
	private JTextField textURL;
	private MediaPlayer mediaPlayer;
	private String binPath;
	private String tempPath;
	private JCheckBox playXML;
	
	public Repro() {
		initialize();
	}
	public void playCancion(String url) {
		URL uri = null;
		System.out.println("NO NULL");

		try {
			com.sun.javafx.application.PlatformImpl.startup(() -> {
			});

			uri = new URL(url);
			System.setProperty("java.io.tmpdir", tempPath);
			System.out.println(System.getProperty("java.io.tmpdir"));
			Path mp3 = Files.createTempFile("now-playing", ".mp3");

			System.out.println(mp3.getFileName());
			try (InputStream stream = uri.openStream()) {
				Files.copy(stream, mp3, StandardCopyOption.REPLACE_EXISTING);
			}
			System.out.println("finished-copy: " + mp3.getFileName());
			System.out.println("NO NULL");
			System.out.println(mp3.toFile().toURI().toString());

			Media media = new Media(mp3.toFile().toURI().toString());
			mediaPlayer = new MediaPlayer(media);
			
			mediaPlayer.play();
		} catch (MalformedURLException e1) {
			e1.printStackTrace();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}
	
	public void stopCancion() {
		if (mediaPlayer != null)
			mediaPlayer.stop();
		File directorio = new File(tempPath);

		String[] files = directorio.list();
		for (String archivo : files) {
			if(archivo.matches(".*mp3"))
			{
				File fichero = new File(tempPath + File.separator + archivo);
				System.out.println(fichero.getName());
				fichero.delete();
			}

			//fichero.delete();
		 }
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
		
		//binPath = "C:\\Users\\Joaquín\\Desktop\\Uni\\Canciones";
		//tempPath = "C:\\Users\\Joaquín\\Desktop\\Uni\\Canciones";
		System.out.println(binPath);
		System.out.println(tempPath);
	}

}
