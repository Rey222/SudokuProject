package com.example.sudokuproject

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View

class SudokuBoard @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

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

    private val textPaint = Paint().apply {
        color = android.graphics.Color.BLACK
        textAlign = Paint.Align.CENTER
    }

    private var cellSize = 0

    private val board = Array(9) { IntArray(9) }
    private val isFixed = Array(9) { BooleanArray(9) }

    var selectedRow = -1
        private set
    var selectedColumn = -1
        private set

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val size = MeasureSpec.getSize(widthMeasureSpec).coerceAtMost(MeasureSpec.getSize(heightMeasureSpec))
        cellSize = size / 9
        setMeasuredDimension(size, size)
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        // Сетка
        for (i in 0..9) {
            val pos = i * cellSize.toFloat()
            canvas.drawLine(0f, pos, width.toFloat(), pos, thinLinePaint)
            canvas.drawLine(pos, 0f, pos, height.toFloat(), thinLinePaint)
        }
        for (i in 0..3) {
            val pos = i * cellSize * 3.toFloat()
            canvas.drawLine(0f, pos, width.toFloat(), pos, thickLinePaint)
            canvas.drawLine(pos, 0f, pos, height.toFloat(), thickLinePaint)
        }

        // Цифры
        textPaint.textSize = cellSize * 0.5f
        for (r in 0 until 9) {
            for (c in 0 until 9) {
                val num = board[r][c]
                if (num != 0) {
                    val textColor = if (isFixed[r][c]) {
                        android.graphics.Color.BLACK // исходные — чёрные
                    } else {
                        android.graphics.Color.BLUE // введённые — синие
                    }
                    textPaint.color = textColor
                    canvas.drawText(
                        num.toString(),
                        (c + 0.5f) * cellSize.toFloat(),
                        (r + 0.7f) * cellSize.toFloat(),
                        textPaint
                    )
                }
            }
        }

        if (selectedRow != -1 && selectedColumn != -1) {
            val highlightPaint = Paint().apply {
                color = 0x4087CEFA.toInt()
                style = Paint.Style.FILL
            }
            canvas.drawRect(
                selectedColumn * cellSize.toFloat(),
                selectedRow * cellSize.toFloat(),
                (selectedColumn + 1) * cellSize.toFloat(),
                (selectedRow + 1) * cellSize.toFloat(),
                highlightPaint
            )
        }
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        if (event.action == MotionEvent.ACTION_DOWN) {
            selectedRow = (event.y / cellSize).toInt().coerceIn(0, 8)
            selectedColumn = (event.x / cellSize).toInt().coerceIn(0, 8)
            invalidate()
            return true
        }
        return super.onTouchEvent(event)
    }

    fun setCell(row: Int, col: Int, value: Int) {
        if (!isFixed[row][col]) {
            board[row][col] = value
        }
    }

    fun initializeSamplePuzzle() {
        val puzzle = arrayOf(
            intArrayOf(5, 3, 0, 0, 7, 0, 0, 0, 0),
            intArrayOf(6, 0, 0, 1, 9, 5, 0, 0, 0),
            intArrayOf(0, 9, 8, 0, 0, 0, 0, 6, 0),
            intArrayOf(8, 0, 0, 0, 6, 0, 0, 0, 3),
            intArrayOf(4, 0, 0, 8, 0, 3, 0, 0, 1),
            intArrayOf(7, 0, 0, 0, 2, 0, 0, 0, 6),
            intArrayOf(0, 6, 0, 0, 0, 0, 2, 8, 0),
            intArrayOf(0, 0, 0, 4, 1, 9, 0, 0, 5),
            intArrayOf(0, 0, 0, 0, 8, 0, 0, 7, 9)
        )

        for (r in 0 until 9) {
            for (c in 0 until 9) {
                board[r][c] = puzzle[r][c]
                isFixed[r][c] = puzzle[r][c] != 0
            }
        }
    }
}