package pe.jsandoval.randomusers.inject.modules

import dagger.Module
import dagger.Provides
import pe.jsandoval.randomusers.data.local.room.AppDatabase
import pe.jsandoval.randomusers.data.local.room.dao.UserDao
import pe.jsandoval.randomusers.data.remote.Api
import pe.jsandoval.randomusers.data.repository.UserRepositoryImpl
import pe.jsandoval.randomusers.domain.repository.UserRepository
import javax.inject.Singleton

@Module
class RepositoryModule {

    @Singleton
    @Provides
    fun provideUserRepository(api: Api, userDao: UserDao): UserRepository {
        return UserRepositoryImpl(api, userDao)
    }

}