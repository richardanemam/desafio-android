package com.picpay.desafio.android

import androidx.lifecycle.Lifecycle
import androidx.test.espresso.Espresso
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.core.app.launchActivity
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import com.picpay.desafio.android.ContatosActivityRobotConstants.CLIENT_ERROR_RESPONSE_CODE
import com.picpay.desafio.android.ContatosActivityRobotConstants.RESPONSE_BODY
import com.picpay.desafio.android.ContatosActivityRobotConstants.RV_POSITION
import com.picpay.desafio.android.ContatosActivityRobotConstants.SERVER_PORT
import com.picpay.desafio.android.ContatosActivityRobotConstants.SUCCESSFUL_RESPONSE_CODE
import com.picpay.desafio.android.presentation.activity.ContatosActivity
import okhttp3.mockwebserver.Dispatcher
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import okhttp3.mockwebserver.RecordedRequest

object ContatosActivityRobotConstants {
    const val CONTATOS_TITLE = "Contatos"
    const val NAME = "Eduardo Santos"
    const val IMG = "https://randomuser.me/api/portraits/men/9.jpg"
    const val USER_NAME = "@eduardo.santos"
    const val RESPONSE_BODY = "\"[{\\\"id\\\":1001,\\\"name\\\":\\\"Eduardo Santos\\\",\\\"img\\\":\\\"https://randomuser.me/api/portraits/men/9.jpg\\\",\\\"username\\\":\\\"@eduardo.santos\\\"}]\""
    const val SERVER_PORT = 8080
    const val SUCCESSFUL_RESPONSE_CODE = 200
    const val CLIENT_ERROR_RESPONSE_CODE = 404
    const val RV_POSITION = 0
}

internal fun mockSuccessfulResponse(): MockResponse {
    return MockResponse()
        .setResponseCode(SUCCESSFUL_RESPONSE_CODE)
        .setBody(RESPONSE_BODY)
}

internal fun mockResponseError(): MockResponse {
    return MockResponse().setResponseCode(CLIENT_ERROR_RESPONSE_CODE)
}

class ContatosActivityRobotPrepare(private val server: MockWebServer) {

    fun setDispatcher() {
        server.dispatcher = object : Dispatcher() {
            override fun dispatch(request: RecordedRequest): MockResponse {
                return when (request.path) {
                    "/users" -> mockSuccessfulResponse()
                    else -> mockResponseError()
                }
            }
        }
    }

    fun startServer() = server.start(SERVER_PORT)
    fun closeServer() = server.close()

    internal fun launchActivity() {
        launchActivity<ContatosActivity>().apply {
            moveToState(Lifecycle.State.RESUMED)
        }
    }
}

class ContatosActivityRobotValidate() {
    fun validateTitle(title: String) {
        Espresso.onView(ViewMatchers.withText(title))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
    }

    fun validateUserName(username: String) {
        RecyclerViewMatchers.checkRecyclerViewItem(R.id.recyclerView, RV_POSITION, withText(username))
    }

    fun validateName(name: String) {
        RecyclerViewMatchers.checkRecyclerViewItem(R.id.recyclerView, RV_POSITION, withText(name))
    }

    fun validateProfileImg() {
        RecyclerViewMatchers.checkRecyclerViewItem(R.id.recyclerView, RV_POSITION, withId(R.id.picture))
    }
}