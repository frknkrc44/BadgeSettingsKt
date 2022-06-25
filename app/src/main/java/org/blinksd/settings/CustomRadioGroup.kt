package org.blinksd.settings

import android.content.Context
import android.content.res.TypedArray
import android.graphics.drawable.GradientDrawable
import android.util.AttributeSet
import android.widget.RadioGroup

class CustomRadioGroup: RadioGroup {
    constructor(ctx: Context) : super(ctx) {
        applyStyle()
    }

    constructor(ctx: Context, attrs: AttributeSet) : super(ctx, attrs) {
        applyStyle()
    }

    private fun applyStyle() {
        val arr: TypedArray = context.obtainStyledAttributes(0, intArrayOf(android.R.attr.colorBackground))
        val bgColor = ((arr.getColor(0, context.getColor(android.R.color.tab_indicator_text)) xor 0xffffff) - 0xee000000).toInt()
        arr.recycle()
        val disabledBg = GradientDrawable()
        disabledBg.colors = intArrayOf(bgColor, bgColor)
        disabledBg.cornerRadius = resources.displayMetrics.density * 32
        background = disabledBg
        val pad = disabledBg.cornerRadius.toInt() / 2
        setPadding(pad, pad, pad, pad)
    }
}