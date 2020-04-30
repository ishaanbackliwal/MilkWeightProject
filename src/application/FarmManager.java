/**
 * FarmManager.java
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

/**
 * FileName: FarmManager.java
 * 
 * Manager for the back end farm data structure
 * 
 * @author Mason Batchelor: mrbatchelor@wisc.edu, Ishaan Backliwal:
 *         backliwal@wisc.edu
 */
public class FarmManager {

	public ArrayList<Farm> farms; // array list of all farms
	public int[] totalWeight; // total weight of each month

	/**
	 * Constructor
	 */
	public FarmManager() {
		farms = new ArrayList<Farm>();
		totalWeight = new int[12];
	}

	/**
	 * Getter method for number of farm objects
	 * 
	 * @return size of farms array list
	 */
	public int size() {
		return farms.size();
	}

	/**
	 * Determines whether farms array list contains a farm with the given farmID
	 * 
	 * @param farmId - farm ID of the farm to be searched for
	 * @return true if farm ID exists, false otherwise
	 */
	public boolean containsFarm(String farmId) {
		for (int i = 0; i < farms.size(); i++) {
			if (farms.get(i).farmID.compareTo(farmId) == 0)
				return true;
		}
		return false;
	}
}
