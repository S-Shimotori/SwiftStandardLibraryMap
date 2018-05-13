package net.terminal_end.graph.service

import net.terminal_end.graph.Neo4jSessionFactory
import net.terminal_end.graph.domain.Enumeration
import org.neo4j.ogm.session.Session


class EnumerationService(session: Session): GenericService<Enumeration>(session) {

    constructor(): this(Neo4jSessionFactory.default.getNeo4jSession())

    override fun getEntityType(): Class<Enumeration> {
        return Enumeration::class.java
    }

}
