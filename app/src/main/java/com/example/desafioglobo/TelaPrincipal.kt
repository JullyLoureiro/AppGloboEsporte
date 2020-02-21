package com.example.desafioglobo

import android.os.Bundle
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import android.view.Menu
import android.view.MenuItem
import android.app.DatePickerDialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import java.text.SimpleDateFormat
import java.util.*
import androidx.core.app.ComponentActivity
import androidx.core.app.ComponentActivity.ExtraData
import androidx.core.content.ContextCompat.getSystemService
import android.icu.lang.UCharacter.GraphemeClusterBreak.T




class TelaPrincipal : AppCompatActivity() {
    companion object {
        var filtroData: String? = ""
    }

    private var limpar : MenuItem? = null


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
    }

    override  fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.actionbar_artigo, menu)
        limpar = menu.findItem(R.id.action_limpar)
        if(filtroData.equals("")){
            limpar!!.setVisible(false)
        }else{
            limpar!!.setVisible(true)
        }
        return true
    }

    fun filtrar(menu : MenuItem) {
        val builder = AlertDialog.Builder(this)
        val inflater = layoutInflater
        builder.setTitle("Filtro por data")
        val dialogLayout = inflater.inflate(R.layout.alert_dialog, null)
        val edtdata = dialogLayout.findViewById<EditText>(R.id.edtdata)

        edtdata.setOnClickListener {
            val dateFormatterBR = SimpleDateFormat("dd/MM/yyyy", Locale.US)
            val newCalendar = Calendar.getInstance()
            val datePicker = DatePickerDialog(this, { view, year, monthOfYear, dayOfMonth ->
                val newDate = Calendar.getInstance()
                newDate.set(year, monthOfYear, dayOfMonth)
                edtdata.setText(dateFormatterBR.format(newDate.time))
            }, newCalendar.get(Calendar.YEAR), newCalendar.get(
                Calendar.MONTH
            ), newCalendar.get(Calendar.DAY_OF_MONTH)
            )
            datePicker.show()
        }

        builder.setView(dialogLayout)
        builder.setPositiveButton("FILTRAR") { dialogInterface, i -> filtroData = edtdata.text.toString(); filtrar() }
        builder.show()

    }

    fun limparFiltro(menu : MenuItem) {
        filtroData = ""
        filtrar()
    }

    fun filtrar () {
        val intent = Intent(this, TelaPrincipal::class.java)
        startActivity(intent)
        finish()
    }



}
