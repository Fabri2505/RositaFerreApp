package com.grupo2.rositaferreapp.ui.pantallas

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.grupo2.rositaferreapp.R
import com.grupo2.rositaferreapp.datos.GestorPedidos
import com.grupo2.rositaferreapp.datos.PedidoAdmin
import com.grupo2.rositaferreapp.navegacion.ControladorNavegacion
import com.grupo2.rositaferreapp.ui.theme.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PantallaPedidos(navController: ControladorNavegacion) {
    val pedidosCliente = GestorPedidos.listaPedidos

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
                            Text("Mis Pedidos", fontSize = 18.sp, fontWeight = FontWeight.ExtraBold, color = TextoOscuro)
                            Text("Toca un pedido para ver GPS y detalle", fontSize = 11.sp, color = AzulSecundario, fontWeight = FontWeight.Bold)
                        }
                    }
                },
                navigationIcon = {
                    IconButton(onClick = { navController.irACatalogo() }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Volver", tint = TextoOscuro)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = BlancoTarjeta)
            )
        },
        bottomBar = {
            Surface(color = BlancoTarjeta, shadowElevation = 8.dp, modifier = Modifier.fillMaxWidth()) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp, horizontal = 4.dp),
                    horizontalArrangement = Arrangement.SpaceAround,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    ItemBarraInferiorCliente("Catálogo", Icons.Default.Home, false) {
                        navController.irACatalogo()
                    }
                    ItemBarraInferiorCliente("Carrito", Icons.Default.ShoppingCart, false) {
                        navController.irACarrito()
                    }
                    ItemBarraInferiorCliente("Pedidos", Icons.Default.List, true) { }
                    ItemBarraInferiorCliente("Salir", Icons.Default.ExitToApp, false) {
                        navController.irABienvenida()
                    }
                }
            }
        },
        containerColor = FondoPantalla
    ) { padding ->
        if (pedidosCliente.isEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding),
                contentAlignment = Alignment.Center
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Icon(Icons.Default.ShoppingCart, contentDescription = null, tint = Color.Gray, modifier = Modifier.size(48.dp))
                    Spacer(modifier = Modifier.height(8.dp))
                    Text("Aún no tienes pedidos registrados", fontSize = 14.sp, color = TextoSecundario)
                }
            }
        } else {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding),
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(14.dp)
            ) {
                items(pedidosCliente, key = { it.codigo }) { pedido ->
                    TarjetaPedidoCliente(
                        pedido = pedido,
                        alIrEstadoPedido = {
                            GestorPedidos.pedidoSeleccionado = pedido
                            navController.irAEstadoPedido()
                        }
                    )
                }
            }
        }
    }
}

@Composable
private fun TarjetaPedidoCliente(
    pedido: PedidoAdmin,
    alIrEstadoPedido: () -> Unit
) {
    val esRecibido = pedido.estado == "Pedido Recibido"

    Surface(
        color = BlancoTarjeta,
        shape = RoundedCornerShape(14.dp),
        shadowElevation = 2.dp,
        modifier = Modifier
            .fillMaxWidth()
            .clickable { alIrEstadoPedido() }
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text("Orden #${pedido.codigo}", fontSize = 15.sp, fontWeight = FontWeight.ExtraBold, color = AzulSecundario)
                Surface(
                    color = when {
                        esRecibido -> Color(0xFFD3F8EF)
                        pedido.confirmadoPorChofer -> Color(0xFFFFF3CD)
                        else -> DuraznoClaro
                    },
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Text(
                        text = pedido.estado.uppercase(),
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Bold,
                        color = when {
                            esRecibido -> Color(0xFF00695C)
                            pedido.confirmadoPorChofer -> Color(0xFF856404)
                            else -> NaranjaOscuro
                        },
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            Text(pedido.resumenProductos, fontSize = 13.sp, fontWeight = FontWeight.Bold, color = TextoOscuro)
            Spacer(modifier = Modifier.height(4.dp))
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(Icons.Default.LocationOn, contentDescription = null, tint = TextoSecundario, modifier = Modifier.size(14.dp))
                Spacer(modifier = Modifier.width(4.dp))
                Text(pedido.destino, fontSize = 12.sp, color = TextoSecundario)
            }

            Spacer(modifier = Modifier.height(10.dp))

            Surface(color = ContenedorBajo, shape = RoundedCornerShape(10.dp), modifier = Modifier.fillMaxWidth()) {
                Row(
                    modifier = Modifier.padding(12.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.weight(1f)) {
                        Box(
                            modifier = Modifier
                                .size(34.dp)
                                .background(AzulSecundario, CircleShape),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(Icons.Default.Person, contentDescription = null, tint = Color.White, modifier = Modifier.size(18.dp))
                        }
                        Spacer(modifier = Modifier.width(10.dp))
                        Column {
                            Text("REPARTIDOR ASIGNADO", fontSize = 9.sp, fontWeight = FontWeight.Bold, color = TextoSecundario)
                            if (pedido.chofer == "Sin asignar") {
                                Text("Por asignar en almacén", fontSize = 12.sp, fontWeight = FontWeight.Bold, color = TextoOscuro)
                            } else {
                                Text(pedido.chofer, fontSize = 13.sp, fontWeight = FontWeight.ExtraBold, color = TextoOscuro)
                                if (pedido.placa.isNotEmpty()) {
                                    Text("Unidad: ${pedido.placa}", fontSize = 11.sp, color = AzulSecundario, fontWeight = FontWeight.SemiBold)
                                }
                            }
                        }
                    }

                    Column(horizontalAlignment = Alignment.End) {
                        Text("TOTAL", fontSize = 9.sp, fontWeight = FontWeight.Bold, color = TextoSecundario)
                        Text("S/ ${String.format("%.2f", pedido.montoTotal)}", fontSize = 15.sp, fontWeight = FontWeight.ExtraBold, color = TextoOscuro)
                    }
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            Button(
                onClick = alIrEstadoPedido,
                colors = ButtonDefaults.buttonColors(containerColor = NaranjaPrincipal),
                shape = RoundedCornerShape(8.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(40.dp)
            ) {
                Icon(Icons.Default.LocationOn, contentDescription = null, modifier = Modifier.size(16.dp))
                Spacer(modifier = Modifier.width(6.dp))
                Text("Rastrear GPS e Información del Pedido", fontSize = 12.sp, fontWeight = FontWeight.Bold)
                Spacer(modifier = Modifier.width(6.dp))
                Icon(Icons.AutoMirrored.Filled.ArrowForward, contentDescription = null, modifier = Modifier.size(14.dp))
            }
        }
    }
}

@Composable
private fun ItemBarraInferiorCliente(titulo: String, icono: ImageVector, activo: Boolean, alClick: () -> Unit) {
    val colorItem = if (activo) NaranjaPrincipal else TextoSecundario
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .clickable { alClick() }
            .padding(horizontal = 10.dp, vertical = 4.dp)
    ) {
        Icon(icono, contentDescription = titulo, tint = colorItem, modifier = Modifier.size(22.dp))
        Spacer(modifier = Modifier.height(2.dp))
        Text(titulo, fontSize = 11.sp, fontWeight = if (activo) FontWeight.Bold else FontWeight.Medium, color = colorItem)
    }
}