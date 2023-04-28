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

class SignUpActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)

        // Firebase
        val analytics = FirebaseAnalytics.getInstance(this)
        val bundle = Bundle()
        bundle.putString("message", "Integracion de firebase completa ")
        analytics.logEvent("InitScreen", bundle)

        // Configuracion ir al view login
        val irRegistro = findViewById<TextView>(R.id.LoginRedirigir)
        irRegistro.setOnClickListener {
            irAlRegistro()
        }
        setup()
    }

    // Configuracion registar cuenta
    private fun setup() {
        val blogout = findViewById<Button>(R.id.SignUpButton)
        val blogin = findViewById<EditText>(R.id.SignUpEmail)
        val blogpas = findViewById<EditText>(R.id.SignUpPassword)
        title = "logueo"

        blogout.setOnClickListener {
            if (blogin.text.isNotEmpty()
                && blogpas.text.isNotEmpty()
            ) {
                FirebaseAuth.getInstance()
                    .createUserWithEmailAndPassword(blogin.text.toString(), blogpas.text.toString())
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

    // Pantalla error registrar
    private fun error() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Error")
        builder.setMessage("Se ha producido un error autenticando la cuenta")
        builder.setPositiveButton("Aceptar", null)
        val dialog: AlertDialog = builder.create()
        dialog.show()
    }

    private fun irAlRegistro() {
        val i = Intent(this, LogInActivity::class.java)
        startActivity(i)
    }

    private fun irACuentas() {
        val i = Intent(this, CuentasActivity::class.java)
        startActivity(i)
    }
}