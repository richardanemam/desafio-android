package com.picpay.desafio.android

import com.picpay.desafio.android.domain.model.User
import com.picpay.desafio.android.data.api.PicPayService

class ExampleService(
    private val service: PicPayService
) {

    suspend fun example(): List<User> {
        val users = service.getUsers()
        return users.body() ?: emptyList()
    }
}