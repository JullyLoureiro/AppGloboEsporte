package com.example.desafioglobo.ListAdapter

import android.annotation.SuppressLint
import android.app.Activity
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView
import com.example.desafioglobo.model.Artigos
import android.graphics.BitmapFactory
import android.util.Base64
import com.example.desafioglobo.R
import com.squareup.picasso.Picasso


class ListArtigos(private val context: Activity, private val artigos: List<Artigos>)
    : ArrayAdapter<Artigos>(context, R.layout.item_artigo, artigos)  {

    @SuppressLint("ViewHolder", "InflateParams")
    override fun getView(position: Int, view: View?, parent: ViewGroup): View {
        val inflater = context.layoutInflater
        val registro = inflater.inflate(R.layout.item_artigo, null, true)

        val titulo = registro.findViewById(R.id.titulo) as TextView
        val autor = registro.findViewById(R.id.autor) as TextView
        val imagem = registro.findViewById(R.id.image) as ImageView

        val autorstring = "Por ${artigos[position].autor} em ${artigos[position].data}"


        val img = artigos[position].imagens[0]
        Picasso.get().load(img).into(imagem)

        titulo.text = artigos[position].titulo
        autor.text = autorstring


        return registro
    }
}