package pe.jsandoval.randomusers.domain.repository

import pe.jsandoval.randomusers.data.remote.util.ApiResponse
import pe.jsandoval.randomusers.domain.model.User

interface UserRepository {

    suspend fun get(refresh: Boolean = false) : ApiResponse<List<User>>

    suspend fun get(uuId: String): User

    suspend fun likeOrDislikeUser(uuId: String, liked: Boolean)

}