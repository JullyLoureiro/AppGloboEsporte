package com.example.desafioglobo.view.favoritos

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import com.example.desafioglobo.R
import com.example.desafioglobo.controller.ArtigoController
import com.example.desafioglobo.listadapter.ListFavoritos
import com.example.desafioglobo.model.Artigos
import com.example.desafioglobo.utils.TransparentProgressDialog
import kotlinx.android.synthetic.main.fragment_home.*

class FavoritosFragment : Fragment() {
    var lista: ArrayList<Artigos> = ArrayList()
    private var pd: TransparentProgressDialog? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        pd = TransparentProgressDialog(
            this@FavoritosFragment.context!!,
            R.drawable.loading,
            ""
        )
        pd!!.show()

        Thread(Runnable {
            Thread.sleep(1000)
            carregarFavoritos(getActivity())
        }).start()

        val root = inflater.inflate(R.layout.fragment_favorites, container, false)
        return root
    }

    fun carregarFavoritos(context : FragmentActivity?) {
        val dbHandler = ArtigoController(context!!, null)

        lista = dbHandler.carregaArtigos()!!

        val myListAdapter = ListFavoritos(context,lista)

        activity!!.runOnUiThread {
            listView.adapter = myListAdapter
            pd!!.dismiss()
        }
    }
}