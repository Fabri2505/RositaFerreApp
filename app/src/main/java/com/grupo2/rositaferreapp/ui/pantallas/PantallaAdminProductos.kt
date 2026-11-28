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
import androidx.compose.ui.draw.clip
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
fun PantallaAdminProductos(navController: ControladorNavegacion) {
    var tabSeleccionado by remember { mutableStateOf("Todos") }
    var textoBuscar by remember { mutableStateOf("") }
    var publicado by remember { mutableStateOf(true) }

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
                            }
                        }
                    }
                },
                actions = {
                    IconButton(onClick = { }) { Icon(Icons.Default.Notifications, contentDescription = "Alertas") }
                    Box(modifier = Modifier.padding(end = 12.dp).size(32.dp).background(NaranjaOscuro, CircleShape), contentAlignment = Alignment.Center) {
                        Icon(Icons.Default.Person, contentDescription = null, tint = Color.White, modifier = Modifier.size(18.dp))
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = BlancoTarjeta)
            )
        },
        bottomBar = {
            // Barra Inferior - Productos Activo
            Surface(color = BlancoTarjeta, shadowElevation = 8.dp, modifier = Modifier.fillMaxWidth()) {
                Row(modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp, horizontal = 4.dp), horizontalArrangement = Arrangement.SpaceAround, verticalAlignment = Alignment.CenterVertically) {
                    ItemBarraInferiorAdmin("Dashboard", Icons.Default.Menu, false) { navController.irAPanelAdmin() }
                    ItemBarraInferiorAdmin("Productos", Icons.Default.ShoppingCart, true) { }
                    ItemBarraInferiorAdmin("Inventario", Icons.Default.Home, false) { }
                    ItemBarraInferiorAdmin("Pedidos", Icons.Default.List, false) { navController.irAAdminPedidos() }
                    ItemBarraInferiorAdmin("Ajustes", Icons.Default.Settings, false) { }
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
            // Cabecera Catálogo
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                Column {
                    Text("Catálogo y Productos", fontSize = 22.sp, fontWeight = FontWeight.ExtraBold, color = TextoOscuro)
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Box(modifier = Modifier.size(6.dp).background(NaranjaPrincipal, CircleShape))
                        Spacer(modifier = Modifier.width(4.dp))
                        Text("342 productos registrados", fontSize = 12.sp, color = TextoSecundario)
                    }
                }
                Button(
                    onClick = { },
                    colors = ButtonDefaults.buttonColors(containerColor = NaranjaPrincipal),
                    shape = RoundedCornerShape(8.dp),
                    contentPadding = PaddingValues(horizontal = 12.dp)
                ) {
                    Icon(Icons.Default.Add, contentDescription = null, modifier = Modifier.size(16.dp))
                    Spacer(modifier = Modifier.width(4.dp))
                    Text("Nuevo", fontSize = 12.sp, fontWeight = FontWeight.Bold)
                }
            }

            Spacer(modifier = Modifier.height(14.dp))

            // Buscador
            Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
                OutlinedTextField(
                    value = textoBuscar,
                    onValueChange = { textoBuscar = it },
                    placeholder = { Text("Buscar por código SKU, nombre o r...", fontSize = 13.sp) },
                    leadingIcon = { Icon(Icons.Default.Search, contentDescription = null) },
                    trailingIcon = { Icon(painterResource(id = R.drawable.ic_launcher_foreground), contentDescription = "Barcode", modifier = Modifier.size(20.dp)) }, // Reemplaza por un icono de barras si tienes
                    singleLine = true,
                    shape = RoundedCornerShape(10.dp),
                    modifier = Modifier.weight(1f)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Surface(color = ContenedorBajo, shape = RoundedCornerShape(10.dp), modifier = Modifier.size(54.dp)) {
                    Icon(Icons.Default.Build, contentDescription = "Filtros", modifier = Modifier.padding(14.dp), tint = AzulSecundario)
                }
            }

            Spacer(modifier = Modifier.height(14.dp))

            // Tabs
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                TabFiltroAdmin("Todos", "342", tabSeleccionado == "Todos") { tabSeleccionado = "Todos" }
                TabFiltroAdmin("Activos", "318", tabSeleccionado == "Activos") { tabSeleccionado = "Activos" }
                TabFiltroAdmin("Borradores", "9", tabSeleccionado == "Borradores") { tabSeleccionado = "Borradores" }
            }

            Spacer(modifier = Modifier.height(20.dp))

            // ====== FORMULARIO DE EDICIÓN (Wireframe) ======
            Surface(color = BlancoTarjeta, shape = RoundedCornerShape(14.dp), shadowElevation = 2.dp, modifier = Modifier.fillMaxWidth()) {
                Column(modifier = Modifier.padding(16.dp)) {
                    // Título Formulario
                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Box(modifier = Modifier.size(34.dp).background(DuraznoClaro, CircleShape), contentAlignment = Alignment.Center) {
                                Icon(Icons.Default.Edit, contentDescription = null, tint = NaranjaOscuro, modifier = Modifier.size(18.dp))
                            }
                            Spacer(modifier = Modifier.width(10.dp))
                            Column {
                                Text("Edición de Ficha Técnica", fontSize = 16.sp, fontWeight = FontWeight.ExtraBold, color = TextoOscuro)
                                Text("H015 • Almacén Central Lima", fontSize = 11.sp, color = TextoSecundario)
                            }
                        }
                        Surface(color = Color(0xFFE8EAF6), shape = RoundedCornerShape(6.dp)) {
                            Text("SKU ACTIVO", color = AzulSecundario, fontSize = 9.sp, fontWeight = FontWeight.Bold, modifier = Modifier.padding(horizontal = 6.dp, vertical = 4.dp))
                        }
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    Text("Nombre del Producto *", fontSize = 12.sp, fontWeight = FontWeight.Bold)
                    Spacer(modifier = Modifier.height(4.dp))
                    OutlinedTextField(
                        value = "Taladro Percutor Bosch 18V GSB 185-LI",
                        onValueChange = {},
                        modifier = Modifier.fillMaxWidth(),
                        colors = OutlinedTextFieldDefaults.colors(unfocusedContainerColor = ContenedorBajo),
                        shape = RoundedCornerShape(8.dp)
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                        Column(modifier = Modifier.weight(1f)) {
                            Text("Código SKU", fontSize = 12.sp, fontWeight = FontWeight.Bold)
                            Spacer(modifier = Modifier.height(4.dp))
                            OutlinedTextField(
                                value = "BOS-GSB185-01",
                                onValueChange = {},
                                trailingIcon = { Icon(Icons.Default.Menu, contentDescription = null) },
                                shape = RoundedCornerShape(8.dp)
                            )
                        }
                        Column(modifier = Modifier.weight(1f)) {
                            Text("Categoría", fontSize = 12.sp, fontWeight = FontWeight.Bold)
                            Spacer(modifier = Modifier.height(4.dp))
                            OutlinedTextField(
                                value = "Herramientas Eléc...",
                                onValueChange = {},
                                trailingIcon = { Icon(Icons.Default.ArrowDropDown, contentDescription = null) },
                                shape = RoundedCornerShape(8.dp)
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    // Estructura de Precios
                    Surface(color = ContenedorBajo, shape = RoundedCornerShape(12.dp), modifier = Modifier.fillMaxWidth()) {
                        Column(modifier = Modifier.padding(14.dp)) {
                            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Icon(Icons.Default.Info, contentDescription = null, tint = NaranjaPrincipal, modifier = Modifier.size(16.dp))
                                    Spacer(modifier = Modifier.width(6.dp))
                                    Text("Estructura de Precios\n(PEN)", fontSize = 13.sp, fontWeight = FontWeight.Bold)
                                }
                                Text("IGV 18%\nIncluido", fontSize = 11.sp, color = AzulSecundario, fontWeight = FontWeight.Bold, textAlign = androidx.compose.ui.text.style.TextAlign.End)
                            }

                            Spacer(modifier = Modifier.height(12.dp))

                            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                                Column(modifier = Modifier.weight(1f)) {
                                    Text("PVP Unitario Regular", fontSize = 11.sp, fontWeight = FontWeight.Bold)
                                    Surface(color = Color.White, shape = RoundedCornerShape(8.dp), modifier = Modifier.fillMaxWidth().padding(top = 4.dp)) {
                                        Text("S/ 489.90", fontSize = 16.sp, fontWeight = FontWeight.ExtraBold, modifier = Modifier.padding(10.dp))
                                    }
                                }
                                Column(modifier = Modifier.weight(1f)) {
                                    Text("Precio Mayorista (≥3u)", fontSize = 11.sp, fontWeight = FontWeight.Bold)
                                    Surface(color = Color.White, shape = RoundedCornerShape(8.dp), modifier = Modifier.fillMaxWidth().padding(top = 4.dp)) {
                                        Text("S/ 445.00", fontSize = 16.sp, fontWeight = FontWeight.ExtraBold, color = AzulSecundario, modifier = Modifier.padding(10.dp))
                                    }
                                }
                            }

                            Spacer(modifier = Modifier.height(12.dp))

                            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                                Column {
                                    Text("Costo de\nAdquisición", fontSize = 11.sp)
                                    Text("S/ 380.00", fontSize = 14.sp, fontWeight = FontWeight.Bold)
                                }
                                Surface(color = DuraznoClaro, shape = RoundedCornerShape(8.dp)) {
                                    Row(modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp), verticalAlignment = Alignment.CenterVertically) {
                                        Icon(Icons.Default.KeyboardArrowUp, contentDescription = null, tint = NaranjaOscuro, modifier = Modifier.size(16.dp))
                                        Spacer(modifier = Modifier.width(4.dp))
                                        Text("Margen estimado:\n28.9%", fontSize = 11.sp, fontWeight = FontWeight.Bold, color = NaranjaOscuro)
                                    }
                                }
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    // Especificaciones
                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                        Text("Especificaciones Técnicas para Obra", fontSize = 12.sp, fontWeight = FontWeight.Bold)
                        Text("Ficha de campo", fontSize = 11.sp, color = TextoSecundario)
                    }
                    Spacer(modifier = Modifier.height(4.dp))
                    Surface(color = ContenedorBajo, shape = RoundedCornerShape(8.dp), modifier = Modifier.fillMaxWidth()) {
                        Text("Mandril metálico 13mm, 2 baterías de litio 2.0Ah, cargador rápido GAL 18V-20, torque máx 50 Nm, luz LED integrada y maletín de transporte rígido.", fontSize = 12.sp, modifier = Modifier.padding(12.dp), lineHeight = 18.sp)
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    // Fotografías
                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                        Text("Fotografías del Producto", fontSize = 12.sp, fontWeight = FontWeight.Bold)
                        Text("Máx. 5 imágenes", fontSize = 11.sp, color = TextoSecundario)
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        Box(modifier = Modifier.size(70.dp).background(ContenedorAlto, RoundedCornerShape(8.dp)), contentAlignment = Alignment.Center) {
                            Text("PRINCIPAL", color = Color.White, fontSize = 8.sp, fontWeight = FontWeight.Bold, modifier = Modifier.align(Alignment.TopStart).background(NaranjaPrincipal, RoundedCornerShape(bottomEnd = 4.dp)).padding(horizontal = 4.dp, vertical = 2.dp))
                            Icon(Icons.Default.Build, contentDescription = null) // Reemplaza por tu imagen
                        }
                        Box(modifier = Modifier.size(70.dp).background(ContenedorAlto, RoundedCornerShape(8.dp)), contentAlignment = Alignment.Center) { Icon(Icons.Default.Build, contentDescription = null) }
                        Box(modifier = Modifier.size(70.dp).background(ContenedorAlto, RoundedCornerShape(8.dp)), contentAlignment = Alignment.Center) { Icon(Icons.Default.Build, contentDescription = null) }
                        Surface(color = BlancoTarjeta, border = androidx.compose.foundation.BorderStroke(1.dp, NaranjaPrincipal), shape = RoundedCornerShape(8.dp), modifier = Modifier.size(70.dp)) {
                            Column(horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center) {                                Icon(Icons.Default.Add, contentDescription = null, tint = NaranjaPrincipal)
                                Text("+ Foto", fontSize = 10.sp, color = NaranjaPrincipal)
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(20.dp))

                    // Toggle Publicado
                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(Icons.Default.Info, contentDescription = null, tint = AzulSecundario)
                            Spacer(modifier = Modifier.width(8.dp))
                            Column {
                                Text("Publicado en la App", fontSize = 14.sp, fontWeight = FontWeight.Bold)
                                Text("Visible a contratistas y clientes", fontSize = 11.sp, color = TextoSecundario)
                            }
                        }
                        Switch(checked = publicado, onCheckedChange = { publicado = it }, colors = SwitchDefaults.colors(checkedThumbColor = Color.White, checkedTrackColor = NaranjaPrincipal))
                    }

                    Spacer(modifier = Modifier.height(20.dp))

                    // Botones Guardar/Cancelar
                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                        OutlinedButton(onClick = { }, modifier = Modifier.weight(1f).height(48.dp), shape = RoundedCornerShape(10.dp)) {
                            Text("Cancelar", color = TextoOscuro, fontWeight = FontWeight.Bold)
                        }
                        Button(onClick = { }, modifier = Modifier.weight(1f).height(48.dp), colors = ButtonDefaults.buttonColors(containerColor = NaranjaPrincipal), shape = RoundedCornerShape(10.dp)) {
                            Icon(Icons.Default.Check, contentDescription = null, modifier = Modifier.size(18.dp))
                            Spacer(modifier = Modifier.width(6.dp))
                            Text("Guardar Cambios", fontWeight = FontWeight.Bold)
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // ====== LISTADO GENERAL DE STOCK ======
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Default.List, contentDescription = null, tint = TextoOscuro)
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Listado General de Stock", fontSize = 18.sp, fontWeight = FontWeight.ExtraBold)
                }
                Text("Orden: Recientes", fontSize = 11.sp, color = AzulSecundario)
            }

            Spacer(modifier = Modifier.height(12.dp))

            when (tabSeleccionado) {
                "Todos" -> {
                    ItemListaStockAdmin("CEMENTOS", "Cemento Sol Tipo I 42.5kg", "CEM-SOL-42", "480 bls", "S/ 32.50", false)
                    ItemListaStockAdmin("ACERO", "Fierro Corrugado 1/2\" Aceros Arequipa", "FIE-AA-12", "220 var", "S/ 44.50", false)
                    ItemListaStockAdmin("ACCESORIOS", "Disco de Corte Diamantado 4 1/2\"", "ACC-BOS-DISC45", "65 und", "S/ 24.00", true)
                    ItemListaStockAdmin("PINTURAS", "Pintura Látex Vencedor 1 Galón", "PIN-VEN-01", "0 bls", "S/ 45.00", true)
                }
                "Activos" -> {
                    ItemListaStockAdmin("CEMENTOS", "Cemento Sol Tipo I 42.5kg", "CEM-SOL-42", "480 bls", "S/ 32.50", false)
                    ItemListaStockAdmin("ACERO", "Fierro Corrugado 1/2\" Aceros Arequipa", "FIE-AA-12", "220 var", "S/ 44.50", false)
                }
                "Borradores" -> {
                    ItemListaStockAdmin("PINTURAS", "Pintura Látex Vencedor 1 Galón (Borrador)", "PIN-VEN-01", "0 bls", "S/ 45.00", true)
                }
            }

            Spacer(modifier = Modifier.height(20.dp))
        }
    }
}

