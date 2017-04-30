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
		workspace.getPanel().getEntityDisplay().getEntityMaker().getEventPicker().getEditor().changeSaveHandler(
				() -> savedEvent("FifthStep",()->addedAction("SixthStep","Jump",
						()->afterAction("SeventhStep",()->savedAction()))));
	}
	
	/*private void savedEvent(String s, Runnable r){
		workspace.getMessage().textProperty().bind(workspace.getPolyglot().get("FifthStep"));
		workspace.getPanel().getEntityDisplay().getEntityMaker().getActionPicker().changeNewHandler(() -> addedAction());
	} */
	
	private void savedEvent(String s, Runnable r){
		workspace.getMessage().textProperty().bind(workspace.getPolyglot().get(s));
		workspace.getPanel().getEntityDisplay().getEntityMaker().getActionPicker().changeNewHandler(r);
	}
	
	/*private void addedAction(){
		workspace.getMessage().textProperty().bind(workspace.getPolyglot().get("SixthStep"));
		workspace.getPanel().getEntityDisplay().getEntityMaker().getActionPicker().getEditor().initTutorialAction("Jump", () -> addedJump());
	} */
	
	private void addedAction(String s, String action, Runnable r){
		workspace.getMessage().textProperty().bind(workspace.getPolyglot().get(s));
		workspace.getPanel().getEntityDisplay().getEntityMaker().getActionPicker().getEditor().initTutorialAction(action, r);
	}
	
	/*private void addedJump(){
		workspace.getMessage().textProperty().bind(workspace.getPolyglot().get("SeventhStep"));
		workspace.getPanel().getEntityDisplay().getEntityMaker().getActionPicker().getEditor().changeSaveHandler(() -> savedAction());
	} */
	
	private void afterAction(String s, Runnable r){
		workspace.getMessage().textProperty().bind(workspace.getPolyglot().get(s));
		workspace.getPanel().getEntityDisplay().getEntityMaker().getActionPicker().getEditor().changeSaveHandler(r);
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
		workspace.getPanel().getEntityDisplay().changeNewHandler(() -> createMonster());
	}
	
	private void createMonster(){
		workspace.getMessage().textProperty().bind(workspace.getPolyglot().get("EleventhStep"));
		workspace.getPanel().getEntityDisplay().getEntityMaker().changeImageHandler(() -> changedMonsterImage());
	}
	
	private void changedMonsterImage(){
		workspace.getMessage().textProperty().bind(workspace.getPolyglot().get("TwelfthStep"));
		workspace.getPanel().getEntityDisplay().getEntityMaker().getEventPicker().changeNewHandler(() -> addedMonsterEvent());
	}
	
	private void addedMonsterEvent(){
		workspace.getMessage().textProperty().bind(workspace.getPolyglot().get("ThirteenthStep"));
		workspace.getPanel().getEntityDisplay().getEntityMaker().getEventPicker().getEditor().initTutorialAction("Collision", () -> addedCollision());
	}
	
	private void addedCollision(){
		workspace.getMessage().textProperty().bind(workspace.getPolyglot().get("FourteenthStep"));
		workspace.getPanel().getEntityDisplay().getEntityMaker().getEventPicker().getEditor().changeSaveHandler(() -> savedEvent("FifteenthStep",
				()->addedAction("SixteenthStep","Entity Remove",()->afterAction("SeventeenthStep",
						()->savedMonsterAction()))));
	}

/*	private void savedMonsterEvent(){
		workspace.getMessage().textProperty().bind(workspace.getPolyglot().get("FifteenthStep"));
		workspace.getPanel().getEntityDisplay().getEntityMaker().getActionPicker().changeNewHandler(() -> addedAction());
	} */
	
	/*private void addedMonsterAction(){
		workspace.getMessage().textProperty().bind(workspace.getPolyglot().get("SixteenthStep"));
		workspace.getPanel().getEntityDisplay().getEntityMaker().getActionPicker().getEditor().initTutorialAction("Entity Remove", () -> addedDeath());
	} */
	
	/*private void addedDeath(){
		workspace.getMessage().textProperty().bind(workspace.getPolyglot().get("SeventeenthStep"));
		workspace.getPanel().getEntityDisplay().getEntityMaker().getActionPicker().getEditor().changeSaveHandler(() -> savedMonsterAction());
	}*/
	
	private void savedMonsterAction(){
		workspace.getMessage().textProperty().bind(workspace.getPolyglot().get("EighteenthStep"));	
		workspace.getPanel().getEntityDisplay().getEntityMaker().changeSaveHandler(() -> savedMonsterCharacter());
	}
	
	private void savedMonsterCharacter(){
		workspace.getMessage().textProperty().bind(workspace.getPolyglot().get("NinteenthStep"));	
		//workspace.getLevelEditor().getCurrentLevel().addEntityListener(() -> canvasCharacter());
	}
}
