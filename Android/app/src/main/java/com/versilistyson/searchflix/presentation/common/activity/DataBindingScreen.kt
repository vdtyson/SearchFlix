package com.versilistyson.searchflix.presentation.common.activity

import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.viewbinding.ViewBinding

interface DataBindingScreen<Binding: ViewDataBinding> {
    var binding: Binding
}