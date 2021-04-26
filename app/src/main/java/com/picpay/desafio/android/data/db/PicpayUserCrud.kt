package com.picpay.desafio.android.data.db

import com.picpay.desafio.android.domain.model.User

interface PicpayUserCrud {
    suspend fun create(): Boolean
    suspend fun read(): List<User>
    fun update()
    fun delete()
}