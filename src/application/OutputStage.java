/**
 * OutputStage.java 
 *
 * Author: Ishaan Backliwal and Mason Batchelor
 * Date: @date Apr 22, 2020
 * 
 * Course: CS400
 * Semester: Spring 2020
 * Lecture: 001
 * 
 * Other Credits: 
 */
package application;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;

import javafx.beans.binding.StringExpression;
import javafx.beans.property.ReadOnlyIntegerWrapper;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.StringConverter;
import javafx.scene.control.DateCell;

/**
 * OutputStage - TODO Describe purpose of this user-defined type
 * 
 * @author backliwal ishaanbackliwal
 *
 */

public class OutputStage extends Stage {

	private BorderPane root = new BorderPane(); // the root
	private FarmManager manager; // the back end data structure
	private boolean inProgress = false; // tells if an output option is inprogress
	VBox extraInput = new VBox(10); // vBox for formatting
	int farmReportNum = 0; // number of farm report files made
	int annualReportNum = 0; // number of annual report files made
	int monthlyReportNum = 0; // number of monthly report files made
	int dateRangeReportNum = 0; // number of date range report files made
	DecimalFormat df = new DecimalFormat("#.###"); // the rounding for percents

	/**
	 * This sets up the output stage and different output options
	 * 
	 * @param manager - the structure storing all of the information
	 */
	OutputStage(FarmManager manager) {
		// set rounding for doubles
		df.setRoundingMode(RoundingMode.CEILING);

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
		// set the formatting of the combo box and other screen displays
		comboBox.setPromptText("Select an Option");
		hbox.getChildren().addAll(comboBox, goButton);
		hbox.setAlignment(Pos.CENTER);
		selectFields.getChildren().add(hbox);
		selectFields.setAlignment(Pos.TOP_CENTER);
		root.setPadding(new Insets(40));
		root.setTop(selectFields);

		// handle button press of the GO button
		EventHandler<ActionEvent> event = new EventHandler<ActionEvent>() {
			public void handle(ActionEvent e) {
				// this clears the display each time a new option is selected
				inProgress = false;
				extraInput.getChildren().clear();
				// if no value is selected alert the user
				if (comboBox.getValue() == null) {
					Alert alert = new Alert(AlertType.WARNING,
							"Must select an option from the drop down.");
					alert.setHeaderText("ERROR WARNING");
					alert.showAndWait();
				} else if (comboBox.getValue()
						.compareTo("STAT DISPLAY: Farm Report") == 0) {
					// farm report statistics display
					farmReportFileOutput("stats");
				} else if (comboBox.getValue()
						.compareTo("STAT DISPLAY: Annual Report") == 0) {
					// annual report statistics display
					annualReportOutput("stats");
				} else if (comboBox.getValue()
						.compareTo("STAT DISPLAY: Monthly Report") == 0) {
					// monthly report statistics display
					monthlyReportOutput("stats");
				} else if (comboBox.getValue()
						.compareTo("STAT DISPLAY: Date Range Report") == 0) {
					// date range report statistics display
					dateRangeReportOutput("stats");
				} else if (comboBox.getValue()
						.compareTo("FILE OUTPUT: Farm Report") == 0) {
					farmReportFileOutput("file");
				} else if (comboBox.getValue()
						.compareTo("FILE OUTPUT: Annual Report") == 0) {
					// annual report file output
					annualReportOutput("file");
				} else if (comboBox.getValue()
						.compareTo("FILE OUTPUT: Monthly Report") == 0) {
					// monthly report file output
					monthlyReportOutput("file");
				} else if (comboBox.getValue()
						.compareTo("FILE OUTPUT: Date Range Report") == 0) {
					// date range report file output
					dateRangeReportOutput("file");
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

////////////////////////////////////////////////////////////////////////////////
///																		   	 ///
///  							FARM REPORT									 ///
///																		   	 ///
////////////////////////////////////////////////////////////////////////////////
	/**
	 * This method is used to create a new Report object if farmReport is chosen.
	 * 
	 * @param type - file or stats choice
	 */
	private void farmReportFileOutput(String type) {
		// Prevents multiple displays from being shown.
		if (inProgress == true) {
			return;
		}
		this.inProgress = true;
		HBox hbox = new HBox();
		Button doneButton = new Button("Done!");

		// text fields
		TextField farmIDTextField = new TextField();
		TextField yearTextField = new TextField();

		// text field vboxes to add labels
		VBox farmIDBox = new VBox();
		Label farmIDLabel = new Label("Farm ID:");
		farmIDBox.getChildren().addAll(farmIDLabel, farmIDTextField);
		VBox yearBox = new VBox();
		Label yearLabel = new Label("Year (leave blank if all data desired):");
		yearBox.getChildren().addAll(yearLabel, yearTextField);

		// Set the alignment and positions of the boxes
		hbox.getChildren().addAll(farmIDBox, yearBox);
		hbox.setAlignment(Pos.CENTER);
		extraInput.getChildren().addAll(hbox, doneButton);
		extraInput.setAlignment(Pos.CENTER);
		root.setCenter(extraInput);

		// event habdler for the DONE button
		EventHandler<ActionEvent> event2 = new EventHandler<ActionEvent>() {
			public void handle(ActionEvent e) {
				// validate the information input
				if (!validateInfo(farmIDTextField, yearTextField, null, null, true)) {
					return;
				}
				// year is empty
				if (yearTextField.getText().compareTo("") == 0) {
					String farmId = farmIDTextField.getText();
					// requesting all data
					try {
						new Reports(farmId, -1, type, 1);
					} catch (Exception e1) {
					}
				} else { // otherwise get the year
					String farmId = farmIDTextField.getText();
					int year = Integer.parseInt(yearTextField.getText());
					try {
						new Reports(farmId, year, type, 1);
					} catch (Exception e2) {
					}
				}
			}
		};
		doneButton.setOnAction(event2);
	}

////////////////////////////////////////////////////////////////////////////////
///																		   	 ///
///  							ANNUAL REPORT								 ///
///																		   	 ///
////////////////////////////////////////////////////////////////////////////////
	/**
	 * This method is used to create a new report for annual report option
	 * 
	 * @param type file or stats choice
	 */
	private void annualReportOutput(String type) {
		// Prevents multiple displays from being shown.
		if (inProgress == true) {
			return;
		}
		this.inProgress = true;
		HBox hbox = new HBox();
		Button doneButton = new Button("Done!");

		// text field
		TextField yearTextField = new TextField();

		// text field vbox
		VBox yearBox = new VBox();
		Label yearLabel = new Label("Year:");
		yearBox.getChildren().addAll(yearLabel, yearTextField);

		// Box set up and alignment options
		hbox.getChildren().addAll(yearBox);
		hbox.setAlignment(Pos.CENTER);
		extraInput.getChildren().addAll(hbox, doneButton);
		extraInput.setAlignment(Pos.CENTER);
		root.setCenter(extraInput);

		// event handler for the done button
		EventHandler<ActionEvent> event = new EventHandler<ActionEvent>() {
			public void handle(ActionEvent e) {
				// validate if the info provided is valid
				if (!validateInfo(null, yearTextField, null, null, false)) {
					return;
				}
				// get the year from the text field and create a new report
				int year = Integer.parseInt(yearTextField.getText());
				try {
					if (type.equals("file")) {
						new Reports(null, year, "file", 2);
					}
					if (type.equals("stats")) {
						new Reports(null, year, "stats", 2);
					}

				} catch (Exception e1) {

				}
			}
		};
		doneButton.setOnAction(event);
	}

////////////////////////////////////////////////////////////////////////////////
///																		   	 ///
///  							MONTHLY REPORT								 ///
///																		   	 ///
////////////////////////////////////////////////////////////////////////////////
	/**
	 * this method is used to create the monthly reports
	 * 
	 * @param type file or stats choice
	 */
	private void monthlyReportOutput(String type) {
		// Prevents multiple displays from being shown.
		if (inProgress == true) {
			return;
		}
		this.inProgress = true;

		HBox hbox = new HBox();
		Button doneButton = new Button("Done!");

		// text fields
		TextField yearTextField = new TextField();
		TextField monthTextField = new TextField();

		// text field vboxes
		VBox yearBox = new VBox();
		Label yearLabel = new Label("Year:");
		yearBox.getChildren().addAll(yearLabel, yearTextField);
		VBox monthBox = new VBox();
		Label monthLabel = new Label("Month:");
		monthBox.getChildren().addAll(monthLabel, monthTextField);

		// Box set up for display and alignment settings
		hbox.getChildren().addAll(yearBox, monthBox);
		hbox.setAlignment(Pos.CENTER);
		extraInput.getChildren().addAll(hbox, doneButton);
		extraInput.setAlignment(Pos.CENTER);
		root.setCenter(extraInput);

		// 0. Create event Handler for DONE
		EventHandler<ActionEvent> event = new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				// validate the information provided
				if (!validateInfo(null, yearTextField, monthTextField, null, false)) {
					return;
				}
				// retrieve the year and month
				int year = Integer.parseInt(yearTextField.getText());
				int month = Integer.parseInt(monthTextField.getText());
				// create the report option of choice
				try {
					if (type.equals("file")) {
						new Reports(null, year, month, "file", 3);
					}
					if (type.equals("stats")) {
						new Reports(null, year, month, "stats", 3);
					}
					// catch any exceptions and alert the user
				} catch (Exception e1) {
					Alert alert = new Alert(AlertType.WARNING,
							"Report was unable to be created");
					alert.setHeaderText("ERROR: Please try again with valid input");
					alert.showAndWait();
				}

			}

		};
		doneButton.setOnAction(event);

	}

////////////////////////////////////////////////////////////////////////////////
///																		   	 ///
///  						  DATE RANGE REPORT								 ///
///																		   	 ///
////////////////////////////////////////////////////////////////////////////////
	/**
	 * This method is used to create the date range reports
	 * 
	 * @param type stats or file choice of output
	 */
	private void dateRangeReportOutput(String type) {
		// Prevents multiple displays from being shown.
		if (inProgress == true) {
			return;
		}
		this.inProgress = true;
		// set up the buttons
		HBox hbox = new HBox();
		Button doneButton = new Button("Done!");
		Button continueButton = new Button("Continue");

		// create the calendars to pick the start date
		VBox startDate = new VBox();
		Label startDateLabel = new Label("Date");
		DatePicker startDatePicker = new DatePicker();
		startDatePicker.setPromptText("yyyy-mm-dd");
		startDatePicker.setConverter(convertDateFormat());
		startDatePicker.requestFocus();
		startDate.getChildren().addAll(startDateLabel, startDatePicker);

		// create calendar to pick the end date
		VBox endDate = new VBox();
		Label dateLabel = new Label("Date");
		DatePicker endDatePicker = new DatePicker();
		endDatePicker.setPromptText("yyyy-mm-dd");
		endDatePicker.setConverter(convertDateFormat());
		endDatePicker.requestFocus();
		endDate.getChildren().addAll(dateLabel, endDatePicker);

		// set formatting alignments and font sizes
		startDate.setAlignment(Pos.TOP_CENTER);
		endDate.setAlignment(Pos.TOP_CENTER);
		Label startLabel = new Label("Start Date Info ");
		Text infoText = new Text("Press done to choose an end date");
		Label endLabel = new Label("End Date Info ");
		startLabel.setFont(new Font(24));
		endLabel.setFont(new Font(24));

		// combine all input displays for final scene
		extraInput.getChildren().addAll(startLabel, infoText, startDate,
				continueButton);
		extraInput.setPadding(new Insets(10, 0, 0, 0));
		extraInput.setAlignment(Pos.TOP_CENTER);
		root.setCenter(extraInput);

		// 0. Create event Handler for DONE
		EventHandler<ActionEvent> event = new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				// get the beginning date from the user (do nothing if not entered
				String begDate = leadingZeros(startDatePicker.getValue());
				String finalDate = "";
				// if a date has been selected
				if (begDate != null && !begDate.equals("")) {
					// retrieve the parts of the date
					int startYear = Integer.parseInt(begDate.split("-")[0]);
					int startMonth = Integer.parseInt(begDate.split("-")[1]);
					int startDay = Integer.parseInt(begDate.split("-")[2]);
					// establish the max date and min date user can choose for end
					LocalDate maxDate = LocalDate.of(startYear, 12, 31);
					LocalDate minDate = LocalDate.of(startYear, startMonth, startDay);
					// set restrictions on what dates the user can choose for the end date
					endDatePicker.setDayCellFactory(d -> new DateCell() {
						public void updateItem(LocalDate item, boolean empty) {
							super.updateItem(item, empty);
							setDisable(item.isAfter(maxDate) || item.isBefore(minDate));
						}
					});
					// restructure the output and display the end date picker
					extraInput.getChildren().remove(continueButton);
					extraInput.getChildren().remove(infoText);
					Text infoText = new Text("Press done once date is entered");
					extraInput.getChildren().addAll(endLabel, infoText, endDate,
							doneButton);
					// call doneButtonAction when both dates are entered
					doneButton.setOnAction(l -> {
						doneButtonAction(finalDate, begDate);
					});
				}
			}

			/**
			 * Private helper method to assist with create the date range reports
			 * 
			 * @param finalDate - the last date of data user wants displayed
			 * @param begDate   - the beginning date of data the user wants displayed.
			 */
			private void doneButtonAction(String finalDate, String begDate) {
				finalDate = leadingZeros(endDatePicker.getValue());
				// ensure that the the end date is valid and has been chosen
				if (finalDate != null && !finalDate.equals("")) {
					// create the stats or file reports
					try {
						if (type.equals("file")) {
							new Reports(begDate, finalDate, "file");

						}
						if (type.equals("stats")) {
							new Reports(begDate, finalDate, "stats");

						}

					} catch (Exception e1) {
						Alert alert = new Alert(AlertType.WARNING,
								"Report was unable to be created");
						alert.setHeaderText("ERROR: Please try again with valid input");
						alert.showAndWait();
					}
				}
			}
		};
		continueButton.setOnAction(event);

	}

////////////////////////////////////////////////////////////////////////////////
///																		   	 ///
///  							OTHER METHODS								 ///
///																		   	 ///
////////////////////////////////////////////////////////////////////////////////
	/**
	 * This private helper method is used to validate the user input
	 * 
	 * @param farmIDTextField - the farm ID field user enters string into
	 * @param yearTextField   - the year field the user enters year into
	 * @param farmIDConditon  - true if farm Id is needed, false otherwise
	 * @return - false if any input is invalid, true otherwise
	 */
	private boolean validateInfo(TextField farmIDTextField,
			TextField yearTextField, TextField monthTextField, TextField dayTextField,
			boolean farmIDCondition) {
		int month = 0;
		// if farm Id is needed
		if (farmIDCondition) {
			// farm id not entered alert user
			if (farmIDTextField.getText().compareTo("") == 0) {
				Alert alert = new Alert(AlertType.WARNING, "Must enter a Farm ID");
				alert.setHeaderText("ERROR WARNING");
				alert.showAndWait();
				return false;
			} else if (farmIndex(farmIDTextField.getText()) == -1) {
				Alert alert = new Alert(AlertType.WARNING,
						"Must enter a valid Farm ID");
				alert.setHeaderText("ERROR WARNING");
				alert.showAndWait();
				return false;
			}
			// only checked if farm id is valid
			if (yearTextField.getText().equals("")) {
				/* all data is requested */
			} else {
				// else check if the year is positive
				try {
					int year = Integer.parseInt(yearTextField.getText());
					if (year < 0) {
						throw new Exception();
					}
				} catch (Exception e) {
					Alert alert = new Alert(AlertType.WARNING,
							"Must enter a valid integer year");
					alert.setHeaderText("ERROR WARNING");
					alert.showAndWait();
					return false;
				}
			}
		}
		// if a year is required and no farm Report is needed
		if (yearTextField != null && farmIDCondition == false) {
			// make sure the value is a postive integer
			try {
				if (yearTextField.getText().equals("")) {
					throw new Exception();
				}
				int year = Integer.parseInt(yearTextField.getText());
				if (year < 0) {
					throw new Exception();
				}
			} catch (Exception e) {
				Alert alert = new Alert(AlertType.WARNING,
						"Must enter a valid integer year");
				alert.setHeaderText("ERROR WARNING");
				alert.showAndWait();
				return false;
			}
		}
		// validate the month text field
		if (monthTextField != null) {
			try {
				month = Integer.parseInt(monthTextField.getText());
				if ((month > 12 || month < 1) || monthTextField.getText().equals("")) {
					throw new Exception();

				}
			} catch (Exception e) {
				Alert alert = new Alert(AlertType.WARNING,
						"Must enter a valid integer month");
				alert.setHeaderText("ERROR WARNING");
				alert.showAndWait();
				return false;
			}
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

////////////////////////////////////////////////////////////////////////////////
///																		   	 ///
///  							REPORTS CLASS								 ///
///																		   	 ///
////////////////////////////////////////////////////////////////////////////////
	class Reports extends Stage {
		private BorderPane root = new BorderPane();
		private Label farmLabel;
		private int[] milkWeight;

		/**
		 * CONSTRUCTOR
		 * 
		 * @param type - 1 = farm report - 2 = annual report - 3 = monthly report -
		 *             4 = date range report
		 */
		public Reports(String farmId, int year, String option, int type)
				throws Exception {
			if (option.compareTo("stats") == 0)
				statDisplay(farmId, year, -1, type);// month = -1 since unneeded
			else if (option.compareTo("file") == 0)
				fileOutput(farmId, year, -1, type); // month = -1 since unneeded
		}

		/**
		 * Constructor for a monthyl report
		 * 
		 * @param farmId - the farm Id desired
		 * @param year   - the year
		 * @param month  - the month
		 * @param option - file or stats
		 * @param type   - the type fo report
		 * @throws Exception - throw any issues in creating the report
		 */
		public Reports(String farmId, int year, int month, String option, int type)
				throws Exception {
			// used when a monthly report is requetsted
			if (option.compareTo("stats") == 0)
				statDisplay(farmId, year, month, type);
			else if (option.compareTo("file") == 0)
				fileOutput(farmId, year, month, type);
		}

		/**
		 * This is the consrtuctor for the date range reports
		 * 
		 * @param startYear - the starting date
		 * @param endYear   - the ending date
		 * @param option    - the option of report
		 */
		public Reports(String startYear, String endYear, String option) {
			if (option.compareTo("stats") == 0)
				rangeStatDisplay(startYear, endYear);
			else if (option.compareTo("file") == 0)
				rangeFileOutput(startYear, endYear);
		}

		/**
		 * this method is used to display the milk weights for all farms with data
		 * within that range
		 * 
		 * @param startYear - the beginning date desired
		 * @param endYear   - the end date desired
		 */
		private void rangeStatDisplay(String startDate, String endDate) {
			// set up a vbox to display all values
			VBox vbox = new VBox();
			root = new BorderPane(vbox);
			root.setPadding(new Insets(15));

			// stage title
			this.setTitle("Data Report for date range");
			farmLabel = new Label("Data Report from " + startDate + " to " + endDate);
			farmLabel.setFont(Font.font(30));
			root.setTop(farmLabel);

			// 1. split input data
			int startYear = Integer.parseInt(startDate.split("-")[0]);
			int startMonth = Integer.parseInt(startDate.split("-")[1]);
			int startDay = Integer.parseInt(startDate.split("-")[2]);
			int endYear = Integer.parseInt(endDate.split("-")[0]);
			int endMonth = Integer.parseInt(endDate.split("-")[1]);
			int endDay = Integer.parseInt(endDate.split("-")[2]);
			// 2. find all farms in the given year
			ArrayList<String> farms = findFarms(startYear);
			// 3. find all farms in month range
			ArrayList<String> range = findFarms(startMonth, startDay, endMonth,
					endDay, farms);
			ArrayList<Integer> weights = new ArrayList<Integer>();
			ArrayList<String> percents = new ArrayList<String>();
			TableView<Object> tableView = new TableView<Object>();
			int totalWeight = 0;

			// Get the total weight of all valid farms
			for (int i = 0; i < range.size(); i++) {
				String farmId = range.get(i);
				int index = farmIndex(farmId);
				Farm farm = manager.farms.get(index);
				for (int j = 0; j < farm.milk.size(); j++) {
					// if the milk has a valid date add it to the total
					if (farm.getYear(j) == startYear && farm.getMonth(j) >= startMonth
							&& farm.getDay(j) >= startDay && farm.getMonth(j) <= endMonth
							&& farm.getDay(j) <= endDay) {
						totalWeight += farm.getWeight(j);
					}
				}
			}
			// get the weight for each farm within the range
			double percent = 0;
			for (int i = 0; i < range.size(); i++) {
				int farmWeight = getRangeWeight(range.get(i), startMonth, startDay,
						endMonth, endDay);
				if (farmWeight != 0) {
					percent = (((double) farmWeight / (double) totalWeight) * 100.0);
				} else {
					percent = 0;
				}
				// add the weights to the column and percents to column
				weights.add(farmWeight);
				percents.add(df.format(percent) + "%");
			}
			for (int i = 0; i < farms.size(); i++) {
				tableView.getItems().add(i + "");
			}

			// Make the columns for the table
			TableColumn<Object, String> farmIDColumn = new TableColumn<Object, String>(
					"Farm ID");
			farmIDColumn.setMinWidth(100);
			TableColumn<Object, Integer> totalWeightColumn = new TableColumn<Object, Integer>(
					"Total Weight");
			totalWeightColumn.setMinWidth(125);
			TableColumn<Object, String> percentColumn = new TableColumn<Object, String>(
					"Percent Weight Compared To All Farms in Range");

			// format the columns of the tableView
			farmIDColumn.setCellValueFactory(cellData -> {
				int rowIndex = Integer.parseInt((String) cellData.getValue());
				return new ReadOnlyStringWrapper(range.get(rowIndex));
			});
			totalWeightColumn.setCellValueFactory(cellData -> {
				int rowIndex = Integer.parseInt((String) cellData.getValue());
				return new ReadOnlyObjectWrapper<>(weights.get(rowIndex));
			});
			percentColumn.setCellValueFactory(cellData -> {
				int rowIndex = Integer.parseInt((String) cellData.getValue());
				return new ReadOnlyStringWrapper(percents.get(rowIndex));
			});

			// set sorting for farmID
			farmIDColumn.setComparator((new Comparator<String>() {
				public int compare(String o1, String o2) {
					return extractInt(o1) - extractInt(o2);
				}

				int extractInt(String s) {
					String num = s.replaceAll("\\D", "");
					// return 0 if no digits found
					return num.isEmpty() ? 0 : Integer.parseInt(num);
				}
			}));
			// add all of the information into the tableView
			tableView.getColumns().add(farmIDColumn);
			tableView.getColumns().add(totalWeightColumn);
			tableView.getSortOrder().add(totalWeightColumn);
			tableView.getColumns().add(percentColumn);
			vbox.getChildren().add(tableView);

			// create the scene
			BorderPane.setAlignment(farmLabel, Pos.CENTER);
			this.setScene(new Scene(root, 600, 400));
			this.show();
		}

		/**
		 * This method is used to write the milk weights for all farms with data
		 * within the date range to a file
		 * 
		 * @param startDate - the beginning date desired
		 * @param endDate   - the end date desired
		 */
		private void rangeFileOutput(String startDate, String endDate) {

			// 1. split input data
			int startYear = Integer.parseInt(startDate.split("-")[0]);
			int startMonth = Integer.parseInt(startDate.split("-")[1]);
			int startDay = Integer.parseInt(startDate.split("-")[2]);
			int endYear = Integer.parseInt(endDate.split("-")[0]);
			int endMonth = Integer.parseInt(endDate.split("-")[1]);
			int endDay = Integer.parseInt(endDate.split("-")[2]);

			// 2. find all farms in the given year
			ArrayList<String> farms = findFarms(startYear);

			// 3. find all farms in month range
			ArrayList<String> range = findFarms(startMonth, startDay, endMonth,
					endDay, farms);
			ArrayList<Integer> weights = new ArrayList<Integer>();
			ArrayList<String> percents = new ArrayList<String>();

			int totalWeight = 0;

			// Get the total weight of all valid farms
			for (int i = 0; i < range.size(); i++) {
				String farmId = range.get(i);
				int index = farmIndex(farmId);
				Farm farm = manager.farms.get(index);
				for (int j = 0; j < farm.milk.size(); j++) {
					// if the milk has a valid date add it to the total
					if (farm.getYear(j) == startYear && farm.getMonth(j) >= startMonth
							&& farm.getDay(j) >= startDay && farm.getMonth(j) <= endMonth
							&& farm.getDay(j) <= endDay) {
						totalWeight += farm.getWeight(j);
					}
				}
			}
			// get the weight for each farm within the range
			double percent = 0;
			for (int i = 0; i < range.size(); i++) {
				int farmWeight = getRangeWeight(range.get(i), startMonth, startDay,
						endMonth, endDay);
				if (farmWeight != 0) {
					percent = (((double) farmWeight / (double) totalWeight) * 100.0);
				} else {
					percent = 0;
				}
				weights.add(farmWeight);
				percents.add(df.format(percent) + "%");
			}

			// writing to the file
			File csvFile = new File(
					"Milk_weight_date_range_report-" + dateRangeReportNum + ".csv");
			FileWriter writer;

			try {
				writer = new FileWriter(csvFile);
				writer
						.write("Start Date: " + startDate + ",End Date: " + endDate + "\n");
				writer
						.write("FarmID,Total Weight in Date Range,Percent of Total Milk\n");
				// write the values as a CSV file format
				for (int i = 0; i < farms.size(); i++) {
					writer.write(farms.get(i) + "," + weights.get(i) + ","
							+ percents.get(i) + "\n");
				}
				if (writer != null) {
					Alert alert = new Alert(AlertType.INFORMATION,
							"New file, " + csvFile + " has been created.");
					alert.setHeaderText("Confirmed data file was created");
					alert.showAndWait();
				}
				writer.close();
				dateRangeReportNum++;
			} catch (Exception e) {
				if (csvFile.delete()) {
					Alert alert = new Alert(AlertType.INFORMATION,
							"File did not complete building.");
					alert.setHeaderText("There was an error in the file creation.");
					alert.showAndWait();
				}
			}
		}

		/**
		 * This method is used to get the farm weight within the range of dates
		 * 
		 * @param farmId     - used to find the farm index
		 * @param startMonth - the starting month
		 * @param startDay   - the starting day
		 * @param endMonth   - the end month
		 * @param endDay     - the ending day
		 * @return - return the total weight for the farm in that range of dates
		 */
		private int getRangeWeight(String farmId, int startMonth, int startDay,
				int endMonth, int endDay) {
			int farmWeight = 0;
			int index = farmIndex(farmId);
			Farm farm = manager.farms.get(index);
			for (int i = 0; i < farm.milk.size(); i++) {
				if (farm.getMonth(i) >= startMonth && farm.getDay(i) >= startDay
						&& farm.getMonth(i) <= endMonth && farm.getDay(i) <= endDay) {
					farmWeight += farm.getWeight(i);
				}
			}
			return farmWeight;
		}

		/**
		 * This method is used to determine which farms have information that is
		 * valid and should be used in the display
		 * 
		 * @param startMonth - month that range starts at
		 * @param startDay   - day that range starts at
		 * @param endMonth   - month that range ends
		 * @param endDay     - day that range ends
		 * @param farms      - all farms from the given year
		 * @return - return an arraylist of the valid farms
		 */
		private ArrayList<String> findFarms(int startMonth, int startDay,
				int endMonth, int endDay, ArrayList<String> farms) {
			// uses all farms in the valid year as refernece point
			ArrayList<String> valid = new ArrayList<String>();
			// iterate through farms valid in year
			for (int i = 0; i < farms.size(); i++) {
				Farm current = manager.farms.get(i);
				// if id is already in valid don't add it
				if (!valid.contains(current.farmID)) {
					for (int j = 0; j < current.milk.size(); j++) {
						if (current.getMonth(j) >= startMonth
								&& current.getDay(j) >= startDay) {
							valid.add(current.farmID);
							break;
						}
					}
				}
			}
			// sort all of the valid farms
			Collections.sort(valid, new Comparator<String>() {
				public int compare(String o1, String o2) {
					return extractInt(o1) - extractInt(o2);
				}

				int extractInt(String s) {
					String num = s.replaceAll("\\D", "");
					// return 0 if no digits found
					return num.isEmpty() ? 0 : Integer.parseInt(num);
				}
			});
			return valid;
		}

		/**
		 * This method is used to output the file option
		 * 
		 * @param type - 1 = farm report - 2 = annual report - 3 = monthly report
		 */
		public void fileOutput(String farmId, int year, int month, int type)
				throws Exception {

			// FARM REPORT
			if (type == 1) {
				// see if farm ID exists by summing all weights for a given farm
				try {
					sumWeights(farmId);
				} catch (Exception e) {
					// alert the user if the farm does not exist in memory
					Alert alert = new Alert(AlertType.WARNING,
							"Please enter a valid farm ID to output data.");
					alert.setHeaderText("Farm ID does not exist.");
					alert.showAndWait();
					return;
				}
				// create the new file and writer
				File csvFile = new File(
						"Milk_weight_farm_report-" + farmReportNum + ".csv");
				FileWriter writer;
				try {
					writer = new FileWriter(csvFile);
					writer.write("Farm ID: " + farmId + ", Year: " + year + "\n");
					writer.write("Month,Avg Weight,Percent of Total Milk\n");

					double percent = 0;
					// determine if specific year or all data was requested
					if (year == -1) {
						// add data
						milkWeight = sumWeights(farmId);
					} else {
						// specific year
						milkWeight = sumWeights(farmId, year);
					}
					// determine the percentage of milk out of the total for the milk
					// manager in that particular month
					for (int i = 1; i <= 12; i++) {
						if (manager.totalWeight[i - 1] != 0) {
							percent = (100 * ((double) milkWeight[i - 1]
									/ (double) manager.totalWeight[i - 1]));
						} else {
							percent = 0;
						}
						writer.write(
								i + "," + milkWeight[i - 1] + "," + df.format(percent) + "\n");
					}
					// tell user if the file was successfully created and its name
					if (writer != null) {
						Alert alert = new Alert(AlertType.INFORMATION,
								"New file, " + csvFile + " has been created.");
						alert.setHeaderText("Confirmed data file was created");
						alert.showAndWait();
					}
					writer.close();
					farmReportNum++;
				} catch (IOException e) {
					if (csvFile.delete()) {
						Alert alert = new Alert(AlertType.INFORMATION,
								"File did not complete building.");
						alert.setHeaderText("There was an error in the file creation.");
						alert.showAndWait();
					}

				}

			}

			// ANNUAL REPORT
			if (type == 2) {
				File csvFile = new File(
						"Milk_weight_annual_report-" + annualReportNum + ".csv");
				FileWriter writer;
				ArrayList<String> farms = findFarms(year);

				// find total weight
				int totalWeight = 0;
				for (int i = 0; i < manager.farms.size(); i++) {
					for (int m = 0; m < manager.farms.get(i).size(); m++) {
						totalWeight += manager.farms.get(i).getWeight(m);
					}
				}
				double percent = 0;
				try {
					writer = new FileWriter(csvFile);
					writer.write("Year: " + year + "\n");
					writer.write("FarmID,Farm Total Weight,Percent of Total Milk\n");
					// determine the total weight for the farms in a given year
					for (int i = 0; i < farms.size(); i++) {
						int farmWeight = sumOfFarmWeightsByYear(farms.get(i), year);
						// determine percent of each farm from totalWeight of all farms
						if (farmWeight != 0) {
							percent = (((double) farmWeight / (double) totalWeight) * 100.0);
						} else {
							percent = 0;
						}
						// write to the file
						writer.write(farms.get(i) + "," + farmWeight + ","
								+ df.format(percent) + "\n");
					}
					// alert user if the file was created
					if (writer != null) {
						Alert alert = new Alert(AlertType.INFORMATION,
								"New file, " + csvFile + " has been created.");
						alert.setHeaderText("Confirmed data file was created");
						alert.showAndWait();
					}
					writer.close();
					annualReportNum++;
				} catch (IOException e) {
					if (csvFile.delete()) {
						Alert alert = new Alert(AlertType.INFORMATION,
								"File did not complete building.");
						alert.setHeaderText("There was an error in the file creation.");
						alert.showAndWait();
					}
				}
			}

			// MONTHLY REPORT
			if (type == 3) {
				File csvFile = new File(
						"Milk_weight_monthly_report-" + monthlyReportNum + ".csv");
				FileWriter writer;
				ArrayList<String> farms = findFarms(year);
				int monthTotal = 0;

				// find all farms with data for the given month and year
				for (int i = 0; i < manager.farms.size(); i++) {
					Farm farm = manager.farms.get(i);
					for (int j = 0; j < farm.milk.size(); j++) {
						// if the farm has milk for given month and year
						if (farm.getMonth(j) == month && farm.getYear(j) == year) {
							// add the milk weight to total weight
							monthTotal += farm.getWeight(j);
							// if this farm has not been added yet
							if (!farms.contains(farm.farmID)) {
								farms.add(farm.farmID);
							}
						}
					}
				}

				double percent = 0;
				try {
					// set up the file
					writer = new FileWriter(csvFile);
					writer.write("Year: " + year + " Month: " + month + " \n");
					writer.write(
							"FarmID,Farm Monthly Total Weight,Percent of Total Milk for Month "
									+ month + " in " + year + "\n");
					// determine the sum of farms weights for a given month and year
					for (int i = 0; i < farms.size(); i++) {
						String monthId = farms.get(i);
						int farmMonth = sumOfWeightsByMonth(monthId, year, month);
						// find the percent of the farms weight from the total
						if (farmMonth != 0) {
							percent = (((double) farmMonth / (double) monthTotal) * 100.0);
						} else {
							percent = 0;
						}
						writer.write(
								monthId + ", " + farmMonth + ", " + df.format(percent) + "\n");
					}
					writer.write("\nTotal milk weight for the month is " + monthTotal);

					// tell the user if the file was successfully created
					if (writer != null) {
						Alert alert = new Alert(AlertType.INFORMATION,
								"New file, " + csvFile + " has been created.");
						alert.setHeaderText("Confirmed data file was created");
						alert.showAndWait();
					}
					writer.close();
					monthlyReportNum++;
				} catch (Exception e) {
					if (csvFile.delete()) {
						Alert alert = new Alert(AlertType.INFORMATION,
								"File did not complete building.");
						alert.setHeaderText("There was an error in the file creation.");
						alert.showAndWait();
					}
				}
			}
		}

		/**
		 * STATS DISPLAY
		 * 
		 * @param type - 1 = farm report - 2 = annual report - 3 = monthly report
		 */
		public void statDisplay(String farmId, int year, int month, int type)
				throws Exception {
			// set up a vbox to display all values
			VBox vbox = new VBox();
			root = new BorderPane(vbox);
			root.setPadding(new Insets(15));

			// farm report
			if (type == 1) {
				// stage title
				this.setTitle("Farm ID: " + farmId);
				farmLabel = new Label("Farm ID: " + farmId);
				farmLabel.setFont(Font.font(40));
				root.setTop(farmLabel);

				// table view set up
				ArrayList<Integer> months = new ArrayList<Integer>();
				ArrayList<Integer> weights = new ArrayList<Integer>();
				ArrayList<String> percents = new ArrayList<String>();
				TableView<Object> tableView = new TableView<Object>();

				// Make the columns for the table
				TableColumn<Object, Integer> monthColumn = new TableColumn<Object, Integer>(
						"Month");
				monthColumn.setMinWidth(100);
				TableColumn<Object, Integer> avgWeightColumn = new TableColumn<Object, Integer>(
						"Average Weight");
				avgWeightColumn.setMinWidth(125);
				TableColumn<Object, String> percentColumn;
				if (year == -1) {
					milkWeight = sumWeights(farmId);
					percentColumn = new TableColumn<Object, String>(
							"Percent of Month Total (All Farms)");
				} else {
					milkWeight = sumWeights(farmId, year);
					percentColumn = new TableColumn<Object, String>(
							"Percent of Month Total for " + year);
				}

				// see if farm ID exists
				try {
					sumWeights(farmId);
				} catch (Exception e) {
					Alert alert = new Alert(AlertType.WARNING,
							"Please enter a valid farm ID to output data.");
					alert.setHeaderText("Farm ID does not exist.");
					alert.showAndWait();
					return;
				}

				double percent = 0;
				// determine the percentage of the farms weight for each month out of
				// the farms total
				for (int i = 1; i <= 12; i++) {
					if (manager.totalWeight[i - 1] != 0) {
						percent = (100 * ((double) milkWeight[i - 1]
								/ (double) manager.totalWeight[i - 1]));
					} else {
						percent = 0;
					}
					months.add(i);
					weights.add(milkWeight[i - 1]);
					percents.add(df.format(percent) + "%");
				}

				// set up table
				for (int i = 0; i < 12; i++) {
					tableView.getItems().add(i + "");
				}
				monthColumn.setCellValueFactory(cellData -> {
					int rowIndex = Integer.parseInt((String) cellData.getValue());
					return new ReadOnlyObjectWrapper<Integer>(months.get(rowIndex));
				});
				avgWeightColumn.setCellValueFactory(cellData -> {
					int rowIndex = Integer.parseInt((String) cellData.getValue());
					return new ReadOnlyObjectWrapper<>(weights.get(rowIndex));
				});
				percentColumn.setCellValueFactory(cellData -> {
					int rowIndex = Integer.parseInt((String) cellData.getValue());
					return new ReadOnlyStringWrapper(percents.get(rowIndex));
				});

				// apply columns to table
				tableView.getColumns().add(monthColumn);
				tableView.getColumns().add(avgWeightColumn);
				tableView.getColumns().add(percentColumn);
				vbox.getChildren().add(tableView);

			}

			// annual report
			else if (type == 2) {
				// stage title
				this.setTitle("Data Report for " + year);
				farmLabel = new Label("Data Report for " + year);
				farmLabel.setFont(Font.font(30));
				root.setTop(farmLabel);

				// finding total weight
				int totalWeight = 0;
				for (int i = 0; i < manager.farms.size(); i++) {
					for (int m = 0; m < manager.farms.get(i).size(); m++) {
						totalWeight += manager.farms.get(i).getWeight(m);
					}
				}

				// table view set up
				ArrayList<String> farms = findFarms(year);
				ArrayList<Integer> weights = new ArrayList<Integer>();
				ArrayList<String> percents = new ArrayList<String>();
				TableView<Object> tableView = new TableView<Object>();

				// Make the columns for the table
				TableColumn<Object, String> farmIDColumn = new TableColumn<Object, String>(
						"Farm ID");
				farmIDColumn.setMinWidth(100);
				farmIDColumn.setSortable(false);
				TableColumn<Object, Integer> totalWeightColumn = new TableColumn<Object, Integer>(
						"Total Weight");
				totalWeightColumn.setMinWidth(125);
				TableColumn<Object, String> percentColumn = new TableColumn<Object, String>(
						"Percent Weight Compared To All Farms");

				double percent = 0;
				// find total weight for farms in year and their percent of the total
				for (int i = 0; i < farms.size(); i++) {
					int farmWeight = sumOfFarmWeightsByYear(farms.get(i), year);
					if (farmWeight != 0) {
						percent = (((double) farmWeight / (double) totalWeight) * 100.0);
					} else {
						percent = 0;
					}
					// add them to the correct columns
					weights.add(farmWeight);
					percents.add(df.format(percent) + "%");
				}
				// add items to the table view
				for (int i = 0; i < farms.size(); i++) {
					tableView.getItems().add(i + "");
				}
				// ensure proper formatting and return options
				farmIDColumn.setCellValueFactory(cellData -> {
					int rowIndex = Integer.parseInt((String) cellData.getValue());
					return new ReadOnlyStringWrapper(farms.get(rowIndex));
				});
				totalWeightColumn.setCellValueFactory(cellData -> {
					int rowIndex = Integer.parseInt((String) cellData.getValue());
					return new ReadOnlyObjectWrapper<>(weights.get(rowIndex));
				});
				percentColumn.setCellValueFactory(cellData -> {
					int rowIndex = Integer.parseInt((String) cellData.getValue());
					return new ReadOnlyStringWrapper(percents.get(rowIndex));
				});

				// set sorting for farm ID column
				farmIDColumn.setComparator((new Comparator<String>() {
					public int compare(String o1, String o2) {
						return extractInt(o1) - extractInt(o2);
					}

					int extractInt(String s) {
						String num = s.replaceAll("\\D", "");
						// return 0 if no digits found
						return num.isEmpty() ? 0 : Integer.parseInt(num);
					}
				}));
				// finally format the table properly
				tableView.getColumns().add(farmIDColumn);
				tableView.getColumns().add(totalWeightColumn);
				tableView.getSortOrder().add(totalWeightColumn);
				tableView.getColumns().add(percentColumn);
				vbox.getChildren().add(tableView);
			}

			// monthly report
			else if (type == 3) {
				// stage title
				this.setTitle("Data Report for " + year + " in Month " + month);
				farmLabel = new Label("Data Report for " + year + " in Month " + month);
				farmLabel.setFont(Font.font(20));
				root.setTop(farmLabel);

				// table view set up
				ArrayList<String> farms = findFarms(year);
				ArrayList<Integer> weights = new ArrayList<Integer>();
				ArrayList<String> percents = new ArrayList<String>();
				TableView<Object> tableView = new TableView<Object>();

				TableColumn<Object, String> farmIDColumn = new TableColumn<Object, String>(
						"Farm ID");
				farmIDColumn.setMinWidth(100);
				farmIDColumn.setSortable(false);
				TableColumn<Object, Integer> totalWeightColumn = new TableColumn<Object, Integer>(
						"Total Weight");
				totalWeightColumn.setMinWidth(125);
				TableColumn<Object, String> percentColumn = new TableColumn<Object, String>(
						"Percent Weight Compared To All Farms");

				int monthTotal = 0;
				// find all farms with data for the given month and year
				for (int i = 0; i < manager.farms.size(); i++) {
					Farm farm = manager.farms.get(i);
					for (int j = 0; j < farm.milk.size(); j++) {
						// if the farm has milk for given month and year
						if (farm.getMonth(j) == month && farm.getYear(j) == year) {
							// add the milk weight to total weight
							monthTotal += farm.getWeight(j);
							// if this farm has not been added yet
							if (!farms.contains(farm.farmID)) {
								farms.add(farm.farmID);
							}
						}
					}
				}
				double percent = 0;
				// display all of the info
				for (int i = 0; i < farms.size(); i++) {
					// get the farms total weight for the month
					String monthId = farms.get(i);
					int farmMonth = sumOfWeightsByMonth(monthId, year, month);
					// get the farms percent of the month total
					if (farmMonth != 0) {
						percent = (((double) farmMonth / (double) monthTotal) * 100.0);
					} else {
						percent = 0;
					}
					// add to their respective columns
					weights.add(farmMonth);
					percents.add(df.format(percent) + "%");
				}
				// add items to the table view
				for (int i = 0; i < farms.size(); i++) {
					tableView.getItems().add(i + "");
				}
				// return read only wrappers for objects at row indexes
				farmIDColumn.setCellValueFactory(cellData -> {
					int rowIndex = Integer.parseInt((String) cellData.getValue());
					return new ReadOnlyStringWrapper(farms.get(rowIndex));
				});
				totalWeightColumn.setCellValueFactory(cellData -> {
					int rowIndex = Integer.parseInt((String) cellData.getValue());
					return new ReadOnlyObjectWrapper<>(weights.get(rowIndex));
				});
				percentColumn.setCellValueFactory(cellData -> {
					int rowIndex = Integer.parseInt((String) cellData.getValue());
					return new ReadOnlyStringWrapper(percents.get(rowIndex));
				});

				// set sorting for farm ID column
				farmIDColumn.setComparator((new Comparator<String>() {
					public int compare(String o1, String o2) {
						return extractInt(o1) - extractInt(o2);
					}

					int extractInt(String s) {
						String num = s.replaceAll("\\D", "");
						// return 0 if no digits found
						return num.isEmpty() ? 0 : Integer.parseInt(num);
					}
				}));

				// finalize the format and display of the tableView
				tableView.getColumns().add(farmIDColumn);
				tableView.getColumns().add(totalWeightColumn);
				tableView.getSortOrder().add(totalWeightColumn);
				tableView.getColumns().add(percentColumn);
				vbox.getChildren().add(tableView);

			}

			// create the scene
			BorderPane.setAlignment(farmLabel, Pos.CENTER);
			this.setScene(new Scene(root, 500, 400));
			this.show();
		}

		/**
		 * Gets an array list of farm ID's that have a milk object with a certain
		 * year specification
		 * 
		 * @param year - the year to find farms with valid data
		 * @return ArrayList of strings
		 */
		private ArrayList<String> findFarms(int year) {
			// if a farm has milk data in the year it is added to arralist
			ArrayList<String> farmIDs = new ArrayList<String>();
			for (int i = 0; i < manager.farms.size(); i++) {
				for (int m = 0; m < manager.farms.get(i).size(); m++) {
					int milkYear = manager.farms.get(i).getYear(m);
					if (milkYear == year) {
						// only added if farmID's does not already contain it
						if (!farmIDs.contains(manager.farms.get(i).farmID))
							farmIDs.add(manager.farms.get(i).farmID);
					}
				}
			}
			// sort the farmID's in order
			Collections.sort(farmIDs, new Comparator<String>() {
				public int compare(String o1, String o2) {
					return extractInt(o1) - extractInt(o2);
				}

				int extractInt(String s) {
					String num = s.replaceAll("\\D", "");
					// return 0 if no digits found
					return num.isEmpty() ? 0 : Integer.parseInt(num);
				}
			});
			return farmIDs;
		}

		/**
		 * This helper method sums all milk weights for a given month and year for
		 * the farmId that was provided
		 * 
		 * @param farmId - the unique ID to sum milk weights
		 * @param year   - the year desired
		 * @param month  - the month desired
		 * @return - the sum of the farms milk weights for the date provided
		 */
		private int sumOfWeightsByMonth(String farmId, int year, int month) {
			int index = farmIndex(farmId);
			int totalWeight = 0;
			// if the farm id doesn't exist return 0.
			if (index == -1) {
				return 0;
			}
			// get the farm at the index found
			Farm farm = manager.farms.get(index);
			// iterate through the farm's milk arraylist
			for (int i = 0; i < farm.milk.size(); i++) {
				if (farm.getMonth(i) == month && farm.getYear(i) == year) {
					totalWeight += farm.getWeight(i);
				}
			}
			return totalWeight;

		}

		/**
		 * This is a helper method for the annual report to get the sum of farm
		 * weights by year
		 * 
		 * @param farmId
		 * @param year
		 * @return
		 * @throws Exception
		 */
		private int sumOfFarmWeightsByYear(String farmId, int year)
				throws Exception {
			int index = farmIndex(farmId);
			int totalWeight = 0;
			if (index == -1) {
				throw new Exception();
			} else {
				// sum all weights for a farm that are in the given year
				Farm farm = manager.farms.get(index);
				for (int i = 0; i < farm.milk.size(); i++) {
					if (farm.getYear(i) == year) {
						totalWeight += farm.getWeight(i);
					}
				}
			}
			return totalWeight;
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
			if (index == -1) {
				throw new Exception();
			} else {
				// sum monthyl weights for the farm
				Farm farm = manager.farms.get(index);
				for (int i = 0; i < farm.milk.size(); i++) {
					if (farm.getYear(i) == year) {
						int month = farm.getMonth(i) - 1;
						averages[month] += farm.getWeight(i);
					}
				}
			}
			// return total weights for each month for given farm
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
			if (index == -1) {
				throw new Exception();
			} else {
				// add weights for all month of all data given for the farm
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
