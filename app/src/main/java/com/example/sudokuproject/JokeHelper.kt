package com.example.sudokuproject

import android.app.AlertDialog
import android.content.Context
import org.json.JSONObject
import java.net.URL
import kotlin.concurrent.thread

object JokeHelper {

    private const val API_URL = "https://v2.jokeapi.dev/joke/Programming,Pun,Misc?blacklistFlags=nsfw,religious,political,racist,sexist,explicit&type=single"

    fun showRandomJoke(context: Context, onSuccess: (() -> Unit)? = null) {
        thread {
            try {
                val json = URL(API_URL).readText()
                val response = JSONObject(json)

                val joke = if (response.has("joke")) {
                    response.getString("joke")
                } else if (response.has("setup") && response.has("delivery")) {
                    "${response.getString("setup")}\n\n${response.getString("delivery")}"
                } else {
                    "😅 Не удалось загрузить шутку"
                }

                (context as? android.app.Activity)?.runOnUiThread {
                    AlertDialog.Builder(context)
                        .setTitle("😄 Мини-пауза")
                        .setMessage(joke)
                        .setPositiveButton("Продолжить", null)
                        .setCancelable(false)
                        .show()
                        ?.setOnDismissListener { onSuccess?.invoke() }
                }
            } catch (e: Exception) {
                onSuccess?.invoke()
            }
        }
    }
}