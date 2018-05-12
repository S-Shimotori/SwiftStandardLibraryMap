package net.terminal_end.graph

import net.terminal_end.graph.domain.Alias
import net.terminal_end.graph.domain.Clazz
import net.terminal_end.graph.domain.ConformsTo
import net.terminal_end.graph.domain.Enumeration
import net.terminal_end.graph.domain.InheritsFrom
import net.terminal_end.graph.domain.Protocol
import net.terminal_end.graph.domain.Structure
import net.terminal_end.graph.domain.TypeAlias
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Rule
import org.neo4j.harness.junit.Neo4jRule
import org.junit.Before
import org.junit.Test
import org.neo4j.ogm.config.Configuration
import org.neo4j.ogm.session.Session
import org.neo4j.ogm.session.SessionFactory


class NodeTest {

    @Rule @JvmField
    val neo4jRule = Neo4jRule()

    private lateinit var session: Session

    @Before
    fun setUp() {
        val configuration = Configuration.Builder()
                                         .uri(neo4jRule.boltURI().toString())
                                         .build()

        val sessionFactory = SessionFactory(configuration, Clazz::class.java.`package`.name)
        session = sessionFactory.openSession()
        session.purgeDatabase()
    }

    @Test
    fun clazzConformsToProtocolTest() {
        val className = "className"
        val protocolName = "protocolName"

        val clazz = Clazz.builder()
                         .name(className)
                         .build()
        val protocol = Protocol.builder()
                               .name(protocolName)
                               .build()

        val conformsTo = ConformsTo.builder()
                                   .abstractType(protocol)
                                   .concreteType(clazz)
                                   .build()
        clazz.conformsToSet = hashSetOf(conformsTo)
        protocol.conformsToSet = hashSetOf(conformsTo)

        session.save(clazz)

        val allClazzes = session.loadAll(Clazz::class.java)
        assertThat(allClazzes.size, `is`(1))
        assertThat(allClazzes.first().name, `is`(className))

        val allProtocols = session.loadAll(Protocol::class.java)
        assertThat(allProtocols.size, `is`(1))
        assertThat(allProtocols.first().name, `is`(protocolName))

        assertThat(allClazzes.first().conformsToSet.first().abstractType.name, `is`(protocolName))
    }

    @Test
    fun enumerationConformsToProtocolTest() {
        val enumerationName = "enumerationName"
        val protocolName = "protocolName"

        val enumeration = Enumeration.builder()
                                     .name(enumerationName)
                                     .build()
        val protocol = Protocol.builder()
                               .name(protocolName)
                               .build()

        val conformsTo = ConformsTo.builder()
                                   .abstractType(protocol)
                                   .concreteType(enumeration)
                                   .build()
        enumeration.conformsToSet = hashSetOf(conformsTo)
        protocol.conformsToSet = hashSetOf(conformsTo)

        session.save(enumeration)

        val allEnumerations = session.loadAll(Enumeration::class.java)
        assertThat(allEnumerations.size, `is`(1))
        assertThat(allEnumerations.first().name, `is`(enumerationName))

        val allProtocols = session.loadAll(Protocol::class.java)
        assertThat(allProtocols.size, `is`(1))
        assertThat(allProtocols.first().name, `is`(protocolName))

        assertThat(allEnumerations.first().conformsToSet.first().abstractType.name, `is`(protocolName))
    }

    @Test
    fun structureConformsToProtocolTest() {
        val structureName = "structureName"
        val protocolName = "protocolName"

        val structure = Structure.builder()
                                 .name(structureName)
                                 .build()
        val protocol = Protocol.builder()
                               .name(protocolName)
                               .build()

        val conformsTo = ConformsTo.builder()
                                   .abstractType(protocol)
                                   .concreteType(structure)
                                   .build()
        structure.conformsToSet = hashSetOf(conformsTo)
        protocol.conformsToSet = hashSetOf(conformsTo)

        session.save(structure)

        val allStructures = session.loadAll(Structure::class.java)
        assertThat(allStructures.size, `is`(1))
        assertThat(allStructures.first().name, `is`(structureName))

        val allProtocols = session.loadAll(Protocol::class.java)
        assertThat(allProtocols.size, `is`(1))
        assertThat(allProtocols.first().name, `is`(protocolName))

        assertThat(allStructures.first().conformsToSet.first().abstractType.name, `is`(protocolName))
    }

    @Test
    fun protocolInheritsFromProtocolTest() {
        val protocolName = "protocolName"
        val anotherProtocolName = "anotherProtocolName"

        val protocol = Protocol.builder()
                               .name(protocolName)
                               .build()
        val anotherProtocol = Protocol.builder()
                                      .name(anotherProtocolName)
                                      .build()

        val inheritsFrom = InheritsFrom.builder()
                                       .abstractTypeInherits(protocol)
                                       .abstractTypeAdopted(anotherProtocol)
                                       .build()
        protocol.inheritsSet = hashSetOf(inheritsFrom)
        anotherProtocol.inheritedSet = hashSetOf(inheritsFrom)

        session.save(protocol)

        val allProtocols = session.loadAll(Protocol::class.java)
        assertThat(allProtocols.size, `is`(2))

        val allProtocolNames = allProtocols.map {
            it.name
        }.toSet()
        assertThat(allProtocolNames, `is`(setOf(protocolName, anotherProtocolName)))

        val protocolInherits = allProtocols.filter {
            it.name == protocolName
        }
        assertThat(protocolInherits.size, `is`(1))
        assertThat(protocolInherits.first().inheritsSet.size, `is`(1))
        assertThat(protocolInherits.first().inheritsSet.first().abstractTypeAdopted.name, `is`(anotherProtocolName))
    }

    @Test
    fun typeAliasTest() {
        val protocolName = "protocolName"
        val anotherProtocolName = "anotherProtocolName"
        val typeAliasName = "typeAliasName"

        val protocol = Protocol.builder()
                               .name(protocolName)
                               .build()
        val anotherProtocol = Protocol.builder()
                                      .name(anotherProtocolName)
                                      .build()
        val typeAlias = TypeAlias.builder()
                                 .name(typeAliasName)
                                 .build()

        val alias = Alias.builder()
                         .typeAlias(typeAlias)
                         .entity(protocol)
                         .build()
        val anotherAlias = Alias.builder()
                                .typeAlias(typeAlias)
                                .entity(anotherProtocol)
                                .build()
        protocol.aliasSet = hashSetOf(alias)
        anotherProtocol.aliasSet = hashSetOf(anotherAlias)
        typeAlias.aliasSet = hashSetOf(alias, anotherAlias)

        session.save(typeAlias)

        val allProtocols = session.loadAll(Protocol::class.java)
        assertThat(allProtocols.size, `is`(2))

        val allProtocolNames = allProtocols.map {
            it.name
        }.toSet()
        assertThat(allProtocolNames, `is`(setOf(protocolName, anotherProtocolName)))

        val allTypeAliases = session.loadAll(TypeAlias::class.java)
        assertThat(allTypeAliases.size, `is`(1))

        val aliasedProtocolNames = allTypeAliases.first().aliasSet.map {
            it.entity.name
        }.toSet()
        assertThat(aliasedProtocolNames, `is`(setOf(protocolName, anotherProtocolName)))
    }

}
