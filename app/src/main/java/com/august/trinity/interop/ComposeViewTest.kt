package com.august.trinity.interop

import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.AbstractComposeView

class TestComposeView @JvmOverloads constructor(
    context: android.content.Context,
    attrs: android.util.AttributeSet? = null,
    defStyleAttr: Int = 0,
) : AbstractComposeView(context, attrs, defStyleAttr) {
//https://www.kodeco.com/31473074-interoperability-with-jetpack-compose

    var properties: TestProperties by mutableStateOf(TestProperties("Hello", {}))

    @Composable
    override fun Content() {
        Button(onClick = { properties.clickRight }) {
            Text(text = properties.myText)
        }
    }
}

data class TestProperties(
    val myText: String,
    val clickRight: () -> Unit,
)
