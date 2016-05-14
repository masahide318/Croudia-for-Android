package t.masahide.android.croudia.ui.activity

import android.content.Context
import android.content.Intent
import android.databinding.DataBindingUtil
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.webkit.WebView
import android.webkit.WebViewClient
import com.trello.rxlifecycle.components.support.RxAppCompatActivity
import t.masahide.android.croudia.presenter.AuthPresenter
import t.masahide.android.croudia.R
import t.masahide.android.croudia.constant.CroudiaConstants
import t.masahide.android.croudia.databinding.ActivityAuthBinding

/**
 * Created by Masahide on 2016/03/12.
 */

class AuthActivity: RxAppCompatActivity(){


    val authPresenter = AuthPresenter(this)

    val binding by lazy{
        DataBindingUtil.setContentView<ActivityAuthBinding>(this, R.layout.activity_auth)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.authWebView.scrollBarStyle = View.SCROLLBARS_INSIDE_OVERLAY
        val webSettings = binding.authWebView.settings
        webSettings.javaScriptEnabled = true
        webSettings.useWideViewPort = true

        val webViewClient = object : WebViewClient() {
            override fun shouldOverrideUrlLoading(view: WebView?, url: String?): Boolean {
                var result = super.shouldOverrideUrlLoading(view, url)
                val uri = Uri.parse(url)
                if(uri.scheme == "crouid"){
                    authPresenter.auth(uri.getQueryParameter("code"))
                    return true
                }
                return result
            }

            override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
                super.onPageStarted(view, url, favicon)
            }

            override fun onPageFinished(view: WebView?, url: String?) {
                super.onPageFinished(view, url)
            }
        }
        binding.authWebView.setWebViewClient(webViewClient)
        binding.authWebView.loadUrl(CroudiaConstants.AUTH_URL)
    }

    fun ((Context) -> Intent).start() {
        startActivity(this(applicationContext))
    }
    companion object {
        fun intent() = {
            c: Context ->
            Intent(c, AuthActivity::class.java)
        }
    }
}