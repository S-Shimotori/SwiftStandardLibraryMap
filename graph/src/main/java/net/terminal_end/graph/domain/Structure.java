package net.terminal_end.graph.domain;


import lombok.Builder;
import lombok.Getter;
import org.neo4j.ogm.annotation.*;

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
    Long id;

    /**
     * type name
     */
    String name;

    /**
     * relationship describing conforming to
     */
    @Relationship(type="ConformsTo")
    Set<ConformsTo> conformsToSet;

}
