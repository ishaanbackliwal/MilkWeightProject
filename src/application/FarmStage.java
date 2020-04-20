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
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class FarmStage extends Stage {

	private BorderPane root = new BorderPane();
	private Label farmLabel;
	private Scene scene;
	private FarmManager manager;

	FarmStage(String farmID, FarmManager manager) {
		this.manager = manager;
		farmLabel = new Label("Farm ID: " + farmID);

		// set up the scroll bar
		VBox vbox = new VBox();
		ScrollPane scrollPane = new ScrollPane(vbox);
		scrollPane.setFitToHeight(true);
		root = new BorderPane(scrollPane);
		root.setPadding(new Insets(15));

		// retrieve all the values stored in the farm location
		getInfo(vbox, farmID);

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

		// button to confirm the input
		VBox buttonBox = new VBox();
		Label blank = new Label("");
		Button confirm = new Button("Add Data");
		buttonBox.getChildren().addAll(blank, confirm);

		insertFields.getChildren().addAll(date, weight, buttonBox);

		// set the label and user input fields
		VBox insert = new VBox();
		insert.getChildren().addAll(farmLabel, insertFields);
		root.setTop(insert);
		
		// set the event handler for adding a new milk value
		confirm.setOnAction(e -> insertMilk(farmID, milkDate, milkWeight, vbox));
	 
		// create the scene
		this.setTitle("Farm ID: " + farmID);
		farmLabel.setFont(Font.font(50));
		root.setAlignment(farmLabel, Pos.CENTER);
		this.setScene(new Scene(root, 400, 400));
		this.show();
	}
	private int farmIndex(String farmID) {
		for(int i = 0; i < manager.farms.size(); i++) {
			if(manager.farms.get(i).farmID.equals(farmID)) {
				return i;
			}
		}
		return -1;
	}
	private void insertMilk(String farmID, TextField milkDate, TextField milkWeight, VBox vbox) {
		// add milk to the array list
		int farmIndex = this.farmIndex(farmID);
		String date = milkDate.getText();
		String weight = milkWeight.getText();
		manager.farms.get(farmIndex).addMilk(weight, date);
		// add milk to the vbox
		Farm farm = manager.farms.get(farmIndex);
		Text milk = getMilk(farm);
		
		vbox.getChildren().add(milk);
		
	}
	
	private Text getMilk(Farm farm) {
		Text milk;
		String date = farm.getDate(farm.milk.size() - 1);
		int weight = farm.getWeight(farm.milk.size() - 1);
		milk = new Text(date + " " + weight);
		return milk;
	}
	
	/**
	 * This method prints all of the milk info contained within the specified
	 * farms milk array
	 * 
	 * @param vbox - the vbox which will display all the values
	 * @param farmID - the farmID to search for in the arraylist
	 */
	private void getInfo(VBox vbox, String farmID) {
		Farm farm = null;
		int farmIndex = this.farmIndex(farmID);
		
		if (farmIndex >= 0) {
			farm = manager.farms.get(farmIndex);
			for (int i = 0; i < farm.milk.size(); i++) {
				Text value = new Text("" + farm.getDate(i) + " " + farm.getWeight(i));
				vbox.getChildren().addAll(value);
			}
			vbox.getChildren().addAll();
		}
	}
}
