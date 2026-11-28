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
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.grupo2.rositaferreapp.R
import com.grupo2.rositaferreapp.navegacion.ControladorNavegacion
import com.grupo2.rositaferreapp.ui.theme.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PantallaPanelAdmin(navController: ControladorNavegacion) {
    var tabSeleccionado by remember { mutableStateOf("Hoy") }

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
                                Text("SJL Principal", fontSize = 11.sp, color = AzulSecundario, fontWeight = FontWeight.Bold)
                                Icon(Icons.Default.ArrowDropDown, contentDescription = null, tint = AzulSecundario, modifier = Modifier.size(14.dp))
                            }
                        }
                    }
                },
                actions = {
                    IconButton(onClick = { }) {
                        Icon(Icons.Default.Notifications, contentDescription = "Alertas", tint = Color(0xFFD32F2F))
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
                        ItemBarraInferior("Dashboard", Icons.Default.Menu, true)
                        ItemBarraInferior("Productos", Icons.Default.ShoppingCart, false) { navController.irAAdminProductos() }
                        ItemBarraInferior("Inventario", Icons.Default.Home, false)
                        ItemBarraInferior("Pedidos", Icons.Default.List, false) { navController.irAAdminPedidos() }
                        ItemBarraInferior("Ajustes", Icons.Default.Settings, false)
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
                .padding(horizontal = 16.dp, vertical = 12.dp)
        ) {
            // Saludo y Filtros Temporales
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(modifier = Modifier.size(8.dp).background(AzulSecundario, CircleShape))
                Spacer(modifier = Modifier.width(6.dp))
                Text("Almacén Central SJL • Turno Mañana", fontSize = 11.sp, fontWeight = FontWeight.Bold, color = AzulSecundario)
            }
            Spacer(modifier = Modifier.height(4.dp))
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                Text("Hola, Ing. Juan 👋", fontSize = 24.sp, fontWeight = FontWeight.ExtraBold, color = TextoOscuro)
                IconButton(onClick = { }, modifier = Modifier.size(32.dp).background(ContenedorAlto, CircleShape)) {
                    Icon(Icons.Default.Refresh, contentDescription = "Actualizar", modifier = Modifier.size(18.dp))
                }
            }
            Text("Control general de operaciones y despacho ferretero", fontSize = 12.sp, color = TextoSecundario)

            Spacer(modifier = Modifier.height(14.dp))

            // Tabs Temporales
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                TabFiltro("Hoy", tabSeleccionado == "Hoy", Modifier.weight(1f)) { tabSeleccionado = "Hoy" }
                TabFiltro("Esta Semana", tabSeleccionado == "Esta Semana", Modifier.weight(1.2f)) { tabSeleccionado = "Esta Semana" }
                TabFiltro("Mes en Curso", tabSeleccionado == "Mes", Modifier.weight(1.2f)) { tabSeleccionado = "Mes" }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Alerta SUNAT
            Surface(color = Color(0xFFFDECEA), shape = RoundedCornerShape(12.dp), modifier = Modifier.fillMaxWidth()) {
                Row(modifier = Modifier.padding(12.dp), verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Default.Warning, contentDescription = null, tint = Color(0xFFD32F2F), modifier = Modifier.size(24.dp))
                    Spacer(modifier = Modifier.width(10.dp))
                    Column(modifier = Modifier.weight(1f)) {
                        Text("2 pedidos express en espera de SUNAT", fontSize = 12.sp, fontWeight = FontWeight.Bold, color = Color(0xFFB71C1C))
                        Text("Guías de remisión pendientes de firmar...", fontSize = 11.sp, color = Color(0xFFD32F2F))
                    }
                    Button(onClick = { }, colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFD32F2F)), contentPadding = PaddingValues(horizontal = 12.dp), modifier = Modifier.height(30.dp)) {
                        Text("Revisar", fontSize = 10.sp, fontWeight = FontWeight.Bold)
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Tarjetas de Métricas Principales
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                TarjetaMetrica(
                    titulo = "VENTAS DE HOY",
                    valor = "S/ 18,450.80",
                    subtitulo = "+14.2% vs ayer",
                    icono = Icons.Default.ShoppingCart,
                    modifier = Modifier.weight(1f)
                )
                TarjetaMetrica(
                    titulo = "TICKET PROMEDIO",
                    valor = "S/ 645.00",
                    subtitulo = "28 transacciones",
                    icono = Icons.Default.List,
                    modifier = Modifier.weight(1f)
                )
            }

            Spacer(modifier = Modifier.height(20.dp))

            // Despachos Pendientes
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                Text("Despachos Pendientes", fontSize = 18.sp, fontWeight = FontWeight.ExtraBold, color = TextoOscuro)
                Surface(color = DuraznoClaro, shape = RoundedCornerShape(12.dp)) {
                    Text("3 urgentes", fontSize = 10.sp, fontWeight = FontWeight.Bold, color = Color(0xFFD32F2F), modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp))
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            ItemDespacho(pedido = "#ROS-88501", cliente = "Constructora Los Andes", tipo = "Obra Prioritaria", monto = "S/ 4,250.00", estado = "Pago al contado", accion = "Aprobar Despacho")
            Spacer(modifier = Modifier.height(8.dp))
            ItemDespacho(pedido = "#ROS-88503", cliente = "Maestro Carlos M.", tipo = "Mostrador / Recojo", monto = "S/ 833.90", estado = "Yape verificado", accion = "En preparación", esSecundario = true)

            Spacer(modifier = Modifier.height(12.dp))
            Text("Ver todos los pedidos en curso (24) →", fontSize = 12.sp, fontWeight = FontWeight.Bold, color = AzulSecundario, modifier = Modifier.fillMaxWidth().wrapContentWidth(Alignment.CenterHorizontally))

            Spacer(modifier = Modifier.height(24.dp))

            // Stock Crítico
            Surface(color = Color(0xFFD32F2F), shape = RoundedCornerShape(12.dp), modifier = Modifier.fillMaxWidth()) {
                Row(modifier = Modifier.padding(16.dp), verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Default.Warning, contentDescription = null, tint = Color.White, modifier = Modifier.size(32.dp))
                    Spacer(modifier = Modifier.width(12.dp))
                    Column(modifier = Modifier.weight(1f)) {
                        Text("Stock Crítico (H016)", fontSize = 16.sp, fontWeight = FontWeight.ExtraBold, color = Color.White)
                        Text("5 artículos bajo umbral de seguridad", fontSize = 11.sp, color = Color.White)
                    }
                    Surface(color = Color.White, shape = RoundedCornerShape(6.dp)) {
                        Text("Comprar", fontSize = 11.sp, fontWeight = FontWeight.Bold, color = Color(0xFFD32F2F), modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp))
                    }
                }
            }

            Spacer(modifier = Modifier.height(10.dp))
            ItemStockCritico("Cemento Sol Tipo I 42.5kg", "Quedan 18 bolsas (Mín: 100)", true)
            Spacer(modifier = Modifier.height(8.dp))
            ItemStockCritico("Fierro 1/2\" Aceros Arequipa", "Quedan 12 varillas (Mín: 80)", false)

            Spacer(modifier = Modifier.height(24.dp))

            // Acciones Frecuentes (Grid 2x2)
            Text("Acciones Frecuentes", fontSize = 18.sp, fontWeight = FontWeight.ExtraBold, color = TextoOscuro)
            Spacer(modifier = Modifier.height(12.dp))

            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                TarjetaAccion("Nuevo Producto", "Registrar SKU y código", Icons.Default.AddCircle, Modifier.weight(1f).clickable { navController.irAAdminProductos() })
                TarjetaAccion("Ajuste de Stock", "Carga masiva", Icons.Default.Edit, Modifier.weight(1f))
            }

            Spacer(modifier = Modifier.height(10.dp))
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                TarjetaAccion("Reporte Diario", "Descargar en PDF", Icons.Default.Info, Modifier.weight(1f))
                TarjetaAccion("Cierre de Caja", "Cuadre turno SJL", Icons.Default.Lock, Modifier.weight(1f))
            }

            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}

