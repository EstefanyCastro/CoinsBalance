package com.example.coinsbalance

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton

class ActividadCuentas : AppCompatActivity() {
    private lateinit var añadirBoton: FloatingActionButton
    private lateinit var recyclerView: RecyclerView
    private lateinit var cuentaLista: ArrayList<UserData>
    private lateinit var adaptadorCuenta: AdaptadorCuenta
    var cerrar = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.actividad_cuentas)
        cuentaLista = ArrayList()
        añadirBoton = findViewById(R.id.añadirCuentaBoton)
        recyclerView = findViewById(R.id.listaCuentas)
        adaptadorCuenta = AdaptadorCuenta(this, cuentaLista)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adaptadorCuenta
        añadirBoton.setOnClickListener { anadirInformacion() }
    }

    private fun anadirInformacion() {
        val inflter = LayoutInflater.from(this)
        val view = inflter.inflate(R.layout.anadir_cuenta, null)
        val cuenta = view.findViewById<EditText>(R.id.nombreCuentaAñadir)
        val saldo = view.findViewById<EditText>(R.id.saldoCuentaAñadir)
        val anadirAlerta = AlertDialog.Builder(this)

		var dbmanager : manejadorBasededatos = manejadorBasededatos()

        anadirAlerta.setView(view)
        anadirAlerta.setPositiveButton("Ok") { dialog, _ ->
            val names = cuenta.text.toString()
            val number = saldo.text.toString()
            cuentaLista.add(UserData("Nombre : $names", "Saldo : $number"))
            adaptadorCuenta.notifyDataSetChanged()
            Toast.makeText(this, "Cuenta añadida exitosamente", Toast.LENGTH_SHORT).show()
            dialog.dismiss()
        }

        anadirAlerta.setNegativeButton("Cancelar") { dialog, _ ->
            dialog.dismiss()
            Toast.makeText(this, "Cancelar", Toast.LENGTH_SHORT).show()
        }

        anadirAlerta.create()
        anadirAlerta.show()
    }

    // Devuelta al inicio de sesion
    private fun irAInicioSesion() {
        val i = Intent(this, ActividadIniciarSesion::class.java)
        startActivity(i)
    }

    // Controlar el boton de atras
    override fun onBackPressed() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("SALIR")
        builder.setMessage("¿Estás seguro que desesa salir de la aplicación?")
        builder.setPositiveButton(
            "SI"
        ) { dialogInterface, i ->
            cerrar = true
            irAInicioSesion()
        }
        builder.setNegativeButton(
            "NO"
        ) { dialogInterface, i ->
            cerrar = false
        }

        builder.create()
        builder.show()
    }
}
