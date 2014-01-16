package es.cbgp.upm.ddxotn.nanopub;

/**
 * Class that represents the assertion that will be stored in the nanopub.
 * @author Alejandro Rodríguez González (Centre of Biotechnology and Plant
 *         Genomics, UPM)
 * 
 */
public class Assertion extends Triple {

	/**
	 * Constructor.
	 * @param s Receives the subject.
	 * @param p Receives the predicate.
	 * @param o Receives the object.
	 */
	public Assertion(String s, String p, String o) {
		super(s, p, o);
	}

	/**
	 * Empty constructor.
	 */
	public Assertion() {
		
	}
}
