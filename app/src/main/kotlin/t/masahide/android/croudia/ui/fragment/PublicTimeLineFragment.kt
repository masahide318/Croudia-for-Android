package t.masahide.android.croudia.ui.fragment

import android.os.Bundle


/**
 * Created by Masahide on 2016/05/04.
 */
class PublicTimeLineFragment : TimelineFragmentBase() {
    companion object {
        fun newInstance(): PublicTimeLineFragment {
            val fragment = PublicTimeLineFragment()
            val args = Bundle()
            fragment.arguments = args
            return fragment
        }
    }
}