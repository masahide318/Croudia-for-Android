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

/**
 * Created by Masahide on 2016/03/13.
 */

class AuthPresenter(val activity: AuthActivity, val service: ServiceCreatable = ServiceCreator, val accessTokenService: AccessTokenService = AccessTokenService()) {

    fun auth(code: String) {
        service.build().createToken(code)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnError { Log.d("hoge", "refresh_error") }
                .onNext {
                    accessTokenService.saveAccessToken(it)
                    activity.setResult(AppCompatActivity.RESULT_OK)
                    activity.finish()
        }.subscribe()
    }
}