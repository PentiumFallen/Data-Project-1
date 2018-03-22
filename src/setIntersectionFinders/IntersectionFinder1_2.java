package setIntersectionFinders;

import interfaces.MySet;
import mySetImplementations.Set1;
import mySetImplementations.Set2;

/**
 * Finds the intersection of a MySet<E>[] t that represents
 * all the numbers from all the companies that were present
 * in all the crime events for procedures 1 and 2
 * 
 * Partial code from professor
 *
 * @author Raúl André Vargas Frechel
 *
 * @param <E> data type (int for our story)
 */
public class IntersectionFinder1_2<E> extends AbstractIntersectionFinder<E> {
	/**
	 * Constructor for the Intersection finder
	 * @param name (AKA the procedure used)
	 */
	public IntersectionFinder1_2(String name) {
		super(name);
	}

	/**
	 * Performs the set intersection
	 * Delegated to procedure1(t) and procedure2(t)
	 * because both procedures use the same intersection finder
	 * but are implemented differently
	 * 
	 * @param MySet[] t as discussed above
	 * @return MySet final set as delivered by procedure1 or procedure 2
	 */
	public MySet<E> intersectSets(MySet<E>[] t) {
		if (this.getName()=="1")
			return procedure1(t);
		if (this.getName()=="2")
			return procedure2(t);
		return null;
	}
	
	/**
	 * Intersects t using Set1
	 * @param t crime event set
	 * @return intersected Set1
	 */
	public MySet<E> procedure1(MySet<E>[] t) {
		Set1<E> set = (Set1<E>) t[0];
		for(int i=1; i<t.length; i++) {
			for(int j=0; j<set.size(); j++) {
				Object[] setArray = set.toArray();
				if(!t[i].contains((E) setArray[j])) {
					set.remove((E) setArray[j]);
				}
			}
		}
		return set;
	}
	
	/**
	 * Intersects t using Set2
	 * @param t crime event set
	 * @return intersected Set2
	 */
	public MySet<E> procedure2(MySet<E>[] t) {
		Set2<E> set = (Set2<E>) t[0];
		for(int i=1; i<t.length; i++) {
			for(E e: set) {
				if(!t[i].contains(e)) {
					set.remove(e);
				}
			}
		}
		return set;
	}
}
