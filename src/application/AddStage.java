package application;

import javafx.scene.Scene;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.*;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollBar;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.StringConverter;

/**
 * FileName: AddStage.java
 * 
 * The adding stage of the GUI 
 * This displays a new stage to add information to the data structure
 * 
 * @author Mason Batchelor: mrbatchelor@wisc.edu 
 * 		   Ishaan Backliwal: backliwal@wisc.edu
 * 
 * Other Credits: Stack Overflow
 *
 */
public class AddStage extends Stage {

	private BorderPane root = new BorderPane();
	private Label farmLabel; // title label for the top of the stage
	private FarmManager manager; // manager for the back end data structure

	/**
	 * Stage to allow adding milk data to farms
	 * 
	 * @param farmID - ID of the farm to display
	 * @param manager - manager for the back end data structure
	 */
	public AddStage(String farmID, FarmManager manager) {
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

		// hbox for insert fields
		HBox insertFields = new HBox();

		// set up input for milk weight
		VBox weight = new VBox();
		Label weightLabel = new Label("Milk Weight");
		TextField milkWeight = new TextField();
		weight.getChildren().addAll(weightLabel, milkWeight);

		// set up input for Milk date
		VBox date = new VBox();
		Label dateLabel = new Label("Date");
		DatePicker datePicker = new DatePicker();
		datePicker.setPromptText("yyyy-mm-dd");
		datePicker.setConverter(convertDateFormat());
		datePicker.requestFocus();
		date.getChildren().addAll(dateLabel, datePicker);

		// button to confirm the input
		VBox buttonBox = new VBox();
		Label blank = new Label("");
		Button confirmAdd = new Button("Add Data");
		buttonBox.getChildren().addAll(blank, confirmAdd);

		// set insert fields
		insertFields.getChildren().addAll(date, weight, buttonBox);

		// set the label and user input fields
		VBox insert = new VBox();
		insert.getChildren().addAll(farmLabel, insertFields);
		root.setTop(insert);

		// set the event handler for adding a new milk value
		confirmAdd.setOnAction(e -> {
			insertMilk(farmID, leadingZeros(datePicker.getValue()), milkWeight, vbox);
		});

		// create the scene
		this.setTitle("Farm ID: " + farmID);
		farmLabel.setFont(Font.font(50));
		BorderPane.setAlignment(farmLabel, Pos.CENTER);
		this.setScene(new Scene(root, 500, 400));
		this.show();
	}

	/**
	 * This is a helper method to get the index of the farm in the arraylist
	 * 
	 * @param farmID - the String version of the FarmID
	 * @return - the index of the farm in the arraylist or -1 if it is not there
	 */
	private int farmIndex(String farmID) {
		for (int i = 0; i < manager.farms.size(); i++) {
			if (manager.farms.get(i).farmID.equals(farmID)) {
				return i;
			}
		}
		return -1;
	}

	private void insertMilk(String farmID, String milkDate, TextField milkWeight, VBox vbox) {

		// initial info
		int farmIndex = this.farmIndex(farmID);
		Text milk;
		String weight = milkWeight.getText();

		// alert user of error if milk, date, or both are not entered
		if (weight.equals("") || milkDate.equals("")) {
			Alert alert = new Alert(AlertType.WARNING, "Enter a milk weight or date.");
			alert.setHeaderText("Must enter a valid integer milk weight or valid date.");
			alert.showAndWait();
			return;
		}

		// isolate month from date
		int month = Integer.parseInt(milkDate.split("-")[1]);

		// try to add weight to total weight
		// alert user if weight entered is not a valid integer
		try {
			manager.totalWeight[month - 1] += (int) Integer.parseInt(weight);
		} catch (NumberFormatException e) {
			Alert alert = new Alert(AlertType.WARNING, "Enter a valid integer milk weight.");
			alert.setHeaderText("Milk weight must be a positive integer.");
			alert.showAndWait();
			return;
		}

		// if farm does not exist must create a new one
		if (farmIndex == -1) {
			Farm farm = new Farm(farmID);
			farm.addMilk(weight, milkDate);
			milk = getMilk(farm);
			manager.farms.add(farm);
			// else, add to existing farm
		} else {
			manager.farms.get(farmIndex).addMilk(weight, milkDate);
			Farm farm = manager.farms.get(farmIndex);
			milk = getMilk(farm);
		}
		// add milk to the vbox
		vbox.getChildren().add(milk);

	}

