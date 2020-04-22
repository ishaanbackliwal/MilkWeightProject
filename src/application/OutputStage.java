/**
 * OutputStage.java created by ishaanbackliwalApr 22, 2020
 *
 * Author: Ishaan Backliwal
 * Date: @date
 * 
 * Course: CS400
 * Semester: Spring 2020
 * Lecture: 001
 * 
 * List Collaborators: name, email@wisc.edu, lecture number
 * 
 * Other Credits: 
 * 
 * Known Bugs: 
 */
package application;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 * OutputStage - TODO Describe purpose of this user-defined type
 * @author backliwal ishaanbackliwal
 *
 */

public class OutputStage extends Stage{

	private BorderPane root = new BorderPane();
	private FarmManager manager;

	OutputStage(FarmManager manager){
		this.manager = manager;
		
		// selection drop down and button
		VBox selectFields = new VBox();
		HBox hbox = new HBox();
		Button outputButton = new Button("Output");
		ObservableList<String> options = FXCollections.observableArrayList(
			        "Farm Report",
			        "Annual Report",
			        "Monthly Report",
			        "Date Range Report");
		ComboBox<String> comboBox = new ComboBox<String>(options);
		comboBox.setPromptText("Select an Option");
		hbox.getChildren().setAll(comboBox, outputButton);
		hbox.setAlignment(Pos.CENTER);
		selectFields.getChildren().add(hbox);
		selectFields.setAlignment(Pos.TOP_CENTER);
		root.setPadding(new Insets(40));
		root.setTop(selectFields);
		
		// further input layout setup
		VBox extraInput = new VBox(10);
		
		// handle button press
		EventHandler<ActionEvent> event = new EventHandler<ActionEvent>() { 
          public void handle(ActionEvent e) 
          { 
        	  if(comboBox.getValue() == null) {
        		  Alert alert = new Alert(AlertType.WARNING, "Must select an option from the drop down.");
        		  alert.setHeaderText("ERROR WARNING");
        		  alert.showAndWait();
        	  }
        	  else if(comboBox.getValue().compareTo("Farm Report") == 0) {
        		  farmReport(extraInput);
        	  }
        	  else if(comboBox.getValue().compareTo("Annual Report") == 0) {
        		  
        	  }
        	  else if(comboBox.getValue().compareTo("Monthly Report") == 0) {
        		  
        	  }
        	  else {
        		  
        	  }
          } 
		};
		outputButton.setOnAction(event);
		
		// set scene
		this.setTitle("Output a File");
		Text title = new Text("Output");
		title.setFont(Font.font(50));
		BorderPane.setAlignment(title, Pos.CENTER);
		this.setScene(new Scene(root, 500, 400));
		this.show();
	}

	private void farmReport(VBox vbox) {
		HBox hbox = new HBox();
		Button doneButton = new Button("Done!");
		
		// text fields
		TextField farmIDTextField = new TextField();
		TextField yearTextField = new TextField();
		
		// text field vboxes
		VBox farmIDBox = new VBox();
		Label farmIDLabel = new Label("Farm ID:");
		farmIDBox.getChildren().addAll(farmIDLabel, farmIDTextField);
		VBox yearBox = new VBox();
		Label yearLabel = new Label("Year (leave blank if all data desired):");
		yearBox.getChildren().addAll(yearLabel, yearTextField);
		
		// more box setup
		hbox.getChildren().addAll(farmIDBox, yearBox);
		hbox.setAlignment(Pos.CENTER);
		vbox.getChildren().addAll(hbox, doneButton);
		vbox.setAlignment(Pos.CENTER);
		root.setCenter(vbox);
		
		EventHandler<ActionEvent> event = new EventHandler<ActionEvent>() { 
	          public void handle(ActionEvent e) 
	          { 
	        	  if(farmIDTextField.getText().compareTo("") == 0) {
	        		  Alert alert = new Alert(AlertType.WARNING, "Must enter a Farm ID");
	        		  alert.setHeaderText("ERROR WARNING");
	        		  alert.showAndWait();
	        	  }
	        	  else if(!isInt(farmIDTextField.getText())){
	        		  Alert alert = new Alert(AlertType.WARNING, "Must enter a valid integer Farm ID");
	        		  alert.setHeaderText("ERROR WARNING");
	        		  alert.showAndWait();
	        	  }
	        	  else if(yearTextField.getText().compareTo("") == 0){
	        		  // **all data**
	        	  }
	        	  else if(!isInt(yearTextField.getText())) {
	        		  Alert alert = new Alert(AlertType.WARNING, "Must enter a valid integer year or leave blank for all data");
	        		  alert.setHeaderText("ERROR WARNING");
	        		  alert.showAndWait();
	        	  }
	        	  else {
	        		  // **specific year data**
	        	  }
	          } 
		};
		doneButton.setOnAction(event);
	}
	private boolean isInt(String input) {
		try{
			Integer.parseInt(input);
		}
		catch(Exception e) {
			return false;
		}
		return true;
	}
	private void annualReport() {
		
	}
	private void monthylReport() {
		
	}
	private void dateRangeReport() {
		
	}
}


















