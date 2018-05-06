package net.terminal_end.scraper.model


/**
 * @property title topic's title
 * @property description topic's description
 * @property url page's url
 * @property typeLinks links to type page in this topic
 * @property functionLinks links to function page in this topic
 * @property subTopicLinks links to sub topic page in this topic
 * @property pageKind this page describes topic
 */
data class TopicPage(
    override val title: String,
    override val description: String,
    override val url: String,
    val typeLinks: List<Link>,
    val functionLinks: List<Link>,
    val subTopicLinks: List<Link>): Page {

    override val pageKind: PageKind
        get() = PageKind.Topic
}
