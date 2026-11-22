package com.grupo2.rositaferreapp.ui.pantallas

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
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
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.grupo2.rositaferreapp.R
import com.grupo2.rositaferreapp.datos.DatosCatalogo
import com.grupo2.rositaferreapp.datos.Producto
import com.grupo2.rositaferreapp.navegacion.ControladorNavegacion
import com.grupo2.rositaferreapp.ui.theme.*
import kotlin.math.roundToInt

private fun formatearPrecio(valor: Double): String {
    val redondeado = (valor * 100.0).roundToInt() / 100.0
    val texto = redondeado.toString()
    return if (texto.substringAfter(".").length == 1) "${texto}0" else texto
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PantallaCatalogo(navController: ControladorNavegacion) {
    var categoriaActual by remember { mutableStateOf("Todos") }
    var textoBuscar by remember { mutableStateOf("") }
    var pestanaInferior by remember { mutableStateOf("Inicio") }

    val favoritos = remember { mutableStateListOf<Int>() }

    val listaFiltrada = DatosCatalogo.filtrarProductos(categoriaActual, textoBuscar)
    val totalUnidades = navController.contarProductos()
    val totalPagar = navController.calcularTotalProductos()

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
                            Text("Ferretería Rosita", fontSize = 16.sp, fontWeight = FontWeight.Bold, color = TextoOscuro)
                            Text("Hola, ${navController.nombreUsuarioActivo}", fontSize = 11.sp, color = AzulSecundario)
                        }
                    }
                },
                navigationIcon = {
                    IconButton(onClick = { navController.irABienvenida() }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Volver", tint = TextoOscuro)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = BlancoTarjeta)
            )
        },
        bottomBar = {
            Column(modifier = Modifier.fillMaxWidth()) {
                if (totalUnidades > 0) {
                    Surface(color = BlancoTarjeta, shadowElevation = 6.dp, modifier = Modifier.fillMaxWidth()) {
                        Row(
                            modifier = Modifier.padding(horizontal = 20.dp, vertical = 10.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Column {
                                Text("$totalUnidades productos", fontSize = 11.sp, color = TextoSecundario)
                                Text(
                                    text = "Total: S/ ${formatearPrecio(totalPagar)}",
                                    fontSize = 16.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = TextoOscuro
                                )
                            }
                            Button(
                                onClick = { navController.irACarrito() },
                                colors = ButtonDefaults.buttonColors(containerColor = NaranjaPrincipal),
                                shape = RoundedCornerShape(10.dp),
                                modifier = Modifier.height(40.dp)
                            ) {
                                Text("Ver Carrito", fontWeight = FontWeight.Bold, fontSize = 13.sp)
                            }
                        }
                    }
                }

                // Barra de navegación inferior (Inicio, Categorías, Carrito, Pedidos, Perfil)
                HorizontalDivider(color = ContenedorAlto)
                Surface(color = BlancoTarjeta, modifier = Modifier.fillMaxWidth()) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp, horizontal = 4.dp),
                        horizontalArrangement = Arrangement.SpaceAround,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        ItemBarraInferior(
                            titulo = "Inicio",
                            icono = Icons.Default.Home,
                            activo = pestanaInferior == "Inicio",
                            alClick = { pestanaInferior = "Inicio" }
                        )
                        ItemBarraInferior(
                            titulo = "Categorías",
                            icono = Icons.Default.Menu,
                            activo = pestanaInferior == "Categorías",
                            alClick = { pestanaInferior = "Categorías" }
                        )
                        ItemBarraInferior(
                            titulo = "Carrito",
                            icono = Icons.Default.ShoppingCart,
                            activo = pestanaInferior == "Carrito",
                            badgeNumero = totalUnidades,
                            alClick = { navController.irACarrito() }
                        )
                        ItemBarraInferior(
                            titulo = "Pedidos",
                            icono = Icons.Default.ShoppingBag,
                            activo = pestanaInferior == "Pedidos",
                            alClick = { navController.irAPedidos() }
                        )
                        ItemBarraInferior(
                            titulo = "Perfil",
                            icono = Icons.Default.Person,
                            activo = pestanaInferior == "Perfil",
                            alClick = { navController.irALogin() }
                        )
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
            // 1. Buscador
            OutlinedTextField(
                value = textoBuscar,
                onValueChange = { textoBuscar = it },
                placeholder = { Text("Buscar taladro, cemento, pintura...", fontSize = 13.sp) },
                leadingIcon = { Icon(Icons.Default.Search, contentDescription = null, tint = NaranjaOscuro) },
                singleLine = true,
                shape = RoundedCornerShape(10.dp),
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(10.dp))

            // 2. Chips de Categorías
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .horizontalScroll(rememberScrollState()),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                DatosCatalogo.categorias.forEach { cat ->
                    val activo = (cat == categoriaActual)
                    Surface(
                        color = if (activo) NaranjaPrincipal else ContenedorBajo,
                        shape = CircleShape,
                        modifier = Modifier
                            .clip(CircleShape)
                            .clickable { categoriaActual = cat }
                    ) {
                        Text(
                            text = cat,
                            color = if (activo) Color.White else TextoOscuro,
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(horizontal = 14.dp, vertical = 7.dp)
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // 3. Título "Productos Más Vendidos" + "Filtrar v"
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(
                        modifier = Modifier
                            .width(4.dp)
                            .height(20.dp)
                            .background(NaranjaPrincipal, CircleShape)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "Productos Más Vendidos",
                        fontSize = 17.sp,
                        fontWeight = FontWeight.ExtraBold,
                        color = TextoOscuro
                    )
                }
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.clickable {
                        categoriaActual = "Todos"
                        textoBuscar = ""
                    }
                ) {
                    Text("Filtrar", fontSize = 13.sp, fontWeight = FontWeight.Bold, color = NaranjaPrincipal)
                    Icon(Icons.Default.KeyboardArrowDown, contentDescription = null, tint = NaranjaPrincipal, modifier = Modifier.size(18.dp))
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            // 4. Cuadrícula de 2 columnas usando chunked(2)
            val filasDeDos = listaFiltrada.chunked(2)
            filasDeDos.forEach { parProductos ->
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    for (prod in parProductos) {
                        val cantidad = navController.obtenerCantidad(prod.id)
                        val esFavorito = favoritos.contains(prod.id)

                        TarjetaProductoGrid(
                            producto = prod,
                            cantidad = cantidad,
                            esFavorito = esFavorito,
                            alVerDetalle = { navController.irADetalle(prod) },
                            alToggleFavorito = {
                                if (esFavorito) favoritos.remove(prod.id) else favoritos.add(prod.id)
                            },
                            alAgregar = { navController.agregarProducto(prod) },
                            alQuitar = { navController.quitarProducto(prod) },
                            modifier = Modifier.weight(1f)
                        )
                    }
                    if (parProductos.size == 1) {
                        Spacer(modifier = Modifier.weight(1f))
                    }
                }
                Spacer(modifier = Modifier.height(12.dp))
            }
        }
    }
}

