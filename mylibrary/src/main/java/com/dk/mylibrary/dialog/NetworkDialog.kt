package com.dk.mylibrary.dialog

import android.content.Context
import android.content.Intent
import android.os.Build
import android.provider.Settings
import com.dk.mylibrary.base.BaseDialog
import com.dk.mylibrary.databinding.DialogInternetConnectBinding
import com.dk.mylibrary.utils.NetworkUtils

class InternetErrorDialog(
    private val mContext: Context
) : BaseDialog<DialogInternetConnectBinding>(mContext){


    override fun initViews() {
        setCancelable(false)
        binding.btnSet.setOnClickListener {
            if (NetworkUtils.isNetworkConnected(mContext)) {
                dismiss()
            } else {
                openNetworkSettings()
            }
        }
    }

    private fun openNetworkSettings() {
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                val panelIntent = Intent(Settings.Panel.ACTION_INTERNET_CONNECTIVITY)
                mContext.startActivity(panelIntent)
            } else {
                val intent = Intent(Settings.ACTION_WIFI_SETTINGS)
                mContext.startActivity(intent)
            }
        } catch (e: Exception) {
            val intent = Intent(Settings.ACTION_SETTINGS)
            mContext.startActivity(intent)
        }
    }


    override fun getViewBinding(): DialogInternetConnectBinding {
        return DialogInternetConnectBinding.inflate(layoutInflater)
    }
}