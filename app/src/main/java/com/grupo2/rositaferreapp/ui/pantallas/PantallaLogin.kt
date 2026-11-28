package com.grupo2.rositaferreapp.ui.pantallas

import android.widget.Toast
import androidx.compose.foundation.Image
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.grupo2.rositaferreapp.R
import com.grupo2.rositaferreapp.componentes.BotonPrincipal
import com.grupo2.rositaferreapp.datos.GestorUsuarios
import com.grupo2.rositaferreapp.datos.TipoCliente
import com.grupo2.rositaferreapp.navegacion.ControladorNavegacion
import com.grupo2.rositaferreapp.ui.theme.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PantallaLogin(navController: ControladorNavegacion) {
    val contexto = LocalContext.current

    var tipoCliente by remember { mutableStateOf(TipoCliente.PERSONA) }
    var documento by remember { mutableStateOf("") }
    var clave by remember { mutableStateOf("") }
    var claveVisible by remember { mutableStateOf(false) }
    var recordarDatos by remember { mutableStateOf(true) }
    var mensajeError by remember { mutableStateOf<String?>(null) }

    val esPersona = tipoCliente == TipoCliente.PERSONA
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
                        Text("Ferretería Rosita", fontSize = 16.sp, fontWeight = FontWeight.Bold, color = TextoOscuro)
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
        containerColor = FondoPantalla
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 20.dp, vertical = 16.dp)
        ) {
            // Banner superior
            Surface(color = ContenedorBajo, shape = RoundedCornerShape(14.dp), modifier = Modifier.fillMaxWidth()) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Surface(color = DuraznoClaro, shape = CircleShape) {
                        Text(
                            text = "ACCESO OFICIAL",
                            color = NaranjaOscuro,
                            fontSize = 10.sp,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.dp)
                        )
                    }
                    Spacer(modifier = Modifier.height(4.dp))
                    Text("¡Bienvenido de nuevo!", fontSize = 20.sp, fontWeight = FontWeight.ExtraBold, color = TextoOscuro)
                    Spacer(modifier = Modifier.height(6.dp))
                    Text(
                        text = "Ingresa a tu cuenta ferretera para cotizar, comprar y gestionar tus pedidos en obra.",
                        fontSize = 13.sp,
                        color = TextoSecundario
                    )
                }
            }

            Spacer(modifier = Modifier.height(18.dp))

            // Pestañas Persona / Empresa
            Surface(color = ContenedorBajo, shape = RoundedCornerShape(10.dp), modifier = Modifier.fillMaxWidth()) {
                Row(modifier = Modifier.padding(4.dp)) {
                    PestanaCliente("Persona / Maestro", esPersona, Modifier.weight(1f)) {
                        tipoCliente = TipoCliente.PERSONA
                        documento = ""
                        mensajeError = null
                    }
                    PestanaCliente("Empresa (RUC)", !esPersona, Modifier.weight(1f)) {
                        tipoCliente = TipoCliente.EMPRESA
                        documento = ""
                        mensajeError = null
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Campo DNI / RUC
            Text(
                text = if (esPersona) "Número de DNI (8 dígitos)" else "Número de RUC (11 dígitos)",
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold,
                color = TextoOscuro
            )
            Spacer(modifier = Modifier.height(6.dp))
            OutlinedTextField(
                value = documento,
                onValueChange = {
                    // SE AGREGÓ: "|| it == "admin"" para permitir la palabra admin
                    if ((it.length <= maxDigitos && it.all { c -> c.isDigit() }) || it == "admin") {
                        documento = it
                        mensajeError = null
                    }
                },
                placeholder = { Text(if (esPersona) "Ej. 12345678" else "Ej. 20601234567", color = Color.Gray) },
                leadingIcon = { Icon(Icons.Default.AccountBox, contentDescription = null, tint = NaranjaOscuro) },
                // SE CAMBIÓ: De Number a Text para poder escribir la palabra "admin"
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                singleLine = true,
                shape = RoundedCornerShape(10.dp),
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(14.dp))

            // Campo Contraseña
            Text("Clave de acceso", fontSize = 12.sp, fontWeight = FontWeight.Bold, color = TextoOscuro)
            Spacer(modifier = Modifier.height(6.dp))
            OutlinedTextField(
                value = clave,
                onValueChange = {
                    clave = it
                    mensajeError = null
                },
                placeholder = { Text("Ej. 1234", color = Color.Gray) },
                leadingIcon = { Icon(Icons.Default.Lock, contentDescription = null, tint = TextoSecundario) },
                trailingIcon = {
                    TextButton(onClick = { claveVisible = !claveVisible }) {
                        Text(if (claveVisible) "Ocultar" else "Ver", color = AzulSecundario, fontSize = 12.sp)
                    }
                },
                visualTransformation = if (claveVisible) VisualTransformation.None else PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                singleLine = true,
                shape = RoundedCornerShape(10.dp),
                modifier = Modifier.fillMaxWidth()
            )

            if (mensajeError != null) {
                Spacer(modifier = Modifier.height(8.dp))
                Text(mensajeError!!, color = Color.Red, fontSize = 12.sp, fontWeight = FontWeight.Bold)
            }

            Spacer(modifier = Modifier.height(10.dp))

            Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.clickable { recordarDatos = !recordarDatos }) {
                Checkbox(checked = recordarDatos, onCheckedChange = { recordarDatos = it })
                Text("Recordar mis datos en este dispositivo", fontSize = 13.sp, color = TextoOscuro)
            }

            Spacer(modifier = Modifier.height(14.dp))

            // Botón Principal usando nuestra clase BotonesRosita + GestorUsuarios
            BotonPrincipal(
                texto = "Iniciar Sesión",
                icono = Icons.AutoMirrored.Filled.ArrowForward,
                alHacerClick = {
                    if (!GestorUsuarios.esDocumentoValido(documento, tipoCliente)) {
                        mensajeError = if (esPersona) "El DNI debe tener 8 dígitos." else "El RUC debe tener 11 dígitos."
                    } else if (clave.isEmpty()) {
                        mensajeError = "Ingresa tu contraseña."
                    } else {
                        val nombre = GestorUsuarios.iniciarSesion(contexto, documento, clave)

                        // SE AGREGÓ: Validación para el administrador
                        if (nombre == "ADMINISTRADOR") {
                            Toast.makeText(contexto, "Panel de Administración", Toast.LENGTH_SHORT).show()
                            // Aquí podrías en el futuro navegar a una PantallaAdmin
                            navController.irACatalogo(nombre)
                        } else if (nombre != null) {
                            Toast.makeText(contexto, "Bienvenido $nombre", Toast.LENGTH_SHORT).show()
                            navController.irACatalogo(nombre)
                        } else {
                            mensajeError = "Credenciales incorrectas. Prueba DNI: 12345678 / Clave: 1234"
                        }
                    }
                }
            )

            Spacer(modifier = Modifier.height(20.dp))

            // Enlace a PantallaRegistro
            Surface(
                color = ContenedorBajo,
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { navController.irARegistro() }
            ) {
                Column(modifier = Modifier.padding(16.dp), horizontalAlignment = Alignment.CenterHorizontally) {
                    Text("¿Aún no tienes cuenta en Ferretería Rosita?", fontSize = 12.sp, color = TextoSecundario)
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "Regístrate aquí para precios de obra →",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold,
                        color = NaranjaOscuro
                    )
                }
            }
        }
    }
}

@Composable
private fun PestanaCliente(titulo: String, seleccionado: Boolean, modifier: Modifier, alClick: () -> Unit) {
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
                color = if (seleccionado) NaranjaOscuro else TextoSecundario
            )
        }
    }
}