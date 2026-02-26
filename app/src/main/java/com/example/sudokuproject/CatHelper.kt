package com.example.sudokuproject

import android.app.AlertDialog
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Handler
import android.os.Looper
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.Toast
import org.json.JSONArray
import java.net.URL
import kotlin.concurrent.thread

object CatHelper {

    private const val API_URL = "https://api.thecatapi.com/v1/images/search?size=med"

    fun showCuteCat(context: Context) {
        val progressBar = ProgressBar(context).apply {
            isIndeterminate = true
        }

        val catImage = ImageView(context).apply {
            scaleType = ImageView.ScaleType.FIT_CENTER
            visibility = android.view.View.GONE
        }

        val linearLayout = LinearLayout(context).apply {
            orientation = LinearLayout.VERTICAL
            setPadding(32, 16, 32, 16)
            addView(progressBar)
            addView(catImage)
        }

        val builder = AlertDialog.Builder(context)
            .setTitle("🐱 Не грусти!")
            .setView(linearLayout)
            .setPositiveButton("Попробовать ещё", null)
            .setCancelable(false)

        val dialog = builder.show()

        thread {
            try {
                val json = URL(API_URL).readText()
                val array = JSONArray(json)
                val imageUrl = array.getJSONObject(0).getString("url")

                val bitmap: Bitmap? = BitmapFactory.decodeStream(URL(imageUrl).openStream())

                Handler(Looper.getMainLooper()).post {
                    progressBar.visibility = android.view.View.GONE
                    if (bitmap != null) {
                        catImage.setImageBitmap(bitmap)
                        catImage.visibility = android.view.View.VISIBLE
                    } else {
                        Toast.makeText(context, "😿 Не удалось загрузить котика", Toast.LENGTH_SHORT).show()
                    }
                }
            } catch (e: Exception) {
                Handler(Looper.getMainLooper()).post {
                    progressBar.visibility = android.view.View.GONE
                    Toast.makeText(context, "😿 Нет интернета для котиков", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}