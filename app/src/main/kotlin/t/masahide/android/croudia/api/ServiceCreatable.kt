package t.masahide.android.croudia.api

import t.masahide.android.croudia.api.CroudiaAPIService

/**
 * Created by Masahide on 2016/05/04.
 */

interface ServiceCreatable{
    fun build(): CroudiaAPIService
}