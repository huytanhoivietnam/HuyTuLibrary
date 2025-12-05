package com.dk.huytulibrary

import com.dk.mylibrary.activity.language.BaseLanguageActivity
import com.dk.mylibrary.activity.language.LanguageConfig

class LanguageActivity(languageConfig: LanguageConfig = LanguageConfig(
        doneButtonTextColor = "#FFFFFF",
        doneButtonBackgroundColor = "#F43D24",
        titleText = "Language",
        selectedLanguageSolidColor = "#FFECEC",
        selectedLanguageStrokeColor = "#F43D24",
        selectedLanguageStrokeWidth = 1f,
        unselectedLanguageSolidColor = "#F5F5F5",
        cornerRadius = 12f,
        radioButtonCheckedColor = "#F43D24",
        radioButtonUncheckedColor = "#D9D9D9"
    )
) : BaseLanguageActivity(languageConfig) {
    
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