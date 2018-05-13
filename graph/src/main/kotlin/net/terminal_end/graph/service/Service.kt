package net.terminal_end.graph.service


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
