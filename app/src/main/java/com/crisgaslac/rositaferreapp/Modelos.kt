package com.crisgaslac.rositaferreapp

enum class TipoCliente {
    PERSONA,
    EMPRESA
}

data class Usuario(
    val id: Int = 0,
    val tipoCliente: TipoCliente = TipoCliente.PERSONA,
    val numeroDocumento: String,
    val nombreORazonSocial: String = "",
    val claveAcceso: String = "",
    val recordarDispositivo: Boolean = false
)

data class Producto(
    val id: Int,
    val codigoSku: String,
    val nombre: String,
    val categoria: String,
    val marca: String,
    val precio: Double,
    val stock: Int,
    val descripcion: String
)

data class ItemCarrito(
    val producto: Producto,
    val cantidad: Int
) {
    val subtotal: Double
        get() = producto.precio * cantidad
}