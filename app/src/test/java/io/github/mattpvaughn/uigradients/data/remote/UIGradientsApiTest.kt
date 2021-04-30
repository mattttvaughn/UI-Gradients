package io.github.mattpvaughn.uigradients.data.remote

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import io.github.mattpvaughn.uigradients.data.local.model.Gradient
import junit.framework.TestCase
import kotlinx.coroutines.runBlocking
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Before
import org.junit.Test
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.io.File
import java.net.HttpURLConnection

class UIGradientsApiTest : TestCase() {

    private val moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()

    private val mockWebServer = MockWebServer()

    private lateinit var gradientsApi: UIGradientsApi

    @Before
    override fun setUp() {
        mockWebServer.start()

        gradientsApi = Retrofit.Builder().addConverterFactory(MoshiConverterFactory.create(moshi))
            .baseUrl(mockWebServer.url("/")) // note the URL is different from production one
            .build().create(UIGradientsApi::class.java)
    }

    @After
    override fun tearDown() {
        mockWebServer.shutdown()
    }

    private val expectedNumGradients = 23
    private val expectedSuccessGradients = (0 until expectedNumGradients).map {
        Gradient(name = "Omolon", colors = listOf("#FFFFFA", "#00000A"))
    }

    @Test
    fun `test api against valid json`() = runBlocking {
        val successFile = File("src/test/resources/example_success_gradients.json")
        val response = MockResponse().setResponseCode(HttpURLConnection.HTTP_OK)
            .setBody(successFile.readText())
        mockWebServer.enqueue(response)

        val gradients = gradientsApi.fetchGradientsList()
        assertEquals(expectedNumGradients, gradients.size)
        assertEquals(
            expectedSuccessGradients, gradients
        )
    }

    @Test
    fun `test api against failed request, ensure error is thrown`() {
        val response = MockResponse().setResponseCode(HttpURLConnection.HTTP_NOT_FOUND)
        mockWebServer.enqueue(response)

        val failMessage = "Should have thrown an error against bad data"
        runBlocking {
            try {
                gradientsApi.fetchGradientsList()
                fail(failMessage)
            } catch (t: Throwable) {
                // try block will catch the fail, so repeat here
                if (t.message == failMessage) {
                    fail(failMessage)
                }
            }
        }
    }
}