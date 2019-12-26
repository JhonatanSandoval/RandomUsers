package pe.jsandoval.randomusers.data.remote.response

import com.google.gson.annotations.SerializedName
import pe.jsandoval.randomusers.data.local.room.entity.UserEntity

data class UsersResultResponse(
    @SerializedName("results") val results: List<UserResponse> = arrayListOf()
)

data class UserResponse(
    @SerializedName("login") val login: UserLoginResponse? = null,
    @SerializedName("name") val name: UserMainInfoResponse? = null,
    @SerializedName("gender") val gender: String,
    @SerializedName("email") val email: String,
    @SerializedName("picture") val picture: UserPictureInfoResponse? = null
)

data class UserLoginResponse(
    @SerializedName("uuid") val uuid: String
)

data class UserPictureInfoResponse(
    @SerializedName("large") val large: String,
    @SerializedName("medium") val medium: String,
    @SerializedName("thumbnail") val thumbnail: String
)

data class UserMainInfoResponse(
    @SerializedName("first") val first: String,
    @SerializedName("last") val last: String
)

fun UserResponse.transformToLocal(): UserEntity =
    UserEntity(login?.uuid!!, name?.first, name?.last, gender, email, picture?.thumbnail, picture?.medium)

fun List<UserResponse>.transformToLocal(): List<UserEntity> = map { it.transformToLocal() }