package com.example.project4

import MyDatabaseHelper
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import android.graphics.LinearGradient
import android.graphics.Shader
import android.util.DisplayMetrics
import androidx.core.content.ContextCompat
import kotlin.random.Random


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val btnEntrar = findViewById<Button>(R.id.buttonEntrar)
        val titulo = findViewById<TextView>(R.id.textView)

        val displayMetrics = DisplayMetrics()
        windowManager.defaultDisplay.getMetrics(displayMetrics)
        val screenWidth = displayMetrics.widthPixels

        val startX = 0f // início na esquerda
        val startY = titulo.textSize // início na base do TextView
        val endX = 0f // fim alinhado à esquerda
        val endY = 199f // fim no topo do TextView

        val textShader = LinearGradient(
            startX, startY, endX, endY,
            intArrayOf(
                ContextCompat.getColor(this, android.R.color.white),      // Branco
                ContextCompat.getColor(this, android.R.color.holo_red_dark) // Vermelho-sangue
            ),
            null,
            Shader.TileMode.CLAMP
        )

        // Aplicar o Shader ao texto
        titulo.paint.shader = textShader

        // Aplicar o Shader ao texto

        btnEntrar.setOnClickListener{
            val intent = Intent(this, SecondActivity::class.java)
            startActivity(intent)
        }
    }
}
