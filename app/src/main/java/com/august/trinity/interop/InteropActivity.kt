package com.august.trinity.interop

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.august.trinity.R
import com.august.trinity.databinding.ActivityInteropBinding

class InteropActivity : AppCompatActivity() {

    val binding by lazy {
        ActivityInteropBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        val adapter = SomeListAdapter()
        val rv = binding.recyclerView
        rv.apply {
            this.adapter = adapter
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(this@InteropActivity)
        }
    }
}

data class TestPropertiesUIM(
    val test: String = "Hello Trinity",
    val clickRight: () -> Unit = {},
)
