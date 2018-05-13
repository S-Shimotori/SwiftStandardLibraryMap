package net.terminal_end.graph.service

import org.neo4j.ogm.cypher.Filter


interface Service<T> {

    /**
     * search all `T` objects
     *
     * @return `T` type objects
     */
    fun findAll(): Iterable<T>

    /**
     * find a object with id
     *
     * @return a `T` type object or null
     */
    fun find(id: Long): T?

    /**
     * find objects with filters
     *
     * @return `T` type objects satisfying the filters
     */
    fun find(vararg filters: Filter): Iterable<T>

    /**
     * delete a object with id
     */
    fun delete(id: Long)

    /**
     * create or update `T` type object and return it
     *
     * @return created or updated object
     */
    fun createOrUpdate(`object`: T): T

}
