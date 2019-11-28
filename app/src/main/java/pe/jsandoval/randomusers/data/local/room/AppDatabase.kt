package pe.jsandoval.randomusers.data.local.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import pe.jsandoval.randomusers.data.local.room.dao.UserDao
import pe.jsandoval.randomusers.data.local.room.entity.UserEntity

@Database(
    exportSchema = false,
    version = 1,
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
                    "RandomUsers_Room"
                ).build()
                INSTANCE = instance
                return instance
            }
        }
    }

}