package pe.jsandoval.randomusers.domain.model

import pe.jsandoval.randomusers.data.local.room.entity.UserEntity

data class User(
    val uuid: String = "",
    val firstName: String?,
    val lastName: String?,
    val gender: String?,
    val email: String?,
    val picThumbNail: String?,
    val picMedium: String?,
    val age: Int,
    var liked: Boolean = false
) {
    fun getFullName(): String = "$firstName $lastName"
}

fun User.transformToLocal(): UserEntity =
    UserEntity(uuid, firstName, lastName, gender, email, picThumbNail, picMedium, age, liked)