package pe.jsandoval.randomusers.data.remote

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import pe.jsandoval.randomusers.data.local.room.entity.transformToDomain
import pe.jsandoval.randomusers.data.remote.response.transformToLocal
import pe.jsandoval.randomusers.datasource.UserRemoteDataSource
import pe.jsandoval.randomusers.domain.model.User

class UserRemoteDataSourceImpl
constructor(
    private val api: Api
) : UserRemoteDataSource {

    private val usersLiveData = MutableLiveData<List<User>>()

    override suspend fun get(onSuccess: (LiveData<List<User>>?) -> Unit, onFailure: (message: String?) -> Unit?) {
        try {
            val usersResponse = api.getUsers()
            if (usersResponse.isSuccessful) {
                usersResponse.body().let {
                    val users = it?.results?.transformToLocal()
                    users?.let { list ->
                        usersLiveData.postValue(list.transformToDomain())
                        onSuccess(usersLiveData)
                    }
                }
            } else {
                onFailure("network error 1")
            }
        } catch (e: Exception) {
            onFailure("network error 2")
        }
    }
}