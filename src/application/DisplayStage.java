package application;

import java.util.ArrayList;
import java.util.Comparator;

import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 * FileName: DisplayStage.java
 * 
 * The display stage of the GUI This displays a new stage to present information
 * for a Farm ID or all data that has been added in
 * 
 * @author Mason Batchelor: mrbatchelor@wisc.edu, Ishaan Backliwal:
 *         backliwal@wisc.edu
 * 
 *         Other Credits: Stack Overflow
 *
 */
public class DisplayStage extends Stage {
	private BorderPane root = new BorderPane();
	private Label farmLabel; // title label for the top of the stage
	private FarmManager manager; // manager for the back end data structure

	/**
	 * Stage to display requested data
	 * 
	 * @param farmId  - farm ID of the data requested or "add" to display all data
	 * @param manager - manager for the back end data structure
	 */
	public DisplayStage(String farmId, FarmManager manager) {
		this.manager = manager;

		// if all data requested
		if (farmId.compareTo("all") == 0) {
			farmLabel = new Label("All Data");
			this.setTitle("All Data");
		}
		// specific farm id requested
		else {
			this.setTitle("Data for Farm ID: " + farmId);
			farmLabel = new Label("Farm ID: " + farmId);
		}

		// set up a vbox to display all values
		VBox vbox = new VBox();
		root = new BorderPane(vbox);
		root.setPadding(new Insets(15));

		// retrieve all the values stored in the farm location/all farms
		setTable(vbox, farmId);

		// create the scene
		root.setTop(farmLabel);
		farmLabel.setFont(Font.font(40));
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
	 * This method creates the table and fills it with all the dat requested
	 * 
	 * @param vbox   - the vbox which will display all the values
	 * @param farmID - the farmID to search for in the arraylist
	 */
	private void setTable(VBox vbox, String farmId) {
		Farm farm = null;

		// array lists for data to add to columns
		ArrayList<String> farms = new ArrayList<String>();
		ArrayList<String> dates = new ArrayList<String>();
		ArrayList<Integer> weights = new ArrayList<Integer>();
		// for all data requested
		if (farmId.compareTo("all") == 0) {
			// go through all farms and add each data point to array lists
			for (int j = 0; j < manager.farms.size(); j++) {
				farm = manager.farms.get(j);
				for (int i = 0; i < farm.milk.size(); i++) {
					farms.add(manager.farms.get(j).farmID);
					dates.add(farm.getDate(i));
					weights.add(farm.getWeight(i));
				}
			}
		}
		// for specific farm
		else {
			int farmIndex = this.farmIndex(farmId);
			if (farmIndex >= 0) {
				farm = manager.farms.get(farmIndex);
				// go through requested farm's data and add info to array lists
				for (int i = 0; i < farm.milk.size(); i++) {
					farms.add(farmId);
					dates.add(farm.getDate(i));
					weights.add(farm.getWeight(i));
				}
			}
		}

		// set up table
		TableView<Object> tableView = new TableView<Object>();
		for (int i = 0; i < farms.size(); i++) {
			tableView.getItems().add(i + "");
		}
		// Make the columns for the table
		TableColumn<Object, String> farmIDColumn = new TableColumn<Object, String>("Farm ID");
		farmIDColumn.setMinWidth(150);
		TableColumn<Object, Integer> weightColumn = new TableColumn<Object, Integer>("Weight");
		weightColumn.setMinWidth(150);
		TableColumn<Object, String> dateColumn = new TableColumn<Object, String>("Date");
		dateColumn.setMinWidth(150);

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

		// fill each cell with the appropriate data
		farmIDColumn.setCellValueFactory(cellData -> {
			int rowIndex = Integer.parseInt((String) cellData.getValue());
			return new ReadOnlyStringWrapper(farms.get(rowIndex));
		});
		weightColumn.setCellValueFactory(cellData -> {
			int rowIndex = Integer.parseInt((String) cellData.getValue());
			return new ReadOnlyObjectWrapper<Integer>(weights.get(rowIndex));
		});
		dateColumn.setCellValueFactory(cellData -> {
			int rowIndex = Integer.parseInt((String) cellData.getValue());
			return new ReadOnlyStringWrapper(dates.get(rowIndex));
		});

		// add columns to table and add table to vbox
		tableView.getColumns().add(farmIDColumn);
		tableView.getSortOrder().add(farmIDColumn);
		tableView.getColumns().add(weightColumn);
		tableView.getColumns().add(dateColumn);
		vbox.getChildren().add(tableView);
	}

}
