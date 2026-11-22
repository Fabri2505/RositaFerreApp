package com.grupo2.rositaferreapp.ui.pantallas

import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.grupo2.rositaferreapp.R
import com.grupo2.rositaferreapp.datos.PedidoRealizado
import com.grupo2.rositaferreapp.navegacion.ControladorNavegacion
import com.grupo2.rositaferreapp.ui.theme.*
import kotlin.math.roundToInt

private fun formatearSolesPedido(valor: Double): String {
    val redondeado = (valor * 100.0).roundToInt() / 100.0
    val texto = redondeado.toString()
    return if (texto.substringAfter(".").length == 1) "${texto}0" else texto
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PantallaPedidos(navController: ControladorNavegacion) {
    val contexto = LocalContext.current
    val totalCarrito = navController.contarProductos()

    val pedidosAnterioresRecibidos = remember {
        listOf(
            PedidoRealizado(
                codigo = "#ROS-2026-88410",
                cantidadProductos = 4,
                resumenProductos = "2x Bolsa de Cemento Sol 42.5kg, 2x Pintura Látex Vencedor 4L",
                itemsComprados = emptyList(),
                montoTotal = 165.80,
                metodoPago = "Tarjeta Crédito / Débito",
                comprobante = "Boleta de Venta (72849102)",
                destino = "Av. Perú 2450 - San Martín de Porres",
                receptor = navController.nombreUsuarioActivo,
                estado = "Pedido Recibido"
            ),
            PedidoRealizado(
                codigo = "#ROS-2026-88325",
                cantidadProductos = 2,
                resumenProductos = "1x Juego de Llaves Mixtas Stanley, 1x Cinta Aislante 3M",
                itemsComprados = emptyList(),
                montoTotal = 94.50,
                metodoPago = "Yape / Plin",
                comprobante = "Boleta de Venta (72849102)",
                destino = "Jr. Las Magnolias 318 - Los Olivos",
                receptor = navController.nombreUsuarioActivo,
                estado = "Pedido Recibido"
            ),
            PedidoRealizado(
                codigo = "#ROS-2026-88190",
                cantidadProductos = 5,
                resumenProductos = "5x Tubo PVC Agua 1/2\" Pavco 5m",
                itemsComprados = emptyList(),
                montoTotal = 87.50,
                metodoPago = "Yape / Plin",
                comprobante = "Factura Electrónica (20601234567)",
                destino = "Av. Próceres de la Independencia 1420 - SJL",
                receptor = navController.nombreUsuarioActivo,
                estado = "Pedido Recibido"
            )
        )
    }

    val totalOrdenes = navController.listaPedidos.size + pedidosAnterioresRecibidos.size

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Image(
                            painter = painterResource(id = R.drawable.ferreteriarosita),
                            contentDescription = null,
                            contentScale = ContentScale.Fit,
                            modifier = Modifier.size(28.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Column {
                            Text("Mis Pedidos", fontSize = 16.sp, fontWeight = FontWeight.Bold, color = TextoOscuro)
                            Text("Historial de compras • ${navController.nombreUsuarioActivo}", fontSize = 11.sp, color = AzulSecundario)
                        }
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = BlancoTarjeta)
            )
        },
        bottomBar = {
            Column(modifier = Modifier.fillMaxWidth()) {
                HorizontalDivider(color = ContenedorAlto)
                Surface(color = BlancoTarjeta, modifier = Modifier.fillMaxWidth()) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp, horizontal = 4.dp),
                        horizontalArrangement = Arrangement.SpaceAround,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        ItemNavPedido("Inicio", Icons.Default.Home, false) { navController.irACatalogo() }
                        ItemNavPedido("Categorías", Icons.Default.Menu, false) { navController.irACatalogo() }
                        ItemNavPedido("Carrito", Icons.Default.ShoppingCart, false, totalCarrito) { navController.irACarrito() }
                        ItemNavPedido("Pedidos", Icons.Default.ShoppingBag, true) { }
                        ItemNavPedido("Perfil", Icons.Default.Person, false) { navController.irALogin() }
                    }
                }
            }
        },
        containerColor = FondoPantalla
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 16.dp, vertical = 14.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text("Pedidos Realizados", fontSize = 19.sp, fontWeight = FontWeight.ExtraBold, color = TextoOscuro)
                Surface(color = DuraznoClaro, shape = CircleShape) {
                    Text(
                        text = "$totalOrdenes órdenes",
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        color = NaranjaOscuro,
                        modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            // 1. PEDIDOS EN CAMINO (CON SU BOTÓN DE ATENCIÓN DE RECLAMOS DEBAJO)
            navController.listaPedidos.forEach { pedido ->
                TarjetaPedidoGuardado(
                    pedido = pedido,
                    esEnCamino = true,
                    alVerEstado = { navController.irAEstadoPedido(pedido) }
                )

                Spacer(modifier = Modifier.height(8.dp))

                // Botón rojo Atención de Reclamos vinculado a este pedido en camino
                OutlinedButton(
                    onClick = { navController.irAReclamo(pedido) },
                    border = BorderStroke(1.5.dp, Color(0xFFD32F2F)),
                    colors = ButtonDefaults.outlinedButtonColors(
                        containerColor = Color(0xFFFFEBEE),
                        contentColor = Color(0xFFD32F2F)
                    ),
                    shape = RoundedCornerShape(10.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(46.dp)
                ) {
                    Icon(Icons.Default.Warning, contentDescription = null, modifier = Modifier.size(18.dp))
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "Atención de Reclamos",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold
                    )
                }

                Spacer(modifier = Modifier.height(14.dp))
            }

            // 2. HISTORIAL ANTERIOR (PEDIDOS RECIBIDOS)
            Text(
                text = "Historial Anterior",
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                color = TextoSecundario
            )

            Spacer(modifier = Modifier.height(8.dp))

            pedidosAnterioresRecibidos.forEach { pedidoPasado ->
                TarjetaPedidoGuardado(
                    pedido = pedidoPasado,
                    esEnCamino = false,
                    alVerEstado = {
                        Toast.makeText(
                            contexto,
                            "Pedido ${pedidoPasado.codigo} entregado conforme",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                )
                Spacer(modifier = Modifier.height(12.dp))
            }
        }
    }
}

@Composable
private fun TarjetaPedidoGuardado(
    pedido: PedidoRealizado,
    esEnCamino: Boolean,
    alVerEstado: () -> Unit
) {
    val colorTextoEstado = if (esEnCamino) Color(0xFF2E7D32) else AzulSecundario
    val colorFondoEstado = if (esEnCamino) Color(0xFFE8F5E9) else ContenedorBajo

    Surface(
        color = BlancoTarjeta,
        shape = RoundedCornerShape(14.dp),
        shadowElevation = 2.dp,
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(14.dp))
            .clickable { alVerEstado() }
    ) {
        Column(modifier = Modifier.padding(14.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(pedido.codigo, fontSize = 15.sp, fontWeight = FontWeight.ExtraBold, color = TextoOscuro)
                Surface(color = colorFondoEstado, shape = CircleShape) {
                    Text(
                        text = pedido.estado,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        color = colorTextoEstado,
                        modifier = Modifier.padding(horizontal = 10.dp, vertical = 3.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "Productos (${pedido.cantidadProductos}): ${pedido.resumenProductos}",
                fontSize = 12.sp,
                color = TextoOscuro,
                fontWeight = FontWeight.Medium
            )

            Spacer(modifier = Modifier.height(6.dp))

            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(Icons.Default.Place, contentDescription = null, tint = NaranjaOscuro, modifier = Modifier.size(14.dp))
                Spacer(modifier = Modifier.width(4.dp))
                Text(pedido.destino, fontSize = 11.sp, color = TextoSecundario)
            }
            Spacer(modifier = Modifier.height(3.dp))
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(Icons.Default.CheckCircle, contentDescription = null, tint = AzulSecundario, modifier = Modifier.size(14.dp))
                Spacer(modifier = Modifier.width(4.dp))
                Text("${pedido.comprobante} • Pagado con ${pedido.metodoPago}", fontSize = 11.sp, color = TextoSecundario)
            }

            HorizontalDivider(modifier = Modifier.padding(vertical = 10.dp), color = ContenedorAlto)

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = if (esEnCamino) "Toca para ver ruta GPS ›" else "Entregado conforme ✓",
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Bold,
                    color = if (esEnCamino) AzulSecundario else Color(0xFF2E7D32)
                )
                Text(
                    text = "Total: S/ ${formatearSolesPedido(pedido.montoTotal)}",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.ExtraBold,
                    color = NaranjaOscuro
                )
            }
        }
    }
}

@Composable
private fun ItemNavPedido(
    titulo: String,
    icono: ImageVector,
    activo: Boolean,
    badgeNumero: Int = 0,
    alClick: () -> Unit
) {
    val colorItem = if (activo) NaranjaPrincipal else TextoOscuro
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .clip(RoundedCornerShape(8.dp))
            .clickable { alClick() }
            .padding(horizontal = 8.dp, vertical = 4.dp)
    ) {
        Box(contentAlignment = Alignment.TopEnd) {
            Icon(
                imageVector = icono,
                contentDescription = titulo,
                tint = colorItem,
                modifier = Modifier.padding(top = 4.dp, end = 6.dp).size(22.dp)
            )
            if (badgeNumero > 0) {
                Surface(color = NaranjaPrincipal, shape = CircleShape, modifier = Modifier.size(16.dp)) {
                    Box(contentAlignment = Alignment.Center) {
                        Text("$badgeNumero", color = Color.White, fontSize = 9.sp, fontWeight = FontWeight.Bold)
                    }
                }
            }
        }
        Spacer(modifier = Modifier.height(2.dp))
        Text(titulo, fontSize = 11.sp, fontWeight = if (activo) FontWeight.Bold else FontWeight.Medium, color = colorItem)
    }
}