package net.terminal_end.graph.domain;


import lombok.Builder;
import lombok.Getter;
import org.neo4j.ogm.annotation.*;

import java.util.HashSet;
import java.util.Set;

@NodeEntity
@Builder
@Getter
public class Structure implements Entity, ConcreteType {

    /**
     * id for Neo4j
     */
    @Id
    @GeneratedValue
    private Long id;

    /**
     * type name
     */
    private String name;

    /**
     * relationship describing conforming to
     */
    @Builder.Default
    @Relationship(type=Neo4jRelationshipType.CONFORMS_TO)
    private Set<ConformsTo> conformsToSet = new HashSet<>();

}
