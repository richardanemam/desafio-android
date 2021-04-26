package com.picpay.desafio.android.data.repository

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.picpay.desafio.android.data.api.PicPayService
import com.picpay.desafio.android.domain.model.User
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ContatosRepository {

    companion object {
        private const val URL = "http://careers.picpay.com/tests/mobdev/"
    }

    private val gson: Gson by lazy { GsonBuilder().create() }

    private val okHttp: OkHttpClient by lazy {
        OkHttpClient.Builder()
            .build()
    }

    private val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(URL)
            .client(okHttp)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
    }

    private val service: PicPayService by lazy {
        retrofit.create(PicPayService::class.java)
    }

    suspend fun getUsers(): Response<List<User>> {
        return withContext(Dispatchers.Default) {
            service.getUsers()
        }
    }
}