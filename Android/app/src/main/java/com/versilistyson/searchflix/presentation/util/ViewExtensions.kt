package com.versilistyson.searchflix.presentation.util

import android.view.Menu
import androidx.appcompat.widget.Toolbar
import androidx.core.view.isNotEmpty

fun Menu.clearMenu() {
    if(this.isNotEmpty()) this.clear()
}
fun Toolbar.swapMenu(menuId: Int) {
    this.menu.clearMenu()
    this.inflateMenu(menuId)
}