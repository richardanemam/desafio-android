package com.picpay.desafio.android.data.repository

import com.picpay.desafio.android.common.BaseTest
import com.picpay.desafio.android.data.api.PicPayService
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Test

class ContatosRepositoryTest : BaseTest() {

    private val service: PicPayService = mockk()
    private val repository: ContatosRepository = mockk()

    @Test
    fun `when making a resquest it should return a succcessful response`() {
        runBlocking {
            coEvery { service.getUsers() } returns getResponse()
            coEvery { repository.getUsers() } returns getResponse()

            val response = repository.getUsers()
            Assert.assertTrue(response.isSuccessful)
            Assert.assertEquals(getResponse().message(), response.message())
            Assert.assertEquals(getResponse().body(), response.body())
            Assert.assertEquals(getResponse().code(), response.code())
        }
    }
}