package net.terminal_end.scraper

import mu.KotlinLogging
import net.terminal_end.scraper.model.EnumerationCasePage
import net.terminal_end.scraper.model.FrameworkPage
import net.terminal_end.scraper.model.FunctionPage
import net.terminal_end.scraper.model.Link
import net.terminal_end.scraper.model.PageKind
import net.terminal_end.scraper.model.Parameter
import net.terminal_end.scraper.model.PropertyPage
import net.terminal_end.scraper.model.TopicPage
import net.terminal_end.scraper.model.TypeAliasPage
import net.terminal_end.scraper.model.TypePage
import org.jsoup.nodes.Document


object AppleReference {

    private val logger = KotlinLogging.logger {}

    /**
     * convert [String] to [PageKind]
     *
     * @param value value to convert
     * @return [PageKind] when succeed
     * @exception IllegalStateException when fail
     */
    private fun getPageKind(value: String): PageKind {
        return when (value) {
            "Framework" -> PageKind.Framework

            "Structure" -> PageKind.Type.Structure
            "Class" -> PageKind.Type.Class
            "Protocol" -> PageKind.Type.Protocol
            "Enumeration" -> PageKind.Type.Enumeration

            "Type Alias" -> PageKind.TypeAlias

            "Instance Property" -> PageKind.InstanceProperty

            "Initializer" -> PageKind.Function.Initializer
            "Instance Method" -> PageKind.Function.InstanceMethod
            "Function" -> PageKind.Function.Function
            "Operator" -> PageKind.Function.Operator

            else -> {
                throw IllegalStateException("\"$this\" is not page kind")
            }
        }
    }

    /**
     * whether the type is generic or not
     *
     * @param value [String] describing the type's kind
     * @return true if the type is generic
     */
    private fun isGeneric(value: String): Boolean {
        return value.startsWith("Generic")
    }

    /**
     * get title from page
     *
     * @param document page to get title
     * @return title represented as [String] when success
     * @exception IllegalStateException when failure
     */
    private fun getTitle(document: Document): String {
        return try {
            document.getElementsByClass("topic-title").first()
                    .getElementsByTag("h1").first()
                    .text()
        } catch (e: Exception) {
            throw IllegalStateException("this document has no valid title")
        }
    }

    /**
     * get description from page
     *
     * @param document page to get description
     * @return description represented as [String] when succeed
     * @exception IllegalStateException when fail
     */
    private fun getDescription(document: Document): String {
        return try {
            document.getElementsByClass("topic-description").first()
                    .text()
        } catch (e: Exception) {
            throw IllegalStateException("this document has no valid description")
        }
    }

    /**
     * which kind the page is
     *
     * @param document page to classify
     * @return [PageKind] when succeed
     * @exception IllegalStateException when fail
     */
    fun getPageKind(document: Document): PageKind {
        val string = try {
            document.getElementsByClass("eyebrow").first()
                    .text()
                    .replace("Generic", "")
                    .trim()
        } catch (e: Exception) {
            throw IllegalStateException("this document has no valid page kind")
        }
        return getPageKind(string)
    }

    /**
     * whether the type is generic or not
     *
     * @param document page
     * @return [Boolean] when succeed
     * @exception IllegalStateException when fail
     */
    private fun isGeneric(document: Document): Boolean {
        return try {
            val text = document.getElementsByClass("eyebrow").first()
                               .text()
            isGeneric(text)
        } catch (e: Exception) {
            throw IllegalStateException("this document has no valid page kind")
        }
    }

    /**
     * get declaration
     *
     * @param document page to get declaration
     * @return declaration represented as [String] when succeed
     * @exception IllegalStateException when fail
     */
    private fun getDeclaration(document: Document): String {
        return try {
            document.getElementsByClass("declaration").first()
                    .text()
        } catch (e: Exception) {
            throw IllegalStateException("this document has no valid declaration")
        }
    }

    /**
     * get parameters
     *
     * @param document page to get parameters
     * @return parameters represented as [Parameter]s in [List] when succeed
     * @exception IllegalStateException when failed
     */
    private fun getParameters(document: Document): List<Parameter> {
        try {
            val tags = document.getElementsByClass("params")
            if (tags.isEmpty()) {
                return listOf()
            }
            val dts = tags.first().getElementsByTag("dt")
            val dds = tags.first().getElementsByTag("dd")

            if (dts.size != dds.size) {
                throw IllegalStateException()
            }

            return dts.zip(dds).map {
                Parameter(it.first.text(), it.second.text())
            }
        } catch (e: Exception) {
            throw IllegalStateException("this document has no valid parameters")
        }
    }

    /**
     * get return value
     *
     * @param document page to get
     * @return return value represented as [String] or null if the function returns nothing
     * @exception IllegalStateException when fail
     */
    private fun getReturnValue(document: Document): String? {
        return try {
            val returnValueSection = document.getElementById("return-value")
            if (returnValueSection == null) {
                null
            } else {
                returnValueSection.getElementsByTag("p")!!.first().text()
            }
        } catch (e: Exception) {
            throw IllegalStateException("this document has no valid return value")
        }
    }

