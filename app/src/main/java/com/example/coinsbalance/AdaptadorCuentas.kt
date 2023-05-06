package com.example.coinsbalance

import android.app.AlertDialog
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.RecyclerView

class AdaptadorCuenta(val context: Context, val listaCuentas: ArrayList<DatosCuenta>) :
    RecyclerView.Adapter<AdaptadorCuenta.vistaCuentas>() {

    var dbmanager: manejadorBasededatos = manejadorBasededatos()

    inner class vistaCuentas(view: View) : RecyclerView.ViewHolder(view) {
        var nombreCuenta: TextView
        var saldoCuenta: TextView
        var menuMas: ImageView

        init {
            nombreCuenta = view.findViewById(R.id.nombreCuentaLista)
            saldoCuenta = view.findViewById(R.id.saldoCuentaLista)
            menuMas = view.findViewById(R.id.menuMasLista)
            menuMas.setOnClickListener { popupMenus(it) }
        }

        private fun popupMenus(view: View) {
            val position = listaCuentas[adapterPosition]
            val popupMenus = PopupMenu(context, view)
            popupMenus.inflate(R.menu.menu_cuentas)
            popupMenus.setOnMenuItemClickListener {

                // Editar cuenta
                when (it.itemId) {
                    R.id.editText -> {
                        val view =
                            LayoutInflater.from(context).inflate(R.layout.editar_cuenta, null)
                        val cuenta = view.findViewById<EditText>(R.id.nombreNuevaCuenta)
                        val saldo = view.findViewById<EditText>(R.id.saldoNuevaCuenta)
                        AlertDialog.Builder(context)
                            .setView(view)
                            .setPositiveButton("Ok") { dialog, _ ->
                                position.nombre = "Nombre: " + cuenta.text.toString()
                                position.saldo = "Saldo: " + saldo.text.toString()
                                dbmanager.crearLibro(position)
                                notifyDataSetChanged()
                                Toast.makeText(
                                    context,
                                    "La información de la cuenta a cambiado",
                                    Toast.LENGTH_SHORT
                                )
                                    .show()
                                dialog.dismiss()
                            }

                            .setNegativeButton("Cancelar") { dialog, _ ->
                                dialog.dismiss()
                            }

                            .create()
                            .show()



                        true
                    }

                    // Eliminar cuenta
                    R.id.delete -> {
                        AlertDialog.Builder(context)
                            .setTitle("Eliminar")
                            .setIcon(R.drawable.logo_advertencia)
                            .setMessage("¿Estas seguro que quieres eliminar la cuenta?")
                            .setPositiveButton("Si") { dialog, _ ->
                                dbmanager.eliminarLibro(
                                    position.usuario as String,
                                    position.nombre as String
                                )

                                listaCuentas.removeAt(adapterPosition)

                                notifyDataSetChanged()
                                Toast.makeText(context, "Información eliminada", Toast.LENGTH_SHORT)
                                    .show()
                                dialog.dismiss()
                            }

                            .setNegativeButton("No") { dialog, _ ->
                                dialog.dismiss()
                            }

                            .create()
                            .show()

                        true
                    }
                    else -> true
                }
            }

            // Mostrar menu
            popupMenus.show()
            val popup = PopupMenu::class.java.getDeclaredField("mPopup")
            popup.isAccessible = true
            val menu = popup.get(popupMenus)
            menu.javaClass.getDeclaredMethod("setForceShowIcon", Boolean::class.java)
                .invoke(menu, true)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): vistaCuentas {
        val inflater = LayoutInflater.from(parent.context)
        val v = inflater.inflate(R.layout.listar_cuentas, parent, false)
        return vistaCuentas(v)
    }

    override fun onBindViewHolder(holder: vistaCuentas, position: Int) {
        val newList = listaCuentas[position]
        holder.nombreCuenta.text = newList.nombre
        holder.saldoCuenta.text = newList.saldo
    }

    override fun getItemCount(): Int {
        return listaCuentas.size
    }
}