package com.crisgaslac.rositaferreapp.datos.modelos

enum class TipoCliente {
    PERSONA, // DNI (8 dígitos)
    EMPRESA  // RUC (11 dígitos)
}

data class Usuario(
    val id: Int = 0,
    val tipoCliente: TipoCliente = TipoCliente.PERSONA,
    val numeroDocumento: String, // DNI o RUC
    val nombreORazonSocial: String = "",
    val claveAcceso: String = "",
    val recordarDispositivo: Boolean = false
)