package t.masahide.android.croudia.presenter

import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers
import t.masahide.android.croudia.entitiy.Status
import t.masahide.android.croudia.entitiy.User
import t.masahide.android.croudia.extensions.onNext
import t.masahide.android.croudia.service.PreferenceService
import t.masahide.android.croudia.ui.activity.MainActivity


class MainPresenter(val activity: MainActivity, val preferenceService: PreferenceService = PreferenceService()) : APIExecutePresenterBase() {
    fun isLogin(): Boolean {
        return !accessTokenService.getAccessToken().accessToken.isNullOrEmpty()
    }

    //
    fun verifyCredential() {
        refreshToken().subscribe {
            croudiaAPI.verifyCredentials(accessToken)
                    .compose(activity.bindToLifecycle<User>())
                    .subscribeOn(Schedulers.newThread())
                    .observeOn(AndroidSchedulers.mainThread())
                    .onNext {
                        preferenceService.applyUser(it)
                        activity.bindDataDrawView(it)
                    }
                    .onError { accessTokenService.logout() }
                    .subscribe()
        }
    }

    fun postStatus(status: String,replyStatusId:String = "") {
        refreshToken().subscribe {
            croudiaAPI.postStatus(accessToken,status,replyStatusId)
                    .compose(activity.bindToLifecycle<Status>())
                    .subscribeOn(Schedulers.newThread())
                    .observeOn(AndroidSchedulers.mainThread())
                    .onNext { activity.onPostSuccess() }
                    .onError {  }
                    .subscribe()
        }

    }

}