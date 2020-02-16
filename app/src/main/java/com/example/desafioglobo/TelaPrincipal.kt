package com.example.desafioglobo

import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.desafioglobo.ListAdapter.ListArtigos
import com.example.desafioglobo.model.Artigos
import com.example.desafioglobo.utils.NetworkUtils
import com.example.desafioglobo.utils.endpoint
import kotlinx.android.synthetic.main.fragment_home.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response



class TelaPrincipal : AppCompatActivity() {

    var lista: MutableList<Artigos> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val navView: BottomNavigationView = findViewById(R.id.nav_view)

        val navController = findNavController(R.id.nav_host_fragment)
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_home, R.id.navigation_favorites
            )
        )


        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)

        carregarArtigos()
    }

    fun carregarArtigos(){
        val retrofit = NetworkUtils.getRetrofitInstance(getString(R.string.link_api))
        val endpoint = retrofit.create(endpoint::class.java)
        val callback = endpoint.getArtigos()

        callback.enqueue(object : Callback<List<Artigos>> {
            override fun onFailure(call: Call<List<Artigos>>, t: Throwable) {
                Toast.makeText(baseContext, t.message, Toast.LENGTH_SHORT).show()
            }

            override fun onResponse(call: Call<List<Artigos>>, response: Response<List<Artigos>>) {
                response.body()?.forEach {
                    lista.add(it)
                }

                val myListAdapter = ListArtigos(this@TelaPrincipal,lista)
                listView.adapter = myListAdapter

                listView.setOnItemClickListener(){adapterView, view, position, id ->
                    Toast.makeText(this@TelaPrincipal, "Click", Toast.LENGTH_LONG).show()
                }

            }
        })
    }
}
