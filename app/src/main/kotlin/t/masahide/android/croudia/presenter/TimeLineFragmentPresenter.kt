package t.masahide.android.croudia.presenter

import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.AbsListView
import android.widget.ArrayAdapter
import rx.Observable
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers
import t.masahide.android.croudia.R
import t.masahide.android.croudia.adapter.TimeLineAdapter
import t.masahide.android.croudia.api.CroudiaAPIService
import t.masahide.android.croudia.api.ServiceCreatable
import t.masahide.android.croudia.api.ServiceCreator
import t.masahide.android.croudia.entitiy.AccessToken
import t.masahide.android.croudia.entitiy.Status
import t.masahide.android.croudia.extensions.onNext
import t.masahide.android.croudia.service.AccessTokenService
import t.masahide.android.croudia.service.IAccessTokenService
import t.masahide.android.croudia.ui.fragment.*

open class TimeLineFragmentPresenter(val fragment: ITimeLineFragmentBase, croudiaAPI: CroudiaAPIService = ServiceCreator.build(), accessTokenService: IAccessTokenService = AccessTokenService()) : APIExecutePresenterBase(croudiaAPI, accessTokenService) {


    var nowLoading = false
    var sinceId = "0"
    var maxId = "0"
    fun requestTimeline(sinceId: String = "", maxId: String = "", count: Int = 30, refresh: Boolean = false, pagingRequest: Boolean = false) {
        if (nowLoading && !refresh) return
        nowLoading = true
        refreshToken().subscribe {
            timeLineAPI(sinceId, maxId, count)
                    .compose(fragment.bindToLifecycle<List<Status>>())
                    .subscribeOn(Schedulers.newThread())
                    .observeOn(AndroidSchedulers.mainThread())
                    .onNext {
                        onNextRequestedTimeLine(it, pagingRequest, refresh)
                    }
                    .onError {
                        onErrorRequestedTimeLine()
                    }.subscribe()

        }
    }

    fun onErrorRequestedTimeLine() {
        nowLoading = false
        fragment.finishRefresh()
    }

    fun onNextRequestedTimeLine(statusList: List<Status>, pagingRequest: Boolean, refresh: Boolean) {
        nowLoading = false
        if (statusList.first().id.toInt() > sinceId.toInt()) {
            sinceId = statusList.first().id
        }
        if (maxId == "0" || statusList.last().id.toInt() < maxId.toInt()) {
            maxId = statusList.last().id
        }
        if (refresh) {
            fragment.finishRefresh()
        }
        if (pagingRequest) {
            fragment.loadedPagignStatus(statusList)
        } else {
            fragment.loadedStatus(statusList)
        }
    }

    fun loadTimeLineSince() {
        requestTimeline(sinceId = sinceId, pagingRequest = true)
    }

    fun timeLineAPI(sinceId: String, maxId: String, count: Int): Observable<MutableList<Status>> {
        when (fragment) {
            is PublicTimeLineFragment -> return croudiaAPI.publicTimeLine(accessToken, sinceId, maxId, count)
            is HomeTimeLineFragment -> return croudiaAPI.homeTimeLine(accessToken, sinceId, maxId, count)
            is ReplyTimeLineFragment -> return croudiaAPI.mentionsTimeLine(accessToken, sinceId, maxId, count)
            is FavoriteTimeLineFragment -> return croudiaAPI.favoriteTimeLine(accessToken, sinceId, maxId, count)
            else -> throw RuntimeException("unknown fragment")
        }
    }

    fun onScrollChanged(view: AbsListView?, firstVisibleItem: Int, visibleItemCount: Int, totalItemCount: Int, adapter: TimeLineAdapter) {
        if ( (visibleItemCount + firstVisibleItem == totalItemCount) && !nowLoading && adapter.count > 0) {
            requestTimeline(maxId = maxId, count = 100)
        }
    }

    fun selectListItem(position: Int, id: Long, adapter: TimeLineAdapter, callback: TimelineFragmentBase.Callback) {
        val selectedStatus = adapter.getItem(position)
        when (id) {
            R.id.txtShare.toLong() -> clickShare(selectedStatus)
            R.id.favorite.toLong() -> clickFavorite(selectedStatus)
            R.id.imgReply.toLong() -> callback.onClickReply(selectedStatus)
            R.id.imgDelete.toLong() -> {
                clickDelete(selectedStatus)
                adapter.remove(selectedStatus)
                adapter.notifyDataSetChanged()
            }
            else -> {
            }
        }
    }

    fun refresh() {
        sinceId = "0"
        maxId = "0"
        requestTimeline(refresh = true)
    }

    fun clickShare(item: Status) {
        refreshToken().subscribe {
            croudiaAPI.spreadStatus(accessToken, item.id)
                    .compose(fragment.bindToLifecycle<Status>())
                    .subscribeOn(Schedulers.newThread())
                    .observeOn(AndroidSchedulers.mainThread())
                    .onNext {
                        fragment.onSuccessShare()
                    }
                    .onError {
                        fragment.onErrorShare()
                    }.subscribe()
        }
    }

    fun clickFavorite(item: Status) {
        refreshToken().subscribe {
            croudiaAPI.favoriteCreate(accessToken, item.id)
                    .compose(fragment.bindToLifecycle<Status>())
                    .subscribeOn(Schedulers.newThread())
                    .observeOn(AndroidSchedulers.mainThread())
                    .onNext {
                        fragment.onSuccessFavorite()
                    }
                    .onError {
                        fragment.onErrorFavorite()
                        Log.d("hoge", "favorite")
                    }.subscribe()
        }
    }

    fun clickDelete(status: Status) {
        refreshToken().subscribe {
            croudiaAPI.destroyStatus(accessToken, status.id)
                    .compose(fragment.bindToLifecycle<Status>())
                    .subscribeOn(Schedulers.newThread())
                    .observeOn(AndroidSchedulers.mainThread())
                    .onNext {
                    }
                    .onError {
                    }.subscribe()
        }
    }
}