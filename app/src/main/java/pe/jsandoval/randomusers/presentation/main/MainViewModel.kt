package pe.jsandoval.randomusers.presentation.main

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch
import pe.jsandoval.randomusers.data.local.sp.AppPreferences
import pe.jsandoval.randomusers.domain.model.User
import pe.jsandoval.randomusers.domain.repository.UserRepository
import pe.jsandoval.randomusers.presentation.base.BaseViewModel
import pe.jsandoval.randomusers.presentation.base.livedata.SingleLiveEvent
import javax.inject.Inject

class MainViewModel
@Inject constructor(
    private val userRepository: UserRepository
) : BaseViewModel() {

    val users = MutableLiveData<List<User>>()
    val errorMessage = SingleLiveEvent<String>()

    fun fetch(refresh: Boolean = false) {
        AppPreferences.isFirstTime = false
        viewModelScope.launch(IO) {
            userRepository.get(refresh, {
                users.postValue(it?.value)
            }, {
                errorMessage.postValue(it)
            })
        }
    }

    fun likeUser(uuid: String, liked: Boolean) {
        viewModelScope.launch(IO) { userRepository.likeOrDislikeUser(uuid, liked) }
    }
}