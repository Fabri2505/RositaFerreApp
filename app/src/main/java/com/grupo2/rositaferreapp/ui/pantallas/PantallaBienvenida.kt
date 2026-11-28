package com.grupo2.rositaferreapp.ui.pantallas

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.grupo2.rositaferreapp.R
import com.grupo2.rositaferreapp.navegacion.ControladorNavegacion
import com.grupo2.rositaferreapp.ui.theme.*

@Composable
fun PantallaBienvenida(navController: ControladorNavegacion) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(FondoPantalla)
            .windowInsetsPadding(WindowInsets.safeDrawing),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        // CONTENIDO PRINCIPAL (ARRIBA Y CENTRO)
        Column(
            modifier = Modifier
                .weight(1f)
                .padding(horizontal = 20.dp, vertical = 10.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceEvenly
        ) {
            // 1. CABECERA CENTRADA (Sin el cuadro lateral derecho)
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Badge superior "DISTRIBUIDOR AUTORIZADO PERÚ" + bandera
                Surface(color = ContenedorAlto, shape = CircleShape) {
                    Row(
                        modifier = Modifier.padding(horizontal = 12.dp, vertical = 4.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Default.CheckCircle,
                            contentDescription = null,
                            tint = AzulSecundario,
                            modifier = Modifier.size(13.dp)
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            text = "DISTRIBUIDOR AUTORIZADO PERÚ",
                            color = AzulSecundario,
                            fontSize = 10.sp,
                            fontWeight = FontWeight.Bold
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Row(modifier = Modifier.clip(RoundedCornerShape(2.dp))) {
                            Box(Modifier.size(width = 3.dp, height = 9.dp).background(Color(0xFFD32F2F)))
                            Box(Modifier.size(width = 3.dp, height = 9.dp).background(Color.White))
                            Box(Modifier.size(width = 3.dp, height = 9.dp).background(Color(0xFFD32F2F)))
                        }
                    }
                }

                Spacer(modifier = Modifier.height(12.dp))

                // Logo + Nombre centrados
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.ferreteriarosita),
                        contentDescription = "Logo Rosita",
                        contentScale = ContentScale.Fit,
                        modifier = Modifier.size(64.dp)
                    )
                    Spacer(modifier = Modifier.width(12.dp))
                    Column(horizontalAlignment = Alignment.Start) {
                        Text(
                            text = "Rosita",
                            fontSize = 32.sp,
                            fontWeight = FontWeight.ExtraBold,
                            color = TextoOscuro,
                            lineHeight = 32.sp
                        )
                        Text(
                            text = "CORPORACIÓN FERRETERÍA S.A.C.",
                            fontSize = 10.sp,
                            fontWeight = FontWeight.Bold,
                            color = AzulSecundario,
                            letterSpacing = 0.6.sp
                        )
                    }
                }

                Spacer(modifier = Modifier.height(10.dp))

                Box(
                    modifier = Modifier
                        .width(48.dp)
                        .height(3.5.dp)
                        .background(NaranjaPrincipal, CircleShape)
                )

                Spacer(modifier = Modifier.height(10.dp))

                // Título principal en dos colores centrado
                Text(
                    text = buildAnnotatedString {
                        withStyle(SpanStyle(color = TextoOscuro, fontWeight = FontWeight.ExtraBold)) {
                            append("Tu aliado experto en ")
                        }
                        withStyle(SpanStyle(color = NaranjaPrincipal, fontWeight = FontWeight.ExtraBold)) {
                            append("construcción y ferretería")
                        }
                    },
                    fontSize = 18.sp,
                    lineHeight = 23.sp,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(5.dp))

                Text(
                    text = "Herramientas de alta calidad y entrega rápida en Lima y provincias.",
                    fontSize = 12.sp,
                    color = TextoSecundario,
                    lineHeight = 16.sp,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth()
                )
            }

            // 2. TARJETA DE MÉTRICAS CON ÍCONO A LA IZQUIERDA (+15k | 24h | 100%)
            Surface(
                color = BlancoTarjeta,
                shape = RoundedCornerShape(14.dp),
                shadowElevation = 2.dp,
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 10.dp, horizontal = 8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    MetricaConIcono(Icons.Default.Inventory2, "+15k", "Productos", Modifier.weight(1f))
                    VerticalDivider(modifier = Modifier.height(28.dp), color = ContenedorAlto)
                    MetricaConIcono(Icons.Default.Schedule, "24h", "Despacho", Modifier.weight(1f))
                    VerticalDivider(modifier = Modifier.height(28.dp), color = ContenedorAlto)
                    MetricaConIcono(Icons.Default.CheckCircle, "100%", "Formales", Modifier.weight(1f))
                }
            }

            // 3. DOS BOTONES PRINCIPALES
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                Button(
                    onClick = { navController.irACatalogo("Invitado") },
                    colors = ButtonDefaults.buttonColors(containerColor = NaranjaPrincipal),
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(48.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Spacer(modifier = Modifier.width(20.dp))
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(Icons.Default.ShoppingCart, contentDescription = null, modifier = Modifier.size(20.dp))
                            Spacer(modifier = Modifier.width(8.dp))
                            Text("Explorar Catálogo", fontSize = 15.sp, fontWeight = FontWeight.Bold)
                        }
                        Icon(Icons.AutoMirrored.Filled.KeyboardArrowRight, contentDescription = null)
                    }
                }

                OutlinedButton(
                    onClick = { navController.irALogin() },
                    colors = ButtonDefaults.outlinedButtonColors(containerColor = ContenedorAlto),
                    border = BorderStroke(1.dp, AzulSecundario.copy(alpha = 0.35f)),
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(46.dp)
                ) {
                    Icon(Icons.Default.Person, contentDescription = null, tint = AzulSecundario, modifier = Modifier.size(20.dp))
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "Ya tengo cuenta / Iniciar Sesión",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold,
                        color = AzulSecundario
                    )
                }
            }

            // 4. SECCIÓN GARANTÍA & SEGURIDAD CON FLECHAS >
            Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(Icons.Default.VerifiedUser, contentDescription = null, tint = AzulSecundario, modifier = Modifier.size(16.dp))
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = "GARANTÍA & SEGURIDAD",
                        fontSize = 11.sp,
                        fontWeight = FontWeight.ExtraBold,
                        color = AzulSecundario
                    )
                }

                ItemGarantiaCard(
                    icono = Icons.Default.Verified,
                    fondoIcono = ContenedorAlto,
                    colorIcono = AzulSecundario,
                    titulo = "100% Garantía de Fábrica",
                    subtitulo = "Primeras marcas certificadas para obra."
                )
                ItemGarantiaCard(
                    icono = Icons.Default.LocalShipping,
                    fondoIcono = DuraznoClaro,
                    colorIcono = NaranjaPrincipal,
                    titulo = "Envíos Rápidos en Lima",
                    subtitulo = "Rutas diarias y despacho consolidado a provincias."
                )
                ItemGarantiaCard(
                    icono = Icons.Default.CreditCard,
                    fondoIcono = ContenedorAlto,
                    colorIcono = AzulSecundario,
                    titulo = "Pagos Seguros y Flexibles",
                    subtitulo = "Yape, Plin, tarjetas bancarias y transferencias BCP."
                )
            }
        }

        // 5. PIE OSCURO INFERIOR (RUC + ACCESO ADMINISTRATIVO)
        Surface(
            color = Color(0xFF102A43),
            shape = RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 10.dp, horizontal = 16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Default.LocationOn, contentDescription = null, tint = Color.White, modifier = Modifier.size(13.dp))
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = "RUC: 20601234567 - Lima, Perú",
                        fontSize = 11.sp,
                        color = Color.White,
                        fontWeight = FontWeight.Medium
                    )
                }
                Spacer(modifier = Modifier.height(4.dp))
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.clickable { navController.irALoginAdmin() }
                ) {
                    Icon(Icons.Default.Lock, contentDescription = null, tint = Color(0xFF90CAF9), modifier = Modifier.size(12.dp))
                    Spacer(modifier = Modifier.width(5.dp))
                    Text(
                        text = "Acceso Administrativo",
                        fontSize = 11.sp,
                        color = Color(0xFF90CAF9),
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
    }
}

