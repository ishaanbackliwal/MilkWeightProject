package application;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

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

	public FileParser(String fileName, FarmManager manager) {
		try {
			this.manager = manager;
			FileReader farms = new FileReader(fileName);
			reader = new BufferedReader(farms);
			String line1 = reader.readLine();
			String[] input = line1.split(",");
			// test if the first line is properly formatted
			if (input[0].equals("date") && input[1].equals("farm_id")
					&& input[2].equals("weight")) {
				validFile = true;
			}
			if (validFile = true) {
				// iterate through the file and store the values in the arraylist
				String data;
				while ((data = reader.readLine()) != null) {
					input = data.split(",");
					// get farm ID as a String value to compare
					String farmId = input[1].split(" ")[1];
					int farmIndex = farmIndex(farmId);
					// if farm does not exist, create it and add the milk
					if (farmIndex == -1) {
						Farm newFarm = new Farm(farmId);
						newFarm.addMilk(input[2], input[0]);
						manager.farms.add(newFarm);
					} else {
						// else farm exists so add new milk data to it
						manager.farms.get(farmIndex).addMilk(input[2], input[0]);
					}
				}
			} else {
				throw new IOException();
			}
		} catch (FileNotFoundException e) {
			System.out.println("Could not open file, try a new one.");
		} catch (IOException i) {
			System.out.println("The file is not formatted properly");
		}
	}

	private int farmIndex(String farmID) {
		for (int i = 0; i < manager.farms.size(); i++) {
			if (manager.farms.get(i).farmID.equals(farmID)) {
				return i;
			}
		}
		return -1;
	}

}
