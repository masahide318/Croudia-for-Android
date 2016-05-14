package t.masahide.android.croudia

import android.app.Application
import t.masahide.android.croudia.entitiy.User

class MyApplication : Application() {
    var user: User? = null

    override fun onCreate() {
        super.onCreate()
        myApplication = this
    }

    companion object {

        private var myApplication: MyApplication? = null

        fun getInstance(): MyApplication {
            return myApplication!!
        }
    }
}