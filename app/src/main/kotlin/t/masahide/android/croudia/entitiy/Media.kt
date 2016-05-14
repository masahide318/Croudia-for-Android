package t.masahide.android.croudia.entitiy

import java.util.*

import android.os.Parcel
import android.os.Parcelable
import com.squareup.moshi.Json

/**
 * Created by masahide on 15/02/23.
 */
data class Media(
        @Json(name = "media_url_https")
        var mediaUrl: String,
        @Json(name = "type")
        var type: String
) : Parcelable {
        constructor(source: Parcel): this(source.readString(), source.readString())

        override fun describeContents(): Int {
                return 0
        }

        override fun writeToParcel(dest: Parcel?, flags: Int) {
                dest?.writeString(mediaUrl)
                dest?.writeString(type)
        }

        companion object {
                @JvmField final val CREATOR: Parcelable.Creator<Media> = object : Parcelable.Creator<Media> {
                        override fun createFromParcel(source: Parcel): Media {
                                return Media(source)
                        }

                        override fun newArray(size: Int): Array<Media?> {
                                return arrayOfNulls(size)
                        }
                }
        }
}