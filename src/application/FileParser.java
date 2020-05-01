/**
 * FileParser.java 
 *
 * Author: Mason Batchelor and Ishaan Backliwal
 * Date: @date Apr 24, 2020
 * 
 * Course: CS400
 * Semester: Spring 2020
 * Lecture: 001
 * 
 * Other Credits: 
 */
package application;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

/**
 * This class is designed to parse the file of farms.
 * 
 * @author Mason Batchelor: mrbatchelor@wisc.edu Ishaan Backliwal:
 *         backliwal@wisc.edu
 *
 */
public class FileParser {
	private BufferedReader reader; // bufferedReader used to parse the csv file
	private boolean validFile = false; // indicator of a file's existence
	private FarmManager manager; // manager of the back end data structure
	ArrayList<String> fileInfo; // array list of all the information in the file

	/**
	 * Constructor
	 * 
	 * @param fileName - name of the file to be parsed
	 * @param manager  - manager of the back end data structure
	 * @throws NumberFormatException
	 * @throws IOException
	 */
	public FileParser(String fileName, FarmManager manager) throws NumberFormatException, IOException {
		fileInfo = new ArrayList<String>();
		String[] input = null;

		// if an exception is thrown here, the file is not valid
		try {
			this.manager = manager;
			FileReader farms = new FileReader(fileName);
			reader = new BufferedReader(farms);
			String line1 = reader.readLine();
			input = line1.split(",");
			// test if the first line is properly formatted
			if (input[0].equals("date") && input[1].equals("farm_id") && input[2].equals("weight")) {
				validFile = true;
			}
		}
		// alert user if the file entered could not be found
		catch (Exception e) {
			Alert alert = new Alert(AlertType.WARNING, "A valid file could not be found.");
			alert.setHeaderText("Please enter a valid file.");
			alert.showAndWait();
			return;
		}

		// if the file exists
		if (validFile = true) {
			try {
				// check if all data in file is valid before adding information
				checkFileData();
			}
			// alert the user if there was bad data in the file provided
			catch (IOException i) {
				// clear file info array list
				fileInfo = null;
				// alert
				Alert alert = new Alert(AlertType.WARNING,
						"Error occured while parsing the file! \nNo data has been added.");
				alert.setHeaderText("Please enter a properly formatted file.");
				alert.showAndWait();
				// quit parsing operations
				return;
			}

			// if file does not have bad data
			// iterate through the file and store the values in the ArrayList
			for (int i = 0; i < fileInfo.size(); i++) {
				input = fileInfo.get(i).split(",");
				// get farm ID as a String value to compare
				String farmId = input[1];
				int farmIndex = farmIndex(farmId);
				// if farm does not exist, create it and add the milk
				if (farmIndex == -1) {
					Farm newFarm = new Farm(farmId);
					newFarm.addMilk(input[2], input[0]);
					this.manager.farms.add(newFarm);
					int month = Integer.parseInt(input[0].split("-")[1]);
					// add weight to total weight of given month
					this.manager.totalWeight[month - 1] += (int) Integer.parseInt(input[2]);
				}
				// else, add milk data to existing farm
				else {
					this.manager.farms.get(farmIndex).addMilk(input[2], input[0]);
					int month = Integer.parseInt(input[0].split("-")[1]);
					// add weight to total weight of given month
					this.manager.totalWeight[month - 1] += (int) Integer.parseInt(input[2]);
				}
			}
			// only gets here if entire file was parsed properly
			// confirm file parsing completion to user
			Alert alert = new Alert(AlertType.INFORMATION, "All data has been added.");
			alert.setHeaderText("Confirmed data was added with no bad lines of input.");
			alert.showAndWait();
		}
	}

	/**
	 * Checks whether the data in each file is valid or not
	 * 
	 * @throws IOException is any day is not valid
	 */
	private void checkFileData() throws IOException {
		String data = "-1";
		String[] input = null;
		while ((data = reader.readLine()) != null) {
			input = data.split(",");
			// if a line does not have 3 arguments, throw exception
			if (input.length != 3) {
				throw new IOException();
			}
			// tests whether weight is an integer; will throw exception if not
			try {
				Integer.parseInt(input[2]);
			} catch (Exception e1) {
				throw new IOException();
			}
			// confirm date is valid with the appropriate format
			confirmDate(input[0]);
			// add data to fileInfo array list since no exceptions were thrown
			fileInfo.add(data);
		}
	}

	/**
	 * This is a helper method to determine if a date is valid, throws an
	 * IOException to be caught by the main parsing method and skip the line
	 * 
	 * @param date - date to be confirmed as valid or not
	 * @throws IOException if date not valid
	 */
	private void confirmDate(String date) throws IOException {
		// throw IO exception if any of the date values are not integers
		try {
			String[] split = date.split("-");
			Integer.parseInt(split[0]);
			Integer.parseInt(split[1]);
			Integer.parseInt(split[2]);
		} catch (Exception e) {
			throw new IOException();
		}
	}

	/**
	 * This private helper is used to help find the index of the farm in the array
	 * list
	 * 
	 * @param farmID - the ID to search for
	 * @return - the cooresponding index for the farm ID provided
	 */
	private int farmIndex(String farmID) {
		for (int i = 0; i < manager.farms.size(); i++) {
			if (manager.farms.get(i).farmID.equals(farmID)) {
				return i;
			}
		}
		return -1;
	}

}
