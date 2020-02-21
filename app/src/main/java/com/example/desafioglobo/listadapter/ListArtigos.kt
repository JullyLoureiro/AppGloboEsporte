package com.example.desafioglobo.listadapter

import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.PackageManager
import android.database.Cursor
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.*
import com.example.desafioglobo.model.Artigos
import androidx.cardview.widget.CardView
import androidx.fragment.app.FragmentActivity
import com.example.desafioglobo.R
import com.example.desafioglobo.controller.ArtigoController
import com.example.desafioglobo.view.detalhado.Detalhes
import com.squareup.picasso.Picasso
import android.widget.ArrayAdapter
import com.example.desafioglobo.TelaPrincipal


class ListArtigos(private val context: FragmentActivity?, private val artigos: List<Artigos>, private val sugestoes: List<String>, private val buscaString: String?)
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
        val logo = registro.findViewById(R.id.logo) as ImageView
        val busca = registro.findViewById(R.id.busca) as AutoCompleteTextView

        val autorstring = "Por ${artigos[position].autor} em ${artigos[position].data}"

       if(position == 0){
           logo.visibility = View.VISIBLE
           busca.visibility = View.VISIBLE
       }else {
           logo.visibility = View.GONE
           busca.visibility = View.GONE
       }


        val adapter : ArrayAdapter<String> = ArrayAdapter(context, android.R.layout.select_dialog_item, sugestoes)

        busca.setAdapter(adapter)
        busca.setThreshold(1)

        busca.setText(buscaString)

        val img = artigos[position].imagens[0]
        Picasso.get().load(img).into(imagem)

        titulo.text = artigos[position].titulo
        autor.text = autorstring

        val dbHandler = ArtigoController(context, null)
        val c : Cursor? = dbHandler.verificaFavorito(artigos[position].id)
        if(c!!.moveToFirst()) {
            imafavoritegem.setImageResource(R.drawable.favorite_selected)
        }else{
            imafavoritegem.setImageResource(R.drawable.favorite)
        }

        busca.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE || actionId == EditorInfo.IME_ACTION_NEXT || actionId == EditorInfo.IME_ACTION_GO) {
                val intent = Intent(context, TelaPrincipal::class.java)
                intent.putExtra("busca", busca.text.toString())
                context.startActivity(intent)
                context.finish()
            }
            true
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

                var stringwpp = "${artigos[position].titulo}  - Acesse em: ${artigos[position].link}"
                waIntent.putExtra(Intent.EXTRA_TEXT, stringwpp)
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