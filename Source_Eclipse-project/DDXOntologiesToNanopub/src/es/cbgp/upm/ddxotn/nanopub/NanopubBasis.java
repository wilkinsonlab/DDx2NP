package es.cbgp.upm.ddxotn.nanopub;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;

import org.apache.jena.riot.RDFDataMgr;
import org.apache.jena.riot.RDFFormat;

import com.hp.hpl.jena.graph.Node;
import com.hp.hpl.jena.graph.NodeFactory;
import com.hp.hpl.jena.ontology.OntModel;
import com.hp.hpl.jena.query.Dataset;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.sparql.core.DatasetGraph;
import com.hp.hpl.jena.sparql.core.Quad;
import com.hp.hpl.jena.tdb.TDBFactory;


/**
 * NanopubBasis is the class which contains all the nodes that are used in the nanopubs and
 * the basic methods that should be overriden by other classes.
 * @author Alejandro Rodríguez González (Centre of Biotechnology and Plant Genomics, UPM)
 *
 */
public abstract class NanopubBasis {

	/*
	 * Nanopub nodes
	 */

	public Node nanoPubSchemaCollectionNode;
	public Node nanoPubSchemaAssertionNode;
	public Node nanoPubSchemaHasAssertionNode;
	public Node nanoPubSchemaHasProvenanceNode;
	public Node nanoPubSchemaHasPublicationInfoNode;
	public Node nanoPubSchemaNanoPublicationNode;
	public Node nanoPubSchemaProvenanceNode;
	public Node nanoPubSchemaPublicationInfoNode;

	/*
	 * RDF nodes
	 */
	public Node rdfTypeNode;
	public Node rdfSubGraphOf;

	/*
	 * Purl nodes
	 */
	public Node purlCoverageNode;
	public Node purlDescriptionNode;
	public Node purlIdentifierNode;
	public Node purlPublisherNode;
	public Node purlSourceNode;
	public Node purlTitleNode;
	public Node purlCreatedNode;
	public Node purlCreatorNode;

	/*
	 * Swan nodes
	 */

	public Node swanCreatedByNode;

	public Node nanoPubCollectionNode;
	public Node provenanceNode;
	public Node publicationInfoNode;
	public Node nanoPubNode;
	public Node assertionNode;

	public String nanopubID;
	public String internalID;
	public String nameID;
	public String uniqueCode;

	public String baseNamespace;
	
	public ArrayList<Quad> quads;

	public final String ASSERTION = "/assertion/";
	public final String NANOPUBLICATION = "/nanopublication/";
	public final String PROVENANCE = "/provenance/";
	public final String PUBLICATION_INFO = "/publication_info/";
	public final String NANOPUB_COLLECTION = "/nanopub_collection/";


	private OntModel model;

	/**
	 * Constructor.
	 * @param nanoID Receives the nanopub/nanocollection ID (as URI)
	 * @param internalID Receives the internal ID (different than nanopubID)
	 * @param nameID Receives the nameID (some easy name to represent the nanopub/nanocollection)
	 * @param bnm Receives the base namespace.
	 */
	public NanopubBasis(String nanoID, String internalID, String nameID, String bnm) {
		this.nanopubID = nanoID;
		this.internalID = internalID;
		this.nameID = nameID;
		this.baseNamespace = bnm;
		/*
		 * We create all the necessary nodes.
		 */
		this.createSwanNodes();
		this.createRDFNodes();
		this.createPurlNodes();
		this.createNanoPubsNodes();
		/*
		 * We create a model to convert types and the quad list.
		 */
		this.model = ModelFactory.createOntologyModel();
		this.quads = new ArrayList<Quad>();
	}

	/**
	 * Method to get the unique code.
	 * @return the value.
	 */
	public String getUniqueCode() {
		return this.uniqueCode;
	}
	/**
	 * Method to get the name ID.
	 * @return A String.
	 */
	public String getNameID() {
		return this.nameID;
	}

	/**
	 * Method to set the name ID.
	 * @param n Receives a String.
	 */
	public void setNameID(String n) {
		this.nameID = n;
	}

	/**
	 * Method to get the nanopub/nanocollection ID.
	 * @return A String.
	 */
	public String getNanopubID() {
		return nanopubID;
	}
	/**
	 * Method to set the nanopub/nanocollection ID.
	 * @param n Receives a String.
	 */
	public void setNanopubID(String id) {
		this.nanopubID = id;
	}
	/**
	 * Method to get the internal ID.
	 * @return A String.
	 */
	public String getInternalID() {
		return internalID;
	}
	/**
	 * Method to set the internal ID.
	 * @param n Receives a String.
	 */
	public void setInternalID(String id) {
		this.internalID = id;
	}

	/**
	 * Method to create SWAN nodes.
	 */
	public void createSwanNodes() {
		this.swanCreatedByNode = NodeFactory
				.createURI("http://swan.mindinformatics.org/ontologies/1.2/pav/createdBy");
	}

	/**
	 * Method to create RDF nodes.
	 */
	public void createRDFNodes() {
		this.rdfTypeNode = NodeFactory
				.createURI("http://www.w3.org/1999/02/22-rdf-syntax-ns#type");
		this.rdfSubGraphOf = NodeFactory
				.createURI("http://www.w3.org/2004/03/trix/rdfg-1/subGraphOf");
	}

