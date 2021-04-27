package com.picpay.desafio.android.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.picpay.desafio.android.data.db.dao.PicpayUserDao

@Database(entities = [PicpayUserEntity::class], version = 1)
abstract class PicpayUserDatabase: RoomDatabase() {

    abstract fun picpayUserDao(): PicpayUserDao

}