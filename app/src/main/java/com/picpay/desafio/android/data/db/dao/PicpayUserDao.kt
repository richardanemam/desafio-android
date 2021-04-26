package com.picpay.desafio.android.data.db.dao

import androidx.room.*
import com.picpay.desafio.android.data.db.PicpayUserEntity

@Dao
interface PicpayUserDao {
    @Query("SELECT * FROM user")
    suspend fun getAll(): List<PicpayUserEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(vararg users: PicpayUserEntity)

    @Delete
    fun delete(user: PicpayUserEntity)
}