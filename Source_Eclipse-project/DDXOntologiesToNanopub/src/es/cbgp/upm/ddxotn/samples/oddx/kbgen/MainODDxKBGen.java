package es.cbgp.upm.ddxotn.samples.oddx.kbgen;


public class MainODDxKBGen {

	public MainODDxKBGen() {
		ODDxKBDataExtractor lo = new ODDxKBDataExtractor();
		try {
			lo.execute();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public static void main(String[] args) {
		new MainODDxKBGen();
	}

}
