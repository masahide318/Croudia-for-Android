package t.masahide.android.croudia

import com.squareup.moshi.Json
import t.masahide.android.croudia.entitiy.User

object UserTestUtil{
    fun createUser(
             description: String = "",
             favoritesCount: Int = 0,
             followRequestSent: Boolean = false,
             following: Boolean = false,
             followersCount: Int = 0,
             friendsCount: Int = 0,
             id: String = "",
             location: String = "",
             name: String = "",
             screenName: String = "",
             profileImageUrl: String = "",
             coverImageUrl: String = "",
             isProtected: Boolean = false,
             statuesCount: Int = 0,
             url: String? = null
    ):User{
        return User(description, favoritesCount, followRequestSent, following, followersCount, friendsCount, id, location, name, screenName, profileImageUrl, coverImageUrl)
    }
}
