package es.cbgp.upm.ddxotn.samples.oddx.diagnosis;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Properties;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.io.FileUtils;

import es.cbgp.upm.ddxotn.logic.DDxDOURIs;
import es.cbgp.upm.ddxotn.logic.StaticUtils;
import es.cbgp.upm.ddxotn.nanopub.Triple;
import es.cbgp.upm.ddxotn.objects.AssertionTriple;

/**
 * Class to extract diagnosis from ODDx system.
 * 
 * @author Alejandro Rodríguez González (Centre of Biotechnology and Plant
 *         Genomics, UPM)
 * 
 */
public class ODDxDiagnosisDataExtractor {

	private ArrayList<ClinicalCase> clinicalCases;
	private final String NAMESPACENANOPUB = "file:///C:/phdonts/ddxont.owl/diagnosis";
	private final String INTENSITY[] = { "Very low", "Low", "Medium", "High",
			"Very high", "Extreme" };
	private final String DURATION[] = { "Less than 15 minutes",
			"About 15 minutes", "About 30 minutes", "About 1 hour",
			"About 1 hour and a half", "About 2 hours", "More than 2 hours" };
	private final String METHODS[] = { "Direct extraction", "Subcutaneous",
			"Biopsy", "Analysis", "Direct observation" };
	private final String VALUES[] = { "Normal", "Over the range",
			"Down the range" };
	private final String CLINICAL_CASES_FOLDER = "data/clinicalcases/";

	/**
	 * Method to execute.
	 * 
	 * @throws Exception
	 *             Can throw exception.
	 */
	public void execute() throws Exception {
		this.cleanFolder();
		this.clinicalCases = new ArrayList<ClinicalCase>();
		loadClinicalCases();
		createNanopubs();
		System.out.println("Diagnosis nanopubs have been created. Check nanopubs/ddx folder");
	}

	private void cleanFolder() throws Exception {
		File f = new File("nanopubs/ddx");
		try {
			FileUtils.deleteDirectory(f);
			FileUtils.forceMkdir(f);
		} catch (IOException e) {
			throw new Exception("Error deleting kb directory. Additional info: " + e.getMessage());
		}
	}

	/**
	 * Method to create the nanopubs.
	 * 
	 * @throws Exception
	 *             Can throw an exception.
	 */
	private void createNanopubs() throws Exception {
		System.out.println("Creating nanopubs ...");
		for (int i = 0; i < this.clinicalCases.size(); i++) {
			ClinicalCase cc = this.clinicalCases.get(i);
			System.out.println("Creating nanopub for clinical case "
					+ cc.getId());
			String baseDir = "nanopubs/ddx/" + cc.getId() + "/";
			/*
			 * We create the nanocollection of patient datas.
			 */
			ODDxDiagnosisNanopubCollection odnc = createDiagnosisNanopubCollection(cc);
			/*
			 * We create the nanopubs inside the nanocollection for the signs.
			 */
			for (int j = 0; j < cc.getPatient().getSigns().size(); j++) {
				System.out
						.println("Creating nanopubs (about signs) inside of nanopub collection ["
								+ odnc.getNameID() + "]");
				ODDxDiagnosisNanopub odn = createDiagnosisFindingNanopub(odnc,
						cc, cc.getPatient().getSigns().get(j));
				odnc.addNanopub(odn);
			}
			/*
			 * We create the nanopubs inside the nanocollection for the
			 * diagnostic tests.
			 */
			for (int j = 0; j < cc.getPatient().getDiagnosticTests().size(); j++) {
				System.out
						.println("Creating nanopubs (about diagnostic tests) inside of nanopub collection ["
								+ odnc.getNameID() + "]");
				ODDxDiagnosisNanopub odn = createDiagnosisTestNanopub(odnc, cc,
						cc.getPatient().getDiagnosticTests().get(j));
				odnc.addNanopub(odn);
			}
			System.out.println("... end of nanopubcollection generation!");
			if (new File(baseDir).mkdir()) {
				/*
				 * We save the nanopubcollection first (it will automatically
				 * save all the nanopubs include inside)
				 */
				System.out
						.println("Writing nanopubcollection and associated nanopubs..");
				try {
					odnc.save(baseDir + odnc.getNameID() + ".npc");
				} catch (Exception e) {
					throw new Exception("Error writing nanopubcollection '"
							+ baseDir + odnc.getNameID() + ".npc"
							+ "'. Additional info: " + e.getMessage());
				}
				/*
				 * We create the nanopub for each diagnosis. Given that we don't
				 * have any provenance information about how the physician reach
				 * the diagnosis, we are going to use a random number of the
				 * original findings of the patient as findings that allows to
				 * reach the diagnosis.
				 */
				System.out.println("Creating diagnosis nanopubs..");
				for (int j = 0; j < cc.getDiagnosis().size(); j++) {
					Diagnosis ddx = cc.getDiagnosis().get(j);
					System.out.println("DDx nanopub. Disease: "
							+ ddx.getDisease() + " - Physician/System: "
							+ ddx.getPhysician());
					ODDxDiagnosisNanopub odn;
					try {
						odn = createDiagnosisNanopub(ddx, cc, odnc, baseDir);
						String fToSave = baseDir + "Diagnosis_"
								+ ddx.getDisease() + ".np";
						odn.save(fToSave);
					} catch (Exception e) {
						throw new Exception("Error creating nanopub '"
								+ "Diagnosis_" + ddx.getDisease() + ".np"
								+ "'. Additional info: " + e.getMessage());
					}

				}
			} else {
				throw new Exception("Error creating dir: " + baseDir);
			}

		}
	}

