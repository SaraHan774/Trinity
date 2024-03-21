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
import com.august.trinity.R
import com.august.trinity.databinding.ActivityInteropBinding
import kotlin.math.round


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

        val roundSpan = RoundedBackgroundSpan(
            textColor = R.color.purple_700,
            backgroundColor = R.color.purple_200
        )

        val roundCornerSpan = RoundCornerSpan(
            backgroundColor = R.color.purple_700,
            textColor = R.color.purple_200,
            cornerRadius = 100f.toDp,
            baselineShift = -binding.mainText.textSize.toDp/2
        )

        binding.mainText.text = buildSpannedString {
            append("HELLO")
                inSpans(
                    // CenterVerticalSpan(), // the order matters.
                    // 사이즈 변경 전에 호출하면 앞의 텍스트 사이즈를 받고, 변경 후에는 변경된 사이즈를 받는다
                    AbsoluteSizeSpan(toPx(14, this@InteropActivity)),
                    roundCornerSpan
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
        Log.d("===", "${tp.textSize.toDp}")
        return -(tp.textSize.toDp/2).toInt()
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