	/**
	 * Method to create nano pubs nodes.
	 */
	public void createNanoPubsNodes() {
		this.nanoPubSchemaAssertionNode = NodeFactory
				.createURI("http://www.nanopub.org/nschema#Assertion");
		this.nanoPubSchemaHasAssertionNode = NodeFactory
				.createURI("http://www.nanopub.org/nschema#hasAssertion");
		this.nanoPubSchemaHasProvenanceNode = NodeFactory
				.createURI("http://www.nanopub.org/nschema#hasProvenance");
		this.nanoPubSchemaHasPublicationInfoNode = NodeFactory
				.createURI("http://www.nanopub.org/nschema#hasPublicationInfo");
		this.nanoPubSchemaNanoPublicationNode = NodeFactory
				.createURI("http://www.nanopub.org/nschema#Nanopublication");
		this.nanoPubSchemaProvenanceNode = NodeFactory
				.createURI("http://www.nanopub.org/nschema#Provenance");
		this.nanoPubSchemaPublicationInfoNode = NodeFactory
				.createURI("http://www.nanopub.org/nschema#PublicationInfo");
		this.nanoPubSchemaCollectionNode = NodeFactory
				.createURI("http://www.nanopub.org/nschema#NanopublicationCollection");
	}

	/**
	 * Method to create purl nodes.
	 */
	public void createPurlNodes() {
		this.purlCoverageNode = NodeFactory
				.createURI("http://purl.org/dc/elements/1.1/coverage");
		this.purlDescriptionNode = NodeFactory
				.createURI("http://purl.org/dc/elements/1.1/description");
		this.purlIdentifierNode = NodeFactory
				.createURI("http://purl.org/dc/elements/1.1/identifier");
		this.purlPublisherNode = NodeFactory
				.createURI("http://purl.org/dc/elements/1.1/publisher");
		this.purlSourceNode = NodeFactory
				.createURI("http://purl.org/dc/elements/1.1/source");
		this.purlTitleNode = NodeFactory
				.createURI("http://purl.org/dc/elements/1.1/title");
		this.purlCreatedNode = NodeFactory
				.createURI("http://purl.org/dc/terms/created");
		this.purlCreatorNode = NodeFactory
				.createURI("http://purl.org/dc/terms/creator");
	}

	/**
	 * Method to create the dynamic nodes (nanopub/collection, assertion and provenance)
	 * and the associated quads.
	 */
	public void createEntireNanopubStructureAndData() {
		this.createNodes();
		this.createQuads();
	}
	/**
	 * Method to get all the quads. It calls createEntireNanopubStructureAndData to ensure the creation of data.
	 * @return Return the quads list.
	 */
	public ArrayList<Quad> getAllQuads() {
		this.createEntireNanopubStructureAndData();
		return this.quads;
	}

	/**
	 * Method to save the nanopub.
	 * @param f Receives the file.
	 * @throws Exception It can throw an exception.
	 */
	public void save(String f) throws Exception {
		this.quads = this.getAllQuads();
		if (quads == null) {
			throw new Exception(
					"Quad list is null. Do you call createNanoPub() first?");
		}
		if (quads.size() == 0) {
			throw new Exception("Quad list is empty.");
		}
		Dataset ds = TDBFactory.createDataset();
		DatasetGraph dsg = ds.asDatasetGraph();
		for (int i = 0; i < quads.size(); i++) {
			dsg.add(quads.get(i));
		}
		RDFDataMgr.write(new FileOutputStream(new File(f)), dsg,
				RDFFormat.NQUADS);
	}

/**
 * Method to create the quads.
 */
	public abstract void createQuads();

	/**
	 * Method to create the nodes.
	 */
	public abstract void createNodes();

	/**
	 * Method to create the provenance quads.
	 * @param node Receives the node that will be subject of the provenance triple.
	 * @param prov Receives the provenance data.
	 * @return Returns true if any provenance data was created.
	 */
	public abstract boolean createProvenanceQuads(Node node, Provenance prov);

	/**
	 * Method to create the publication info quads.
	 * @param node Receives the node that will be subject of the publication info triple.
	 * @param prov Receives the publication info data.
	 * @return Returns true if any publication info data was created.
	 */
	public abstract boolean createPublicationInfoQuads(Node node,
			PublicationInfo publicationInfo);
	
	/**
	 * Method to create the assertion quads.
	 * @param node Receives the node that will be subject of the assertion triple.
	 * @param prov Receives the assertion data.
	 * @return Returns true if any assertion data was created.
	 */
	public abstract boolean createAssertionQuads(Node node, Assertion asrt);

	/**
	 * Method to create the quad.
	 * @param c Receives the context.
	 * @param s Receives the subject.
	 * @param p Receives the predicate.
	 * @param o Receives the object.
	 * @return Returns the quad.
	 */
	public Quad createQuad(Node c, Node s, Node p, Node o) {
		return new Quad(o, c, s, p);
	}

	/**
	 * Method to obtain a Literal node from String.
	 * 
	 * @param s
	 *            Receives the String.
	 * @return Returns the node.
	 */
	public Node getLiteralFromString(String s) {
		return NodeFactory.createLiteral(s);
	}

	/**
	 * Method to obtain an URI node from String.
	 * 
	 * @param s
	 *            Receives the String.
	 * @return Returns the node.
	 */
	public Node getURIFromString(String s) {
		return NodeFactory.createURI(s);
	}

	/**
	 * Method to obtain a typed literal node from String.
	 * 
	 * @param s
	 *            Receives the String.
	 * @return Return the node.
	 */
	public Node getTypedLiteralFromString(Object s) {
		return model.createTypedLiteral(s).asNode();
	}

	/**
	 * Method to obtain a Literal with language from String.
	 * 
	 * @param s
	 *            Receives the string in format "string@language".
	 * @return Returns the literal node.
	 */
	public Node getLiteralFromStringWithLanguage(String s) throws Exception {
		String parts[] = s.split("@");
		if (parts.length == 2) {
			return NodeFactory.createLiteral(parts[0], parts[1], false);
		} else {
			throw new Exception("Error getting literal and language from: " + s);
		}
	}
}
