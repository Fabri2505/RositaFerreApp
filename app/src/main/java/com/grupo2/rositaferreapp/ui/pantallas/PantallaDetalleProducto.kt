package com.grupo2.rositaferreapp.ui.pantallas

import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.grupo2.rositaferreapp.R
import com.grupo2.rositaferreapp.datos.DatosCatalogo
import com.grupo2.rositaferreapp.navegacion.ControladorNavegacion
import com.grupo2.rositaferreapp.ui.theme.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PantallaDetalleProducto(navController: ControladorNavegacion) {
    val contexto = LocalContext.current
    val producto = navController.productoSeleccionado

    // Estados simples de la pantalla
    var indiceImagen by remember { mutableIntStateOf(0) }
    var esFavorito by remember { mutableStateOf(false) }
    var cajaExpandida by remember { mutableStateOf(true) }
    var cantidadSeleccionada by remember { mutableIntStateOf(1) }

    val listaImagenes = producto.imagenesCarrusel
    // Producto complementario para "Frecuentemente comprados juntos"
    val productoComplemento = DatosCatalogo.listaProductos.firstOrNull { it.id != producto.id }

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
                        Text(
                            text = "Detalle De Producto",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold,
                            color = TextoOscuro
                        )
                    }
                },
                navigationIcon = {
                    IconButton(onClick = { navController.irACatalogo() }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Volver al catálogo",
                            tint = TextoOscuro
                        )
                    }
                },
                actions = {
                    Box(
                        modifier = Modifier
                            .padding(end = 12.dp)
                            .size(34.dp)
                            .background(NaranjaOscuro, CircleShape),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.Person,
                            contentDescription = null,
                            tint = Color.White,
                            modifier = Modifier.size(18.dp)
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = BlancoTarjeta)
            )
        },
        bottomBar = {
            // Barra inferior fija: selector (- 1 +), Agregar al Carrito y Comprar Ahora
            Surface(color = BlancoTarjeta, shadowElevation = 8.dp, modifier = Modifier.fillMaxWidth()) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 14.dp, vertical = 10.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    // Selector de cantidad (- 1 +)
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .height(46.dp)
                            .background(ContenedorBajo, RoundedCornerShape(10.dp))
                    ) {
                        IconButton(
                            onClick = { if (cantidadSeleccionada > 1) cantidadSeleccionada-- },
                            modifier = Modifier.size(34.dp)
                        ) {
                            Text("-", fontWeight = FontWeight.ExtraBold, fontSize = 18.sp, color = TextoOscuro)
                        }
                        Text(
                            text = "$cantidadSeleccionada",
                            fontWeight = FontWeight.Bold,
                            fontSize = 14.sp,
                            modifier = Modifier.padding(horizontal = 6.dp)
                        )
                        IconButton(
                            onClick = { if (cantidadSeleccionada < producto.stock) cantidadSeleccionada++ },
                            modifier = Modifier.size(34.dp)
                        ) {
                            Text("+", fontWeight = FontWeight.ExtraBold, fontSize = 16.sp, color = TextoOscuro)
                        }
                    }

                    // Botón Agregar al Carrito
                    Button(
                        onClick = {
                            navController.agregarProducto(producto, cantidadSeleccionada)
                            Toast.makeText(contexto, "Agregado ($cantidadSeleccionada) al carrito", Toast.LENGTH_SHORT).show()
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = NaranjaPrincipal),
                        shape = RoundedCornerShape(10.dp),
                        contentPadding = PaddingValues(horizontal = 10.dp),
                        modifier = Modifier
                            .weight(1f)
                            .height(46.dp)
                    ) {
                        Icon(Icons.Default.ShoppingCart, contentDescription = null, modifier = Modifier.size(16.dp))
                        Spacer(modifier = Modifier.width(4.dp))
                        Text("Agregar al Carrito", fontSize = 11.sp, fontWeight = FontWeight.Bold, lineHeight = 13.sp)
                    }

                    // Botón Comprar Ahora
                    Button(
                        onClick = {
                            navController.agregarProducto(producto, cantidadSeleccionada)
                            Toast.makeText(contexto, "Listo para pasar a Proceso de Pago", Toast.LENGTH_SHORT).show()
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = AzulSecundario),
                        shape = RoundedCornerShape(10.dp),
                        contentPadding = PaddingValues(horizontal = 10.dp),
                        modifier = Modifier
                            .weight(1f)
                            .height(46.dp)
                    ) {
                        Icon(Icons.Default.CheckCircle, contentDescription = null, modifier = Modifier.size(16.dp))
                        Spacer(modifier = Modifier.width(4.dp))
                        Text("Comprar Ahora", fontSize = 11.sp, fontWeight = FontWeight.Bold, lineHeight = 13.sp)
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
            // 1. CARRUSEL DE IMÁGENES
            Surface(
                color = ContenedorBajo,
                shape = RoundedCornerShape(16.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier.padding(12.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    // Fila superior: Garantía + Corazón
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Surface(color = BlancoTarjeta, shape = CircleShape) {
                            Row(
                                modifier = Modifier.padding(horizontal = 10.dp, vertical = 5.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Icon(Icons.Default.CheckCircle, contentDescription = null, tint = AzulSecundario, modifier = Modifier.size(14.dp))
                                Spacer(modifier = Modifier.width(4.dp))
                                Text("Garantía Oficial ${producto.marca}", fontSize = 11.sp, fontWeight = FontWeight.Bold, color = TextoOscuro)
                            }
                        }

                        Surface(
                            color = BlancoTarjeta,
                            shape = CircleShape,
                            modifier = Modifier
                                .size(34.dp)
                                .clickable { esFavorito = !esFavorito }
                        ) {
                            Box(contentAlignment = Alignment.Center) {
                                Icon(
                                    imageVector = if (esFavorito) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                                    contentDescription = "Favorito",
                                    tint = if (esFavorito) Color.Red else TextoOscuro,
                                    modifier = Modifier.size(18.dp)
                                )
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    // Imagen principal del carrusel con flechas para deslizar/cambiar
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(210.dp)
                            .clip(RoundedCornerShape(12.dp))
                            .background(BlancoTarjeta),
                        contentAlignment = Alignment.Center
                    ) {
                        Image(
                            painter = painterResource(id = listaImagenes[indiceImagen]),
                            contentDescription = producto.nombre,
                            contentScale = ContentScale.Fit,
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(14.dp)
                        )

                        // Flecha izquierda del carrusel
                        IconButton(
                            onClick = {
                                indiceImagen = if (indiceImagen > 0) indiceImagen - 1 else listaImagenes.size - 1
                            },
                            modifier = Modifier
                                .align(Alignment.CenterStart)
                                .padding(4.dp)
                                .size(32.dp)
                                .background(ContenedorBajo.copy(alpha = 0.85f), CircleShape)
                        ) {
                            Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Anterior", modifier = Modifier.size(16.dp))
                        }

                        // Flecha derecha del carrusel
                        IconButton(
                            onClick = {
                                indiceImagen = if (indiceImagen < listaImagenes.size - 1) indiceImagen + 1 else 0
                            },
                            modifier = Modifier
                                .align(Alignment.CenterEnd)
                                .padding(4.dp)
                                .size(32.dp)
                                .background(ContenedorBajo.copy(alpha = 0.85f), CircleShape)
                        ) {
                            Icon(Icons.AutoMirrored.Filled.ArrowForward, contentDescription = "Siguiente", modifier = Modifier.size(16.dp))
                        }
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    // Miniaturas seleccionables del carrusel
                    Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                        for (i in listaImagenes.indices) {
                            val seleccionado = (i == indiceImagen)
                            Box(
                                modifier = Modifier
                                    .size(50.dp)
                                    .clip(RoundedCornerShape(8.dp))
                                    .background(BlancoTarjeta)
                                    .border(
                                        width = if (seleccionado) 2.dp else 1.dp,
                                        color = if (seleccionado) NaranjaOscuro else ContenedorAlto,
                                        shape = RoundedCornerShape(8.dp)
                                    )
                                    .clickable { indiceImagen = i }
                                    .padding(4.dp),
                                contentAlignment = Alignment.Center
                            ) {
                                Image(
                                    painter = painterResource(id = listaImagenes[i]),
                                    contentDescription = null,
                                    contentScale = ContentScale.Fit,
                                    modifier = Modifier.fillMaxSize()
                                )
                            }
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(14.dp))

            // 2. INFORMACIÓN PRINCIPAL DEL PRODUCTO
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(producto.marca, fontSize = 13.sp, fontWeight = FontWeight.ExtraBold, color = AzulSecundario)
                    Spacer(modifier = Modifier.width(6.dp))
                    Text("• ${producto.categoria}", fontSize = 12.sp, color = TextoSecundario)
                }
                Text(
                    text = "Ver catálogo ${producto.marca} ›",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    color = NaranjaOscuro,
                    modifier = Modifier.clickable { navController.irACatalogo() }
                )
            }

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = producto.nombre,
                fontSize = 20.sp,
                fontWeight = FontWeight.ExtraBold,
                color = TextoOscuro,
                lineHeight = 25.sp
            )

            Spacer(modifier = Modifier.height(6.dp))

            // Estrellas y opiniones
            Row(verticalAlignment = Alignment.CenterVertically) {
                repeat(5) {
                    Icon(Icons.Default.Star, contentDescription = null, tint = NaranjaPrincipal, modifier = Modifier.size(16.dp))
                }
                Spacer(modifier = Modifier.width(6.dp))
                Text("4.8", fontSize = 12.sp, fontWeight = FontWeight.Bold, color = TextoOscuro)
                Text(" (142 opiniones)", fontSize = 12.sp, color = TextoSecundario)
            }

            Spacer(modifier = Modifier.height(6.dp))

            // Badge Original + SKU
            Row(verticalAlignment = Alignment.CenterVertically) {
                Surface(color = ContenedorAlto, shape = RoundedCornerShape(6.dp)) {
                    Row(
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(Icons.Default.CheckCircle, contentDescription = null, tint = AzulSecundario, modifier = Modifier.size(13.dp))
                        Spacer(modifier = Modifier.width(4.dp))
                        Text("Producto 100% Original", fontSize = 11.sp, fontWeight = FontWeight.Bold, color = AzulSecundario)
                    }
                }
                Spacer(modifier = Modifier.width(10.dp))
                Text("SKU: ${producto.sku}", fontSize = 11.sp, color = Color.Gray)
            }

            Spacer(modifier = Modifier.height(12.dp))

            // 3. TARJETA DE PRECIO Y FACTURACIÓN SUNAT
            Surface(
                color = ContenedorBajo,
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(14.dp)) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(
                            text = "S/ ${producto.precio}",
                            fontSize = 24.sp,
                            fontWeight = FontWeight.ExtraBold,
                            color = NaranjaOscuro
                        )
                        if (producto.precioAnterior != null) {
                            Spacer(modifier = Modifier.width(10.dp))
                            Text(
                                text = "S/ ${producto.precioAnterior}",
                                fontSize = 14.sp,
                                color = Color.Gray,
                                textDecoration = TextDecoration.LineThrough
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Surface(color = Color(0xFFD32F2F), shape = RoundedCornerShape(4.dp)) {
                                Text(
                                    text = "OFERTA",
                                    color = Color.White,
                                    fontSize = 10.sp,
                                    fontWeight = FontWeight.Bold,
                                    modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                                )
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(6.dp))

                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(Icons.Default.Info, contentDescription = null, tint = AzulSecundario, modifier = Modifier.size(16.dp))
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            text = buildAnnotatedString {
                                append("Emisión inmediata de ")
                                withStyle(SpanStyle(fontWeight = FontWeight.Bold)) {
                                    append("Factura con RUC")
                                }
                                append(" o Boleta Electrónica.")
                            },
                            fontSize = 12.sp,
                            color = TextoOscuro
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            // 4. STOCK DISPONIBLE (Sin opción de entrega)
            Surface(
                color = BlancoTarjeta,
                shape = RoundedCornerShape(12.dp),
                shadowElevation = 1.dp,
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    modifier = Modifier.padding(14.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Box(modifier = Modifier.size(8.dp).background(NaranjaPrincipal, CircleShape))
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "Stock disponible: ${producto.stock} unidades",
                            fontSize = 13.sp,
                            fontWeight = FontWeight.Bold,
                            color = TextoOscuro
                        )
                    }
                    Text("en Almacén Central", fontSize = 11.sp, color = TextoSecundario)
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            // 5. ¿QUÉ INCLUYE LA CAJA?
            Surface(
                color = BlancoTarjeta,
                shape = RoundedCornerShape(12.dp),
                shadowElevation = 1.dp,
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(14.dp)) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { cajaExpandida = !cajaExpandida },
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(Icons.Default.ShoppingBag, contentDescription = null, tint = NaranjaOscuro, modifier = Modifier.size(18.dp))
                            Spacer(modifier = Modifier.width(8.dp))
                            Text("¿Qué incluye la caja?", fontSize = 14.sp, fontWeight = FontWeight.Bold, color = TextoOscuro)
                        }
                        Icon(
                            imageVector = if (cajaExpandida) Icons.Default.KeyboardArrowUp else Icons.Default.KeyboardArrowDown,
                            contentDescription = null,
                            tint = TextoOscuro
                        )
                    }

                    if (cajaExpandida) {
                        Spacer(modifier = Modifier.height(10.dp))
                        producto.incluyeCaja.forEach { itemCaja ->
                            Row(
                                modifier = Modifier.padding(vertical = 3.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Icon(Icons.Default.Check, contentDescription = null, tint = AzulSecundario, modifier = Modifier.size(16.dp))
                                Spacer(modifier = Modifier.width(8.dp))
                                Text(itemCaja, fontSize = 12.sp, color = TextoOscuro)
                            }
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            // 6. FRECUENTEMENTE COMPRADOS JUNTOS
            if (productoComplemento != null) {
                Surface(
                    color = BlancoTarjeta,
                    shape = RoundedCornerShape(12.dp),
                    shadowElevation = 1.dp,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(modifier = Modifier.padding(14.dp)) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(Icons.Default.ShoppingCart, contentDescription = null, tint = NaranjaOscuro, modifier = Modifier.size(18.dp))
                            Spacer(modifier = Modifier.width(8.dp))
                            Text("Frecuentemente comprados juntos", fontSize = 14.sp, fontWeight = FontWeight.Bold, color = TextoOscuro)
                        }

                        Spacer(modifier = Modifier.height(10.dp))

                        Surface(color = ContenedorBajo, shape = RoundedCornerShape(10.dp), modifier = Modifier.fillMaxWidth()) {
                            Row(
                                modifier = Modifier.padding(10.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Image(
                                    painter = painterResource(id = productoComplemento.imagenId),
                                    contentDescription = null,
                                    contentScale = ContentScale.Fit,
                                    modifier = Modifier
                                        .size(48.dp)
                                        .background(BlancoTarjeta, RoundedCornerShape(8.dp))
                                        .padding(4.dp)
                                )
                                Spacer(modifier = Modifier.width(10.dp))
                                Column(modifier = Modifier.weight(1f)) {
                                    Text(productoComplemento.nombre, fontSize = 12.sp, fontWeight = FontWeight.Bold, color = TextoOscuro, maxLines = 1)
                                    Text(productoComplemento.marca, fontSize = 11.sp, color = TextoSecundario)
                                    Text("S/ ${productoComplemento.precio}", fontSize = 13.sp, fontWeight = FontWeight.ExtraBold, color = NaranjaOscuro)
                                }
                                OutlinedButton(
                                    onClick = {
                                        navController.agregarProducto(productoComplemento, 1)
                                        Toast.makeText(contexto, "${productoComplemento.nombre} agregado", Toast.LENGTH_SHORT).show()
                                    },
                                    border = BorderStroke(1.dp, ContenedorAlto),
                                    shape = RoundedCornerShape(8.dp),
                                    contentPadding = PaddingValues(horizontal = 10.dp, vertical = 4.dp)
                                ) {
                                    Text("+ Agregar", fontSize = 11.sp, fontWeight = FontWeight.Bold, color = AzulSecundario)
                                }
                            }
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}