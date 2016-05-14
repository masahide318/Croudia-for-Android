package t.masahide.android.croudia.adapter

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import android.support.v4.view.ViewPager
import t.masahide.android.croudia.entitiy.TimeLineEnum
import t.masahide.android.croudia.ui.fragment.PublicTimeLineFragment
import t.masahide.android.croudia.ui.fragment.TimelineFragmentBase

/**
 * Created by Masahide on 2016/05/04.
 */

class TimeLinePagerAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm) {

    override fun getItem(position: Int): Fragment {
        return TimeLineEnum.values()[position].getFragment()
    }

    override fun getCount(): Int {
        return TimeLineEnum.values().size
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return TimeLineEnum.values().get(position).getTitle()
    }

    fun getCurrentFragment(viewPager:ViewPager,position: Int): TimelineFragmentBase {
        return instantiateItem(viewPager,position) as TimelineFragmentBase
    }
}