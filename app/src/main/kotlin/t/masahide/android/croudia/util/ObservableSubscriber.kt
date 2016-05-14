//package t.masahide.android.croudia.util
//
//import android.util.Log
//import rx.Observable
//import rx.Subscription
//import rx.android.schedulers.AndroidSchedulers
//import rx.schedulers.Schedulers
//import t.masahide.android.croudia.LockObject
//import t.masahide.android.croudia.api.ServiceCreatable
//import t.masahide.android.croudia.api.ServiceCreator
//import t.masahide.android.croudia.entitiy.APIObjectable
//import t.masahide.android.croudia.entitiy.AccessToken
//import t.masahide.android.croudia.extensions.KSubscription
//import t.masahide.android.croudia.extensions.onNext
//import t.masahide.android.croudia.service.AccessTokenService
//import java.util.*
//import java.util.concurrent.CountDownLatch
//import java.util.concurrent.TimeUnit
//import java.util.concurrent.locks.ReentrantLock
//import kotlin.concurrent.thread
//
///**
// * Created by Masahide on 2016/05/12.
// */
//
//object ObservableSubscriber {
//
//    val lock = Object()
//
//    fun initialize(accessTokenService: AccessTokenService = AccessTokenService(), service: ServiceCreatable = ServiceCreator) {
//        synchronized(this) {
//            Thread() {
//                while (true) {
//                    if (subscribeQueue.isEmpty()) {
//                        lock.wait()
//                    } else {
//                        createObserval(accessTokenService,service).subscribe {
//                            subscribeQueue[0].subscribe()
//                        }
//                    }
//                }
//            }.start()
//        }
//    }
//
//    fun addQueue(subscribe: KSubscription<APIObjectable>) {
//        synchronized(this){
//            subscribeQueue.add(subscribe)
//            lock.notify()
//        }
//    }
//
//    fun createObserval(accessTokenService: AccessTokenService, service: ServiceCreatable): Observable<AccessToken> {
//        val refreshObservable: Observable<AccessToken>
//        if (!accessTokenService.isExpiredToken()) {
//            refreshObservable = Observable.create<AccessToken> {
//                subscribe ->
//                subscribe.onNext(accessTokenService.getAccessToken())
//            }
//        } else {
//            refreshObservable = Observable.create<AccessToken> {
//                subscribe ->
//                service.build().refreshToken()
//                        .subscribeOn(Schedulers.newThread())
//                        .observeOn(AndroidSchedulers.mainThread())
//                        .onNext {
//                            Log.d("hoge", "refresh onNext")
//                            accessTokenService.saveAccessToken(it)
//                            subscribe.onNext(it)
//                        }
//                        .onError {
//                            Log.d("hoge", "refresh error")
//                        }.subscribe()
//            }
//        }
//        return refreshObservable
//    }
//}