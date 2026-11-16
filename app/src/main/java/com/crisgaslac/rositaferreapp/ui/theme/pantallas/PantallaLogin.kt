package com.crisgaslac.rositaferreapp.ui.theme.pantallas

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.crisgaslac.rositaferreapp.R
import com.crisgaslac.rositaferreapp.TipoCliente
import com.crisgaslac.rositaferreapp.ui.theme.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PantallaLogin(
    alVolver: () -> Unit,
    alIniciarSesionExitoso: (String) -> Unit,
    alIrARegistro: () -> Unit = {}
) {
    var tipoSeleccionado by remember { mutableStateOf(TipoCliente.PERSONA) }
    var documento by remember { mutableStateOf("") }
    var clave by remember { mutableStateOf("") }
    var claveVisible by remember { mutableStateOf(false) }
    var recordarDatos by remember { mutableStateOf(true) }

    val esPersona = tipoSeleccionado == TipoCliente.PERSONA
    val maxDigitos = if (esPersona) 8 else 11

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
                            text = "Ferretería Rosita",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold,
                            color = TextoOscuro
                        )
                    }
                },
                navigationIcon = {
                    IconButton(onClick = alVolver) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Volver",
                            tint = TextoOscuro
                        )
                    }
                },
                actions = {
                    Box(
                        modifier = Modifier
                            .padding(end = 12.dp)
                            .size(34.dp)
                            .background(NaranjaOscuro, RoundedCornerShape(8.dp)),
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
        containerColor = FondoPantalla
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 20.dp, vertical = 16.dp)
        ) {
            // 1. Tarjeta Superior: Acceso Oficial + Facturación SUNAT
            Surface(
                color = ContenedorBajo,
                shape = RoundedCornerShape(14.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Box(
                            modifier = Modifier
                                .size(48.dp)
                                .background(BlancoTarjeta, RoundedCornerShape(10.dp))
                                .padding(6.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Image(
                                painter = painterResource(id = R.drawable.ferreteriarosita),
                                contentDescription = null,
                                contentScale = ContentScale.Fit,
                                modifier = Modifier.fillMaxSize()
                            )
                        }
                        Spacer(modifier = Modifier.width(12.dp))
                        Column {
                            Surface(
                                color = DuraznoClaro,
                                shape = CircleShape
                            ) {
                                Text(
                                    text = "ACCESO OFICIAL",
                                    color = NaranjaOscuro,
                                    fontSize = 10.sp,
                                    fontWeight = FontWeight.Bold,
                                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.dp)
                                )
                            }
                            Spacer(modifier = Modifier.height(2.dp))
                            Text(
                                text = "¡Bienvenido de nuevo!",
                                fontSize = 20.sp,
                                fontWeight = FontWeight.ExtraBold,
                                color = TextoOscuro
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    Text(
                        text = "Ingresa a tu cuenta ferretera para cotizar, comprar y gestionar tus pedidos en obra con entrega inmediata.",
                        fontSize = 13.sp,
                        color = TextoSecundario,
                        lineHeight = 18.sp
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    // Banner SUNAT
                    Surface(
                        color = BlancoTarjeta,
                        shape = RoundedCornerShape(10.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Row(
                            modifier = Modifier.padding(10.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.CheckCircle,
                                contentDescription = null,
                                tint = NaranjaOscuro,
                                modifier = Modifier.size(20.dp)
                            )
                            Text(
                                text = "Facturación automática SUNAT y tarifa mayorista garantizada en obra.",
                                fontSize = 11.sp,
                                fontWeight = FontWeight.SemiBold,
                                color = TextoOscuro,
                                lineHeight = 15.sp
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(18.dp))

            // 2. Selector Tipo de Cliente (Persona / Empresa)
            Surface(
                color = ContenedorBajo,
                shape = RoundedCornerShape(10.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(modifier = Modifier.padding(4.dp)) {
                    TabTipoCliente(
                        titulo = "Persona / Maestro",
                        icono = Icons.Default.Person,
                        seleccionado = esPersona,
                        modifier = Modifier.weight(1f),
                        alClick = {
                            tipoSeleccionado = TipoCliente.PERSONA
                            documento = ""
                        }
                    )
                    TabTipoCliente(
                        titulo = "Empresa (RUC)",
                        icono = Icons.Default.Home,
                        seleccionado = !esPersona,
                        modifier = Modifier.weight(1f),
                        alClick = {
                            tipoSeleccionado = TipoCliente.EMPRESA
                            documento = ""
                        }
                    )
                }
            }

            Spacer(modifier = Modifier.height(18.dp))

            // 3. Campo DNI o RUC
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = if (esPersona) "Número de DNI (8 dígitos)" else "Número de RUC (11 dígitos)",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    color = TextoOscuro
                )
                Text(
                    text = "$maxDigitos dígitos",
                    fontSize = 11.sp,
                    color = Color.Gray
                )
            }
            Spacer(modifier = Modifier.height(6.dp))
            OutlinedTextField(
                value = documento,
                onValueChange = { if (it.length <= maxDigitos && it.all { c -> c.isDigit() }) documento = it },
                placeholder = { Text(if (esPersona) "Ej. 45892310" else "Ej. 20601234567", color = Color.Gray) },
                leadingIcon = { Icon(Icons.Default.AccountBox, contentDescription = null, tint = NaranjaOscuro) },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                singleLine = true,
                shape = RoundedCornerShape(10.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedContainerColor = BlancoTarjeta,
                    unfocusedContainerColor = BlancoTarjeta,
                    focusedBorderColor = NaranjaPrincipal,
                    unfocusedBorderColor = ContenedorAlto
                ),
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(14.dp))

            // 4. Campo Clave de acceso
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Clave de acceso",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    color = TextoOscuro
                )
                Text(
                    text = "¿Olvidaste tu contraseña?",
                    fontSize = 11.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = AzulSecundario,
                    modifier = Modifier.clickable { }
                )
            }
            Spacer(modifier = Modifier.height(6.dp))
            OutlinedTextField(
                value = clave,
                onValueChange = { clave = it },
                placeholder = { Text("••••••••", color = Color.Gray) },
                leadingIcon = { Icon(Icons.Default.Lock, contentDescription = null, tint = TextoSecundario) },
                trailingIcon = {
                    TextButton(onClick = { claveVisible = !claveVisible }) {
                        Text(
                            text = if (claveVisible) "Ocultar" else "Ver",
                            fontSize = 12.sp,
                            color = AzulSecundario,
                            fontWeight = FontWeight.Bold
                        )
                    }
                },
                visualTransformation = if (claveVisible) VisualTransformation.None else PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                singleLine = true,
                shape = RoundedCornerShape(10.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedContainerColor = BlancoTarjeta,
                    unfocusedContainerColor = BlancoTarjeta,
                    focusedBorderColor = NaranjaPrincipal,
                    unfocusedBorderColor = ContenedorAlto
                ),
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(10.dp))

            // 5. Checkbox Recordar datos
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.clickable { recordarDatos = !recordarDatos }
            ) {
                Checkbox(
                    checked = recordarDatos,
                    onCheckedChange = { recordarDatos = it },
                    colors = CheckboxDefaults.colors(checkedColor = NaranjaOscuro)
                )
                Text(
                    text = "Recordar mis datos en este dispositivo",
                    fontSize = 13.sp,
                    color = TextoOscuro
                )
            }

            Spacer(modifier = Modifier.height(14.dp))

            // 6. Botón Principal Iniciar Sesión
            Button(
                onClick = { alIniciarSesionExitoso(documento) },
                colors = ButtonDefaults.buttonColors(containerColor = NaranjaPrincipal),
                shape = RoundedCornerShape(10.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp)
            ) {
                Text("Iniciar Sesión", fontSize = 16.sp, fontWeight = FontWeight.Bold)
                Spacer(modifier = Modifier.width(8.dp))
                Icon(Icons.AutoMirrored.Filled.ArrowForward, contentDescription = null, modifier = Modifier.size(20.dp))
            }

            Spacer(modifier = Modifier.height(16.dp))

            // 7. Accesos Rápidos: Biometría y Clave Rápida
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                TarjetaAccesoRapido(
                    icono = Icons.Default.Lock,
                    fondoIcono = ContenedorAlto,
                    colorIcono = AzulSecundario,
                    titulo = "Biometría",
                    subtitulo = "Huella o Face ID",
                    modifier = Modifier.weight(1f),
                    alClick = { }
                )
                TarjetaAccesoRapido(
                    icono = Icons.Default.Email,
                    fondoIcono = DuraznoClaro,
                    colorIcono = NaranjaOscuro,
                    titulo = "Clave Rápida",
                    subtitulo = "Vía WhatsApp / SMS",
                    modifier = Modifier.weight(1f),
                    alClick = { }
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            // 8. Tarjeta de Registro
            Surface(
                color = ContenedorBajo,
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { alIrARegistro() }
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "¿Aún no tienes cuenta en Ferretería Rosita?",
                        fontSize = 12.sp,
                        color = TextoSecundario
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(
                            text = "Regístrate aquí para precios de obra",
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Bold,
                            color = NaranjaOscuro
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                            contentDescription = null,
                            tint = NaranjaOscuro,
                            modifier = Modifier.size(16.dp)
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(14.dp))

            // 9. Pie de soporte WhatsApp
            Surface(
                color = BlancoTarjeta,
                shape = RoundedCornerShape(12.dp),
                shadowElevation = 1.dp,
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    modifier = Modifier.padding(12.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.weight(1f)
                    ) {
                        Box(
                            modifier = Modifier
                                .size(36.dp)
                                .background(ContenedorAlto, CircleShape),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(Icons.Default.Phone, contentDescription = null, tint = AzulSecundario)
                        }
                        Spacer(modifier = Modifier.width(10.dp))
                        Column {
                            Text(
                                text = "¿Dudas con tu cuenta o pedido?",
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Bold,
                                color = TextoOscuro,
                                maxLines = 1
                            )
                            Text(
                                text = "Soporte ferretero en tiempo real",
                                fontSize = 11.sp,
                                color = TextoSecundario,
                                maxLines = 1
                            )
                        }
                    }
                    OutlinedButton(
                        onClick = { },
                        shape = RoundedCornerShape(8.dp),
                        contentPadding = PaddingValues(horizontal = 10.dp, vertical = 4.dp)
                    ) {
                        Text("WhatsApp", fontSize = 12.sp, color = AzulSecundario, fontWeight = FontWeight.Bold)
                    }
                }
            }
        }
    }
}

@Composable
private fun TabTipoCliente(
    titulo: String,
    icono: ImageVector,
    seleccionado: Boolean,
    modifier: Modifier = Modifier,
    alClick: () -> Unit
) {
    Surface(
        color = if (seleccionado) BlancoTarjeta else Color.Transparent,
        shape = RoundedCornerShape(8.dp),
        shadowElevation = if (seleccionado) 2.dp else 0.dp,
        modifier = modifier
            .clip(RoundedCornerShape(8.dp))
            .clickable { alClick() }
    ) {
        Row(
            modifier = Modifier.padding(vertical = 10.dp, horizontal = 8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Icon(
                imageVector = icono,
                contentDescription = null,
                tint = if (seleccionado) NaranjaOscuro else TextoSecundario,
                modifier = Modifier.size(16.dp)
            )
            Spacer(modifier = Modifier.width(6.dp))
            Text(
                text = titulo,
                fontSize = 12.sp,
                fontWeight = if (seleccionado) FontWeight.Bold else FontWeight.Medium,
                color = if (seleccionado) NaranjaOscuro else TextoSecundario
            )
        }
    }
}

@Composable
private fun TarjetaAccesoRapido(
    icono: ImageVector,
    fondoIcono: Color,
    colorIcono: Color,
    titulo: String,
    subtitulo: String,
    modifier: Modifier = Modifier,
    alClick: () -> Unit
) {
    Surface(
        color = BlancoTarjeta,
        shape = RoundedCornerShape(12.dp),
        shadowElevation = 1.dp,
        modifier = modifier
            .clip(RoundedCornerShape(12.dp))
            .clickable { alClick() }
    ) {
        Column(
            modifier = Modifier.padding(vertical = 14.dp, horizontal = 8.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .background(fondoIcono, RoundedCornerShape(10.dp)),
                contentAlignment = Alignment.Center
            ) {
                Icon(icono, contentDescription = null, tint = colorIcono, modifier = Modifier.size(22.dp))
            }
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = titulo, fontSize = 13.sp, fontWeight = FontWeight.Bold, color = TextoOscuro)
            Text(text = subtitulo, fontSize = 11.sp, color = TextoSecundario)
        }
    }
}