package pe.jsandoval.randomusers.inject

import android.app.Application
import dagger.Component
import pe.jsandoval.randomusers.App
import pe.jsandoval.randomusers.data.local.room.dao.UserDao
import pe.jsandoval.randomusers.inject.modules.AppModule
import pe.jsandoval.randomusers.inject.modules.NetworkModule
import pe.jsandoval.randomusers.inject.modules.RepositoryModule
import pe.jsandoval.randomusers.inject.modules.ViewModelModule
import pe.jsandoval.randomusers.presentation.main.MainActivity
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class, NetworkModule::class, RepositoryModule::class, ViewModelModule::class])
interface AppComponent {

    fun inject(app: App)
    fun inject(mainActivity: MainActivity)

    val application: Application
    val userDao: UserDao

}