package t.masahide.android.croudia.entitiy

import java.util.*

import android.os.Parcel
import android.os.Parcelable
import com.squareup.moshi.Json

/**
 * Created with IntelliJ IDEA.
 * User: masahide
 * Date: 2013/08/12
 * Time: 23:47
 * To change this template use File | Settings | File Templates.
 */
data class User(
        @Json(name = "description")
        val description: String,
        @Json(name = "favorites_count")
        val favoritesCount: Int = 0,
        @Json(name = "follow_request_sent")
        val followRequestSent: Boolean = false,
        @Json(name = "following")
        val following: Boolean = false,
        @Json(name = "followers_count")
        val followersCount: Int = 0,
        @Json(name = "friends_count")
        val friendsCount: Int = 0,
        @Json(name = "id_str")
        val id: String,
        @Json(name = "location")
        val location: String,
        @Json(name = "name")
        val name: String,
        @Json(name = "screen_name")
        val screenName: String,
        @Json(name = "profile_image_url_https")
        val profileImageUrl: String,
        @Json(name = "cover_image_url_https")
        val coverImageUrl: String,
        @Json(name = "protected")
        val isProtected: Boolean = false,
        @Json(name = "statuses_count")
        val statuesCount: Int = 0,
        @Json(name = "url")
        val url: String? = null) : Parcelable {
        constructor(source: Parcel): this(source.readString(), source.readInt(), 1.toByte().equals(source.readByte()), 1.toByte().equals(source.readByte()), source.readInt(), source.readInt(), source.readString(), source.readString(), source.readString(), source.readString(), source.readString(), source.readString(), 1.toByte().equals(source.readByte()), source.readInt(), source.readSerializable() as String?)

        override fun describeContents(): Int {
                return 0
        }

        override fun writeToParcel(dest: Parcel?, flags: Int) {
                dest?.writeString(description)
                dest?.writeInt(favoritesCount)
                dest?.writeByte((if (followRequestSent) 1 else 0).toByte())
                dest?.writeByte((if (following) 1 else 0).toByte())
                dest?.writeInt(followersCount)
                dest?.writeInt(friendsCount)
                dest?.writeString(id)
                dest?.writeString(location)
                dest?.writeString(name)
                dest?.writeString(screenName)
                dest?.writeString(profileImageUrl)
                dest?.writeString(coverImageUrl)
                dest?.writeByte((if (isProtected) 1 else 0).toByte())
                dest?.writeInt(statuesCount)
                dest?.writeSerializable(url)
        }

        companion object {
                @JvmField final val CREATOR: Parcelable.Creator<User> = object : Parcelable.Creator<User> {
                        override fun createFromParcel(source: Parcel): User {
                                return User(source)
                        }

                        override fun newArray(size: Int): Array<User?> {
                                return arrayOfNulls(size)
                        }
                }
        }
}