package com.example.desafioglobo.view.detalhado

import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import com.example.desafioglobo.R

import kotlinx.android.synthetic.main.activity_detalhes.*
import kotlinx.android.synthetic.main.content_detalhes.*

class Detalhes : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detalhes)
        setSupportActionBar(toolbar)

        val intent = intent

        titulo.text = intent.getStringExtra("titulo")
        autor.text = intent.getStringExtra("autor")
        data.text = "em ${intent.getStringExtra("data")}"
        texto.text = intent.getStringExtra("texto")
    }

}
