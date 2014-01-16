package es.cbgp.upm.ddxotn.samples.oddx.diagnosis;

/**
 * Class that represents the diagnosis made and by who
 * 
 * @author Alejandro Rodríguez González (Centre of Biotechnology and Plant
 *         Genomics, UPM)
 * 
 */
public class Diagnosis {

	private String disease;
	private String physician;

	/**
	 * Constructor.
	 * 
	 * @param d
	 *            Receives the diagnosed disease.
	 * @param p
	 *            Receives the physician/system.
	 */
	public Diagnosis(String d, String p) {
		this.disease = d;
		this.physician = p;
	}

	/**
	 * Method to get the disease.
	 * 
	 * @return the value.
	 */
	public String getDisease() {
		return disease;
	}

	/**
	 * Method to set the diasease.
	 * 
	 * @param disease
	 *            Receives the disease.
	 */
	public void setDisease(String disease) {
		this.disease = disease;
	}

	/**
	 * Method to get the physician/system.
	 * 
	 * @return the value.
	 */
	public String getPhysician() {
		return physician;
	}

	/**
	 * Method to set the phyisician/system.
	 * 
	 * @param physician
	 *            Receives the value.
	 */
	public void setPhysician(String physician) {
		this.physician = physician;
	}
}
