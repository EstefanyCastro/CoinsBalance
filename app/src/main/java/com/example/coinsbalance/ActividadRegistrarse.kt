package com.example.coinsbalance

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.auth.FirebaseAuth

class ActividadRegistrarse : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.actividad_registrarse)

        // Firebase
        val analytics = FirebaseAnalytics.getInstance(this)
        val bundle = Bundle()
        bundle.putString("message", "Integración de firebase completa ")
        analytics.logEvent("InitScreen", bundle)

        // Configuracion ir al view login
        val irInicioSesion = findViewById<TextView>(R.id.redirigirAIniciarSesion)
        irInicioSesion.setOnClickListener {
            irAInicioSesion()
        }
        setup()
    }

    // Configuracion registar cuenta
    private fun setup() {
        val blogout = findViewById<Button>(R.id.registrarseBoton)
        val blogin = findViewById<EditText>(R.id.correoRegistrarse)
        val blogpas = findViewById<EditText>(R.id.contraseñaRegistrarse)
        title = "logueo"

        blogout.setOnClickListener {
            if (blogin.text.isEmpty() || blogpas.text.isEmpty()) {
                errorCampoVacio()
            } else if (blogin.text.isNotEmpty()
                && blogpas.text.isNotEmpty()
            ) {
                FirebaseAuth.getInstance()
                    .createUserWithEmailAndPassword(blogin.text.toString(), blogpas.text.toString())
                    .addOnCompleteListener {
                        if (it.isSuccessful) {
                            irAInicioSesion()
                        } else {
                            error()
                        }
                    }
            }
        }
    }

    // Pantalla error registrar
    private fun error() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Error")
        builder.setMessage("Se ha producido un error autenticando la cuenta")
        builder.setPositiveButton("Aceptar", null)
        val dialog: AlertDialog = builder.create()
        dialog.show()
    }

    private fun errorCampoVacio() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Error")
        builder.setMessage("No pueden haber campos vacios")
        builder.setPositiveButton("Aceptar", null)
        val dialog: AlertDialog = builder.create()
        dialog.show()
    }

    private fun irAInicioSesion() {
        val i = Intent(this, ActividadIniciarSesion::class.java)
        startActivity(i)
    }
}