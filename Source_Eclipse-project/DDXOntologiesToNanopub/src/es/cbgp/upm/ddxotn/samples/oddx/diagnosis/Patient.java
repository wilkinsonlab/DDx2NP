package es.cbgp.upm.ddxotn.samples.oddx.diagnosis;

import java.util.ArrayList;

/**
 * 
 * @author Alejandro Rodríguez González (Centre of Biotechnology and Plant
 *         Genomics, UPM)
 * 
 */
public class Patient {

	private String id;
	private ArrayList<String> signs;
	private ArrayList<String> diagnosticTests;

	/**
	 * Constructor.
	 * 
	 * @param id
	 *            Receives the id.
	 */
	public Patient(String id) {
		this.id = id;
		this.signs = new ArrayList<String>();
		this.diagnosticTests = new ArrayList<String>();
	}

	/**
	 * Method to add a sign to the patient.
	 * 
	 * @param s
	 *            Receives the sign.
	 */
	public void addSign(String s) {
		this.signs.add(s);
	}

	/**
	 * Method to add a diagnostic test to the patient.
	 * 
	 * @param dt
	 *            Receives the diagnostic test.
	 */
	public void addDiagnosticTest(String dt) {
		this.diagnosticTests.add(dt);
	}

	/**
	 * Method to get the id.
	 * 
	 * @return the value.
	 */
	public String getId() {
		return id;
	}

	/**
	 * Method to set the ID.
	 * 
	 * @param id
	 *            Receives the id.
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * Method to get the signs.
	 * 
	 * @return the value.
	 */
	public ArrayList<String> getSigns() {
		return signs;
	}

	/**
	 * Method to set the signs.
	 * 
	 * @param signs
	 *            Receives the signs.
	 */
	public void setSigns(ArrayList<String> signs) {
		this.signs = signs;
	}

	/**
	 * Method to get the diagnostic tests.
	 * 
	 * @return the value.
	 */
	public ArrayList<String> getDiagnosticTests() {
		return diagnosticTests;
	}

	/**
	 * Method to set the diagnostic tests.
	 * 
	 * @param diagnosticTests
	 *            Receives the diagnostic tests.
	 */
	public void setDiagnosticTests(ArrayList<String> diagnosticTests) {
		this.diagnosticTests = diagnosticTests;
	}

}