@Composable
private fun MetricaConIcono(icono: ImageVector, valor: String, etiqueta: String, modifier: Modifier = Modifier) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        Icon(icono, contentDescription = null, tint = AzulSecundario, modifier = Modifier.size(22.dp))
        Spacer(modifier = Modifier.width(6.dp))
        Column {
            Text(valor, fontSize = 15.sp, fontWeight = FontWeight.ExtraBold, color = TextoOscuro)
            Text(etiqueta, fontSize = 10.sp, color = TextoSecundario)
        }
    }
}

@Composable
private fun ItemGarantiaCard(
    icono: ImageVector,
    fondoIcono: Color,
    colorIcono: Color,
    titulo: String,
    subtitulo: String
) {
    Surface(
        color = BlancoTarjeta,
        shape = RoundedCornerShape(10.dp),
        shadowElevation = 1.dp,
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 12.dp, vertical = 7.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(32.dp)
                    .background(fondoIcono, CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Icon(icono, contentDescription = null, tint = colorIcono, modifier = Modifier.size(17.dp))
            }
            Spacer(modifier = Modifier.width(10.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(titulo, fontSize = 12.sp, fontWeight = FontWeight.Bold, color = TextoOscuro, maxLines = 1)
                Text(subtitulo, fontSize = 10.sp, color = TextoSecundario, maxLines = 1, overflow = TextOverflow.Ellipsis)
            }
            Icon(
                imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                contentDescription = null,
                tint = TextoSecundario,
                modifier = Modifier.size(18.dp)
            )
        }
    }
}