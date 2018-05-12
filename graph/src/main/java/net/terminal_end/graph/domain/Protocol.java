package net.terminal_end.graph.domain;


import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.neo4j.ogm.annotation.GeneratedValue;
import org.neo4j.ogm.annotation.Id;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;

import java.util.HashSet;
import java.util.Set;

@NodeEntity
@Builder
@Getter
@Setter
public class Protocol implements Entity, AbstractType {

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
     * relationship describing adopted by
     */
    @Builder.Default
    @Relationship(type=Neo4jRelationshipType.CONFORMS_TO, direction=Relationship.INCOMING)
    private Set<ConformsTo> conformsToSet = new HashSet<>();

    /**
     * relationship describing inherits from
     */
    @Builder.Default
    @Relationship(type=Neo4jRelationshipType.INHERITS_FROM)
    private Set<InheritsFrom> inheritsSet = new HashSet<>();

    /**
     * relationship describing inherited
     */
    @Builder.Default
    @Relationship(type=Neo4jRelationshipType.INHERITS_FROM, direction=Relationship.INCOMING)
    private Set<InheritsFrom> inheritedSet = new HashSet<>();

    /**
     * relationship describing alias
     */
    @Builder.Default
    @Relationship(type=Neo4jRelationshipType.ALIAS, direction=Relationship.INCOMING)
    private Set<Alias> aliasSet = new HashSet<>();

}
