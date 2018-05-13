package net.terminal_end.graph;


import net.terminal_end.graph.domain.Clazz;
import net.terminal_end.graph.service.ClazzService;
import org.neo4j.ogm.session.Session;

import java.util.Calendar;
import java.util.HashSet;

public class Example {

    public static void main(String[] args) {

        String testName = Calendar.getInstance().getTime().toString();
        Clazz clazz = Clazz.builder()
                           .name(testName)
                           .conformsToSet(new HashSet<>())
                           .build();

        new ClazzService().createOrUpdate(clazz);
        System.exit(0);
    }
}
