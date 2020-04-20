package application;

import javafx.scene.Scene;

import java.util.ArrayList;

import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollBar;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class FarmStage extends Stage{
		BorderPane root = new BorderPane();
		Label farmLabel;
		Scene scene;
		
		
		FarmStage(String farmID){
			farmLabel = new Label("Farm ID: " + farmID);
			this.setTitle(farmID);
						
			// scroll pane
			VBox vbox = new VBox();
		    ScrollPane scrollPane = new ScrollPane(vbox);
		    scrollPane.setFitToHeight(true);
		    root.setPadding(new Insets(15));
		    for (int i = 0; i < 1000; i ++) {
		    	vbox.getChildren().add(new Text("This is a test to see if the scroll bar works"));
		    }
		    root = new BorderPane(scrollPane);
			
			
	        // set the scene
			scene = new Scene(root, 400, 400);
			root.setTop(farmLabel);
			farmLabel.setFont(Font.font(30));
			BorderPane.setAlignment(farmLabel, Pos.CENTER);
		    this.setScene(scene);
		    this.show();
		}
		

}
