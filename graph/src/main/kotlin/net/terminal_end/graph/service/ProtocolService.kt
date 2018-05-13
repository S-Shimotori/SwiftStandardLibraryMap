package net.terminal_end.graph.service

import net.terminal_end.graph.Neo4jSessionFactory
import net.terminal_end.graph.domain.Protocol
import org.neo4j.ogm.session.Session


class ProtocolService(session: Session): GenericService<Protocol>(session) {

    constructor(): this(Neo4jSessionFactory.default.getNeo4jSession())

    override fun getEntityType(): Class<Protocol> {
        return Protocol::class.java
    }

}
