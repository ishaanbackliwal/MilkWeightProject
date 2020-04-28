package application;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
/**
 * This class is used to display the data values of a given farm
 * @author batch_vdenlpx
 *
 */
public class DisplayStage extends Stage {
	private BorderPane root = new BorderPane();
	private Label farmLabel;
	private FarmManager manager;
	
	public DisplayStage(String farmId, FarmManager manager) {
		this.manager = manager;
		
		// if all data requested
		if(farmId.compareTo("all") == 0)
			farmLabel = new Label("All Data");
		// specific farm id requested
		else
			farmLabel = new Label("Farm ID: " + farmId);
		
		// set up a vbox to display all values
		VBox vbox = new VBox();
		ScrollPane scrollPane = new ScrollPane(vbox);
		scrollPane.setFitToHeight(true);
		root = new BorderPane(scrollPane);
		root.setPadding(new Insets(15));

		// retrieve all the values stored in the farm location
		getInfo(vbox, farmId);
		
		root.setTop(farmLabel);
		// create the scene
			this.setTitle("Farm ID: " + farmId);
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
	private int farmIndex(String farmId) {
		for (int i = 0; i < manager.farms.size(); i++) {
			if (manager.farms.get(i).farmID.compareTo(farmId) == 0) {
				return i;
			}
		}
		return -1;
	}
	/**
	 * This method prints all of the milk info contained within the specified
	 * farms milk array
	 * 
	 * @param vbox   - the vbox which will display all the values
	 * @param farmID - the farmID to search for in the arraylist
	 */
	private void getInfo(VBox vbox, String farmId) {
		Farm farm = null;
		if(farmId.compareTo("all") == 0) {
			for(int j = 0; j < manager.farms.size(); j++) {
				farm = manager.farms.get(j);
				for (int i = 0; i < farm.milk.size(); i++) {
					Text value = new Text(farm.getDate(i) + "; " + farm.getWeight(i));
					vbox.getChildren().addAll(value);
				}
			}
		}
		else {
			int farmIndex = this.farmIndex(farmId);
			if (farmIndex >= 0) {
				farm = manager.farms.get(farmIndex);
				for (int i = 0; i < farm.milk.size(); i++) {
					Text value = new Text(farm.getDate(i) + "; " + farm.getWeight(i));
					vbox.getChildren().addAll(value);
				}
			}	
		}
	}

}
