package test;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.animation.AnimationTimer;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.binding.BooleanBinding;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.util.Duration;

public class Controller2 implements Initializable {
	
	private Stage stage;
	private Scene scene;
	private Parent root;
	
    private BooleanProperty aPressed = new SimpleBooleanProperty();
    private BooleanProperty dPressed = new SimpleBooleanProperty();
    
    private BooleanBinding keyPressed = aPressed.or(dPressed);
	
	@FXML
	private Circle circle;
	@FXML
	private Rectangle catchRec;	
	@FXML
	private Label welcomeLabel;
	@FXML
	private Label pointLabel;
	@FXML
	private Rectangle bonusRec;
	@FXML
	private Label bonusLabel;
	@FXML
	private Circle health1, health2, health3;
	@FXML
	private Label dieLabel;
	
	int point = 0, health = 3;
	Boolean bonus = false, die = false;
	Paint oriColor;

	public void back(ActionEvent e) throws IOException {
		
		root = FXMLLoader.load(getClass().getResource("menu.fxml"));
		stage = (Stage)((Node)e.getSource()).getScene().getWindow();
		scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
	}
	
	public void setWelcome(String name) {
		welcomeLabel.setText("Player : " + name);
	}
	
	public void setRedCircle() {		
		circle.setFill(Color.RED);	
		oriColor = circle.getFill();
	}
	
	public void setBlueCircle() {
		circle.setFill(Color.BLUE);	
		oriColor = circle.getFill();
	}
	
	public void setGreenCircle() {		
		circle.setFill(Color.GREEN);
		oriColor = circle.getFill();
	}
	
	public void moveCatchRecRight() {
		if(catchRec.getTranslateX() >= 240) {
			catchRec.setTranslateX(catchRec.getTranslateX() - 5.5);
		}
		catchRec.setTranslateX(catchRec.getTranslateX() + 5.5);
	}
	
	public void moveCatchRecLeft() {
		if(catchRec.getTranslateX() <= -240) {
			catchRec.setTranslateX(catchRec.getTranslateX() + 5.5);
		}
		catchRec.setTranslateX(catchRec.getTranslateX() - 5.5);
	}
	
	public void loseHealthCheck() {
		if(circle.getTranslateY() >= 400) {
			health --;
			if(health == 2) {
				health3.setFill(Color.TRANSPARENT);

			}
			else if(health == 1) {
				health2.setFill(Color.TRANSPARENT);

			}
			else if(health == 0) {
				health1.setFill(Color.TRANSPARENT);
				die = true;
			}
		}
	}
	
	public void setDifficulty(double speed) {
		circle.setTranslateY(circle.getTranslateY() + speed);
	}

	AnimationTimer timerCircle = new AnimationTimer() {
		@Override
		public void handle(long now) {
			if(point >= 0 && point < 20) {
				setDifficulty(5);
			}
			else if(point >= 20 && point < 50) {
				setDifficulty(6);
			}
			else {
				setDifficulty(6.75);
			}
			if(getPoint() || circle.getTranslateY() >= 400){
				
				if(Math.random() >= 0.9) {
					circle.setFill(Color.TRANSPARENT);
					this.stop();
					loseHealthCheck();
					circle.setTranslateX((Math.random() * 2 - 1) * 250);
					circle.setTranslateY(0);
					timerBonus.start();					
				}
				else {
					this.stop();
					loseHealthCheck();
					circle.setTranslateX((Math.random() * 2 - 1) * 250);
					circle.setTranslateY(0);					
					this.start();
				}				
			}
			if(die) {
				circle.setFill(Color.TRANSPARENT);
				catchRec.setFill(Color.TRANSPARENT);
				catchRec.setStroke(Color.TRANSPARENT);
				dieLabel.setText("You Die!");
				bonusLabel.setText(null);
				timerBonus.stop();
				timerCircle.stop();
			}
		}
	};
	
	AnimationTimer timerBonus = new AnimationTimer() {
		@Override		
		public void handle(long now) {			
			bonusRec.setFill(Color.ORANGE);			
			bonusRec.setTranslateY(bonusRec.getTranslateY() + 8);
			if(getBonus() || bonusRec.getTranslateY() >= 400){	
				bonusRec.setFill(Color.TRANSPARENT);
				this.stop();
				bonusRec.setTranslateX((Math.random() * 2 - 1) * 250);
				bonusRec.setTranslateY(0);							
				
				circle.setFill(oriColor);
				timerCircle.start();
			}
		}
	};
	
	public Boolean getPoint() {
		double xDistance = Math.abs(catchRec.getTranslateX() - circle.getTranslateX());
		double yDistance = Math.abs(catchRec.getLayoutY() - circle.getLayoutY() - circle.getTranslateY());
		if(yDistance <= circle.getRadius() && xDistance <= circle.getRadius() + 50) {
			if(bonus) {
				point += 2;
			}
			else {				
				point ++;
			}					
			pointLabel.setText("Points : " + point);			
			return true;
		}
		else {
			return false;
		}
	}
	
	public Boolean getBonus() {
		double xDistance = Math.abs(catchRec.getTranslateX() - bonusRec.getTranslateX());
		double yDistance = Math.abs(catchRec.getLayoutY() - bonusRec.getLayoutY() - bonusRec.getTranslateY());
		if(yDistance <= bonusRec.getHeight() && xDistance <= bonusRec.getWidth()/2 + 50) {	
			
			Timeline tm = new Timeline(
				new KeyFrame(Duration.ZERO, e -> {bonus = true; bonusLabel.setText("X2 POINTS IN 10 SECONDS!");}),
				new KeyFrame(Duration.seconds(10), e -> {bonus = false; bonusLabel.setText(null);})
			);
			tm.play();
			return true;
		}
		else {
			return false;
		}
	}
	
	AnimationTimer timerCatchRec = new AnimationTimer() {
        @Override
        public void handle(long timestamp) {
            if(aPressed.get()){
            	moveCatchRecLeft();	
            }

            if(dPressed.get()){
            	moveCatchRecRight();	
            }            
        }
    };
	
	public void addGameControls(Scene s) {		
		 keyPressed.addListener(((observableValue, aBoolean, t1) -> {
			 	if(die) {
			 		timerCatchRec.stop();
			 	}
			 	else if(!aBoolean){
	                timerCatchRec.start();
	            } 
			 	else {
	                timerCatchRec.stop();
	            }
	        }));
		
		s.setOnKeyPressed(e -> {
            if(e.getCode() == KeyCode.A) {
                aPressed.set(true);
            }

            if(e.getCode() == KeyCode.D) {
                dPressed.set(true);
            }
        });

        s.setOnKeyReleased(e ->{
            if(e.getCode() == KeyCode.A) {
                aPressed.set(false);
            }

            if(e.getCode() == KeyCode.D) {
                dPressed.set(false);
            }
        });
	}
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		timerCircle.start();
		bonusRec.setFill(Color.TRANSPARENT);
	}
}






