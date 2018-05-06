package net.terminal_end.scraper.model


/**
 * @property title enumeration case's name
 * @property url page's url
 * @property description enumeration case's description
 * @property pageKind this page describes a case in enumeration
 */
data class EnumerationCasePage(
    override val title: String,
    override val url: String,
    override val description: String
): Page {

    override val pageKind: PageKind
        get() = PageKind.EnumerationCase
}
