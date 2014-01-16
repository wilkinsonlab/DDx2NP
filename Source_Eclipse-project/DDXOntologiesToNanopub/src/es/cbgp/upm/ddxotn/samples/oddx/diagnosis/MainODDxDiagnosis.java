package es.cbgp.upm.ddxotn.samples.oddx.diagnosis;


public class MainODDxDiagnosis {

	public MainODDxDiagnosis() {
		ODDxDiagnosisDataExtractor lo = new ODDxDiagnosisDataExtractor();
		try {
			lo.execute();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		new MainODDxDiagnosis();

	}

}
