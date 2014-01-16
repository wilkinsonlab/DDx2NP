package es.cbgp.upm.ddxotn.nanopub;

/**
 * Class that represents the assertion that will be stored in the nanopub.
 * 
 * @author Alejandro Rodríguez González (Centre of Biotechnology and Plant
 *         Genomics, UPM)
 * 
 */
public class Triple {

	public String subject;
	public String predicate;
	public String object;

	/**
	 * Constructor.
	 * @param s Receives subject.
	 * @param p Receives predicate.
	 * @param o Receives object.
	 */
	public Triple(String s, String p, String o) {
		this.subject = s;
		this.predicate = p;
		this.object = o;
	}

	/**
	 * Empty constructor.
	 */
	public Triple() {

	}

	/**
	 * Method to get the subject.
	 * 
	 * @return A String.
	 */
	public String getSubject() {
		return subject;
	}

	/**
	 * Method to set the subject.
	 * 
	 * @param subject
	 *            The subject.
	 */
	public void setSubject(String subject) {
		this.subject = subject;
	}

	/**
	 * Method to get the predicate.
	 * 
	 * @return A String.
	 */
	public String getPredicate() {
		return predicate;
	}

	/**
	 * Method to set the predicate.
	 * 
	 * @param predicate
	 *            The predicate.
	 */
	public void setPredicate(String predicate) {
		this.predicate = predicate;
	}

	/**
	 * Method to get the object.
	 * 
	 * @return A String.
	 */
	public String getObject() {
		return object;
	}

	/**
	 * Method to set the object.
	 * 
	 * @param object
	 *            The object.
	 */
	public void setObject(String object) {
		this.object = object;
	}

}