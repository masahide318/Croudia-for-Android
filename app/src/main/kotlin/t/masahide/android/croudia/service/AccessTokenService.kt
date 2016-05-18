package t.masahide.android.croudia.service

import android.content.SharedPreferences
import android.preference.PreferenceManager
import t.masahide.android.croudia.extensions.applyToJson
import t.masahide.android.croudia.extensions.getFromJson

import java.util.Date

import t.masahide.android.croudia.MyApplication
import t.masahide.android.croudia.entitiy.AccessToken

class AccessTokenService:IAccessTokenService {
    private val preferences: SharedPreferences

    init {
        preferences = PreferenceManager.getDefaultSharedPreferences(MyApplication.getInstance())
    }

    override fun logout(){
        preferences.edit().clear().commit()
    }

    override fun saveAccessToken(accessToken: AccessToken) {
        preferences.applyToJson("access_token", accessToken)
        //有効期限少し短くしてる
        preferences.edit().putLong("expired_at", Date().time + accessToken.expiresIn * 900).commit()
    }

    override fun getAccessToken(): AccessToken {
        return preferences.getFromJson("access_token", AccessToken::class.java)
    }

    override fun isExpiredToken(): Boolean {
        return Date().time >= preferences.getLong("expired_at", 0)
    }
}
