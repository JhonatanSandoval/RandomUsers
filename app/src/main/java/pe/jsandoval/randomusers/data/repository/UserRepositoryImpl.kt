package pe.jsandoval.randomusers.data.repository

import androidx.lifecycle.LiveData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch
import pe.jsandoval.randomusers.datasource.UserCacheDataSource
import pe.jsandoval.randomusers.datasource.UserRemoteDataSource
import pe.jsandoval.randomusers.domain.model.User
import pe.jsandoval.randomusers.domain.repository.UserRepository
import javax.inject.Inject

class UserRepositoryImpl
@Inject constructor(
    private val cacheDataSource: UserCacheDataSource,
    private val remoteDataSource: UserRemoteDataSource
) : UserRepository {

    override suspend fun get(refresh: Boolean, onSuccess: (LiveData<List<User>>?) -> Unit, onFailure: (message: String?) -> Unit) {
        when (refresh) {
            true -> {
                remoteDataSource.get({ liveDataList ->
                    CoroutineScope(IO).launch {
                        cacheDataSource.deleteAll()
                        liveDataList?.value?.map {
                            cacheDataSource.set(it)
                        }
                        onSuccess(liveDataList)
                    }
                }, {
                    onFailure(it)
                })
            }
            false ->  {
                val users = cacheDataSource.get()
                onSuccess(users)
            }
        }
    }

    override suspend fun get(uuId: String): LiveData<User> {
        return cacheDataSource.get(uuId)
    }

    override suspend fun likeOrDislikeUser(uuId: String, liked: Boolean) {
        cacheDataSource.likeOrDislikeUser(uuId, liked)
    }
}