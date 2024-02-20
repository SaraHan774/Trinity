package com.august.trinity.interop

import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Path
import android.graphics.Rect
import android.graphics.RectF
import android.util.DisplayMetrics
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.view.forEachIndexed
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ItemDecoration
import com.august.trinity.R
import kotlin.math.max
import kotlin.math.min


class SomeListAdapter : RecyclerView.Adapter<SomeViewHolder>() {
    private val items = listOf("Hello", "World", "Name")
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SomeViewHolder {
        val v = SomeViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.some_view_holder, parent, false)
        )
        return v
    }

    override fun getItemCount(): Int {
        return 3
    }

    override fun onBindViewHolder(holder: SomeViewHolder, position: Int) {
        holder.bind(items[position])
    }

}

class SomeViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    fun bind(item: String) {
        if (item == "Hello") {
            itemView.findViewById<TextView>(R.id.someTextView).setBackgroundColor(
                itemView.context.resources.getColor(R.color.purple_200)
            )
        }
        if (item == "World") {
            itemView.findViewById<TextView>(R.id.someTextView).setBackgroundColor(
                itemView.context.resources.getColor(R.color.purple_500)
            )
        }
        if (item == "Name") {
            itemView.findViewById<TextView>(R.id.someTextView).setBackgroundColor(
                itemView.context.resources.getColor(R.color.purple_700)
            )
        }
        itemView.findViewById<TextView>(R.id.someTextView).text = item
    }
}

class RoundedCornerItemDecorator(private val radius: Float, private val margin: Float) :
    RecyclerView.ItemDecoration() {

    private val rect = RectF()
    private val paint = Paint()

    override fun onDraw(canvas: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        super.onDraw(canvas, parent, state)
        val itemCount = parent.adapter?.itemCount ?: 0
        val left = parent.paddingLeft.toFloat()
        val right = parent.width - parent.paddingRight.toFloat()

        for (i in 0 until itemCount) {
            val child = parent.getChildAt(i) ?: continue
            val position = parent.getChildAdapterPosition(child)

            if (position == 0 || position == itemCount - 1) {
                val params = child.layoutParams as RecyclerView.LayoutParams
                val top = (child.top - params.topMargin).toFloat()
                val bottom = (child.bottom + params.bottomMargin).toFloat()

                rect.set(left, top, right, bottom)

                val cornerRadius = if (position == 0) {
                    // 첫 번째 아이템의 경우 상단 코너만 둥글게 처리합니다.
                    floatArrayOf(radius, radius, radius, radius, 0f, 0f, 0f, 0f)
                } else {
                    // 마지막 아이템의 경우 하단 코너만 둥글게 처리합니다.
                    floatArrayOf(0f, 0f, 0f, 0f, radius, radius, radius, radius)
                }

                canvas.drawRoundRect(rect, cornerRadius[0], cornerRadius[1], paint)
            }
        }
    }
}

class RoundCornersDecoration(private val radius: Float) : ItemDecoration() {

    override fun onDraw(canvas: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        val rectToClip = getRectToClip(parent)
        val path = Path()
        path.addRoundRect(rectToClip, radius, radius, Path.Direction.CW)
        canvas.clipPath(path)
    }

    private fun getRectToClip(parent: RecyclerView): RectF {
        val rectToClip = RectF(0f, 0f, 0f, 0f)
        val childRect = Rect()

        for (i in 0 until parent.childCount) {
            val child = parent.getChildAt(i)
            val position = parent.getChildAdapterPosition(child)
            if (position == 0) {

            }
            if (position == parent.adapter?.itemCount?.minus(1)) {

            }
            parent.getDecoratedBoundsWithMargins(child, childRect)
            rectToClip.left = min(rectToClip.left.toDouble(), childRect.left.toDouble()).toFloat()
            rectToClip.top = min(rectToClip.top.toDouble(), childRect.top.toDouble()).toFloat()
            rectToClip.right =
                max(rectToClip.right.toDouble(), childRect.right.toDouble()).toFloat()
            rectToClip.bottom =
                max(rectToClip.bottom.toDouble(), childRect.bottom.toDouble()).toFloat()
        }

        return rectToClip
    }
}

class RoundedCornerItemDecorator2(private val radius: Float) : RecyclerView.ItemDecoration() {

