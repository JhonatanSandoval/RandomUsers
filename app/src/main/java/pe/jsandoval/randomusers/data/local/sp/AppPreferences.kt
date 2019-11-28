package pe.jsandoval.randomusers.data.local.sp

import android.content.Context
import android.content.SharedPreferences

object AppPreferences {

    private const val PREFERENCES_NAME = "RandomUsers_SP"
    private const val MODE = Context.MODE_PRIVATE
    private lateinit var preferences: SharedPreferences

    private val IS_FIRST_TIME = Pair("isFirstTime", true)

    fun init(context: Context) {
        preferences = context.getSharedPreferences(PREFERENCES_NAME, MODE)
    }

    fun reset() {
        isFirstTime = false
    }

    private inline fun SharedPreferences.edit(operation: (SharedPreferences.Editor) -> Unit) {
        val editor = edit()
        operation(editor)
        editor.apply()
    }

    var isFirstTime: Boolean
        get() = preferences.getBoolean(IS_FIRST_TIME.first, IS_FIRST_TIME.second)
        set(value) = preferences.edit { it.putBoolean(IS_FIRST_TIME.first, value) }

}