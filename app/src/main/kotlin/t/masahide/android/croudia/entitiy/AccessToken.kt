package t.masahide.android.croudia.entitiy

import com.squareup.moshi.Json

/**
 * Created by masahide on 15/02/17.
 */
data class AccessToken(
        @Json(name = "access_token")
        val accessToken: String,
        @Json(name = "refresh_token")
        val refreshToken: String,
        @Json(name = "expires_in")
        val expiresIn: Long = 0,
        @Json(name = "token_type")
        val tokenType: String) {}
