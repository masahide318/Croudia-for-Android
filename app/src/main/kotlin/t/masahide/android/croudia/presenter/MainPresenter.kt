package t.masahide.android.croudia.presenter

import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers
import t.masahide.android.croudia.entitiy.Status
import t.masahide.android.croudia.entitiy.User
import t.masahide.android.croudia.extensions.onNext
import t.masahide.android.croudia.service.PreferenceService
import t.masahide.android.croudia.ui.activity.MainActivity

/**
 * Created by Masahide on 2016/03/13.
 */

class MainPresenter(val activity: MainActivity, val preferenceService: PreferenceService = PreferenceService()) : APIExecutePresenterBase() {
    fun isLogin(): Boolean {
        //        accessTokenService.logout()
        return !accessTokenService.getAccessToken().accessToken.isNullOrEmpty()
    }

    //
    fun verifyCredential() {
        refreshToken().subscribe {
            service.build().verifyCredentials(accessToken)
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
            service.build().postStatus(accessToken,status,replyStatusId)
                    .compose(activity.bindToLifecycle<Status>())
                    .subscribeOn(Schedulers.newThread())
                    .observeOn(AndroidSchedulers.mainThread())
                    .onNext { activity.onPostSuccess() }
                    .onError {  }
                    .subscribe()
        }

    }

}