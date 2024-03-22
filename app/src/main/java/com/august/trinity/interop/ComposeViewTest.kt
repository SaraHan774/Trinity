package com.august.trinity.interop

import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.AbstractComposeView
import com.august.trinity.R

class TestComposeView @JvmOverloads constructor(
    context: android.content.Context,
    attrs: android.util.AttributeSet? = null,
    defStyleAttr: Int = 0,
) : AbstractComposeView(context, attrs, defStyleAttr) {
//https://www.kodeco.com/31473074-interoperability-with-jetpack-compose

    var properties: TestProperties by mutableStateOf(TestProperties("Hello", {}))

    init {
        val typedArr = context.obtainStyledAttributes(attrs, R.styleable.TestComposeView)
        val text = typedArr.getString(R.styleable.TestComposeView_myText).orEmpty()
        properties = TestProperties(text, {})
        typedArr.recycle()
    }

    @Composable
    override fun Content() {
        Button(onClick = { properties.clickRight.invoke() }) {
            Text(text = properties.myText)
        }
    }
}

data class TestProperties(
    val myText: String,
    val clickRight: () -> Unit,
)