	/**
	 * Method to create the diagnosis nanopub.
	 * 
	 * @param ddx
	 *            Receives the diagnosis.
	 * @param cc
	 *            Receives the clinical case.
	 * @param odnc
	 *            Receives the collection of the input data.
	 * @param baseDir
	 *            Receives the base directory where the nanopubs are stored.
	 * @return the object.
	 */
	private ODDxDiagnosisNanopub createDiagnosisNanopub(Diagnosis ddx,
			ClinicalCase cc, ODDxDiagnosisNanopubCollection odnc, String baseDir)
			throws Exception {
		int subs = new java.util.Random(System.currentTimeMillis()).nextInt(27);
		String miniHashDiagnosis = DigestUtils.md5Hex(
				ddx.getDisease() + ddx.getPhysician()
						+ System.currentTimeMillis()).substring(subs, subs + 5);
		String internalIDddx = DDxDOURIs.getInstance().DDXDO_DIAGNOSIS_RELATION
				+ ":" + miniHashDiagnosis;
		String nanopubIDddx = DigestUtils.md5Hex(internalIDddx);
		System.out.println("\tinternalID: " + internalIDddx);
		System.out.println("\tnanopubID: " + nanopubIDddx);
		System.out.println("\tnameID: " + ddx.getDisease());

		ODDxDiagnosisNanopub odn = new ODDxDiagnosisNanopub(nanopubIDddx,
				internalIDddx, ddx.getDisease(), NAMESPACENANOPUB);
		/*
		 * Diagnosis assertion.
		 */
		odn.getAssertion().setSubject(
				DDxDOURIs.getInstance().DDXDO_PATIENT_URI
						+ cc.getPatient().getId());
		odn.getAssertion().setPredicate(
				DDxDOURIs.getInstance().DDXDO_DIAGNOSIS_RELATION);
		odn.getAssertion().setObject(
				DDxDOURIs.getInstance().SNOMED_URI + ddx.getDisease());
		System.out.println("\tAssertion: ("
				+ DDxDOURIs.getInstance().DDXDO_PATIENT_URI
				+ cc.getPatient().getId() + ","
				+ DDxDOURIs.getInstance().DDXDO_DIAGNOSIS_RELATION + ","
				+ DDxDOURIs.getInstance().SNOMED_URI + ddx.getDisease() + ")");
		/*
		 * We introduce in provenance one triple about input data.
		 */
		odn.getProvenance().addTriple(
				new Triple(DDxDOURIs.getInstance().DDXDO_PATIENT_URI
						+ cc.getPatient().getId(),
						DDxDOURIs.getInstance().DDXDO_HAS_INPUT_DATA_RELATION,
						NAMESPACENANOPUB + "/nanopub_collection/"
								+ odnc.getUniqueCode()));
		System.out.println("\tProvenance: ("
				+ DDxDOURIs.getInstance().DDXDO_PATIENT_URI
				+ cc.getPatient().getId() + ","
				+ DDxDOURIs.getInstance().DDXDO_HAS_INPUT_DATA_RELATION + ","
				+ NAMESPACENANOPUB + "/nanopub_collection/"
				+ odnc.getUniqueCode() + ")");

		odn.getPublicationInfo().setCreated(getRandomDate());
		odn.getPublicationInfo().setCreator("Ont to nanopub system");
		odn.getPublicationInfo().setCreatedBy(ddx.getPhysician());
		System.out.println("\n");
		System.out.println("Creating supporting diagnosis nanopubs..");
		/*
		 * Now, we introduce in provenance a random number of findings and/or
		 * diagnostic tests as evidences that allow to reach the diagnosis
		 * conclusion
		 */
		if (cc.getPatient().getSigns().size() > 0) {
			int nRandSigns = new java.util.Random(System.currentTimeMillis()
					* subs).nextInt(cc.getPatient().getSigns().size() - 1);
			if (nRandSigns <= 0)
				nRandSigns++; // at least one
			System.out.println("Random selected signs: " + nRandSigns
					+ " from: " + cc.getPatient().getSigns().size()
					+ " available");
			for (int i = 0; i < nRandSigns; i++) {
				String sign = cc.getPatient().getSigns().get(i); // we get a
																	// concrete
																	// sign
				String relationID = NAMESPACENANOPUB
						+ "/nanopub/"
						+ DigestUtils.md5Hex(odn.getNanopubID()
								+ new java.util.Random(System
										.currentTimeMillis()).nextInt(10000)
								+ new java.util.Random(System
										.currentTimeMillis() * subs + i)
										.nextInt(10000));
				System.out
						.println("Creating supporting diagnosis nanopub (for sign):");
				ODDxDiagnosisNanopub odnConf = createNanopubDiagnosisConfirmationFinding(
						odn, sign, relationID,
						DDxDOURIs.getInstance().DDXDO_HAS_SIGN_RELATION);
				odn.getProvenance()
						.addTriple(
								new Triple(
										DDxDOURIs.getInstance().DDXDO_PATIENT_URI
												+ cc.getPatient().getId(),
										DDxDOURIs.getInstance().DDXDO_HAS_CLINICAL_FINDING_RELATION,
										relationID));
				String fToSave = baseDir + "Diagnosis_" + ddx.getDisease()
						+ "_SupportSIGN_" + sign + ".np";
				try {
					odnConf.save(fToSave);
				} catch (Exception e) {
					throw new Exception(
							"Error writing diagnosis supported by sign nanopub '"
									+ fToSave + "'. Additional info: "
									+ e.getMessage());
				}
			}
		}
		if (cc.getPatient().getDiagnosticTests().size() > 0) {
			int nRandDTs = new java.util.Random(System.currentTimeMillis()
					* subs)
					.nextInt(cc.getPatient().getDiagnosticTests().size() - 1);
			System.out.println("Random selected diagnostic tests: " + nRandDTs
					+ " from: " + cc.getPatient().getDiagnosticTests().size()
					+ " available");
			for (int i = 0; i < nRandDTs; i++) {
				String dt = cc.getPatient().getDiagnosticTests().get(i); // we
																			// get
																			// a
																			// concrete
																			// diagnostic
																			// test
				String relationID = DigestUtils.md5Hex(odn.getNanopubID()
						+ new java.util.Random(System.currentTimeMillis())
								.nextInt(10000)
						+ new java.util.Random(System.currentTimeMillis()
								* subs + i).nextInt(10000));
				System.out
						.println("Creating supporting diagnosis nanopub (for diagnostic test):");
				ODDxDiagnosisNanopub odnConf = createNanopubDiagnosisConfirmationFinding(
						odn,
						dt,
						relationID,
						DDxDOURIs.getInstance().DDXDO_HAS_DIAGNOSTIC_TEST_RELATION);
				odn.getProvenance()
						.addTriple(
								new Triple(
										DDxDOURIs.getInstance().DDXDO_PATIENT_URI
												+ cc.getPatient().getId(),
										DDxDOURIs.getInstance().DDXDO_HAS_CLINICAL_FINDING_RELATION,
										relationID));
				String fToSave = baseDir + "Diagnosis_" + ddx.getDisease()
						+ "_SupportDT_" + dt + ".np";
				try {
					odnConf.save(fToSave);
				} catch (Exception e) {
					throw new Exception(
							"Error writing diagnosis supported by sign nanopub '"
									+ fToSave + "'. Additional info: "
									+ e.getMessage());
				}
			}
		}
		return odn;
	}

