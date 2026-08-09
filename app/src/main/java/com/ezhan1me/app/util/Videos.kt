package com.ezhan1me.app.util

import androidx.fragment.app.Fragment
import com.ezhan1me.app.ui.activity.MainActivity

fun Fragment.openVideo(code: String) {
    (activity as? MainActivity)?.showVideoDetailFragment(code)
}
