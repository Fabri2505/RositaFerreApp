package com.grupo2.rositaferreapp.ui.pantallas

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.grupo2.rositaferreapp.R
import com.grupo2.rositaferreapp.componentes.BotonPrincipal
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
    val totalCarrito = navController.contarProductos()

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
                        text = "${navController.listaPedidos.size} órdenes",
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        color = NaranjaOscuro,
                        modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            if (navController.listaPedidos.isEmpty()) {
                Surface(
                    color = BlancoTarjeta,
                    shape = RoundedCornerShape(14.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(
                        modifier = Modifier.padding(24.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Icon(Icons.Default.ShoppingBag, contentDescription = null, tint = Color.Gray, modifier = Modifier.size(48.dp))
                        Spacer(modifier = Modifier.height(8.dp))
                        Text("Aún no tienes pedidos guardados", fontSize = 16.sp, fontWeight = FontWeight.Bold, color = TextoOscuro)
                        Text("Cuando confirmes una compra aparecerá aquí.", fontSize = 12.sp, color = TextoSecundario)
                        Spacer(modifier = Modifier.height(16.dp))
                        BotonPrincipal(texto = "Ir a Comprar") {
                            navController.irACatalogo()
                        }
                    }
                }
            } else {
                navController.listaPedidos.forEach { pedido ->
                    TarjetaPedidoGuardado(pedido)
                    Spacer(modifier = Modifier.height(12.dp))
                }
            }
        }
    }
}

@Composable
private fun TarjetaPedidoGuardado(pedido: PedidoRealizado) {
    val verdeExito = Color(0xFF2E7D32)
    val verdeFondo = Color(0xFFE8F5E9)

    Surface(
        color = BlancoTarjeta,
        shape = RoundedCornerShape(14.dp),
        shadowElevation = 2.dp,
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(modifier = Modifier.padding(14.dp)) {
            // Fila 1: Código y Estado verde
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(pedido.codigo, fontSize = 15.sp, fontWeight = FontWeight.ExtraBold, color = TextoOscuro)
                Surface(color = verdeFondo, shape = CircleShape) {
                    Text(
                        text = pedido.estado,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        color = verdeExito,
                        modifier = Modifier.padding(horizontal = 10.dp, vertical = 3.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Fila 2: Productos comprados
            Text(
                text = "Productos (${pedido.cantidadProductos}): ${pedido.resumenProductos}",
                fontSize = 12.sp,
                color = TextoOscuro,
                fontWeight = FontWeight.Medium
            )

            Spacer(modifier = Modifier.height(6.dp))

            // Fila 3: Destino y Comprobante
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

            // Fila 4: Total pagado y entrega estimada
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text("Entrega est.: 3 días hábiles", fontSize = 11.sp, color = TextoSecundario)
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