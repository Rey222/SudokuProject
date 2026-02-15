package com.example.sudokuproject

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Кнопка "Вернуться в меню"
        findViewById<Button>(R.id.backToMenuButton).setOnClickListener {
            Intent(this, MenuActivity::class.java).also { intent ->
                startActivity(intent)
                finish()
            }
        }

        // Подключаем доску — ID как в XML: SudokuBoard (с большой буквы!)
        val sudokuBoard = findViewById<SudokuBoard>(R.id.SudokuBoard)
        // Пока без генерации — просто убедимся, что она отображается
    }
}