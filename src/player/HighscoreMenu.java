package player;

import javafx.geometry.Pos;
import javafx.scene.control.TableCell;
import java.util.ResourceBundle;

import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TableView.ResizeFeatures;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.util.Callback;
import polyglot.Polyglot;

/**
 * 
 * @author Jesse
 *
 */
public class HighscoreMenu extends AbstractMenu{
	
	private TableView<Score> scoreTable;	

	public HighscoreMenu(Stage stage, Loader loader, Polyglot polyglot, ResourceBundle IOResources) {
		super(stage, loader, "Highscores", polyglot, IOResources);
		setupScene();
		loadScores();
	}
	
	@SuppressWarnings("unchecked")
	private void setupScene(){
		scoreTable = new TableView<>();

		scoreTable.getColumns().setAll(makeRankColumn(), makeScoreColumn(), makeTimeColumn());
		//scoreTable.setEditable(false);
		
		this.setCenter(scoreTable);
	}
	
	private void loadScores(){
		scoreTable.setItems(getLoader().getScores());
	}
	
	private TableColumn<Score, Integer> makeRankColumn(){
		TableColumn<Score, Integer>	rank = new TableColumn<>("Rank");
		rank.setCellValueFactory(new PropertyValueFactory<>("rank"));
		rank.prefWidthProperty().bind(scoreTable.widthProperty().multiply(.1));
		
		
		return rank;
	}
	
	private TableColumn<Score, String> makeScoreColumn(){
		TableColumn<Score, String> score = new TableColumn<>("Score");
		score.setCellValueFactory(new PropertyValueFactory<Score, String>("score"));
		score.prefWidthProperty().bind(scoreTable.widthProperty().multiply(.5));
		
		return score;
	}
	
	private TableColumn<Score, String> makeTimeColumn(){
		TableColumn<Score, String>	time = new TableColumn<>("Time Left");
		time.setCellValueFactory(new PropertyValueFactory<>("time"));
		time.prefWidthProperty().bind(scoreTable.widthProperty().multiply(.5));
		
		return time;
	}


}
