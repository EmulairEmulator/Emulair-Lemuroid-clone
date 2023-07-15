package com.bigbratan.emulair.ui

import android.content.Context
import androidx.core.graphics.ColorUtils
import android.util.AttributeSet
import android.util.TypedValue
import com.bigbratan.emulair.R
import com.bigbratan.emulair.activities.BaseThemedActivity
import com.google.android.material.card.MaterialCardView
import kotlin.math.max

class CustomMaterialCardView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = R.attr.materialCardViewFilledStyle
) : MaterialCardView(context, attrs, defStyleAttr) {

    companion object {
        lateinit var baseThemedActivity: BaseThemedActivity
    }

    init {
        val typedValue = TypedValue()
        context.theme.resolveAttribute(R.attr.colorSurfaceVariant, typedValue, true)
        val colorSurfaceVariant = typedValue.data
        val hsl = FloatArray(3)
        ColorUtils.colorToHSL(colorSurfaceVariant, hsl)
        hsl[2] = baseThemedActivity.adjustLuminance(hsl[2])
        val luminanceColor = ColorUtils.HSLToColor(hsl)

        setCardBackgroundColor(luminanceColor)
    }
}
