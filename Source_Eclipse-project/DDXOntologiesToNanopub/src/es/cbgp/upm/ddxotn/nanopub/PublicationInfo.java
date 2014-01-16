package es.cbgp.upm.ddxotn.nanopub;

import java.util.Calendar;

/**
 * Publication Info data.
 * @author Alejandro Rodríguez González (Centre of Biotechnology and Plant Genomics, UPM)
 *
 */
public class PublicationInfo {

	private Calendar created;
	private String creator;
	private String createdBy;
	
	/**
	 * Method to get created.
	 * @return the value.
	 */
	public Calendar getCreated() {
		return created;
	}
	/**
	 * Method to set created.
	 * @param created Value.
	 */
	public void setCreated(Calendar created) {
		this.created = created;
	}
	/**
	 * Method to get creator.
	 * @return the value.
	 */
	public String getCreator() {
		return creator;
	}
	/**
	 * Method to set creator.
	 * @param creator Value.
	 */
	public void setCreator(String creator) {
		this.creator = creator;
	}
	/**
	 * Method to get created by.
	 * @return the value.
	 */
	public String getCreatedBy() {
		return createdBy;
	}
	/**
	 * Method to set created by.
	 * @param createdBy Value.
	 */
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}
	

}
