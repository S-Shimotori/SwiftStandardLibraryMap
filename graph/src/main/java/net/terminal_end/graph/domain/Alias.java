package net.terminal_end.graph.domain;


import lombok.Builder;
import lombok.Getter;
import org.neo4j.ogm.annotation.*;

@RelationshipEntity(type=Neo4jRelationshipType.ALIAS)
@Builder
@Getter
public class Alias {

    /**
     * id for Neo4j
     */
    @Id
    @GeneratedValue
    private Long id;

    /**
     * which type aliases
     */
    @StartNode
    private Entity entity;

    /**
     * which type is aliased
     */
    @EndNode
    private TypeAlias typeAlias;

}
