package t.masahide.android.croudia.extensions

import android.content.Intent
import android.os.Bundle
import com.squareup.moshi.Moshi

/**
 * Created by masahide on 2016/01/17.
 */

fun Bundle.putJson(key:String, obj:Any){
    val json =  Moshi.Builder().build().adapter(obj.javaClass).toJson(obj);
    putString(key,json)
}

fun <T> Bundle.getJson(key:String, clazz: Class<T>): T {
    val json = getString(key)
    return Moshi.Builder().build().adapter(clazz).fromJson(json)
}