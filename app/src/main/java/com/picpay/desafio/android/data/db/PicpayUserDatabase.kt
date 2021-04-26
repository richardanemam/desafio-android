package com.picpay.desafio.android.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.picpay.desafio.android.data.db.dao.PicpayUserDao

@Database(entities = [PicpayUserEntity::class], version = 1)
abstract class PicpayUserDatabase: RoomDatabase() {

    abstract fun picpayUserDao(): PicpayUserDao

    companion object {
        @Volatile
        private var INSTANCE: PicpayUserDatabase? = null

        fun getDatabase(context: Context): PicpayUserDatabase {
            val tempInstance = INSTANCE
            if(tempInstance != null) return tempInstance

            synchronized(this) {
                val dbInstance = Room.databaseBuilder(
                    context.applicationContext,
                    PicpayUserDatabase::class.java,
                    "picpay_user_database"
                ).build()
                INSTANCE = dbInstance
                return dbInstance
            }
        }
    }
}