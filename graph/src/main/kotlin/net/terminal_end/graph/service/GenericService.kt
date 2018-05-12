package net.terminal_end.graph.service


import mu.KotlinLogging
import net.terminal_end.graph.Neo4jSessionFactory
import net.terminal_end.graph.domain.Entity

abstract class GenericService<T: Entity>: Service<T> {

    companion object {
        private const val DEPTH_LIST = 0
        private const val DEPTH_ENTITY = 1
    }

    private val session = Neo4jSessionFactory.instance.getNeo4jSession()

    private val logger = KotlinLogging.logger {}

    override fun find(id: Long): T? {
        return session.load(getEntityType(), id, DEPTH_ENTITY)
    }

    override fun findAll(): Iterable<T> {
        return session.loadAll(getEntityType(), DEPTH_LIST)
    }

    override fun createOrUpdate(`object`: T): T {
        session.save(`object`, DEPTH_ENTITY)
        val newObject = find(`object`.id)

        if (newObject != null) {
            return newObject
        } else {
            val errorMessage = "couldn't find object(id: ${`object`.id})"
            logger.error(NullPointerException()) { errorMessage }
            throw IllegalStateException(errorMessage)
        }
    }

    override fun delete(id: Long) {
        session.delete(session.load(getEntityType(), id))
    }

    abstract fun getEntityType(): Class<T>

}
