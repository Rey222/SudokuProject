package com.example.sudokuproject

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private lateinit var sudokuBoard: SudokuBoard
    private lateinit var solver: Solver

    private lateinit var heart1: ImageView
    private lateinit var heart2: ImageView
    private lateinit var heart3: ImageView

    private var errorCount = 0
    private val maxLives = 3

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val difficulty = intent.getStringExtra("difficulty") ?: "Средний"

        sudokuBoard = findViewById(R.id.SudokuBoard)
        solver = sudokuBoard.getSolver()

        heart1 = findViewById(R.id.heart1)
        heart2 = findViewById(R.id.heart2)
        heart3 = findViewById(R.id.heart3)

        sudokuBoard.generatePuzzle(difficulty)
        updateHearts()

        findViewById<Button>(R.id.backToMenuButton).setOnClickListener {
            startActivity(Intent(this, MenuActivity::class.java))
            finish()
        }

        for (i in 1..9) {
            val btnId = resources.getIdentifier("btn$i", "id", packageName)
            findViewById<Button>(btnId).setOnClickListener {
                onNumberClick(i)
            }
        }
    }

    private fun onNumberClick(number: Int) {
        val row = solver.selectedRow
        val col = solver.selectedColumn

        if (row == -1 || col == -1) {
            Toast.makeText(this, "Выберите ячейку", Toast.LENGTH_SHORT).show()
            return
        }

        val isCorrect = sudokuBoard.trySetNumber(number)

        if (!isCorrect) {
            errorCount++
            updateHearts()
            Toast.makeText(this, "Ошибка!", Toast.LENGTH_SHORT).show()

            if (errorCount >= maxLives) {
                gameOver()
                return
            }
        }

        if (sudokuBoard.checkWin()) {
            Toast.makeText(this, "Победа!", Toast.LENGTH_LONG).show()

        }
    }

    private fun updateHearts() {
        heart1.visibility = if (errorCount < 1) ImageView.VISIBLE else ImageView.INVISIBLE
        heart2.visibility = if (errorCount < 2) ImageView.VISIBLE else ImageView.INVISIBLE
        heart3.visibility = if (errorCount < 3) ImageView.VISIBLE else ImageView.INVISIBLE
    }

    private fun gameOver() {
        Toast.makeText(this, "Игра окончена", Toast.LENGTH_LONG).show()
        sudokuBoard.resetGame()
        errorCount = 0
        updateHearts()
    }
}