package com.dk.mylibrary.activity.language

class LanguageBuilder {
    var titleText: String = "Select Language"
    var selectedLanguageDrawable: Int? = null
    var unselectedLanguageDrawable: Int? = null
    var backgroundScreen: Int? = null
    var fontTitle: Int? = null
    var fontDone: Int? = null
    var fontLanguageName: Int? = null
    var textSizeTitle: Float? = null
    var textSizeDone: Float? = null
    var textSizeLanguageName: Float? = null
    var backgroundDone: Int? = null
    var backgroundDoneDisabled: Int? = null
    var backArrowDrawable: Int? = null

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

    fun setTextSizeTitle(textSize: Float): LanguageBuilder {
        this.textSizeTitle = textSize
        return this
    }

    fun setTextSizeDone(textSize: Float): LanguageBuilder {
        this.textSizeDone = textSize
        return this
    }

    fun setTextSizeLanguageName(textSize: Float): LanguageBuilder {
        this.textSizeLanguageName = textSize
        return this
    }

    fun setBackgroundDone(drawableRes: Int): LanguageBuilder {
        this.backgroundDone = drawableRes
        return this
    }

    fun setBackgroundDoneDisabled(drawableRes: Int): LanguageBuilder {
        this.backgroundDoneDisabled = drawableRes
        return this
    }

    fun setBackArrowDrawable(drawableRes: Int): LanguageBuilder {
        this.backArrowDrawable = drawableRes
        return this
    }
}

