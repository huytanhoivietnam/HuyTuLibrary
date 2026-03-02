package com.dk.mylibrary.custom_view

import android.content.Context
import android.graphics.LinearGradient
import android.graphics.Shader
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatTextView
import com.dk.mylibrary.R

class GradientTextView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = android.R.attr.textViewStyle
) : AppCompatTextView(context, attrs, defStyleAttr) {

    private var startColor: Int = currentTextColor
    private var endColor: Int = currentTextColor
    private var orientation: GradientOrientation =
        GradientOrientation.LEFT_TO_RIGHT

    private var isGradientEnabled = false

    init {
        context.theme.obtainStyledAttributes(
            attrs,
            R.styleable.GradientTextView,
            0, 0
        ).apply {
            try {
                startColor = getColor(
                    R.styleable.GradientTextView_gtv_startColor,
                    currentTextColor
                )

                endColor = getColor(
                    R.styleable.GradientTextView_gtv_endColor,
                    currentTextColor
                )

                val orientationIndex = getInt(
                    R.styleable.GradientTextView_gtv_orientation,
                    0
                )

                orientation = GradientOrientation.values()
                    .getOrElse(orientationIndex) {
                        GradientOrientation.LEFT_TO_RIGHT
                    }

                isGradientEnabled = startColor != endColor

            } finally {
                recycle()
            }
        }
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        applyShader(w, h)
    }

    override fun onTextChanged(
        text: CharSequence?,
        start: Int,
        lengthBefore: Int,
        lengthAfter: Int
    ) {
        super.onTextChanged(text, start, lengthBefore, lengthAfter)
        post { applyShader(width, height) }
    }

    private fun applyShader(w: Int, h: Int) {
        if (!isGradientEnabled) return
        if (w <= 0 || h <= 0) return

        val shader = when (orientation) {
            GradientOrientation.LEFT_TO_RIGHT ->
                LinearGradient(0f, 0f, w.toFloat(), 0f,
                    startColor, endColor, Shader.TileMode.CLAMP)

            GradientOrientation.RIGHT_TO_LEFT ->
                LinearGradient(w.toFloat(), 0f, 0f, 0f,
                    startColor, endColor, Shader.TileMode.CLAMP)

            GradientOrientation.TOP_TO_BOTTOM ->
                LinearGradient(0f, 0f, 0f, h.toFloat(),
                    startColor, endColor, Shader.TileMode.CLAMP)

            GradientOrientation.BOTTOM_TO_TOP ->
                LinearGradient(0f, h.toFloat(), 0f, 0f,
                    startColor, endColor, Shader.TileMode.CLAMP)
        }

        paint.shader = shader
    }

    fun setGradientColors(
        startColor: Int,
        endColor: Int,
        orientation: GradientOrientation = GradientOrientation.LEFT_TO_RIGHT
    ) {
        this.startColor = startColor
        this.endColor = endColor
        this.orientation = orientation
        this.isGradientEnabled = true

        applyShader(width, height)
    }

    fun setSolidTextColor(color: Int) {
        isGradientEnabled = false
        paint.shader = null
        setTextColor(color)
    }
}

/**  ========== Example =========
        binding.textView.setGradientColors("#F80000".toColorInt(),"#004EFF".toColorInt())
        binding.textView.setSolidTextColor("#F80000".toColorInt())
 */