package net.terminal_end.gatherer

import net.terminal_end.graph.domain.Clazz
import net.terminal_end.graph.service.ClazzService
import java.util.Calendar

fun main(args: Array<String>) {
    println("example")

    ClazzService().createOrUpdate(Clazz.builder()
                                       .name(Calendar.getInstance().toString())
                                       .build())
}
