package pe.jsandoval.randomusers.inject.modules

import dagger.Module
import dagger.Provides
import pe.jsandoval.randomusers.data.local.UserCacheDataSourceImpl
import pe.jsandoval.randomusers.data.local.room.dao.UserDao
import pe.jsandoval.randomusers.data.remote.Api
import pe.jsandoval.randomusers.data.remote.UserRemoteDataSourceImpl
import pe.jsandoval.randomusers.data.repository.UserRepositoryImpl
import pe.jsandoval.randomusers.datasource.UserCacheDataSource
import pe.jsandoval.randomusers.datasource.UserRemoteDataSource
import pe.jsandoval.randomusers.domain.repository.UserRepository
import javax.inject.Singleton

@Module
class RepositoryModule {

    @Provides
    fun provideCacheDataSource(userDao: UserDao): UserCacheDataSource = UserCacheDataSourceImpl(userDao)

    @Provides
    fun provideRemoteDatSource(api: Api): UserRemoteDataSource = UserRemoteDataSourceImpl(api)

    @Singleton
    @Provides
    fun provideUserRepository(cacheDataSource: UserCacheDataSource, remoteDataSource: UserRemoteDataSource): UserRepository {
        return UserRepositoryImpl(cacheDataSource, remoteDataSource)
    }

}