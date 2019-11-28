package pe.jsandoval.randomusers

import android.app.Application
import com.facebook.stetho.Stetho
import pe.jsandoval.randomusers.data.local.sp.AppPreferences
import pe.jsandoval.randomusers.inject.Injector
import pe.jsandoval.randomusers.inject.modules.AppModule
import timber.log.Timber

class App : Application() {

    init {
        Injector.init(AppModule(this))
    }

    override fun onCreate() {
        super.onCreate()

        // init shared preferences
        AppPreferences.init(this)

        // initialize dagger
        Injector.get().inject(this)

        if (BuildConfig.DEBUG) {
            enableStrictMode()
            initTimber()
            Stetho.initializeWithDefaults(this)
        }
    }

    private fun initTimber() {
        Timber.plant(Timber.DebugTree())
    }

    private fun enableStrictMode() {
        android.os.StrictMode.setThreadPolicy(
            android.os.StrictMode.ThreadPolicy.Builder()
                //.detectDiskReads()
                .detectDiskWrites()
                .detectNetwork()   // or .detectAll() for all detectable problems
                .penaltyLog()
                .build()
        )

        android.os.StrictMode.setVmPolicy(
            android.os.StrictMode.VmPolicy.Builder()
                .detectLeakedSqlLiteObjects()
                .penaltyLog()
                .penaltyDeath()
                .build()
        )
    }
}