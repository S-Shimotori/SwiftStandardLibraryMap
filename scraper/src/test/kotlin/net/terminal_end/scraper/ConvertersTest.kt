package net.terminal_end.scraper

import net.terminal_end.scraper.model.Link
import net.terminal_end.scraper.model.PageKind
import net.terminal_end.scraper.model.Parameter
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat
import org.jsoup.Jsoup
import org.junit.Test
import java.io.File


class ConvertersTest {

    private val classLoader = this::class.java.classLoader
    private val baseUrl = "https://developer.apple.com/documentation/swift"

    @Test
    fun testTypeAliasTopic() {
        val url = "$baseUrl/codable"
        val entityLinks = listOf(
            Link("$baseUrl/decodable", "Decodable", null),
            Link("$baseUrl/encodable", "Encodable", null)
        )
        val html = File(classLoader.getResource("Codable.html").file).readText()
        val actualTypeAliasPage = AppleReference.getTypeAliasPage(Jsoup.parse(html), url)

        assertThat(actualTypeAliasPage.entityLinks, `is`(entityLinks))
    }

    @Test
    fun testToTopic() {
        val url = "$baseUrl/collections"
        val subTopicLinks = listOf(
            Link("$baseUrl/collections/sequence_and_collection_protocols", "Sequence and Collection Protocols", "Write generic code that works with any collection, or build your own collection types."),
            Link("$baseUrl/collections/supporting_types", "Supporting Types", "Use wrappers, indices, and iterators in operations like slicing, flattening, and reversing a collection."),
            Link("$baseUrl/collections/managed_buffers", "Managed Buffers", "Build your own buffer-backed collection types.")
        )
        val typeLinks = listOf(
            Link("$baseUrl/array", "struct Array","An ordered, random-access collection."),
            Link("$baseUrl/dictionary","struct Dictionary", "A collection whose elements are key-value pairs."),
            Link("$baseUrl/set", "struct Set", "An unordered collection of unique elements."),
            Link("$baseUrl/optionset", "protocol OptionSet", "A type that presents a mathematical set interface to a bit set."),
            Link("$baseUrl/countablerange", "struct CountableRange", "A half-open range that forms a collection of consecutive values."),
            Link("$baseUrl/countableclosedrange", "struct CountableClosedRange", "A closed range that forms a collection of consecutive values."),
            Link("$baseUrl/collectionofone", "struct CollectionOfOne", "A collection containing a single element of type Element."),
            Link("$baseUrl/emptycollection", "struct EmptyCollection", "A collection whose element type is Element but that is always empty."),
            Link("$baseUrl/dictionaryliteral", "struct DictionaryLiteral", "A lightweight collection of key-value pairs.")
        )
        val html = File(classLoader.getResource("Collections.html").file).readText()
        val actualTopicPage = AppleReference.getTopicPage(Jsoup.parse(html), url)

        assertThat(actualTopicPage.subTopicLinks, `is`(subTopicLinks))
        assertThat(actualTopicPage.typeLinks, `is`(typeLinks))
    }

    @Test
    fun testToFunction() {
        val url = "$baseUrl/dictionary/1539001-updatevalue"
        val declaration = "@discardableResult mutating func updateValue(_ value: Dictionary.Value, forKey key: Dictionary.Key) -> Dictionary.Value?"
        val parameters = listOf(
                Parameter("value", "The new value to add to the dictionary."),
                Parameter("key", "The key to associate with value. If key already exists in the dictionary, value replaces the existing associated value. If key isn’t already a key of the dictionary, the (key, value) pair is added.")
        )
        val returnValue = "The value that was replaced, or nil if a new key-value pair was added."

        val html = File(classLoader.getResource("updateValue.html").file).readText()
        val actualFunctionPage = AppleReference.getFunctionPage(Jsoup.parse(html), url)

        assertThat(actualFunctionPage.declaration, `is`(declaration))
        assertThat(actualFunctionPage.parameters, `is`(parameters))
        assertThat(actualFunctionPage.returnValue, `is`(returnValue))
    }

