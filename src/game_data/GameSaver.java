package game_data;

import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;
import engine.Entity;
import engine.game.Level;

// Make references to paths relative. 
public class GameSaver {

	private Game game;
	private GameXMLFactory gameXMLFactory;

	/** 
	 * Main method to save the entire game to the selected file path.
	 * Creates the XML factory factory that adds nodes to the XML file.
	 * Calls the helper methods that save each individual part of the XML file.
	 * 
	 */
	public void saveGame(Game game, String filepath) {
		this.game = game;
		gameXMLFactory = new GameXMLFactory();
		gameXMLFactory.setName(game.getName());
		createRoot(filepath);
		saveLevels(game.getLevels(), filepath + File.separator + game.getName());
		saveDefaults(game.getDefaults(), filepath + File.separator + game.getName());
		saveSong(filepath + File.separator + game.getName(), game.getSongPath());
		saveDocument(filepath);
	}

	private void createRoot(String filePath) {
		File folder = new File(filePath + File.separator + game.getName());
		if (!folder.exists()) {
			folder.mkdirs();
		}
	}

	private void saveSong(String filePath, String songPath) {
		if (songPath.equals("")) {
			return;
		}
		try {
			String updated = new File(songPath).getAbsolutePath();
			Path sourcePath = Paths.get(updated);
			String relative = "resources" + File.separator + game.getName() + ".mp3";
			game.setSongPath(relative);
			File file = new File(filePath + File.separator + relative);
			file.getParentFile().mkdirs();
			file.createNewFile();
			Path targetPath = Paths.get(filePath + File.separator + relative);
			Files.copy(sourcePath, targetPath, REPLACE_EXISTING);
			gameXMLFactory.addSong(relative);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void saveEntityImage(Entity entity, String filePath) {
		try {
			String sourcePathString = new File(new URI(entity.getImagePath())).getAbsolutePath();
			Path sourcePath = Paths.get(sourcePathString);
			String targetPathString = "resources" + File.separator + entity.getName() + "Image.png";
			entity.setImagePath(targetPathString);
			File entityImageFile = new File(filePath + File.separator + targetPathString);

			entityImageFile.getParentFile().mkdirs();
			entityImageFile.createNewFile();

			Path targetPath = Paths.get(filePath + File.separator + targetPathString);
			Files.copy(sourcePath, targetPath, REPLACE_EXISTING);

		} catch (Exception i) {
			// i.printStackTrace();
		}
	}

	private void saveDefaults(List<Entity> defaults, String filePath) {

		List<Entity> entities = defaults;
		List<Element> entityNodes = new ArrayList<Element>();

		for (int j = 0; j < entities.size(); j++) {
			Entity currEntity = entities.get(j);

			Element entityNode = getEntityNode(currEntity, filePath);
			entityNodes.add(entityNode);
		}
		LevelSaver ls = new LevelSaver(entityNodes);
		String xmlLevel = ls.saveLevel();
		// System.out.println(xmlLevel);
		Element levelElement = gameXMLFactory.stringToElement(xmlLevel);
		gameXMLFactory.addDefaultEntity(levelElement);
		// System.out.println(xmlLevel);

	}

	private void saveLevels(List<Level> levels, String filePath) {
		for (int i = 0; i < levels.size(); i++) {
			List<Entity> entities = new ArrayList<Entity>(levels.get(i).getEntities());
			List<Element> entityNodes = new ArrayList<Element>();

			for (int j = 0; j < entities.size(); j++) {
				Entity currEntity = entities.get(j);
				Element entityNode = getEntityNode(currEntity, filePath);
				entityNodes.add(entityNode);
			}

			LevelSaver ls = new LevelSaver(entityNodes);
			String xmlLevel = ls.saveLevel();
			// System.out.println(xmlLevel);
			Element levelElement = gameXMLFactory.stringToElement(xmlLevel);
			gameXMLFactory.addLevel(levelElement);
			// System.out.println(xmlLevel);
		}
	}

	private Element getEntityNode(Entity entity, String folderPath) {
		String tempImagePath = entity.getImagePath();
		saveEntityImage(entity, folderPath);
		XStream xStream = new XStream(new DomDriver());
		xStream.registerConverter(new EntityConverter());
		String xmlString = xStream.toXML(entity);
		entity.setImagePath(tempImagePath);
		return gameXMLFactory.stringToElement(xmlString);
	}

	private void saveDocument(String filePath) {
		Document doc = gameXMLFactory.getDocument();
		File levelDirectory = new File(filePath);
		if (!levelDirectory.exists()) {
			levelDirectory.mkdirs();
		}
		try {
			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			DOMSource source = new DOMSource(doc);

			StreamResult result = new StreamResult(
					new File(filePath + File.separator + game.getName() + File.separator + "settings.xml"));
			transformer.transform(source, result);


			


			String outputFolder = "My Game.zip";
			ArrayList<String> fileList = new ArrayList<String>();
			byte[] buffer = new byte[1024];
			generateFileList(new File(filePath), fileList, filePath);

			FileOutputStream fos = null;
			ZipOutputStream zos = null;

			try {
				fos = new FileOutputStream(outputFolder);
				zos = new ZipOutputStream(fos);

				System.out.println("Output to Zip : " + outputFolder);
				FileInputStream in = null;

				for (String file: fileList) {
					System.out.println("File Added : " + file);
					ZipEntry ze = new ZipEntry(source + File.separator + file);
					zos.putNextEntry(ze);
					try {
						in = new FileInputStream(filePath + File.separator + file);
						int len;
						while ((len = in .read(buffer)) > 0) {
							zos.write(buffer, 0, len);
						}
					} finally {
						in.close();
					}
				}

				zos.closeEntry();
				System.out.println("Folder successfully compressed");

			} catch (IOException ex) {
				ex.printStackTrace();
			} finally {
				try {
					zos.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}



		} catch (TransformerException e) {
			e.printStackTrace();
		}

	}

	private void generateFileList(File node, ArrayList<String> fileList, String filePath) {
		// add file only
		if (node.isFile()) {
			fileList.add(generateZipEntry(node.toString(), filePath));
		}

		if (node.isDirectory()) {
			String[] subNote = node.list();
			for (String filename: subNote) {
				generateFileList(new File(node, filename), fileList, filePath);
			}
		}
	}
	
	private String generateZipEntry(String file, String filePath) {
        return file.substring(filePath.length() + 1, file.length());
    }

}




//	public class ZipUtils {
//
//	    private List <String> fileList;
//	    private static final String OUTPUT_ZIP_FILE = "Folder.zip";
//	    private static final String SOURCE_FOLDER = "D:\\Reports"; // SourceFolder path
//
//	    public ZipUtils() {
//	        fileList = new ArrayList < String > ();
//	    }
//
//	    public static void main(String[] args) {
//	        ZipUtils appZip = new ZipUtils();
//	        appZip.generateFileList(new File(SOURCE_FOLDER));
//	        appZip.zipIt(OUTPUT_ZIP_FILE);
//	    }
//
//	    public void zipIt(String zipFile) {
//	        byte[] buffer = new byte[1024];
//	        String source = new File(SOURCE_FOLDER).getName();
//	        FileOutputStream fos = null;
//	        ZipOutputStream zos = null;
//	        try {
//	            fos = new FileOutputStream(zipFile);
//	            zos = new ZipOutputStream(fos);
//
//	            System.out.println("Output to Zip : " + zipFile);
//	            FileInputStream in = null;
//
//	            for (String file: this.fileList) {
//	                System.out.println("File Added : " + file);
//	                ZipEntry ze = new ZipEntry(source + File.separator + file);
//	                zos.putNextEntry(ze);
//	                try {
//	                    in = new FileInputStream(SOURCE_FOLDER + File.separator + file);
//	                    int len;
//	                    while ((len = in .read(buffer)) > 0) {
//	                        zos.write(buffer, 0, len);
//	                    }
//	                } finally {
//	                    in.close();
//	                }
//	            }
//
//	            zos.closeEntry();
//	            System.out.println("Folder successfully compressed");
//
//	        } catch (IOException ex) {
//	            ex.printStackTrace();
//	        } finally {
//	            try {
//	                zos.close();
//	            } catch (IOException e) {
//	                e.printStackTrace();
//	            }
//	        }
//	    }
//
//	    public void generateFileList(File node) {
//	        // add file only
//	        if (node.isFile()) {
//	            fileList.add(generateZipEntry(node.toString()));
//	        }
//
//	        if (node.isDirectory()) {
//	            String[] subNote = node.list();
//	            for (String filename: subNote) {
//	                generateFileList(new File(node, filename));
//	            }
//	        }
//	    }
//
//	    private String generateZipEntry(String file) {
//	        return file.substring(SOURCE_FOLDER.length() + 1, file.length());
//	    }
//	}