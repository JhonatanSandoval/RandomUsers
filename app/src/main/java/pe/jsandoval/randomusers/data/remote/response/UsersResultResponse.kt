package pe.jsandoval.randomusers.data.remote.response

import pe.jsandoval.randomusers.data.local.room.entity.UserEntity

data class UsersResultResponse(
    val results: List<UserResponse> = arrayListOf()
)

data class UserResponse(
    val login: UserLoginResponse? = null,
    val name: UserMainInfoResponse? = null,
    val gender: String,
    val email: String,
    val picture: UserPictureInfoResponse? = null
)

data class UserLoginResponse(
    val uuid: String
)

data class UserPictureInfoResponse(
    val large: String,
    val medium: String,
    val thumbnail: String
)

data class UserMainInfoResponse(
    val first: String,
    val last: String
)

fun UserResponse.transformToLocal(): UserEntity =
    UserEntity(login?.uuid!!, name?.first, name?.last, gender, email, picture?.thumbnail, picture?.medium)

fun List<UserResponse>.transformToLocal(): List<UserEntity> = map { it.transformToLocal() }