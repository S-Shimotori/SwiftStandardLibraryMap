package net.terminal_end.scraper.api

import com.nytimes.android.external.store3.base.impl.Store
import com.nytimes.android.external.store3.base.impl.StoreBuilder
import io.reactivex.Single
import mu.KotlinLogging
import okhttp3.Call
import okhttp3.Callback
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import org.jsoup.Jsoup
import java.io.IOException
import java.net.URL


/**
 * @property html cached [GetHtmlResponse] with key represented as [URL]
 */
object Stores {

    private val logger = KotlinLogging.logger {}

    private val okHttpClient = OkHttpClient.Builder()
                                           .build()

    val html: Store<GetHtmlResponse, URL> =
        StoreBuilder.key<URL, GetHtmlResponse>().fetcher {
            val request = Request.Builder()
                                 .url(it)
                                 .build()
            return@fetcher Single.create {
                okHttpClient.newCall(request).enqueue(object : Callback {
                    override fun onResponse(call: Call, response: Response) {
                        val responseBody = response.body()
                        if (responseBody != null) {
                            it.onSuccess(GetHtmlResponse.Ok(Jsoup.parse(responseBody.string())))
                        } else {
                            logger.warn { "Received response code ${response.code()}" }
                            it.onSuccess(GetHtmlResponse.Error(ResponseCode.from(response.code())))
                        }
                    }

                    override fun onFailure(call: Call, e: IOException) {
                        logger.error(e) { "Raised ${e.message}" }
                        it.onError(e)
                    }
                })
            }
        }.open()
}
