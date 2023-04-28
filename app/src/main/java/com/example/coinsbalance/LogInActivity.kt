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


class LogInActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        // Firebase
        val analytics = FirebaseAnalytics.getInstance(this)
        val bundle = Bundle()
        bundle.putString("message", "Integracion de firebase completa ")
        analytics.logEvent("InitScreen", bundle)

        // Configuracion ir al view registro
        val irRegistro = findViewById<TextView>(R.id.RegisterRedirigir)
        irRegistro.setOnClickListener {
            irAlRegistro()
        }
        setup()
    }

    // Verificar logueo
    private fun setup() {
        val blogout = findViewById<Button>(R.id.LogInButton)
        val blogin = findViewById<EditText>(R.id.LogInEmail)
        val blogpas = findViewById<EditText>(R.id.LogInPassword)
        title = "logueo"

        blogout.setOnClickListener {
            if (blogin.text.isNotEmpty()
                && blogpas.text.isNotEmpty()
            ) {
                FirebaseAuth.getInstance()
                    .signInWithEmailAndPassword(blogin.text.toString(), blogpas.text.toString())
                    .addOnCompleteListener {
                        if (it.isSuccessful) {
                            irACuentas()
                        } else {
                            error()
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

    private fun irAlRegistro() {
        val i = Intent(this, SignUpActivity::class.java)
        startActivity(i)
    }

    private fun irACuentas() {
        val i = Intent(this, CuentasActivity::class.java)
        startActivity(i)
    }
}