package net.terminal_end.graph.domain;


import lombok.Builder;
import lombok.Getter;
import org.neo4j.ogm.annotation.*;

@RelationshipEntity(type=Neo4jRelationshipType.INHERITS_FROM)
@Builder
@Getter
public class InheritsFrom {

    /**
     * id for Neo4j
     */
    @Id
    @GeneratedValue
    private Long id;

    /**
     * which type inherits from
     */
    @StartNode
    private AbstractType abstractTypeInherits;

    /**
     * which type is adopted by
     */
    @EndNode
    private AbstractType abstractTypeAdopted;

}
