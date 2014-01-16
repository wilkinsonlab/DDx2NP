package es.cbgp.upm.ddxotn.samples.oddx.kbgen;

/**
 * ID is the SNOMED ID (with an I)
 * @author Alejandro Rodríguez González (Centre of Biotechnology and Plant Genomics, UPM)
 */
public class Disease {

	private String name;
	private String id;
	private String description;

	/**
	 * Constructor.
	 * @param id Receives the ID.
	 * @param n Receives the name.
	 * @param d Receives the description.
	 */
	public Disease(String id, String n, String d) {
		this.id = id;
		this.name = n;
		this.description = d;
	}

	/**
	 * Method to get the name.
	 * @return the value.
	 */
	public String getName() {
		return name;
	}

	/**
	 * Method to set the name.
	 * @param name Value.
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * Method to get the id.
	 * @return the value.
	 */
	public String getId() {
		return id;
	}

	/**
	 * Method to set the ID.
	 * @param id Value.
	 */
	public void setId(String id) {
		this.id = id;
	}
	/**
	 * Method to get the description.
	 * @return the value.
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * Method to set the description.
	 * @param description Value.
	 */
	public void setDescription(String description) {
		this.description = description;
	}
	
	/**
	 * toString() method.
	 */
	public String toString() {
		return "ID: " + id + " [ " + name + " ]";
	}
}
