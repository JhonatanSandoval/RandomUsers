package pe.jsandoval.randomusers.data.local.room.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import pe.jsandoval.randomusers.domain.model.User

@Entity(tableName = "users")
data class UserEntity(

    @PrimaryKey(autoGenerate = false)
    val uuid: String = "",

    @ColumnInfo
    val firstName: String? = null,

    @ColumnInfo
    val lastName: String? = null,

    @ColumnInfo
    val gender: String? = null,

    @ColumnInfo
    val email: String? = null,

    @ColumnInfo
    val picThumbNail: String? = null,

    @ColumnInfo
    val picMedium: String? = null,

    @ColumnInfo
    val age: Int = 0,

    @ColumnInfo
    val liked: Boolean = false
)

fun UserEntity.transformToDomain(): User = User(uuid, firstName, lastName, gender, email, picThumbNail, picMedium, age, liked)

fun List<UserEntity>.transformToDomain(): List<User> = map { it.transformToDomain() }