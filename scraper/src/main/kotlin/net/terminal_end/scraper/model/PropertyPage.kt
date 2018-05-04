package net.terminal_end.scraper.model


/**
 * @property title property's name
 * @property url page's url
 * @property description property's description
 * @property declaration property's declaration
 * @property pageKind this page describes a property in instance
 */
class PropertyPage(
    override val title: String,
    override val url: String,
    override val description: String,
    val declaration: String,
    val fromProtocolLinks: List<Link>): Page {

    override val pageKind: PageKind
        get() = PageKind.InstanceProperty
}
