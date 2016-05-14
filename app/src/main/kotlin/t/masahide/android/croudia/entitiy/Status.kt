package t.masahide.android.croudia.entitiy


import android.os.Parcel
import android.os.Parcelable
import com.squareup.moshi.Json
import t.masahide.android.croudia.entitiy.Entities
import t.masahide.android.croudia.entitiy.Source
import t.masahide.android.croudia.entitiy.User

data class Status(
        @Json(name = "favorited")
        val favorited: Boolean = false,
        @Json(name = "favorited_count")
        val favoritedCount: Int = 0,
        @Json(name = "id")
        val id: String,
        @Json(name = "in_reply_to_screen_name")
        val inReplyToScreeName: String,
        @Json(name = "in_reply_to_user_id_str")
        val inReplyToUserId: String,
        @Json(name = "spread_count")
        val spreadCount: Int = 0,
        @Json(name = "spread")
        val spread: Boolean = false,
        @Json(name = "spread_status")
        val spreadStatus: Status? = null,
        @Json(name = "reply_status")
        val replyStatus: Status? = null,
        @Json(name = "source")
        val source: Source? = null,
        @Json(name = "text")
        val text: String,
        @Json(name = "user")
        val user: User,
        @Json(name = "entities")
        val entities: Entities? = null,
        @Json(name = "created_at")
        val createdAt: String
) : Parcelable {
        constructor(source: Parcel): this(1.toByte().equals(source.readByte()), source.readInt(), source.readString(), source.readString(), source.readString(), source.readInt(), 1.toByte().equals(source.readByte()), source.readParcelable<Status?>(Status::class.java.classLoader), source.readParcelable<Status?>(Status::class.java.classLoader), source.readParcelable<Source?>(Source::class.java.classLoader), source.readString(), source.readParcelable<User>(User::class.java.classLoader), source.readParcelable<Entities?>(Entities::class.java.classLoader), source.readString())

        override fun describeContents(): Int {
                return 0
        }

        override fun writeToParcel(dest: Parcel?, flags: Int) {
                dest?.writeByte((if (favorited) 1 else 0).toByte())
                dest?.writeInt(favoritedCount)
                dest?.writeString(id)
                dest?.writeString(inReplyToScreeName)
                dest?.writeString(inReplyToUserId)
                dest?.writeInt(spreadCount)
                dest?.writeByte((if (spread) 1 else 0).toByte())
                dest?.writeParcelable(spreadStatus,0)
                dest?.writeParcelable(replyStatus,0)
                dest?.writeParcelable(source, 0)
                dest?.writeString(text)
                dest?.writeParcelable(user, 0)
                dest?.writeParcelable(entities, 0)
                dest?.writeString(createdAt)
        }

        companion object {
                @JvmField final val CREATOR: Parcelable.Creator<Status> = object : Parcelable.Creator<Status> {
                        override fun createFromParcel(source: Parcel): Status {
                                return Status(source)
                        }

                        override fun newArray(size: Int): Array<Status?> {
                                return arrayOfNulls(size)
                        }
                }
        }
}
