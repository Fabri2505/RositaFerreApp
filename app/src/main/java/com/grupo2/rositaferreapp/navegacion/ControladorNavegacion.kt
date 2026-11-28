package com.grupo2.rositaferreapp.navegacion

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableDoubleStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.grupo2.rositaferreapp.datos.DatosCatalogo
import com.grupo2.rositaferreapp.datos.ItemCarrito
import com.grupo2.rositaferreapp.datos.PedidoRealizado
import com.grupo2.rositaferreapp.datos.Producto

enum class RutaPantalla {
    BIENVENIDA,
    LOGIN,
    LOGIN_ADMIN,
    PANEL_ADMIN,
    ADMIN_PRODUCTOS,
    REGISTRO,
    CATALOGO,
    DETALLE,
    CARRITO,
    ENTREGA,
    PAGO,
    PAGO_EXITOSO,
    PEDIDOS,
    ESTADO_PEDIDO,
    RECLAMO,
    ADMIN_PEDIDOS
}

class ControladorNavegacion {
    var pantallaActual by mutableStateOf(RutaPantalla.BIENVENIDA)
        private set

    var nombreUsuarioActivo by mutableStateOf("Invitado")
        private set

    var productoSeleccionado by mutableStateOf(DatosCatalogo.listaProductos[0])
        private set

    val listaCarrito = mutableStateListOf<ItemCarrito>()
    val listaPedidos = mutableStateListOf<PedidoRealizado>()

    var pedidoSeleccionado by mutableStateOf<PedidoRealizado?>(null)
        private set

    // Datos guardados del Paso 1: Modalidad de Entrega
    var esDelivery by mutableStateOf(true)
    var direccionEntrega by mutableStateOf("Av. Próceres de la Independencia 1420")
    var distritoEntrega by mutableStateOf("San Martín de Porres")
    var nombreReceptor by mutableStateOf("Carlos Mendoza P.")
    var telefonoReceptor by mutableStateOf("987654321")
    var costoEnvioActivo by mutableDoubleStateOf(15.00)

    // Datos guardados del Paso 2: Método de Pago y Comprobante
    var metodoPagoSeleccionado by mutableStateOf("Yape / Plin")
    var detallePagoGuardado by mutableStateOf("")
    var tipoComprobante by mutableStateOf("Boleta de Venta")
    var documentoComprobante by mutableStateOf("")
    var nombreComprobante by mutableStateOf("")

    var ultimoMontoPagado by mutableDoubleStateOf(0.0)
    var ultimoCodigoPedido by mutableStateOf("#ROS-2026-88492")

    fun irABienvenida() { pantallaActual = RutaPantalla.BIENVENIDA }
    fun irALogin() { pantallaActual = RutaPantalla.LOGIN }
    fun irALoginAdmin() { pantallaActual = RutaPantalla.LOGIN_ADMIN }
    fun irAPanelAdmin() { pantallaActual = RutaPantalla.PANEL_ADMIN }
    fun irAAdminProductos() { pantallaActual = RutaPantalla.ADMIN_PRODUCTOS }
    fun irARegistro() { pantallaActual = RutaPantalla.REGISTRO }
    fun irAAdminPedidos() { pantallaActual = RutaPantalla.ADMIN_PEDIDOS }

    fun irACatalogo(nombreUsuario: String = nombreUsuarioActivo) {
        nombreUsuarioActivo = nombreUsuario
        pantallaActual = RutaPantalla.CATALOGO
    }

    fun irADetalle(producto: Producto) {
        productoSeleccionado = producto
        pantallaActual = RutaPantalla.DETALLE
    }

    fun irACarrito() { pantallaActual = RutaPantalla.CARRITO }
    fun irAEntrega() { pantallaActual = RutaPantalla.ENTREGA }
    fun irAPago() { pantallaActual = RutaPantalla.PAGO }
    fun irAPedidos() { pantallaActual = RutaPantalla.PEDIDOS }

    fun irAEstadoPedido(pedido: PedidoRealizado? = listaPedidos.firstOrNull()) {
        pedidoSeleccionado = pedido
        pantallaActual = RutaPantalla.ESTADO_PEDIDO
    }

    fun irAReclamo(pedido: PedidoRealizado? = listaPedidos.firstOrNull()) {
        pedidoSeleccionado = pedido
        pantallaActual = RutaPantalla.RECLAMO
    }


    fun irAPagoExitoso() {
        val totalFinal = calcularTotalProductos() + costoEnvioActivo
        val totalArticulos = contarProductos()
        val detalleItems = listaCarrito.joinToString(", ") { "${it.cantidad}x${it.producto.nombre}" }
        val copiaItems = listaCarrito.toList()

        val numeroPedido = 88492 + listaPedidos.size
        val codigoNuevo = "#ROS-2026-$numeroPedido"

        ultimoMontoPagado = totalFinal
        ultimoCodigoPedido = codigoNuevo

        val nuevoPedido = PedidoRealizado(
            codigo = codigoNuevo,
            cantidadProductos = totalArticulos,
            resumenProductos = detalleItems,
            itemsComprados = copiaItems,
            montoTotal = totalFinal,
            metodoPago = metodoPagoSeleccionado,
            comprobante = "$tipoComprobante ($documentoComprobante)",
            destino = if (esDelivery) "$direccionEntrega -$distritoEntrega" else "Recojo en Tienda Rosita",
            receptor = nombreReceptor
        )

        listaPedidos.add(index = 0, element = nuevoPedido)
        pedidoSeleccionado = nuevoPedido

        listaCarrito.clear()
        pantallaActual = RutaPantalla.PAGO_EXITOSO
    }

    fun agregarProducto(producto: Producto, cantidadAgregar: Int = 1) {
        var encontrado = false
        for (i in listaCarrito.indices) {
            val item = listaCarrito[i]
            if (item.producto.id == producto.id) {
                listaCarrito[i] = ItemCarrito(producto, item.cantidad + cantidadAgregar)
                encontrado = true
                break
            }
        }
        if (!encontrado) {
            listaCarrito.add(ItemCarrito(producto, cantidadAgregar))
        }
    }

    fun quitarProducto(producto: Producto) {
        for (i in listaCarrito.indices) {
            val item = listaCarrito[i]
            if (item.producto.id == producto.id) {
                if (item.cantidad > 1) {
                    listaCarrito[i] = ItemCarrito(producto, item.cantidad - 1)
                } else {
                    listaCarrito.removeAt(i)
                }
                break
            }
        }
    }

    fun eliminarItemCompleto(idProducto: Int) {
        for (i in listaCarrito.indices) {
            if (listaCarrito[i].producto.id == idProducto) {
                listaCarrito.removeAt(i)
                break
            }
        }
    }

    fun vaciarCarrito() { listaCarrito.clear() }

    fun obtenerCantidad(idProducto: Int): Int {
        for (item in listaCarrito) {
            if (item.producto.id == idProducto) return item.cantidad
        }
        return 0
    }

    fun calcularTotalProductos(): Double {
        var total = 0.0
        for (item in listaCarrito) {
            total += item.subtotal
        }
        return total
    }

    fun contarProductos(): Int {
        var cantidadTotal = 0
        for (item in listaCarrito) {
            cantidadTotal += item.cantidad
        }
        return cantidadTotal
    }
}