package pe.jsandoval.randomusers.data.local.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import pe.jsandoval.randomusers.data.local.room.dao.UserDao
import pe.jsandoval.randomusers.data.local.room.entity.UserEntity
import pe.jsandoval.randomusers.util.AppConstants

@Database(
    exportSchema = false,
    version = AppConstants.ROOM_DB_VERSION,
    entities = [UserEntity::class]
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun userDao(): UserDao

    companion object {

        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }
            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext, AppDatabase::class.java,
                    AppConstants.ROOM_DB_NAME
                ).build()
                INSTANCE = instance
                return instance
            }
        }
    }

}