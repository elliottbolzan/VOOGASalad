package data;

import java.io.File;

public class PathCreator {

	
	
	
	
	
	
	public String getSongPath(String gameName){
		System.out.println("resources" + File.separator + gameName + ".mp3");
		return "resources" + File.separator + gameName + ".mp3";
	}
	
	public String getImagePath(String entityName){
		System.out.println("resources" + File.separator + entityName + "Image.png");
		return "resources" + File.separator + entityName + "Image.png";
	}
	
	
	public String getAbsolutePath(String gameFolderPath, String relativePath){
		
		System.out.println(gameFolderPath + File.separator + relativePath);
		return gameFolderPath + File.separator + relativePath ;
		
	}
}


