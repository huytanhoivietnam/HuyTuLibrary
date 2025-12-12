package com.dk.huytulibrary

import com.dk.mylibrary.activity.language.BaseLanguageActivity
import com.dk.mylibrary.activity.language.LanguageBuilder
import com.dk.mylibrary.utils.addActivity
import com.dk.mylibrary.utils.replaceActivity

class LanguageActivity : BaseLanguageActivity() {
    
    override fun getLanguageBuilder(): LanguageBuilder {
        return LanguageBuilder()
            .setTitleText("Language")
            .setFontTitle(R.font.happy_school)
            .setFontLanguageName(R.font.happy_school)
    }
    
    override fun nextToIntro() {
        addActivity <MainActivity>{
            putExtra("fromSplash", true)
        }
    }

    override fun nextToHome() {
        replaceActivity<MainActivity>{
            putExtra("fromSplash", true)
        }
    }

    override fun reloadNativeFirstClick() {

    }

    override fun showNativeId1() {

    }

    override fun loadAdsIntro() {

    }
}