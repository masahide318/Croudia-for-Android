package t.masahide.android.croudia.service

import android.content.SharedPreferences
import android.preference.PreferenceManager
import t.masahide.android.croudia.extensions.applyToJson
import t.masahide.android.croudia.extensions.getFromJson

import java.util.Date

import t.masahide.android.croudia.MyApplication
import t.masahide.android.croudia.entitiy.AccessToken

class AccessTokenService {
    private val preferences: SharedPreferences

    init {
        preferences = PreferenceManager.getDefaultSharedPreferences(MyApplication.getInstance())
    }

    fun logout(){
        preferences.edit().clear().commit()
    }

    fun saveAccessToken(accessToken: AccessToken) {
        preferences.applyToJson("access_token", accessToken)
        preferences.edit().putLong("expired_at", Date().time + accessToken.expiresIn * 900).commit()
    }

    fun hasAccessToken(): Boolean {
        if (!preferences.contains("access_token")) return false
        val accessToken = getAccessToken()
        return accessToken.refreshToken != ""
    }

    fun getAccessToken(): AccessToken {
        return preferences.getFromJson("access_token", AccessToken::class.java)
    }

    fun isExpiredToken(): Boolean {
        return Date().time >= preferences.getLong("expired_at", 0)
//        return true
    }


}
