package com.dk.mylibrary.activity.language

import android.Manifest
import android.content.pm.PackageManager
import android.content.res.ColorStateList
import android.os.Build
import android.view.View
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.dk.mylibrary.R
import com.dk.mylibrary.base.BaseActivity
import com.dk.mylibrary.databinding.ActivityLanguageBinding
import com.dk.mylibrary.utils.Common

abstract class BaseLanguageActivity : BaseActivity<ActivityLanguageBinding>(ActivityLanguageBinding::inflate) {

    lateinit var language: String
    var flag = R.drawable.ic_english
    var pos = -1
    var adapter: LanguageAdapter? = null
    var languageName = "English"
    val fromSplash by lazy { intent.getBooleanExtra("fromSplash", false) }

    abstract fun nextToIntro()
    abstract fun nextToHome()
    abstract fun reloadNativeFirstClick()
    abstract fun showNativeId1()
    abstract fun loadAdsIntro()
    open fun getLanguageBuilder(): LanguageBuilder = LanguageBuilder()

    override fun initViewBase() {
        val builder = getLanguageBuilder()
        LanguageAdapter.Companion.selected = -1
        flag = Common.getPreLanguageflag(this)
        language = Common.getLang(this).toString()
        languageName = Common.getLangName(this).toString()
        binding.txtname.text = builder.titleText
        if (builder.fontTitle != null) {
            binding.txtname.typeface = resources.getFont(builder.fontTitle!!)
        }
        if (builder.fontDone != null) {
            binding.done.typeface = resources.getFont(builder.fontDone!!)
        }
        if(builder.backgroundScreen != null) {
            binding.main.setBackgroundResource(builder.backgroundScreen!!)
        }
        getBack()
        getLanguage(builder)
        changeLanguageDone()
        showNativeId1()
    }

    private fun getBack() {
        if (fromSplash) {
            checkNotification()
            binding.backArrow.visibility = View.GONE
            binding.done.setTextColor(ContextCompat.getColor(this, R.color.lib_done_button_text_color_disabled))
            binding.done.backgroundTintList = ColorStateList.valueOf(ContextCompat.getColor(this, R.color.lib_done_button_background_color_disabled))
            loadAdsIntro()
        } else {
            pos = Common.getPosition(this)
            binding.backArrow.visibility = View.VISIBLE
            // Use colors from resources (có thể override trong app module)
            binding.done.setTextColor(ContextCompat.getColor(this, R.color.lib_done_button_text_color))
            binding.done.backgroundTintList = ColorStateList.valueOf(ContextCompat.getColor(this, R.color.lib_done_button_background_color))
            binding.backArrow.setOnClickListener {
                finish()
            }
        }
    }

    private fun checkNotification() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(
                    this,
                    Manifest.permission.POST_NOTIFICATIONS
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                ActivityCompat.requestPermissions(
                    this,
                    arrayOf(Manifest.permission.POST_NOTIFICATIONS), 100
                )
            }
        }
    }

    override fun onResume() {
        super.onResume()

    }

    private fun changeLanguageDone() {
        binding.done.setOnClickListener {
            if (LanguageAdapter.Companion.selected != -1) {
                binding.done.isEnabled = false
                binding.done.isClickable = false
                Common.setLang(this, language)
                Common.setPosition(this, pos)
                Common.setPreLanguageflag(this, flag)
                Common.setLangName(this,languageName)
                if (fromSplash) {
                    nextToIntro()
                } else {
                    nextToHome()
                }
            } else {
                Toast.makeText(this, "Please choose your language!", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun getLanguage(builder: LanguageBuilder) {
        val languageList = languages

        adapter = LanguageAdapter(
            object : LanguageAdapter.OnClickListener {
                override fun onClickListener(position: Int, name: String, img: Int) {
                    if (pos == -1) {
                        reloadNativeFirstClick()
                    }
                    binding.done.setTextColor(ContextCompat.getColor(this@BaseLanguageActivity, R.color.lib_done_button_text_color))
                    binding.done.backgroundTintList = ColorStateList.valueOf(ContextCompat.getColor(this@BaseLanguageActivity, R.color.lib_done_button_background_color))
                    pos = position
                    language = name
                    languageName = languageList[position].name
                    flag = img
                    adapter?.updatePosition(pos)
                }
            }, 
            this,
            builder.selectedLanguageDrawable,
            builder.unselectedLanguageDrawable,
            builder.fontLanguageName
        )

        adapter?.updateData(languageList)
        adapter?.updatePosition(pos)
        binding.rcvLanguage.apply {
            layoutManager = LinearLayoutManager(
                this@BaseLanguageActivity,
                LinearLayoutManager.VERTICAL,
                false
            )
        }
        binding.rcvLanguage.adapter = adapter
    }

    override fun onDestroy() {
        super.onDestroy()
    }

    companion object {
        var languages = arrayListOf(
            Language(R.drawable.ic_english, "English", "en"),
            Language(R.drawable.ic_hindi, "हिंदी", "hi"),
            Language(R.drawable.ic_spanish, "Español", "es"),
            Language(R.drawable.ic_french, "Français", "fr"),
            Language(R.drawable.ic_arabic, "عربي", "ar"),
            Language(R.drawable.ic_bengali, "বাংলা", "bn"),
            Language(R.drawable.ic_russian, "Русский", "ru"),
            Language(R.drawable.ic_portuguese, "Português", "pt"),
            Language(R.drawable.ic_indonesian, "Bahasa Indonesia", "in"),
            Language(R.drawable.ic_german, "Deutsch", "de"),
            Language(R.drawable.ic_italian, "Italiano", "it"),
            Language(R.drawable.ic_korean, "한국어", "ko")
        )

    }

}