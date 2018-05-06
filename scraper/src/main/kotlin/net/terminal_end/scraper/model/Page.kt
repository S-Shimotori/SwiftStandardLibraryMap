package net.terminal_end.scraper.model


/**
 * @property title page title
 * @property url page URL
 * @property description topic's description
 * @property pageKind what this page documents
 */
interface Page {
    val title: String
    val url: String
    val description: String
    val pageKind: PageKind
}
