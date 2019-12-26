package pe.jsandoval.randomusers.domain.usecase

import pe.jsandoval.randomusers.data.remote.util.ApiResponse
import pe.jsandoval.randomusers.domain.model.User
import pe.jsandoval.randomusers.domain.repository.UserRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UsersUseCase
@Inject constructor(
    private val userRepository: UserRepository
) {
    suspend operator fun invoke(refresh: Boolean): ApiResponse<List<User>> =
        userRepository.get(refresh)

    suspend fun likeOrDislikeUser(uuiD: String, liked: Boolean) =
        userRepository.likeOrDislikeUser(uuiD, liked)
}