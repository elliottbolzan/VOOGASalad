package authoring;


import java.util.ResourceBundle;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import polyglot.Polyglot;

public class AuthoringTutorial {
	
	AuthoringEnvironment myEnvironment;
	Workspace workspace;
	Alert alert;
	
	public AuthoringTutorial(Polyglot polyglot){
		myEnvironment = new AuthoringEnvironment(polyglot,ResourceBundle.getBundle("resources/IO"));
		workspace = myEnvironment.getWorkspace();
		workspace.addTutorialHost();
		initTutorial();
	}
	
	private void initTutorial(){
		alert = workspace.getMaker().makeAlert(AlertType.INFORMATION,"TutorialTitle", "Welcome", workspace.getPolyglot().get("ContinuePrompt"));
		alert.showAndWait();
		editMario();
	}
	
	private void editMario(){
		workspace.getMessage().textProperty().bind(workspace.getPolyglot().get("FirstPrompt"));
		workspace.getPanel().getEntityDisplay().changeEditHandler(() -> clickedEdit());
	}
	
	private void clickedEdit(){
		workspace.getMessage().textProperty().bind(workspace.getPolyglot().get("SecondStep"));
		workspace.getPanel().getEntityDisplay().getEntityMaker().getEventPicker().changeNewHandler(() -> addedEvent());
	}
	
	private void addedEvent(){
		workspace.getMessage().textProperty().bind(workspace.getPolyglot().get("ThirdStep"));
	}

	
	
	
}
