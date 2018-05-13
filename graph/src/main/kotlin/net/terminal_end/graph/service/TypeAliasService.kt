package net.terminal_end.graph.service;

import net.terminal_end.graph.Neo4jSessionFactory
import net.terminal_end.graph.domain.TypeAlias
import org.neo4j.ogm.session.Session


class TypeAliasService(session: Session): GenericService<TypeAlias>(session) {

    constructor(): this(Neo4jSessionFactory.default.getNeo4jSession())

    override fun getEntityType(): Class<TypeAlias> {
        return TypeAlias::class.java
    }

}
