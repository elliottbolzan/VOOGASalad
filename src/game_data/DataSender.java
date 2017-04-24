package game_data;

import java.io.File;
import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

public class DataSender {

	private Unpackager unzip;
	private Game currentGame;
	
	public DataSender(Game analyzedGame){
		currentGame=analyzedGame;
		unzip= new Unpackager();
		System.out.println("datasender");
		System.out.println(currentGame.getCurrentPath());
		System.out.println(new File(currentGame.getCurrentPath()).getParentFile().getAbsolutePath());
		extractDocument();
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
		System.out.println("4mariosextraction");
		Document doc = null;
		try{
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder docBuilder = factory.newDocumentBuilder();
		doc = docBuilder.parse(currentGame.getCurrentPath() + File.separator + "analytics.xml");
		} catch (Exception e){
			e.printStackTrace();
		}
		
		NodeList playsNode = doc.getElementsByTagName("Plays");	
		System.out.println(playsNode.item(0).getAttributes().item(0).getNodeValue());
	
		
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
