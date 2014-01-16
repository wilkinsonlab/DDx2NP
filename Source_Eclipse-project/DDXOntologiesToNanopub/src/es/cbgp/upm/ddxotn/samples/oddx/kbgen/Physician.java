package es.cbgp.upm.ddxotn.samples.oddx.kbgen;

/**
 * Class that represents a Physician.
 * @author Alejandro Rodríguez González (Centre of Biotechnology and Plant Genomics, UPM)
 *
 */
public class Physician {

	private String name;
	
	/**
	 * Constructor.
	 * @param n Receives the name.
	 */
	public Physician(String n) {
		name = n;
	}
	
	/**
	 * Method to get the name.
	 * @return the value.
	 */
	public String getName() {
		return this.name;
	}
}
