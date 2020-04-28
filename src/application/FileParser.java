package application;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

/**
 * This class is designed to parse the file of farms.
 * 
 * @author Mason Batchelor mrbatchelor@wisc.edu Ishaan Backliwal
 *         backliwal@wisc.edu
 *
 */
public class FileParser {
	private BufferedReader reader; // bufferedReader used to parse the csv file
	private Scanner sc; // scanner used to get the farm num from string
	private boolean validFile = false;
	private FarmManager manager;
	private int badInput = 0;

	public FileParser(String fileName, FarmManager manager) {
		String[] input = null;
		String data = "-1";
		// no matter the exception throw here, the file is not valid
		try {
			this.manager = manager;
			FileReader farms = new FileReader(fileName);
			reader = new BufferedReader(farms);
			String line1 = reader.readLine();
			input = line1.split(",");
			// test if the first line is properly formatted
			if (input[0].equals("date") && input[1].equals("farm_id")
					&& input[2].equals("weight")) {
				validFile = true;
			}
		} catch (Exception e) {
			Alert alert = new Alert(AlertType.WARNING,
					"A valid file could not be found.");
			alert.setHeaderText("Please enter a valid file.");
			alert.showAndWait();
			return;
		}

		if (validFile = true) {
			// iterate through the file and store the values in the arraylist
			try {
				while ((data = reader.readLine()) != null) {
					input = data.split(",");
					// if a line does not have 3 arguments throw exception.
					if (input.length != 3) {
						throw new IOException();
					}
					// get farm ID as a String value to compare
					String farmId = input[1];
					int farmIndex = farmIndex(farmId);
					// if farm does not exist, create it and add the milk
					confirmDate(input[0]);
					if (farmIndex == -1) {
						Farm newFarm = new Farm(farmId);
						newFarm.addMilk(input[2], input[0]);
						manager.farms.add(newFarm);
						int month = Integer.parseInt(input[0].split("-")[1]);
						manager.totalWeight[month - 1] += (int) Integer.parseInt(input[2]);
					} else {
						// else farm exists so add new milk data to it
						manager.farms.get(farmIndex).addMilk(input[2], input[0]);
						int month = Integer.parseInt(input[0].split("-")[1]);
						manager.totalWeight[month - 1] += (int) Integer.parseInt(input[2]);
					}
				}
			} catch (IOException i) {
				Alert alert = new Alert(AlertType.INFORMATION,
						"Error occured while parsing the file, data up to the \n bad line was added.");
				alert.setHeaderText(
						"Please enter a properly formatted file");
				alert.showAndWait();
				return;
			}
			// only gets here if entire file was parsed properly
			Alert alert = new Alert(AlertType.INFORMATION,
					"All data has been added.");
			alert.setHeaderText(
					"Confirmed data was added with no bad lines of input.");
			alert.showAndWait();
		}
	}

	/**
	 * This is a helper method to determine if a date is valid, throws an
	 * IOException to be caught by the main parsing method and skip the line
	 * 
	 * @param date
	 * @throws IOException
	 */
	private void confirmDate(String date) throws IOException {
		try {
			String[] split = date.split("-");
			Integer.parseInt(split[0]);
			Integer.parseInt(split[1]);
			Integer.parseInt(split[2]);
		} catch (NumberFormatException i) {
			throw new IOException();
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
