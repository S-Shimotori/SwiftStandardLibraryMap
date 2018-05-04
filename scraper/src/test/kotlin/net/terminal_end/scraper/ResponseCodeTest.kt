package net.terminal_end.scraper

import net.terminal_end.scraper.api.ResponseCode
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Rule
import org.junit.Test
import org.junit.rules.ExpectedException

class ResponseCodeTest {

    @Rule @JvmField
    val expectedException: ExpectedException = ExpectedException.none()

    @Test
    fun testTooSmall() {
        expectedException.expect(IllegalStateException::class.java)
        ResponseCode.from(99)
    }

    @Test
    fun testCreate() {
        assertThat(ResponseCode.from(100), `is`(ResponseCode.INFORMATIONAL_RESPONSE))
        assertThat(ResponseCode.from(199), `is`(ResponseCode.INFORMATIONAL_RESPONSE))
        assertThat(ResponseCode.from(200), `is`(ResponseCode.SUCCESS))
        assertThat(ResponseCode.from(299), `is`(ResponseCode.SUCCESS))
        assertThat(ResponseCode.from(300), `is`(ResponseCode.REDIRECTION))
        assertThat(ResponseCode.from(399), `is`(ResponseCode.REDIRECTION))
        assertThat(ResponseCode.from(400), `is`(ResponseCode.CLIENT_ERROR))
        assertThat(ResponseCode.from(499), `is`(ResponseCode.CLIENT_ERROR))
        assertThat(ResponseCode.from(500), `is`(ResponseCode.SERVER_ERROR))
        assertThat(ResponseCode.from(599), `is`(ResponseCode.SERVER_ERROR))
    }

    @Test
    fun testTooBig() {
        expectedException.expect(IllegalStateException::class.java)
        ResponseCode.from(600)
    }
}
