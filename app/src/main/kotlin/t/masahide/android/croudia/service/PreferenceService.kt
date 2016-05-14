package t.masahide.android.croudia.service

import android.content.SharedPreferences
import android.preference.PreferenceManager
import t.masahide.android.croudia.MyApplication
import t.masahide.android.croudia.extensions.applyToJson
import t.masahide.android.croudia.extensions.getFromJson
import t.masahide.android.croudia.entitiy.User

/**
 * Created by Masahide on 2016/05/04.
 */

class PreferenceService(val preference: SharedPreferences = PreferenceManager.getDefaultSharedPreferences(MyApplication.getInstance())){
    fun applyUser(user: User){
        preference.applyToJson("user",user)
    }
    fun getUser():User{
        return preference.getFromJson("user", User::class.java)
    }
}