package t.masahide.android.croudia

import com.squareup.moshi.Json
import t.masahide.android.croudia.entitiy.Entities
import t.masahide.android.croudia.entitiy.Source
import t.masahide.android.croudia.entitiy.Status
import t.masahide.android.croudia.entitiy.User

/**
 * Created by Masahide on 2016/05/18.
 */
object StatusTestUtil {
    fun createStatus(
            favorited: Boolean = false,
            favoritedCount: Int = 0,
            id: String = "1",
            inReplyToScreeName: String = "",
            inReplyToUserId: String = "",
            spreadCount: Int = 0,
            spread: Boolean = false,
            spreadStatus: Status? = null,
            replyStatus: Status? = null,
            source: Source? = null,
            text: String = "",
            user: User = UserTestUtil.createUser(),
            entities: Entities? = null,
            createdAt: String = ""
    ): Status {
        val status = Status(favorited, favoritedCount, id, inReplyToScreeName, inReplyToUserId, spreadCount, spread, spreadStatus, replyStatus, source, text, user, entities, createdAt)
        return status;
    }
}
