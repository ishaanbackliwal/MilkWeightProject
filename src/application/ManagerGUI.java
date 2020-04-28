
package application;

import java.io.IOException;
import java.util.List;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
/**
 *  Milk Manager GUI class
 * This is the primary class that runs our GUI
 * 
 * @author Mason Batchelor: mrbatchelor@wisc.edu
 * 				 Ishaan Backliwal: backliwal@wisc.edu
 *
 */
public class ManagerGUI extends Application {
	// store any command-line arguments that were entered.
	// NOTE: this.getParameters().getRaw() will get these also

	private List<String> args;

	private static final int WINDOW_WIDTH = 650;
	private static final int WINDOW_HEIGHT = 500;
	private static final String APP_TITLE = "Milk Manager";
	private FarmManager manager = new FarmManager();

	@Override
	public void start(Stage primaryStage) throws Exception {

		
		// Buttons
		Button addButton = new Button("ADD");
		Button removeButton = new Button("REMOVE");
		Button displayButton = new Button("DISPLAY");
		Button outputButton = new Button("OUTPUT");

		// Label texts
		Text tile = new Text();
		tile.setText("Milk Manager");
		tile.setFont(Font.font(50));

		// Text fields
		TextField fileNameTextField = new TextField();
		TextField farmIDTextField = new TextField();

		// Main layout is Border Pane
		BorderPane root = new BorderPane();
		root.setPadding(new Insets(30, 30, 200, 30));
		
		// Title horizontal box
		HBox hboxTitle = new HBox(10);
		hboxTitle.getChildren().add(tile);
		hboxTitle.setAlignment(Pos.TOP_CENTER);
		root.setTop(hboxTitle);

		// Button vertical box
		HBox hboxButtons = new HBox(10);
		hboxButtons.getChildren().addAll(addButton, removeButton,
				displayButton, outputButton);
		hboxButtons.setAlignment(Pos.BOTTOM_CENTER);
		root.setBottom(hboxButtons);

		// File name text field
		VBox vboxFileName = new VBox(10);
		Label fileNameLabel = new Label("File Name");
		vboxFileName.setAlignment(Pos.CENTER);
		vboxFileName.getChildren().addAll(fileNameLabel, fileNameTextField);

		// File name text field
		VBox vboxFarmID = new VBox(10);
		Label farmIDLabel = new Label("Farm ID");
		vboxFarmID.setAlignment(Pos.CENTER);
		vboxFarmID.getChildren().addAll(farmIDLabel, farmIDTextField);

		// 'OR' text vertical box
		VBox vboxOr = new VBox(10);
		Label orLabel = new Label("OR");
		vboxOr.setAlignment(Pos.CENTER);
		vboxOr.getChildren().add(orLabel);

		// Text field horizontal box
		HBox hboxTextFields = new HBox(30);
		hboxTextFields.getChildren().addAll(vboxFileName, vboxOr, vboxFarmID);
		hboxTextFields.setAlignment(Pos.BOTTOM_CENTER);

		root.setCenter(hboxTextFields);
		
		// Create new pop up windows taking text input
		// Checks for bad input on button press
		// add button
		addButton.setOnAction(e -> {
			try {
				fileOrFarm(farmIDTextField, fileNameTextField);
			} catch (NumberFormatException | IOException e1) {
				e1.printStackTrace();
			}
		});
		// remove button
		removeButton.setOnAction(e -> {
			// farm id is left blank
			if(farmIDTextField.getText().compareTo("") == 0) {
				Alert alert = new Alert(AlertType.WARNING, "Please enter a valid farm ID to remove data.");
				alert.setHeaderText("ERROR: Farm ID was left blank.");
				alert.showAndWait();
			}
			// farm id does not exist among inputted data
			else if(!manager.containsFarm(farmIDTextField.getText())) {
				Alert alert = new Alert(AlertType.WARNING, "Please enter a valid farm ID to remove data.");
				alert.setHeaderText("ERROR: Farm ID does not exist.");
				alert.showAndWait();
			}
			// open farm id to remove data
			else {
				new RemoveStage(farmIDTextField.getText(), manager);
			}
		});	
		// display button
		displayButton.setOnAction(e -> {
			// farm id was left blank
			if(farmIDTextField.getText().compareTo("") == 0) {
				new DisplayStage("all", manager);
			}
			// farm id entered does not exist
			else if(!manager.containsFarm(farmIDTextField.getText())) {
				Alert alert = new Alert(AlertType.WARNING, "Please enter a valid farm ID to display data.\n (or leave field blank to display all data)");
				alert.setHeaderText("ERROR: Farm ID does not exist.");
				alert.showAndWait();
			}
			// open farm id to display data
			else {
				new DisplayStage(farmIDTextField.getText(), manager);
			}
		});
		// output button
		outputButton.setOnAction(e -> {
			// no data exists to output
			if(manager.farms.size() == 0) {
				Alert alert = new Alert(AlertType.WARNING, "Please enter input data to output information.");
				alert.setHeaderText("ERROR: No data has been provided.");
				alert.showAndWait();
			}	
			// open output stage and prompt user for input
			else {
				new OutputStage(manager);
			}
		});
		
		// Create the main scene
		Scene mainScene = new Scene(root, WINDOW_WIDTH, WINDOW_HEIGHT);

		// Add the stuff and set the primary stage
		primaryStage.setTitle(APP_TITLE);
		primaryStage.setScene(mainScene);
		primaryStage.show();
	}
	private void fileOrFarm(TextField farmNum, TextField newFile) throws NumberFormatException, IOException {
		// get the text from both fields
		String farmId = farmNum.getText();
		String farmFile = newFile.getText();
		// determine which field user has text in
		if(farmId.equals("") && !farmFile.equals("")) {
			// if the user has entered a file, parse the file 
			FileParser parser = new FileParser(farmFile, this.manager);
		} else if(!farmId.equals("") && farmFile.equals("")) {
			// otherwise they want to add to a specific farm
			new AddStage(farmId, manager);
		} else {
			// otherwise alert the user to add a farm id or file name
			Alert alert = new Alert(AlertType.WARNING, "Enter a file OR a farm.");
			alert.setHeaderText("Must enter a Farm ID number (OR) a file name.");
			alert.showAndWait();
		}
	}
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		launch(args);
	}
}
