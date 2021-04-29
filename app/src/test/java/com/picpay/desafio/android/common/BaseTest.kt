package com.picpay.desafio.android.common

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.picpay.desafio.android.data.db.PicpayUserEntity
import com.picpay.desafio.android.domain.model.User
import retrofit2.Response

open class BaseTest {

    companion object {
        private const val USER_JSON = "[{\"id\":1001,\"name\":\"Eduardo Santos\",\"img\":\"https://randomuser.me/api/portraits/men/9.jpg\",\"username\":\"@eduardo.santos\"}]"
    }

    fun getResponse(): Response<List<User>> {
        return Response.success(getUsers())
    }

    fun getUsers(): List<User> {
        val type = object : TypeToken<List<User>>() {}.type
        return Gson().fromJson<List<User>>(USER_JSON, type)
    }

    fun getUserEntity(): List<PicpayUserEntity> {
        val list = mutableListOf<PicpayUserEntity>()
        getUsers().forEach {
            list.add(it.toPicpayUserEntity())
        }
        return list
    }

    fun User.toPicpayUserEntity(): PicpayUserEntity {
        return with(this) {
            PicpayUserEntity(
                id = this.id,
                img = this.img,
                name = this.name,
                username = this.username
            )
        }
    }
}