@Composable
fun TabFiltroAdmin(texto: String, cantidad: String, seleccionado: Boolean, alClick: () -> Unit) {
    Surface(
        color = if (seleccionado) NaranjaPrincipal else ContenedorAlto,
        shape = RoundedCornerShape(20.dp),
        modifier = Modifier.clickable { alClick() }
    ) {
        Row(modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp), verticalAlignment = Alignment.CenterVertically) {
            Text(texto, color = if (seleccionado) Color.White else TextoSecundario, fontSize = 12.sp, fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.width(6.dp))
            Surface(color = if (seleccionado) NaranjaOscuro else BlancoTarjeta, shape = CircleShape) {
                Text(cantidad, color = if (seleccionado) Color.White else TextoOscuro, fontSize = 10.sp, fontWeight = FontWeight.Bold, modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp))
            }
        }
    }
}

@Composable
fun ItemListaStockAdmin(categoria: String, nombre: String, sku: String, stock: String, precio: String, advertencia: Boolean) {
    Surface(color = BlancoTarjeta, shape = RoundedCornerShape(12.dp), shadowElevation = 1.dp, modifier = Modifier.fillMaxWidth().padding(bottom = 8.dp)) {
        Row(modifier = Modifier.padding(12.dp), verticalAlignment = Alignment.CenterVertically) {
            Box(modifier = Modifier.size(54.dp).background(ContenedorBajo, RoundedCornerShape(8.dp)), contentAlignment = Alignment.Center) {
                Icon(Icons.Default.Build, contentDescription = null, tint = Color.Gray) // Placeholder de imagen
            }
            Spacer(modifier = Modifier.width(12.dp))
            Column(modifier = Modifier.weight(1f)) {
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                    Text(categoria, fontSize = 10.sp, fontWeight = FontWeight.Bold, color = AzulSecundario)
                    Text(precio, fontSize = 16.sp, fontWeight = FontWeight.ExtraBold)
                }
                Text(nombre, fontSize = 13.sp, fontWeight = FontWeight.Bold, color = TextoOscuro, maxLines = 1, overflow = TextOverflow.Ellipsis)
                Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.padding(top = 4.dp)) {
                    Text("SKU: $sku • ", fontSize = 10.sp, color = TextoSecundario)
                    Surface(color = if (advertencia) DuraznoClaro else ContenedorAlto, shape = RoundedCornerShape(4.dp)) {
                        Text("Stock: $stock", fontSize = 10.sp, fontWeight = FontWeight.Bold, color = if(advertencia) Color(0xFFD32F2F) else TextoOscuro, modifier = Modifier.padding(horizontal = 4.dp, vertical = 2.dp))
                    }
                }
            }
            IconButton(onClick = {}) { Icon(Icons.Default.MoreVert, contentDescription = "Opciones") }
        }
    }
}

@Composable
private fun ItemBarraInferiorAdmin(titulo: String, icono: ImageVector, activo: Boolean, alClick: () -> Unit) {
    val colorItem = if (activo) NaranjaPrincipal else TextoSecundario
    Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.clickable { alClick() }.padding(horizontal = 8.dp, vertical = 4.dp)) {
        Icon(icono, contentDescription = titulo, tint = colorItem, modifier = Modifier.size(22.dp))
        Spacer(modifier = Modifier.height(2.dp))
        Text(titulo, fontSize = 11.sp, fontWeight = if (activo) FontWeight.Bold else FontWeight.Medium, color = colorItem)
    }
}