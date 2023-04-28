package com.example.coinsbalance

import android.app.AlertDialog
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.RecyclerView

class AdaptadorCuenta(val c: Context, val userList: ArrayList<UserData>) :
    RecyclerView.Adapter<AdaptadorCuenta.UserViewHolder>() {

    inner class UserViewHolder(val v: View) : RecyclerView.ViewHolder(v) {
        var name: TextView
        var mbNum: TextView
        var mMenus: ImageView

        init {
            name = v.findViewById<TextView>(R.id.tituloCuenta)
            mbNum = v.findViewById<TextView>(R.id.saldoDeCuenta)
            mMenus = v.findViewById(R.id.menuMas)
            mMenus.setOnClickListener { popupMenus(it) }
        }

        private fun popupMenus(v: View) {
            val position = userList[adapterPosition]
            val popupMenus = PopupMenu(c, v)
            popupMenus.inflate(R.menu.menu_cuentas)
            popupMenus.setOnMenuItemClickListener {

                when (it.itemId) {
                    R.id.editText -> {
                        val v = LayoutInflater.from(c).inflate(R.layout.editar_cuenta, null)
                        val name = v.findViewById<EditText>(R.id.nombreNuevaCuenta)
                        val number = v.findViewById<EditText>(R.id.saldoNuevaCuenta)
                        AlertDialog.Builder(c)
                            .setView(v)
                            .setPositiveButton("Ok") { dialog, _ ->
                                position.cuenta = "Nombre: " + name.text.toString()
                                position.saldo = "Saldo: " + number.text.toString()
                                notifyDataSetChanged()
                                Toast.makeText(c, "User Information is Edited", Toast.LENGTH_SHORT)
                                    .show()
                                dialog.dismiss()
                            }

                            .setNegativeButton("Cancel") { dialog, _ ->
                                dialog.dismiss()
                            }

                            .create()
                            .show()

                        true
                    }

                    R.id.delete -> {
                        AlertDialog.Builder(c)
                            .setTitle("Delete")
                            .setIcon(R.drawable.warning)
                            .setMessage("Are you sure delete this Information")
                            .setPositiveButton("Yes") { dialog, _ ->
                                userList.removeAt(adapterPosition)
                                notifyDataSetChanged()
                                Toast.makeText(c, "Deleted this Information", Toast.LENGTH_SHORT)
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

            popupMenus.show()
            val popup = PopupMenu::class.java.getDeclaredField("mPopup")
            popup.isAccessible = true
            val menu = popup.get(popupMenus)
            menu.javaClass.getDeclaredMethod("setForceShowIcon", Boolean::class.java)
                .invoke(menu, true)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val v = inflater.inflate(R.layout.lista_cuentas, parent, false)
        return UserViewHolder(v)
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        val newList = userList[position]
        holder.name.text = newList.cuenta
        holder.mbNum.text = newList.saldo
    }

    override fun getItemCount(): Int {
        return userList.size
    }
}