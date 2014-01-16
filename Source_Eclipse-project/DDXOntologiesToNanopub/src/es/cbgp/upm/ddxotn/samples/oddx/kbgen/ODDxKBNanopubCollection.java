package es.cbgp.upm.ddxotn.samples.oddx.kbgen;

import com.hp.hpl.jena.graph.Node;

import es.cbgp.upm.ddxotn.logic.StaticUtils;
import es.cbgp.upm.ddxotn.nanopub.NanopubCollection;
import es.cbgp.upm.ddxotn.nanopub.PublicationInfo;

public class ODDxKBNanopubCollection extends NanopubCollection {

	public ODDxKBNanopubCollection(String nanoID, String internalID, String nameID, String bnm) {
		super(nanoID, internalID, nameID, bnm);
	}


	public boolean createPublicationInfoQuads(Node node,
			PublicationInfo publicationInfo) {
		boolean createdAtLeastOne = false;
		if (!StaticUtils.isEmpty(publicationInfo.getCreator())) {
			quads.add(createQuad(node, this.purlCreatorNode,
					getTypedLiteralFromString(publicationInfo.getCreator()),
					this.publicationInfoNode));
			createdAtLeastOne = true;
		}

		if (!StaticUtils.isEmpty(publicationInfo.getCreated().getTime().toString())) {
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
