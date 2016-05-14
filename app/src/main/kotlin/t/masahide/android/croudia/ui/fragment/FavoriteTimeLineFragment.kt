package t.masahide.android.croudia.ui.fragment

import android.content.Context
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v4.app.Fragment
import android.support.v4.widget.SwipeRefreshLayout
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AbsListView
import android.widget.AdapterView
import android.widget.ListView
import com.trello.rxlifecycle.components.support.RxFragment
import t.masahide.android.croudia.R
import t.masahide.android.croudia.adapter.TimeLineAdapter
import t.masahide.android.croudia.databinding.FragmentTimelineBinding
import t.masahide.android.croudia.entitiy.Status
import t.masahide.android.croudia.presenter.TimeLineFragmentPresenter
import java.util.*


/**
 * Created by Masahide on 2016/05/04.
 */
class FavoriteTimeLineFragment : TimelineFragmentBase() {

    companion object {
        fun newInstance(): FavoriteTimeLineFragment {
            val fragment = FavoriteTimeLineFragment()
            val args = Bundle()
            fragment.arguments = args
            return fragment
        }

    }
}