@Composable
fun TabFiltro(texto: String, seleccionado: Boolean, modifier: Modifier, alClick: () -> Unit) {
    Surface(
        color = if (seleccionado) NaranjaPrincipal else ContenedorAlto,
        shape = RoundedCornerShape(20.dp),
        modifier = modifier.clickable { alClick() }
    ) {
        Text(
            text = texto,
            color = if (seleccionado) Color.White else TextoSecundario,
            fontSize = 12.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(vertical = 8.dp).wrapContentWidth(Alignment.CenterHorizontally)
        )
    }
}

@Composable
fun TarjetaMetrica(titulo: String, valor: String, subtitulo: String, icono: ImageVector, modifier: Modifier = Modifier) {
    Surface(color = BlancoTarjeta, shape = RoundedCornerShape(12.dp), shadowElevation = 2.dp, modifier = modifier) {
        Column(modifier = Modifier.padding(14.dp)) {
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                Text(titulo, fontSize = 10.sp, fontWeight = FontWeight.Bold, color = TextoSecundario)
                Icon(icono, contentDescription = null, tint = DuraznoClaro, modifier = Modifier.size(16.dp))
            }
            Spacer(modifier = Modifier.height(8.dp))
            Text(valor, fontSize = 20.sp, fontWeight = FontWeight.ExtraBold, color = TextoOscuro)
            Spacer(modifier = Modifier.height(4.dp))
            Text(subtitulo, fontSize = 10.sp, color = AzulSecundario, fontWeight = FontWeight.Bold)
        }
    }
}

