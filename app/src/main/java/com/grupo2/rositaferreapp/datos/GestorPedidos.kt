package com.grupo2.rositaferreapp.datos

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue

data class PedidoAdmin(
    val codigo: String,
    val usuario: String,
    val cantidadProductos: Int,
    val resumenProductos: String,
    val montoTotal: Double,
    val metodoPago: String,
    val comprobante: String,
    val destino: String,
    val receptor: String,
    val estado: String = "Pendiente de Despacho",
    val chofer: String = "Sin asignar",
    val placa: String = "",
    val confirmadoPorChofer: Boolean = false
)

object GestorPedidos {
    val listaPedidos = mutableStateListOf(
        PedidoAdmin(
            codigo = "ROS-88501",
            usuario = "Jose Luis Paucar",
            cantidadProductos = 3,
            resumenProductos = "2x Cemento Sol 42.5kg, 1x Taladro Bosch 18V",
            montoTotal = 554.90,
            metodoPago = "Yape / Plin",
            comprobante = "Boleta",
            destino = "Av. Próceres de la Independencia 1420, SJL",
            receptor = "Jose Luis Paucar",
            estado = "Pendiente de Despacho"
        ),
        PedidoAdmin(
            codigo = "ROS-88503",
            usuario = "Constructora Los Andes",
            cantidadProductos = 15,
            resumenProductos = "10x Fierro Corrugado 1/2\", 5x Pintura Látex Vencedor",
            montoTotal = 670.00,
            metodoPago = "Transferencia BCP",
            comprobante = "Factura",
            destino = "Jr. Las Flores 450, San Juan de Lurigancho",
            receptor = "Ing. Marcos Vargas",
            estado = "En Camino",
            chofer = "Carlos Mendoza",
            placa = "ABC-452 (Camión 3T)",
            confirmadoPorChofer = false
        )
    )

    var pedidoSeleccionado by mutableStateOf<PedidoAdmin?>(listaPedidos.firstOrNull())

    fun agregarPedido(pedido: PedidoAdmin) {
        listaPedidos.add(0, pedido)
        pedidoSeleccionado = pedido
    }

    fun asignarChofer(codigo: String, chofer: String, placa: String) {
        val index = listaPedidos.indexOfFirst { it.codigo == codigo }
        if (index != -1) {
            val actualizado = listaPedidos[index].copy(
                chofer = chofer,
                placa = placa,
                estado = "En Camino",
                confirmadoPorChofer = false
            )
            listaPedidos[index] = actualizado
            if (pedidoSeleccionado?.codigo == codigo) pedidoSeleccionado = actualizado
        }
    }

    fun confirmarEntregaChofer(codigo: String) {
        val index = listaPedidos.indexOfFirst { it.codigo == codigo }
        if (index != -1) {
            val actualizado = listaPedidos[index].copy(
                estado = "Entregado por Chofer",
                confirmadoPorChofer = true
            )
            listaPedidos[index] = actualizado
            if (pedidoSeleccionado?.codigo == codigo) pedidoSeleccionado = actualizado
        }
    }

    fun aceptarPedidoRecibidoAdmin(codigo: String) {
        val index = listaPedidos.indexOfFirst { it.codigo == codigo }
        if (index != -1) {
            val actualizado = listaPedidos[index].copy(
                estado = "Pedido Recibido"
            )
            listaPedidos[index] = actualizado
            if (pedidoSeleccionado?.codigo == codigo) pedidoSeleccionado = actualizado
        }
    }
}