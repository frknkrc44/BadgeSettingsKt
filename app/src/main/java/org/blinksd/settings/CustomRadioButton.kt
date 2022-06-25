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
import androidx.appcompat.widget.AppCompatRadioButton

class CustomRadioButton: AppCompatRadioButton {
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
        fun createOption(ctx :Context, resId: Int, value: Int) :CustomRadioButton {
            return CustomRadioButton(ctx).also {
                it.text = ctx.getString(resId)
                it.id = value
            }
        }
    }

    private fun applyStyle() {
        var disabledColor = context.getColor(android.R.color.tab_indicator_text) xor 0x00ffffff
        var accentColor: Int
        if(Build.VERSION.SDK_INT > Build.VERSION_CODES.R) {
            accentColor = context.getColor(android.R.color.system_accent1_500)
        } else {
            val arr: TypedArray = context.obtainStyledAttributes(
                0, intArrayOf(
                    R.attr.colorAccent
                )
            )
            accentColor = arr.getColor(0, context.getColor(R.color.holo_blue_light))
            arr.recycle()
        }

        val state1 = intArrayOf(android.R.attr.state_enabled, android.R.attr.state_checked)
        val state2 = intArrayOf(android.R.attr.state_enabled)

        buttonTintList = ColorStateList(arrayOf(state1, state2), intArrayOf(accentColor, disabledColor))

        setTextAppearance(android.R.style.TextAppearance_DeviceDefault_Small)
    }
}