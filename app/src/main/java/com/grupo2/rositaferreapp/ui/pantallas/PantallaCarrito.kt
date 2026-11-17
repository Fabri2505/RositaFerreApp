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
import androidx.compose.material.icons.automirrored.filled.ArrowForward
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
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.grupo2.rositaferreapp.R
import com.grupo2.rositaferreapp.componentes.BotonPrincipal
import com.grupo2.rositaferreapp.datos.ItemCarrito
import com.grupo2.rositaferreapp.navegacion.ControladorNavegacion
import com.grupo2.rositaferreapp.ui.theme.*
import kotlin.math.roundToInt

private fun formatearSoles(valor: Double): String {
    val redondeado = (valor * 100.0).roundToInt() / 100.0
    val texto = redondeado.toString()
    return if (texto.substringAfter(".").length == 1) "${texto}0" else texto
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PantallaCarrito(navController: ControladorNavegacion) {
    val totalUnidades = navController.contarProductos()
    val totalProductosConIgv = navController.calcularTotalProductos()
    val costoEnvio = if (totalUnidades > 0) 15.00 else 0.00

    val subtotalSinIgv = totalProductosConIgv / 1.18
    val igvMonto = totalProductosConIgv - subtotalSinIgv
    val totalFinalPagar = totalProductosConIgv + costoEnvio

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Image(
                            painter = painterResource(id = R.drawable.ferreteriarosita),
                            contentDescription = null,
                            contentScale = ContentScale.Fit,
                            modifier = Modifier.size(30.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Column {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Text("Ferretería Rosita", fontSize = 15.sp, fontWeight = FontWeight.ExtraBold, color = TextoOscuro)
                                Spacer(modifier = Modifier.width(4.dp))
                                Surface(color = ContenedorBajo, shape = RoundedCornerShape(4.dp)) {
                                    Text(
                                        text = "SAC",
                                        fontSize = 9.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = TextoSecundario,
                                        modifier = Modifier.padding(horizontal = 4.dp, vertical = 1.dp)
                                    )
                                }
                            }
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(Icons.Default.Place, contentDescription = null, tint = NaranjaOscuro, modifier = Modifier.size(12.dp))
                                Spacer(modifier = Modifier.width(2.dp))
                                Text(
                                    text = "Lima / San Martín de Porres • Ca...",
                                    fontSize = 11.sp,
                                    color = TextoSecundario,
                                    maxLines = 1
                                )
                            }
                        }
                    }
                },
                actions = {
                    IconButton(onClick = { }) {
                        Icon(Icons.Default.Notifications, contentDescription = "Notificaciones", tint = TextoOscuro)
                    }
                    Box(
                        modifier = Modifier
                            .padding(end = 12.dp)
                            .size(32.dp)
                            .background(NaranjaOscuro, CircleShape),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(Icons.Default.Person, contentDescription = null, tint = Color.White, modifier = Modifier.size(18.dp))
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = BlancoTarjeta)
            )
        },
        bottomBar = {
            Column(modifier = Modifier.fillMaxWidth()) {
                if (navController.listaCarrito.isNotEmpty()) {
                    Surface(color = BlancoTarjeta, shadowElevation = 8.dp, modifier = Modifier.fillMaxWidth()) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 16.dp, vertical = 10.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Column {
                                Text("TOTAL A PAGAR", fontSize = 10.sp, fontWeight = FontWeight.Bold, color = TextoSecundario)
                                Text(
                                    text = "S/ ${formatearSoles(totalFinalPagar)}",
                                    fontSize = 20.sp,
                                    fontWeight = FontWeight.ExtraBold,
                                    color = TextoOscuro
                                )
                            }
                            Button(
                                onClick = { navController.irAEntrega() },
                                colors = ButtonDefaults.buttonColors(containerColor = NaranjaPrincipal),
                                shape = RoundedCornerShape(10.dp),
                                modifier = Modifier.height(48.dp)
                            ) {
                                Text("Continuar con la\nCompra", fontSize = 12.sp, fontWeight = FontWeight.Bold, lineHeight = 14.sp)
                                Spacer(modifier = Modifier.width(8.dp))
                                Icon(Icons.AutoMirrored.Filled.ArrowForward, contentDescription = null, modifier = Modifier.size(18.dp))
                            }
                        }
                    }
                }

                // Barra de navegación inferior (5 pestañas)
                HorizontalDivider(color = ContenedorAlto)
                Surface(color = BlancoTarjeta, modifier = Modifier.fillMaxWidth()) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp, horizontal = 4.dp),
                        horizontalArrangement = Arrangement.SpaceAround,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        ItemNavCarrito("Inicio", Icons.Default.Home, false) { navController.irACatalogo() }
                        ItemNavCarrito("Categorías", Icons.Default.Menu, false) { navController.irACatalogo() }
                        ItemNavCarrito("Carrito", Icons.Default.ShoppingCart, true, totalUnidades) { }
                        ItemNavCarrito("Pedidos", Icons.Default.ShoppingBag, false) { navController.irAPedidos() }
                        ItemNavCarrito("Perfil", Icons.Default.Person, false) { navController.irALogin() }
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
                Row(verticalAlignment = Alignment.Bottom) {
                    Text("Mi Carrito", fontSize = 20.sp, fontWeight = FontWeight.ExtraBold, color = TextoOscuro)
                    Spacer(modifier = Modifier.width(6.dp))
                    Text("($totalUnidades productos)", fontSize = 13.sp, color = TextoSecundario)
                }

                if (navController.listaCarrito.isNotEmpty()) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .clip(RoundedCornerShape(6.dp))
                            .clickable { navController.vaciarCarrito() }
                            .padding(4.dp)
                    ) {
                        Icon(Icons.Default.Delete, contentDescription = null, tint = AzulSecundario, modifier = Modifier.size(16.dp))
                        Spacer(modifier = Modifier.width(4.dp))
                        Text("Vaciar", fontSize = 12.sp, fontWeight = FontWeight.Bold, color = AzulSecundario)
                    }
                }
            }

            Spacer(modifier = Modifier.height(14.dp))

            if (navController.listaCarrito.isEmpty()) {
                Surface(
                    color = BlancoTarjeta,
                    shape = RoundedCornerShape(14.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(
                        modifier = Modifier.padding(24.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Icon(Icons.Default.ShoppingCart, contentDescription = null, tint = Color.Gray, modifier = Modifier.size(48.dp))
                        Spacer(modifier = Modifier.height(8.dp))
                        Text("Tu carrito está vacío", fontSize = 16.sp, fontWeight = FontWeight.Bold, color = TextoOscuro)
                        Text("Agrega herramientas o materiales desde el catálogo.", fontSize = 12.sp, color = TextoSecundario)
                        Spacer(modifier = Modifier.height(16.dp))
                        BotonPrincipal(texto = "Ir al Catálogo") {
                            navController.irACatalogo()
                        }
                    }
                }
            } else {
                navController.listaCarrito.forEach { item ->
                    TarjetaItemCarrito(
                        item = item,
                        alSumar = { navController.agregarProducto(item.producto, 1) },
                        alRestar = { navController.quitarProducto(item.producto) },
                        alEliminar = { navController.eliminarItemCompleto(item.producto.id) }
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                }

                Spacer(modifier = Modifier.height(6.dp))

                Surface(
                    color = BlancoTarjeta,
                    shape = RoundedCornerShape(14.dp),
                    shadowElevation = 1.dp,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text("Resumen del Pedido", fontSize = 16.sp, fontWeight = FontWeight.ExtraBold, color = TextoOscuro)

                        Spacer(modifier = Modifier.height(12.dp))

                        FilaResumen("Subtotal (sin IGV)", "S/ ${formatearSoles(subtotalSinIgv)}")
                        Spacer(modifier = Modifier.height(6.dp))
                        FilaResumen("IGV (18%)", "S/ ${formatearSoles(igvMonto)}")
                        Spacer(modifier = Modifier.height(6.dp))
                        FilaResumen("Costo estimado de envío", "S/ ${formatearSoles(costoEnvio)}")

                        HorizontalDivider(modifier = Modifier.padding(vertical = 12.dp), color = ContenedorAlto)

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Column {
                                Text("TOTAL ESTIMADO", fontSize = 13.sp, fontWeight = FontWeight.ExtraBold, color = TextoOscuro)
                                Text("Incluye impuestos de ley", fontSize = 10.sp, color = TextoSecundario)
                            }
                            Text(
                                text = "S/ ${formatearSoles(totalFinalPagar)}",
                                fontSize = 22.sp,
                                fontWeight = FontWeight.ExtraBold,
                                color = NaranjaPrincipal
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(12.dp))

                Surface(
                    color = ContenedorBajo,
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Row(
                        modifier = Modifier.padding(14.dp),
                        verticalAlignment = Alignment.Top
                    ) {
                        Box(
                            modifier = Modifier
                                .size(34.dp)
                                .background(ContenedorAlto, RoundedCornerShape(8.dp)),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(Icons.Default.Info, contentDescription = null, tint = AzulSecundario, modifier = Modifier.size(18.dp))
                        }
                        Spacer(modifier = Modifier.width(10.dp))
                        Column {
                            Text("Factura o Boleta Electrónica", fontSize = 13.sp, fontWeight = FontWeight.Bold, color = TextoOscuro)
                            Spacer(modifier = Modifier.height(2.dp))
                            Text(
                                text = buildAnnotatedString {
                                    append("Comprobantes electrónicos automáticos con ")
                                    withStyle(SpanStyle(fontWeight = FontWeight.Bold)) {
                                        append("RUC 20...")
                                    }
                                    append(" para empresas, maestros de obra y contratistas en el siguiente paso.")
                                },
                                fontSize = 11.sp,
                                color = TextoSecundario,
                                lineHeight = 15.sp
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}

@Composable
private fun TarjetaItemCarrito(
    item: ItemCarrito,
    alSumar: () -> Unit,
    alRestar: () -> Unit,
    alEliminar: () -> Unit
) {
    Surface(
        color = BlancoTarjeta,
        shape = RoundedCornerShape(14.dp),
        shadowElevation = 1.dp,
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(modifier = Modifier.padding(12.dp)) {
            Row(verticalAlignment = Alignment.Top) {
                Box(
                    modifier = Modifier
                        .size(72.dp)
                        .clip(RoundedCornerShape(10.dp))
                        .background(ContenedorBajo)
                ) {
                    Image(
                        painter = painterResource(id = item.producto.imagenId),
                        contentDescription = item.producto.nombre,
                        contentScale = ContentScale.Fit,
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(6.dp)
                    )
                    Surface(
                        color = BlancoTarjeta.copy(alpha = 0.9f),
                        shape = RoundedCornerShape(4.dp),
                        modifier = Modifier
                            .align(Alignment.TopStart)
                            .padding(4.dp)
                    ) {
                        Text(
                            text = item.producto.marca,
                            fontSize = 8.sp,
                            fontWeight = FontWeight.ExtraBold,
                            color = TextoOscuro,
                            modifier = Modifier.padding(horizontal = 4.dp, vertical = 1.dp)
                        )
                    }
                }

                Spacer(modifier = Modifier.width(12.dp))

                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = item.producto.nombre,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold,
                        color = TextoOscuro,
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis
                    )
                    Spacer(modifier = Modifier.height(2.dp))
                    Text("SKU: ${item.producto.sku}", fontSize = 11.sp, color = Color.Gray)
                    Spacer(modifier = Modifier.height(4.dp))
                    Row {
                        Text("Unitario: ", fontSize = 12.sp, color = TextoSecundario)
                        Text("S/ ${formatearSoles(item.producto.precio)}", fontSize = 12.sp, fontWeight = FontWeight.Bold, color = TextoOscuro)
                    }
                }

                IconButton(onClick = alEliminar, modifier = Modifier.size(28.dp)) {
                    Icon(Icons.Default.Delete, contentDescription = "Eliminar", tint = Color.Gray, modifier = Modifier.size(18.dp))
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .height(34.dp)
                        .background(ContenedorBajo, RoundedCornerShape(8.dp))
                ) {
                    IconButton(onClick = alRestar, modifier = Modifier.size(32.dp)) {
                        Text("-", fontWeight = FontWeight.ExtraBold, fontSize = 16.sp, color = TextoOscuro)
                    }
                    Text("${item.cantidad}", fontWeight = FontWeight.Bold, fontSize = 13.sp, modifier = Modifier.padding(horizontal = 10.dp))
                    IconButton(onClick = alSumar, modifier = Modifier.size(32.dp)) {
                        Text("+", fontWeight = FontWeight.ExtraBold, fontSize = 15.sp, color = TextoOscuro)
                    }
                }

                Column(horizontalAlignment = Alignment.End) {
                    Text("Subtotal", fontSize = 10.sp, color = TextoSecundario)
                    Text(
                        text = "S/ ${formatearSoles(item.subtotal)}",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.ExtraBold,
                        color = TextoOscuro
                    )
                }
            }
        }
    }
}

@Composable
private fun FilaResumen(etiqueta: String, valor: String) {
    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
        Text(etiqueta, fontSize = 13.sp, color = TextoSecundario)
        Text(valor, fontSize = 13.sp, fontWeight = FontWeight.Bold, color = TextoOscuro)
    }
}

@Composable
private fun ItemNavCarrito(
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