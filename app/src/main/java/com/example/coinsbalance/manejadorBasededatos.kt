package com.example.coinsbalance

import com.google.firebase.firestore.FirebaseFirestore

class manejadorBasededatos{

    private val dbRef = FirebaseFirestore.getInstance()

    init{

    }

    public fun crearLibro(cuenta: DatosCuenta){
        //el nombre del documento es el hash de usuario con en nombre de la cuenta,
        var nameDoc = "${cuenta.usuario}-${cuenta.nombre}".hashCode().toString()

        dbRef.collection("cuentas").document(nameDoc).set(
            hashMapOf("usuario" to cuenta.usuario,
                "nombre" to cuenta.nombre,
            "saldo" to cuenta.saldo)
        )
    }

    public fun renameLibro(usuario: String,nombreCuenta: String ,nombreNuevo: String){

        var nameDoc =  "${usuario}-${nombreCuenta}".hashCode().toString()

        var saldo : String? = null
        dbRef.collection("cuentas").document(nameDoc).get().addOnSuccessListener {
            saldo = it.get("saldo") as String?
        }

        var nameNuevo =  "${usuario}-${nombreNuevo}".hashCode().toString()

        dbRef.collection("cuentas").document(nameNuevo).set(
            hashMapOf(
                "usuario" to usuario,
                "nombre" to nombreNuevo,
                "saldo" to saldo
            )
        )

        this.eliminarLibro(usuario,nombreCuenta)
    }

    public fun cambiarSaldo(usuario: String,nombreCuenta: String ,saldo: String){

        var nameDoc =  "${usuario}-${nombreCuenta}".hashCode().toString()

        dbRef.collection("cuentas").document(nameDoc).set(
            hashMapOf("saldo" to saldo)
        )
    }

    public fun eliminarLibro(usuario: String, nombreCuenta: String){
        var nameDoc = "$usuario-$nombreCuenta".hashCode().toString()

        dbRef.collection("cuentas").document(nameDoc).delete()
    }

    public fun getLibros(usuario: String){
        var libros = dbRef.collection("cuentas")
    }

    public fun crearTransaccion(transaccion: DatosTransaccion){
        var nameDoc = "${transaccion.usuario}-${transaccion.cuenta}-${transaccion.id}".hashCode().toString()
        dbRef.collection("transaccion").document(nameDoc).set(
            hashMapOf(
                "id" to transaccion.id,
                "usuario" to transaccion.usuario,
                "cuenta" to transaccion.cuenta,
                "descripcion" to transaccion.descripcion,
                "fecha" to transaccion.fecha,
                "tipo" to transaccion.tipo,
                "valor" to transaccion.valor,
            )
        )
     }

    public fun editarTransaccion(transaccion: DatosTransaccion){
        var nameDoc = "${transaccion.usuario}-${transaccion.cuenta}-${transaccion.id}".hashCode().toString()
        dbRef.collection("transaccion").document(nameDoc).set(
            hashMapOf(
                "descripcion" to transaccion.descripcion,
                "fecha" to transaccion.fecha,
                "tipo" to transaccion.tipo,
                "valor" to transaccion.valor,
            )
        )
    }

    public fun eliminarTransaccion(transaccion: DatosTransaccion){
        var nameDoc = "${transaccion.usuario}-${transaccion.cuenta}-${transaccion.id}".hashCode().toString()
        dbRef.collection("transaccion").document(nameDoc).delete()
    }



}