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
import androidx.compose.material.icons.automirrored.filled.Send
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.grupo2.rositaferreapp.R
import com.grupo2.rositaferreapp.datos.DatosCatalogo
import com.grupo2.rositaferreapp.datos.ItemCarrito
import com.grupo2.rositaferreapp.navegacion.ControladorNavegacion
import com.grupo2.rositaferreapp.ui.theme.*
import kotlin.math.roundToInt

private fun formatearPrecioReclamo(valor: Double): String {
    val redondeado = (valor * 100.0).roundToInt() / 100.0
    val texto = redondeado.toString()
    return if (texto.substringAfter(".").length == 1) "${texto}0" else texto
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PantallaReclamo(navController: ControladorNavegacion) {
    val contexto = LocalContext.current
    val pedido = navController.pedidoSeleccionado

    // Obtenemos todos los productos que el cliente pidió en esa orden
    val productosDelPedido = remember(pedido) {
        if (pedido != null && pedido.itemsComprados.isNotEmpty()) {
            pedido.itemsComprados
        } else {
            listOf(ItemCarrito(DatosCatalogo.listaProductos[0], 1))
        }
    }

    // Estados del Paso 1: Producto seleccionado y cantidad a reportar
    var indiceProductoAfectado by remember { mutableIntStateOf(0) }
    var cantidadReportar by remember { mutableIntStateOf(1) }

    // Estados del Paso 2: Motivo del reclamo
    val listaMotivos = listOf(
        "Herramienta defectuosa / No enciende o presenta falla",
        "Producto o especificación equivocada",
        "Caja o empaque llegó golpeado / dañado por transporte",
        "Material incompleto o faltan accesorios",
        "Otro motivo técnico"
    )
    var motivoSeleccionado by remember { mutableIntStateOf(0) }

    // Estados del Paso 3: Detalle y foto
    var detalleProblema by remember { mutableStateOf("") }
    var fotoSubida by remember { mutableStateOf(false) }

    // Estados del Paso 4: Solución preferida
    var solucionSeleccionada by remember { mutableIntStateOf(0) }

    // Estados del Paso 5: Modalidad de inspección
    var esRecojoEnObra by remember { mutableStateOf(true) }

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
                        Text("Atención de Reclamos", fontSize = 16.sp, fontWeight = FontWeight.Bold, color = TextoOscuro)
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
        bottomBar = {
            Surface(color = BlancoTarjeta, shadowElevation = 8.dp, modifier = Modifier.fillMaxWidth()) {
                Column(modifier = Modifier.padding(horizontal = 16.dp, vertical = 10.dp)) {
                    Button(
                        onClick = {
                            if (detalleProblema.isBlank()) {
                                Toast.makeText(contexto, "Describe brevemente el problema en el paso 3", Toast.LENGTH_SHORT).show()
                            } else {
                                Toast.makeText(
                                    contexto,
                                    "Solicitud enviada para ${pedido?.codigo ?: "#ROS-2026"}. Un asesor te contactará pronto.",
                                    Toast.LENGTH_LONG
                                ).show()
                                navController.irAPedidos()
                            }
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = NaranjaPrincipal),
                        shape = RoundedCornerShape(10.dp),
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(48.dp)
                    ) {
                        Icon(Icons.AutoMirrored.Filled.Send, contentDescription = null, modifier = Modifier.size(18.dp))
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("Enviar Solicitud de Cambio / Devolución", fontSize = 13.sp, fontWeight = FontWeight.Bold)
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
            // CABECERA DEL PEDIDO Y GARANTÍA
            Surface(color = BlancoTarjeta, shape = RoundedCornerShape(14.dp), shadowElevation = 1.dp, modifier = Modifier.fillMaxWidth()) {
                Column(modifier = Modifier.padding(14.dp)) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(Icons.Default.CheckCircle, contentDescription = null, tint = AzulSecundario, modifier = Modifier.size(14.dp))
                        Spacer(modifier = Modifier.width(6.dp))
                        Text("POST-VENTA & GARANTÍA", fontSize = 10.sp, fontWeight = FontWeight.Bold, color = AzulSecundario)
                    }

                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "Pedido ${pedido?.codigo ?: "#ROS-2026-88492"}",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.ExtraBold,
                        color = TextoOscuro
                    )
                    Text(
                        text = pedido?.comprobante ?: "Comprobante Emitido",
                        fontSize = 11.sp,
                        color = TextoSecundario
                    )
                    Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.padding(top = 2.dp)) {
                        Icon(Icons.Default.Place, contentDescription = null, tint = NaranjaOscuro, modifier = Modifier.size(13.dp))
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = pedido?.destino ?: "${navController.direccionEntrega} - ${navController.distritoEntrega}",
                            fontSize = 11.sp,
                            color = TextoSecundario
                        )
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    // Banner Garantía Técnica 7 Días
                    Surface(color = DuraznoClaro, shape = RoundedCornerShape(10.dp), modifier = Modifier.fillMaxWidth()) {
                        Row(modifier = Modifier.padding(10.dp), verticalAlignment = Alignment.Top) {
                            Icon(Icons.Default.Lock, contentDescription = null, tint = NaranjaOscuro, modifier = Modifier.size(18.dp))
                            Spacer(modifier = Modifier.width(8.dp))
                            Column {
                                Text("Garantía Técnica de 7 Días Ferretería Rosita", fontSize = 12.sp, fontWeight = FontWeight.Bold, color = NaranjaOscuro)
                                Text(
                                    text = "Cambio directo por falla de fábrica, avería o empaque dañado durante el transporte.",
                                    fontSize = 11.sp,
                                    color = TextoOscuro
                                )
                            }
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            // 1. SELECCIONA EL PRODUCTO AFECTADO (TODOS LOS DEL PEDIDO)
            Surface(color = BlancoTarjeta, shape = RoundedCornerShape(14.dp), shadowElevation = 1.dp, modifier = Modifier.fillMaxWidth()) {
                Column(modifier = Modifier.padding(14.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            NumeroPasoCircular("1")
                            Spacer(modifier = Modifier.width(8.dp))
                            Text("Selecciona el producto afectado", fontSize = 14.sp, fontWeight = FontWeight.Bold, color = TextoOscuro)
                        }
                        Text("1 seleccionado", fontSize = 10.sp, color = AzulSecundario, fontWeight = FontWeight.Bold)
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    productosDelPedido.forEachIndexed { index, item ->
                        val seleccionado = (index == indiceProductoAfectado)
                        Surface(
                            color = if (seleccionado) ContenedorBajo else BlancoTarjeta,
                            shape = RoundedCornerShape(12.dp),
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(bottom = 8.dp)
                                .border(
                                    width = if (seleccionado) 1.5.dp else 1.dp,
                                    color = if (seleccionado) AzulSecundario else ContenedorAlto,
                                    shape = RoundedCornerShape(12.dp)
                                )
                                .clip(RoundedCornerShape(12.dp))
                                .clickable {
                                    indiceProductoAfectado = index
                                    cantidadReportar = 1
                                }
                        ) {
                            Column(modifier = Modifier.padding(10.dp)) {
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Checkbox(
                                        checked = seleccionado,
                                        onCheckedChange = {
                                            indiceProductoAfectado = index
                                            cantidadReportar = 1
                                        },
                                        colors = CheckboxDefaults.colors(checkedColor = AzulSecundario)
                                    )
                                    Image(
                                        painter = painterResource(id = item.producto.imagenId),
                                        contentDescription = null,
                                        contentScale = ContentScale.Fit,
                                        modifier = Modifier
                                            .size(50.dp)
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
                                            maxLines = 2,
                                            overflow = TextOverflow.Ellipsis
                                        )
                                        Text("SKU: ${item.producto.sku}", fontSize = 10.sp, color = TextoSecundario)
                                        Spacer(modifier = Modifier.height(4.dp))
                                        Row(verticalAlignment = Alignment.CenterVertically) {
                                            Text(
                                                text = "S/ ${formatearPrecioReclamo(item.subtotal)}",
                                                fontSize = 14.sp,
                                                fontWeight = FontWeight.ExtraBold,
                                                color = AzulSecundario
                                            )
                                            Spacer(modifier = Modifier.width(8.dp))
                                            Surface(color = BlancoTarjeta, shape = RoundedCornerShape(4.dp)) {
                                                Text(
                                                    text = "Cant. comprada: ${item.cantidad} und",
                                                    fontSize = 10.sp,
                                                    fontWeight = FontWeight.Bold,
                                                    color = TextoOscuro,
                                                    modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                                                )
                                            }
                                        }
                                    }
                                }

                                // Selector de cantidad a reportar si está marcado
                                if (seleccionado) {
                                    HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp), color = ContenedorAlto)
                                    Row(
                                        modifier = Modifier.fillMaxWidth(),
                                        horizontalArrangement = Arrangement.SpaceBetween,
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Text("Cantidad a reportar:", fontSize = 12.sp, fontWeight = FontWeight.Bold, color = TextoOscuro)
                                        Row(verticalAlignment = Alignment.CenterVertically) {
                                            IconButton(
                                                onClick = { if (cantidadReportar > 1) cantidadReportar-- },
                                                modifier = Modifier.size(30.dp).background(BlancoTarjeta, RoundedCornerShape(6.dp))
                                            ) {
                                                Text("-", fontWeight = FontWeight.Bold, fontSize = 16.sp)
                                            }
                                            Text(
                                                text = "$cantidadReportar unidad",
                                                fontSize = 13.sp,
                                                fontWeight = FontWeight.ExtraBold,
                                                modifier = Modifier.padding(horizontal = 12.dp)
                                            )
                                            IconButton(
                                                onClick = { if (cantidadReportar < item.cantidad) cantidadReportar++ },
                                                modifier = Modifier.size(30.dp).background(BlancoTarjeta, RoundedCornerShape(6.dp))
                                            ) {
                                                Text("+", fontWeight = FontWeight.Bold, fontSize = 16.sp)
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            // 2. MOTIVO DEL RECLAMO TÉCNICO
            Surface(color = BlancoTarjeta, shape = RoundedCornerShape(14.dp), shadowElevation = 1.dp, modifier = Modifier.fillMaxWidth()) {
                Column(modifier = Modifier.padding(14.dp)) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        NumeroPasoCircular("2")
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("Motivo del reclamo técnico", fontSize = 14.sp, fontWeight = FontWeight.Bold, color = TextoOscuro)
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    listaMotivos.forEachIndexed { idx, motivo ->
                        val activo = (idx == motivoSeleccionado)
                        Surface(
                            color = if (activo) ContenedorBajo else Color.Transparent,
                            shape = RoundedCornerShape(10.dp),
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable { motivoSeleccionado = idx }
                        ) {
                            Row(
                                modifier = Modifier.padding(vertical = 4.dp, horizontal = 4.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                RadioButton(
                                    selected = activo,
                                    onClick = { motivoSeleccionado = idx },
                                    colors = RadioButtonDefaults.colors(selectedColor = AzulSecundario)
                                )
                                Column {
                                    Text(
                                        text = motivo,
                                        fontSize = 12.sp,
                                        fontWeight = if (activo) FontWeight.Bold else FontWeight.Normal,
                                        color = TextoOscuro
                                    )
                                    if (idx == 0 && activo) {
                                        Text("Revisión prioritaria por Servicio Autorizado", fontSize = 10.sp, color = AzulSecundario)
                                    }
                                }
                            }
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            // 3. EVIDENCIAS Y DETALLE TÉCNICO
            Surface(color = BlancoTarjeta, shape = RoundedCornerShape(14.dp), shadowElevation = 1.dp, modifier = Modifier.fillMaxWidth()) {
                Column(modifier = Modifier.padding(14.dp)) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        NumeroPasoCircular("3")
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("Evidencias y detalle técnico", fontSize = 14.sp, fontWeight = FontWeight.Bold, color = TextoOscuro)
                    }

                    Spacer(modifier = Modifier.height(8.dp))
                    Text("Describe el problema presentado (sé específico):", fontSize = 11.sp, fontWeight = FontWeight.Bold, color = TextoOscuro)
                    Spacer(modifier = Modifier.height(6.dp))

                    OutlinedTextField(
                        value = detalleProblema,
                        onValueChange = { if (it.length <= 500) detalleProblema = it },
                        placeholder = { Text("Ej. Al encender el equipo emite ruido o llegó con el empaque abierto...", fontSize = 12.sp) },
                        minLines = 3,
                        shape = RoundedCornerShape(10.dp),
                        modifier = Modifier.fillMaxWidth()
                    )

                    Row(
                        modifier = Modifier.fillMaxWidth().padding(top = 4.dp),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text("Incluye número de serie si es legible", fontSize = 10.sp, color = TextoSecundario)
                        Text("${detalleProblema.length} / 500 car.", fontSize = 10.sp, color = TextoSecundario)
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    // Botón sencillo para añadir foto de respaldo
                    OutlinedButton(
                        onClick = {
                            fotoSubida = !fotoSubida
                            Toast.makeText(contexto, "Fotografía de respaldo adjuntada", Toast.LENGTH_SHORT).show()
                        },
                        border = BorderStroke(1.dp, if (fotoSubida) Color(0xFF2E7D32) else ContenedorAlto),
                        shape = RoundedCornerShape(10.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Icon(
                            imageVector = if (fotoSubida) Icons.Default.CheckCircle else Icons.Default.AddCircle,
                            contentDescription = null,
                            tint = if (fotoSubida) Color(0xFF2E7D32) else NaranjaOscuro
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = if (fotoSubida) "Foto de respaldo adjuntada ✓" else "Añadir foto o video de evidencia",
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold,
                            color = if (fotoSubida) Color(0xFF2E7D32) else TextoOscuro
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            // 4. TIPO DE SOLUCIÓN PREFERIDA
            Surface(color = BlancoTarjeta, shape = RoundedCornerShape(14.dp), shadowElevation = 1.dp, modifier = Modifier.fillMaxWidth()) {
                Column(modifier = Modifier.padding(14.dp)) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        NumeroPasoCircular("4")
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("Tipo de solución preferida", fontSize = 14.sp, fontWeight = FontWeight.Bold, color = TextoOscuro)
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    OpcionSolucionCard(
                        titulo = "Cambio por producto nuevo e idéntico",
                        subtitulo = "Reemplazo directo en obra o domicilio en menos de 24 hrs.",
                        recomendado = true,
                        seleccionado = solucionSeleccionada == 0,
                        alClick = { solucionSeleccionada = 0 }
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    OpcionSolucionCard(
                        titulo = "Nota de Crédito Rosita (Saldo a favor)",
                        subtitulo = "Válido para cualquier producto en la app o mostrador.",
                        recomendado = false,
                        seleccionado = solucionSeleccionada == 1,
                        alClick = { solucionSeleccionada = 1 }
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    OpcionSolucionCard(
                        titulo = "Reembolso del dinero (Yape / Tarjeta)",
                        subtitulo = "Acreditación a la misma cuenta de origen en 24 a 48 horas hábiles.",
                        recomendado = false,
                        seleccionado = solucionSeleccionada == 2,
                        alClick = { solucionSeleccionada = 2 }
                    )
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            // 5. MODALIDAD DE INSPECCIÓN Y ENTREGA
            Surface(color = BlancoTarjeta, shape = RoundedCornerShape(14.dp), shadowElevation = 1.dp, modifier = Modifier.fillMaxWidth()) {
                Column(modifier = Modifier.padding(14.dp)) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        NumeroPasoCircular("5")
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("Modalidad de inspección y entrega", fontSize = 14.sp, fontWeight = FontWeight.Bold, color = TextoOscuro)
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        Surface(
                            color = if (esRecojoEnObra) DuraznoClaro else ContenedorBajo,
                            shape = RoundedCornerShape(10.dp),
                            modifier = Modifier
                                .weight(1f)
                                .border(1.dp, if (esRecojoEnObra) NaranjaPrincipal else ContenedorAlto, RoundedCornerShape(10.dp))
                                .clickable { esRecojoEnObra = true }
                        ) {
                            Text(
                                text = "Recojo en Obra / Casa",
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Bold,
                                color = if (esRecojoEnObra) NaranjaOscuro else TextoOscuro,
                                textAlign = TextAlign.Center,
                                modifier = Modifier.padding(vertical = 10.dp)
                            )
                        }
                        Surface(
                            color = if (!esRecojoEnObra) DuraznoClaro else ContenedorBajo,
                            shape = RoundedCornerShape(10.dp),
                            modifier = Modifier
                                .weight(1f)
                                .border(1.dp, if (!esRecojoEnObra) NaranjaPrincipal else ContenedorAlto, RoundedCornerShape(10.dp))
                                .clickable { esRecojoEnObra = false }
                        ) {
                            Text(
                                text = "Entrega en Tienda",
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Bold,
                                color = if (!esRecojoEnObra) NaranjaOscuro else TextoOscuro,
                                textAlign = TextAlign.Center,
                                modifier = Modifier.padding(vertical = 10.dp)
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    Surface(color = ContenedorBajo, shape = RoundedCornerShape(10.dp), modifier = Modifier.fillMaxWidth()) {
                        Column(modifier = Modifier.padding(12.dp)) {
                            Text("PUNTO DE RECOJO REGISTRADO", fontSize = 10.sp, fontWeight = FontWeight.Bold, color = AzulSecundario)
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = if (esRecojoEnObra) {
                                    pedido?.destino ?: "${navController.direccionEntrega}, ${navController.distritoEntrega}"
                                } else {
                                    "Sede Principal: Av. Perú 2450 - San Martín de Porres"
                                },
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Bold,
                                color = TextoOscuro
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = "Contacto: ${pedido?.receptor ?: navController.nombreReceptor} • ${navController.telefonoReceptor}",
                                fontSize = 11.sp,
                                color = TextoSecundario
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(20.dp))
        }
    }
}

@Composable
private fun NumeroPasoCircular(numero: String) {
    Box(
        modifier = Modifier
            .size(22.dp)
            .background(NaranjaPrincipal, CircleShape),
        contentAlignment = Alignment.Center
    ) {
        Text(numero, color = Color.White, fontSize = 11.sp, fontWeight = FontWeight.Bold)
    }
}

@Composable
private fun OpcionSolucionCard(
    titulo: String,
    subtitulo: String,
    recomendado: Boolean,
    seleccionado: Boolean,
    alClick: () -> Unit
) {
    Surface(
        color = if (seleccionado) ContenedorBajo else BlancoTarjeta,
        shape = RoundedCornerShape(10.dp),
        modifier = Modifier
            .fillMaxWidth()
            .border(
                width = if (seleccionado) 1.5.dp else 1.dp,
                color = if (seleccionado) AzulSecundario else ContenedorAlto,
                shape = RoundedCornerShape(10.dp)
            )
            .clickable { alClick() }
    ) {
        Row(modifier = Modifier.padding(10.dp), verticalAlignment = Alignment.Top) {
            RadioButton(
                selected = seleccionado,
                onClick = alClick,
                colors = RadioButtonDefaults.colors(selectedColor = AzulSecundario)
            )
            Column(modifier = Modifier.weight(1f)) {
                Text(titulo, fontSize = 13.sp, fontWeight = FontWeight.Bold, color = TextoOscuro)
                if (recomendado) {
                    Spacer(modifier = Modifier.height(2.dp))
                    Surface(color = NaranjaPrincipal, shape = RoundedCornerShape(4.dp)) {
                        Text(
                            text = "Recomendado",
                            color = Color.White,
                            fontSize = 9.sp,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(horizontal = 6.dp, vertical = 1.dp)
                        )
                    }
                }
                Spacer(modifier = Modifier.height(3.dp))
                Text(subtitulo, fontSize = 11.sp, color = TextoSecundario)
            }
        }
    }
}