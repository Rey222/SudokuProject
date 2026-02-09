package com.example.sudokuproject

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.Spinner
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity

class MenuActivity : AppCompatActivity() {

    private lateinit var difficultySpinner: Spinner

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu)

        // Инициализация элементов интерфейса
        difficultySpinner = findViewById(R.id.difficultySpinner)
        val playButton: Button = findViewById(R.id.playButton)
        val rulesButton: Button = findViewById(R.id.rulesButton)

        // Обработка нажатия кнопки "Играть"
        playButton.setOnClickListener {
            val difficulty = difficultySpinner.selectedItem.toString()
            val intent = Intent(this, MainActivity::class.java).apply {
                putExtra("difficulty", difficulty)
            }
            startActivity(intent)
        }

        // Обработка нажатия кнопки "Правила"
        rulesButton.setOnClickListener {
            showRulesDialog()
        }
    }

    /**
     * Отображает диалоговое окно с правилами Судоку
     */
    private fun showRulesDialog() {
        AlertDialog.Builder(this)
            .setTitle("Правила игры в Судоку")
            .setMessage(
                "Судоку — это головоломка с числами.\n\n" +
                        "Цель игры: заполнить пустые ячейки цифрами от 1 до 9 так, чтобы:\n" +
                        "• В каждой строке были все цифры от 1 до 9 без повторений.\n" +
                        "• В каждом столбце были все цифры от 1 до 9 без повторений.\n" +
                        "• В каждом блоке 3×3 были все цифры от 1 до 9 без повторений.\n\n" +
                        "Удачи!"
            )
            .setPositiveButton("Закрыть", null)
            .show()
    }
}