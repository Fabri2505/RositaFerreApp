package com.grupo2.rositaferreapp.ui.pantallas

import android.widget.Toast
import androidx.compose.animation.core.*
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
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
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.grupo2.rositaferreapp.R
import com.grupo2.rositaferreapp.navegacion.ControladorNavegacion
import com.grupo2.rositaferreapp.ui.theme.*
import kotlin.math.roundToInt

private fun formatearMonto(valor: Double): String {
    val redondeado = (valor * 100.0).roundToInt() / 100.0
    val texto = redondeado.toString()
    return if (texto.substringAfter(".").length == 1) "${texto}0" else texto
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PantallaEntrega(navController: ControladorNavegacion) {
    val contexto = LocalContext.current

    var esDelivery by remember { mutableStateOf(navController.esDelivery) }
    var ubicacion by remember { mutableStateOf(navController.direccionEntrega) }
    var distrito by remember { mutableStateOf(navController.distritoEntrega) }
    var nombre by remember { mutableStateOf(navController.nombreReceptor) }
    var numero by remember { mutableStateOf(navController.telefonoReceptor) }
    var gpsUbicado by remember { mutableStateOf(true) }

    val subtotalMateriales = navController.calcularTotalProductos()
    val costoFlete = if (esDelivery) 15.00 else 0.00
    val totalConFlete = subtotalMateriales + costoFlete

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
                        Text("Proceso De Pago", fontSize = 16.sp, fontWeight = FontWeight.Bold, color = TextoOscuro)
                    }
                },
                navigationIcon = {
                    IconButton(onClick = { navController.irACarrito() }) {
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
        bottomBar = {
            Surface(color = BlancoTarjeta, shadowElevation = 8.dp, modifier = Modifier.fillMaxWidth()) {
                Column(modifier = Modifier.padding(horizontal = 16.dp, vertical = 10.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = "Subtotal materiales: S/ ${formatearMonto(subtotalMateriales)}",
                            fontSize = 11.sp,
                            color = TextoSecundario
                        )
                        Text(
                            text = if (esDelivery) "Costo flete: S/ 15.00" else "Costo flete: GRATIS",
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            color = NaranjaOscuro
                        )
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    Button(
                        onClick = {
                            if (esDelivery && (ubicacion.isBlank() || distrito.isBlank())) {
                                Toast.makeText(contexto, "Completa tu ubicación y distrito", Toast.LENGTH_SHORT).show()
                            } else if (nombre.isBlank() || numero.length < 9) {
                                Toast.makeText(contexto, "Ingresa el nombre y celular (9 dígitos)", Toast.LENGTH_SHORT).show()
                            } else {
                                navController.esDelivery = esDelivery
                                navController.direccionEntrega = ubicacion
                                navController.distritoEntrega = distrito
                                navController.nombreReceptor = nombre
                                navController.telefonoReceptor = numero
                                navController.costoEnvioActivo = costoFlete

                                navController.irAPago()
                            }
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = NaranjaPrincipal),
                        shape = RoundedCornerShape(10.dp),
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(48.dp)
                    ) {
                        Text(
                            text = "Continuar al Pago (S/ ${formatearMonto(totalConFlete)})",
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Bold
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Icon(Icons.AutoMirrored.Filled.ArrowForward, contentDescription = null, modifier = Modifier.size(18.dp))
                    }

                    Spacer(modifier = Modifier.height(6.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(Icons.Default.Lock, contentDescription = null, tint = Color.Gray, modifier = Modifier.size(12.dp))
                        Spacer(modifier = Modifier.width(4.dp))
                        Text("Compra 100% Protegida • RUC 20601234567", fontSize = 10.sp, color = Color.Gray)
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
            // 1. INDICADOR DE PROGRESO (PASO 1 DE 3)
            Surface(color = BlancoTarjeta, shape = RoundedCornerShape(12.dp), modifier = Modifier.fillMaxWidth()) {
                Column(modifier = Modifier.padding(12.dp)) {
                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                        Text("PASO 1 DE 3", fontSize = 10.sp, fontWeight = FontWeight.Bold, color = NaranjaOscuro)
                        Text("Modalidad de Entrega", fontSize = 11.sp, color = TextoSecundario)
                    }
                    Spacer(modifier = Modifier.height(6.dp))
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(5.dp)
                            .clip(CircleShape)
                            .background(ContenedorBajo)
                    ) {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth(0.33f)
                                .fillMaxHeight()
                                .background(NaranjaPrincipal, CircleShape)
                        )
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                        Text("1. Entrega", fontSize = 11.sp, fontWeight = FontWeight.Bold, color = NaranjaOscuro)
                        Text("2. Pago", fontSize = 11.sp, color = Color.Gray)
                        Text("3. Resumen", fontSize = 11.sp, color = Color.Gray)
                    }
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            // 2. SELECTOR MODALIDAD: A DOMICILIO / OBRA vs RECOJO EN TIENDA
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                Surface(
                    color = if (esDelivery) BlancoTarjeta else ContenedorBajo,
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier
                        .weight(1f)
                        .border(
                            width = if (esDelivery) 2.dp else 1.dp,
                            color = if (esDelivery) NaranjaPrincipal else ContenedorAlto,
                            shape = RoundedCornerShape(12.dp)
                        )
                        .clip(RoundedCornerShape(12.dp))
                        .clickable { esDelivery = true }
                ) {
                    Column(
                        modifier = Modifier.padding(12.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Icon(
                            Icons.Default.Place,
                            contentDescription = null,
                            tint = if (esDelivery) NaranjaOscuro else TextoSecundario,
                            modifier = Modifier.size(22.dp)
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = "A Domicilio /\nObra",
                            fontSize = 13.sp,
                            fontWeight = FontWeight.Bold,
                            textAlign = TextAlign.Center,
                            color = TextoOscuro,
                            lineHeight = 16.sp
                        )
                        Spacer(modifier = Modifier.height(2.dp))
                        Text("S/ 15.00 • Lima", fontSize = 10.sp, color = TextoSecundario)
                    }
                }

                Surface(
                    color = if (!esDelivery) BlancoTarjeta else ContenedorBajo,
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier
                        .weight(1f)
                        .border(
                            width = if (!esDelivery) 2.dp else 1.dp,
                            color = if (!esDelivery) NaranjaPrincipal else ContenedorAlto,
                            shape = RoundedCornerShape(12.dp)
                        )
                        .clip(RoundedCornerShape(12.dp))
                        .clickable { esDelivery = false }
                ) {
                    Column(
                        modifier = Modifier.padding(12.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Icon(
                            Icons.Default.Home,
                            contentDescription = null,
                            tint = if (!esDelivery) NaranjaOscuro else TextoSecundario,
                            modifier = Modifier.size(22.dp)
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = "Recojo en\nTienda",
                            fontSize = 13.sp,
                            fontWeight = FontWeight.Bold,
                            textAlign = TextAlign.Center,
                            color = TextoOscuro,
                            lineHeight = 16.sp
                        )
                        Spacer(modifier = Modifier.height(2.dp))
                        Text("¡100% Gratis!", fontSize = 10.sp, fontWeight = FontWeight.Bold, color = AzulSecundario)
                    }
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            // 3. ESTIMACIÓN DE ENTREGA (3 DÍAS)
            Surface(
                color = DuraznoClaro,
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    modifier = Modifier.padding(12.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier
                            .size(36.dp)
                            .background(NaranjaPrincipal, RoundedCornerShape(8.dp)),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(Icons.Default.DateRange, contentDescription = null, tint = Color.White, modifier = Modifier.size(20.dp))
                    }
                    Spacer(modifier = Modifier.width(12.dp))
                    Column {
                        Text(
                            text = if (esDelivery) "Estimación de entrega: 3 días hábiles" else "Recojo disponible en 24 horas",
                            fontSize = 13.sp,
                            fontWeight = FontWeight.ExtraBold,
                            color = NaranjaOscuro
                        )
                        Text(
                            text = if (esDelivery) "Despacho programado directo a tu domicilio u obra." else "Sede Principal: Av. Perú 2450 - San Martín de Porres.",
                            fontSize = 11.sp,
                            color = TextoOscuro
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            // 4. MAPA SENCILLO CON ANIMACIÓN DE GPS
            Surface(
                color = BlancoTarjeta,
                shape = RoundedCornerShape(14.dp),
                shadowElevation = 1.dp,
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(12.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(Icons.Default.Place, contentDescription = null, tint = NaranjaOscuro, modifier = Modifier.size(18.dp))
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(
                                text = if (esDelivery) "Ubicación GPS seleccionada" else "Ubicación de Tienda Rosita",
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Bold,
                                color = TextoOscuro
                            )
                        }
                        Surface(color = ContenedorBajo, shape = CircleShape) {
                            Text(
                                text = if (gpsUbicado) "GPS Activo" else "Buscando...",
                                fontSize = 10.sp,
                                fontWeight = FontWeight.Bold,
                                color = AzulSecundario,
                                modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp)
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    MapaGpsAnimado(
                        textoZona = if (esDelivery) "$distrito • $ubicacion" else "Tienda Ferretería Rosita - SMP",
                        alPulsarMapa = {
                            gpsUbicado = true
                            Toast.makeText(contexto, "Punto GPS fijado en $distrito", Toast.LENGTH_SHORT).show()
                        }
                    )
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            // 5. CUADROS DE DATOS: UBICACIÓN, DISTRITO, NOMBRE Y NÚMERO
            Surface(
                color = BlancoTarjeta,
                shape = RoundedCornerShape(14.dp),
                shadowElevation = 1.dp,
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(14.dp)) {
                    Text(
                        text = if (esDelivery) "Datos de Entrega y Contacto" else "Datos de Quien Recoge",
                        fontSize = 15.sp,
                        fontWeight = FontWeight.ExtraBold,
                        color = TextoOscuro
                    )

                    Spacer(modifier = Modifier.height(10.dp))

                    if (esDelivery) {
                        OutlinedTextField(
                            value = ubicacion,
                            onValueChange = { ubicacion = it },
                            label = { Text("Ubicación / Dirección exacta") },
                            leadingIcon = { Icon(Icons.Default.Place, contentDescription = null, tint = NaranjaOscuro) },
                            singleLine = true,
                            shape = RoundedCornerShape(10.dp),
                            modifier = Modifier.fillMaxWidth()
                        )

                        Spacer(modifier = Modifier.height(10.dp))

                        OutlinedTextField(
                            value = distrito,
                            onValueChange = { distrito = it },
                            label = { Text("Distrito") },
                            leadingIcon = { Icon(Icons.Default.LocationOn, contentDescription = null, tint = AzulSecundario) },
                            singleLine = true,
                            shape = RoundedCornerShape(10.dp),
                            modifier = Modifier.fillMaxWidth()
                        )

                        Spacer(modifier = Modifier.height(10.dp))
                    }

                    OutlinedTextField(
                        value = nombre,
                        onValueChange = { nombre = it },
                        label = { Text("Nombre completo de quien recibe") },
                        leadingIcon = { Icon(Icons.Default.Person, contentDescription = null, tint = NaranjaOscuro) },
                        singleLine = true,
                        shape = RoundedCornerShape(10.dp),
                        modifier = Modifier.fillMaxWidth()
                    )

                    Spacer(modifier = Modifier.height(10.dp))

                    OutlinedTextField(
                        value = numero,
                        onValueChange = { if (it.length <= 9 && it.all { c -> c.isDigit() }) numero = it },
                        label = { Text("Número de celular (9 dígitos)") },
                        leadingIcon = { Icon(Icons.Default.Phone, contentDescription = null, tint = AzulSecundario) },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
                        singleLine = true,
                        shape = RoundedCornerShape(10.dp),
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}

@Composable
private fun MapaGpsAnimado(
    textoZona: String,
    alPulsarMapa: () -> Unit
) {
    val transicion = rememberInfiniteTransition(label = "gps")
    val radioOnda by transicion.animateFloat(
        initialValue = 22f,
        targetValue = 75f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 1500, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "radio"
    )
    val opacidadOnda by transicion.animateFloat(
        initialValue = 0.55f,
        targetValue = 0f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 1500, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "alpha"
    )

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(145.dp)
            .clip(RoundedCornerShape(12.dp))
            .background(Color(0xFFEAF2F8))
            .clickable { alPulsarMapa() },
        contentAlignment = Alignment.Center
    ) {
        Canvas(modifier = Modifier.fillMaxSize()) {
            val w = size.width
            val h = size.height

            drawCircle(color = Color(0xFFD4EFDF), radius = 38f, center = Offset(w * 0.2f, h * 0.3f))
            drawCircle(color = Color(0xFFD4EFDF), radius = 45f, center = Offset(w * 0.82f, h * 0.7f))

            drawLine(color = Color.White, start = Offset(0f, h * 0.35f), end = Offset(w, h * 0.35f), strokeWidth = 14f)
            drawLine(color = Color.White, start = Offset(0f, h * 0.72f), end = Offset(w, h * 0.72f), strokeWidth = 10f)
            drawLine(color = Color.White, start = Offset(w * 0.28f, 0f), end = Offset(w * 0.28f, h), strokeWidth = 12f)
            drawLine(color = Color.White, start = Offset(w * 0.68f, 0f), end = Offset(w * 0.68f, h), strokeWidth = 14f)

            drawLine(color = Color(0xFFFDEBD0), start = Offset(0f, h * 0.52f), end = Offset(w, h * 0.48f), strokeWidth = 18f)

            drawCircle(
                color = NaranjaPrincipal.copy(alpha = opacidadOnda),
                radius = radioOnda,
                center = center
            )
        }

        Box(
            modifier = Modifier
                .size(42.dp)
                .background(NaranjaPrincipal, RoundedCornerShape(12.dp))
                .border(2.dp, Color.White, RoundedCornerShape(12.dp)),
            contentAlignment = Alignment.Center
        ) {
            Icon(Icons.Default.Build, contentDescription = null, tint = Color.White, modifier = Modifier.size(22.dp))
        }

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
                Icon(Icons.Default.CheckCircle, contentDescription = null, tint = NaranjaOscuro, modifier = Modifier.size(13.dp))
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    text = textoZona,
                    fontSize = 10.sp,
                    fontWeight = FontWeight.Bold,
                    color = TextoOscuro,
                    maxLines = 1
                )
            }
        }
    }
}