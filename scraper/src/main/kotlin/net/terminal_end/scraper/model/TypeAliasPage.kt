package net.terminal_end.scraper.model


/**
 * @property title type alias' name
 * @property description type alias' description
 * @property url page's url
 * @property entityLinks
 */
class TypeAliasPage(
    override val title: String,
    override val description: String,
    override val url: String,
    val entityLinks: List<Link>): Page {

    override val pageKind: PageKind
        get() = PageKind.TypeAlias
}
