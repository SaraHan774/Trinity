package com.august.trinity.interop

import androidx.databinding.BindingAdapter

@BindingAdapter(value = ["myText", "clickRight"], requireAll = false)
fun TestComposeView.setProperties(
    myText: String,
    clickRight: () -> Unit,
) {
    this.properties = TestProperties(myText) { clickRight() }
}