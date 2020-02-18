package com.example.desafioglobo.view.detalhado

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.desafioglobo.R
import com.example.desafioglobo.listadapter.ListImageSlider
import kotlinx.android.synthetic.main.activity_detalhes.*
import kotlinx.android.synthetic.main.content_detalhes.*

class Detalhes : AppCompatActivity() {
    var listaimagens : ArrayList<String> = arrayListOf()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detalhes)
        setSupportActionBar(toolbar)

        val intent = intent

        titulo.text = intent.getStringExtra("titulo")
        autor.text = "Por ${intent.getStringExtra("autor")}"
        data.text = "em ${intent.getStringExtra("data")}"
        texto.text = intent.getStringExtra("texto")
        listaimagens = intent.getStringArrayListExtra("imagens")
        imageSliderImplementation()
    }

    private fun imageSliderImplementation() {
        var adapter = ListImageSlider(this, listaimagens)
        viewpager.adapter = adapter
    }

}
