package net.terminal_end.graph.domain;


import lombok.Builder;
import lombok.Getter;
import org.neo4j.ogm.annotation.*;

@RelationshipEntity(type="ConformsTo")
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
    Type concreteType;

    /**
     * which type is adopted by
     */
    @EndNode
    Type abstractType;

}
