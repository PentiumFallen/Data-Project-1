package setIntersectionFinders;

import java.util.ArrayList;

import interfaces.MySet;
import mySetImplementations.Set2;

/**
 * Finds the intersection of a MySet<E>[] t that represents
 * all the numbers from all the companies that were present
 * in all the crime events for procedure 3
 * 
 * Partial code from professor
 * 
 * @author Raúl André Vargas Frechel
 *
 * @param <E> data type (int for our story)
 */
public class IntersectionFinder3<E> extends AbstractIntersectionFinder<E>{
	/**
	 * Constructor for the Intersection finder
	 * @param name (AKA the procedure used)
	 */
	public IntersectionFinder3(String name) {
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
		for (MySet<E> mySet : t) {
			for (E e : mySet) {
				allElements.add(e);
			}
		}
		allElements.sort(null);
		MySet<E> set = new Set2<E>(); // sets in P3's solution are of type Set2
		E e = allElements.get(0);
		Integer c = 1;
		for (int i=1; i < allElements.size(); i++) {
			if (allElements.get(i).equals(e))
				c++;
			else {
				if (c == m)
					set.add(e); // m is as in the previous discussion
				e = allElements.get(i);
				c = 1;
			}
		}
		if (c == m)
			set.add(allElements.get(allElements.size()-1));
		return set;
	}
}