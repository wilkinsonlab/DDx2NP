package es.cbgp.upm.ddxotn.samples.oddx.diagnosis;

import java.util.ArrayList;

/**
 * Class that represents a clinical case.
 * @author Alejandro Rodríguez González (Centre of Biotechnology and Plant Genomics, UPM)
 *
 */
public class ClinicalCase {

	private Patient patient;
	private ArrayList<Diagnosis> diagnosis;
	private String id;

	/**
	 * Constructor.
	 * @param id Receives the id.
	 */
	public ClinicalCase(String id) {
		this.id = id;
		this.diagnosis = new ArrayList<Diagnosis>();
	}

	/**
	 * Method to get the ID.
	 * @return the id.
	 */
	public String getId() {
		return id;
	}

	/**
	 * Method to set the id.
	 * @param id Receives the id.
	 */
	public void setId(String id) {
		this.id = id;
	}
	
	/**
	 * Method to add a diagnosis.
	 * @param d Receives the diagnosis.
	 */
	public void addDiagnosis(Diagnosis d) {
		this.diagnosis.add(d);
	}

	/**
	 * Method to get the patient.
	 * @return the value.
	 */
	public Patient getPatient() {
		return patient;
	}

	/**
	 * Method to set the patient.
	 * @param patient Receives the patient.
	 */
	public void setPatient(Patient patient) {
		this.patient = patient;
	}

	/**
	 * Method to get the diagnosis.
	 * @return the value.
	 */
	public ArrayList<Diagnosis> getDiagnosis() {
		return diagnosis;
	}

	/**
	 * Method to set the diagnosis.
	 * @param diagnosis Receives the value.
	 */
	public void setDiagnosis(ArrayList<Diagnosis> diagnosis) {
		this.diagnosis = diagnosis;
	}

	/**
	 * toString()
	 */
	public String toString() {
		String ret = "";
		ret += "Clinical case: " + id + "\n";
		ret += "Patient: " + patient.getId() + "\n";
		if (patient.getSigns().size() > 0) {
			ret += "\tSigns:\n";
			for (int i = 0; i < patient.getSigns().size(); i++) {
				ret += "\t\t[!] " + patient.getSigns().get(i) + "\n";
			}
		}
		if (patient.getDiagnosticTests().size() > 0) {
			ret += "\tDiagnostic tests:\n";
			for (int i = 0; i < patient.getDiagnosticTests().size(); i++) {
				ret += "\t\t[!] " + patient.getDiagnosticTests().get(i) + "\n";
			}
		}
		ret += "\n\nDiagnosis:\n";
		for (int i = 0; i < this.diagnosis.size(); i++) {
			ret += "\tDiagnosis:\n";
			ret += "\t\tDisease: " + this.diagnosis.get(i).getDisease() +"\n";
			ret += "\t\tDiagnosis method: " + this.diagnosis.get(i).getPhysician() +"\n";
		}
		return ret;
	}
}
