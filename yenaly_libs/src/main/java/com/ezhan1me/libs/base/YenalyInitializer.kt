@file:Suppress("unused")

package com.ezhan1me.libs.base

import android.content.Context
import androidx.annotation.CallSuper
import androidx.startup.Initializer
import com.ezhan1me.libs.utils.applicationContext

/**
 * @ProjectName : YenalyModule
 * @Author : Yenaly Liew
 * @Time : 2022/04/21 021 14:04
 * @Description : Description...
 */
open class YenalyInitializer : Initializer<Unit> {

    @CallSuper
    override fun create(context: Context) {
        applicationContext = context
    }

    override fun dependencies(): MutableList<Class<out Initializer<*>>> {
        return mutableListOf()
    }
}