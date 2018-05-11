package net.terminal_end.graph;


import net.terminal_end.graph.domain.Type;
import org.neo4j.ogm.session.Session;

import java.util.Calendar;
import java.util.HashSet;

public class Example {

    public static void main(String[] args) {
        Session session = Neo4jSessionFactory.Companion.getInstance().getNeo4jSession();

        String testName = Calendar.getInstance().getTime().toString();
        session.save(Type.builder()
                         .name(testName)
                         .conformsToSet(new HashSet<>())
                         .build());

        System.exit(0);
    }
}