	/**
	 * Method to create the nanopub of a diagnosis confirmation finding
	 * 
	 * @param odn
	 *            Receives the diagnosis nanopub.
	 * @param sign
	 *            Receives the sign
	 * @param relationID
	 *            Receives the relation between this nanopub and diagnosis
	 *            nanopub
	 * @param relationFinding
	 *            Receives the relation finding (has_sign or
	 *            has_diagnostic_test)
	 * @return the object
	 */
	private ODDxDiagnosisNanopub createNanopubDiagnosisConfirmationFinding(
			ODDxDiagnosisNanopub odn, String sign, String relationID,
			String relationFinding) {
		int subs = new java.util.Random(System.currentTimeMillis()).nextInt(27);
		String miniHashCF = DigestUtils.md5Hex(odn.getNanopubID() + sign)
				.substring(subs, subs + 5);
		String internalIDFinding = odn.getInternalID() + ":support:"
				+ miniHashCF;
		String nanopubIDFinding = DigestUtils.md5Hex(internalIDFinding);
		System.out.println("\tinternalID: " + internalIDFinding);
		System.out.println("\tnanopubID: " + nanopubIDFinding);
		System.out.println("\tnameID: " + sign);
		System.out.println("\n");
		ODDxDiagnosisNanopub odnret = new ODDxDiagnosisNanopub(
				nanopubIDFinding, internalIDFinding, sign, NAMESPACENANOPUB);
		odnret.getAssertion().setSubject(relationID);
		odnret.getAssertion().setPredicate(relationFinding);
		odnret.getAssertion().setObject(
				DDxDOURIs.getInstance().SNOMED_URI + sign);
		odnret.getProvenance().addTriple(
				new Triple(DDxDOURIs.getInstance().SNOMED_URI + sign, DDxDOURIs
						.getInstance().TEMP_ONTOLOGY_CONFIRMED_BY_RELATION,
						DDxDOURIs.getInstance().TEMP_ONTOLOGY_URI
								+ this.getRandomMethod()));
		return odnret;
	}