@Composable
private fun TarjetaProductoGrid(
    producto: Producto,
    cantidad: Int,
    esFavorito: Boolean,
    alVerDetalle: () -> Unit,
    alToggleFavorito: () -> Unit,
    alAgregar: () -> Unit,
    alQuitar: () -> Unit,
    modifier: Modifier = Modifier
) {
    Surface(
        color = BlancoTarjeta,
        shape = RoundedCornerShape(14.dp),
        shadowElevation = 2.dp,
        modifier = modifier
            .clip(RoundedCornerShape(14.dp))
            .clickable { alVerDetalle() }
    ) {
        Column(modifier = Modifier.padding(10.dp)) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(125.dp)
                    .clip(RoundedCornerShape(10.dp))
                    .background(ContenedorBajo)
            ) {
                Image(
                    painter = painterResource(id = producto.imagenId),
                    contentDescription = producto.nombre,
                    contentScale = ContentScale.Fit,
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(6.dp)
                )

                Surface(
                    color = if (producto.esEtiquetaNaranja) NaranjaOscuro else AzulSecundario,
                    shape = RoundedCornerShape(6.dp),
                    modifier = Modifier
                        .align(Alignment.TopStart)
                        .padding(6.dp)
                ) {
                    Text(
                        text = producto.etiqueta,
                        color = Color.White,
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(horizontal = 7.dp, vertical = 3.dp)
                    )
                }

                Surface(
                    color = BlancoTarjeta.copy(alpha = 0.9f),
                    shape = CircleShape,
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .padding(6.dp)
                        .size(28.dp)
                        .clickable { alToggleFavorito() }
                ) {
                    Box(contentAlignment = Alignment.Center) {
                        Icon(
                            imageVector = if (esFavorito) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                            contentDescription = "Favorito",
                            tint = if (esFavorito) Color.Red else TextoOscuro,
                            modifier = Modifier.size(16.dp)
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "${producto.marca} •${producto.subtituloMarca}",
                fontSize = 10.sp,
                fontWeight = FontWeight.Bold,
                color = NaranjaOscuro,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )

            Spacer(modifier = Modifier.height(2.dp))

            Text(
                text = producto.nombre,
                fontSize = 13.sp,
                fontWeight = FontWeight.Bold,
                color = TextoOscuro,
                maxLines = 2,
                minLines = 2,
                lineHeight = 16.sp,
                overflow = TextOverflow.Ellipsis
            )

            Spacer(modifier = Modifier.height(6.dp))

            Text(
                text = if (producto.precioAnterior != null) "S/ ${formatearPrecio(producto.precioAnterior)}" else " ",
                fontSize = 11.sp,
                color = if (producto.precioAnterior != null) Color.Gray else Color.Transparent,
                textDecoration = if (producto.precioAnterior != null) TextDecoration.LineThrough else TextDecoration.None,
                maxLines = 1
            )

            Text(
                text = "S/ ${formatearPrecio(producto.precio)}",
                fontSize = 17.sp,
                fontWeight = FontWeight.ExtraBold,
                color = NaranjaOscuro
            )

            Text(
                text = producto.detalleExtra,
                fontSize = 10.sp,
                color = TextoSecundario,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )

            Spacer(modifier = Modifier.height(8.dp))

            if (cantidad == 0) {
                Button(
                    onClick = alAgregar,
                    colors = ButtonDefaults.buttonColors(containerColor = NaranjaPrincipal),
                    shape = RoundedCornerShape(8.dp),
                    contentPadding = PaddingValues(horizontal = 8.dp, vertical = 4.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(36.dp)
                ) {
                    Icon(Icons.Default.ShoppingCart, contentDescription = null, modifier = Modifier.size(15.dp))
                    Spacer(modifier = Modifier.width(6.dp))
                    Text("Agregar", fontSize = 12.sp, fontWeight = FontWeight.Bold)
                }
            } else {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(36.dp)
                        .background(ContenedorBajo, RoundedCornerShape(8.dp)),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    IconButton(onClick = alQuitar, modifier = Modifier.size(32.dp)) {
                        Text("-", fontWeight = FontWeight.ExtraBold, color = NaranjaOscuro, fontSize = 16.sp)
                    }
                    Text("$cantidad", fontWeight = FontWeight.Bold, fontSize = 13.sp, color = TextoOscuro)
                    IconButton(onClick = alAgregar, modifier = Modifier.size(32.dp)) {
                        Text("+", fontWeight = FontWeight.ExtraBold, color = NaranjaOscuro, fontSize = 16.sp)
                    }
                }
            }
        }
    }
}

@Composable
private fun ItemBarraInferior(
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
                modifier = Modifier
                    .padding(top = 4.dp, end = 6.dp)
                    .size(22.dp)
            )
            if (badgeNumero > 0) {
                Surface(
                    color = NaranjaPrincipal,
                    shape = CircleShape,
                    modifier = Modifier.size(16.dp)
                ) {
                    Box(contentAlignment = Alignment.Center) {
                        Text(
                            text = "$badgeNumero",
                            color = Color.White,
                            fontSize = 9.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }
        }
        Spacer(modifier = Modifier.height(2.dp))
        Text(
            text = titulo,
            fontSize = 11.sp,
            fontWeight = if (activo) FontWeight.Bold else FontWeight.Medium,
            color = colorItem
        )
    }
}