package com.versilistyson.searchflix.presentation.util

import android.view.Menu
import android.widget.ImageView
import androidx.appcompat.widget.Toolbar
import androidx.core.view.isNotEmpty
import com.google.android.material.appbar.MaterialToolbar
import com.squareup.picasso.Picasso

fun Menu.clearMenu() {
    if(this.isNotEmpty()) this.clear()
}
fun Toolbar.swapMenu(menuId: Int) {
    this.menu.clearMenu()
    this.inflateMenu(menuId)
}

fun MaterialToolbar.setMenuItemVisibility(id: Int, isVisible: Boolean) {
    menu.findItem(id).isVisible = isVisible
}

fun ImageView.setImageWithPicasso(path: String) {
    Picasso.get().load(path).into(this)
}