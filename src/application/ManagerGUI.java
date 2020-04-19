/**
 * Milk Manager GUI class
 * Mason Batchelor: mrbatchelor@wisc.edu
 * Ishaan Backliwal: backliwal@wisc.edu
 */
package application;

import java.util.List;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
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

public class ManagerGUI extends Application {
	// store any command-line arguments that were entered.
	// NOTE: this.getParameters().getRaw() will get these also

	private List<String> args;

	private static final int WINDOW_WIDTH = 650;
	private static final int WINDOW_HEIGHT = 500;
	private static final String APP_TITLE = "Milk Manager";

	@Override
	public void start(Stage primaryStage) throws Exception {

		// Buttons
		Button addButton = new Button("ADD");
		Button removeButton = new Button("REMOVE");
		Button editButton = new Button("EDIT");
		Button displayButton = new Button("DISPLAY");

		// Label texts
		Text tile = new Text();
		tile.setText("Milk Manager");
		tile.setFont(Font.font(50));

		// Text fields
		TextField fileNameTextField = new TextField();
		TextField farmIDTextField = new TextField();

		// Main layout is Border Pane
		BorderPane root1 = new BorderPane();
		root1.setPadding(new Insets(30, 30, 200, 30));
		
		// Title horizontal box
		HBox hboxTitle = new HBox(10);
		hboxTitle.getChildren().add(tile);
		hboxTitle.setAlignment(Pos.TOP_CENTER);
		root1.setTop(hboxTitle);

		// Button vertical box
		HBox hboxButtons = new HBox(10);
		hboxButtons.getChildren().addAll(addButton, removeButton, editButton,
				displayButton);
		hboxButtons.setAlignment(Pos.BOTTOM_CENTER);
		root1.setBottom(hboxButtons);

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

		root1.setCenter(hboxTextFields);
		
		// create new pop up windows taking text input
		addButton.setOnAction(e -> new FarmStage(farmIDTextField.getText()));
		removeButton.setOnAction(e -> new FarmStage(farmIDTextField.getText()));
		editButton.setOnAction(e -> new FarmStage(farmIDTextField.getText()));
		displayButton.setOnAction(e -> new FarmStage(farmIDTextField.getText()));
		
		// Create the main scene
		Scene mainScene = new Scene(root1, WINDOW_WIDTH, WINDOW_HEIGHT);

		// Add the stuff and set the primary stage
		primaryStage.setTitle(APP_TITLE);
		primaryStage.setScene(mainScene);
		primaryStage.show();
	}
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		launch(args);
	}
}
