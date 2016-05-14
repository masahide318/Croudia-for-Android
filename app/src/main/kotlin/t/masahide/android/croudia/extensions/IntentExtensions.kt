package t.masahide.android.croudia.extensions

import android.content.Intent
import com.squareup.moshi.Moshi

/**
 * Created by masahide on 2016/01/17.
 */

fun Intent.putJson(key:String, obj:Any): Intent {
    val json =  Moshi.Builder().build().adapter(obj.javaClass).toJson(obj);
    putExtra(key,json)
    return this
}

fun <T> Intent.getJson(key:String, clazz: Class<T>): T {
    val json = getStringExtra(key)
    return Moshi.Builder().build().adapter(clazz).fromJson(json)
}