	/**
	 * Method to create the nanopub of a diagnostic test (inside of a
	 * nanocollection)
	 * 
	 * @param odnc
	 *            Receives the upper nanocollection.
	 * @param cc
	 *            Receives the clinical case.
	 * @param dt
	 *            Receives the test.
	 * @return the object.
	 */
	private ODDxDiagnosisNanopub createDiagnosisTestNanopub(
			ODDxDiagnosisNanopubCollection odnc, ClinicalCase cc, String dt) {
		int subs = new java.util.Random(System.currentTimeMillis()).nextInt(27);
		String miniHashTest = DigestUtils.md5Hex(odnc.getNanopubID() + dt)
				.substring(subs, subs + 5);
		String internalIDTest = odnc.getInternalID() + ":cf:" + miniHashTest;
		String nanopubIDTest = DigestUtils.md5Hex(internalIDTest);
		System.out.println("\tinternalID: " + internalIDTest);
		System.out.println("\tnanopubID: " + nanopubIDTest);
		System.out.println("\tnameID: " + dt);
		System.out.println("\n");
		ODDxDiagnosisNanopub odn = new ODDxDiagnosisNanopub(nanopubIDTest,
				internalIDTest, dt, NAMESPACENANOPUB);
		odn.getProvenance().addAssertionTriple(
				new AssertionTriple(
						DDxDOURIs.getInstance().TEMP_ONTOLOGY_METHOD_RELATION,
						getRandomMethod()));
		odn.getProvenance().addAssertionTriple(
				new AssertionTriple(
						DDxDOURIs.getInstance().TEMP_ONTOLOGY_VALUE_RELATION,
						getRandomValues()));
		odn.getProvenance().setCreated(getRandomDate());
		odn.getProvenance().setCreator("Creator");
		odn.getProvenance().setSource("Patient data");
		odn.getAssertion().setSubject(
				DDxDOURIs.getInstance().DDXDO_PATIENT_URI
						+ cc.getPatient().getId());
		odn.getAssertion().setPredicate(
				DDxDOURIs.getInstance().DDXDO_HAS_TEST_RELATION);
		odn.getAssertion().setObject(DDxDOURIs.getInstance().SNOMED_URI + dt);
		return odn;
	}

