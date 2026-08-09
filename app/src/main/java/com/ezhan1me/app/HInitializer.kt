package com.ezhan1me.app

import android.content.Context
import com.ezhan1me.libs.base.YenalyInitializer

class HInitializer : YenalyInitializer() {
    override fun create(context: Context) {
        super.create(context)
        // 用于处理 Firebase Crashlytics 初始化
        Thread.setDefaultUncaughtExceptionHandler(HCrashHandler)
    }
}