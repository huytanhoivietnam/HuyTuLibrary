package com.dk.mylibrary.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.activity.addCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.viewbinding.ViewBinding
import com.dk.mylibrary.dialog.InternetErrorDialog
import com.dk.mylibrary.utils.Common
import com.dk.mylibrary.utils.NetworkUtils
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import java.util.Locale

abstract class BaseActivity<VB : ViewBinding>(val inflater: (LayoutInflater) -> VB) :
    AppCompatActivity() {
    val binding: VB by lazy { inflater(layoutInflater) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setLocal()
        hideSystemUI()
        setContentView(binding.root)
        onBackPressedDispatcher.addCallback(this) {
            callBackPress()
        }
        setupNetworkMonitoring()
        initViewBase()
    }

    private val internetErrorDialog by lazy {
        InternetErrorDialog(mContext = this)
    }

    abstract fun initViewBase()
    open fun callBackPress() {
        finish()
    }

    override fun onStart() {
        super.onStart()
    }

    override fun onWindowFocusChanged(hasFocus: Boolean) {
        super.onWindowFocusChanged(hasFocus)
        hideSystemUI()
    }

    override fun onPause() {
        super.onPause()
    }

    override fun onResume() {
        super.onResume()
        setLocal()
    }

    private fun hideSystemUI() {
        window.decorView.systemUiVisibility = (View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                or View.SYSTEM_UI_FLAG_FULLSCREEN
                or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY)
    }

    private fun setLocal() {
        var langCode = Common.getLang(this)
        val locale = Locale(langCode)
        Locale.setDefault(locale)
        val resource = resources
        val configuration = resource.configuration
        configuration.setLocale(locale)
        configuration.setLayoutDirection(Locale("en"))
        resource.updateConfiguration(configuration, resource.displayMetrics)
    }

    open fun shouldShowInternetDialog(): Boolean = true

    private fun setupNetworkMonitoring() {
        if (!shouldShowInternetDialog()) return
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED){
                NetworkUtils.observeNetworkState(this@BaseActivity).collectLatest { isConnected ->
                    if (isConnected) {
                        if(internetErrorDialog.isShowing){
                            internetErrorDialog.dismiss()
                        }
                    } else {
                        showInternetDialog()
                    }
                }
            }
        }
    }

    private fun showInternetDialog() {
        internetErrorDialog.show()
    }

}

