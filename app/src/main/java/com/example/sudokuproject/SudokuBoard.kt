package com.example.sudokuproject

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import kotlin.random.Random

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

        textPaint.textSize = cellSize * 0.5f
        for (r in 0 until 9) {
            for (c in 0 until 9) {
                val num = board[r][c]
                if (num != 0) {
                    val textColor = if (isFixed[r][c]) {
                        android.graphics.Color.BLACK
                    } else {
                        android.graphics.Color.BLUE
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

    fun generatePuzzle(difficulty: String) {
        val solution = arrayOf(
            intArrayOf(5, 3, 4, 6, 7, 8, 9, 1, 2),
            intArrayOf(6, 7, 2, 1, 9, 5, 3, 4, 8),
            intArrayOf(1, 9, 8, 3, 4, 2, 5, 6, 7),
            intArrayOf(8, 5, 9, 7, 6, 1, 4, 2, 3),
            intArrayOf(4, 2, 6, 8, 5, 3, 7, 9, 1),
            intArrayOf(7, 1, 3, 9, 2, 4, 8, 5, 6),
            intArrayOf(9, 6, 1, 5, 3, 7, 2, 8, 4),
            intArrayOf(2, 8, 7, 4, 1, 9, 6, 3, 5),
            intArrayOf(3, 4, 5, 2, 8, 6, 1, 7, 9)
        )

        val cellsToClear = when (difficulty) {
            "Легкий" -> 20
            "Средний" -> 35
            "Сложный" -> 50
            else -> 35
        }

        for (r in 0 until 9) {
            for (c in 0 until 9) {
                board[r][c] = solution[r][c]
                isFixed[r][c] = true
            }
        }

        val random = Random
        var removed = 0
        while (removed < cellsToClear) {
            val r = random.nextInt(9)
            val c = random.nextInt(9)
            if (board[r][c] != 0) {
                board[r][c] = 0
                isFixed[r][c] = false
                removed++
            }
        }
    }
}