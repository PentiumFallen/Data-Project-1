package timeStrategies;

import java.util.AbstractMap;
import java.util.ArrayList;
import interfaces.IntersectionFinder;
import interfaces.MySet;
import mySetImplementations.Set1;
import mySetImplementations.Set2;

/**
 * An ArrayList of Map entries to store each size and the average time taken for each size
 * Contains the IntersectionFinder that will be used for the chosen procedure/strategy
 * Also contains a sum that corresponds to the total time taken by the strategy of the current 
 * size through all its repetitions. This will later be averaged. 
 *
 * @author Raúl André Vargas Frechel
 */
public class StrategiesTimeCollection<E> extends ArrayList<AbstractMap.SimpleEntry<Integer, Float>>{

	private static final long serialVersionUID = 1L;
	private IntersectionFinder<E> inter;
	private int sum; //sum of times to later average

	/**
	 * Constructor for the strategy
	 * 
	 * @param inter IntersectionFinder to be used in this strategy
	 */
	public StrategiesTimeCollection(IntersectionFinder<E> inter) {
		this.inter = inter;
	}

	/**
	 * @return the strategy's name (AKA the IntersectionFinder used)
	 */
	public String getStrategyName() {
		return inter.getName();
	}

	/**
	 * resets the sum value to run the strategy with a new size
	 */
	public void resetSum() {
		sum = 0;
	}
	
	/**
	 * Direct interaction with the sum value
	 * Adds the time of each repetition
	 * 
	 * @param estimatedTime of current repetitions
	 */
	public void incSum(int estimatedTime) {
		sum += estimatedTime;
	}

	/**
	 * @return the current value of sum
	 */
	public float getSum() {
		return sum;
	}
	
	/**
	 * Runs intersectSets from inter using the code from intersectionTarget from Part1Main
	 * 
	 * @param allData Integer[][][] in which:
	 * <li> allData collection of crime events
	 * <li> allData[m] collection of files provided by each phone company for event m
	 * <li> allData[m][n] collection of numbers provided by company n on event m
	 */
	public void runTrial(Integer[][][] allData) {
		//executes the strategy
		//must create the MySet[] (sets T0->Tm-1) and intersect
		MySet<Integer>[] t = new MySet[allData.length];
		if (inter.getName()=="1") {
			for (int i=0; i<allData.length;i++) {
				t[i] = new Set1<>();
				for (int j = 0; j < allData[i].length; j++) {
					for (int k = 0; k < allData[i][j].length; k++) {
						t[i].add(allData[i][j][k]);
					}
				}
			}
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
		}
		inter.intersectSets((MySet<E>[]) t);
	}
}