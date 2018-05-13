package net.terminal_end.graph

import net.terminal_end.graph.domain.Clazz
import net.terminal_end.graph.domain.Enumeration
import net.terminal_end.graph.domain.Protocol
import net.terminal_end.graph.domain.Structure
import net.terminal_end.graph.domain.TypeAlias
import net.terminal_end.graph.service.ClazzService
import net.terminal_end.graph.service.EnumerationService
import net.terminal_end.graph.service.ProtocolService
import net.terminal_end.graph.service.StructureService
import net.terminal_end.graph.service.TypeAliasService
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.neo4j.harness.junit.Neo4jRule
import org.neo4j.ogm.config.Configuration
import org.neo4j.ogm.cypher.ComparisonOperator
import org.neo4j.ogm.cypher.Filter
import org.neo4j.ogm.session.Session
import org.neo4j.ogm.session.SessionFactory


class ServiceTest {

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
    fun clazzServiceTest() {
        val clazzService = ClazzService(session)
        val clazzName = "clazzName"
        val clazz = Clazz.builder()
                         .name(clazzName)
                         .build()

        val createdClazz = clazzService.createOrUpdate(clazz)
        assertThat(createdClazz.name, `is`(clazzName))
        assertThat(clazzService.find(createdClazz.id)!!.name, `is`(clazzName))
        assertThat(clazzService.findAll().toList().size, `is`(1))

        val foundClazzes = clazzService.find(
            Filter("name", ComparisonOperator.EQUALS, clazzName)
        )
        assertThat(foundClazzes.toList().size, `is`(1))
        assertThat(foundClazzes.first().id, `is`(createdClazz.id))

        clazzService.delete(createdClazz.id)
        assertThat(clazzService.findAll().toList().size, `is`(0))
    }

    @Test
    fun enumerationServiceTest() {
        val enumerationService = EnumerationService(session)
        val enumerationName = "enumerationName"
        val enumeration = Enumeration.builder()
                                     .name(enumerationName)
                                     .build()

        val createdEnumeration = enumerationService.createOrUpdate(enumeration)
        assertThat(createdEnumeration.name, `is`(enumerationName))
        assertThat(enumerationService.find(createdEnumeration.id)!!.name, `is`(enumerationName))
        assertThat(enumerationService.findAll().toList().size, `is`(1))

        val foundEnumerations = enumerationService.find(
            Filter("name", ComparisonOperator.EQUALS, enumerationName)
        )
        assertThat(foundEnumerations.toList().size, `is`(1))
        assertThat(foundEnumerations.first().id, `is`(createdEnumeration.id))

        enumerationService.delete(createdEnumeration.id)
        assertThat(enumerationService.findAll().toList().size, `is`(0))
    }

    @Test
    fun protocolServiceTest() {
        val protocolService = ProtocolService(session)
        val protocolName = "protocolName"
        val protocol = Protocol.builder()
                               .name(protocolName)
                               .build()

        val createdProtocol = protocolService.createOrUpdate(protocol)
        assertThat(createdProtocol.name, `is`(protocolName))
        assertThat(protocolService.find(createdProtocol.id)!!.name, `is`(protocolName))
        assertThat(protocolService.findAll().toList().size, `is`(1))

        val foundProtocols = protocolService.find(
            Filter("name", ComparisonOperator.EQUALS, protocolName)
        )
        assertThat(foundProtocols.toList().size, `is`(1))
        assertThat(foundProtocols.first().id, `is`(createdProtocol.id))

        protocolService.delete(createdProtocol.id)
        assertThat(protocolService.findAll().toList().size, `is`(0))
    }

    @Test
    fun structureServiceTest() {
        val structureService = StructureService(session)
        val structureName = "structureName"
        val structure = Structure.builder()
                                 .name(structureName)
                                 .build()

        val createdStructure = structureService.createOrUpdate(structure)
        assertThat(createdStructure.name, `is`(structureName))
        assertThat(structureService.find(createdStructure.id)!!.name, `is`(structureName))
        assertThat(structureService.findAll().toList().size, `is`(1))

        val foundStructures = structureService.find(
            Filter("name", ComparisonOperator.EQUALS, structureName)
        )
        assertThat(foundStructures.toList().size, `is`(1))
        assertThat(foundStructures.first().id, `is`(createdStructure.id))

        structureService.delete(createdStructure.id)
        assertThat(structureService.findAll().toList().size, `is`(0))
    }

    @Test
    fun typeAliasServiceTest() {
        val typeAliasService = TypeAliasService(session)
        val typeAliasName = "typeAliasName"
        val typeAlias = TypeAlias.builder()
                                 .name(typeAliasName)
                                 .aliasSet(setOf())
                                 .build()

        val createdTypeAlias = typeAliasService.createOrUpdate(typeAlias)
        assertThat(createdTypeAlias.name, `is`(typeAliasName))
        assertThat(typeAliasService.find(createdTypeAlias.id)!!.name, `is`(typeAliasName))
        assertThat(typeAliasService.findAll().toList().size, `is`(1))

        val foundTypeAliases = typeAliasService.find(
            Filter("name", ComparisonOperator.EQUALS, typeAliasName)
        )
        assertThat(foundTypeAliases.toList().size, `is`(1))
        assertThat(foundTypeAliases.first().id, `is`(createdTypeAlias.id))

        typeAliasService.delete(createdTypeAlias.id)
        assertThat(typeAliasService.findAll().toList().size, `is`(0))
    }
}
