package es.cbgp.upm.ddxotn.samples.oddx.diagnosis;

import com.hp.hpl.jena.graph.Node;

import es.cbgp.upm.ddxotn.logic.StaticUtils;
import es.cbgp.upm.ddxotn.nanopub.NanopubCollection;
import es.cbgp.upm.ddxotn.nanopub.PublicationInfo;

/**
 * Class to create the diagnosis nanopub collection
 * 
 * @author Alejandro Rodríguez González (Centre of Biotechnology and Plant
 *         Genomics, UPM)
 * 
 */
public class ODDxDiagnosisNanopubCollection extends NanopubCollection {

	/**
	 * Constructor.
	 * 
	 * @param nanoID
	 *            Nanopub ID.
	 * @param internalID
	 *            Internal ID.
	 * @param nameID
	 *            Name ID.
	 * @param bnm
	 *            Base namespace.
	 */
	public ODDxDiagnosisNanopubCollection(String nanoID, String internalID,
			String nameID, String bnm) {
		super(nanoID, internalID, nameID, bnm);
	}

	/**
	 * Method to create publication info quads.
	 */
	public boolean createPublicationInfoQuads(Node node,
			PublicationInfo publicationInfo) {
		boolean createdAtLeastOne = false;
		if (!StaticUtils.isEmpty(publicationInfo.getCreator())) {
			quads.add(createQuad(node, this.purlCreatorNode,
					getTypedLiteralFromString(publicationInfo.getCreator()),
					this.publicationInfoNode));
			createdAtLeastOne = true;
		}

		if (!StaticUtils.isEmpty(publicationInfo.getCreated().getTime()
				.toString())) {
			quads.add(createQuad(node, this.purlCreatedNode,
					getTypedLiteralFromString(publicationInfo.getCreated()),
					this.publicationInfoNode));
			createdAtLeastOne = true;
		}
		if (!StaticUtils.isEmpty(publicationInfo.getCreatedBy())) {
			quads.add(createQuad(node, this.swanCreatedByNode,
					getTypedLiteralFromString(publicationInfo.getCreatedBy()),
					this.publicationInfoNode));
			createdAtLeastOne = true;
		}
		return createdAtLeastOne;
	}

}
