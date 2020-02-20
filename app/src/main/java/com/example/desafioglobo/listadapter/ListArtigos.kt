package com.example.desafioglobo.listadapter

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.database.Cursor
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.example.desafioglobo.model.Artigos
import androidx.cardview.widget.CardView
import com.example.desafioglobo.R
import com.example.desafioglobo.controller.ArtigoController
import com.example.desafioglobo.view.detalhado.Detalhes
import com.squareup.picasso.Picasso


class ListArtigos(private val context: Activity?,private val artigos: List<Artigos>)
    : ArrayAdapter<Artigos>(context!!, R.layout.item_artigo, artigos)  {

    @SuppressLint("ViewHolder", "InflateParams")
    override fun getView(position: Int, view: View?, parent: ViewGroup): View {
        val inflater = context!!.layoutInflater
        val registro = inflater.inflate(R.layout.item_artigo, null, true)

        val titulo = registro.findViewById(R.id.titulo) as TextView
        val autor = registro.findViewById(R.id.autor) as TextView
        val imagem = registro.findViewById(R.id.image) as ImageView
        val imafavoritegem = registro.findViewById(R.id.favorite) as ImageView
        val share = registro.findViewById(R.id.share) as ImageView
        val card = registro.findViewById(R.id.card) as CardView

        val autorstring = "Por ${artigos[position].autor} em ${artigos[position].data}"


        val img = artigos[position].imagens[0]
        Picasso.get().load(img).into(imagem)

        titulo.text = artigos[position].titulo
        autor.text = autorstring

        val dbHandler = ArtigoController(context!!, null)
        val c : Cursor? = dbHandler.verificaFavorito(artigos[position].id)
        if(c!!.moveToFirst()) {
            imafavoritegem.setImageResource(R.drawable.favorite_selected)
        }else{
            imafavoritegem.setImageResource(R.drawable.favorite)
        }

        imafavoritegem.setOnClickListener {

            val cursor : Cursor? = dbHandler.verificaFavorito(artigos[position].id)
            if(cursor!!.moveToFirst()) {
                dbHandler.removeFavorito(artigos[position].id.toString())
                imafavoritegem.setImageResource(R.drawable.favorite)
            }else{
                dbHandler.adicionaArtigoFavorito(artigos[position])
                imafavoritegem.setImageResource(R.drawable.favorite_selected)
            }
        }

        share.setOnClickListener {
            val pm : PackageManager = context.getPackageManager()
            try {

                val waIntent = Intent(Intent.ACTION_SEND)
                waIntent.setType("text/plain")
                pm.getPackageInfo("com.whatsapp", PackageManager.GET_META_DATA)
                waIntent.setPackage("com.whatsapp")

                waIntent.putExtra(Intent.EXTRA_TEXT, artigos[position].titulo)
                context.startActivity(Intent.createChooser(waIntent, context.getString(R.string.compartilhar)))

            } catch (e : PackageManager.NameNotFoundException ) {
                Toast.makeText(context, "Instale o whatsapp!", Toast.LENGTH_SHORT).show()
            }
        }

        card.setOnClickListener {
            val intent = Intent(context, Detalhes::class.java)
            intent.putExtra("id", artigos[position].id)
            intent.putExtra("titulo", artigos[position].titulo)
            intent.putExtra("texto", artigos[position].texto)
            intent.putExtra("autor", artigos[position].autor)
            intent.putExtra("data", artigos[position].data)
            intent.putStringArrayListExtra("imagens", artigos[position].imagens)
            context.startActivity(intent)
        }

        return registro
    }
}