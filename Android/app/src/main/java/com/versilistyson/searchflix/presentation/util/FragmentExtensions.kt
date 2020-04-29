package com.versilistyson.searchflix.presentation.util

import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager

fun Fragment.provideLinearLayoutManager(orientation: Int, reverseLayout: Boolean = false) =
    LinearLayoutManager(context, orientation, reverseLayout)

fun Fragment.showToast(text: String, duration: Int = Toast.LENGTH_SHORT) =
    Toast.makeText(context, text, duration).show()