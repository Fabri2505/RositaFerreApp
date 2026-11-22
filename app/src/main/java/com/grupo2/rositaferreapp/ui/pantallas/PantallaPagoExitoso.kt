package com.grupo2.rositaferreapp.ui.pantallas

import android.widget.Toast
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.grupo2.rositaferreapp.R
import com.grupo2.rositaferreapp.navegacion.ControladorNavegacion
import com.grupo2.rositaferreapp.ui.theme.*
import kotlin.math.roundToInt

private fun formatearMontoFinal(valor: Double): String {
    val redondeado = (valor * 100.0).roundToInt() / 100.0
    val texto = redondeado.toString()
    return if (texto.substringAfter(".").length == 1) "${texto}0" else texto
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PantallaPagoExitoso(navController: ControladorNavegacion) {
    val contexto = LocalContext.current
    val portapapeles = LocalClipboardManager.current

    var animacionIniciada by remember { mutableStateOf(false) }
    LaunchedEffect(Unit) {
        animacionIniciada = true
    }

    val escalaVerde by animateFloatAsState(
        targetValue = if (animacionIniciada) 1f else 0.3f,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessLow
        ),
        label = "escalaCheckVerde"
    )

    val verdeExito = Color(0xFF2E7D32)
    val verdeClaroFondo = Color(0xFFE8F5E9)
    val codigoPedido = navController.ultimoCodigoPedido

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
                    IconButton(onClick = { navController.irACatalogo() }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Inicio", tint = TextoOscuro)
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
                .padding(horizontal = 16.dp, vertical = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // 1. ANIMACIÓN VERDE SENCILLA DE PAGO CONFIRMADO
            Box(
                modifier = Modifier
                    .scale(escalaVerde)
                    .size(84.dp)
                    .background(verdeClaroFondo, CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Box(
                    modifier = Modifier
                        .size(62.dp)
                        .background(verdeExito, CircleShape),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.Check,
                        contentDescription = "Pago Exitoso",
                        tint = Color.White,
                        modifier = Modifier.size(36.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            Surface(color = verdeClaroFondo, shape = CircleShape) {
                Row(
                    modifier = Modifier.padding(horizontal = 12.dp, vertical = 4.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(Icons.Default.CheckCircle, contentDescription = null, tint = verdeExito, modifier = Modifier.size(14.dp))
                    Spacer(modifier = Modifier.width(6.dp))
                    Text("TRANSACCIÓN EXITOSA", fontSize = 11.sp, fontWeight = FontWeight.Bold, color = verdeExito)
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            Text("¡Pedido Confirmado!", fontSize = 24.sp, fontWeight = FontWeight.ExtraBold, color = TextoOscuro)
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = "Tu orden ya fue registrada en el almacén central de Ferretería Rosita.",
                fontSize = 13.sp,
                color = TextoSecundario,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(16.dp))

            // 2. TARJETA PRINCIPAL DEL COMPROBANTE Y MONTO PAGADO
            Surface(
                color = BlancoTarjeta,
                shape = RoundedCornerShape(14.dp),
                shadowElevation = 2.dp,
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(14.dp)) {
                    Surface(color = ContenedorBajo, shape = RoundedCornerShape(10.dp), modifier = Modifier.fillMaxWidth()) {
                        Row(
                            modifier = Modifier.padding(12.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Column {
                                Text("CÓDIGO DE PEDIDO", fontSize = 10.sp, fontWeight = FontWeight.Bold, color = TextoSecundario)
                                Text(codigoPedido, fontSize = 16.sp, fontWeight = FontWeight.ExtraBold, color = TextoOscuro)
                            }
                            Surface(
                                color = BlancoTarjeta,
                                shape = RoundedCornerShape(8.dp),
                                modifier = Modifier.clickable {
                                    portapapeles.setText(AnnotatedString(codigoPedido))
                                    Toast.makeText(contexto, "Código copiado", Toast.LENGTH_SHORT).show()
                                }
                            ) {
                                Text(
                                    text = "Copiar",
                                    fontSize = 11.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = AzulSecundario,
                                    modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp)
                                )
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                        Surface(color = ContenedorBajo, shape = RoundedCornerShape(10.dp), modifier = Modifier.weight(1f)) {
                            Column(modifier = Modifier.padding(10.dp)) {
                                Text("Estado del Pago", fontSize = 10.sp, color = TextoSecundario)
                                Text("Aprobado", fontSize = 13.sp, fontWeight = FontWeight.Bold, color = verdeExito)
                            }
                        }
                        Surface(color = ContenedorBajo, shape = RoundedCornerShape(10.dp), modifier = Modifier.weight(1f)) {
                            Column(modifier = Modifier.padding(10.dp)) {
                                Text("Medio de Pago", fontSize = 10.sp, color = TextoSecundario)
                                Text(
                                    text = navController.metodoPagoSeleccionado,
                                    fontSize = 13.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = TextoOscuro,
                                    maxLines = 1
                                )
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    Surface(color = DuraznoClaro, shape = RoundedCornerShape(10.dp), modifier = Modifier.fillMaxWidth()) {
                        Row(
                            modifier = Modifier.padding(12.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text("Monto Total Cancelado", fontSize = 13.sp, fontWeight = FontWeight.Bold, color = TextoOscuro)
                            Text(
                                text = "S/ ${formatearMontoFinal(navController.ultimoMontoPagado)}",
                                fontSize = 20.sp,
                                fontWeight = FontWeight.ExtraBold,
                                color = NaranjaOscuro
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    Surface(color = ContenedorBajo, shape = RoundedCornerShape(10.dp), modifier = Modifier.fillMaxWidth()) {
                        Column(modifier = Modifier.padding(12.dp)) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    text = "${navController.tipoComprobante} SUNAT",
                                    fontSize = 12.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = TextoOscuro
                                )
                                Surface(color = verdeClaroFondo, shape = CircleShape) {
                                    Text(
                                        text = "Emitida",
                                        fontSize = 10.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = verdeExito,
                                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.dp)
                                    )
                                }
                            }
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = "Doc: ${navController.documentoComprobante} • ${navController.nombreComprobante}",
                                fontSize = 11.sp,
                                color = TextoSecundario
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            // 3. TARJETA SENCILLA DE DESPACHO & ENTREGA
            Surface(
                color = BlancoTarjeta,
                shape = RoundedCornerShape(14.dp),
                shadowElevation = 1.dp,
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(14.dp)) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Box(
                            modifier = Modifier.size(34.dp).background(DuraznoClaro, RoundedCornerShape(8.dp)),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(Icons.Default.Place, contentDescription = null, tint = NaranjaOscuro, modifier = Modifier.size(18.dp))
                        }
                        Spacer(modifier = Modifier.width(10.dp))
                        Column {
                            Text("Despacho & Entrega", fontSize = 14.sp, fontWeight = FontWeight.Bold, color = TextoOscuro)
                            Text(
                                text = if (navController.esDelivery) "Envío a Domicilio / Obra" else "Recojo en Tienda Rosita",
                                fontSize = 11.sp,
                                color = NaranjaOscuro,
                                fontWeight = FontWeight.SemiBold
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    Row(verticalAlignment = Alignment.Top) {
                        Icon(Icons.Default.LocationOn, contentDescription = null, tint = AzulSecundario, modifier = Modifier.size(16.dp))
                        Spacer(modifier = Modifier.width(8.dp))
                        Column {
                            Text("Destino de Entrega", fontSize = 10.sp, color = TextoSecundario)
                            Text(
                                text = if (navController.esDelivery) "${navController.direccionEntrega} - ${navController.distritoEntrega}" else "Av. Perú 2450 - San Martín de Porres",
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Bold,
                                color = TextoOscuro
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    Row(verticalAlignment = Alignment.Top) {
                        Icon(Icons.Default.DateRange, contentDescription = null, tint = verdeExito, modifier = Modifier.size(16.dp))
                        Spacer(modifier = Modifier.width(8.dp))
                        Column {
                            Text("Tiempo Estimado de Entrega", fontSize = 10.sp, color = TextoSecundario)
                            Text(
                                text = if (navController.esDelivery) "Dentro de los próximos 3 días hábiles" else "Disponible en 24 horas en tienda",
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Bold,
                                color = TextoOscuro
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            // 4. COMPRA 100% PROTEGIDA
            Surface(color = ContenedorBajo, shape = RoundedCornerShape(12.dp), modifier = Modifier.fillMaxWidth()) {
                Row(modifier = Modifier.padding(12.dp), verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Default.Lock, contentDescription = null, tint = AzulSecundario, modifier = Modifier.size(20.dp))
                    Spacer(modifier = Modifier.width(10.dp))
                    Column {
                        Text("Compra 100% Protegida", fontSize = 13.sp, fontWeight = FontWeight.Bold, color = TextoOscuro)
                        Text("Productos verificados en almacén con respaldo de garantía Rosita.", fontSize = 11.sp, color = TextoSecundario)
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // 5. BOTONES FINALES
            Button(
                onClick = { navController.irAPedidos() },
                colors = ButtonDefaults.buttonColors(containerColor = NaranjaPrincipal),
                shape = RoundedCornerShape(10.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp)
            ) {
                Icon(Icons.Default.CheckCircle, contentDescription = null, modifier = Modifier.size(18.dp))
                Spacer(modifier = Modifier.width(8.dp))
                Text("Ver Mis Pedidos Guardados", fontSize = 14.sp, fontWeight = FontWeight.Bold)
            }

            Spacer(modifier = Modifier.height(10.dp))

            OutlinedButton(
                onClick = { navController.irACatalogo() },
                border = BorderStroke(1.dp, ContenedorAlto),
                colors = ButtonDefaults.outlinedButtonColors(containerColor = BlancoTarjeta),
                shape = RoundedCornerShape(10.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp)
            ) {
                Icon(Icons.Default.Home, contentDescription = null, tint = TextoOscuro, modifier = Modifier.size(18.dp))
                Spacer(modifier = Modifier.width(8.dp))
                Text("Volver a la Tienda / Comprar Más", fontSize = 14.sp, fontWeight = FontWeight.Bold, color = TextoOscuro)
            }

            Spacer(modifier = Modifier.height(20.dp))
        }
    }
}