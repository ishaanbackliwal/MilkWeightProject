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

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
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
 * 
 * @author backliwal ishaanbackliwal
 *
 */

public class OutputStage extends Stage {

	private BorderPane root = new BorderPane();
	private FarmManager manager;
	private boolean inProgress = false;
	VBox extraInput = new VBox(10);
	int outputNum;

	OutputStage(FarmManager manager) {
		outputNum = 0;
		this.manager = manager;

		// selection drop down and button
		VBox selectFields = new VBox();
		HBox hbox = new HBox();
		Button goButton = new Button("Go!");
		ObservableList<String> options = FXCollections.observableArrayList(
				"STAT DISPLAY: Farm Report", "STAT DISPLAY: Annual Report",
				"STAT DISPLAY: Monthly Report", "STAT DISPLAY: Date Range Report",
				"FILE OUTPUT: Farm Report", "FILE OUTPUT: Annual Report",
				"FILE OUTPUT: Monthly Report", "FILE OUTPUT: Date Range Report");
		ComboBox<String> comboBox = new ComboBox<String>(options);
		comboBox.setPromptText("Select an Option");
		hbox.getChildren().addAll(comboBox, goButton);
		hbox.setAlignment(Pos.CENTER);
		selectFields.getChildren().add(hbox);
		selectFields.setAlignment(Pos.TOP_CENTER);
		root.setPadding(new Insets(40));
		root.setTop(selectFields);

		// handle button press
		EventHandler<ActionEvent> event = new EventHandler<ActionEvent>() {
			public void handle(ActionEvent e) {
				inProgress = false;
				extraInput.getChildren().clear();
				if (comboBox.getValue() == null) {
					Alert alert = new Alert(AlertType.WARNING,
							"Must select an option from the drop down.");
					alert.setHeaderText("ERROR WARNING");
					alert.showAndWait();
				} else if (comboBox.getValue()
						.compareTo("STAT DISPLAY: Farm Report") == 0) {
					farmReportStatDisplay(1);
				} else if (comboBox.getValue()
						.compareTo("STAT DISPLAY: Annual Report") == 0) {
					// TODO
				} else if (comboBox.getValue()
						.compareTo("STAT DISPLAY: Monthly Report") == 0) {
					// monthly report stat display
				} else if (comboBox.getValue()
						.compareTo("STAT DISPLAY: Date Range Report") == 0) {
					// date range report stat display
				} else if (comboBox.getValue()
						.compareTo("FILE OUTPUT: Farm Report") == 0) {
					farmReportFileOutput();
				} else if (comboBox.getValue()
						.compareTo("FILE OUTPUT: Annual Report") == 0) {
					// annual report file output
				} else if (comboBox.getValue()
						.compareTo("FILE OUTPUT: Monthly Report") == 0) {
					// monthly report file output
				} else if (comboBox.getValue()
						.compareTo("FILE OUTPUT: Date Range Report") == 0) {
					// date range report file output
				}
			}
		};
		goButton.setOnAction(event);

		// set scene
		this.setTitle("Output a File");
		Text title = new Text("Output");
		title.setFont(Font.font(50));
		BorderPane.setAlignment(title, Pos.CENTER);
		this.setScene(new Scene(root, 500, 400));
		this.show();
	}

	/**
	 * Private method to help output the file report 1 - farm report 2 - yearly
	 * report 3 - Monthly report 4 - Date range report
	 * 
	 * @param outputFormat - the type of report desired
	 */
	private void farmReportFileOutput() {
		if (inProgress == true) {
			return;
		}
		this.inProgress = true;
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
		extraInput.getChildren().addAll(hbox, doneButton);
		extraInput.setAlignment(Pos.CENTER);
		root.setCenter(extraInput);

		EventHandler<ActionEvent> event2 = new EventHandler<ActionEvent>() {
			public void handle(ActionEvent e) {

				// farm id input absent
				if (farmIDTextField.getText().compareTo("") == 0) {
					Alert alert = new Alert(AlertType.WARNING, "Must enter a Farm ID");
					alert.setHeaderText("ERROR WARNING");
					alert.showAndWait();
				}

				// year is empty
				if (yearTextField.getText().compareTo("") == 0) {
					String farmId = farmIDTextField.getText();
					// requesting all data
					try {
						new FarmReport(farmId, -1, "file", 1);
					} catch (Exception e1) {}

					// year input is not a valid integer
				} else if (!isInt(yearTextField.getText())) {
					Alert alert = new Alert(AlertType.WARNING,
							"Must enter a valid integer year or leave blank for all data");
					alert.setHeaderText("ERROR WARNING");
					alert.showAndWait();
					// specific year data requested
				} else {
					String farmId = farmIDTextField.getText();
					int year = Integer.parseInt(yearTextField.getText());
					try {
						new FarmReport(farmId, year, "file", 1);
					} catch (Exception e2) {}
				}
			}
		};
		doneButton.setOnAction(event2);
	}

