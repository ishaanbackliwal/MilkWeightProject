/**
 * Farms.java created by ishaanbackliwalApr 19, 2020
 *
 * Author: Ishaan Backliwal
 * Date: @date
 * 
 * Course: CS400
 * Semester: Spring 2020
 * Lecture: 001
 * 
 * List Collaborators: name, email@wisc.edu, lecture number
 * 
 * Other Credits: 
 * 
 * Known Bugs: 
 */
package application;

import java.util.ArrayList;


public class Farm {
	ArrayList<Milk> milk;
	
	/**
	 * Milk sub-class
	 */
	private class Milk{
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
	public Farm() {
		milk = new ArrayList<Milk>();
	}
	/**
	 * Adds milk with desired weight and date to the milk array list
	 * @param weight
	 * @param date
	 */
	public void addMilk(String weight, String date) {
		milk.add(new Milk(weight, date));
	}
	public String getDate(int index) {
		return milk.get(index).getDate();
	}
	
}