    /**
     * get links to function pages
     *
     * @param document page to get links
     * @return links represented as [Link]s in [List] when succeed
     * @exception IllegalStateException when fail
     */
    private fun getFunctionLinks(document: Document): List<Link> {
        return try {
            document.getElementById("topics")
                .getElementsByClass("task-topic-info")
                .filter {
                    val signature = it.getElementsByTag("code").text()
                    return@filter signature.startsWith("init")
                        || signature.startsWith("func")
                        || signature.startsWith("static func")
                }.map {
                    val url = it.getElementsByTag("a").first().attr("href")
                    val text = it.getElementsByTag("code").first().text()
                    val description = it.getElementsByTag("p").first().text()
                    return@map Link(url, text, description)
                }
        } catch (e: Exception) {
            e.printStackTrace()
            throw IllegalStateException("this document has no valid functions")
        }
    }

    /**
     * get links to property pages
     *
     * @param document page to get links
     * @return links represented as [Link]s in [List] when succeed
     * @exception IllegalStateException when fail
     */
    private fun getPropertyLinks(document: Document): List<Link> {
        // TODO
        logger.warn { "TODO: get function links" }
        return listOf()
    }

    /**
     * get links to type pages
     *
     * @param document page to get links
     * @return links represented as [Link]s in [List] when succeed
     * @exception IllegalStateException when fail
     */
    private fun getTypeLinks(document: Document): List<Link> {
        return try {
            document.getElementById("topics")
                .getElementsByClass("task-topic-info")
                .filter {
                    it.getElementsByClass("decorator").size == 1
                    && it.getElementsByClass("identifier").size == 1
                }.map {
                    val url = it.getElementsByTag("a").first().attr("href")
                    val text = it.getElementsByTag("code").first().text()
                    val description = it.getElementsByTag("p").first().text()
                    return@map Link(url, text, description)
                }
        } catch (e: Exception) {
            throw IllegalStateException("this document has no valid types")
        }
    }

    /**
     * get links to topic pages
     *
     * @param document page to get links
     * @return links represented as [Link]s in [List] when succeed
     * @exception IllegalStateException when fail
     */
    private fun getSubTopicLinks(document: Document): List<Link> {
        return try {
            document.getElementById("topics")
                    .getElementsByClass("task-topic-info")
                    .filter {
                        it.getElementsByTag("svg").isNotEmpty()
                    }.map {
                        val url = it.getElementsByTag("a").first().attr("href")
                        val text = it.getElementsByTag("span").first().text()
                        val description = it.getElementsByTag("p").first().text()
                        return@map Link(url, text, description)
                    }
        } catch (e: Exception) {
            throw IllegalStateException("this document has no valid sub topics")
        }
    }

    /**
     * get links to entities in declaration
     *
     * @param document page to get links
     * @return links represented as [Link]s in [List] when succeed
     * @exception IllegalStateException when fail
     */
    private fun getEntityLinks(document: Document): List<Link> {
        return try {
            document.getElementsByClass("declaration").first()
                    .getElementsByTag("a")
                    .map {
                        Link(it.attr("href"), it.text(), null)
                    }
        } catch (e: Exception) {
            throw IllegalStateException("this document has no valid declaration")
        }
    }

    /**
     * get links to type pages the type inherits from
     *
     * @param document page to get links
     * @return links represented as [Link]s in [List] when succeed
     * @exception IllegalStateException when fail
     */
    private fun getInheritsFromList(document: Document): List<Link> {
        return try {
            val inheritsFromSection = document.getElementById("inherits-from")
            if (inheritsFromSection != null) {
                inheritsFromSection.getElementsByClass("symbol-name").map {
                    Link(it.attr("href"), it.text(), null)
                }
            } else {
                listOf()
            }
        } catch (e: Exception) {
            throw IllegalStateException("this document has no valid inherits from")
        }
    }

    /**
     * get links to type pages the type conforms to
     *
     * @param document page to get links
     * @return links represented as [Link]s in [List] when succeed
     * @exception IllegalStateException when fail
     */
    private fun getConformsToList(document: Document): List<Link> {
        return try {
            val conformsToFromSection = document.getElementById("conforms-to")
            if (conformsToFromSection != null) {
                conformsToFromSection.getElementsByClass("symbol-name").map {
                    return@map Link(it.attr("href"), it.text(), null)
                }
            } else {
                listOf()
            }
        } catch (e: Exception) {
            throw IllegalStateException("this document has no valid conforms to")
        }
    }

    /**
     * get links to topic pages
     *
     * @param document page to get links
     * @return links represented as [Link]s in [List] when succeed
     * @exception IllegalStateException when fail
     */
    private fun getTopicLinks(document: Document): List<Link> {
        return try {
            document.getElementById("topics")
                    .getElementsByClass("task-topic-info").map {
                        val a = it.getElementsByTag("a").first()
                        val url = a.attr("href")
                        val text = a.getElementsByTag("span")
                                    .text()
                        val description = it.getElementsByTag("p").first()
                                            .text()
                        return@map Link(url, text, description)
                    }
        } catch (e: Exception) {
            throw IllegalStateException("this document has no valid topic links")
        }
    }

