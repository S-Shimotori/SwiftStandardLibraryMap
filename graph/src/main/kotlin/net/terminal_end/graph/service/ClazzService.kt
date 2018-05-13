package net.terminal_end.graph.service

import net.terminal_end.graph.Neo4jSessionFactory
import net.terminal_end.graph.domain.Clazz
import org.neo4j.ogm.session.Session


class ClazzService(session: Session): GenericService<Clazz>(session) {

    constructor(): this(Neo4jSessionFactory.default.getNeo4jSession())

    override fun getEntityType(): Class<Clazz> {
        return Clazz::class.java
    }

}