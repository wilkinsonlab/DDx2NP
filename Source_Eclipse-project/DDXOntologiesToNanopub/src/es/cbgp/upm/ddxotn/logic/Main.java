package es.cbgp.upm.ddxotn.logic;


import es.cbgp.upm.ddxotn.samples.oddx.diagnosis.ODDxDiagnosisDataExtractor;
import es.cbgp.upm.ddxotn.samples.oddx.kbgen.ODDxKBDataExtractor;

public class Main {

	public Main(String args[]) {
		if (args.length == 1) {
			if (args[0].equalsIgnoreCase("-kb")) {
				ODDxKBDataExtractor lo = new ODDxKBDataExtractor();
				try {
					lo.execute();
				} catch (Exception e) {
					System.err.println("Error executing: " + e.getMessage());
					e.printStackTrace();
				}
			}
			if (args[0].equalsIgnoreCase("-ddx")) {
				ODDxDiagnosisDataExtractor lo = new ODDxDiagnosisDataExtractor();
				try {
					lo.execute();
				} catch (Exception e) {
					System.err.println("Error executing: " + e.getMessage());
					e.printStackTrace();
				}
			}
		}

		if (!args[0].equalsIgnoreCase("-kb")
				&& !args[0].equalsIgnoreCase("-ddx")) {
			errorMessage();
		}

	}


	private void errorMessage() {
		System.err.println("Error. Run program with the following arguments:");
		System.err.println("-kb to create knowledge base nanopubs.");
		System.err.println("-ddx to create diagnosis nanopubs.");
	}

	public static void main(String[] args) {
		new Main(args);

	}

}
