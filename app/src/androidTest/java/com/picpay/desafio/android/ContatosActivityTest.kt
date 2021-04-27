package com.picpay.desafio.android

import com.picpay.desafio.android.ContatosActivityRobotConstants.CONTATOS_TITLE
import com.picpay.desafio.android.ContatosActivityRobotConstants.NAME
import com.picpay.desafio.android.ContatosActivityRobotConstants.USER_NAME
import okhttp3.mockwebserver.MockWebServer
import org.junit.Test


class ContatosActivityTest {

    private val server = MockWebServer()

    fun prepare(func: ContatosActivityRobotPrepare.() -> Unit) = ContatosActivityRobotPrepare(server).apply(func)
    fun validate(func: ContatosActivityRobotValidate.() -> Unit) = ContatosActivityRobotValidate().apply(func)

    @Test
    fun whenLauchingActivity_ItShouldDisplayTitle() {
        prepare {
            launchActivity()
        }
        validate {
            validateTitle(CONTATOS_TITLE)
        }
    }

    // TODO("validate if list displays items returned by server")
    @Test
    fun whenLauchingActivity_ItShouldDisplayListItem() {
        prepare {
            setDispatcher()
            startServer()
            launchActivity()
            closeServer()
        }
        validate {
            validateUserName(USER_NAME)
            validateName(NAME)
            validateProfileImg()
        }
    }
}