package t.masahide.android.croudia.presenter

import android.util.Log
import com.trello.rxlifecycle.ActivityLifecycleProvider
import t.masahide.android.croudia.api.ServiceCreatable
import t.masahide.android.croudia.entitiy.Status
import t.masahide.android.croudia.service.AccessTokenService
import rx.Observable
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers
import t.masahide.android.croudia.LockObject
import t.masahide.android.croudia.api.ServiceCreator
import t.masahide.android.croudia.entitiy.AccessToken
import t.masahide.android.croudia.entitiy.User
import t.masahide.android.croudia.extensions.onCompleted
import t.masahide.android.croudia.extensions.onError
import t.masahide.android.croudia.extensions.onNext
import java.util.concurrent.TimeUnit
import kotlin.concurrent.withLock

/**
 * Created by Masahide on 2016/05/04.
 */

abstract class APIExecutePresenterBase(val service: ServiceCreatable = ServiceCreator, val accessTokenService: AccessTokenService = AccessTokenService()) {
    val accessToken: String
        get() = " Bearer " + accessTokenService.getAccessToken().accessToken

    val refreshToken: String
        get() = accessTokenService.getAccessToken().refreshToken

    val delayTime: Long
        get() = if (LockObject.refreshing) 1000 else 0

    fun refreshToken(): Observable<AccessToken> {
        val refreshObservable: Observable<AccessToken>
        if (!accessTokenService.isExpiredToken() || LockObject.refreshing) {
            refreshObservable = Observable.create<AccessToken> {
                subscribe ->
                subscribe.onNext(accessTokenService.getAccessToken())
            }.delay(delayTime, TimeUnit.MILLISECONDS)
        } else {
            LockObject.refreshing = true
            refreshObservable = Observable.create<AccessToken> {
                subscribe ->
                service.build().refreshToken(accessToken, refreshToken)
                        .subscribeOn(Schedulers.newThread())
                        .observeOn(AndroidSchedulers.mainThread())
                        .onNext {
                            accessTokenService.saveAccessToken(it)
                            subscribe.onNext(it)
                            LockObject.refreshing = false
                        }
                        .onError {
                            LockObject.refreshing = false
                        }.subscribe()
            }
        }
        return refreshObservable
        //        return Observable.create<Boolean> { subscriber ->
        //            if (!accessTokenService.isExpiredToken()) {
        //                subscriber.onNext(true)
        //            } else {
        //                service.build().refreshToken(accessToken, refreshToken)
        //                        .subscribeOn(Schedulers.newThread())
        //                        .observeOn(AndroidSchedulers.mainThread())
        //                        .onNext {
        //                            accessTokenService.saveAccessToken(it)
        //                            subscriber.onNext(true)
        //                            Log.d("refreshObservable", "refresh_success")
        //                        }
        //                        .onError {
        //                            Log.d("refreshObservable", "refresh_error")
        //                        }
        //                        .subscribe()
        //            }
        //        }

    }

}