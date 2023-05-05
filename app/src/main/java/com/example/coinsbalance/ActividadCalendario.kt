package com.example.coinsbalance


import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.app.DatePickerDialog
import android.widget.TextView
import java.util.*

class ActividadCalendario : AppCompatActivity() {
    // almacena la fecha seleccionada
    private var selectedYear = 0
    private var selectedMonth = 0
    private var selectedDay = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.actividad_prestamo)

        // Visualizar
        val tvDate = findViewById<TextView>(R.id.tvDate)
        tvDate.setOnClickListener {
            // Es para seleccionar la fecha actual
            val currentDate = Calendar.getInstance()
            var year = currentDate.get(Calendar.YEAR)
            var month = currentDate.get(Calendar.MONTH)
            var day = currentDate.get(Calendar.DAY_OF_MONTH)

            if (tvDate.text.isNotEmpty()) {
                year = this.selectedYear
                month = this.selectedMonth
                day = this.selectedDay
            }


            val listener = DatePickerDialog.OnDateSetListener{ datePicker, selectedYear, selectedMonth, selectedDay ->
                this.selectedYear = selectedYear
                this.selectedMonth = selectedMonth
                this.selectedDay = selectedDay

                tvDate.text = "${selectedMonth + 1}/$selectedDay/$selectedYear"
            }

            //calendario
            val datePicker = DatePickerDialog(this, listener, year, month, day)
            datePicker.show()
        }
    }
}