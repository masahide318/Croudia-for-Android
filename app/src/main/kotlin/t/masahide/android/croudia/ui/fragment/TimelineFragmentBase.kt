package t.masahide.android.croudia.ui.fragment

import android.content.Context
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AbsListView
import com.trello.rxlifecycle.components.support.RxFragment
import t.masahide.android.croudia.R
import t.masahide.android.croudia.adapter.TimeLineAdapter
import t.masahide.android.croudia.databinding.FragmentTimelineBinding
import t.masahide.android.croudia.entitiy.Status
import t.masahide.android.croudia.presenter.TimeLineFragmentPresenter

/**
 * Created by Masahide on 2016/05/09.
 */

open class TimelineFragmentBase : RxFragment() {

    interface Callback {
        fun onClickReply(status: Status)
    }

    val presenter = TimeLineFragmentPresenter(this)
    lateinit var adapter: TimeLineAdapter
    lateinit var binding: FragmentTimelineBinding

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        return inflater!!.inflate(R.layout.fragment_timeline, container, false)
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
    }

    override fun onResume() {
        super.onResume()
        presenter.requestTimeline(refresh = true)
    }

    fun finishRefresh() {
        adapter.clear()
    }

    fun loadTimeLineBySinceId() {
        presenter.loadTimeLineSince()
    }

    fun loadedStatus(statusList: List<Status>) {
        binding.progressBar.visibility = View.GONE
        adapter.addAll(statusList)
        adapter.notifyDataSetChanged()
        binding.refresh.isRefreshing = false
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = DataBindingUtil.bind(view)
        adapter = TimeLineAdapter(this.context, R.layout.time_line_row, arrayListOf())
        binding.refresh.setOnRefreshListener { presenter.refresh() }
        binding.list.setOnItemClickListener { parent, view, position, id ->
            val selectedStatus = adapter.getItem(position)
            when (id) {
                R.id.txtShare.toLong() -> presenter.clickShare(selectedStatus)
                R.id.favorite.toLong() -> presenter.clickFavorite(selectedStatus)
                R.id.imgReply.toLong() -> {
                    if (isAdded && activity is Callback) {
                        (activity as Callback).onClickReply(selectedStatus)
                    }
                }
                R.id.imgDelete.toLong() -> {
                    presenter.clickDelete(selectedStatus)
                    adapter.remove(selectedStatus)
                    adapter.notifyDataSetChanged()
                }
                else -> {
                }
            }
        }
        binding.list.setOnScrollListener(object : AbsListView.OnScrollListener {
            override fun onScroll(view: AbsListView?, firstVisibleItem: Int, visibleItemCount: Int, totalItemCount: Int) {
                presenter.onScrollChanged(view, firstVisibleItem, visibleItemCount, totalItemCount,adapter)
            }

            override fun onScrollStateChanged(p0: AbsListView?, p1: Int) {
            }

        })
        binding.list.adapter = adapter
    }

    fun onSuccessShare() {
        Snackbar.make(binding.root, "シェアしました", Snackbar.LENGTH_SHORT).setAction("Action", null).show()
    }

    fun onErrorShare() {

    }

    fun onSuccessFavorite() {
        Snackbar.make(binding.root, "お気に入り登録しました", Snackbar.LENGTH_SHORT).setAction("Action", null).show()
    }

    fun onErrorFavorite() {


    }

    fun loadedPagignStatus(statusList: List<Status>) {
        for (item in statusList.reversed()) {
            adapter.insert(item, 0)
        }
        adapter.notifyDataSetChanged()

    }
}