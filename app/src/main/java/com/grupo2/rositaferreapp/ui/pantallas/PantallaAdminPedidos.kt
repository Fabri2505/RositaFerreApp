package com.grupo2.rositaferreapp.ui.pantallas

import android.widget.Toast
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
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.grupo2.rositaferreapp.R
import com.grupo2.rositaferreapp.datos.GestorPedidos
import com.grupo2.rositaferreapp.datos.PedidoAdmin
import com.grupo2.rositaferreapp.navegacion.ControladorNavegacion
import com.grupo2.rositaferreapp.ui.theme.*

data class ChoferReparto(
    val nombre: String,
    val placa: String,
    val telefono: String
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PantallaAdminPedidos(navController: ControladorNavegacion) {
    val contexto = LocalContext.current
    var filtroEstado by remember { mutableStateOf("Todos") }

    val listaChoferes = remember {
        listOf(
            ChoferReparto("Carlos Mendoza", "ABC-452 (Camión 3T)", "987 654 321"),
            ChoferReparto("Roberto Quispe", "XYZ-891 (Furgón)", "945 112 334"),
            ChoferReparto("Luis Huamán", "MNO-120 (Camioneta)", "912 887 440")
        )
    }

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
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Text("Rosita S.A.C.", fontSize = 16.sp, fontWeight = FontWeight.ExtraBold, color = TextoOscuro)
                                Spacer(modifier = Modifier.width(6.dp))
                                Surface(color = NaranjaPrincipal, shape = RoundedCornerShape(4.dp)) {
                                    Text("ADMIN", color = Color.White, fontSize = 9.sp, fontWeight = FontWeight.Bold, modifier = Modifier.padding(horizontal = 4.dp, vertical = 2.dp))
                                }
                            }
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(Icons.Default.Home, contentDescription = null, tint = AzulSecundario, modifier = Modifier.size(12.dp))
                                Spacer(modifier = Modifier.width(2.dp))
                                Text("SJL Principal • Pedidos", fontSize = 11.sp, color = AzulSecundario, fontWeight = FontWeight.Bold)
                            }
                        }
                    }
                },
                actions = {
                    IconButton(onClick = { }) {
                        Icon(Icons.Default.Notifications, contentDescription = "Alertas")
                    }
                    Box(
                        modifier = Modifier
                            .padding(end = 12.dp)
                            .size(32.dp)
                            .background(NaranjaOscuro, CircleShape)
                            .clickable { navController.irABienvenida() },
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(Icons.Default.Person, contentDescription = null, tint = Color.White, modifier = Modifier.size(18.dp))
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = BlancoTarjeta)
            )
        },
        bottomBar = {
            Surface(color = BlancoTarjeta, shadowElevation = 8.dp, modifier = Modifier.fillMaxWidth()) {
                Row(
                    modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp, horizontal = 4.dp),
                    horizontalArrangement = Arrangement.SpaceAround,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    ItemBarraInferiorAdminPedidos("Dashboard", Icons.Default.Menu, false) { navController.irAPanelAdmin() }
                    ItemBarraInferiorAdminPedidos("Productos", Icons.Default.ShoppingCart, false) { navController.irAAdminProductos() }
                    ItemBarraInferiorAdminPedidos("Inventario", Icons.Default.Home, false) { }
                    ItemBarraInferiorAdminPedidos("Pedidos", Icons.Default.List, true) { }
                    ItemBarraInferiorAdminPedidos("Ajustes", Icons.Default.Settings, false) { }
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
                .padding(horizontal = 16.dp, vertical = 12.dp)
        ) {
            Text("Gestión de Pedidos y Despacho", fontSize = 22.sp, fontWeight = FontWeight.ExtraBold, color = TextoOscuro)
            Text("Asigna chofer, valida entrega del conductor y acepta recepción", fontSize = 12.sp, color = TextoSecundario)

            Spacer(modifier = Modifier.height(14.dp))

            // Tabs de filtro por estado
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                listOf("Todos", "En Camino", "Pedido Recibido").forEach { tab ->
                    val seleccionado = filtroEstado == tab
                    Surface(
                        color = if (seleccionado) NaranjaPrincipal else ContenedorAlto,
                        shape = RoundedCornerShape(20.dp),
                        modifier = Modifier.clickable { filtroEstado = tab }
                    ) {
                        Text(
                            text = tab,
                            color = if (seleccionado) Color.White else TextoSecundario,
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp)
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            val pedidosFiltrados = GestorPedidos.listaPedidos.filter {
                filtroEstado == "Todos" || it.estado.equals(filtroEstado, ignoreCase = true)
            }

            if (pedidosFiltrados.isEmpty()) {
                Surface(color = BlancoTarjeta, shape = RoundedCornerShape(12.dp), modifier = Modifier.fillMaxWidth()) {
                    Column(modifier = Modifier.padding(24.dp), horizontalAlignment = Alignment.CenterHorizontally) {
                        Icon(Icons.Default.ShoppingCart, contentDescription = null, tint = Color.Gray, modifier = Modifier.size(36.dp))
                        Spacer(modifier = Modifier.height(8.dp))
                        Text("No hay pedidos en esta categoría", fontSize = 13.sp, color = TextoSecundario)
                    }
                }
            } else {
                pedidosFiltrados.forEach { pedido ->
                    TarjetaPedidoAdmin(
                        pedido = pedido,
                        choferes = listaChoferes,
                        onAsignarChofer = { chofer ->
                            GestorPedidos.asignarChofer(pedido.codigo, chofer.nombre, chofer.placa)
                            Toast.makeText(contexto, "Chofer ${chofer.nombre} asignado", Toast.LENGTH_SHORT).show()
                        },
                        onConfirmarConductor = {
                            GestorPedidos.confirmarEntregaChofer(pedido.codigo)
                            Toast.makeText(contexto, "Conductor confirmó entrega en obra/destino", Toast.LENGTH_SHORT).show()
                        },
                        onAceptarRecibido = {
                            GestorPedidos.aceptarPedidoRecibidoAdmin(pedido.codigo)
                            Toast.makeText(contexto, "Pedido aceptado y cerrado como Recibido", Toast.LENGTH_SHORT).show()
                        }
                    )
                    Spacer(modifier = Modifier.height(14.dp))
                }
            }
        }
    }
}

@Composable
private fun TarjetaPedidoAdmin(
    pedido: PedidoAdmin,
    choferes: List<ChoferReparto>,
    onAsignarChofer: (ChoferReparto) -> Unit,
    onConfirmarConductor: () -> Unit,
    onAceptarRecibido: () -> Unit
) {
    var choferSeleccionado by remember(pedido.chofer) {
        mutableStateOf(choferes.find { it.nombre == pedido.chofer } ?: choferes.first())
    }
    var menuExpandido by remember { mutableStateOf(false) }

    val esRecibido = pedido.estado == "Pedido Recibido"

    Surface(
        color = BlancoTarjeta,
        shape = RoundedCornerShape(14.dp),
        shadowElevation = 2.dp,
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            // Cabecera: Código + Badge Estado
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
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

            // Bloque con todos los datos de la compra
            Surface(color = ContenedorBajo, shape = RoundedCornerShape(10.dp), modifier = Modifier.fillMaxWidth()) {
                Column(modifier = Modifier.padding(12.dp)) {
                    Text("DATOS DE LA COMPRA", fontSize = 10.sp, fontWeight = FontWeight.Bold, color = TextoSecundario)
                    Spacer(modifier = Modifier.height(4.dp))
                    Text("Cliente: ${pedido.usuario} • Receptor: ${pedido.receptor}", fontSize = 12.sp, fontWeight = FontWeight.Bold, color = TextoOscuro)
                    Text("Productos (${pedido.cantidadProductos}): ${pedido.resumenProductos}", fontSize = 12.sp, color = TextoOscuro)
                    Text("Destino: ${pedido.destino}", fontSize = 11.sp, color = TextoSecundario)
                    Spacer(modifier = Modifier.height(6.dp))
                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                        Text("Pago: ${pedido.metodoPago} (${pedido.comprobante})", fontSize = 11.sp, color = AzulSecundario, fontWeight = FontWeight.Bold)
                        Text("Total: S/ ${String.format("%.2f", pedido.montoTotal)}", fontSize = 14.sp, fontWeight = FontWeight.ExtraBold, color = TextoOscuro)
                    }
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Selector de Chofer
            Text("Chofer Asignado al Pedido", fontSize = 12.sp, fontWeight = FontWeight.Bold, color = TextoOscuro)
            Spacer(modifier = Modifier.height(6.dp))

            Box(modifier = Modifier.fillMaxWidth()) {
                OutlinedButton(
                    onClick = { if (!esRecibido) menuExpandido = true },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Icon(Icons.Default.Person, contentDescription = null, tint = AzulSecundario, modifier = Modifier.size(18.dp))
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = if (pedido.chofer != "Sin asignar") "${pedido.chofer} • ${pedido.placa}"
                        else "${choferSeleccionado.nombre} • ${choferSeleccionado.placa}",
                        modifier = Modifier.weight(1f),
                        fontSize = 12.sp,
                        color = TextoOscuro
                    )
                    Icon(Icons.Default.ArrowDropDown, contentDescription = null)
                }

                DropdownMenu(expanded = menuExpandido, onDismissRequest = { menuExpandido = false }) {
                    choferes.forEach { chofer ->
                        DropdownMenuItem(
                            text = { Text("${chofer.nombre} (${chofer.placa})") },
                            onClick = {
                                choferSeleccionado = chofer
                                menuExpandido = false
                            }
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            // Botones de flujo de estado
            if (!esRecibido) {
                Button(
                    onClick = { onAsignarChofer(choferSeleccionado) },
                    colors = ButtonDefaults.buttonColors(containerColor = AzulSecundario),
                    shape = RoundedCornerShape(8.dp),
                    modifier = Modifier.fillMaxWidth().height(40.dp)
                ) {
                    Text(
                        text = if (pedido.chofer == "Sin asignar") "1. Asignar Chofer y Despachar" else "Cambiar Chofer Asignado",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold
                    )
                }

                Spacer(modifier = Modifier.height(8.dp))

                OutlinedButton(
                    onClick = onConfirmarConductor,
                    enabled = pedido.chofer != "Sin asignar" && !pedido.confirmadoPorChofer,
                    shape = RoundedCornerShape(8.dp),
                    modifier = Modifier.fillMaxWidth().height(40.dp)
                ) {
                    Icon(
                        imageVector = if (pedido.confirmadoPorChofer) Icons.Default.CheckCircle else Icons.Default.LocationOn,
                        contentDescription = null,
                        tint = if (pedido.confirmadoPorChofer) Color(0xFF2E7D32) else NaranjaPrincipal,
                        modifier = Modifier.size(16.dp)
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = if (pedido.confirmadoPorChofer) "Entrega Confirmada por Conductor ✓" else "2. Confirmar Entrega (Conductor)",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold,
                        color = if (pedido.confirmadoPorChofer) Color(0xFF2E7D32) else TextoOscuro
                    )
                }

                Spacer(modifier = Modifier.height(8.dp))

                Button(
                    onClick = onAceptarRecibido,
                    enabled = pedido.confirmadoPorChofer,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF2E7D32),
                        disabledContainerColor = ContenedorAlto
                    ),
                    shape = RoundedCornerShape(8.dp),
                    modifier = Modifier.fillMaxWidth().height(42.dp)
                ) {
                    Icon(Icons.Default.Check, contentDescription = null, modifier = Modifier.size(18.dp))
                    Spacer(modifier = Modifier.width(6.dp))
                    Text("3. Aceptar Pedido Recibido", fontSize = 13.sp, fontWeight = FontWeight.Bold)
                }
            } else {
                Surface(color = Color(0xFFE8F5E9), shape = RoundedCornerShape(8.dp), modifier = Modifier.fillMaxWidth()) {
                    Row(modifier = Modifier.padding(10.dp), verticalAlignment = Alignment.CenterVertically) {
                        Icon(Icons.Default.CheckCircle, contentDescription = null, tint = Color(0xFF2E7D32), modifier = Modifier.size(18.dp))
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("Entrega finalizada y aceptada por Administración", fontSize = 12.sp, fontWeight = FontWeight.Bold, color = Color(0xFF1B5E20))
                    }
                }
            }
        }
    }
}

@Composable
private fun ItemBarraInferiorAdminPedidos(titulo: String, icono: ImageVector, activo: Boolean, alClick: () -> Unit) {
    val colorItem = if (activo) NaranjaPrincipal else TextoSecundario
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.clickable { alClick() }.padding(horizontal = 8.dp, vertical = 4.dp)
    ) {
        Icon(icono, contentDescription = titulo, tint = colorItem, modifier = Modifier.size(22.dp))
        Spacer(modifier = Modifier.height(2.dp))
        Text(titulo, fontSize = 11.sp, fontWeight = if (activo) FontWeight.Bold else FontWeight.Medium, color = colorItem)
    }
}