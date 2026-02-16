package com.example.sudokuproject

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private lateinit var sudokuBoard: SudokuBoard

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        sudokuBoard = findViewById(R.id.SudokuBoard)
        sudokuBoard.initializeSamplePuzzle()

        findViewById<Button>(R.id.backToMenuButton).setOnClickListener {
            Intent(this, MenuActivity::class.java).also { intent ->
                startActivity(intent)
                finish()
            }
        }

        for (i in 1..9) {
            val buttonId = resources.getIdentifier("btn$i", "id", packageName)
            findViewById<Button>(buttonId).setOnClickListener {
                onNumberClick(i)
            }
        }
    }

    private fun onNumberClick(number: Int) {
        val row = sudokuBoard.selectedRow
        val col = sudokuBoard.selectedColumn

        if (row != -1 && col != -1) {
            sudokuBoard.setCell(row, col, number)
            sudokuBoard.invalidate()
        }
    }
}