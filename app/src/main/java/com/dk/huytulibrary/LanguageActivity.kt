package com.dk.huytulibrary

import com.dk.mylibrary.activity.language.BaseLanguageActivity
import com.dk.mylibrary.activity.language.LanguageBuilder

class LanguageActivity : BaseLanguageActivity() {
    
    override fun getLanguageBuilder(): LanguageBuilder {
        return LanguageBuilder()
            .setTitleText("Language")
            .setFontTitle(R.font.happy_school)
            .setFontLanguageName(R.font.happy_school)
    }
    
    override fun nextToIntro() {
        finish()
    }

    override fun nextToHome() {
        finish()
    }

    override fun reloadNativeFirstClick() {

    }

    override fun showNativeId1() {

    }

    override fun loadAdsIntro() {

    }
}