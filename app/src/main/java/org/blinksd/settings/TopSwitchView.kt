package org.blinksd.settings

import android.R
import android.content.Context
import android.content.res.ColorStateList
import android.content.res.TypedArray
import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.graphics.drawable.StateListDrawable
import android.os.Build
import android.util.AttributeSet
import android.widget.CompoundButton
import androidx.appcompat.widget.SwitchCompat


class TopSwitchView: SwitchCompat {
    constructor(ctx: Context) : super(ctx) {
        applyStyle()
    }

    constructor(ctx: Context, attrs: AttributeSet) : super(ctx, attrs) {
        applyStyle()
    }

    constructor(ctx: Context, attrs: AttributeSet, defA: Int) : super(ctx, attrs, defA) {
        applyStyle()
    }

    companion object {

    }

    private fun applyStyle() {
        val disabledColor = (context.getColor(android.R.color.tab_indicator_text) xor 0x00ffffff) - 0x44000000
        val accentColor = SystemUtils.getAccentColor(context, true)

        val state1 = intArrayOf(android.R.attr.state_enabled, android.R.attr.state_checked)
        val state2 = intArrayOf(android.R.attr.state_enabled)
        val thumbColor = Color.WHITE
        val trackColor = thumbColor - 0x66000000
        thumbTintList = ColorStateList.valueOf(thumbColor)
        trackTintList = ColorStateList.valueOf(trackColor)

        val gradientBg = GradientDrawable()
        gradientBg.colors = intArrayOf(accentColor, accentColor)
        gradientBg.cornerRadius = 100f
        val disabledBg = GradientDrawable()
        disabledBg.colors = intArrayOf(disabledColor, disabledColor)
        disabledBg.cornerRadius = gradientBg.cornerRadius
        val stateDrawable = StateListDrawable()
        stateDrawable.addState(state1, gradientBg)
        stateDrawable.addState(state2, disabledBg)
        background = stateDrawable

        val pad = (resources.displayMetrics.density * 24).toInt()
        setPadding(pad, pad, pad, pad)

        setTextAppearance(android.R.style.TextAppearance_DeviceDefault_Medium_Inverse)
        setTextColor(thumbColor)
    }
}