    @Test
    fun testToType() {
        val url = "$baseUrl/optional"
        val pageKind: PageKind.Type = PageKind.Type.Enumeration
        val isGeneric = true
        val html = File(classLoader.getResource("Optional.html").file).readText()
        val functionLinks = listOf(
            Link("$baseUrl/optional/1540045-init", "init(Wrapped)", "Creates an instance that stores the given value."),
            Link("$baseUrl/optional/1538243-init", "init(nilLiteral: ())", "Creates an instance initialized with nil."),
            Link("$baseUrl/optional/1539476-map", "func map<U>((Wrapped) -> U)", "Evaluates the given closure when this Optional instance is not nil, passing the unwrapped value as a parameter."),
            Link("$baseUrl/optional/1540500-flatmap", "func flatMap<U>((Wrapped) -> U?)", "Evaluates the given closure when this Optional instance is not nil, passing the unwrapped value as a parameter."),
            Link("$baseUrl/1539917", "func ?? <T>(T?, () -> T)", "Performs a nil-coalescing operation, returning the wrapped value of an Optional instance or a default value."),
            Link("$baseUrl/1541015", "func ?? <T>(T?, () -> T?)", "Performs a nil-coalescing operation, returning the wrapped value of an Optional instance or a default Optional value."),
            Link("$baseUrl/optional/2950146", "static func == (Wrapped?, Wrapped?)", "Returns a Boolean value indicating whether two optional instances are equal."),
            Link("$baseUrl/optional/2949565", "static func != (Wrapped?, Wrapped?)", "Returns a Boolean value indicating whether two optional instances are not equal."),
            Link("$baseUrl/optional/2949362", "static func != (Optional<Wrapped>, Optional<Wrapped>)", "Returns a Boolean value indicating whether two values are not equal."),
            Link("$baseUrl/optional/2910611-encode", "func encode(to: Encoder)", "Encodes this optional value into the given encoder."),
            Link("$baseUrl/optional/2910616-init", "init(from: Decoder)", "Creates a new instance by decoding from the given decoder.")
        )
        val conformsToLinks = listOf(
            Link("$baseUrl/customdebugstringconvertible", "CustomDebugStringConvertible", null),
            Link("$baseUrl/customreflectable", "CustomReflectable", null),
            Link("$baseUrl/decodable", "Decodable", null),
            Link("$baseUrl/encodable", "Encodable", null),
            Link("$baseUrl/equatable", "Equatable", null),
            Link("$baseUrl/expressiblebynilliteral", "ExpressibleByNilLiteral", null)
        )
        val actualTypePage = AppleReference.getTypePage(Jsoup.parse(html), url)

        assertThat(actualTypePage.pageKind, `is`(pageKind))
        assertThat(actualTypePage.isGeneric, `is`(isGeneric))
        assertThat(actualTypePage.functionLinks, `is`(functionLinks))
        // TODO: propertyLinks
        assertThat(actualTypePage.inheritFromLinks, `is`(listOf()))
        assertThat(actualTypePage.conformToLinks, `is`(conformsToLinks))
    }

    @Test
    fun testToFrameworkPage() {
        val title = "Swift Standard Library"
        val description = "Solve complex problems and write high-performance, readable code."
        val topicLinks = listOf(
            Link("$baseUrl/numbers_and_basic_values", "Numbers and Basic Values", "Model data with numbers, Boolean values, and other fundamental types."),
            Link("$baseUrl/strings_and_text", "Strings and Text", "Work with text using Unicode-safe strings."),
            Link("$baseUrl/collections", "Collections", "Store and organize data using arrays, dictionaries, sets, and other data structures."),
            Link("$baseUrl/basic_behaviors", "Basic Behaviors", "Use your custom types in operations that depend on testing for equality or order and as members of sets and dictionaries."),
            Link("$baseUrl/encoding_decoding_and_serialization", "Encoding, Decoding, and Serialization", "Serialize and deserialize instances of your types with implicit or customized encoding."),
            Link("$baseUrl/initialization_with_literals", "Initialization with Literals", "Allow values of your type to be expressed using different kinds of literals."),
            Link("$baseUrl/input_and_output", "Input and Output", "Print values to the console, read from and write to text streams, and use command line arguments."),
            Link("$baseUrl/debugging_and_reflection", "Debugging and Reflection", "Fortify your code with runtime checks, and examine your values' runtime representation."),
            Link("$baseUrl/key_path_expressions", "Key-Path Expressions", "Use key-path expressions to access properties dynamically."),
            Link("$baseUrl/manual_memory_management", "Manual Memory Management", "Allocate and manage memory manually."),
            Link("$baseUrl/type_casting_and_existential_types", "Type Casting and Existential Types", "Perform casts between types or represent values of any type."),
            Link("$baseUrl/c_interoperability", "C Interoperability", "Use imported C types or call C variadic functions."),
            Link("$baseUrl/operator_declarations", "Operator Declarations", "Work with prefix, postfix, and infix operators.")
        )

        val html = File(classLoader.getResource("SwiftStandardLibrary.html").file).readText()
        val actualFrameworkPage = AppleReference.getFrameworkPage(Jsoup.parse(html), baseUrl)

        assertThat(actualFrameworkPage.title, `is`(title))
        assertThat(actualFrameworkPage.url, `is`(baseUrl))
        assertThat(actualFrameworkPage.description, `is`(description))
        assertThat(actualFrameworkPage.topicLinks, `is`(topicLinks))
    }

    @Test
    fun testToPropertyPage() {
        val url = "$baseUrl/array/2943906-count"
        val fromProtocolLinks = listOf(
            Link("$baseUrl/collection", "Collection", null)
        )

        val html = File(classLoader.getResource("count.html").file).readText()
        val actualPropertyPage = AppleReference.getPropertyPage(Jsoup.parse(html), url)

        assertThat(actualPropertyPage.fromProtocolLinks, `is`(fromProtocolLinks))
    }
}
