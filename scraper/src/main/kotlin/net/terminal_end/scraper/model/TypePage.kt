package net.terminal_end.scraper.model


/**
 * @property title type's name
 * @property url page's url
 * @property description type's description
 * @property pageKind which kind this type belongs to
 * @property isGeneric whether this type is generic or not
 * @property functionLinks links to function page in this type
 * @property propertyLinks links to property page in this type
 */
data class TypePage(
    override val title: String,
    override val url: String,
    override val description: String,
    override val pageKind: PageKind.Type,
    val isGeneric: Boolean,
    val functionLinks: List<Link>,
    val propertyLinks: List<Link>,
    val inheritFromLinks: List<Link>,
    val conformToLinks: List<Link>): Page
    // TODO: enumeration case links
    // TODO: type links
