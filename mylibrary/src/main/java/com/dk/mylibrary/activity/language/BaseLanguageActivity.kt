package com.dk.mylibrary.activity.language

import android.Manifest
import android.content.pm.PackageManager
import android.content.res.ColorStateList
import android.graphics.Typeface
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
import java.util.Locale

abstract class BaseLanguageActivity : BaseActivity<ActivityLanguageBinding>(ActivityLanguageBinding::inflate) {

    companion object {
        private const val PERMISSION_REQUEST_CODE = 100
        private const val EXTRA_FROM_SPLASH = "fromSplash"
        private const val DEFAULT_LANGUAGE_NAME = "English"
        private const val INVALID_POSITION = -1
        
        val languages = listOf(
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

    private var language: String = ""
    private var flag = R.drawable.ic_english
    private var pos = INVALID_POSITION
    private var adapter: LanguageAdapter? = null
    private var languageName = DEFAULT_LANGUAGE_NAME
    private var isProcessingLanguageChange = false
    private var hasBackgroundDone = false
    private var backgroundDoneEnabled: Int? = null
    private var backgroundDoneDisabled: Int? = null
    private val doneButtonTextColorEnabled by lazy {
        ContextCompat.getColor(this, R.color.lib_done_button_text_color)
    }
    private val doneButtonBackgroundColorEnabled by lazy {
        ContextCompat.getColor(this, R.color.lib_done_button_background_color)
    }
    private val doneButtonTextColorDisabled by lazy {
        ContextCompat.getColor(this, R.color.lib_done_button_text_color_disabled)
    }
    private val doneButtonBackgroundColorDisabled by lazy {
        ContextCompat.getColor(this, R.color.lib_done_button_background_color_disabled)
    }
    
    val fromSplash by lazy { 
        intent.getBooleanExtra(EXTRA_FROM_SPLASH, false) 
    }

    abstract fun nextToIntro()
    abstract fun nextToHome()
    abstract fun reloadNativeFirstClick()
    abstract fun showNativeId1()
    abstract fun loadAdsIntro()
    open fun getLanguageBuilder(): LanguageBuilder = LanguageBuilder()

    override fun initViewBase() {
        val builder = getLanguageBuilder()
        LanguageAdapter.Companion.selected = INVALID_POSITION
        loadSavedLanguagePreferences()
        setupUIWithBuilder(builder)
        setupBackButton()
        setupLanguageRecyclerView(builder)
        setupDoneButton()
        showNativeId1()
    }

    private fun loadSavedLanguagePreferences() {
        flag = Common.getPreLanguageflag(this)
        language = Common.getLang(this)?.toString() ?: languages.first().key
        languageName = Common.getLangName(this)?.toString() ?: languages.first().name
        pos = Common.getPosition(this)
        if (pos < 0 || pos >= languages.size) {
            pos = INVALID_POSITION
        }
    }

    private fun setupUIWithBuilder(builder: LanguageBuilder) {
        binding.txtname.text = builder.titleText
        
        builder.fontTitle?.let { fontRes ->
            try {
                binding.txtname.typeface = resources.getFont(fontRes)
            } catch (e: Exception) {
                binding.txtname.typeface = resources.getFont(R.font.inter_semibold)
            }
        }
        
        builder.textSizeTitle?.let { textSize ->
            binding.txtname.textSize = textSize
        }
        
        builder.fontDone?.let { fontRes ->
            try {
                binding.done.typeface = resources.getFont(fontRes)
            } catch (e: Exception) {
                binding.txtname.typeface = resources.getFont(R.font.inter_medium)
            }
        }
        
        builder.textSizeDone?.let { textSize ->
            binding.done.textSize = textSize
        }
        
        builder.backgroundDone?.let { 
            backgroundDoneEnabled = it
            hasBackgroundDone = true
        }
        
        builder.backgroundDoneDisabled?.let {
            backgroundDoneDisabled = it
            hasBackgroundDone = true
        }
        
        builder.backgroundScreen?.let { 
            binding.main.setBackgroundResource(it) 
        }
        
        builder.backArrowDrawable?.let {
            binding.backArrow.setImageResource(it)
        }
    }

    private fun setupBackButton() {
        if (fromSplash) {
            checkNotificationPermission()
            binding.backArrow.visibility = View.GONE
            updateDoneButtonState(enabled = false)
            loadAdsIntro()
        } else {
            binding.backArrow.visibility = View.VISIBLE
            updateDoneButtonState(enabled = pos != INVALID_POSITION)
            binding.backArrow.setOnClickListener {
                finish()
            }
        }
    }

    private fun checkNotificationPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(
                    this,
                    Manifest.permission.POST_NOTIFICATIONS
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                ActivityCompat.requestPermissions(
                    this,
                    arrayOf(Manifest.permission.POST_NOTIFICATIONS),
                    PERMISSION_REQUEST_CODE
                )
            }
        }
    }

