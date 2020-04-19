package application;

import javafx.scene.Scene;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.*;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class FarmStage extends Stage{
		BorderPane farmInfo = new BorderPane();
		Label farmNum;
		
		
		FarmStage(String farmID){
				farmNum = new Label(farmID);
				this.setTitle(farmID);
				this.createScene();
		    this.setScene(new Scene(farmInfo, 400, 400));
		    this.show();
		}
		
		/*
		 * This private method is used to create the add stage scene
		 */
		private void createScene() {
				farmInfo.setTop(farmNum);
				farmNum.setFont(Font.font(40));
				farmInfo.setAlignment(farmNum, Pos.CENTER);
		}

}
