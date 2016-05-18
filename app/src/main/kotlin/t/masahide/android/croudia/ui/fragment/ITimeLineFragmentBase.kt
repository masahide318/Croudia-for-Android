package t.masahide.android.croudia.ui.fragment

import com.trello.rxlifecycle.FragmentLifecycleProvider
import t.masahide.android.croudia.entitiy.Status

interface ITimeLineFragmentBase: FragmentLifecycleProvider {
    fun onSuccessShare()

    fun onErrorShare()

    fun onSuccessFavorite()

    fun onErrorFavorite()

    fun loadedStatus(statusList: List<Status>)

    fun loadedPagignStatus(statusList: List<Status>)

    fun finishRefresh()

}
