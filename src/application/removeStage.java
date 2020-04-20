package application;

import javafx.scene.Scene;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.*;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class removeStage extends Stage {
	BorderPane root;
	Label farmLabel;
	Scene scene;
	FarmManager manager;
	removeStage(String farmID, FarmManager manager) {
		this.manager = manager;
		farmLabel = new Label("Farm ID: " + farmID);

		// set up the scroll bar
		VBox vbox = new VBox();
		ScrollPane scrollPane = new ScrollPane(vbox);
		scrollPane.setFitToHeight(true);
		root = new BorderPane(scrollPane);
		root.setPadding(new Insets(15));
		
		HBox insertFields = new HBox();
		
		// set up input for milk weight
		VBox weight = new VBox();
		Label weightLabel = new Label("Milk Weight");
		TextField milkWeight = new TextField();
		weight.getChildren().addAll(weightLabel, milkWeight);
		
		// set up input for Milk date
		VBox date = new VBox();
		Label dateLabel = new Label("Date");
		TextField milkDate = new TextField();
		date.getChildren().addAll(dateLabel, milkDate);

		insertFields.getChildren().addAll(weight, date);
		
		//set the label and user input fields
		VBox insert = new VBox();
		insert.getChildren().addAll(farmLabel, insertFields);
		root.setTop(insert);
		
		// create the scene
		this.setTitle("Farm ID: " + farmID);
		farmLabel.setFont(Font.font(50));
		root.setAlignment(farmLabel, Pos.CENTER);
		this.setScene(new Scene(root, 400, 400));
		this.show();
	}

}
