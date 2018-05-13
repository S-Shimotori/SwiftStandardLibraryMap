package net.terminal_end.graph.service

import net.terminal_end.graph.Neo4jSessionFactory
import net.terminal_end.graph.domain.Structure
import org.neo4j.ogm.session.Session


class StructureService(session: Session): GenericService<Structure>(session) {

    constructor(): this(Neo4jSessionFactory.default.getNeo4jSession())

    override fun getEntityType(): Class<Structure> {
        return Structure::class.java
    }

}
