package t.masahide.android.croudia.ui.activity

import android.content.Context
import android.content.Intent
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.design.widget.*
import android.support.v4.view.GravityCompat
import android.support.v4.view.ViewPager
import android.support.v4.widget.DrawerLayout
import android.support.v7.app.ActionBarDrawerToggle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.ImageView
import android.widget.TextView
import com.squareup.picasso.Picasso
import com.trello.rxlifecycle.components.support.RxAppCompatActivity
import t.masahide.android.croudia.adapter.TimeLinePagerAdapter
import t.masahide.android.croudia.entitiy.User
import t.masahide.android.croudia.presenter.MainPresenter
import t.masahide.android.croudia.R
import t.masahide.android.croudia.databinding.ActivityMainBinding
import t.masahide.android.croudia.entitiy.Status
import t.masahide.android.croudia.ui.fragment.TimelineFragmentBase

class MainActivity : RxAppCompatActivity(), NavigationView.OnNavigationItemSelectedListener,TimelineFragmentBase.Callback {


    val mainPresenter = MainPresenter(this)
    val REQUEST_CODE_AUTH = 1
    val binding by lazy {
        DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main)
    }
    val drawer by lazy {
        binding.drawerLayout
    }
    var mTmpSlideOffset = 0f

    lateinit var mTimeLinePagerAdapter: TimeLinePagerAdapter;
    lateinit var mViewPager: ViewPager
    val behavior by lazy {
        BottomSheetBehavior.from(binding.bottomSheet)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setSupportActionBar(binding.toolbar)

        mTimeLinePagerAdapter = TimeLinePagerAdapter(supportFragmentManager)
        mViewPager = binding.container
        mViewPager.adapter = mTimeLinePagerAdapter

        binding.tabs.setupWithViewPager(mViewPager)

        val fab = binding.fab

        behavior.setBottomSheetCallback(object : BottomSheetBehavior.BottomSheetCallback() {
            override fun onStateChanged(bottomSheet: View, newState: Int) {
                if(newState == BottomSheetBehavior.STATE_COLLAPSED){
                    closeBottomSheet()
                }
            }

            override fun onSlide(bottomSheet: View, slideOffset: Float) {
                if (slideOffset > 0.8) {
                    if (mTmpSlideOffset > slideOffset) {
                        fab.show();
                    } else {
                        fab.hide();
                    }
                } else if (slideOffset < -0.8) {
                    if (mTmpSlideOffset < slideOffset) {
                        fab.show();
                    } else {
                        fab.hide();
                    }
                }
                mTmpSlideOffset = slideOffset;
            }
        })

        fab.setOnClickListener {
            view -> openBottomSheet()
        }

        val toggle = ActionBarDrawerToggle(
                this, drawer, binding.toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        drawer.setDrawerListener(toggle)
        toggle.syncState()

        val navigationView = binding.navView
        navigationView.setNavigationItemSelectedListener(this)

        if (mainPresenter.isLogin()) {
            mainPresenter.verifyCredential()
        } else {
            AuthActivity.intent().start()
        }
        binding.editStatus.tag = ""
        binding.postButton.setOnClickListener {
            mainPresenter.postStatus(binding.editStatus.text.toString(),binding.editStatus.tag.toString())
        }

    }

    fun openBottomSheet(status:Status? = null){
        behavior.state = BottomSheetBehavior.STATE_EXPANDED
        status?.let {
            binding.editStatus.setText("@"+status.user.screenName + " ")
            binding.editStatus.tag = status.id
        }
        binding.editStatus.setSelection(binding.editStatus.text.length)
        binding.editStatus.isFocusableInTouchMode = true
        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.showSoftInput(binding.editStatus,0)

    }
    fun closeBottomSheet(){
        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(binding.editStatus.windowToken, InputMethodManager.HIDE_NOT_ALWAYS);
        binding.editStatus.text.clear()
        binding.editStatus.tag = ""
        behavior.state = BottomSheetBehavior.STATE_COLLAPSED
    }

    fun onPostSuccess() {
        closeBottomSheet()
        mTimeLinePagerAdapter.getCurrentFragment(mViewPager,mViewPager.currentItem).loadTimeLineBySinceId()
    }

    override fun onClickReply(status: Status) {
        openBottomSheet(status)
    }

    fun bindDataDrawView(user: User) {
        val headerImageView = drawer.findViewById(R.id.headerImage) as ImageView
        val userImageView = drawer.findViewById(R.id.userImageView) as ImageView
        val textView = drawer.findViewById(R.id.userNameText) as TextView
        val screenName = drawer.findViewById(R.id.userScreenNameText) as TextView
        Picasso.with(this).load(user.profileImageUrl).into(userImageView)
        Picasso.with(this).load(user.coverImageUrl).fit().into(headerImageView)
        textView.text = user.name
        screenName.text = user.screenName

    }

    fun ((Context) -> Intent).start() {
        startActivityForResult(this(applicationContext), REQUEST_CODE_AUTH)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_CODE_AUTH && resultCode == RESULT_OK) {
        }
    }

    override fun onBackPressed() {
        val drawer = findViewById(R.id.drawer_layout) as DrawerLayout
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START)
        } else if(behavior.state == BottomSheetBehavior.STATE_EXPANDED){
            behavior.state = BottomSheetBehavior.STATE_COLLAPSED
        } else {
            super.onBackPressed()
        }
    }

//    override fun onCreateOptionsMenu(menu: Menu): Boolean {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        menuInflater.inflate(R.menu.main, menu)
//        return true
//    }

//    override fun onOptionsItemSelected(item: MenuItem): Boolean {
//        val id = item.itemId
//
//        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings) {
//            return true
//        }
//
//        return super.onOptionsItemSelected(item)
//    }

    @SuppressWarnings("StatementWithEmptyBody")
    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        // Handle navigation view item clicks here.
//        val id = item.itemId

//        if (id == R.id.nav_camera) {
//            // Handle the camera action
//        } else if (id == R.id.nav_gallery) {
//
//        } else if (id == R.id.nav_slideshow) {
//
//        } else if (id == R.id.nav_manage) {
//
//        } else if (id == R.id.nav_share) {
//
//        } else if (id == R.id.nav_send) {
//
//        }
        drawer.closeDrawer(GravityCompat.START)
        return true
    }

}
