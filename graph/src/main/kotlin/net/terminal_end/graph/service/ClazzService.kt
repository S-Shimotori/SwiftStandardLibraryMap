package net.terminal_end.graph.service

import net.terminal_end.graph.domain.Clazz


class ClazzService: GenericService<Clazz>() {

    override fun getEntityType(): Class<Clazz> {
        return Clazz::class.java
    }

}