package com.grupo2.rositaferreapp.ui.pantallas

import android.widget.Toast
import androidx.compose.animation.core.*
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
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
fun PantallaEstadoPedido(navController: ControladorNavegacion) {
    val contexto = LocalContext.current
    val pedido = navController.pedidoSeleccionado

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
                        Text("Estado del Pedido", fontSize = 16.sp, fontWeight = FontWeight.Bold, color = TextoOscuro)
                    }
                },
                navigationIcon = {
                    IconButton(onClick = { navController.irAPedidos() }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Volver", tint = TextoOscuro)
                    }
                },
                actions = {
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
        containerColor = FondoPantalla
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 16.dp, vertical = 12.dp)
        ) {
            // 1. TARJETA SUPERIOR: CÓDIGO Y TIEMPO APROXIMADO DE DESTINO
            Surface(
                color = DuraznoClaro,
                shape = RoundedCornerShape(14.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(14.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Surface(color = NaranjaPrincipal, shape = CircleShape) {
                            Text(
                                text = "• En Camino",
                                color = Color.White,
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier.padding(horizontal = 10.dp, vertical = 3.dp)
                            )
                        }
                        Text(
                            text = pedido?.codigo ?: "#ROS-2026-88492",
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold,
                            color = TextoOscuro
                        )
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.Bottom
                    ) {
                        Column {
                            Text("TIEMPO APROXIMADO DE DESTINO", fontSize = 10.sp, fontWeight = FontWeight.Bold, color = NaranjaOscuro)
                            Text("4:00 PM - 6:20 PM", fontSize = 19.sp, fontWeight = FontWeight.ExtraBold, color = TextoOscuro)
                        }
                        Column(horizontalAlignment = Alignment.End) {
                            Text("80%", fontSize = 18.sp, fontWeight = FontWeight.ExtraBold, color = NaranjaOscuro)
                            Text("Progreso", fontSize = 10.sp, color = TextoSecundario)
                        }
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(6.dp)
                            .clip(CircleShape)
                            .background(BlancoTarjeta)
                    ) {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth(0.80f)
                                .fillMaxHeight()
                                .background(NaranjaPrincipal, CircleShape)
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            // 2. LÍNEA DE TIEMPO DEL PEDIDO (SOLO 3 ETAPAS)
            Surface(
                color = BlancoTarjeta,
                shape = RoundedCornerShape(14.dp),
                shadowElevation = 1.dp,
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(14.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text("Seguimiento del Pedido", fontSize = 15.sp, fontWeight = FontWeight.ExtraBold, color = TextoOscuro)
                        Text("3 Etapas", fontSize = 11.sp, color = TextoSecundario)
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    FilaEtapaSimple(
                        titulo = "1. Pedido Pagado",
                        descripcion = "Pago confirmado y comprobante emitido.",
                        completado = true,
                        activo = false,
                        mostrarLineaAbajo = true
                    )

                    FilaEtapaSimple(
                        titulo = "2. En Preparación (3 días hábiles)",
                        descripcion = "Materiales embalados en almacén central.",
                        completado = true,
                        activo = false,
                        mostrarLineaAbajo = true
                    )

                    FilaEtapaSimple(
                        titulo = "3. En Camino",
                        descripcion = "Tu pedido va en ruta hacia tu dirección.",
                        completado = false,
                        activo = true,
                        mostrarLineaAbajo = false
                    )
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            // 3. MAPA GPS EN CAMINO + CONDUCTOR Y PLACA
            Surface(
                color = BlancoTarjeta,
                shape = RoundedCornerShape(14.dp),
                shadowElevation = 1.dp,
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(14.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text("Ubicación GPS en Camino", fontSize = 15.sp, fontWeight = FontWeight.ExtraBold, color = TextoOscuro)
                        Surface(color = Color(0xFFE8F5E9), shape = CircleShape) {
                            Text(
                                text = "En Ruta",
                                fontSize = 10.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color(0xFF2E7D32),
                                modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.dp)
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    MapaRutaEnCamino()

                    Spacer(modifier = Modifier.height(12.dp))

                    // Datos sencillos del Conductor y Placa
                    Surface(color = ContenedorBajo, shape = RoundedCornerShape(10.dp), modifier = Modifier.fillMaxWidth()) {
                        Row(
                            modifier = Modifier.padding(12.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.weight(1f)) {
                                Box(
                                    modifier = Modifier
                                        .size(42.dp)
                                        .background(AzulSecundario, CircleShape),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Icon(Icons.Default.Person, contentDescription = null, tint = Color.White, modifier = Modifier.size(22.dp))
                                }
                                Spacer(modifier = Modifier.width(10.dp))
                                Column {
                                    Text("Conductor Asignado", fontSize = 10.sp, color = TextoSecundario)
                                    Text("Manuel Quispe Torres", fontSize = 14.sp, fontWeight = FontWeight.Bold, color = TextoOscuro)
                                    Spacer(modifier = Modifier.height(2.dp))
                                    Surface(color = BlancoTarjeta, shape = RoundedCornerShape(4.dp)) {
                                        Text(
                                            text = "Placa: B9X-721",
                                            fontSize = 11.sp,
                                            fontWeight = FontWeight.ExtraBold,
                                            color = NaranjaOscuro,
                                            modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                                        )
                                    }
                                }
                            }

                            IconButton(
                                onClick = {
                                    Toast.makeText(contexto, "Llamando al conductor Manuel Quispe...", Toast.LENGTH_SHORT).show()
                                },
                                modifier = Modifier
                                    .size(38.dp)
                                    .background(AzulSecundario, CircleShape)
                            ) {
                                Icon(Icons.Default.Phone, contentDescription = "Llamar", tint = Color.White, modifier = Modifier.size(18.dp))
                            }
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            // 4. PEDIDOS SOLICITADOS Y PUNTO DE ENTREGA
            Surface(
                color = BlancoTarjeta,
                shape = RoundedCornerShape(14.dp),
                shadowElevation = 1.dp,
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(14.dp)) {
                    val cantidad = pedido?.cantidadProductos ?: 0
                    Text("Pedidos Solicitados ($cantidad)", fontSize = 15.sp, fontWeight = FontWeight.ExtraBold, color = TextoOscuro)

                    Spacer(modifier = Modifier.height(10.dp))

                    val items = pedido?.itemsComprados ?: emptyList()
                    if (items.isEmpty()) {
                        Text(
                            text = pedido?.resumenProductos ?: "Productos de Ferretería Rosita",
                            fontSize = 12.sp,
                            color = TextoOscuro
                        )
                    } else {
                        items.forEach { item ->
                            Surface(
                                color = ContenedorBajo,
                                shape = RoundedCornerShape(10.dp),
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(bottom = 8.dp)
                            ) {
                                Row(
                                    modifier = Modifier.padding(10.dp),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Image(
                                        painter = painterResource(id = item.producto.imagenId),
                                        contentDescription = null,
                                        contentScale = ContentScale.Fit,
                                        modifier = Modifier
                                            .size(44.dp)
                                            .background(BlancoTarjeta, RoundedCornerShape(8.dp))
                                            .padding(4.dp)
                                    )
                                    Spacer(modifier = Modifier.width(10.dp))
                                    Column(modifier = Modifier.weight(1f)) {
                                        Text(
                                            text = item.producto.nombre,
                                            fontSize = 13.sp,
                                            fontWeight = FontWeight.Bold,
                                            color = TextoOscuro,
                                            maxLines = 1,
                                            overflow = TextOverflow.Ellipsis
                                        )
                                        Text(item.producto.marca, fontSize = 11.sp, color = TextoSecundario)
                                    }
                                    Surface(color = DuraznoClaro, shape = RoundedCornerShape(6.dp)) {
                                        Text(
                                            text = "x${item.cantidad} und",
                                            fontSize = 11.sp,
                                            fontWeight = FontWeight.Bold,
                                            color = NaranjaOscuro,
                                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                                        )
                                    }
                                }
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(4.dp))

                    // Punto de entrega final
                    Surface(color = DuraznoClaro, shape = RoundedCornerShape(10.dp), modifier = Modifier.fillMaxWidth()) {
                        Row(modifier = Modifier.padding(12.dp), verticalAlignment = Alignment.Top) {
                            Icon(Icons.Default.Place, contentDescription = null, tint = NaranjaOscuro, modifier = Modifier.size(18.dp))
                            Spacer(modifier = Modifier.width(8.dp))
                            Column {
                                Text("PUNTO DE ENTREGA", fontSize = 10.sp, fontWeight = FontWeight.Bold, color = NaranjaOscuro)
                                Text(
                                    text = pedido?.destino ?: "${navController.direccionEntrega} - ${navController.distritoEntrega}",
                                    fontSize = 13.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = TextoOscuro
                                )
                                Text(
                                    text = "Recibe: ${pedido?.receptor ?: navController.nombreReceptor}",
                                    fontSize = 11.sp,
                                    color = TextoSecundario
                                )
                            }
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(20.dp))
        }
    }
}

@Composable
private fun FilaEtapaSimple(
    titulo: String,
    descripcion: String,
    completado: Boolean,
    activo: Boolean,
    mostrarLineaAbajo: Boolean
) {
    val verde = Color(0xFF2E7D32)
    Row(modifier = Modifier.fillMaxWidth()) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Box(
                modifier = Modifier
                    .size(26.dp)
                    .background(
                        color = when {
                            activo -> NaranjaPrincipal
                            completado -> Color(0xFFE8F5E9)
                            else -> ContenedorBajo
                        },
                        shape = CircleShape
                    ),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = if (activo) Icons.Default.Place else Icons.Default.Check,
                    contentDescription = null,
                    tint = if (activo) Color.White else verde,
                    modifier = Modifier.size(15.dp)
                )
            }
            if (mostrarLineaAbajo) {
                Box(
                    modifier = Modifier
                        .width(2.dp)
                        .height(34.dp)
                        .background(verde.copy(alpha = 0.4f))
                )
            }
        }

        Spacer(modifier = Modifier.width(12.dp))

        Surface(
            color = if (activo) DuraznoClaro else Color.Transparent,
            shape = RoundedCornerShape(10.dp),
            modifier = Modifier
                .weight(1f)
                .padding(bottom = if (mostrarLineaAbajo) 8.dp else 0.dp)
        ) {
            Column(modifier = Modifier.padding(if (activo) 10.dp else 2.dp)) {
                Text(
                    text = titulo,
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Bold,
                    color = if (activo) NaranjaOscuro else TextoOscuro
                )
                Text(text = descripcion, fontSize = 11.sp, color = TextoSecundario)
            }
        }
    }
}

// Mapa lejano con ruta de pista y emoji de camión avanzando lento sin retroceder
@Composable
private fun MapaRutaEnCamino() {
    val transicion = rememberInfiniteTransition(label = "rutaGps")
    val progreso by transicion.animateFloat(
        initialValue = 0f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 9000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "avanceCamion"
    )

    BoxWithConstraints(
        modifier = Modifier
            .fillMaxWidth()
            .height(175.dp)
            .clip(RoundedCornerShape(12.dp))
            .background(Color(0xFFEAF2F8))
    ) {
        val anchoPx = constraints.maxWidth.toFloat()
        val altoPx = constraints.maxHeight.toFloat()

        // Puntos clave de la pista: Almacén -> Esquina (giro) -> Cliente
        val puntoInicio = Offset(anchoPx * 0.14f, altoPx * 0.30f)
        val puntoEsquina = Offset(anchoPx * 0.68f, altoPx * 0.30f)
        val puntoCliente = Offset(anchoPx * 0.68f, altoPx * 0.76f)

        val posicionCamion = if (progreso < 0.60f) {
            val tramo1 = progreso / 0.60f
            Offset(
                x = puntoInicio.x + (puntoEsquina.x - puntoInicio.x) * tramo1,
                y = puntoInicio.y
            )
        } else {
            val tramo2 = (progreso - 0.60f) / 0.40f
            Offset(
                x = puntoEsquina.x,
                y = puntoEsquina.y + (puntoCliente.y - puntoEsquina.y) * tramo2
            )
        }

        Canvas(modifier = Modifier.fillMaxSize()) {
            val w = size.width
            val h = size.height

            // Áreas verdes (parques lejanos)
            drawCircle(color = Color(0xFFD4EFDF), radius = 32f, center = Offset(w * 0.42f, h * 0.14f))
            drawCircle(color = Color(0xFFD4EFDF), radius = 40f, center = Offset(w * 0.25f, h * 0.72f))
            drawCircle(color = Color(0xFFD4EFDF), radius = 30f, center = Offset(w * 0.88f, h * 0.55f))

            // Red de calles lejanas (cuadrícula urbana)
            drawLine(color = Color.White, start = Offset(0f, h * 0.30f), end = Offset(w, h * 0.30f), strokeWidth = 18f)
            drawLine(color = Color.White, start = Offset(0f, h * 0.62f), end = Offset(w, h * 0.62f), strokeWidth = 10f)
            drawLine(color = Color.White, start = Offset(0f, h * 0.86f), end = Offset(w, h * 0.86f), strokeWidth = 10f)

            drawLine(color = Color.White, start = Offset(w * 0.14f, 0f), end = Offset(w * 0.14f, h), strokeWidth = 12f)
            drawLine(color = Color.White, start = Offset(w * 0.42f, 0f), end = Offset(w * 0.42f, h), strokeWidth = 10f)
            drawLine(color = Color.White, start = Offset(w * 0.68f, 0f), end = Offset(w * 0.68f, h), strokeWidth = 18f)
            drawLine(color = Color.White, start = Offset(w * 0.90f, 0f), end = Offset(w * 0.90f, h), strokeWidth = 10f)

            // Pista resaltada de la ruta del pedido
            drawLine(
                color = AzulSecundario.copy(alpha = 0.35f),
                start = puntoInicio,
                end = puntoEsquina,
                strokeWidth = 12f
            )
            drawLine(
                color = AzulSecundario.copy(alpha = 0.35f),
                start = puntoEsquina,
                end = puntoCliente,
                strokeWidth = 12f
            )

            // Línea punteada central
            drawLine(
                color = AzulSecundario,
                start = puntoInicio,
                end = puntoEsquina,
                strokeWidth = 4f,
                pathEffect = PathEffect.dashPathEffect(floatArrayOf(10f, 8f), 0f)
            )
            drawLine(
                color = AzulSecundario,
                start = puntoEsquina,
                end = puntoCliente,
                strokeWidth = 4f,
                pathEffect = PathEffect.dashPathEffect(floatArrayOf(10f, 8f), 0f)
            )

            // Punto azul: Almacén Rosita
            drawCircle(color = AzulSecundario, radius = 11f, center = puntoInicio)
            // Punto verde: Ubicación del Cliente
            drawCircle(color = Color(0xFF2E7D32).copy(alpha = 0.25f), radius = 24f, center = puntoCliente)
            drawCircle(color = Color(0xFF2E7D32), radius = 12f, center = puntoCliente)
        }

        // Etiqueta fija en el punto del cliente
        val densidad = LocalDensity.current
        val clienteXdp = with(densidad) { puntoCliente.x.toDp() }
        val clienteYdp = with(densidad) { puntoCliente.y.toDp() }
        Surface(
            color = Color(0xFF2E7D32),
            shape = CircleShape,
            modifier = Modifier.offset(x = clienteXdp + 12.dp, y = clienteYdp - 10.dp)
        ) {
            Text(
                text = "Tú",
                color = Color.White,
                fontSize = 9.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
            )
        }

        // Emoji de camión avanzando por la pista
        val camionXdp = with(densidad) { posicionCamion.x.toDp() }
        val camionYdp = with(densidad) { posicionCamion.y.toDp() }
        Box(
            modifier = Modifier
                .offset(x = camionXdp - 15.dp, y = camionYdp - 15.dp)
                .size(30.dp)
                .background(BlancoTarjeta, CircleShape)
                .border(1.5.dp, NaranjaPrincipal, CircleShape),
            contentAlignment = Alignment.Center
        ) {
            Text(text = "🚚", fontSize = 15.sp)
        }

        // Etiqueta inferior dentro del mapa
        Surface(
            color = BlancoTarjeta.copy(alpha = 0.95f),
            shape = CircleShape,
            shadowElevation = 2.dp,
            modifier = Modifier
                .align(Alignment.BottomStart)
                .padding(8.dp)
        ) {
            Row(
                modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(Icons.Default.Place, contentDescription = null, tint = NaranjaOscuro, modifier = Modifier.size(13.dp))
                Spacer(modifier = Modifier.width(4.dp))
                Text("En camino hacia tu punto de entrega", fontSize = 10.sp, fontWeight = FontWeight.Bold, color = TextoOscuro)
            }
        }
    }
}