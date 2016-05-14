package t.masahide.android.croudia.entitiy

import java.util.*

import android.os.Parcel
import android.os.Parcelable
import com.squareup.moshi.Json

/**
 * Created with IntelliJ IDEA.
 * User: masahide
 * Date: 2013/08/19
 * Time: 23:40
 * To change this template use File | Settings | File Templates.
 */
data class Source(
        @Json(name = "name")
        var name: String,
        @Json(name = "url")
        var url: String) : Parcelable {
        constructor(source: Parcel): this(source.readString(), source.readString())

        override fun describeContents(): Int {
                return 0
        }

        override fun writeToParcel(dest: Parcel?, flags: Int) {
                dest?.writeString(name)
                dest?.writeString(url)
        }

        companion object {
                @JvmField final val CREATOR: Parcelable.Creator<Source> = object : Parcelable.Creator<Source> {
                        override fun createFromParcel(source: Parcel): Source {
                                return Source(source)
                        }

                        override fun newArray(size: Int): Array<Source?> {
                                return arrayOfNulls(size)
                        }
                }
        }
}