package test;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Controller3 {
	
	private Stage stage;
	private Scene scene;
	private Parent root;
	
	public void back(ActionEvent e) throws IOException {
		
		root = FXMLLoader.load(getClass().getResource("menu.fxml"));
		stage = (Stage)((Node)e.getSource()).getScene().getWindow();
		scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
	}	
}
