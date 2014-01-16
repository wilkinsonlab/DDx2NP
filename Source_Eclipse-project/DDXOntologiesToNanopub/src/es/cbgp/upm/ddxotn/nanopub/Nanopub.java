package es.cbgp.upm.ddxotn.nanopub;

import com.hp.hpl.jena.graph.Node;
import com.hp.hpl.jena.graph.NodeFactory;

/**
 * Class that represents a nanopub.
 * @author Alejandro Rodríguez González (Centre of Biotechnology and Plant Genomics, UPM)
 *
 */
public abstract class Nanopub extends NanopubBasis {

	public Provenance provenance;
	public PublicationInfo pubInfo;
	public Assertion assertion;
	private NanopubCollection parentGraph;

	/**
	 * Constructor.
	 * @param nanoID Nanopub ID.
	 * @param internalID Internal ID.
	 * @param nameID Name ID.
	 * @param bnm Base namespace.
	 */
	public Nanopub(String nanoID, String internalID, String nameID, String bnm) {
		super(nanoID, internalID, nameID, bnm);
		this.provenance = new Provenance();
		this.pubInfo = new PublicationInfo();
		this.assertion = new Assertion();
		this.uniqueCode = Long.toString(System.currentTimeMillis())
				+ new java.util.Random(System.currentTimeMillis()
						* (this.getNanopubID()).length()).nextInt(123456789);
	}
	/**
	 * Method to get the provenance.
	 * @return Returns the object.
	 */
	public Provenance getProvenance() {
		return provenance;
	}

	/**
	 * Method to set the provenance.
	 * @param provenance Receives the object.
	 */
	public void setProvenance(Provenance provenance) {
		this.provenance = provenance;
	}

	/**
	 * Method to get the publication info.
	 * @return Returns the object.
	 */
	public PublicationInfo getPublicationInfo() {
		return pubInfo;
	}

	/**
	 * Method to set the publication info.
	 * @param pi Receives the object.
	 */
	public void setPublicationInfo(PublicationInfo pi) {
		this.pubInfo = pi;
	}

	/**
	 * Method to get the assertion.
	 * @return Returns the object.
	 */
	public Assertion getAssertion() {
		return assertion;
	}

	/**
	 * Method to set the assertion.
	 * @param assertion Receives the object.
	 */
	public void setAssertion(Assertion assertion) {
		this.assertion = assertion;
	}

	/**
	 * Method to create the nodes of the nanopub (nanopub node, provenance node
	 * and publication info node)
	 */
	public void createNodes() {
		String nanopubS = baseNamespace + NANOPUBLICATION + this.getNanopubID();
		this.nanoPubNode = NodeFactory.createURI(nanopubS);
		String assertion = baseNamespace + ASSERTION + this.getNanopubID();
		this.assertionNode = NodeFactory.createURI(assertion);
		String provenance = baseNamespace + PROVENANCE + uniqueCode;
		this.provenanceNode = NodeFactory.createURI(provenance);
		String pubInfo = baseNamespace + PUBLICATION_INFO + uniqueCode;
		this.publicationInfoNode = NodeFactory.createURI(pubInfo);
	}

	public abstract boolean createProvenanceQuads(Node node, Provenance prov);

	public abstract boolean createPublicationInfoQuads(Node node,
			PublicationInfo publicationInfo);

	public abstract boolean createAssertionQuads(Node node, Assertion asrt);

	/**
	 * Method to establish who is the parent nanocollection (if aplicable) of a nanopub.
	 * @param nc Receives the nanopub collection.
	 */
	public void setParentGraph(NanopubCollection nc) {
		this.parentGraph = nc;
	}

	/**
	 * Method to get the parent nanopubcollection graph.
	 * @return Returns the object.
	 */
	public NanopubCollection getParentGraph() {
		return this.parentGraph;
	}

	public void createQuads() {
		
		quads.add(createQuad(this.nanoPubNode,
				this.rdfTypeNode, this.nanoPubSchemaNanoPublicationNode,
				this.nanoPubNode));
		boolean provQuads = createProvenanceQuads(this.nanoPubNode,
				this.provenance);
		boolean pubInfoQuads = createPublicationInfoQuads(this.nanoPubNode,
				this.pubInfo);
		boolean assertionQuads = createAssertionQuads(this.nanoPubNode, this.assertion);

		if (provQuads) {
			/*
			 * If we have provenance quads created we create the following
			 * quads: <nanopubNode>
			 * <http://www.nanopub.org/nschema#hasProvenance> <provenanceNode>
			 * <nanopubNode>
			 * and
			 * <provenanceNode>
			 * <http://www.w3.org/1999/02/22-rdf-syntax-ns#type>
			 * <http://www.nanopub.org/nschema#Provenance> <nanopubNode> .
			 */
			quads.add(createQuad(this.nanoPubNode,
					this.nanoPubSchemaHasProvenanceNode, this.provenanceNode,
					this.nanoPubNode));
			quads.add(createQuad(this.provenanceNode, this.rdfTypeNode,
					this.nanoPubSchemaProvenanceNode, this.nanoPubNode));

		}
		if (pubInfoQuads) {
			/*
			 * Same with publication info
			 */
			quads.add(createQuad(this.nanoPubNode,
					this.nanoPubSchemaHasPublicationInfoNode,
					this.publicationInfoNode, this.nanoPubNode));
			quads.add(createQuad(this.publicationInfoNode, this.rdfTypeNode,
					this.nanoPubSchemaPublicationInfoNode, this.nanoPubNode));
		}
		if (assertionQuads) {
			/*
			 * Same with assertions.
			 */
			quads.add(createQuad(this.nanoPubNode,
					this.nanoPubSchemaHasAssertionNode,
					this.assertionNode, this.nanoPubNode));
			quads.add(createQuad(this.assertionNode, this.rdfTypeNode,
					this.nanoPubSchemaAssertionNode, this.nanoPubNode));
		}
		/*
		 * If the nanopub is part of a nanopub collection..
		 * we establish the relation
		 */
		if (this.getParentGraph() != null) {
			quads.add(createQuad(this.nanoPubNode,
					this.rdfSubGraphOf,
					this.getParentGraph().nanoPubCollectionNode, this.nanoPubNode));
		}
	}
	
}
