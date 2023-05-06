package com.example.coinsbalance

data class DatosTransaccion(
    var id: String? = null,
    var usuario: String? = null,
    var cuenta: String? = null,
    var descripcion: String? = null,
    var fecha: String? = null,
    var tipo: String? = null,
    var valor: String? = null,
)
