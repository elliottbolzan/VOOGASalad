package game_data;

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
	}
	
	
	
	
	
