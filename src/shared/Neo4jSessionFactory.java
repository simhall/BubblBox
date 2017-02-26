package shared;

import org.neo4j.ogm.session.Session;
import org.neo4j.ogm.session.SessionFactory;

public class Neo4jSessionFactory {

	private final static SessionFactory sessionFactory = new SessionFactory("shared","shared.ConfigurationArtifacts");

	private static Neo4jSessionFactory factory = new Neo4jSessionFactory();

	public static Neo4jSessionFactory getInstance() {
		return factory;
	}

	public Session getNeo4jSession() {
		// "http://localhost:7474"
		return sessionFactory.openSession();
	}
}