@Composable
fun ItemDespacho(pedido: String, cliente: String, tipo: String, monto: String, estado: String, accion: String, esSecundario: Boolean = false) {
    Surface(color = BlancoTarjeta, shape = RoundedCornerShape(12.dp), shadowElevation = 1.dp, modifier = Modifier.fillMaxWidth()) {
        Column(modifier = Modifier.padding(14.dp)) {
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(pedido, fontSize = 12.sp, fontWeight = FontWeight.ExtraBold, color = AzulSecundario)
                    Spacer(modifier = Modifier.width(6.dp))
                    Surface(color = ContenedorBajo, shape = RoundedCornerShape(4.dp)) {
                        Text(tipo, fontSize = 9.sp, color = TextoSecundario, modifier = Modifier.padding(horizontal = 4.dp, vertical = 2.dp))
                    }
                }
                Text(monto, fontSize = 14.sp, fontWeight = FontWeight.ExtraBold, color = TextoOscuro)
            }
            Spacer(modifier = Modifier.height(4.dp))
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                Text(cliente, fontSize = 14.sp, fontWeight = FontWeight.Bold, color = TextoOscuro)
                Text(estado, fontSize = 11.sp, color = TextoSecundario)
            }
            Spacer(modifier = Modifier.height(12.dp))

            if (esSecundario) {
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    OutlinedButton(onClick = { }, modifier = Modifier.weight(1f).height(36.dp), shape = RoundedCornerShape(8.dp), contentPadding = PaddingValues(0.dp)) {
                        Text("Empacando", fontSize = 11.sp, color = TextoSecundario)
                    }
                    OutlinedButton(onClick = { }, modifier = Modifier.weight(1f).height(36.dp), shape = RoundedCornerShape(8.dp), contentPadding = PaddingValues(0.dp)) {
                        Text(accion, fontSize = 11.sp, color = TextoOscuro)
                    }
                }
            } else {
                Button(onClick = { }, colors = ButtonDefaults.buttonColors(containerColor = NaranjaPrincipal), modifier = Modifier.fillMaxWidth().height(40.dp), shape = RoundedCornerShape(8.dp)) {
                    Icon(Icons.Default.CheckCircle, contentDescription = null, modifier = Modifier.size(16.dp))
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(accion, fontSize = 13.sp, fontWeight = FontWeight.Bold)
                }
            }
        }
    }
}

