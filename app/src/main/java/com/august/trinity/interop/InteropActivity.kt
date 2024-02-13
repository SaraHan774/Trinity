package com.august.trinity.interop

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.enableEdgeToEdge
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.august.trinity.R
import com.august.trinity.databinding.ActivityInteropBinding

class InteropActivity : ComponentActivity() {

    val binding by lazy {
        ActivityInteropBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        binding.uiModel = TestPropertiesUIM(
            clickRight = {
                Toast.makeText(this, "Right", Toast.LENGTH_SHORT).show()
                Log.d("Interop", "Right")
            }
        )
    }
}

data class TestPropertiesUIM(
    val test: String = "Hello Trinity",
    val clickRight: () -> Unit = {},
)
