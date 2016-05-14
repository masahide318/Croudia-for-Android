package t.masahide.android.croudia.entitiy

import android.support.v4.app.Fragment
import t.masahide.android.croudia.ui.fragment.FavoriteTimeLineFragment
import t.masahide.android.croudia.ui.fragment.HomeTimeLineFragment
import t.masahide.android.croudia.ui.fragment.PublicTimeLineFragment
import t.masahide.android.croudia.ui.fragment.ReplyTimeLineFragment

/**
 * Created by Masahide on 2016/05/04.
 */

enum class TimeLineEnum{
    PUBLIC {
        override fun getTitle(): String {
            return "Public"
        }

        override fun getFragment(): Fragment {
            return PublicTimeLineFragment.newInstance()
        }
    },
    HOME {
        override fun getTitle(): String {
            return "HOME"
        }

        override fun getFragment(): Fragment {
            return HomeTimeLineFragment.newInstance()
        }
    },
    MENTION {
        override fun getTitle(): String {
            return "REPLY"
        }

        override fun getFragment(): Fragment {
            return ReplyTimeLineFragment.newInstance()
        }
    },
    FAVORITE {
        override fun getTitle(): String {
            return "FAVORITE"
        }

        override fun getFragment(): Fragment {
            return FavoriteTimeLineFragment.newInstance()
        }
    };
    abstract fun getTitle():String
    abstract fun getFragment(): Fragment
}