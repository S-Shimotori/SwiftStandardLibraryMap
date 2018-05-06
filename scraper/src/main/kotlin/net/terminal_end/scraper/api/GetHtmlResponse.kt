package net.terminal_end.scraper.api

import org.jsoup.nodes.Document


/**
 * @property code HTTP status code
 */
sealed class GetHtmlResponse(val code: ResponseCode) {

    class Error(code: ResponseCode): GetHtmlResponse(code)

    /**
     * @property document HTML
     */
    class Ok(val document: Document): GetHtmlResponse(ResponseCode.SUCCESS)
}
