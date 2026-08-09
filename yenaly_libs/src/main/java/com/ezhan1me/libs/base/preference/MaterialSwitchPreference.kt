package com.ezhan1me.libs.base.preference

import android.content.Context
import android.util.AttributeSet
import androidx.preference.SwitchPreferenceCompat
import com.ezhan1me.libs.R

open class MaterialSwitchPreference @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null,
) : SwitchPreferenceCompat(context, attrs) {
    init {
        widgetLayoutResource = R.layout.yenaly_preference_switch_widget
    }
}