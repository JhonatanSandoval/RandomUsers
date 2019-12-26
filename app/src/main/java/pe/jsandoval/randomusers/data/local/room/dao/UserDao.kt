package pe.jsandoval.randomusers.data.local.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import pe.jsandoval.randomusers.data.local.room.entity.UserEntity

@Dao
interface UserDao {

    @Insert
    fun insertUser(userEntity: UserEntity)

    @Query("SELECT * FROM users WHERE uuid = :uuid")
    fun getUser(uuid: String): UserEntity

    @Query("SELECT * FROM users")
    fun get(): List<UserEntity>

    @Query("DELETE FROM users")
    fun deleteAll()

    @Query("UPDATE users SET liked = :liked WHERE uuid = :uuid")
    fun updateUserLikeOrDislike(uuid: String, liked: Boolean)

}