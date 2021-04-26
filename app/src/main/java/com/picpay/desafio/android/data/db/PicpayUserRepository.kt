package com.picpay.desafio.android.data.db

import com.picpay.desafio.android.domain.model.User

interface PicpayUserRepository {
    suspend fun set(): Boolean
    suspend fun get(): List<User>
    fun delete()
}