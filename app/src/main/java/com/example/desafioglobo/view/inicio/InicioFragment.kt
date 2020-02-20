package com.example.desafioglobo.view.inicio

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.desafioglobo.R
import com.example.desafioglobo.listadapter.ListArtigos
import com.example.desafioglobo.model.Artigos
import com.example.desafioglobo.utils.NetworkUtils
import com.example.desafioglobo.utils.TransparentProgressDialog
import com.example.desafioglobo.utils.endpoint
import kotlinx.android.synthetic.main.fragment_home.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class InicioFragment : Fragment() {

    var lista: ArrayList<Artigos> = ArrayList()
    private var pd: TransparentProgressDialog? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        pd = TransparentProgressDialog(
            this@InicioFragment.context!!,
            R.drawable.loading,
            ""
        )
        pd!!.show()

        Thread(Runnable {
            Thread.sleep(1000)
            carregarArtigos(getActivity())
        }).start()

        val root = inflater.inflate(R.layout.fragment_home, container, false)
        return root
    }

    fun carregarArtigos(context : FragmentActivity?){
        val retrofit = NetworkUtils.getRetrofitInstance(getString(R.string.link_api))
        val endpoint = retrofit.create(endpoint::class.java)
        val callback = endpoint.getArtigos()

        callback.enqueue(object : Callback<List<Artigos>> {
            override fun onFailure(call: Call<List<Artigos>>, t: Throwable) {
                Toast.makeText(context, t.message, Toast.LENGTH_SHORT).show()
                pd!!.dismiss()
            }

            override fun onResponse(call: Call<List<Artigos>>, response: Response<List<Artigos>>) {
                response.body()?.forEach {
                    lista.add(it)
                }

                val myListAdapter = ListArtigos(context,lista)
                listView.adapter = myListAdapter
                pd!!.dismiss()
            }
        })
    }
}