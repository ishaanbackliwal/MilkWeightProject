package application;

import javafx.scene.Scene;

import java.util.ArrayList;
import java.util.Collection;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class RemoveStage extends Stage {
	BorderPane root;
	Label farmLabel;
	Scene scene;
	FarmManager manager;
	int checkBoxCount;
	
	
	RemoveStage(String farmID, FarmManager manager) {
		checkBoxCount = 0;
		this.manager = manager;
		farmLabel = new Label("Farm ID: " + farmID);

		// set up the scroll bar
		VBox vbox = new VBox();
		ScrollPane scrollPane = new ScrollPane(vbox);
		scrollPane.setFitToHeight(true);
		root = new BorderPane(scrollPane);
		root.setPadding(new Insets(15));
		
		// get existing info
		getInfo(vbox, farmID);
		
		// hbox for input fields
		HBox insertFields = new HBox();
		Button confirmRemove = new Button("Remove Selected Data");
		
		
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
		root.setTop(farmLabel);
		root.setBottom(confirmRemove);
		EventHandler<ActionEvent> event = new EventHandler<ActionEvent>() { 
             public void handle(ActionEvent e) 
             { 
            	 ArrayList<CheckBox> collection = new ArrayList<CheckBox>();
                  for(int i = 0; i < checkBoxCount; i++) {
                	  CheckBox box = (CheckBox)vbox.getChildren().get(i);
                	  if(box.isSelected()) {
                		  collection.add(box);
                		  removeMilk(farmID, box.getText());
                	  }
                  }
                  for(int i = 0; i < collection.size(); i++) {
                	  checkBoxCount--;
                  }
                  removeMilkFromVbox(collection, vbox);
             } 
         }; 
         confirmRemove.setOnAction(event);
		
		// create the scene
		this.setTitle("Farm ID: " + farmID);
		farmLabel.setFont(Font.font(50));
		BorderPane.setAlignment(farmLabel, Pos.CENTER);
		this.setScene(new Scene(root, 400, 400));
		this.show();
	}
	
	private void removeMilk(String farmID, String milk) {
		// add milk to the array list
		int farmIndex = this.farmIndex(farmID);
		String[] split = milk.split("; ");
		String weight = split[1];
		String date = split[0];
		Farm farm = manager.farms.get(farmIndex);
		Text milkText = getMilk(farm);
		// remove milk from the farm
		manager.farms.get(farmIndex).removeMilk(weight, date);
	}
	private void removeMilkFromVbox(ArrayList<CheckBox> collection, VBox vbox) {
		vbox.getChildren().removeAll(collection);
	}
	
	private Text getMilk(Farm farm) {
		Text milk;
		String date = farm.getDate(farm.milk.size() - 1);
		int weight = farm.getWeight(farm.milk.size() - 1);
		milk = new Text(date + "; " + weight);
		return milk;
	}
	
	private int farmIndex(String farmID) {
		for(int i = 0; i < manager.farms.size(); i++) {
			if(manager.farms.get(i).farmID.equals(farmID)) {
				return i;
			}
		}
		return -1;
	}

	/**
	 * This method prints all of the milk info contained within the specified
	 * farms milk array
	 * 
	 * @param vbox - the vbox which will display all the values
	 * @param farmID - the farmID to search for in the arraylist
	 */
	private void getInfo(VBox vbox, String farmID) {
		vbox.getChildren().clear();
		Farm farm = null;
		int farmIndex = this.farmIndex(farmID);
		
		if (farmIndex >= 0) {
			farm = manager.farms.get(farmIndex);
			for (int i = 0; i < farm.milk.size(); i++) {
				String value = farm.getDate(i) + "; " + farm.getWeight(i);
				// create a checkbox 
	            CheckBox checkBox = new CheckBox(value.toString()); 
	            // add label 
	            vbox.getChildren().add(checkBox);
	            checkBoxCount++;
			}
			vbox.getChildren().addAll();
		}
	}

}
