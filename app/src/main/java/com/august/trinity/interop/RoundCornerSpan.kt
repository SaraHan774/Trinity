package com.august.trinity.interop

import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.RectF
import android.text.style.ReplacementSpan
import android.content.res.Resources

class RoundCornerSpan(
    private val backgroundColor: Int,
    private val textColor: Int,
    private val cornerRadius: Float = 8f, // Default corner radius
    private val baselineShift: Float = 0f // Default baseline shift for vertical centering
) : ReplacementSpan() {

    override fun draw(
        canvas: Canvas,
        text: CharSequence,
        start: Int,
        end: Int,
        x: Float,
        top: Int,
        y: Int,
        bottom: Int,
        paint: Paint
    ) {
        val newTop = y + paint.fontMetrics.ascent + baselineShift
        val newBottom = y + paint.fontMetrics.descent + baselineShift
        val rect = RectF(x, newTop, x + measureText(paint, text, start, end), newBottom)

        paint.color = backgroundColor
        canvas.drawRoundRect(rect, cornerRadius, cornerRadius, paint)

        paint.color = textColor
        canvas.drawText(text, start, end, x, y.toFloat() + baselineShift, paint)
    }

    override fun getSize(
        paint: Paint,
        text: CharSequence?,
        start: Int,
        end: Int,
        fm: Paint.FontMetricsInt?
    ): Int {
        return (paint.measureText(text, start, end) + 2 * cornerRadius).toInt()
    }

    private fun measureText(paint: Paint, text: CharSequence, start: Int, end: Int): Float {
        return paint.measureText(text, start, end)
    }
}
