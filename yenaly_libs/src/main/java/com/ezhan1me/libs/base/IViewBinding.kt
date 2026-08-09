package com.ezhan1me.libs.base

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.ViewDataBinding

interface IViewBinding<DB : ViewDataBinding> {

    val binding: DB

    fun getViewBinding(inflater: LayoutInflater, container: ViewGroup?): DB

}