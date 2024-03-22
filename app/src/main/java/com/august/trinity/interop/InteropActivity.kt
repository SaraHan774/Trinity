package com.august.trinity.interop

import android.content.Context
import android.content.res.Resources
import android.os.Bundle
import android.text.TextPaint
import android.text.style.AbsoluteSizeSpan
import android.text.style.MetricAffectingSpan
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.text.buildSpannedString
import androidx.core.text.inSpans
import androidx.recyclerview.widget.LinearLayoutManager
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

        binding.mainText.text = buildSpannedString {
            append("HELLO")
            inSpans(
                CenterVerticalSpan(),
                AbsoluteSizeSpan(toPx(14, this@InteropActivity))
            ) {
                append("Not Available")
            }
        }
    }
}

class CenterVerticalSpan : MetricAffectingSpan() {
    override fun updateMeasureState(textPaint: TextPaint) {
        textPaint.baselineShift += getBaselineShift(textPaint)
    }

    override fun updateDrawState(textPaint: TextPaint) {
        textPaint.baselineShift += getBaselineShift(textPaint)
    }

    private fun getBaselineShift(tp: TextPaint): Int {
        return -(tp.textSize.toDp / 2).toInt()
    }
}

fun toPx(dp: Int, context: Context): Int {
    val density = context.resources.displayMetrics.density
    return (dp * density + 0.5f).toInt() // Adding 0.5f for rounding to the nearest whole number
}

data class TestPropertiesUIM(
    val test: String = "Hello Trinity",
    val clickRight: () -> Unit = {},
)

val Float.toDp: Float get() = this / Resources.getSystem().displayMetrics.density
val Int.toDp: Float get() = this / Resources.getSystem().displayMetrics.density.toInt().toFloat()
