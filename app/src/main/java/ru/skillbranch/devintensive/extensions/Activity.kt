package ru.skillbranch.devintensive.extensions

import android.app.Activity
import android.content.Context
import android.graphics.Rect
import android.view.inputmethod.InputMethodManager

fun Activity.hideKeyboard() {
    val view = this.currentFocus
    if (view != null) {
        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view.windowToken, 0)
    }
}

fun Activity.isKeyboardOpen(): Boolean {
    val rect = Rect()
    val rootView = this.window.decorView
    rootView.getWindowVisibleDisplayFrame(rect)
    val screenHeight = rootView.height
    val heightDifference = screenHeight - (rect.bottom - rect.top)
    return (heightDifference > 200)
}

fun Activity.isKeyboardClosed() = !isKeyboardOpen()
