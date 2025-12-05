package com.dk.mylibrary.activity.language

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.drawable.GradientDrawable
import android.graphics.drawable.LayerDrawable
import android.graphics.drawable.StateListDrawable
import android.util.TypedValue
import androidx.core.graphics.toColorInt
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.dk.mylibrary.R
import com.dk.mylibrary.databinding.RcvLanguageBinding

class LanguageAdapter(
    val onClickListener: OnClickListener,
    val context: Context,
    private val languageConfig: LanguageConfig
) : RecyclerView.Adapter<LanguageAdapter.ViewHolder>() {

    companion object {
        var selected = -1
    }

    var languageList: ArrayList<Language> = ArrayList()

    inner class ViewHolder(val binding: RcvLanguageBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            RcvLanguageBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    interface OnClickListener {
        fun onClickListener(position: Int, name: String, img: Int)
    }

    @SuppressLint("NotifyDataSetChanged")
    fun updateData(data: ArrayList<Language>) {
        languageList.clear()
        languageList.addAll(data)
        notifyDataSetChanged()
    }

    @SuppressLint("NotifyDataSetChanged")
    fun updatePosition(position: Int) {
        selected = position
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = languageList[position]
        val b = holder.binding

        // --- Selected UI ---
        if (selected == position) {
            if (languageConfig.selectedLanguageDrawable != null) {
                b.language.setBackgroundResource(languageConfig.selectedLanguageDrawable)
            } else if (languageConfig.selectedLanguageSolidColor != null) {
                b.language.background = createDrawable(
                    solidColor = languageConfig.selectedLanguageSolidColor,
                    strokeColor = languageConfig.selectedLanguageStrokeColor,
                    strokeWidth = languageConfig.selectedLanguageStrokeWidth,
                    cornerRadius = languageConfig.cornerRadius
                )
            } else {
                b.language.setBackgroundResource(R.drawable.btn_select_language)
            }
            b.checkbox.isChecked = true

        } else {
            if (languageConfig.unselectedLanguageDrawable != null) {
                b.language.setBackgroundResource(languageConfig.unselectedLanguageDrawable)
            } else if (languageConfig.unselectedLanguageSolidColor != null) {
                b.language.background = createDrawable(
                    solidColor = languageConfig.unselectedLanguageSolidColor,
                    strokeColor = null,
                    strokeWidth = 0f,
                    cornerRadius = languageConfig.cornerRadius
                )
            } else {
                b.language.setBackgroundResource(R.drawable.btn_unselect_language)
            }
            b.checkbox.isChecked = false
        }

        // --- Set icon + text ---
        b.languageIcon.setImageResource(item.img)
        b.languageName.text = item.name
        b.languageName.setHorizontallyScrolling(true)
        b.languageName.isSelected = true

        if (languageConfig.radioButtonCheckedColor != null ||
            languageConfig.radioButtonUncheckedColor != null
        ) {
            b.checkbox.background = createRadioButtonDrawable()
        }

        holder.itemView.setOnClickListener {
            onClickListener.onClickListener(position, item.key, item.img)
        }
    }

    override fun getItemCount(): Int = languageList.size


    // ----------------------------
    // Drawable Functions
    // ----------------------------

    private fun createDrawable(
        solidColor: String,
        strokeColor: String?,
        strokeWidth: Float,
        cornerRadius: Float
    ): GradientDrawable {
        val drawable = GradientDrawable()
        drawable.shape = GradientDrawable.RECTANGLE
        drawable.setColor(solidColor.toColorInt())

        if (strokeColor != null && strokeWidth > 0) {
            val strokeWidthPx = TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP,
                strokeWidth,
                context.resources.displayMetrics
            ).toInt()
            drawable.setStroke(strokeWidthPx, strokeColor.toColorInt())
        }

        drawable.cornerRadius = TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP,
            cornerRadius,
            context.resources.displayMetrics
        )

        return drawable
    }

    private fun createRadioButtonDrawable(): StateListDrawable {
        val stateListDrawable = StateListDrawable()

        val checkedColor = languageConfig.radioButtonCheckedColor ?: "#45B454"
        val uncheckedColor = languageConfig.radioButtonUncheckedColor ?: "#D9D9D9"

        val strokeWidthPx = TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP,
            1f,
            context.resources.displayMetrics
        ).toInt()

        // Outer circle
        val outer = GradientDrawable().apply {
            shape = GradientDrawable.OVAL
            setStroke(strokeWidthPx, checkedColor.toColorInt())
            setColor(android.graphics.Color.TRANSPARENT)
        }

        // Inner circle
        val inner = GradientDrawable().apply {
            shape = GradientDrawable.OVAL
            setColor(checkedColor.toColorInt())
        }

        val checkedDrawable = LayerDrawable(arrayOf(outer, inner)).apply {
            val sizePx = TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP, 17f, context.resources.displayMetrics
            ).toInt()
            val innerSizePx = TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP, 8f, context.resources.displayMetrics
            ).toInt()
            val inset = (sizePx - innerSizePx) / 2
            setLayerInset(1, inset, inset, inset, inset)
        }

        val uncheckedDrawable = GradientDrawable().apply {
            shape = GradientDrawable.OVAL
            setStroke(strokeWidthPx, uncheckedColor.toColorInt())
            setColor(android.graphics.Color.TRANSPARENT)
        }

        stateListDrawable.addState(intArrayOf(android.R.attr.state_checked), checkedDrawable)
        stateListDrawable.addState(intArrayOf(-android.R.attr.state_checked), uncheckedDrawable)
        stateListDrawable.addState(intArrayOf(), uncheckedDrawable)

        return stateListDrawable
    }
}
