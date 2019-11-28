package pe.jsandoval.randomusers.data.local

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.liveData
import pe.jsandoval.randomusers.data.local.room.dao.UserDao
import pe.jsandoval.randomusers.data.local.room.entity.transformToDomain
import pe.jsandoval.randomusers.datasource.UserCacheDataSource
import pe.jsandoval.randomusers.domain.model.User
import pe.jsandoval.randomusers.domain.model.transformToLocal

class UserCacheDataSourceImpl constructor(
    private val userDao: UserDao
) : UserCacheDataSource {

    private val usersLiveData = MutableLiveData<List<User>>()

    override suspend fun get(): LiveData<List<User>> {
        val users = userDao.get()
        users.let {
            usersLiveData.postValue(it.transformToDomain())
        }
        return usersLiveData
    }

    override suspend fun get(uuid: String): LiveData<User> {
        val user = userDao.getUser(uuid)
        return liveData { emit(user.value?.transformToDomain()!!) }
    }

    override suspend fun set(user: User) {
        userDao.insertUser(user.transformToLocal())
    }

    override suspend fun set(list: List<User>) {
        list.map { set(it) }
    }

    override suspend fun deleteAll() {
        userDao.deleteAll()
    }

    override suspend fun likeOrDislikeUser(uuId: String, liked: Boolean) {
        userDao.updateUserLikeOrDislike(uuId, liked)
    }
}