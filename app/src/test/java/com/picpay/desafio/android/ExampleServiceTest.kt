package com.picpay.desafio.android

import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import com.picpay.desafio.android.domain.model.User
import com.picpay.desafio.android.data.api.PicPayService
import junit.framework.Assert.assertEquals
import kotlinx.coroutines.runBlocking
import org.junit.Test
import retrofit2.Call
import retrofit2.Response

class ExampleServiceTest {

    private val api = mock<PicPayService>()

    private val service = ExampleService(api)

    @Test
    fun exampleTest() {
        runBlocking {
            // given
            val call = mock<Call<List<User>>>()
            val expectedUsers = emptyList<User>()

           // whenever(call.execute()).thenReturn(Response.success(expectedUsers))
            whenever(api.getUsers()).thenReturn(Response.success(expectedUsers))

            // when
            val users = service.example()

            // then
            assertEquals(users, expectedUsers)
        }
    }
}