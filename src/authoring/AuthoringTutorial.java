package authoring;

import java.util.ResourceBundle;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import polyglot.Polyglot;

public class AuthoringTutorial {
	
	AuthoringEnvironment myEnvironment;
	Workspace workspace;
	Alert alert;
	
	public AuthoringTutorial(Polyglot polyglot){
		myEnvironment = new AuthoringEnvironment(polyglot,ResourceBundle.getBundle("resources/IO"));
		workspace = myEnvironment.getWorkspace();
		initTutorial();
	}
	
	private void initTutorial(){
		alert = workspace.getMaker().makeAlert(AlertType.INFORMATION,"TutorialTitle", "Welcome", workspace.getPolyglot().get("ContinuePrompt"));
		alert.showAndWait();
		editMario();
	}
	
	private void editMario(){
		alert = workspace.getMaker().makeAlert(AlertType.INFORMATION,"TutorialTitle", "FirstStep", workspace.getPolyglot().get("FirstPrompt"));
		setCoordinates(200,-20);
		alert.showAndWait();
		workspace.getPanel().getEntityDisplay().changeEditHandler(() -> clickedEdit());
	}
	
	private void clickedEdit(){
		alert = workspace.getMaker().makeAlert(AlertType.INFORMATION,"TutorialTitle", "SecondStep", workspace.getPolyglot().get("SecondPrompt"));
		setCoordinates(20,50);
		workspace.getPanel().getEntityDisplay().getEntityMaker().getEventPicker().changeNewHandler(() -> addedEvent());
	}
	
	private void addedEvent(){
		alert = workspace.getMaker().makeAlert(AlertType.INFORMATION,"TutorialTitle", "ThirdStep", workspace.getPolyglot().get("ThirdPrompt"));
	
		alert.showAndWait();
	}
	
	private void setCoordinates(int x, int y){
		alert.setX(x);
		alert.setY(y);
		alert.showAndWait();
	}
	
	
	
}
