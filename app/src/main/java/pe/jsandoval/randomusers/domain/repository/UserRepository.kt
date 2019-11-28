package pe.jsandoval.randomusers.domain.repository

import androidx.lifecycle.LiveData
import pe.jsandoval.randomusers.domain.model.User

interface UserRepository {

    suspend fun get(refresh: Boolean = false, onSuccess: (LiveData<List<User>>?) -> Unit, onFailure: (message: String?) -> Unit)

    suspend fun get(uuId: String): LiveData<User>

    suspend fun likeOrDislikeUser(uuId: String, liked: Boolean)

}