    private val rect = RectF()
    private val paint = Paint()

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        super.getItemOffsets(outRect, view, parent, state)
        // 아이템 오프셋 설정 코드를 변경하지 않습니다.
    }

    override fun onDraw(canvas: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        super.onDraw(canvas, parent, state)
        val itemCount = parent.adapter?.itemCount ?: 0
        val left = parent.paddingLeft.toFloat()
        val right = parent.width - parent.paddingRight.toFloat()


        // 좌상단, 우상단 모서리의 반지름을 dp 단위로 지정합니다.
        val cornerRadius = 4.dpToPx(parent.context.resources.displayMetrics)

        // 네모의 경로를 추가할 Path 객체를 생성합니다.

        val path = Path()
        parent.forEachIndexed { index, child ->
        // 네모의 바깥쪽 사각형을 정의합니다.
        val c = parent.getChildAt(index)
        val rect = RectF(0f, 0f, c.width.toFloat(), c.height.toFloat())
        path.addRoundRect(rect, cornerRadius, cornerRadius, Path.Direction.CW)
        }
        canvas.clipPath(path)
    }

    override fun onDrawOver(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        super.onDrawOver(c, parent, state)
    }
}

fun Int.dpToPx(displayMetrics: DisplayMetrics): Float {
    return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, this.toFloat(), displayMetrics)
}


class CustomItemDecoration(
    private val cornerRadius: Float, // 둥근 코너의 반지름
    private val margin: Float // 아이템 간의 간격
) : RecyclerView.ItemDecoration() {

    private val path = Path() // 둥근 코너를 그리기 위한 Path 객체
    private val paint = Paint(Paint.ANTI_ALIAS_FLAG)

    override fun onDraw(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        super.onDraw(c, parent, state)

        // RecyclerView의 아이템 개수를 가져옵니다.
        val itemCount = parent.adapter?.itemCount ?: return

        // RecyclerView의 가로 길이를 가져옵니다.
        val width = parent.width.toFloat()
        val cornerRadius = 4.dpToPx(parent.context.resources.displayMetrics)

        // 아이템의 인덱스와 높이를 순회하며 각 아이템의 배경을 그립니다.
        for (i in 0 until itemCount) {
            val child = parent.getChildAt(i) ?: continue
            val params = child.layoutParams as RecyclerView.LayoutParams

            // 아이템의 상하 좌우 좌표를 계산합니다.
            val left = child.left.toFloat() - params.leftMargin
            val top = child.top.toFloat() - params.topMargin
            val right = child.right.toFloat() + params.rightMargin
            val bottom = child.bottom.toFloat() + params.bottomMargin

            // 아이템의 경계를 나타내는 사각형을 생성합니다.
            val rect = RectF(left, top, right, bottom)

            // 현재 아이템이 첫 번째인지 여부를 확인합니다.
            val isFirstItem = i == 0
            // 현재 아이템이 마지막인지 여부를 확인합니다.
            val isLastItem = i == itemCount - 1

            // Path 객체를 초기화합니다.
            path.reset()

            // 좌상단 모서리의 둥근 코너를 추가합니다.
            if (isFirstItem) {
                path.addRoundRect(rect, cornerRadius, cornerRadius, Path.Direction.CW)
            } else {
                path.addRect(rect, Path.Direction.CW)
            }

            // 우상단 모서리의 둥근 코너를 추가합니다.
            if (isFirstItem) {
                path.addRect(right - cornerRadius, top, right, top + cornerRadius, Path.Direction.CW)
            } else {
                path.addRect(right - cornerRadius, top, right, top + margin, Path.Direction.CW)
            }

            // 우하단 모서리의 둥근 코너를 추가합니다.
            if (isLastItem) {
                path.addRect(right - cornerRadius, bottom - cornerRadius, right, bottom, Path.Direction.CW)
            } else {
                path.addRect(right - cornerRadius, bottom - margin, right, bottom, Path.Direction.CW)
            }

            // 좌하단 모서리의 둥근 코너를 추가합니다.
            if (isLastItem) {
                path.addRect(left, bottom - cornerRadius, left + cornerRadius, bottom, Path.Direction.CW)
            } else {
                path.addRect(left, bottom - margin, left + cornerRadius, bottom, Path.Direction.CW)
            }

            // Path를 사용하여 아이템의 배경을 그립니다.
            c.drawPath(path, paint)
        }
    }
}
