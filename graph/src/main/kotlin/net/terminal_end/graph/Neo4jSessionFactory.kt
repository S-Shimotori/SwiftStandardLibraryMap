package net.terminal_end.graph


import org.neo4j.ogm.config.Configuration
import org.neo4j.ogm.session.Session
import org.neo4j.ogm.session.SessionFactory
import java.util.Properties


/**
 * @property configuration Neo4j [Configuration]
 * @property sessionFactory factory to get [Session] including [Configuration] and entities
 * @property classLoader to get properties
 *
 * @property default to get [Session]
 *
 * @property PROP_NEO4J file path to properties for Neo4j
 * @property PROP_KEY_NEO4J_URI URI to access Neo4j
 * @property PROP_KEY_NEO4J_USERNAME username to login Neo4j
 * @property PROP_KEY_NEO4J_PASSWORD password to login Neo4j
 * @property PROP_KEY_NEO4J_DOMAIN where models are
 */
class Neo4jSessionFactory {

    private val sessionFactory: SessionFactory
    private val classLoader = this::class.java.classLoader

    companion object {
        val default = Neo4jSessionFactory()

        private const val PROP_NEO4J = "neo4j.properties"
        private const val PROP_KEY_NEO4J_URI = "neo4juri"
        private const val PROP_KEY_NEO4J_USERNAME = "neo4jusername"
        private const val PROP_KEY_NEO4J_PASSWORD = "neo4jpassword"
        private const val PROP_KEY_NEO4J_DOMAIN = "neo4jdomain"
    }

    private constructor() {
        val properties = Properties()
        properties.load(classLoader.getResourceAsStream((PROP_NEO4J)))
        val configuration = Configuration.Builder()
                                         .uri(properties.getProperty(PROP_KEY_NEO4J_URI))
                                         .credentials(
                                             properties.getProperty(PROP_KEY_NEO4J_USERNAME),
                                             properties.getProperty(PROP_KEY_NEO4J_PASSWORD))
                                         .build()

        sessionFactory = SessionFactory(configuration, properties.getProperty(PROP_KEY_NEO4J_DOMAIN))
    }

    constructor(sessionFactory: SessionFactory) {
        this.sessionFactory = sessionFactory
    }

    /**
     * @return opened Neo4j [Session]
     */
    fun getNeo4jSession(): Session {
        return sessionFactory.openSession()
    }

}
