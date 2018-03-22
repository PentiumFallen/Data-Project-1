package setIntersectionFinders;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import interfaces.MySet;
import mySetImplementations.Set2;

/**
 * Finds the intersection of a MySet<E>[] t that represents
 * all the numbers from all the companies that were present
 * in all the crime events for procedure 4
 * 
 * Partial code from professor
 * 
 * @author Raúl André Vargas Frechel
 *
 * @param <E> data type (int for our story)
 */
public class IntersectionFinder4<E> extends AbstractIntersectionFinder<E>{

	/**
	 * Constructor for the Intersection finder
	 * @param name (AKA the procedure used)
	 */
	public IntersectionFinder4(String name) {
		super(name);
	}

	/**
	 * Performs the set intersection
	 * 
	 * @param MySet[] t as discussed above
	 * @return MySet final intersected set
	 */
	public MySet<E> intersectSets(MySet<E>[] t) {
		int m = t.length;
		ArrayList<E> allElements = new ArrayList<E>();
		for (int i = 0; i<m; i++) {
			for (E e : t[i]) {
				allElements.add(e);
			}
		}
		HashMap<E, Integer> map = new HashMap<>();
		for (E e : allElements) {
			Integer c = map.getOrDefault(e, 0);
			map.put(e, c+1);
		}
		MySet<E> set = new Set2<>();
		for (Map.Entry<E, Integer> entry : map.entrySet())
			if (entry.getValue() == m)
				set.add(entry.getKey());
		return set;
	}
}
