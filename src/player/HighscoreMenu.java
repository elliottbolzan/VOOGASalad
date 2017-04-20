package player;

import java.util.ArrayList;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

public class HighscoreMenu extends AbstractMenu{
	
	private TableView<Score> scoreTable;	
	private ObservableList<Score> scores;

	//Have both scores and times give ability to sort by either

	public HighscoreMenu(Stage stage, String gameFolderPath) {
		super(stage, gameFolderPath, "Highscores");
		setupScene();
		loadScores();
	}
	
	private void setupScene(){
		scoreTable = new TableView<>();
		scoreTable.getColumns().setAll(makeRankColumn(), makeScoreColumn(), makeTimeColumn());
		scoreTable.setEditable(false);
		
		this.setCenter(scoreTable);
	}
	
	private void loadScores(){
		//test
		Score score = new Score(1, "0001", "01:57:69");
		List<Score> scores = new ArrayList<>();
		scores.add(score);
		
		this.scores = FXCollections.observableArrayList(addDefaults(scores));
		scoreTable.setItems(this.scores);
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
	
	private List<Score> addDefaults(List<Score> scores){
		for(int i = scores.size(); i < 10; i++){
			scores.add(new Score(i+1));
		}
		
		return scores;
	}
	
	

}
