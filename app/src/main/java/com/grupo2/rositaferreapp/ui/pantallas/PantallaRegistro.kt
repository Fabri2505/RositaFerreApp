package com.grupo2.rositaferreapp.ui.pantallas

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.grupo2.rositaferreapp.R
import com.grupo2.rositaferreapp.componentes.BotonPrincipal
import com.grupo2.rositaferreapp.componentes.BotonSecundario
import com.grupo2.rositaferreapp.datos.GestorUsuarios
import com.grupo2.rositaferreapp.datos.TipoCliente
import com.grupo2.rositaferreapp.datos.Usuario
import com.grupo2.rositaferreapp.navegacion.ControladorNavegacion
import com.grupo2.rositaferreapp.ui.theme.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PantallaRegistro(navController: ControladorNavegacion) {
    val contexto = LocalContext.current

    var tipoCliente by remember { mutableStateOf(TipoCliente.PERSONA) }
    var documento by remember { mutableStateOf("") }
    var nombreCompleto by remember { mutableStateOf("") }
    var celular by remember { mutableStateOf("") }
    var correo by remember { mutableStateOf("") }
    var clave by remember { mutableStateOf("") }
    var aceptaTerminos by remember { mutableStateOf(false) }

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
                        Text("Crear Cuenta", fontSize = 16.sp, fontWeight = FontWeight.Bold, color = TextoOscuro)
                    }
                },
                navigationIcon = {
                    IconButton(onClick = { navController.irALogin() }) {
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
            Text("AFILIACIÓN FERRETERA", color = AzulSecundario, fontSize = 11.sp, fontWeight = FontWeight.Bold)
            Text("Crea tu Cuenta", fontSize = 22.sp, fontWeight = FontWeight.ExtraBold, color = TextoOscuro)
            Spacer(modifier = Modifier.height(12.dp))

            // Selector Persona / Empresa
            Surface(color = ContenedorBajo, shape = RoundedCornerShape(10.dp), modifier = Modifier.fillMaxWidth()) {
                Row(modifier = Modifier.padding(4.dp)) {
                    TabPerfil("Maestro / Natural", esPersona, Modifier.weight(1f)) {
                        tipoCliente = TipoCliente.PERSONA
                        documento = ""
                    }
                    TabPerfil("Empresa / RUC", !esPersona, Modifier.weight(1f)) {
                        tipoCliente = TipoCliente.EMPRESA
                        documento = ""
                    }
                }
            }

            Spacer(modifier = Modifier.height(14.dp))

            // Documento
            OutlinedTextField(
                value = documento,
                onValueChange = { if (it.length <= maxDigitos && it.all { c -> c.isDigit() }) documento = it },
                label = { Text(if (esPersona) "Número de DNI (8 dígitos)" else "Número de RUC (11 dígitos)") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                singleLine = true,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Botón simular validación RENIEC/SUNAT
            BotonSecundario(
                texto = if (esPersona) "Validar con RENIEC" else "Validar con SUNAT",
                icono = Icons.Default.CheckCircle,
                alHacerClick = {
                    if (documento.isNotEmpty()) {
                        nombreCompleto = if (esPersona) "CARLOS ALBERTO MENDOZA" else "CONSTRUCTORA LIMA NORTE S.A.C."
                    }
                }
            )

            Spacer(modifier = Modifier.height(10.dp))

            OutlinedTextField(
                value = nombreCompleto,
                onValueChange = { nombreCompleto = it },
                label = { Text(if (esPersona) "Nombres y Apellidos Completos" else "Razón Social") },
                singleLine = true,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(10.dp))

            OutlinedTextField(
                value = celular,
                onValueChange = { if (it.length <= 9 && it.all { c -> c.isDigit() }) celular = it },
                label = { Text("Celular (+51)") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
                singleLine = true,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(10.dp))

            OutlinedTextField(
                value = correo,
                onValueChange = { correo = it },
                label = { Text("Correo Electrónico") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                singleLine = true,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(10.dp))

            OutlinedTextField(
                value = clave,
                onValueChange = { clave = it },
                label = { Text("Crea tu Contraseña (mín. 4 caracteres)") },
                visualTransformation = PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                singleLine = true,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(10.dp))

            Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.clickable { aceptaTerminos = !aceptaTerminos }) {
                Checkbox(checked = aceptaTerminos, onCheckedChange = { aceptaTerminos = it })
                Text("Acepto los Términos y Condiciones de Ferretería Rosita.", fontSize = 12.sp)
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Botón Crear Cuenta usando nuestra clase BotonesRosita + object GestorUsuarios
            BotonPrincipal(
                texto = "Crear mi Cuenta",
                icono = Icons.Default.AddCircle,
                habilitado = aceptaTerminos && GestorUsuarios.esDocumentoValido(documento, tipoCliente) && clave.length >= 4,
                alHacerClick = {
                    val nuevoUsuario = Usuario(
                        documento = documento,
                        nombre = nombreCompleto.ifEmpty { "Cliente $documento" },
                        clave = clave,
                        tipo = tipoCliente
                    )
                    GestorUsuarios.registrarUsuario(contexto, nuevoUsuario)
                    Toast.makeText(contexto, "¡Cuenta registrada con éxito!", Toast.LENGTH_SHORT).show()
                    navController.irALogin()
                }
            )

            Spacer(modifier = Modifier.height(14.dp))

            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
                Text("¿Ya tienes cuenta? ", fontSize = 13.sp)
                Text(
                    text = "Inicia Sesión",
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Bold,
                    color = AzulSecundario,
                    modifier = Modifier.clickable { navController.irALogin() }
                )
            }
        }
    }
}

@Composable
private fun TabPerfil(titulo: String, seleccionado: Boolean, modifier: Modifier, alClick: () -> Unit) {
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