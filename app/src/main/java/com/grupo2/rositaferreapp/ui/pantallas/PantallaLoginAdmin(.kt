package com.grupo2.rositaferreapp.ui.pantallas

import android.widget.Toast
import androidx.compose.foundation.Image
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
import com.grupo2.rositaferreapp.navegacion.ControladorNavegacion
import com.grupo2.rositaferreapp.ui.theme.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PantallaLoginAdmin(navController: ControladorNavegacion) {
    val contexto = LocalContext.current

    var usuario by remember { mutableStateOf("") }
    var clave by remember { mutableStateOf("") }
    var claveVisible by remember { mutableStateOf(false) }
    var mensajeError by remember { mutableStateOf<String?>(null) }

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
                        Text("Portal Interno", fontSize = 16.sp, fontWeight = FontWeight.Bold, color = TextoOscuro)
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
                .padding(horizontal = 20.dp, vertical = 24.dp)
        ) {
            // Diseño diferenciado, indicando área restringida
            Surface(color = TextoOscuro, shape = RoundedCornerShape(14.dp), modifier = Modifier.fillMaxWidth()) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Surface(color = Color(0xFFF44336), shape = CircleShape) {
                        Text(
                            text = "ACCESO RESTRINGIDO",
                            color = Color.White,
                            fontSize = 10.sp,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.dp)
                        )
                    }
                    Spacer(modifier = Modifier.height(10.dp))
                    Text("Gestor Ferretería Rosita", fontSize = 20.sp, fontWeight = FontWeight.ExtraBold, color = Color.White)
                    Spacer(modifier = Modifier.height(6.dp))
                    Text(
                        text = "Ingrese sus credenciales administrativas para gestionar inventario, ventas y usuarios.",
                        fontSize = 13.sp,
                        color = Color.LightGray
                    )
                }
            }

            Spacer(modifier = Modifier.height(30.dp))

            // Campo Usuario Admin (Permite texto libre, no lo limita a números)
            Text("Usuario Administrador", fontSize = 12.sp, fontWeight = FontWeight.Bold, color = TextoOscuro)
            Spacer(modifier = Modifier.height(6.dp))
            OutlinedTextField(
                value = usuario,
                onValueChange = {
                    usuario = it
                    mensajeError = null
                },
                placeholder = { Text("Ej. admin", color = Color.Gray) },
                leadingIcon = { Icon(Icons.Default.Settings, contentDescription = null, tint = AzulSecundario) },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text), // Teclado alfanumérico
                singleLine = true,
                shape = RoundedCornerShape(10.dp),
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(18.dp))

            // Campo Contraseña Admin
            Text("Clave de Seguridad", fontSize = 12.sp, fontWeight = FontWeight.Bold, color = TextoOscuro)
            Spacer(modifier = Modifier.height(6.dp))
            OutlinedTextField(
                value = clave,
                onValueChange = {
                    clave = it
                    mensajeError = null
                },
                placeholder = { Text("****", color = Color.Gray) },
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

            Spacer(modifier = Modifier.height(28.dp))

            BotonPrincipal(
                texto = "Ingresar al Sistema",
                icono = Icons.Default.CheckCircle,
                alHacerClick = {
                    if (usuario.isEmpty() || clave.isEmpty()) {
                        mensajeError = "Ingrese ambos campos requeridos."
                    } else {
                        // Reutilizamos el GestorUsuarios que actualizamos
                        val nombre = GestorUsuarios.iniciarSesion(contexto, usuario, clave)

                        if (nombre == "ADMINISTRADOR") {
                            Toast.makeText(contexto, "Acceso Autorizado", Toast.LENGTH_SHORT).show()
                            navController.irAPanelAdmin()
                        } else {
                            mensajeError = "Credenciales administrativas incorrectas."
                        }
                    }
                }
            )
        }
    }
}