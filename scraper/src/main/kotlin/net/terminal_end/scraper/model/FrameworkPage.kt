package net.terminal_end.scraper.model


/**
 * @property title framework's name
 * @property url page's url
 * @property description framework's description
 * @property topicLinks topic links in framework
 * @property pageKind this page describes framework
 */
data class FrameworkPage(
    override val title: String,
    override val url: String,
    override val description: String,
    val topicLinks: List<Link>): Page {

    override val pageKind: PageKind
        get() = PageKind.Framework
}
