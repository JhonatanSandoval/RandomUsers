package pe.jsandoval.randomusers.data.repository

import pe.jsandoval.randomusers.data.local.room.dao.UserDao
import pe.jsandoval.randomusers.data.local.room.entity.transformToDomain
import pe.jsandoval.randomusers.data.remote.Api
import pe.jsandoval.randomusers.data.remote.response.transformToLocal
import pe.jsandoval.randomusers.data.remote.util.ApiResponse
import pe.jsandoval.randomusers.data.remote.util.safeApiCall
import pe.jsandoval.randomusers.domain.model.User
import pe.jsandoval.randomusers.domain.model.transformToLocal
import pe.jsandoval.randomusers.domain.repository.UserRepository
import javax.inject.Inject

class UserRepositoryImpl
@Inject constructor(
    private val api: Api,
    private val userDao: UserDao
) : UserRepository {

    override suspend fun get(refresh: Boolean): ApiResponse<List<User>> {
        return if (refresh) {
            safeApiCall(
                call = { getUserFromRemote() },
                errorMessage = "Network error happened !"
            )
        } else {
            ApiResponse.Success(userDao.get().map { it.transformToDomain() })
        }
    }

    private suspend fun getUserFromRemote(): ApiResponse<List<User>> {
        val result = api.getUsers()
        if (result.isSuccessful) {
            result.body()?.results.let {
                val users = it?.transformToLocal()
                users?.let { list ->
                    cacheUsers(list.transformToDomain())
                    return ApiResponse.Success(list.transformToDomain())
                }
            }
        }
        return ApiResponse.Error(result.code(), result.message())
    }

    private fun cacheUsers(users: List<User>) {
        userDao.deleteAll()
        users.forEach {
            userDao.insertUser(it.transformToLocal())
        }
    }

    override suspend fun get(uuId: String): User = userDao.getUser(uuId).transformToDomain()

    override suspend fun likeOrDislikeUser(uuId: String, liked: Boolean) {
        userDao.updateUserLikeOrDislike(uuId, liked)
    }
}