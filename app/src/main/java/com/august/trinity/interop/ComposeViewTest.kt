package com.august.trinity.interop

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.AbstractComposeView


class TestComposeView @JvmOverloads constructor(
    context: android.content.Context,
    attrs: android.util.AttributeSet? = null,
    defStyleAttr: Int = 0,
) : AbstractComposeView(context, attrs, defStyleAttr) {

    @Composable
    override fun Content() {
        TODO("Not yet implemented")
    }
}