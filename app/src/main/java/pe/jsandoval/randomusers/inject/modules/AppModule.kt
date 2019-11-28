package pe.jsandoval.randomusers.inject.modules

import android.app.Application
import dagger.Module
import dagger.Provides
import pe.jsandoval.randomusers.data.local.room.AppDatabase
import pe.jsandoval.randomusers.data.local.room.dao.UserDao
import javax.inject.Singleton

@Module
class AppModule(private val application: Application) {

    @Provides
    @Singleton
    fun provideApplication(): Application {
        return application
    }

    @Provides
    @Singleton
    fun provideUserDao(): UserDao = AppDatabase.getDatabase(application).userDao()

}