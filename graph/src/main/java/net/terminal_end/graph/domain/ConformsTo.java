package net.terminal_end.graph.domain;


import lombok.Builder;
import lombok.Getter;
import org.neo4j.ogm.annotation.*;

@RelationshipEntity(type=Neo4jRelationshipType.CONFORMS_TO)
@Builder
@Getter
class ConformsTo {

    /**
     * id for Neo4j
     */
    @Id
    @GeneratedValue
    Long id;

    /**
     * which type conforms to
     */
    @StartNode
    ConcreteType concreteType;

    /**
     * which type is adopted by
     */
    @EndNode
    AbstractType abstractType;

}
