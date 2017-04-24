package game_data;

import java.io.File;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;

public class DataSender {

	private Unpackager unzip;
	private Game currentGame;
	
	public DataSender(Game analyzedGame){
		currentGame=analyzedGame;
		unzip= new Unpackager();
		
	}
	
	public void sendData(){
		try{
		String zipFolderPath =currentGame.getCurrentPath();
		String parentFolderPath = new File(zipFolderPath).getParentFile().getAbsolutePath();
		unzip.unzip(zipFolderPath, parentFolderPath);
		}catch( Exception e){
			e.printStackTrace();
		}
		
		extractDocument();
		
		incrementPlays();
		incrementWins();
		incrementLosses();
		addScoreDistribution(currentGame.getAnalytics().getScore());
		
		rezip();
	}
	
	private void rezip(){
		
	}
	
	private void extractDocument(){
		Document doc = null;
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder docBuilder = factory.newDocumentBuilder();
		doc = docBuilder.parse(newPath + File.separator + saveName);
	}
	
	private void incrementPlays(){
		
		
	}
	
	private void incrementWins(){
		
		
	}
	private void incrementLosses(){
		
	}
	private void addScoreDistribution(float score){
		
	}
}
