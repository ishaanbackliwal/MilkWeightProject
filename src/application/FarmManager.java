/**
 * FarmManager.java created by ishaanbackliwalApr 19, 2020
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

public class FarmManager {
	public ArrayList<Farm> farms;
	public int[] totalWeight;
	
	public FarmManager() {
		
		farms = new ArrayList<Farm>();
		totalWeight = new int[12];
	}
}
