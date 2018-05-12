package net.terminal_end.graph.domain;


import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.neo4j.ogm.annotation.*;

@RelationshipEntity(type=Neo4jRelationshipType.CONFORMS_TO)
@Builder
@Getter
@Setter
public class ConformsTo {

    /**
     * id for Neo4j
     */
    @Id
    @GeneratedValue
    private Long id;

    /**
     * which type conforms to
     */
    @StartNode
    private ConcreteType concreteType;

    /**
     * which type is adopted by
     */
    @EndNode
    private AbstractType abstractType;

}
