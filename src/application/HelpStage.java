/**
 * HelpStage.java 
 *
 * Author:  Mason Batchelor and Ishaan Backliwal
 * Date: @date Apr 30, 2020
 * 
 * Course: CS400
 * Semester: Spring 2020
 * Lecture: 001
 * 
 * Other Credits: 
 */
package application;

import java.util.ArrayList;

import javafx.collections.ObservableList;
import javafx.geometry.Insets;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 * FileName: HelpStage.java
 * 
 * The purpose of this class is to provide a high level of the formatting rules
 * that are necessary to utilize this interface to its fullest potential
 * 
 * @author Mason Batchelor - mrbatchelor@wisc.edu Ishaan Backliwal -
 *         backliwal@wisc.edu
 *
 */
public class HelpStage extends Stage {
	VBox information = new VBox(8);
	BorderPane root = new BorderPane();

	/**
	 * Default constructor for the help stage
	 */
	public HelpStage() {
		this.setTitle("Help Information");
		Label helpLabel = new Label("Help Information Page");
		
		// create the bullet points
		Text bullet1 = new Text(
				"1. Must search for exact string. Ex) If input was \"Farm 1\" then search \"Farm 1\"");
		Text bullet2 = new Text(
				"2. All weights must be integers and dates are from the calendars.");
		Text bullet3 = new Text("3. Any bad files will be completely ignored.");
		Text bullet4 = new Text(
				"4. Output file names increment automatically however when the program is\n "
						+ "    exited and re-run, the files will be overwritten with new files.\n"
						+ "    (If you would like to avoid this, rename output files after they have been created)");
		Text bullet5 = new Text(
				"5. To display/remove all data, leave Farm ID field blank and press the display button.");
		Text bullet6 = new Text(
				"6. Removing and adding data does not modify input files.");
		Text bullet7 = new Text(
				"7. Click on table headers in any stat display window to sort data by that column.");
		Text bullet8 = new Text(
				"8. File output is sorted by FarmID automatically.");
		Text bullet9 = new Text(
				"9. To edit already submitted dates for date range report, press go button\n"
						+ "    next to drop down again (this will restart date selection).");
		Text bullet10 = new Text(
				"10. To switch output options, press go button each time.");
		Text bullet11 = new Text(
				"11. All fields are CASE SENSITIVE.");
		
		// add all information to vbox to display
		information.getChildren().addAll(bullet1, bullet2, bullet3, bullet4,
				bullet5, bullet6, bullet7, bullet8, bullet9, bullet10, bullet11);
		
		// set the font size
		Text[] bullets = { bullet1, bullet2, bullet3, bullet4, bullet5, bullet6,
				bullet7, bullet8, bullet9, bullet10, bullet11 };
		for(int i = 0; i < bullets.length; i++) {
			setFont(bullets[i]);
		}

		// set up the scene for display
		root.setTop(helpLabel);
		root.setCenter(information);
		helpLabel.setFont(Font.font(40));
		helpLabel.setPadding(new Insets(0, 0, 15, 0));
		information.setPadding(new Insets(0, 10, 0, 10));
		BorderPane.setAlignment(helpLabel, Pos.CENTER);
		this.setScene(new Scene(root, 500, 400));
		this.show();
	}
	
	/**
	 * Sets font for desired text
	 * 
	 * @param text - font size to be set for this text
	 */
	private void setFont(Text text) {
		text.setFont(Font.font(14));
	}
}
