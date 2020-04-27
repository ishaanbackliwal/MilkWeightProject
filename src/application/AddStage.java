package application;

import javafx.scene.Scene;

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

public class AddStage extends Stage {

	private BorderPane root = new BorderPane();
	private Label farmLabel;
	private FarmManager manager;

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
		confirmAdd.setOnAction(e -> insertMilk(farmID,
				leadingZeros(datePicker.getValue()), milkWeight, vbox));
		
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

	private void insertMilk(String farmID, String milkDate, TextField milkWeight,
			VBox vbox) {
		// add milk to the array list
		int farmIndex = this.farmIndex(farmID);
		Text milk;
		String weight = milkWeight.getText();
		int month = Integer.parseInt(milkDate.split("-")[1]);
		manager.totalWeight[month - 1] += (int)Integer.parseInt(weight);
		if(weight.equals("") || milkDate.equals("")) {
			Alert alert = new Alert(AlertType.WARNING, "Enter a milk weight or date.");
			alert.setHeaderText("Must enter a valid integer milk weight or valid date.");
			alert.showAndWait();
			return;
		}
		
		if (farmIndex == -1) {
			// if farm does not exist must create a new one
			Farm farm = new Farm(farmID);
			farm.addMilk(weight, milkDate);
			milk = getMilk(farm);
			manager.farms.add(farm);
		} else {
			manager.farms.get(farmIndex).addMilk(weight, milkDate);
			// add milk to the vbox
			Farm farm = manager.farms.get(farmIndex);
			milk = getMilk(farm);
		}
		vbox.getChildren().add(milk);

	}

	private Text getMilk(Farm farm) {
		Text milk;
		String date = farm.getDate(farm.milk.size() - 1);
		int weight = farm.getWeight(farm.milk.size() - 1);
		milk = new Text(date + "; " + weight);
		return milk;
	}

	/**
	 * This method prints all of the milk info contained within the specified
	 * farms milk array
	 * 
	 * @param vbox   - the vbox which will display all the values
	 * @param farmID - the farmID to search for in the arraylist
	 */
	private void getInfo(VBox vbox, String farmID) {
		Farm farm = null;
		int farmIndex = this.farmIndex(farmID);

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
	 * Converts the date to desired format
	 * 
	 * @return
	 */
	private StringConverter<LocalDate> convertDateFormat() {
		// convert date format
		StringConverter<LocalDate> converter = new StringConverter<LocalDate>() {
			DateTimeFormatter dateFormatter1 = DateTimeFormatter
					.ofPattern("yyyy-MM-dd");
			DateTimeFormatter dateFormatter2 = DateTimeFormatter
					.ofPattern("yyyy-M-dd");
			DateTimeFormatter dateFormatter3 = DateTimeFormatter
					.ofPattern("yyyy-MM-d");
			DateTimeFormatter dateFormatter4 = DateTimeFormatter
					.ofPattern("yyyy-M-d");

			@Override
			public String toString(LocalDate date) {
				if (date != null && date.getMonthValue() < 10
						&& date.getDayOfMonth() < 10) {
					return dateFormatter4.format(date);
				} else if (date != null && date.getMonthValue() < 10) {
					return dateFormatter2.format(date);
				} else if (date != null && date.getDayOfMonth() < 10) {
					return dateFormatter3.format(date);
				} else if (date != null && date.getMonthValue() > 9
						&& date.getDayOfMonth() > 9) {
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
	 * @param date
	 * @return string of date without leading zeros
	 */
	private String leadingZeros(LocalDate date) {
		DateTimeFormatter dateFormatter1 = DateTimeFormatter
				.ofPattern("yyyy-MM-dd");
		DateTimeFormatter dateFormatter2 = DateTimeFormatter.ofPattern("yyyy-M-dd");
		DateTimeFormatter dateFormatter3 = DateTimeFormatter.ofPattern("yyyy-MM-d");
		DateTimeFormatter dateFormatter4 = DateTimeFormatter.ofPattern("yyyy-M-d");
		if (date != null && date.getMonthValue() < 10
				&& date.getDayOfMonth() < 10) {
			return dateFormatter4.format(date);
		} else if (date != null && date.getMonthValue() < 10) {
			return dateFormatter2.format(date);
		} else if (date != null && date.getDayOfMonth() < 10) {
			return dateFormatter3.format(date);
		} else if (date != null && date.getMonthValue() > 9
				&& date.getDayOfMonth() > 9) {
			return dateFormatter1.format(date);
		} else {
			return "";
		}
	}
}
