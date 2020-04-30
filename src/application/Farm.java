/**
 * Farms.java 
 *
 * Author: Ishaan Backliwal and Mason Batchelor
 * Date: @date Apr 19, 2020
 * 
 * Course: CS400
 * Semester: Spring 2020
 * Lecture: 001
 * 
 * Other Credits: 
 */
package application;

import java.util.ArrayList;
import java.io.IOException;
import java.lang.NumberFormatException;

/**
 * FileName: Farm.java
 * 
 * This is the farm class that stores an array list of milk objects and the
 * farmID
 * 
 * @author Mason Batchelor: mrbatchelor@wisc.edu, Ishaan Backliwal:
 *         backliwal@wisc.edu
 */
public class Farm {
	public ArrayList<Milk> milk; // array list of milk objects
	public String farmID; // farm ID

	/**
	 * Milk sub-class that contains milk data
	 */
	private class Milk {
		int weight; // weight of milk
		int day; // day of month
		int month; // month
		int year; // year
		String date; // entire date in yyyy-MM-dd format

		/**
		 * Constructor to initialize fields
		 * 
		 * @param weight - weight of milk
		 * @param date   - date of milk
		 */
		private Milk(String weight, String date) {
			this.weight = Integer.parseInt(weight);
			this.date = date;
			// split date into sections
			String[] split = date.split("-", 3);
			year = Integer.parseInt(split[0]);
			month = Integer.parseInt(split[1]);
			day = Integer.parseInt(split[2]);
		}

		/**
		 * Getter method for milk weight
		 * 
		 * @return weight of milk
		 */
		private int getWeight() {
			return weight;
		}

		/**
		 * Getter method for year of milk
		 * 
		 * @return milk year
		 */
		private int getYear() {
			return year;
		}

		/**
		 * Getter method for month of milk
		 * 
		 * @return month of milk
		 */
		private int getMonth() {
			return month;
		}

		/**
		 * Getter method for day of milk
		 * 
		 * @return day of the month of milk
		 */
		private int getDay() {
			return day;
		}

		/**
		 * Getter method for entire date of milk
		 * 
		 * @return string of entire date of milk in yyyy-MM-dd format
		 */
		private String getDate() {
			return date;
		}
	}

	/**
	 * Constructor, initializes milk array list
	 */
	public Farm(String farmID) {
		this.farmID = farmID;
		milk = new ArrayList<Milk>();
	}

	/**
	 * Gets number of milk objects in farm
	 * 
	 * @return number of milk objects for the farm
	 */
	public int size() {
		return milk.size();
	}

	/**
	 * Adds milk with desired weight and date to the milk array list
	 * 
	 * @param weight - weight to add
	 * @param date   - date to add
	 * @throws IOException
	 */
	public void addMilk(String weight, String date) {
		milk.add(new Milk(weight, date));
	}

	/**
	 * Removes milk with desired weight and date from the milk array list
	 * 
	 * @param weight - weight to remove
	 * @param date   - date to remove
	 */
	public void removeMilk(String weight, String date) {
		int index = -1;
		for (int i = 0; i < milk.size(); i++) {
			if (milk.get(i).getWeight() == Integer.parseInt(weight)) {
				if (milk.get(i).getDate().compareTo(date) == 0) {
					index = i;
					i = milk.size();
				}
			}
		}
		// remove milk if it exists
		if (index != -1)
			milk.remove(index);
	}

	/**
	 * Getter method for date of milk
	 * 
	 * @param index - index of milk
	 * @return date of milk at the given index
	 */
	public String getDate(int index) {
		return milk.get(index).getDate();
	}

	/**
	 * Getter method for weight of milk
	 * 
	 * @param index - index of milk
	 * @return weight of milk at given index
	 */
	public int getWeight(int index) {
		return milk.get(index).getWeight();
	}

	/**
	 * Getter method for the year of milk
	 * 
	 * @param index - index of milk
	 * @return year of milk at given index
	 */
	public int getYear(int index) {
		return milk.get(index).getYear();
	}

	/**
	 * Getter method for the month of milk
	 * 
	 * @param index - index of milk
	 * @return month of milk at given index
	 */
	public int getMonth(int index) {
		return milk.get(index).getMonth();
	}

	/**
	 * Getter method for the day of milk
	 * 
	 * @param index - index of milk
	 * @return day of month of milk at given index
	 */
	public int getDay(int index) {
		return milk.get(index).getDay();
	}

}
