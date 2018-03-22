package p1MainClasses;

import java.io.FileNotFoundException;
import dataGenerator.DataReader;
import interfaces.MySet;
import mySetImplementations.Set1;
import mySetImplementations.Set2;
import setIntersectionFinders.AbstractIntersectionFinder;
import setIntersectionFinders.IntersectionFinder1_2;
import setIntersectionFinders.IntersectionFinder3;
import setIntersectionFinders.IntersectionFinder4;

/**
 * Part1 of P1 programming project
 * Takes the data created by FilesGeneratorMain to process
 * Implements the appropiate procedure and IntersectionFinder
 * Ends by displaying the final intersected set of the chosen procedure
 * 
 * Built with code from professor
 *
 * @author Raúl André Vargas Frechel
 * @studentID 801-14-8312
 * @section ICOM 4035-030
 * 
 * @param allData Integer[][][] in which:
 * <li> allData collection of crime events
 * <li> allData[m] collection of files provided by each phone company for event m
 * <li> allData[m][n] collection of numbers provided by company n on event m
 * @param reader DataReader used for reading data files
 * @param t MySet[] which will be the intersection target
 */
public class Part1Main {
	private static Integer[][][] allData;
	private static DataReader reader;
	private static MySet<Integer>[] t;
	
	/**
	 * main method for Part1
	 * 
	 * @param args used to choose witch procedure to run (1-4); default runs all procedures
	 * @throws FileNotFoundException
	 */
	public static void main(String[] args) throws FileNotFoundException {
		if (args.length <= 1) {
			int sol = 0; 
			if (args.length == 1) 
				sol = Integer.parseInt(args[0]);
			reader = new DataReader();
			allData = (Integer[][][]) reader.readDataFiles();
			executeSolution(sol);
		} 
		else 
			System.out.println("Invalid number of parameters. Must be <= 1.");
	}

	/**
	 * Primarily used to decide which procedure to run
	 * 
	 * @param sol procedure to run (1-4); default runs all procedures
	 * @throws FileNotFoundException
	 */
	private static void executeSolution(int sol) throws FileNotFoundException {
		switch (sol) {
		case 1:
			t = intersectionTarget(1);
			IntersectionFinder1_2<Integer> inter1 = new IntersectionFinder1_2<Integer>("1");
			intersectAndOut(inter1, t);
			break;
		case 2:
			t = intersectionTarget(2);
			IntersectionFinder1_2<Integer> inter2 = new IntersectionFinder1_2<Integer>("2");
			intersectAndOut(inter2, t);
			break;
		case 3:
			t = intersectionTarget(3);
			IntersectionFinder3<Integer> inter3 = new IntersectionFinder3<Integer>("3");
			intersectAndOut(inter3, t);
			break;
		case 4:
			t = intersectionTarget(4);
			IntersectionFinder4<Integer> inter4 = new IntersectionFinder4<Integer>("4");
			intersectAndOut(inter4, t);
			break;
		default:
			t = intersectionTarget(1);
			IntersectionFinder1_2<Integer> interAll1 = new IntersectionFinder1_2<Integer>("1");
			intersectAndOut(interAll1, t);
			t = intersectionTarget(2);
			IntersectionFinder1_2<Integer> interAll2 = new IntersectionFinder1_2<Integer>("2");
			intersectAndOut(interAll2, t);
			t = intersectionTarget(3);
			IntersectionFinder3<Integer> interAll3 = new IntersectionFinder3<Integer>("3");
			intersectAndOut(interAll3, t);
			t = intersectionTarget(4);
			IntersectionFinder4<Integer> interAll4 = new IntersectionFinder4<Integer>("4");
			intersectAndOut(interAll4, t);
			break;
		}
	}
	
	/**
	 * Takes the specified procedure to create the target for the according IntersectionFinder
	 * 
	 * @param sol procedure to run
	 * @return MySet[] where each entry is a MySet that contains all the numbers present in an event
	 */
	private static MySet[] intersectionTarget(int sol) {
		t = new MySet[allData.length];
		if (sol==1) {
			for (int i=0; i<allData.length;i++) {
				t[i] = new Set1<>();
				for (int j = 0; j < allData[i].length; j++) {
					for (int k = 0; k < allData[i][j].length; k++) {
						t[i].add(allData[i][j][k]);
					}
				}
			}
			return t;
		}
		else {
			for (int i=0; i<allData.length;i++) {
				t[i] = new Set2<>();
				for (int j = 0; j < allData[i].length; j++) {
					for (int k = 0; k < allData[i][j].length; k++) {
						t[i].add(allData[i][j][k]);
					}
				}
			}
			return t;
		}
	}
	
	/**
	 * Takes the intersection target, intersects it, and displays the final intersected set 
	 * 
	 * @param inter IntersectionFinder used to find intersection
	 * @param t MySet[] target of intersection
	 */
	private static void intersectAndOut(AbstractIntersectionFinder<Integer> inter, MySet<Integer>[] t) {
		MySet<Integer> finalSet = inter.intersectSets(t);
		System.out.println("Final Set by P"+inter.getName()+": "+finalSet.toString());
	}
}