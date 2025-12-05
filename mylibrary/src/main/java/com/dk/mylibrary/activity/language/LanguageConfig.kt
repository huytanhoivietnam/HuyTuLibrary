package com.dk.mylibrary.activity.language

data class LanguageConfig(
    val doneButtonTextColor: String = "#FFFFFF",
    val doneButtonBackgroundColor: String = "#298666",
    val titleText: String = "Select Language",
    val selectedLanguageDrawable: Int? = null,
    val unselectedLanguageDrawable: Int? = null,
    val selectedLanguageSolidColor: String? = null,
    val selectedLanguageStrokeColor: String? = null,
    val selectedLanguageStrokeWidth: Float = 1f,
    val unselectedLanguageSolidColor: String? = null,
    val cornerRadius: Float = 12f,
    val radioButtonCheckedColor: String? = null,
    val radioButtonUncheckedColor: String? = null
)

