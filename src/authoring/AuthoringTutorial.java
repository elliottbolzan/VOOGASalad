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
		workspace.getPanel().getEntityDisplay().getEntityMaker().getEventPicker().getEditor().initTutorialAction("Key Press", () -> addedKeyPress());
	}
	
	private void addedKeyPress(){
		workspace.getMessage().textProperty().bind(workspace.getPolyglot().get("FourthStep"));
		workspace.getPanel().getEntityDisplay().getEntityMaker().getEventPicker().getEditor().changeSaveHandler(() -> savedEvent());
	}
	
	private void savedEvent(){
		workspace.getMessage().textProperty().bind(workspace.getPolyglot().get("FifthStep"));
		workspace.getPanel().getEntityDisplay().getEntityMaker().getActionPicker().changeNewHandler(() -> addedAction());
	}
	
	private void addedAction(){
		workspace.getMessage().textProperty().bind(workspace.getPolyglot().get("SixthStep"));
		workspace.getPanel().getEntityDisplay().getEntityMaker().getActionPicker().getEditor().initTutorialAction("Jump", () -> addedJump());
	}
	
	private void addedJump(){
		workspace.getMessage().textProperty().bind(workspace.getPolyglot().get("SeventhStep"));
		workspace.getPanel().getEntityDisplay().getEntityMaker().getActionPicker().getEditor().changeSaveHandler(() -> savedAction());
	}
	
	private void savedAction(){
		workspace.getMessage().textProperty().bind(workspace.getPolyglot().get("EighthStep"));	
		workspace.getPanel().getEntityDisplay().getEntityMaker().changeSaveHandler(() -> savedCharacter());
	}
	
	private void savedCharacter(){
		workspace.getMessage().textProperty().bind(workspace.getPolyglot().get("NinthStep"));	
		workspace.getLevelEditor().getCurrentLevel().addEntityListener(() -> canvasCharacter());
	}
	
	private void canvasCharacter(){
		workspace.getMessage().textProperty().bind(workspace.getPolyglot().get("TenthStep"));	
	//	workspace.getLevelEditor().getCurrentLevel().addEntityListener(() -> canvasCharacter());
	}
	
	

	
	
	
}
