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
public class Protocol implements Entity, AbstractType {

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
     * relationship describing adopted by
     */
    @Relationship(type=Neo4jRelationshipType.CONFORMS_TO, direction=Relationship.INCOMING)
    Set<ConformsTo> conformsToSet;

    /**
     * relationship describing inherits from
     */
    @Relationship(type=Neo4jRelationshipType.INHERITS_FROM)
    Set<InheritsFrom> inheritsSet;

    /**
     * relationship describing inherited
     */
    @Relationship(type=Neo4jRelationshipType.INHERITS_FROM, direction=Relationship.INCOMING)
    Set<InheritsFrom> inheritedSet;

    /**
     * relationship describing alias
     */
    @Relationship(type=Neo4jRelationshipType.ALIAS, direction=Relationship.INCOMING)
    Set<Alias> aliasSet;

}
