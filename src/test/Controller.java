package test;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

public class Controller {
	
	private Stage stage;
	private Scene scene;
	private Parent root;
	
	@FXML
	private Label myLabel;
	@FXML
	private TextField nameTextField;
	@FXML
	private RadioButton redRadioButton, blueRadioButton, greenRadioButton;
	
	int age;
	String name;
	
	public void start(ActionEvent e) throws IOException {	
				

		name = nameTextField.getText();
		
		FXMLLoader loader = new FXMLLoader(getClass().getResource("test.fxml"));
		root = loader.load();
		Controller2 controller2 = loader.getController();
		controller2.setWelcome(name);
		
		if(redRadioButton.isSelected()) {
			controller2.setRedCircle();
		}
		else if(blueRadioButton.isSelected()) {				
			controller2.setBlueCircle();
		}	
		else if(greenRadioButton.isSelected()) {
			controller2.setGreenCircle();
		}					
		
		stage = (Stage)((Node)e.getSource()).getScene().getWindow();			
		scene = new Scene(root);
		controller2.addGameControls(scene);
		stage.setScene(scene);
		stage.show();		
	}
	
	public void rules(ActionEvent e) throws IOException {	
		
		FXMLLoader loader = new FXMLLoader(getClass().getResource("rules.fxml"));
		root = loader.load();
		
		stage = (Stage)((Node)e.getSource()).getScene().getWindow();			
		scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
	}
	
	public void exit(ActionEvent e) {
		
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("EXIT");
		alert.setHeaderText("You are about to exit");
		alert.setContentText("Are you sure to exit?");
		
		if(alert.showAndWait().get() == ButtonType.OK) {
			stage = (Stage)((Node)e.getSource()).getScene().getWindow();
			stage.close();
		}
	}
}