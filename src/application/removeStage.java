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

public class removeStage extends Stage{
		BorderPane farmInfo = new BorderPane();
		Label x = new Label("Farm Number 00");
		
		
		removeStage(){
				this.setTitle("Farm Number 00");
				this.createScene();
		    this.setScene(new Scene(farmInfo, 400, 400));
		    this.show();
		}
		
		/*
		 * This private method is used to create the add stage scene
		 */
		private void createScene() {
				farmInfo.setTop(x);
				x.setFont(Font.font (50));
				farmInfo.setAlignment(x, Pos.CENTER);
		}

}
