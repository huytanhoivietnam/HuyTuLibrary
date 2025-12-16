package com.dk.mylibrary.dialog

import android.app.Dialog
import android.content.Context
import android.content.Intent
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
        val panelIntent = Intent(Settings.Panel.ACTION_INTERNET_CONNECTIVITY)
        mContext.startActivity(panelIntent)
    }


    override fun getViewBinding(): DialogInternetConnectBinding {
        return DialogInternetConnectBinding.inflate(layoutInflater)
    }
}