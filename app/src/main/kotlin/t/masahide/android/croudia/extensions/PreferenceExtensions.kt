package t.masahide.android.croudia.extensions

/**
 * Created by Masahide on 2016/01/24.
 */

import android.content.SharedPreferences
import com.squareup.moshi.Moshi
import android.support.v4.app.Fragment as SupportFragment


fun SharedPreferences.applyToJson(key: String, value: Any) {
    val json = Moshi.Builder().build().adapter(value.javaClass).toJson(value);
    edit().putString(key, json).apply()
}

fun <T : Any> SharedPreferences.getFromJson(key: String, clazz: Class<T>): T {
    return Moshi.Builder().build().adapter(clazz).fromJson(getString(key, "{}"))
}