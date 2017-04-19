package player;

import java.util.ResourceBundle;

import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import polyglot.Polyglot;

/**
 * 
 * @author Jesse
 *
 */
public class HighscoreMenu extends PlayerView {
	
	private TableView<Score> scoreTable;	
	private Loader loader;

	public HighscoreMenu(Stage stage, Loader loader, Polyglot polyglot, ResourceBundle IOResources) {
		super(polyglot, IOResources);
		this.loader = loader;
		setupScene();
		loadScores();
	}
	
	@SuppressWarnings("unchecked")
	private void setupScene(){
		scoreTable = new TableView<>();
		scoreTable.getColumns().setAll(makeRankColumn(), makeScoreColumn(), makeTimeColumn());
		this.setCenter(scoreTable);
	}
	
	private void loadScores(){
		scoreTable.setItems(loader.getScores());
	}
	
	private TableColumn<Score, Integer> makeRankColumn(){
		TableColumn<Score, Integer>	rank = new TableColumn<>("Rank");
		rank.setCellValueFactory(new PropertyValueFactory<>("rank"));
		
		return rank;
	}
	
	private TableColumn<Score, String> makeScoreColumn(){
		TableColumn<Score, String> score = new TableColumn<>("Score");
		score.setCellValueFactory(new PropertyValueFactory<Score, String>("score"));
		
		return score;
	}
	
	private TableColumn<Score, String> makeTimeColumn(){
		TableColumn<Score, String>	time = new TableColumn<>("Time Left");
		time.setCellValueFactory(new PropertyValueFactory<>("time"));
		
		return time;
	}

}
