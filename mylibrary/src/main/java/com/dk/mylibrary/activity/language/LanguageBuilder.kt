package com.dk.mylibrary.activity.language

class LanguageBuilder {
    var titleText: String = "Select Language"
    var selectedLanguageDrawable: Int? = null
    var unselectedLanguageDrawable: Int? = null
    var backgroundScreen: Int? = null
    var fontTitle: Int? = null
    var fontDone: Int? = null
    var fontLanguageName: Int? = null

    fun setTitleText(text: String): LanguageBuilder {
        this.titleText = text
        return this
    }

    fun setSelectedLanguageDrawable(drawableRes: Int): LanguageBuilder {
        this.selectedLanguageDrawable = drawableRes
        return this
    }

    fun setUnselectedLanguageDrawable(drawableRes: Int): LanguageBuilder {
        this.unselectedLanguageDrawable = drawableRes
        return this
    }

    fun setBackgroundScreen(drawableRes: Int): LanguageBuilder {
        this.backgroundScreen = drawableRes
        return this
    }

    fun setFontTitle(fontFamilyRes: Int): LanguageBuilder {
        this.fontTitle = fontFamilyRes
        return this
    }

    fun setFontDone(fontFamilyRes: Int): LanguageBuilder {
        this.fontDone = fontFamilyRes
        return this
    }

    fun setFontLanguageName(fontFamilyRes: Int): LanguageBuilder {
        this.fontLanguageName = fontFamilyRes
        return this
    }
}

