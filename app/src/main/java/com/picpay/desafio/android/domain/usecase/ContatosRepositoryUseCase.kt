package com.picpay.desafio.android.domain.usecase

import com.picpay.desafio.android.data.db.PicpayUserCrud
import com.picpay.desafio.android.data.db.dao.PicpayUserDao
import com.picpay.desafio.android.data.db.toPicpayUserEntity
import com.picpay.desafio.android.data.db.toUser
import com.picpay.desafio.android.data.repository.ContatosRepository
import com.picpay.desafio.android.domain.model.User

class ContatosRepositoryUseCase(
    private val picpayUserDao: PicpayUserDao,
    private val repository: ContatosRepository
) : PicpayUserCrud {

    override suspend fun create() {
        val response = repository.getUsers()
        cacheUserData(response.body())
    }

    override suspend fun read(): List<User> {
        return getCachedData()
    }

    override fun update() {
        TODO("Not yet implemented")
    }

    override fun delete() {
        TODO("Not yet implemented")
    }

    private suspend fun cacheUserData(users: List<User>?) {
        users?.forEach {
            picpayUserDao.insertAll(it.toPicpayUserEntity())
        }
    }

    private suspend fun getCachedData(): List<User> {
        val users = mutableListOf<User>()
        val userEntity = picpayUserDao.getAll()

        userEntity.forEach {
            users.add(it.toUser())
        }

        return users
    }
}