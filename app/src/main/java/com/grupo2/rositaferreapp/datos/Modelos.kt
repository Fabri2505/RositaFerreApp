package com.grupo2.rositaferreapp.datos

enum class TipoCliente {
    PERSONA,
    EMPRESA,
    ADMIN
}

data class Usuario(
    val documento: String,
    val nombre: String,
    val clave: String,
    val tipo: TipoCliente
)

data class Producto(
    val id: Int,
    val sku: String,
    val nombre: String,
    val categoria: String,
    val marca: String,
    val subtituloMarca: String,
    val precio: Double,
    val precioAnterior: Double?,
    val detalleExtra: String,
    val etiqueta: String,
    val esEtiquetaNaranja: Boolean = false,
    val stock: Int,
    val imagenId: Int,
    val imagenesCarrusel: List<Int> = listOf(imagenId, imagenId, imagenId),
    val incluyeCaja: List<String> = listOf(
        "1 Producto original sellado de fábrica",
        "Manual de usuario y póliza de garantía oficial"
    )
)

data class ItemCarrito(
    val producto: Producto,
    val cantidad: Int
) {
    val subtotal: Double
        get() = producto.precio * cantidad
}
data class PedidoRealizado(
    val codigo: String,
    val cantidadProductos: Int,
    val resumenProductos: String,
    val itemsComprados: List<ItemCarrito>,
    val montoTotal: Double,
    val metodoPago: String,
    val comprobante: String,
    val destino: String,
    val receptor: String,
    val estado: String = "En Camino"
)