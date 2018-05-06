package net.terminal_end.scraper.api

import mu.KotlinLogging

enum class ResponseCode {

    INFORMATIONAL_RESPONSE,
    SUCCESS,
    REDIRECTION,
    CLIENT_ERROR,
    SERVER_ERROR;

    companion object {

        private val logger = KotlinLogging.logger {}

        /**
         * @param code HTTP status code
         */
        fun from(code: Int): ResponseCode {
            return when (code) {
                in 100 until 200 -> INFORMATIONAL_RESPONSE
                in 200 until 300 -> SUCCESS
                in 300 until 400 -> REDIRECTION
                in 400 until 500 -> CLIENT_ERROR
                in 500 until 600 -> SERVER_ERROR
                else -> {
                    logger.error { "ResponseCode received invalid code $code" }
                    throw IllegalStateException()
                }
            }
        }

    }
}
