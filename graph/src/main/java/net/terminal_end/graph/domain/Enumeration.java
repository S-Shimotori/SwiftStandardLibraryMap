package net.terminal_end.graph.domain;


import lombok.Builder;
import lombok.Getter;
import org.neo4j.ogm.annotation.GeneratedValue;
import org.neo4j.ogm.annotation.Id;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;

import java.util.Set;

@NodeEntity
@Builder
@Getter
public class Enumeration implements Entity, ConcreteType {

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
    @Relationship(type=Neo4jRelationshipType.CONFORMS_TO)
    Set<ConformsTo> conformsToSet;

}
