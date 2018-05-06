package net.terminal_end.scraper.model


/**
 * @property title function's signature
 * @property url page's url
 * @property description function's description
 * @property pageKind function's role
 * @property isGeneric whether this function is generic or not
 * @property declaration function's declaration
 * @property parameters function's parameter list
 * @property returnValue return value's description
 */
data class FunctionPage(
    override val title: String,
    override val url: String,
    override val description: String,
    override val pageKind: PageKind.Function,
    val isGeneric: Boolean,
    val declaration: String,
    val parameters: List<Parameter>,
    val returnValue: String?): Page