	/**
	 * Method to create the nanopub of a finding (inside of a nanocollection)
	 * 
	 * @param odnc
	 *            Receives the upper nanocollection.
	 * @param cc
	 *            Receives the clinical case.
	 * @param dt
	 *            Receives the finding.
	 * @return the object.
	 */
	private ODDxDiagnosisNanopub createDiagnosisFindingNanopub(
			ODDxDiagnosisNanopubCollection odnc, ClinicalCase cc, String finding) {
		int subs = new java.util.Random(System.currentTimeMillis()).nextInt(27);
		String miniHashCF = DigestUtils.md5Hex(odnc.getNanopubID() + finding)
				.substring(subs, subs + 5);
		String internalIDFinding = odnc.getInternalID() + ":cf:" + miniHashCF;
		String nanopubIDFinding = DigestUtils.md5Hex(internalIDFinding);
		System.out.println("\tinternalID: " + internalIDFinding);
		System.out.println("\tnanopubID: " + nanopubIDFinding);
		System.out.println("\tnameID: " + finding);
		System.out.println("\n");
		ODDxDiagnosisNanopub odn = new ODDxDiagnosisNanopub(nanopubIDFinding,
				internalIDFinding, finding, NAMESPACENANOPUB);
		odn.getProvenance()
				.addAssertionTriple(
						new AssertionTriple(
								DDxDOURIs.getInstance().TEMP_ONTOLOGY_INTENSITY_RELATION,
								getRandomIntensity()));
		odn.getProvenance()
				.addAssertionTriple(
						new AssertionTriple(
								DDxDOURIs.getInstance().TEMP_ONTOLOGY_DURATION_RELATION,
								getRandomDuration()));
		odn.getAssertion().setSubject(
				DDxDOURIs.getInstance().DDXDO_PATIENT_URI
						+ cc.getPatient().getId());
		odn.getAssertion().setPredicate(
				DDxDOURIs.getInstance().DDXDO_MANIFEST_SIGN_RELATION);
		odn.getAssertion().setObject(
				DDxDOURIs.getInstance().SNOMED_URI + finding);
		return odn;
	}

	/**
	 * Method to create the diagnosis nanopub collection.
	 * 
	 * @param cc
	 *            Receives the clinical case.
	 * @return the object.
	 */
	private ODDxDiagnosisNanopubCollection createDiagnosisNanopubCollection(
			ClinicalCase cc) {
		System.out.println("Creating nanopub collection..");
		int subs = new java.util.Random(System.currentTimeMillis()).nextInt(27);
		String miniHash = DigestUtils.md5Hex(cc.getPatient().getId())
				.substring(subs, subs + 5);
		String internalIDCC = "ddxdo:inputData:patientID:"
				+ cc.getPatient().getId() + ":inputData:" + miniHash;
		String nanopubIDCC = DigestUtils.md5Hex(internalIDCC);
		String nameIDCC = cc.getId();
		System.out.println("\tinternalID: " + internalIDCC);
		System.out.println("\tnanopubID: " + nanopubIDCC);
		System.out.println("\tnameID: " + nameIDCC);
		System.out.println("\tPatient ID: " + cc.getPatient().getId());
		System.out.println("\n");
		ODDxDiagnosisNanopubCollection odnc = new ODDxDiagnosisNanopubCollection(
				nanopubIDCC, internalIDCC, nameIDCC, NAMESPACENANOPUB);
		odnc.getPublicationInfo().setCreated(getRandomDate());
		odnc.getPublicationInfo()
				.setCreator("Automatic diagnosis KB generator");
		odnc.getPublicationInfo().setCreatedBy(
				"Patient " + cc.getPatient().getId());
		return odnc;
	}

	/**
	 * Method to get a random method.
	 * 
	 * @return the value.
	 */
	private String getRandomMethod() {
		return METHODS[new java.util.Random(System.currentTimeMillis())
				.nextInt(METHODS.length - 1)];
	}

	/**
	 * Method to get a random value.
	 * 
	 * @return the value.
	 */
	private String getRandomValues() {
		return VALUES[new java.util.Random(System.currentTimeMillis())
				.nextInt(VALUES.length - 1)];
	}

	/**
	 * Method to get a random duration.
	 * 
	 * @return the value.
	 */
	private String getRandomDuration() {
		return DURATION[new java.util.Random(System.currentTimeMillis())
				.nextInt(DURATION.length - 1)];
	}

	/**
	 * Method to get a random intensity.
	 * 
	 * @return the value.
	 */
	private String getRandomIntensity() {
		return INTENSITY[new java.util.Random(System.currentTimeMillis())
				.nextInt(INTENSITY.length - 1)];
	}

