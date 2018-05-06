package net.terminal_end.scraper

import com.squareup.okhttp.mockwebserver.MockResponse
import com.squareup.okhttp.mockwebserver.MockWebServer
import net.terminal_end.scraper.api.GetHtmlResponse
import net.terminal_end.scraper.api.Stores
import org.junit.Test
import java.io.File


class StoresTest {

    private val classLoader = this::class.java.classLoader

    @Test
    fun testGetHtml() {
        val mockWebServer = MockWebServer()
        val mockResponse = MockResponse()
            .setResponseCode(200)
            .setBody(File(classLoader.getResource("SwiftStandardLibrary.html").file).readText())
        mockWebServer.enqueue(mockResponse)
        mockWebServer.start()

        val url = mockWebServer.url("/").url()

        val response = Stores.html.get(url)

        val testObserver = response.test()
        testObserver.awaitTerminalEvent()
        testObserver.assertValueAt(0) {
            val document = AppleReference.getFrameworkPage((it as GetHtmlResponse.Ok).document, url.toString())
            return@assertValueAt document.title == "Swift Standard Library"
        }

        mockWebServer.shutdown()
    }

}