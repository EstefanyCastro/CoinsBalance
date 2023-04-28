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

class ActividadIniciarSesion : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.actividad_iniciar_sesion)

        // Firebase
        val analytics = FirebaseAnalytics.getInstance(this)
        val bundle = Bundle()
        bundle.putString("message", "Integracion de firebase completa ")
        analytics.logEvent("InitScreen", bundle)

        // Configuracion ir al view registro
        val irRegistro = findViewById<TextView>(R.id.redirigirARegistrar)
        irRegistro.setOnClickListener {
            irAlRegistro()
        }
        setup()
    }

    // Verificar logueo
    private fun setup() {
        val blogout = findViewById<Button>(R.id.iniciarSesionBoton)
        val blogin = findViewById<EditText>(R.id.correoIniciarSesion)
        val blogpas = findViewById<EditText>(R.id.contraseñaIniciarSesion)
        title = "logueo"

        blogout.setOnClickListener {
            if (blogin.text.isEmpty() || blogpas.text.isEmpty()) {
                errorCampoVacio()
            } else if (blogin.text.isNotEmpty()
                && blogpas.text.isNotEmpty()
            ) {
                FirebaseAuth.getInstance()
                    .signInWithEmailAndPassword(blogin.text.toString(), blogpas.text.toString())
                    .addOnCompleteListener {
                        if (it.isSuccessful) {
                            irACuentas()
                        } else {
                            error()
                            findViewById<EditText>(R.id.contraseñaIniciarSesion).setText("");
                        }
                    }
            }
        }
    }

    // Pantalla error logueo
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

    private fun irAlRegistro() {
        val i = Intent(this, ActividadRegistrarse::class.java)
        startActivity(i)
    }

    private fun irACuentas() {
        val i = Intent(this, ActividadCuentas::class.java)
        startActivity(i)
    }
}