    /**
     * get links to property pages
     *
     * @param document page to get links
     * @return links represented as [Link]s in [List] when succeed
     * @exception IllegalStateException when fail
     */
    private fun getFromProtocolLinks(document: Document): List<Link> {
        return try {
            document.getElementById("from-protocol")
                    .getElementsByTag("a")
                    .map {
                        return@map Link(it.attr("href"), it.text(), null)
                    }
        } catch (e: Exception) {
            throw IllegalStateException("this document has no valid protocol links")
        }
    }

    /**
     * convert [Document] to [FunctionPage]
     *
     * @param document [Document] to convert
     * @param url document's url
     * @return [FunctionPage] when succeed
     * @exception IllegalStateException when fail
     */
    fun getFunctionPage(document: Document, url: String): FunctionPage {
        val pageKind = getPageKind(document)
        if (pageKind !is PageKind.Function) {
            throw IllegalStateException("this document is not about function reference")
        }

        val title = getTitle(document)
        val description = getDescription(document)

        val isGeneric = isGeneric(document)
        val declaration = getDeclaration(document)
        val parameters = getParameters(document)
        val returnValue = getReturnValue(document)

        return FunctionPage(title, url, description, pageKind, isGeneric, declaration, parameters, returnValue)
    }

    /**
     * convert [Document] to [TypePage]
     *
     * @param document [Document] to convert
     * @param url document's url
     * @return [TypePage] when succeed
     * @exception IllegalStateException when fail
     */
    fun getTypePage(document: Document, url: String): TypePage {
        val pageKind = getPageKind(document)
        if (pageKind !is PageKind.Type) {
            throw IllegalStateException("this document is not about type reference")
        }

        val title = getTitle(document)
        val description = getDescription(document)

        val isGeneric = isGeneric(document)
        val functionLinks = getFunctionLinks(document)
        val propertyLinks = getPropertyLinks(document)
        val inheritsLinks = getInheritsFromList(document)
        val conformsLinks = getConformsToList(document)

        return TypePage(title, url, description, pageKind, isGeneric, functionLinks, propertyLinks, inheritsLinks, conformsLinks)
    }

    /**
     * convert [Document] to [FrameworkPage]
     *
     * @param document [Document] to convert
     * @param url document's url
     * @return [FrameworkPage] when succeed
     * @exception IllegalStateException when fail
     */
    fun getFrameworkPage(document: Document, url: String): FrameworkPage {
        if (getPageKind(document) != PageKind.Framework) {
            throw IllegalStateException("this document is not top page")
        }

        val title = getTitle(document)
        val description = getDescription(document)
        val topicLinks = getTopicLinks(document)
        return FrameworkPage(title, url, description, topicLinks)
    }

    /**
     * convert [Document] to [PropertyPage]
     *
     * @param document [Document] to convert
     * @param url document's url
     * @return [PropertyPage] when succeed
     * @exception IllegalStateException when fail
     */
    fun getPropertyPage(document: Document, url: String): PropertyPage {
        val title = getTitle(document)
        val description = getDescription(document)
        val declaration = getDeclaration(document)
        val fromProtocolLinks = getFromProtocolLinks(document)
        return PropertyPage(title, url, description, declaration, fromProtocolLinks)
    }

    /**
     * convert [Document] to [TopicPage]
     *
     * @param document [Document] to convert
     * @param url document's url
     * @return [TopicPage] when succeed
     * @exception IllegalStateException when fail
     */
    fun getTopicPage(document: Document, url: String): TopicPage {
        val title = getTitle(document)
        val description = getDescription(document)
        val typeLinks = getTypeLinks(document)
        val functionLinks = getFunctionLinks(document)
        val subTopicLinks = getSubTopicLinks(document)
        return TopicPage(title, description, url, typeLinks, functionLinks, subTopicLinks)
    }

    /**
     * convert [Document] to [TypeAliasPage]
     *
     * @param document [Document] to convert
     * @param url document's url
     * @return [TypeAliasPage] when succeed
     * @exception IllegalStateException when fail
     */
    fun getTypeAliasPage(document: Document, url: String): TypeAliasPage {
        val title = getTitle(document)
        val description = getDescription(document)
        val entityLinks = getEntityLinks(document)
        return TypeAliasPage(title, description, url, entityLinks)
    }

    /**
     * convert [Document] to [EnumerationCasePage]
     *
     * @param document [Document] to convert
     * @param url document's url
     * @return [EnumerationCasePage] when succeed
     * @exception IllegalStateException when fail
     */
    fun getEnumerationCasePage(document: Document, url: String): EnumerationCasePage {
        TODO("later")
    }
}
