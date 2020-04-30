/**
 * RemoveStage.java 
 *
 * Author: Ishaan Backliwal and Mason Batchelor
 * Date: @date Apr 19, 2020
 * 
 * Course: CS400
 * Semester: Spring 2020
 * Lecture: 001
 * 
 * Other Credits: 
 */

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
/**
 * FileName: RemoveStage.java
 * 
 * Stage where the user can remove certain data points
 * 
 * @author Mason Batchelor: mrbatchelor@wisc.edu Ishaan Backliwal:
 *         backliwal@wisc.edu
 * 
 * Other Credits: Stack Overflow
 *
 */
public class RemoveStage extends Stage {
	
	BorderPane root;	
	Label farmLabel;
	Scene scene;
	FarmManager manager;	// DS manager
	int checkBoxCount;	// count of the number of check boxes
	
	/**
	 * Creates the stage and allows the user to remove data
	 * 
	 * @param farmID - farm ID of data the user wants to remove, or "all" for all data
	 * @param manager - DS manager
	 */
	RemoveStage(String farmId, FarmManager manager) {
		checkBoxCount = 0;
		this.manager = manager;
		if(farmId.compareTo("all") == 0) {
			farmLabel = new Label("All Data");
		}
		else {
			farmLabel = new Label("Farm ID: " + farmId);
		}
		// buttons
		Button confirmRemove = new Button("Remove Selected Data");
		Button selectAllButton = new Button("Select All");
		Button deselectAllButton = new Button("Deselect All");
		
		// set up the scroll bar
		VBox vbox = new VBox();
		ScrollPane scrollPane = new ScrollPane(vbox);
		scrollPane.setFitToHeight(true);
		root = new BorderPane(scrollPane);
		root.setPadding(new Insets(15));
		
		// top hbox and vbox
		HBox title = new HBox();
		title.getChildren().add(farmLabel);
		title.setAlignment(Pos.CENTER);
		HBox selectButtons = new HBox(5);
		selectButtons.getChildren().addAll(selectAllButton, deselectAllButton);
		VBox top = new VBox();
		top.getChildren().addAll(title, selectButtons);
		
		// get existing info
		getInfo(vbox, farmId);
		
		//set the label and user input fields
		root.setTop(top);
		root.setBottom(confirmRemove);
		
		// when "remove selected" button is pressed, run this
		EventHandler<ActionEvent> selectCertain = new EventHandler<ActionEvent>() { 
             public void handle(ActionEvent e) 
             { 
            	 ArrayList<CheckBox> collection = new ArrayList<CheckBox>();
            	 // run through each check box and add selected ones to the collection
            	 for(int i = 0; i < checkBoxCount; i++) {
            		 CheckBox box = (CheckBox)vbox.getChildren().get(i);
            		 if(box.isSelected()) {
            			 String ID = farmId;
            			 if(farmId.compareTo("all") == 0) {
                			 String[] split = box.getText().split("; ");
                			 ID = split[2];
                		 }
                 		  collection.add(box);
                 		  removeMilk(ID, box.getText());
                 	  }
                 }
            	 // decrement check box size by the size of the collection
                 for(int i = 0; i < collection.size(); i++) {
                	 checkBoxCount--;
                 }
                 // remove each check box from the collection from overall data
                 removeMilkFromVbox(collection, vbox);
             } 
         }; 
         confirmRemove.setOnAction(selectCertain);
         
         // when "select all" button is pressed
         EventHandler<ActionEvent> selectAll = new EventHandler<ActionEvent>() { 
             public void handle(ActionEvent e) 
             { 
            	 // run through each check box and select it
            	 for(int i = 0; i < checkBoxCount; i++) {
            		 CheckBox box = (CheckBox)vbox.getChildren().get(i);
            		 if(!box.isSelected())
                  		 box.setSelected(true);
                 }
             } 
         }; 
         selectAllButton.setOnAction(selectAll);
        
         // when "deselect all" button is pressed
         EventHandler<ActionEvent> deselectAll = new EventHandler<ActionEvent>() { 
             public void handle(ActionEvent e) 
             { 
            	 // run through each check box and deselect it
            	 for(int i = 0; i < checkBoxCount; i++) {
            		 CheckBox box = (CheckBox)vbox.getChildren().get(i);
                  	 if(box.isSelected())
                  		 box.setSelected(false);
                 }
             } 
         };
         deselectAllButton.setOnAction(deselectAll);
		
		// create the scene
		this.setTitle("Farm ID: " + farmId);
		farmLabel.setFont(Font.font(50));
		BorderPane.setAlignment(farmLabel, Pos.CENTER);
		this.setScene(new Scene(root, 500, 400));
		this.show();
	}
	
	/**
	 * Removes the selected data from the DS
	 * 
	 * @param farmId - farm ID of the data to be removed
	 * @param milk - data of milk to be deleted
	 */
	private void removeMilk(String farmId, String milk) {
		// add milk to the array list
		int farmIndex;
		String[] split = milk.split("; ");
		String date = split[0];
		String weight = split[1];
		farmIndex = this.farmIndex(farmId);
		// remove milk from the farm
		manager.farms.get(farmIndex).removeMilk(weight, date);
	}
	/**
	 * Removes selected check boxes from the vbox
	 * 
	 * @param collection - collection of all the selected data to be removed
	 * @param vbox
	 */
	private void removeMilkFromVbox(ArrayList<CheckBox> collection, VBox vbox) {
		vbox.getChildren().removeAll(collection);
	}
	
	/**
	 * This is a helper method to get the index of the farm in the arraylist
	 * 
	 * @param farmID - the String version of the FarmID
	 * @return - the index of the farm in the arraylist or -1 if it is not there
	 */
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
	private void getInfo(VBox vbox, String farmId) {
		vbox.getChildren().clear();
		Farm farm = null;
		if(farmId.compareTo("all") == 0) {
			for (int j = 0; j < manager.farms.size(); j++) {
				farm = manager.farms.get(j);
				for (int i = 0; i < farm.milk.size(); i++) {
					String value = farm.getDate(i) + "; " + farm.getWeight(i) + "; " + manager.farms.get(j).farmID;
					// create a checkbox 
					CheckBox checkBox = new CheckBox(value.toString());
					vbox.getChildren().add(checkBox);
					checkBoxCount++;
				}
			}
		}
		else {
			int farmIndex = this.farmIndex(farmId);
			if (farmIndex >= 0) {
				farm = manager.farms.get(farmIndex);
				for (int i = 0; i < farm.milk.size(); i++) {
					String value = farm.getDate(i) + "; " + farm.getWeight(i);
					// create a checkbox 
		            CheckBox checkBox = new CheckBox(value.toString()); 
		            vbox.getChildren().add(checkBox);
		            checkBoxCount++;
				}
				vbox.getChildren().addAll();
			}
		}

	}

}
