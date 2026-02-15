package com.example.sudokuproject

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Кнопка "Назад в меню"
        findViewById<Button>(R.id.backToMenuButton).setOnClickListener {
            Intent(this, MenuActivity::class.java).also {
                startActivity(it)
                finish()
            }
        }

        // Пока просто находим доску — позже добавим генерацию игры
        val sudokuBoard = findViewById<SudokuBoard>(R.id.sudokuBoard)
        // TODO: sudokuBoard.generateGame("Средний")
    }
}