package com.picpay.desafio.android.data.db

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.picpay.desafio.android.domain.model.User

@Entity(tableName = "user")
data class PicpayUserEntity (
    @PrimaryKey val id: Int,
    val img: String,
    val name: String,
    val username: String
)

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

fun PicpayUserEntity.toUser(): User {
    return User(
        id = this.id,
        img = this.img,
        name = this.name,
        username = this.username
    )
}