package pe.jsandoval.randomusers.presentation.main

import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import pe.jsandoval.randomusers.data.local.sp.AppPreferences
import pe.jsandoval.randomusers.data.remote.util.ApiResponse
import pe.jsandoval.randomusers.domain.model.User
import pe.jsandoval.randomusers.domain.usecase.UsersUseCase
import pe.jsandoval.randomusers.presentation.base.BaseViewModel
import pe.jsandoval.randomusers.presentation.base.livedata.SingleLiveEvent
import javax.inject.Inject

class MainViewModel
@Inject constructor(
    private val usersUseCase: UsersUseCase
) : BaseViewModel() {

    private var job: Job? = null

    val users = SingleLiveEvent<List<User>>()
    val errorMessage = SingleLiveEvent<String>()

    fun fetch(refresh: Boolean = false) {
        AppPreferences.isFirstTime = false
        job = viewModelScope.launch(IO) {
            when (val result = usersUseCase.invoke(refresh)) {
                is ApiResponse.Success -> users.postValue(result.items)
                is ApiResponse.Error -> errorMessage.postValue(result.errorMessage)
                is ApiResponse.Exception -> errorMessage.postValue(result.exception.message)
            }
        }
    }

    fun likeUser(uuid: String, liked: Boolean) {
        viewModelScope.launch(IO) { usersUseCase.likeOrDislikeUser(uuid, liked) }
    }

    @ExperimentalCoroutinesApi
    override fun onCleared() {
        super.onCleared()
        job?.cancel()
    }
}