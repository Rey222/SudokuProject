package com.example.sudokuproject

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
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
        color = Color.BLACK
        strokeWidth = 8f
        style = Paint.Style.STROKE
    }

    private val thinLinePaint = Paint().apply {
        color = Color.GRAY
        strokeWidth = 2f
        style = Paint.Style.STROKE
    }

    private val textPaint = Paint().apply {
        textAlign = Paint.Align.CENTER
        isAntiAlias = true
    }

    private var cellSize = 0
    private val solver = Solver()

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val size = MeasureSpec.getSize(widthMeasureSpec)
            .coerceAtMost(MeasureSpec.getSize(heightMeasureSpec))
        cellSize = size / 9
        setMeasuredDimension(size, size)
        textPaint.textSize = cellSize * 0.5f
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        for (i in 0..9) {
            val pos = i * cellSize.toFloat()
            canvas.drawLine(0f, pos, width.toFloat(), pos, thinLinePaint)
            canvas.drawLine(pos, 0f, pos, height.toFloat(), thinLinePaint)
        }
        for (i in 0..3) {
            val pos = i * cellSize * 3f
            canvas.drawLine(0f, pos, width.toFloat(), pos, thickLinePaint)
            canvas.drawLine(pos, 0f, pos, height.toFloat(), thickLinePaint)
        }

        val board = solver.getBoard()
        val solution = solver.getSolution()

        for (r in 0 until 9) {
            for (c in 0 until 9) {
                val num = board[r][c]
                if (num != 0) {
                    textPaint.color = when {
                        solver.isFixed(r, c) -> Color.BLACK
                        num == solution[r][c] -> Color.parseColor("#2E7D32")
                        else -> Color.RED
                    }

                    val x = (c + 0.5f) * cellSize.toFloat()
                    val y = (r + 0.5f) * cellSize.toFloat() - (textPaint.descent() + textPaint.ascent()) / 2
                    canvas.drawText(num.toString(), x, y, textPaint)
                }
            }
        }

        if (solver.selectedRow != -1 && solver.selectedColumn != -1) {
            val highlightPaint = Paint().apply {
                color = 0x4087CEFA.toInt()
                style = Paint.Style.FILL
            }
            canvas.drawRect(
                solver.selectedColumn * cellSize.toFloat(),
                solver.selectedRow * cellSize.toFloat(),
                (solver.selectedColumn + 1) * cellSize.toFloat(),
                (solver.selectedRow + 1) * cellSize.toFloat(),
                highlightPaint
            )
        }
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        if (event.action == MotionEvent.ACTION_DOWN) {
            val row = (event.y / cellSize).toInt().coerceIn(0, 8)
            val col = (event.x / cellSize).toInt().coerceIn(0, 8)

            if (!solver.isFixed(row, col)) {
                solver.selectedRow = row
                solver.selectedColumn = col
                invalidate()
                return true
            }
        }
        return super.onTouchEvent(event)
    }


    fun getSolver() = solver

    fun generatePuzzle(difficulty: String) {
        solver.generateBoard(difficulty)
        invalidate()
    }

    fun trySetNumber(number: Int): Boolean {
        val row = solver.selectedRow
        val col = solver.selectedColumn
        if (row == -1 || col == -1) return false

        val success = solver.setNumber(row, col, number)
        invalidate()
        return success
    }

    fun resetGame() {
        solver.resetBoard()
        invalidate()
    }

    fun checkWin() = solver.isSolved()
}