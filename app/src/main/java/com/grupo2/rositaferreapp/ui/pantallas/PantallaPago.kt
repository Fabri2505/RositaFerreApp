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
import androidx.compose.foundation.text.KeyboardOptions
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.grupo2.rositaferreapp.R
import com.grupo2.rositaferreapp.navegacion.ControladorNavegacion
import com.grupo2.rositaferreapp.ui.theme.*
import kotlin.math.roundToInt

private fun formatearTotalPago(valor: Double): String {
    val redondeado = (valor * 100.0).roundToInt() / 100.0
    val texto = redondeado.toString()
    return if (texto.substringAfter(".").length == 1) "${texto}0" else texto
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PantallaPago(navController: ControladorNavegacion) {
    val contexto = LocalContext.current
    val portapapeles = LocalClipboardManager.current

    // Estados del Método de Pago (true = Yape/Plin, false = Tarjeta)
    var esBilleteraDigital by remember { mutableStateOf(true) }
    var verDetallePedido by remember { mutableStateOf(false) }

    // Campos Yape / Plin
    var codigoOperacion by remember { mutableStateOf("") }
    var capturaAdjuntada by remember { mutableStateOf(false) }

    // Campos Tarjeta
    var numeroTarjeta by remember { mutableStateOf("") }
    var vencimientoTarjeta by remember { mutableStateOf("") }
    var cvvTarjeta by remember { mutableStateOf("") }

    // Campos Comprobante (true = Boleta DNI, false = Factura RUC)
    var esBoleta by remember { mutableStateOf(true) }
    var documentoCliente by remember { mutableStateOf("") }
    var nombreRazonSocial by remember { mutableStateOf(navController.nombreReceptor) }

    val totalUnidades = navController.contarProductos()
    val totalFinal = navController.calcularTotalProductos() + navController.costoEnvioActivo

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
                    IconButton(onClick = { navController.irAEntrega() }) {
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
                Column(
                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 10.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Button(
                        onClick = {
                            val maxDoc = if (esBoleta) 8 else 11
                            if (esBilleteraDigital && codigoOperacion.length < 6) {
                                Toast.makeText(contexto, "Ingresa el código de operación (6 dígitos)", Toast.LENGTH_SHORT).show()
                            } else if (!esBilleteraDigital && (numeroTarjeta.length < 16 || vencimientoTarjeta.length < 4 || cvvTarjeta.length < 3)) {
                                Toast.makeText(contexto, "Completa los datos de tu tarjeta", Toast.LENGTH_SHORT).show()
                            } else if (documentoCliente.length != maxDoc || nombreRazonSocial.isBlank()) {
                                Toast.makeText(
                                    contexto,
                                    if (esBoleta) "Ingresa un DNI válido de 8 dígitos" else "Ingresa un RUC válido de 11 dígitos",
                                    Toast.LENGTH_SHORT
                                ).show()
                            } else {
                        // Guardamos los datos para el Resumen Final
                        navController.metodoPagoSeleccionado = if (esBilleteraDigital) "Yape / Plin" else "Tarjeta Crédito / Débito"
                        navController.detallePagoGuardado = if (esBilleteraDigital) {
                            "Op. N° $codigoOperacion"
                        } else {
                            "Terminada en **** ${numeroTarjeta.takeLast(4)}"
                        }
                        navController.tipoComprobante = if (esBoleta) "Boleta de Venta" else "Factura Electrónica"
                        navController.documentoComprobante = documentoCliente
                        navController.nombreComprobante = nombreRazonSocial

                        navController.irAPagoExitoso()
                    }
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = NaranjaPrincipal),
                        shape = RoundedCornerShape(10.dp),
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(48.dp)
                    ) {
                        Icon(Icons.Default.Lock, contentDescription = null, modifier = Modifier.size(18.dp))
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "Confirmar y Pagar S/ ${formatearTotalPago(totalFinal)}",
                            fontSize = 15.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }

                    Spacer(modifier = Modifier.height(6.dp))

                    Text(
                        text = "Al pagar aceptas los Términos y Condiciones de Rosita Ferretería",
                        fontSize = 10.sp,
                        color = TextoSecundario
                    )
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
            // 1. INDICADOR PASO 2 DE 3
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text("PASO 2 DE 3", fontSize = 10.sp, fontWeight = FontWeight.Bold, color = NaranjaOscuro)
                Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                    Box(modifier = Modifier.width(24.dp).height(4.dp).background(NaranjaPrincipal, CircleShape))
                    Box(modifier = Modifier.width(24.dp).height(4.dp).background(NaranjaPrincipal, CircleShape))
                    Box(modifier = Modifier.width(24.dp).height(4.dp).background(ContenedorAlto, CircleShape))
                }
            }

            Spacer(modifier = Modifier.height(4.dp))
            Text("Selecciona tu Método de Pago", fontSize = 18.sp, fontWeight = FontWeight.ExtraBold, color = TextoOscuro)

            Spacer(modifier = Modifier.height(10.dp))

            // 2. TARJETA RESUMEN RÁPIDO DEL TOTAL + VER DETALLE
            Surface(color = BlancoTarjeta, shape = RoundedCornerShape(12.dp), shadowElevation = 1.dp, modifier = Modifier.fillMaxWidth()) {
                Column(modifier = Modifier.padding(12.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Box(
                                modifier = Modifier.size(38.dp).background(DuraznoClaro, RoundedCornerShape(8.dp)),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(Icons.Default.ShoppingBag, contentDescription = null, tint = NaranjaOscuro, modifier = Modifier.size(20.dp))
                            }
                            Spacer(modifier = Modifier.width(10.dp))
                            Column {
                                Text("Total del pedido ($totalUnidades artículos)", fontSize = 11.sp, color = TextoSecundario)
                                Text(
                                    text = "S/ ${formatearTotalPago(totalFinal)}",
                                    fontSize = 18.sp,
                                    fontWeight = FontWeight.ExtraBold,
                                    color = TextoOscuro
                                )
                            }
                        }

                        Surface(
                            color = ContenedorBajo,
                            shape = RoundedCornerShape(8.dp),
                            modifier = Modifier
                                .clip(RoundedCornerShape(8.dp))
                                .clickable { verDetallePedido = !verDetallePedido }
                        ) {
                            Row(
                                modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text("Ver detalle", fontSize = 11.sp, fontWeight = FontWeight.Bold, color = TextoOscuro)
                                Icon(
                                    imageVector = if (verDetallePedido) Icons.Default.KeyboardArrowUp else Icons.Default.KeyboardArrowDown,
                                    contentDescription = null,
                                    modifier = Modifier.size(16.dp)
                                )
                            }
                        }
                    }

                    // Lista desplegable de productos
                    if (verDetallePedido) {
                        HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp), color = ContenedorAlto)
                        navController.listaCarrito.forEach { item ->
                            Row(modifier = Modifier.fillMaxWidth().padding(vertical = 2.dp), horizontalArrangement = Arrangement.SpaceBetween) {
                                Text("${item.cantidad}x ${item.producto.nombre}", fontSize = 11.sp, color = TextoSecundario, modifier = Modifier.weight(1f))
                                Text("S/ ${formatearTotalPago(item.subtotal)}", fontSize = 11.sp, fontWeight = FontWeight.Bold)
                            }
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(14.dp))

            // 3. CABECERA FORMA DE PAGO
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text("Forma de pago", fontSize = 14.sp, fontWeight = FontWeight.Bold, color = TextoOscuro)
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Default.CheckCircle, contentDescription = null, tint = AzulSecundario, modifier = Modifier.size(14.dp))
                    Spacer(modifier = Modifier.width(4.dp))
                    Text("Transacción Segura", fontSize = 11.sp, fontWeight = FontWeight.Bold, color = AzulSecundario)
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            // 4. OPCIÓN A: BILLETERAS DIGITALES (YAPE / PLIN)
            Surface(
                color = BlancoTarjeta,
                shape = RoundedCornerShape(14.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .border(
                        width = if (esBilleteraDigital) 2.dp else 1.dp,
                        color = if (esBilleteraDigital) NaranjaPrincipal else ContenedorAlto,
                        shape = RoundedCornerShape(14.dp)
                    )
                    .clickable { esBilleteraDigital = true }
            ) {
                Column(modifier = Modifier.padding(14.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            RadioButton(
                                selected = esBilleteraDigital,
                                onClick = { esBilleteraDigital = true },
                                colors = RadioButtonDefaults.colors(selectedColor = NaranjaPrincipal)
                            )
                            Column {
                                Text("Billeteras Digitales", fontSize = 14.sp, fontWeight = FontWeight.Bold, color = TextoOscuro)
                                Text("Yape o Plin al instante y sin comisiones", fontSize = 11.sp, color = TextoSecundario)
                            }
                        }
                        Surface(color = DuraznoClaro, shape = CircleShape) {
                            Text(
                                text = "Recomendado",
                                fontSize = 10.sp,
                                fontWeight = FontWeight.Bold,
                                color = NaranjaOscuro,
                                modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp)
                            )
                        }
                    }

                    // Badges Yape y Plin
                    Row(modifier = Modifier.padding(start = 48.dp, top = 4.dp), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        Surface(color = Color(0xFF742284), shape = CircleShape) {
                            Text("• Yape", color = Color.White, fontSize = 10.sp, fontWeight = FontWeight.Bold, modifier = Modifier.padding(horizontal = 10.dp, vertical = 3.dp))
                        }
                        Surface(color = Color(0xFF00C2E0), shape = CircleShape) {
                            Text("• Plin", color = Color.White, fontSize = 10.sp, fontWeight = FontWeight.Bold, modifier = Modifier.padding(horizontal = 10.dp, vertical = 3.dp))
                        }
                    }

                    // Contenido desplegado cuando se elige Yape / Plin
                    if (esBilleteraDigital) {
                        Spacer(modifier = Modifier.height(12.dp))

                        // Caja con QR y Número Oficial
                        Surface(color = ContenedorBajo, shape = RoundedCornerShape(10.dp), modifier = Modifier.fillMaxWidth()) {
                            Row(
                                modifier = Modifier.padding(12.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                // QR visual sencillo
                                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                    Box(
                                        modifier = Modifier
                                            .size(64.dp)
                                            .background(BlancoTarjeta, RoundedCornerShape(8.dp))
                                            .border(1.dp, ContenedorAlto, RoundedCornerShape(8.dp)),
                                        contentAlignment = Alignment.Center
                                    ) {
                                        Icon(Icons.Default.QrCode2, contentDescription = "QR Yape Plin", tint = TextoOscuro, modifier = Modifier.size(48.dp))
                                    }
                                    Surface(color = NaranjaOscuro, shape = RoundedCornerShape(bottomStart = 6.dp, bottomEnd = 6.dp)) {
                                        Text("ESCANEAR", color = Color.White, fontSize = 8.sp, fontWeight = FontWeight.Bold, modifier = Modifier.padding(horizontal = 10.dp, vertical = 2.dp))
                                    }
                                }

                                Spacer(modifier = Modifier.width(12.dp))

                                Column(modifier = Modifier.weight(1f)) {
                                    Text("NÚMERO OFICIAL ROSITA", fontSize = 10.sp, fontWeight = FontWeight.Bold, color = TextoSecundario)
                                    Row(verticalAlignment = Alignment.CenterVertically) {
                                        Text("+51 987 654 321", fontSize = 16.sp, fontWeight = FontWeight.ExtraBold, color = TextoOscuro)
                                        IconButton(
                                            onClick = {
                                                portapapeles.setText(AnnotatedString("987654321"))
                                                Toast.makeText(contexto, "Número copiado", Toast.LENGTH_SHORT).show()
                                            },
                                            modifier = Modifier.size(28.dp)
                                        ) {
                                            Icon(Icons.Default.ContentCopy, contentDescription = "Copiar", tint = NaranjaOscuro, modifier = Modifier.size(16.dp))
                                        }
                                    }
                                    Text("Titular: Ferretería Rosita S.A.C.", fontSize = 11.sp, color = TextoSecundario)
                                }
                            }
                        }

                        Spacer(modifier = Modifier.height(10.dp))

                        // Código de operación (6 dígitos)
                        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                            Text("Código de Operación (6 dígitos)", fontSize = 12.sp, fontWeight = FontWeight.Bold, color = TextoOscuro)
                            Text("Obligatorio", fontSize = 11.sp, fontWeight = FontWeight.Bold, color = NaranjaOscuro)
                        }
                        Spacer(modifier = Modifier.height(4.dp))
                        OutlinedTextField(
                            value = codigoOperacion,
                            onValueChange = { if (it.length <= 6 && it.all { c -> c.isDigit() }) codigoOperacion = it },
                            placeholder = { Text("Ej. 483921") },
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                            singleLine = true,
                            shape = RoundedCornerShape(10.dp),
                            modifier = Modifier.fillMaxWidth()
                        )

                        Spacer(modifier = Modifier.height(10.dp))

                        // Adjuntar comprobante opcional
                        Surface(color = ContenedorBajo, shape = RoundedCornerShape(10.dp), modifier = Modifier.fillMaxWidth()) {
                            Row(
                                modifier = Modifier.padding(10.dp),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.weight(1f)) {
                                    Icon(Icons.Default.AddPhotoAlternate, contentDescription = null, tint = NaranjaOscuro)
                                    Spacer(modifier = Modifier.width(8.dp))
                                    Column {
                                        Text(
                                            text = if (capturaAdjuntada) "Captura adjuntada ✓" else "Subir comprobante o captura",
                                            fontSize = 12.sp,
                                            fontWeight = FontWeight.Bold,
                                            color = TextoOscuro
                                        )
                                        Text("PNG o JPG máx. 5MB (Opcional)", fontSize = 10.sp, color = TextoSecundario)
                                    }
                                }
                                OutlinedButton(
                                    onClick = {
                                        capturaAdjuntada = !capturaAdjuntada
                                        Toast.makeText(contexto, "Comprobante seleccionado", Toast.LENGTH_SHORT).show()
                                    },
                                    border = BorderStroke(1.dp, ContenedorAlto),
                                    shape = RoundedCornerShape(8.dp)
                                ) {
                                    Text(if (capturaAdjuntada) "Listo" else "Adjuntar", fontSize = 11.sp, fontWeight = FontWeight.Bold, color = AzulSecundario)
                                }
                            }
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            // 5. OPCIÓN B: TARJETA DE CRÉDITO O DÉBITO
            Surface(
                color = BlancoTarjeta,
                shape = RoundedCornerShape(14.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .border(
                        width = if (!esBilleteraDigital) 2.dp else 1.dp,
                        color = if (!esBilleteraDigital) NaranjaPrincipal else ContenedorAlto,
                        shape = RoundedCornerShape(14.dp)
                    )
                    .clickable { esBilleteraDigital = false }
            ) {
                Column(modifier = Modifier.padding(14.dp)) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        RadioButton(
                            selected = !esBilleteraDigital,
                            onClick = { esBilleteraDigital = false },
                            colors = RadioButtonDefaults.colors(selectedColor = NaranjaPrincipal)
                        )
                        Column {
                            Text("Tarjeta de Crédito o Débito", fontSize = 14.sp, fontWeight = FontWeight.Bold, color = TextoOscuro)
                            Text("Visa, Mastercard, Amex, Diners", fontSize = 11.sp, color = TextoSecundario)
                        }
                    }

                    // Logos sencillos de tarjetas
                    Row(modifier = Modifier.padding(start = 48.dp, top = 4.dp), horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                        BadgeTarjeta("VISA", AzulSecundario)
                        BadgeTarjeta("MC", Color(0xFFD32F2F))
                        BadgeTarjeta("AMEX", TextoOscuro)
                        BadgeTarjeta("DINERS", TextoSecundario)
                    }

                    // Formulario simple de tarjeta cuando está seleccionado
                    if (!esBilleteraDigital) {
                        Spacer(modifier = Modifier.height(12.dp))
                        OutlinedTextField(
                            value = numeroTarjeta,
                            onValueChange = { if (it.length <= 16 && it.all { c -> c.isDigit() }) numeroTarjeta = it },
                            label = { Text("Número de Tarjeta (16 dígitos)") },
                            leadingIcon = { Icon(Icons.Default.CreditCard, contentDescription = null, tint = AzulSecundario) },
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                            singleLine = true,
                            shape = RoundedCornerShape(10.dp),
                            modifier = Modifier.fillMaxWidth()
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                            OutlinedTextField(
                                value = vencimientoTarjeta,
                                onValueChange = { if (it.length <= 5) vencimientoTarjeta = it },
                                label = { Text("Vencimiento (MM/AA)") },
                                singleLine = true,
                                shape = RoundedCornerShape(10.dp),
                                modifier = Modifier.weight(1f)
                            )
                            OutlinedTextField(
                                value = cvvTarjeta,
                                onValueChange = { if (it.length <= 3 && it.all { c -> c.isDigit() }) cvvTarjeta = it },
                                label = { Text("CVV") },
                                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                                singleLine = true,
                                shape = RoundedCornerShape(10.dp),
                                modifier = Modifier.weight(1f)
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(14.dp))

            // 6. COMPROBANTE DE PAGO ELECTRÓNICO SUNAT
            Surface(color = BlancoTarjeta, shape = RoundedCornerShape(14.dp), shadowElevation = 1.dp, modifier = Modifier.fillMaxWidth()) {
                Column(modifier = Modifier.padding(14.dp)) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(Icons.Default.ReceiptLong, contentDescription = null, tint = NaranjaOscuro, modifier = Modifier.size(18.dp))
                        Spacer(modifier = Modifier.width(6.dp))
                        Text("Comprobante de Pago Electrónico", fontSize = 14.sp, fontWeight = FontWeight.Bold, color = TextoOscuro)
                        Spacer(modifier = Modifier.width(6.dp))
                        Text("SUNAT", fontSize = 10.sp, fontWeight = FontWeight.Bold, color = TextoSecundario)
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    // Selector Boleta de Venta vs Factura Electrónica
                    Surface(color = ContenedorBajo, shape = RoundedCornerShape(10.dp), modifier = Modifier.fillMaxWidth()) {
                        Row(modifier = Modifier.padding(4.dp)) {
                            TabComprobante("Boleta de Venta", esBoleta, Modifier.weight(1f)) {
                                esBoleta = true
                                documentoCliente = ""
                            }
                            TabComprobante("Factura Electrónica", !esBoleta, Modifier.weight(1f)) {
                                esBoleta = false
                                documentoCliente = ""
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    Text(
                        text = if (esBoleta) "Documento de Identidad (DNI)" else "Número de RUC (11 dígitos)",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold,
                        color = TextoOscuro
                    )
                    Spacer(modifier = Modifier.height(4.dp))

                    Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        val maxLen = if (esBoleta) 8 else 11
                        OutlinedTextField(
                            value = documentoCliente,
                            onValueChange = { if (it.length <= maxLen && it.all { c -> c.isDigit() }) documentoCliente = it },
                            placeholder = { Text(if (esBoleta) "Ej. 72849102" else "Ej. 20601234567") },
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                            singleLine = true,
                            shape = RoundedCornerShape(10.dp),
                            modifier = Modifier.weight(1f)
                        )
                        OutlinedButton(
                            onClick = {
                                if (documentoCliente.isNotEmpty()) {
                                    nombreRazonSocial = if (esBoleta) "Carlos Mendoza Prado" else "CONSTRUCTORA LIMA NORTE S.A.C."
                                    Toast.makeText(contexto, "Datos validados", Toast.LENGTH_SHORT).show()
                                }
                            },
                            border = BorderStroke(1.dp, ContenedorAlto),
                            shape = RoundedCornerShape(10.dp),
                            modifier = Modifier.height(54.dp)
                        ) {
                            Text("Validar", fontWeight = FontWeight.Bold, color = NaranjaOscuro)
                        }
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    Text(
                        text = if (esBoleta) "Nombre Completo" else "Razón Social",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold,
                        color = TextoOscuro
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    OutlinedTextField(
                        value = nombreRazonSocial,
                        onValueChange = { nombreRazonSocial = it },
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
private fun BadgeTarjeta(texto: String, colorTexto: Color) {
    Surface(color = ContenedorBajo, shape = RoundedCornerShape(4.dp)) {
        Text(
            text = texto,
            fontSize = 9.sp,
            fontWeight = FontWeight.ExtraBold,
            color = colorTexto,
            modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
        )
    }
}

@Composable
private fun TabComprobante(titulo: String, seleccionado: Boolean, modifier: Modifier, alClick: () -> Unit) {
    Surface(
        color = if (seleccionado) BlancoTarjeta else Color.Transparent,
        shape = RoundedCornerShape(8.dp),
        modifier = modifier.clip(RoundedCornerShape(8.dp)).clickable { alClick() }
    ) {
        Box(modifier = Modifier.padding(vertical = 10.dp), contentAlignment = Alignment.Center) {
            Text(
                text = titulo,
                fontSize = 12.sp,
                fontWeight = if (seleccionado) FontWeight.Bold else FontWeight.Normal,
                color = if (seleccionado) TextoOscuro else TextoSecundario
            )
        }
    }
}