@Composable
fun ItemStockCritico(nombre: String, estado: String, muyCritico: Boolean) {
    Surface(color = BlancoTarjeta, shape = RoundedCornerShape(12.dp), shadowElevation = 1.dp, modifier = Modifier.fillMaxWidth()) {
        Row(modifier = Modifier.padding(12.dp), verticalAlignment = Alignment.CenterVertically) {
            Box(modifier = Modifier.size(48.dp).background(ContenedorBajo, RoundedCornerShape(8.dp)), contentAlignment = Alignment.Center) {
                Icon(Icons.Default.Build, contentDescription = null, tint = Color.Gray)
            }
            Spacer(modifier = Modifier.width(12.dp))
            Column(modifier = Modifier.weight(1f)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(nombre, fontSize = 13.sp, fontWeight = FontWeight.Bold, color = TextoOscuro, maxLines = 1, overflow = TextOverflow.Ellipsis, modifier = Modifier.weight(1f))
                    if (muyCritico) {
                        Surface(color = Color(0xFFD32F2F), shape = RoundedCornerShape(4.dp)) {
                            Text("Crítico", color = Color.White, fontSize = 8.sp, fontWeight = FontWeight.Bold, modifier = Modifier.padding(horizontal = 4.dp, vertical = 2.dp))
                        }
                    }
                }
                Text(estado, fontSize = 11.sp, color = if(muyCritico) Color(0xFFD32F2F) else TextoSecundario)
            }
            Spacer(modifier = Modifier.width(8.dp))
            Button(onClick = {}, colors = ButtonDefaults.buttonColors(containerColor = if (muyCritico) Color(0xFF8D6E63) else ContenedorAlto), shape = RoundedCornerShape(8.dp), contentPadding = PaddingValues(horizontal = 10.dp), modifier = Modifier.height(32.dp)) {
                Text(if (muyCritico) "Reponer" else "Solicitar", fontSize = 10.sp, color = if(muyCritico) Color.White else TextoOscuro)
            }
        }
    }
}

@Composable
fun TarjetaAccion(titulo: String, subtitulo: String, icono: ImageVector, modifier: Modifier = Modifier) {
    Surface(color = BlancoTarjeta, shape = RoundedCornerShape(12.dp), shadowElevation = 1.dp, modifier = modifier.clickable {}) {
        Column(modifier = Modifier.padding(14.dp)) {
            Icon(icono, contentDescription = null, tint = NaranjaPrincipal, modifier = Modifier.size(24.dp))
            Spacer(modifier = Modifier.height(10.dp))
            Text(titulo, fontSize = 13.sp, fontWeight = FontWeight.Bold, color = TextoOscuro)
            Text(subtitulo, fontSize = 10.sp, color = TextoSecundario)
        }
    }
}

@Composable
private fun ItemBarraInferior(titulo: String, icono: ImageVector, activo: Boolean, alClick: () -> Unit = {}) {
    val colorItem = if (activo) NaranjaPrincipal else TextoSecundario
    Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.clickable { alClick() }.padding(horizontal = 8.dp, vertical = 4.dp)) {
        Icon(icono, contentDescription = titulo, tint = colorItem, modifier = Modifier.size(22.dp))
        Spacer(modifier = Modifier.height(2.dp))
        Text(titulo, fontSize = 11.sp, fontWeight = if (activo) FontWeight.Bold else FontWeight.Medium, color = colorItem)
    }
}