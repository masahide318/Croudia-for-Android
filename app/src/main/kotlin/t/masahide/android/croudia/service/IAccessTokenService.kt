package t.masahide.android.croudia.service

import t.masahide.android.croudia.entitiy.AccessToken

interface IAccessTokenService {

    fun logout()
    fun saveAccessToken(accessToken: AccessToken)
    fun getAccessToken(): AccessToken
    fun isExpiredToken(): Boolean
}
