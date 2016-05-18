package t.masahide.android.croudia.presenter

import org.junit.Before
import org.junit.Test

import t.masahide.android.croudia.api.CroudiaAPIService
import t.masahide.android.croudia.api.ServiceCreatable
import t.masahide.android.croudia.service.AccessTokenService
import t.masahide.android.croudia.service.IAccessTokenService
import t.masahide.android.croudia.ui.fragment.ITimeLineFragmentBase
import t.masahide.android.croudia.ui.fragment.TimelineFragmentBase

import org.junit.Assert.assertEquals
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify
import t.masahide.android.croudia.StatusTestUtil
import t.masahide.android.croudia.entitiy.Status
import t.masahide.android.croudia.entitiy.User

class TimeLineFragmentPresenterTest {

    lateinit  var target: TimeLineFragmentPresenter

    @Before
    fun setUp() {
        target = TimeLineFragmentPresenter(mock(ITimeLineFragmentBase::class.java), mock(CroudiaAPIService::class.java), mock(IAccessTokenService::class.java))
    }

    @Test
    fun onNextRequestedTimeLine_maxIdとsinceIdがちゃんと更新される() {
        val status1 = StatusTestUtil.createStatus(id = "1")
        val status2 = StatusTestUtil.createStatus(id = "2")
        val status3 = StatusTestUtil.createStatus(id = "3")
        val statusList = arrayListOf(status1,status2,status3)
        target.onNextRequestedTimeLine(statusList,false,false)
        assertEquals(target.maxId, "3")
        assertEquals(target.sinceId,"1")
        verify(target.fragment).loadedStatus(statusList)
    }

    @Test
    fun onNextRequestedTimeLine_finishRefreshがちゃんと呼ばれる() {
        val status1 = StatusTestUtil.createStatus(id = "1")
        val status2 = StatusTestUtil.createStatus(id = "2")
        val status3 = StatusTestUtil.createStatus(id = "3")
        val statusList = arrayListOf(status1,status2,status3)
        target.onNextRequestedTimeLine(statusList,false,true)
        assertEquals(target.maxId, "3")
        assertEquals(target.sinceId,"1")
        verify(target.fragment).finishRefresh()
    }
}
