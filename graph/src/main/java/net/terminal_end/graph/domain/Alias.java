package net.terminal_end.graph.domain;


import lombok.Builder;
import lombok.Getter;
import org.neo4j.ogm.annotation.*;

@RelationshipEntity(type=Neo4jRelationshipType.ALIAS)
@Builder
@Getter
class Alias {

    /**
     * id for Neo4j
     */
    @Id
    @GeneratedValue
    Long id;

    /**
     * which type aliases
     */
    @StartNode
    Entity entity;

    /**
     * which type is aliased
     */
    @EndNode
    TypeAlias typeAlias;

}
