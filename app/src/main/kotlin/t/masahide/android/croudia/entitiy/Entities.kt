package t.masahide.android.croudia.entitiy

import java.util.*

import android.os.Parcel
import android.os.Parcelable

import com.squareup.moshi.Json
import t.masahide.android.croudia.entitiy.Media

data class Entities(
        @Json(name = "media")
        val media: Media? = null
) : Parcelable {
    constructor(source: Parcel): this(source.readParcelable<Media?>(Media::class.java.classLoader))

    override fun describeContents(): Int {
        return 0
    }

    override fun writeToParcel(dest: Parcel?, flags: Int) {
        dest?.writeParcelable(media, 0)
    }

    companion object {
        @JvmField final val CREATOR: Parcelable.Creator<Entities> = object : Parcelable.Creator<Entities> {
            override fun createFromParcel(source: Parcel): Entities {
                return Entities(source)
            }

            override fun newArray(size: Int): Array<Entities?> {
                return arrayOfNulls(size)
            }
        }
    }
}