	/**
	 * Method to get a random date.
	 * 
	 * @return Return the date.
	 */
	private Calendar getRandomDate() {
		return getRandomDate(new java.util.Random(System.currentTimeMillis())
				.nextInt(1000));
	}

	/**
	 * Method to get a random date.
	 * 
	 * @param i
	 *            Receives the seed.
	 * @return Return the date.
	 */
	private Calendar getRandomDate(int i) {
		Calendar c = Calendar.getInstance();
		int d = new java.util.Random(System.currentTimeMillis() * (i + 1))
				.nextInt((28 - 1) + 1) + 1;
		int m = new java.util.Random(System.currentTimeMillis() * (i + 2))
				.nextInt((12 - 1) + 1) + 1;
		int y = new java.util.Random(System.currentTimeMillis() * (i + 3))
				.nextInt((2013 - 1990) + 1) + 1990;
		int h = new java.util.Random(System.currentTimeMillis() * (i + 4))
				.nextInt((23 - 1) + 1) + 1;
		int mi = new java.util.Random(System.currentTimeMillis() * (i + 5))
				.nextInt((59 - 1) + 1) + 1;
		int s = new java.util.Random(System.currentTimeMillis() * (i + 6))
				.nextInt((59 - 1) + 1) + 1;
		c.set(y, m, d, h, mi, s);
		return c;
	}

	/**
	 * Method to load clinical cases.
	 * 
	 * @throws Exception
	 *             Can throw exception.
	 */
	private void loadClinicalCases() throws Exception {
		System.out.println("Loading clinical cases...");
		File folder = new File(CLINICAL_CASES_FOLDER);
		for (int i = 0; i < folder.listFiles().length; i++) {
			ClinicalCase cc = processClinicalCase(folder.listFiles()[i]);
			this.clinicalCases.add(cc);
			System.out.println("Clinical cases loaded!\n\n" + cc);
		}
		System.out.println("End of clinical cases load.");
	}

	/**
	 * Method to process a clinical case file.
	 * 
	 * @param file
	 *            Receives the file.
	 * @return the case.
	 * @throws Exception
	 *             Can throw exception.
	 */
	private ClinicalCase processClinicalCase(File file) throws Exception {
		Properties prop = new Properties();
		prop.load(new FileInputStream(file));
		ClinicalCase cc = new ClinicalCase(file.getName());
		int numDiagnosis = Integer.parseInt(prop.getProperty("DIAGNOSIS_MADE"));
		Patient pat = new Patient(getRandomPatientID());
		String inputSigns = prop.getProperty("INPUT_SIGNS");
		if (!StaticUtils.isEmpty(inputSigns)) {
			String iSigns[] = inputSigns.split("@");
			for (int i = 0; i < iSigns.length; i++) {
				pat.addSign(iSigns[i]);
			}
		}
		String inputDts = prop.getProperty("INPUT_DTS");
		if (!StaticUtils.isEmpty(inputDts)) {
			String iDts[] = inputDts.split("@");
			for (int i = 0; i < iDts.length; i++) {
				pat.addDiagnosticTest(iDts[i]);
			}
		}
		cc.setPatient(pat);
		for (int i = 1; i <= numDiagnosis; i++) {
			String out = prop.getProperty("OUTPUT_" + i);
			String output[] = out.split("@");
			String diagnosisMadeBy = prop.getProperty("SOURCE_" + i);
			for (int j = 0; j < output.length; j++) {
				String dis = output[j];
				Diagnosis diag = new Diagnosis(dis, diagnosisMadeBy);
				cc.addDiagnosis(diag);
			}
		}
		return cc;
	}

	/**
	 * Method to get a random patient ID.
	 * 
	 * @return the value.
	 */
	private String getRandomPatientID() {
		String rndString = Integer.toString(new java.util.Random(System
				.currentTimeMillis()).nextInt(10000));
		rndString += Integer.toString(new java.util.Random(System
				.currentTimeMillis() * rndString.length()).nextInt(10000));
		rndString += Integer.toString(new java.util.Random(System
				.currentTimeMillis() + rndString.length()).nextInt(10000));
		String hash = DigestUtils.md5Hex(rndString);
		int subs = new java.util.Random(System.currentTimeMillis()).nextInt(27);
		String hashCf = DigestUtils.md5Hex(hash).substring(subs, subs + 5);
		return hashCf;
	}

}
