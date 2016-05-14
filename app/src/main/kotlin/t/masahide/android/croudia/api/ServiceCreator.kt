package t.masahide.android.croudia.api

import com.squareup.okhttp.OkHttpClient
import t.masahide.android.croudia.api.ServiceCreatable
import retrofit.MoshiConverterFactory
import retrofit.Retrofit
import retrofit.RxJavaCallAdapterFactory
import t.masahide.android.croudia.api.CroudiaAPIService

/**
 * Created by Takahata on 2016/01/04.
 */

object ServiceCreator : ServiceCreatable {

    override fun build(): CroudiaAPIService {
        val retrofit = Retrofit.Builder()
                .addConverterFactory(MoshiConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .client(OkHttpClient())
                .baseUrl("https://api.croudia.com")
                .build();

        return retrofit.create(CroudiaAPIService::class.java);
    }

}