    private fun updateDoneButtonState(enabled: Boolean) {
        if (enabled) {
            binding.done.setTextColor(doneButtonTextColorEnabled)
            if (hasBackgroundDone) {
                backgroundDoneEnabled?.let {
                    binding.done.setBackgroundResource(it)
                }
            } else {
                binding.done.backgroundTintList = ColorStateList.valueOf(doneButtonBackgroundColorEnabled)
            }
            binding.done.isEnabled = true
        } else {
            binding.done.setTextColor(doneButtonTextColorDisabled)
            if (hasBackgroundDone) {
                backgroundDoneDisabled?.let {
                    binding.done.setBackgroundResource(it)
                } ?: backgroundDoneEnabled?.let {
                    binding.done.setBackgroundResource(it)
                }
            } else {
                binding.done.backgroundTintList = ColorStateList.valueOf(doneButtonBackgroundColorDisabled)
            }
            binding.done.isEnabled = false
        }
    }

    private fun setupDoneButton() {
        binding.done.setOnClickListener {
            handleLanguageSelection()
        }
    }

    private fun handleLanguageSelection() {
        if (isProcessingLanguageChange) return
        
        if (LanguageAdapter.Companion.selected == INVALID_POSITION) {
            showLanguageSelectionRequiredMessage()
            return
        }
        
        if (pos < 0 || pos >= languages.size) {
            showLanguageSelectionRequiredMessage()
            return
        }
        
        isProcessingLanguageChange = true
        binding.done.isEnabled = false
        binding.done.isClickable = false
        
        saveLanguagePreferences()
        
        if (fromSplash) {
            nextToIntro()
        } else {
            nextToHome()
        }
    }

    private fun saveLanguagePreferences() {
        Common.setLang(this, language)
        Common.setPosition(this, pos)
        Common.setPreLanguageflag(this, flag)
        Common.setLangName(this, languageName)
    }

    private fun showLanguageSelectionRequiredMessage() {
        Toast.makeText(this, "Please choose your language!", Toast.LENGTH_SHORT).show()
    }

    private fun setupLanguageRecyclerView(builder: LanguageBuilder) {
        adapter = LanguageAdapter(
            object : LanguageAdapter.OnClickListener {
                override fun onClickListener(position: Int, name: String, img: Int) {
                    handleLanguageItemClick(position, name, img, builder)
                }
            },
            this,
            builder.selectedLanguageDrawable,
            builder.unselectedLanguageDrawable,
            builder.fontLanguageName,
            builder.textSizeLanguageName
        )

        adapter?.updateData(ArrayList(languages))
        adapter?.updatePosition(pos)
        
        binding.rcvLanguage.apply {
            layoutManager = LinearLayoutManager(
                this@BaseLanguageActivity,
                LinearLayoutManager.VERTICAL,
                false
            )
            adapter = this@BaseLanguageActivity.adapter
        }
    }

    private fun handleLanguageItemClick(
        position: Int,
        name: String,
        img: Int,
        builder: LanguageBuilder
    ) {
        if (pos == INVALID_POSITION) {
            reloadNativeFirstClick()
        }
        
        pos = position
        language = name
        languageName = languages[position].name
        flag = img
        
        updateDoneButtonState(enabled = true)
        adapter?.updatePosition(pos)
    }

    override fun onDestroy() {
        adapter = null
        super.onDestroy()
    }
}