	/**
	 * Gets newly added milk data to display
	 * 
	 * @param farm - the farm to which the milk was added
	 * @return Text of milk data to display
	 */
	private Text getMilk(Farm farm) {
		Text milk;
		String date = farm.getDate(farm.milk.size() - 1);
		int weight = farm.getWeight(farm.milk.size() - 1);
		milk = new Text(date + "; " + weight);
		return milk;
	}

	/**
	 * This method prints all of the milk info contained within the specified farms
	 * milk array
	 * 
	 * @param vbox   - the vbox which will display all the values
	 * @param farmId - the farm ID to search for in the arraylist
	 */
	private void getInfo(VBox vbox, String farmId) {
		Farm farm = null;
		// index of the farm ID in the array
		int farmIndex = this.farmIndex(farmId);

		if (farmIndex >= 0) {
			farm = manager.farms.get(farmIndex);
			for (int i = 0; i < farm.milk.size(); i++) {
				Text value = new Text(farm.getDate(i) + "; " + farm.getWeight(i));
				vbox.getChildren().addAll(value);
			}
			vbox.getChildren().addAll();
		}
	}

	/**
	 * Converts the date to desired (yyyy-MM-dd) format and removes leading zeros
	 * before displaying date in date picker
	 * 
	 * @return a date converter for the new format
	 */
	private StringConverter<LocalDate> convertDateFormat() {
		// convert date format
		StringConverter<LocalDate> converter = new StringConverter<LocalDate>() {

			// date format for two digit month and day
			DateTimeFormatter dateFormatter1 = DateTimeFormatter.ofPattern("yyyy-MM-dd");
			// date format for one digit month and two digit day
			DateTimeFormatter dateFormatter2 = DateTimeFormatter.ofPattern("yyyy-M-dd");
			// date format for two digit month and one digit day
			DateTimeFormatter dateFormatter3 = DateTimeFormatter.ofPattern("yyyy-MM-d");
			// date format for one digit month and day
			DateTimeFormatter dateFormatter4 = DateTimeFormatter.ofPattern("yyyy-M-d");

			@Override
			public String toString(LocalDate date) {
				if (date != null && date.getMonthValue() < 10 && date.getDayOfMonth() < 10) {
					return dateFormatter4.format(date);
				} else if (date != null && date.getMonthValue() < 10) {
					return dateFormatter2.format(date);
				} else if (date != null && date.getDayOfMonth() < 10) {
					return dateFormatter3.format(date);
				} else if (date != null && date.getMonthValue() > 9 && date.getDayOfMonth() > 9) {
					return dateFormatter1.format(date);
				} else {
					return "";
				}
			}

			@Override
			public LocalDate fromString(String string) {
				if (string != null && !string.isEmpty()) {
					return LocalDate.parse(string, dateFormatter1);
				} else {
					return null;
				}
			}
		};
		return converter;
	}

	/**
	 * Removes leading zeros from date values before adding data
	 * 
	 * @param date - date to remove the leading zeros for
	 * @return string of date without leading zeros
	 */
	private String leadingZeros(LocalDate date) {

		// date format for two digit month and day
		DateTimeFormatter dateFormatter1 = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		// date format for one digit month and two digit day
		DateTimeFormatter dateFormatter2 = DateTimeFormatter.ofPattern("yyyy-M-dd");
		// date format for two digit month and one digit day
		DateTimeFormatter dateFormatter3 = DateTimeFormatter.ofPattern("yyyy-MM-d");
		// date format for one digit month and day
		DateTimeFormatter dateFormatter4 = DateTimeFormatter.ofPattern("yyyy-M-d");

		if (date != null && date.getMonthValue() < 10 && date.getDayOfMonth() < 10) {
			return dateFormatter4.format(date);
		} else if (date != null && date.getMonthValue() < 10) {
			return dateFormatter2.format(date);
		} else if (date != null && date.getDayOfMonth() < 10) {
			return dateFormatter3.format(date);
		} else if (date != null && date.getMonthValue() > 9 && date.getDayOfMonth() > 9) {
			return dateFormatter1.format(date);
		} else {
			return "";
		}
	}
}
