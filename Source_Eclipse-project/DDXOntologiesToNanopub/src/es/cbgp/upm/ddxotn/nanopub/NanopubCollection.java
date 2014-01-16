package es.cbgp.upm.ddxotn.nanopub;

import java.util.ArrayList;

import com.hp.hpl.jena.graph.Node;
import com.hp.hpl.jena.graph.NodeFactory;
import com.hp.hpl.jena.sparql.core.Quad;

/**
 * Class that represents a nanopub collection.
 * 
 * @author Alejandro Rodríguez González (Centre of Biotechnology and Plant
 *         Genomics, UPM)
 * 
 */
public abstract class NanopubCollection extends NanopubBasis {

	public ArrayList<Nanopub> nanopubs;
	public PublicationInfo publicationInfo;

	/**
	 * Constructor.
	 * 
	 * @param nanoID
	 *            Nanopub ID.
	 * @param internalID
	 *            Internal ID.
	 * @param nameID
	 *            Name ID.
	 */
	public NanopubCollection(String nanoID, String internalID, String nameID,
			String bnm) {
		super(nanoID, internalID, nameID, bnm);
		this.nanopubs = new ArrayList<Nanopub>();
		this.publicationInfo = new PublicationInfo();
		this.uniqueCode = Long.toString(System.currentTimeMillis())
				+ new java.util.Random(System.currentTimeMillis()
						* (this.getNanopubID()).length()).nextInt(123456789);
	}

	/**
	 * Method to add a nanopub to the collection.
	 * 
	 * @param n
	 *            Receives the nanopub.
	 */
	public void addNanopub(Nanopub n) {
		/*
		 * We set current collection as parent.
		 */
		n.setParentGraph(this);
		this.nanopubs.add(n);
	}

	/**
	 * Method to get the list of nanopubs.
	 * 
	 * @return The list.
	 */
	public ArrayList<Nanopub> getNanopubs() {
		return this.nanopubs;
	}

	/**
	 * Method to create the nodes.
	 */
	public void createNodes() {
		String nanoPublicationCollectionData = baseNamespace
				+ NANOPUB_COLLECTION + uniqueCode;
		String publicationInfoData = baseNamespace + PUBLICATION_INFO
				+ uniqueCode;
		this.nanoPubCollectionNode = NodeFactory
				.createURI(nanoPublicationCollectionData);
		this.publicationInfoNode = NodeFactory.createURI(publicationInfoData);
	}

	/**
	 * Method to create the quads of the nanopubcollection
	 */
	public void createQuads() {
		quads.add(createQuad(this.nanoPubCollectionNode, this.rdfTypeNode,
				this.nanoPubSchemaCollectionNode, this.nanoPubCollectionNode));
		this.createPublicationInfoQuads(this.nanoPubCollectionNode,
				publicationInfo);
	}

	/**
	 * Method to get the publication info object.
	 * 
	 * @return
	 */
	public PublicationInfo getPublicationInfo() {
		return publicationInfo;
	}

	/**
	 * Override of save method.
	 * 
	 * It joins together all the quads of the nanopubs of the collection and
	 * save them.
	 */
	public void save(String f) throws Exception {
		createEntireNanopubStructureAndData();
		for (int i = 0; i < nanopubs.size(); i++) {
			ArrayList<Quad> nanopubQuads = nanopubs.get(i).getAllQuads();
			this.quads.addAll(nanopubQuads);
		}
		super.save(f);
	}

	public abstract boolean createPublicationInfoQuads(Node node,
			PublicationInfo publicationInfo);

	// NO PROVENANCE OR ASSERTIONS IN A NANOCOLLECTION
	
	public boolean createProvenanceQuads(Node node, Provenance prov) {
		return false;
	}

	public boolean createAssertionQuads(Node node, Assertion asrt) {
		return false;
	}

}
