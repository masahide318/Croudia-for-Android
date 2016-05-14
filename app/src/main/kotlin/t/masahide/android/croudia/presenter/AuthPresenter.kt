package t.masahide.android.croudia.presenter

import android.content.Context
import android.support.v7.app.AppCompatActivity
import android.util.Log
import t.masahide.android.croudia.api.ServiceCreatable
import t.masahide.android.croudia.extensions.onNext
import t.masahide.android.croudia.service.AccessTokenService
import t.masahide.android.croudia.ui.activity.AuthActivity
import t.masahide.android.croudia.api.ServiceCreator
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers
import t.masahide.android.croudia.entitiy.User
import t.masahide.android.croudia.service.PreferenceService
import t.masahide.android.croudia.ui.activity.MainActivity

/**
 * Created by Masahide on 2016/03/13.
 */

class AuthPresenter(val activity: AuthActivity, val preferenceService: PreferenceService = PreferenceService()) : APIExecutePresenterBase() {

    fun auth(code: String) {
        service.build().createToken(code)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnError { }
                .onNext {
                    accessTokenService.saveAccessToken(it)
                    verifyCredential()
                }.subscribe()
    }

    fun verifyCredential() {
        service.build().verifyCredentials(accessToken)
                .compose(activity.bindToLifecycle<User>())
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .onNext {
                    preferenceService.applyUser(it)
                    activity.setResult(AppCompatActivity.RESULT_OK)
                    activity.finish()
                }
                .onError { accessTokenService.logout() }
                .subscribe()
    }
}