	private void farmReportStatDisplay(int reportType) {
		if (inProgress == true) {
			return;
		}
		this.inProgress = true;
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
		extraInput.getChildren().addAll(hbox, doneButton);
		extraInput.setAlignment(Pos.CENTER);
		root.setCenter(extraInput);

		EventHandler<ActionEvent> event2 = new EventHandler<ActionEvent>() {
			public void handle(ActionEvent e) {
				if (!validateInfo(farmIDTextField, yearTextField)) {
					return;
				}
				// 1 = farmReport
				if (reportType == 1) {
					// all data requested
					if (yearTextField.getText().compareTo("") == 0) {
						String farmId = farmIDTextField.getText();
						// send a year of -1 if all data requested
						try {
							new FarmReport(farmId, -1, "stats", 1);
						} catch (Exception e1) {}
						// year is not a valid integer
					} else {
						String farmId = farmIDTextField.getText();
						int year = Integer.parseInt(yearTextField.getText());
						try {
							new FarmReport(farmId, year, "stats", 1);
						} catch (Exception e1) {}
					}
				} else if (reportType == 2) {

				} else if (reportType == 3) {

				} else if (reportType == 4) {

				}

			}
		};
		doneButton.setOnAction(event2);
	}

	/**
	 * This private helper method is used to validate the user input
	 * 
	 * @param farmIDTextField - the farm ID field user enters string into
	 * @param yearTextField   - the year field the user enters year into
	 * @return - false if any input is invalid, true otherwise
	 */
	private boolean validateInfo(TextField farmIDTextField,
			TextField yearTextField) {
		// farm id not entered
		if (farmIDTextField.getText().compareTo("") == 0) {
			Alert alert = new Alert(AlertType.WARNING, "Must enter a Farm ID");
			alert.setHeaderText("ERROR WARNING");
			alert.showAndWait();
			return false;
		} else if (farmIndex(farmIDTextField.getText()) == -1) {
			Alert alert = new Alert(AlertType.WARNING, "Must enter a valid Farm ID");
			alert.setHeaderText("ERROR WARNING");
			alert.showAndWait();
			return false;
		}
		if (!isInt(yearTextField.getText())
				&& !yearTextField.getText().equals("")) {
			Alert alert = new Alert(AlertType.WARNING,
					"Must enter a valid integer year or leave blank for all data");
			alert.setHeaderText("ERROR WARNING");
			alert.showAndWait();
			return false;
		}
		return true;
	}

	/**
	 * This is a helper method to get the index of the farm in the arraylist
	 * 
	 * @param farmID - the String version of the FarmID
	 * @return - the index of the farm in the arraylist or -1 if it is not there
	 */
	private int farmIndex(String farmID) {
		for (int i = 0; i < manager.farms.size(); i++) {
			if (manager.farms.get(i).farmID.compareTo(farmID) == 0) {
				return i;
			}
		}
		return -1;
	}

	class FarmReport extends Stage {
		private BorderPane root = new BorderPane();
		private Label farmLabel;
		private int[] milkWeight;

		public FarmReport(String farmId, int year, String option, int type) throws Exception {
			if (option.compareTo("stats") == 0)
				statDisplay(farmId, year, type);
			else if (option.compareTo("file") == 0)
				fileOutput(farmId, year, type);
		}

		public void fileOutput(String farmId, int year, int type) throws Exception {
			try {
				sumWeights(farmId);
			}
			catch(Exception e) {
				Alert alert = new Alert(AlertType.WARNING, "Please enter a valid farm ID to output data.");
				alert.setHeaderText("Farm ID does not exist.");
				alert.showAndWait();
				return;
			}
			// farm report
			if (type == 1) {
				File csvFile = new File(
						"Milk_weight_farm_report-" + outputNum + ".csv");
				FileWriter writer;
				try {
					writer = new FileWriter(csvFile);

					writer.write("Farm ID: " + farmId + ", Year: " + year + "\n");
					writer.write("Month,Avg Weight,Percent of Total Milk\n");

					double percent = 0;
					// determine if specific year or all data was requested
					if (year == -1) {
						milkWeight = sumWeights(farmId);
					} else {
						milkWeight = sumWeights(farmId, year);
					}
					for (int i = 1; i <= 12; i++) {
						if (manager.totalWeight[i - 1] != 0) {
							percent = Math.round(100 * ((double) milkWeight[i - 1]
									/ (double) manager.totalWeight[i - 1]));
						} else {
							percent = 0;
						}
						writer.write(i + "," + milkWeight[i - 1] + "," + percent + "\n");
					}
					if (writer != null) {
						Alert alert = new Alert(AlertType.INFORMATION,
								"New file, " + csvFile + " has been created.");
						alert.setHeaderText("Confirmed stats file was created");
						alert.showAndWait();
					}
					writer.close();
					outputNum++;
				} catch (IOException e) {
					if (csvFile.delete()) {
						Alert alert = new Alert(AlertType.INFORMATION,
								"File did not complete building.");
						alert.setHeaderText("There was an error in the file creation.");
						alert.showAndWait();
					}

				}

			}
		}

