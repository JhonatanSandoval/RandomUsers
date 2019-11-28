package pe.jsandoval.randomusers.datasource

import androidx.lifecycle.LiveData
import pe.jsandoval.randomusers.domain.model.User

interface UserCacheDataSource {
    suspend fun get(): LiveData<List<User>>
    suspend fun get(uuid: String): LiveData<User>
    suspend fun set(user: User)
    suspend fun set(list: List<User>)
    suspend fun deleteAll()
    suspend fun likeOrDislikeUser(uuId: String, liked: Boolean)
}

interface UserRemoteDataSource {
    suspend fun get(onSuccess: (LiveData<List<User>>?) -> Unit, onFailure: (message: String?) -> Unit?)
}