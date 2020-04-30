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
 * This is the farm class that stores an arraylist of milk objects and the farm ID
 * @author Ishaan Backliwal and Mason Batchelor
 *
 */
public class Farm {
	public ArrayList<Milk> milk; // arraylist of milk objects
	public String farmID; // farm ID

	/**
	 * Milk sub-class
	 */
	private class Milk {
		int weight;
		int day;
		int month;
		int year;
		String date;

		private Milk(String weight, String date) {
			
				this.weight = Integer.parseInt(weight);
				this.date = date;
				String[] split = date.split("-", 3);
				year = Integer.parseInt(split[0]);
				month = Integer.parseInt(split[1]);
				day = Integer.parseInt(split[2]);
			 
		}

		private int getWeight() {
			return weight;
		}

		private int getYear() {
			return year;
		}

		private int getMonth() {
			return month;
		}

		private int getDay() {
			return day;
		}

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
	 * @return
	 */
	public int size() {
		return milk.size();
	}
	/**
	 * Adds milk with desired weight and date to the milk array list
	 * 
	 * @param weight
	 * @param date
	 * @throws IOException 
	 */
	public void addMilk(String weight, String date) {
		milk.add(new Milk(weight, date));

	}

	/**
	 * Removes milk with desired weight and date from the milk array list
	 * 
	 * @param weight
	 * @param date
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
		if (index != -1)
			milk.remove(index);
	}

	/**
	 * Getter method for date of milk
	 * 
	 * @param index
	 * @return
	 */
	public String getDate(int index) {
		return milk.get(index).getDate();
	}

	/**
	 * Getter method for weight of milk
	 * 
	 * @param index
	 * @return
	 */
	public int getWeight(int index) {
		return milk.get(index).getWeight();
	}

	/**
	 * Getter method for the year of milk
	 * 
	 * @return
	 */
	public int getYear(int index) {
		return milk.get(index).getYear();
	}

	/**
	 * Getter method for the month of milk
	 * 
	 * @return
	 */
	public int getMonth(int index) {
		return milk.get(index).getMonth();
	}
	/**
	 * Getter method for the day of milk
	 * 
	 * @return
	 */
	public int getDay(int index) {
		return milk.get(index).getDay();
	}


}
