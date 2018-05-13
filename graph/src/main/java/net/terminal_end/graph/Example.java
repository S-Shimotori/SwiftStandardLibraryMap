package net.terminal_end.graph;


import net.terminal_end.graph.domain.Clazz;
import net.terminal_end.graph.domain.ConformsTo;
import net.terminal_end.graph.domain.Protocol;
import net.terminal_end.graph.service.ClazzService;

import java.util.Calendar;
import java.util.HashSet;

public class Example {

    public static void main(String[] args) {

        String testName = Calendar.getInstance().getTime().toString();
        Clazz clazz = Clazz.builder()
                           .name("class(" + testName + ")")
                           .build();
        Protocol protocol = Protocol.builder()
                                    .name("protocol(" + testName + ")")
                                    .build();
        ConformsTo conformsTo = ConformsTo.builder()
                                          .abstractType(protocol)
                                          .concreteType(clazz)
                                          .build();
        HashSet<ConformsTo> conformsToSet = new HashSet<>();
        conformsToSet.add(conformsTo);
        clazz.setConformsToSet(conformsToSet);
        protocol.setConformsToSet(conformsToSet);

        new ClazzService().createOrUpdate(clazz);
        System.exit(0);

    }
}