		public void statDisplay(String farmId, int year, int type) throws Exception {
			try {
				sumWeights(farmId);
			}
			catch(Exception e) {
				Alert alert = new Alert(AlertType.WARNING, "Please enter a valid farm ID to output data.");
				alert.setHeaderText("Farm ID does not exist.");
				alert.showAndWait();
				return;
			}
			farmLabel = new Label("Farm ID: " + farmId);
			// set up a vbox to display all values
			VBox vbox = new VBox();
			root = new BorderPane(vbox);
			root.setPadding(new Insets(15));
			root.setTop(farmLabel);

			// farm report
			if (type == 1) {
				// get the statistics
				Text info;
				// info = new Text("Company total milk weight: " + manager.totalWeight);
				// vbox.getChildren().add(info);
				/* Find Total Milk weight : percentage of Total each month */
				// determine is specific year or all data was requested
				if (year == -1) {
					milkWeight = sumWeights(farmId);
					info = new Text(
							"Average farm weights per month, and percentage of month totals for all data.\n");
					vbox.getChildren().add(info);
				} else {
					milkWeight = sumWeights(farmId, year);
					info = new Text(
							"Average farm weights per month, and percentage of month totals for the year "
									+ year + ".\n");
					vbox.getChildren().add(info);
				}
				double percent = 0;
				for (int i = 1; i <= 12; i++) {
					if (manager.totalWeight[i - 1] != 0) {
						percent = Math.round(100 * ((double) milkWeight[i - 1]
								/ (double) manager.totalWeight[i - 1]));
					} else {
						percent = 0;
					}
					info = new Text("Month " + i + " Weight: " + milkWeight[i - 1]
							+ " Percent of total milk across all farms for Month : "
							+ percent);

					vbox.getChildren().add(info);
				}
			}

			// create the scene
			this.setTitle("Farm ID: " + farmId);
			farmLabel.setFont(Font.font(50));
			BorderPane.setAlignment(farmLabel, Pos.CENTER);
			this.setScene(new Scene(root, 500, 400));
			this.show();
		}

		/**
		 * this method is used to find the sum of all milk weights for a farm in a
		 * given year.
		 * 
		 * @param farmId - the farm to sum the weights
		 * @return - the sum of weights in the farm
		 * @throws Exception 
		 */
		private int[] sumWeights(String farmId, int year) throws Exception {
			int[] averages = new int[12];
			int index = farmIndex(farmId);
			if(index == -1) {
				throw new Exception();
			} 
			else {
				Farm farm = manager.farms.get(index);
				for (int i = 0; i < farm.milk.size(); i++) {
					if (farm.getYear(i) == year) {
						int month = farm.getMonth(i) - 1;
						averages[month] += farm.getWeight(i);
					}
				}
			}
			return averages;
		}

		/**
		 * this method is used to find the sum of all milk weights for a farm over
		 * all years.
		 * 
		 * @param farmId - the farm to sum the weights
		 * @return - the sum of weights in the farm
		 * @throws Exception 
		 */
		private int[] sumWeights(String farmId) throws Exception {
			int[] averages = new int[12];
			int index = farmIndex(farmId);
			if(index == -1) {
				throw new Exception();
			}
			else {
				Farm farm = manager.farms.get(index);
				for (int i = 0; i < farm.milk.size(); i++) {
					int month = farm.getMonth(i) - 1;
					averages[month] += farm.getWeight(i);
				}
			}
			return averages;
		}

	}

	/**
	 * This helper method is used to determine if input is an integer
	 * 
	 * @param input - the user input
	 * @return - true if the value is an int
	 */
	private boolean isInt(String input) {
		try {
			Integer.parseInt(input);
		} catch (Exception e) {
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
