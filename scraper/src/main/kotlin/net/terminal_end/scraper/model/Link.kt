package net.terminal_end.scraper.model


/**
 * @property url link url
 * @property text linked text
 * @property description linked page's description
 */
data class Link(
    val url: String,
    val text: String,
    val description: String?)
