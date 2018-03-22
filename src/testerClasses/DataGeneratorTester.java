package testerClasses;

import dataGenerator.DataGenerator;

/**
 * Tests data generation
 * 
 * All code belongs to professor
 */
public class DataGeneratorTester {

	public static void main(String[] args) {
		int totalSize = 2000; 
		int n = 20, m = 10; 
		DataGenerator dg = new DataGenerator(n, m, totalSize);
		dg.generateData(); 
		dg.printSizes();
		dg.printSets();
	}
}
