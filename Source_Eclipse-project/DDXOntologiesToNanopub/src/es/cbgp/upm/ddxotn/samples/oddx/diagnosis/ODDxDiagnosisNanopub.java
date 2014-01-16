package es.cbgp.upm.ddxotn.samples.oddx.diagnosis;

import com.hp.hpl.jena.graph.Node;

import es.cbgp.upm.ddxotn.logic.StaticUtils;
import es.cbgp.upm.ddxotn.nanopub.Assertion;
import es.cbgp.upm.ddxotn.nanopub.Nanopub;
import es.cbgp.upm.ddxotn.nanopub.Provenance;
import es.cbgp.upm.ddxotn.nanopub.PublicationInfo;

/**
 * Class that represents the diagnosis nanopub.
 * 
 * @author Alejandro Rodríguez González (Centre of Biotechnology and Plant
 *         Genomics, UPM)
 * 
 */
public class ODDxDiagnosisNanopub extends Nanopub {

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
	public ODDxDiagnosisNanopub(String nanoID, String internalID,
			String nameID, String bnm) {
		super(nanoID, internalID, nameID, bnm);
	}

	/**
	 * Method to create the assertion quads.
	 */
	public boolean createAssertionQuads(Node node, Assertion asrt) {
		if ((!StaticUtils.isEmpty(asrt.getSubject()))
				&& (!StaticUtils.isEmpty(asrt.getPredicate()))
				&& (!StaticUtils.isEmpty(asrt.getObject()))) {
			quads.add(createQuad(getURIFromString(asrt.getSubject()),
					getURIFromString(asrt.getPredicate()),
					getURIFromString(asrt.getObject()), this.assertionNode));
			return true;
		}
		return false;
	}

	/**
	 * Method to create the provenance quads.
	 */
	public boolean createProvenanceQuads(Node node, Provenance prov) {
		boolean createdAtLeastOne = false;
		if (!StaticUtils.isEmpty(prov.getCoverageContent())) {
			quads.add(createQuad(node, this.purlCoverageNode,
					getTypedLiteralFromString(prov.getCoverageContent()),
					this.provenanceNode));
			createdAtLeastOne = true;
		}
		if (!StaticUtils.isEmpty(prov.getDescription())) {
			quads.add(createQuad(node, this.purlDescriptionNode,
					getTypedLiteralFromString(prov.getDescription()),
					this.provenanceNode));
			createdAtLeastOne = true;
		}
		if (!StaticUtils.isEmpty(prov.getIdentifier())) {
			quads.add(createQuad(node, this.purlIdentifierNode,
					getTypedLiteralFromString(prov.getIdentifier()),
					this.provenanceNode));
			createdAtLeastOne = true;
		}
		if (!StaticUtils.isEmpty(prov.getPublisher())) {
			quads.add(createQuad(node, this.purlPublisherNode,
					getTypedLiteralFromString(prov.getPublisher()),
					this.provenanceNode));
			createdAtLeastOne = true;
		}
		if (!StaticUtils.isEmpty(prov.getSource())) {
			quads.add(createQuad(node, this.purlSourceNode,
					getTypedLiteralFromString(prov.getSource()),
					this.provenanceNode));
			createdAtLeastOne = true;
		}
		if (!StaticUtils.isEmpty(prov.getTitle())) {
			quads.add(createQuad(node, this.purlTitleNode,
					getTypedLiteralFromString(prov.getTitle()),
					this.provenanceNode));
			createdAtLeastOne = true;
		}
		if (prov.getCreated() != null) {
			if (!StaticUtils.isEmpty(prov.getCreated().getTime().toString())) {
				quads.add(createQuad(node, this.purlCreatedNode,
						getTypedLiteralFromString(prov.getCreated()),
						this.provenanceNode));
				createdAtLeastOne = true;
			}
		}
		if (!StaticUtils.isEmpty(prov.getCreator())) {
			quads.add(createQuad(node, this.purlCreatorNode,
					getTypedLiteralFromString(prov.getCreator()),
					this.provenanceNode));
			createdAtLeastOne = true;
		}
		if (prov.getAssertionTriples().size() > 0) {
			createdAtLeastOne = true;
			for (int i = 0; i < prov.getAssertionTriples().size(); i++) {
				quads.add(createQuad(this.assertionNode, getURIFromString(prov
						.getAssertionTriples().get(i).getPredicate()),
						getTypedLiteralFromString(prov.getAssertionTriples()
								.get(i).getObject()), this.provenanceNode));
			}
		}

		if (prov.getTriples().size() > 0) {
			createdAtLeastOne = true;
			for (int i = 0; i < prov.getTriples().size(); i++) {
				quads.add(createQuad(getURIFromString(prov.getTriples().get(i)
						.getSubject()),
						getURIFromString(prov.getTriples().get(i)
								.getPredicate()), getURIFromString(prov
								.getTriples().get(i).getObject()),
						this.provenanceNode));
			}
		}
		return createdAtLeastOne;
	}

	/**
	 * Method to create publication info quads.
	 */
	public boolean createPublicationInfoQuads(Node node, PublicationInfo pi) {
		boolean createdAtLeastOne = false;
		if (!StaticUtils.isEmpty(pi.getCreator())) {
			quads.add(createQuad(node, this.purlCreatorNode,
					getTypedLiteralFromString(pi.getCreator()),
					this.publicationInfoNode));
			createdAtLeastOne = true;
		}
		if (pi.getCreated() != null) {
			if (!StaticUtils.isEmpty(pi.getCreated().getTime().toString())) {
				quads.add(createQuad(node, this.purlCreatedNode,
						getTypedLiteralFromString(pi.getCreated()),
						this.publicationInfoNode));
				createdAtLeastOne = true;
			}
		}
		if (!StaticUtils.isEmpty(pi.getCreatedBy())) {
			quads.add(createQuad(node, this.swanCreatedByNode,
					getTypedLiteralFromString(pi.getCreatedBy()),
					this.publicationInfoNode));
			createdAtLeastOne = true;
		}
		return createdAtLeastOne;
	}

}
