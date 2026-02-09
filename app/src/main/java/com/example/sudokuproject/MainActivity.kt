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
        val backToMenuButton = findViewById<Button>(R.id.backToMenuButton)
        backToMenuButton.setOnClickListener {
            val intent = Intent(this, MenuActivity::class.java)
            startActivity(intent)
            finish()
        }

        // Позже сюда добавим:
        // - инициализацию игровой доски
        // - обработку уровней сложности
        // - логику ввода чисел и проверки
    }
}