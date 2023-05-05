package com.example.coinsbalance

import android.widget.ToggleButton
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class ActividadTransaccion : AppCompatActivity() {
    lateinit var toggleButtonIngreso: ToggleButton
    lateinit var toggleButtonEgreso: ToggleButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.actividad_transaccion)

        toggleButtonIngreso = findViewById(R.id.toggleBotonIngreso)
        toggleButtonEgreso = findViewById(R.id.toggleBotonEgreso)

        toggleButtonIngreso.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                // Acciones cuando se activa el botón de Ingreso
                toggleButtonEgreso.isChecked = false
            }
        }

        toggleButtonEgreso.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                // Acciones cuando se activa el botón de Egreso
                toggleButtonIngreso.isChecked = false
            }
        }
    }
}
