package es.cbgp.upm.ddxotn.nanopub;

import java.util.ArrayList;
import java.util.Calendar;

import es.cbgp.upm.ddxotn.objects.AssertionTriple;

/**
 * Provenance data.
 * 
 * @author Alejandro Rodríguez González (Centre of Biotechnology and Plant
 *         Genomics, UPM)
 * 
 */
public class Provenance {

	private String coverageContent;
	private String description;
	private String identifier;
	private String publisher;
	private String source;
	private String title;
	private Calendar created;
	private String creator;

	/**
	 * Apart of typical data, we can have some triples in provenance data
	 * refered to the assertions of the nanopublication.
	 */
	private ArrayList<AssertionTriple> assertionTriples;

	/**
	 * We can also have norma triples as part of the provenance data.
	 */
	private ArrayList<Triple> triples;

	/**
	 * Constructor
	 */
	public Provenance() {
		this.assertionTriples = new ArrayList<AssertionTriple>();
		this.triples = new ArrayList<Triple>();
	}

	public ArrayList<Triple> getTriples() {
		return this.triples;
	}

	/**
	 * Method to get the assertion triples.
	 * 
	 * @return the list.
	 */
	public ArrayList<AssertionTriple> getAssertionTriples() {
		return this.assertionTriples;
	}

	/**
	 * Method to add a triple to the list.
	 * 
	 * @param t
	 *            Receives the assertion.
	 */
	public void addTriple(Triple t) {
		this.triples.add(t);
	}

	/**
	 * Method to add an assertion triple to the list.
	 * 
	 * @param t
	 *            Receives the assertion.
	 */
	public void addAssertionTriple(AssertionTriple t) {
		this.assertionTriples.add(t);
	}

	/**
	 * Method to get coverage content.
	 * 
	 * @return the value.
	 */
	public String getCoverageContent() {
		return coverageContent;
	}

	/**
	 * Method to set the coverage content.
	 * 
	 * @param coverageContent
	 *            Value.
	 */
	public void setCoverageContent(String coverageContent) {
		this.coverageContent = coverageContent;
	}

	/**
	 * Method to get the description.
	 * 
	 * @return the value.
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * Method to set the description.
	 * 
	 * @param description
	 *            Value.
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * Method to get the identifier.
	 * 
	 * @return the value.
	 */
	public String getIdentifier() {
		return identifier;
	}

	/**
	 * Method to set the identifier.
	 * 
	 * @param identifier
	 *            Value.
	 */
	public void setIdentifier(String identifier) {
		this.identifier = identifier;
	}

	/**
	 * Method to get the publisher.
	 * 
	 * @return the value.
	 */
	public String getPublisher() {
		return publisher;
	}

	/**
	 * Method to set the publisher.
	 * 
	 * @param publisher
	 *            Value.
	 */
	public void setPublisher(String publisher) {
		this.publisher = publisher;
	}

	/**
	 * Method to get the source.
	 * 
	 * @return the value.
	 */
	public String getSource() {
		return source;
	}

	/**
	 * Method to set the source.
	 * 
	 * @param source
	 *            Value.
	 */
	public void setSource(String source) {
		this.source = source;
	}

	/**
	 * Method to get the title.
	 * 
	 * @return the value.
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * Method to set the title.
	 * 
	 * @param title
	 *            Value.
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * Method to get created.
	 * 
	 * @return the value.
	 */
	public Calendar getCreated() {
		return created;
	}

	/**
	 * Method to set created.
	 * 
	 * @param created
	 *            Value.
	 */
	public void setCreated(Calendar created) {
		this.created = created;
	}

	/**
	 * Method to get creator.
	 * 
	 * @return the value.
	 */
	public String getCreator() {
		return creator;
	}

	/**
	 * Method to set creator.
	 * 
	 * @param creator
	 *            Value.
	 */
	public void setCreator(String creator) {
		this.creator = creator;
	}

}
