package p1MainClasses;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.AbstractMap;
import java.util.ArrayList;
import dataGenerator.DataGenerator;
import setIntersectionFinders.IntersectionFinder1_2;
import setIntersectionFinders.IntersectionFinder3;
import setIntersectionFinders.IntersectionFinder4;
import timeStrategies.StrategiesTimeCollection;

/**
 * Part2 of P1 programming project
 * Runs all the data analysis procedures implemented in Part1 with differing sizes
 * Records the average time taken by each size and procedure 
 * 
 * Built with code from professor
 *
 * @author Raúl André Vargas Frechel
 * @studentID 801-14-8312
 * @section ICOM 4035-030
 * 
 * @param n amount of phone companies
 * @param m amount of crime events
 * @param initialSize initial size of all data to be tested
 * @param finalSize final size of all data to be tested
 * @param incrementalSizeStep change in size for each run
 * @param repetitionsPerSize repetitions per size
 * @param resultsPerStrategy ArrayList of StrategiesTimeCollection
 */
public class Part2Main {
	private static int n;						// amount of companies
	private static int m;						// amount of events
	private static int initialSize;				// initial size to be tested
	private static int finalSize;				// last size to be tested
	private static int incrementalSizeStep;		// change of sizes (size delta)
	private static int repetitionsPerSize;		// experimental repetitions per size
	private static ArrayList<StrategiesTimeCollection<Integer>> resultsPerStrategy; 

	/**
	 * main method for Part2
	 * 
	 * @param args used in the command line to change the above six int values in order
	 * @throws CloneNotSupportedException
	 */
	public static void main(String[] args) throws CloneNotSupportedException {
		if (args.length <= 6) {
			n = 10;
			m = 50;
			initialSize = 1000;
			finalSize = 50000;
			incrementalSizeStep = 1000;
			repetitionsPerSize = 200;
			if (args.length >= 1)
				n = Integer.parseInt(args[0]);
			if (args.length >= 2)
				m = Integer.parseInt(args[1]);
			if (args.length >= 3)
				initialSize = Integer.parseInt(args[2]);
			if (args.length >= 4)
				finalSize = Integer.parseInt(args[3]);
			if (args.length >= 5)
				incrementalSizeStep = Integer.parseInt(args[4]);
			if (args.length == 6)
				repetitionsPerSize = Integer.parseInt(args[5]);
			resultsPerStrategy = new ArrayList<>();
			resultsPerStrategy.add(new StrategiesTimeCollection<>(new IntersectionFinder1_2<Integer>("1")));
			resultsPerStrategy.add(new StrategiesTimeCollection<>(new IntersectionFinder1_2<Integer>("2")));
			resultsPerStrategy.add(new StrategiesTimeCollection<>(new IntersectionFinder3<Integer>("3")));
			resultsPerStrategy.add(new StrategiesTimeCollection<>(new IntersectionFinder4<Integer>("4")));
			for(StrategiesTimeCollection<Integer> strat: resultsPerStrategy) {
				System.out.println(strat.getStrategyName());
			}
			run();
			// save the results for each strategy....
			try {
				saveResults();
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} 
			System.out.println("Find the saved data in the folder experimentalResults");
		} 
		else 
			System.out.println("Invalid number of parameters. Must be <= 6.");
	}

	/**
	 * The brunt of the main class
	 * It is in charge of running each strategy, for each size, for each repetition
	 * Leaving the command line empty (with the default main values) this runs a total
	 * of 40,000 trials each 800 increasing in size by 1000
	 * 
	 * @throws CloneNotSupportedException
	 */
	public static void run() throws CloneNotSupportedException { 
		if (resultsPerStrategy.isEmpty())
			throw new IllegalStateException("No strategy has been added."); 
		int counter=0;
		int total=((((finalSize-initialSize)/incrementalSizeStep)+1)*repetitionsPerSize)*4;
		for (int size=initialSize; size<=finalSize; size+=incrementalSizeStep) { 
			// For each strategy, reset the corresponding variable that will be used
			// to store the sum of times that the particular strategy exhibits for
			// the current size size
			for (StrategiesTimeCollection<Integer> strategy : resultsPerStrategy) 
				strategy.resetSum();  
			// Run all trials for the current size. 
			for (int r = 0; r<repetitionsPerSize; r++) {
				// The following will be the common dataset to be used in the current 
				// trial by all the strategies being tested.
				DataGenerator dg = new DataGenerator(n, m, size);
				Integer[][][] data = (Integer[][][]) dg.generateData();
				// Apply each one of the strategies being tested using the previous 
				// dataset (of size size) as input; and, for each, estimate the time
				// that the execution takes. 
				for (StrategiesTimeCollection<Integer> strategy : resultsPerStrategy) {  
					// no need to clone the data set to be used by each strategy since
					// no modification of it is done in the process...
					long startTime = System.nanoTime(); // System.currentTimeMillis();   // time before

					strategy.runTrial(data.clone());   // run the particular strategy...

					long endTime = System.nanoTime(); // System.currentTimeMillis();    // time after

					// accumulate the estimated time (add it) to sum of times that
					// the current strategy has exhibited on trials for datasets
					// of the current size. 
					strategy.incSum((int) (endTime-startTime));
					
					counter++;
					System.out.print(counter+" of "+total);
					System.out.print("\t[");
					for (int d=1; d<=counter/incrementalSizeStep; d++)
						System.out.print(">");
					for (int l=(counter/incrementalSizeStep)+1; l<=total/incrementalSizeStep; l++)
						System.out.print("-");
					System.out.println("]");
				}
			}			
			for (StrategiesTimeCollection<Integer> strategy : resultsPerStrategy) { 
				strategy.add(new AbstractMap.SimpleEntry<Integer, Float>
				(size, (strategy.getSum()/((float) repetitionsPerSize)))); 
			}
		}
		System.out.print("DONE\t\t[");
		for (int f=1; f<=total/incrementalSizeStep; f++)
			System.out.print("|");
		System.out.println("]");
	}

	/**
	 * Saves all estimated times for all strategies for all sizes in a text file
	 * Text file allResults.txt found in folder experimentalResults
	 * @throws FileNotFoundException
	 */
	public static void saveResults() throws FileNotFoundException { 		
		PrintStream out = new PrintStream(new File("experimentalResults", "allResults.txt"));
		out.print("Size");
		for (StrategiesTimeCollection<Integer> trc : resultsPerStrategy) 
			out.print("\tProcedure"+trc.getStrategyName()); 
		out.println();

		int numberOfExperiments = resultsPerStrategy.get(0).size(); 
		for (int i=0; i<numberOfExperiments; i++) {
			out.print(resultsPerStrategy.get(0).get(i).getKey()+"\t");
			for (StrategiesTimeCollection<Integer> trc : resultsPerStrategy)
				out.print(trc.get(i).getValue()+"\t");
			out.println(); 
		}			
		out.close();
	}
}