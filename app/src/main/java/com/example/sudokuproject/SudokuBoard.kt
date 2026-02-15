package com.example.sudokuproject

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View

class SudokuBoard @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    // Кисти для рисования
    private val thickLinePaint = Paint().apply {
        color = android.graphics.Color.BLACK
        strokeWidth = 8f
        style = Paint.Style.STROKE
    }

    private val thinLinePaint = Paint().apply {
        color = android.graphics.Color.GRAY
        strokeWidth = 2f
        style = Paint.Style.STROKE
    }

    private var cellSize = 0

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        // Делаем доску квадратной
        val size = MeasureSpec.getSize(widthMeasureSpec).coerceAtMost(MeasureSpec.getSize(heightMeasureSpec))
        cellSize = size / 9
        setMeasuredDimension(size, size)
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        // Рисуем тонкие линии (все ячейки)
        for (i in 0..9) {
            val pos = i * cellSize.toFloat()
            canvas.drawLine(0f, pos, width.toFloat(), pos, thinLinePaint)
            canvas.drawLine(pos, 0f, pos, height.toFloat(), thinLinePaint)
        }

        // Рисуем жирные линии (границы 3×3 блоков)
        for (i in 0..3) {
            val pos = i * cellSize * 3.toFloat()
            canvas.drawLine(0f, pos, width.toFloat(), pos, thickLinePaint)
            canvas.drawLine(pos, 0f, pos, height.toFloat(), thickLinePaint)
        }
    }
}