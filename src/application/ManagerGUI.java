/**
 * Milk Manager GUI class
 * Mason Batchelor: mrbatchelor@wisc.edu
 * Ishaan Backliwal: backliwal@wisc.edu
 */
package application;

import java.util.List;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class ManagerGUI extends Application {
	// store any command-line arguments that were entered.
	// NOTE: this.getParameters().getRaw() will get these also
	private List<String> args;

	private static final int WINDOW_WIDTH = 300;
	private static final int WINDOW_HEIGHT = 200;
	private static final String APP_TITLE = "Hello World!";
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		
		// Main layout is Border Pane
  	BorderPane root = new BorderPane();
  	
  	// Create a vertical box with Hello labels
    VBox vbox = new VBox();
    vbox.getChildren().add(new Label("GUI running" ));

		// Add the vertical box to the center of the root pane
		root.setTop(new Label(APP_TITLE));
    root.setCenter(vbox);
    
    // creat the main scene
		Scene mainScene = new Scene(root, WINDOW_WIDTH, WINDOW_HEIGHT);

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
