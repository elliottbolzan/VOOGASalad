package data;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ResourceManager {

	private final static String DEFAULT_RESOURCE_PACKAGE = "src" + File.separator + "resources";
	private final static String SYNTAX_PACKAGE = "XMLHeaders.properties";
	private final static String GAME_TITLE ="GameTitle";
	private final static String NAME_TITLE="NameTitle";
	private final static String LEVELS_TITLE = "LevelsTitle";
	private final static String DEFAULTS_TITLE = "DefaultsTitle";
	private final static String CAMERA_TITLE="CameraTitle";
	private final static String RESOURCE_TITLE= "ResourceTitle";
	private final static String NAME_ATTRIBUTE = "NameAttribute";
	private final static String LEVEL_ELEMENT = "LevelElement";
	private final static String SONG_RESOURCE_ELEMENT = "SongResourceElement";
	private final static String ENTITY_ELEMENT= "EntityElement";
	private final static String ENTITY_STATE = "EntityState";
	private final static String TEMP_DIR = "IOTempDir";
	private final static String VOOGA = "VoogaSalad";
	private final static String NUM_LIVES = "NumLives";
	private final static String UNLOCKED_LVLS = "UnlockedLvls";
	private final static String CURR_TIME = "CurrTime";
	private final static String TIME_DOWN = "TimeDown";
	private final static String GAME_INFO = "GameInfo";
	private final static String SAVE = "Save";
	private final static String FILE_NAME = "Settings";
	private final static String XML_FORMAT = "XMLFormat";
	private final static String XML = "XML";
	private final static String VS = "VS";
	private final static String MP3 = "MP3";

	private Properties prop;

	public ResourceManager(){
		initialize();
	}

	private void initialize(){
		prop = new Properties();
		InputStream input = null;
		try {
			input = new FileInputStream(DEFAULT_RESOURCE_PACKAGE + File.separator + SYNTAX_PACKAGE);
			prop.load(input);
		}catch (IOException e){
		}
	}

	public String getGameTitle(){
		return prop.getProperty(GAME_TITLE);
	}

	public String getNameTitle(){
		return prop.getProperty(NAME_TITLE);
	}

	public String getLevelsTitle(){
		return prop.getProperty(LEVELS_TITLE);
	}

	public String getDefaultsTitle(){
		return prop.getProperty(DEFAULTS_TITLE);
	}

	public String getCameraTitle(){
		return prop.getProperty(CAMERA_TITLE);
	}

	public String getResourceTitle(){
		return prop.getProperty(RESOURCE_TITLE);
	}

	public String getNameAttribute(){
		return prop.getProperty(NAME_ATTRIBUTE);
	}

	public String getLevelElement(){
		return prop.getProperty(LEVEL_ELEMENT);
	}

	public String getSongResourceElement(){
		return prop.getProperty(SONG_RESOURCE_ELEMENT);
	}

	public String getEntityElement(){
		return prop.getProperty(ENTITY_ELEMENT);
	}

	public String getEntityState(){
		return prop.getProperty(ENTITY_STATE);
	}

	public String getTempDir(){
		return prop.getProperty(TEMP_DIR);
	}

	public String getVoogaName(){
		return prop.getProperty(VOOGA);
	}

	public String getNumLives(){
		return prop.getProperty(NUM_LIVES);
	}

	public String getUnlockedLvls(){
		return prop.getProperty(UNLOCKED_LVLS);
	}

	public String getCurrTime(){
		return prop.getProperty(CURR_TIME);
	}

	public String getTimeDown(){
		return prop.getProperty(TIME_DOWN);
	}

	public String getGameInfo(){
		return prop.getProperty(GAME_INFO);
	}

	public String getSave(){
		return prop.getProperty(SAVE);
	}

	public String getFileName(){
		return prop.getProperty(FILE_NAME);
	}

	public String getXML(){
		return prop.getProperty(XML);
	}

	public String getVS(){
		return prop.getProperty(VS);
	}

	public String getXMLFormat(){
		return prop.getProperty(XML_FORMAT);
	}

	public String getMP3(){
		return prop.getProperty(MP3);
	}
}





