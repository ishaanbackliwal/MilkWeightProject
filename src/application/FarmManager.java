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
	
	public FarmManager() {
		farms = new ArrayList<Farm>();
		Farm test1 = new Farm("1");
		test1.addMilk("12", "2015-3-20");
		test1.addMilk("114", "2016-2-20");
		test1.addMilk("56", "2015-8-12");
		test1.addMilk("132", "2018-12-31");
		test1.addMilk("1200", "2017-4-3");
		farms.add(test1);
		
	}
}
