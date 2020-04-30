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

public class FarmManager {
	public ArrayList<Farm> farms;
	public int[] totalWeight;
	
	public FarmManager() {
		
		farms = new ArrayList<Farm>();
		totalWeight = new int[12];
	}
	public int size() {
		return farms.size();
	}
	public boolean containsFarm(String farmId) {
		for(int i = 0; i < farms.size(); i ++) {
			if(farms.get(i).farmID.compareTo(farmId) == 0)
				return true;
		}
		return false;
	}
}
