package starter.exporter;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.jar.Attributes;
import java.util.jar.JarEntry;
import java.util.jar.JarOutputStream;
import java.util.jar.Manifest;

/**
 * @author Elliott Bolzan
 *
 */
public class Exporter {
	
	private final static String MAIN_CLASS = "player.StandaloneLoader";

	/**
	 * 
	 */
	public Exporter() {
		selectClasses();
		try {
			copy();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void selectClasses() {
		
	}
	

	private void copy() throws IOException {
		JarOutputStream target = new JarOutputStream(new FileOutputStream("Game.jar"), createManifest());
		copyClasses(target);
		copyLibs(target);
		copyGame(target);
		target.close();
	}
	
	private Manifest createManifest() {
		Manifest manifest = new Manifest();
		manifest.getMainAttributes().put(Attributes.Name.MANIFEST_VERSION, "1.0");
		manifest.getMainAttributes().put(Attributes.Name.MAIN_CLASS, MAIN_CLASS);
		manifest.getMainAttributes().put(Attributes.Name.CLASS_PATH, ".");
		return manifest;
	}
	
	private void copyClasses(JarOutputStream target) throws IOException {
		add(new File("bin/"), target);
	}
	
	private void copyLibs(JarOutputStream target) throws IOException {
		// start at lib
		// recursively go through:
			// if jar, unpack and add to jar
	}
	
	private void copyGame(JarOutputStream target) throws IOException {

	}

	// Factor out.
	private void add(File source, JarOutputStream target) throws IOException {
		BufferedInputStream in = null;
		try {
			if (source.isDirectory()) {
				String name = source.getPath().replace("\\", "/");
				if (!name.isEmpty()) {
					if (!name.endsWith("/"))
						name += "/";
					JarEntry entry = new JarEntry(name);
					entry.setTime(source.lastModified());
					target.putNextEntry(entry);
					target.closeEntry();
				}
				for (File nestedFile : source.listFiles()) {
					add(nestedFile, target);
				}
				return;
			}

			JarEntry entry = new JarEntry(source.getPath().replace("bin/", "").replace("\\", "/"));
			entry.setTime(source.lastModified());
			target.putNextEntry(entry);
			in = new BufferedInputStream(new FileInputStream(source));

			byte[] buffer = new byte[1024];
			while (true) {
				int count = in.read(buffer);
				if (count == -1)
					break;
				target.write(buffer, 0, count);
			}
			target.closeEntry();
		} finally {
			if (in != null)
				in.close();
		}
	}

}
