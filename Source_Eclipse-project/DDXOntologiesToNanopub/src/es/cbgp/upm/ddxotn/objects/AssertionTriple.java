package es.cbgp.upm.ddxotn.objects;

/**
 * Class to store information about an assertion triple.
 * 
 * An assertion triple is a triple where the subject is the assertion.
 * 
 * @author Alejandro Rodríguez González (Centre of Biotechnology and Plant Genomics, UPM)
 *
 */
public class AssertionTriple {

	private String predicate;
	private Object object;

	/**
	 * Constructor.
	 * @param p Predicate.
	 * @param o Object.
	 */
	public AssertionTriple(String p, Object o) {
		this.predicate = p;
		this.object = o;
	}
/**
 * Method to get the predicate.
 * @return the value.
 */
	public String getPredicate() {
		return predicate;
	}

	/**
	 * Method to set the predicate.
	 * @param predicate Value.
	 */
	public void setPredicate(String predicate) {
		this.predicate = predicate;
	}
	/**
	 * Method to get the object.
	 * @return the value.
	 */
	public Object getObject() {
		return object;
	}

	/**
	 * Method to set the object.
	 * @param object Value.
	 */
	public void setObject(String object) {
		this.object = object;
	}
}
