package com.crisgaslac.rositaferreapp.ui.theme.pantallas

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.crisgaslac.rositaferreapp.R
import com.crisgaslac.rositaferreapp.ui.theme.*

@Composable
fun PantallaBienvenida(
    alPresionarExplorarCatalogo: () -> Unit,
    alPresionarIniciarSesion: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(FondoPantalla)
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 20.dp, vertical = 24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // 1. Etiqueta Superior
        Surface(
            color = ContenedorAlto,
            shape = CircleShape,
            shadowElevation = 1.dp
        ) {
            Row(
                modifier = Modifier.padding(horizontal = 14.dp, vertical = 6.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.CheckCircle,
                    contentDescription = null,
                    tint = AzulSecundario,
                    modifier = Modifier.size(16.dp)
                )
                Text(
                    text = "DISTRIBUIDOR AUTORIZADO PERÚ",
                    color = AzulSecundario,
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Bold,
                    letterSpacing = 0.5.sp
                )
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        // 2. Contenedor del Logo Real
        Box(
            modifier = Modifier
                .size(128.dp)
                .shadow(6.dp, RoundedCornerShape(16.dp))
                .background(BlancoTarjeta, RoundedCornerShape(16.dp))
                .padding(12.dp),
            contentAlignment = Alignment.Center
        ) {
            Image(
                painter = painterResource(id = R.drawable.ferreteriarosita),
                contentDescription = "Logo Ferretería Rosita",
                contentScale = ContentScale.Fit,
                modifier = Modifier.fillMaxSize()
            )
        }

        Spacer(modifier = Modifier.height(20.dp))

        // 3. Títulos y Descripción
        Text(
            text = "Rosita",
            fontSize = 32.sp,
            fontWeight = FontWeight.ExtraBold,
            color = TextoOscuro
        )
        Text(
            text = "CORPORACIÓN FERRETERÍA S.A.C.",
            fontSize = 13.sp,
            fontWeight = FontWeight.Bold,
            color = AzulSecundario,
            letterSpacing = 1.2.sp
        )

        Spacer(modifier = Modifier.height(10.dp))
        Box(
            modifier = Modifier
                .width(48.dp)
                .height(4.dp)
                .background(NaranjaPrincipal, CircleShape)
        )
        Spacer(modifier = Modifier.height(14.dp))

        Text(
            text = "Tu aliado experto en construcción y ferretería",
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            color = NaranjaOscuro,
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(6.dp))
        Text(
            text = "Herramientas de alta calidad, materiales de construcción y entrega rápida en Lima y provincias.",
            fontSize = 14.sp,
            color = TextoSecundario,
            textAlign = TextAlign.Center,
            lineHeight = 20.sp
        )

        Spacer(modifier = Modifier.height(24.dp))

        // 4. Barra de Indicadores (+15k | 24h | 100%)
        Surface(
            color = ContenedorBajo,
            shape = RoundedCornerShape(12.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 14.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                IndicadorItem(valor = "+15k", etiqueta = "Productos", modifier = Modifier.weight(1f))
                VerticalDivider(modifier = Modifier.height(32.dp), color = ContenedorAlto)
                IndicadorItem(valor = "24h", etiqueta = "Despacho", modifier = Modifier.weight(1f))
                VerticalDivider(modifier = Modifier.height(32.dp), color = ContenedorAlto)
                IndicadorItem(valor = "100%", etiqueta = "Formales", modifier = Modifier.weight(1f))
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        // 5. Botón Explorar Catálogo
        Button(
            onClick = alPresionarExplorarCatalogo,
            colors = ButtonDefaults.buttonColors(containerColor = NaranjaPrincipal),
            shape = RoundedCornerShape(10.dp),
            modifier = Modifier
                .fillMaxWidth()
                .height(52.dp)
        ) {
            Icon(Icons.Default.ShoppingCart, contentDescription = null)
            Spacer(modifier = Modifier.width(8.dp))
            Text("Explorar Catálogo", fontSize = 16.sp, fontWeight = FontWeight.SemiBold)
            Spacer(modifier = Modifier.weight(1f))
            Icon(Icons.AutoMirrored.Filled.ArrowForward, contentDescription = null, modifier = Modifier.size(18.dp))
        }

        Spacer(modifier = Modifier.height(10.dp))

        // 6. Botón Iniciar Sesión
        Button(
            onClick = alPresionarIniciarSesion,
            colors = ButtonDefaults.buttonColors(
                containerColor = ContenedorAlto,
                contentColor = AzulSecundario
            ),
            shape = RoundedCornerShape(10.dp),
            modifier = Modifier
                .fillMaxWidth()
                .height(52.dp)
        ) {
            Icon(Icons.Default.Person, contentDescription = null)
            Spacer(modifier = Modifier.width(8.dp))
            Text("Ya tengo cuenta / Iniciar Sesión", fontSize = 15.sp, fontWeight = FontWeight.SemiBold)
        }

        Spacer(modifier = Modifier.height(28.dp))

        // 7. Sección Garantía & Seguridad
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            HorizontalDivider(modifier = Modifier.weight(1f), color = ContenedorAlto)
            Text(
                text = "  GARANTÍA & SEGURIDAD  ",
                fontSize = 11.sp,
                fontWeight = FontWeight.Bold,
                color = TextoSecundario
            )
            HorizontalDivider(modifier = Modifier.weight(1f), color = ContenedorAlto)
        }

        Spacer(modifier = Modifier.height(12.dp))

        TarjetaGarantia(
            icono = Icons.Default.Lock,
            fondoIcono = ContenedorAlto,
            colorIcono = AzulSecundario,
            titulo = "100% Garantía de Fábrica",
            subtitulo = "Primeras marcas certificadas para obra"
        )
        Spacer(modifier = Modifier.height(8.dp))
        TarjetaGarantia(
            icono = Icons.Default.Place,
            fondoIcono = DuraznoClaro,
            colorIcono = NaranjaOscuro,
            titulo = "Envíos Rápidos en Lima",
            subtitulo = "Rutas diarias y despacho consolidado a provincias"
        )
        Spacer(modifier = Modifier.height(8.dp))
        TarjetaGarantia(
            icono = Icons.Default.Star,
            fondoIcono = ContenedorAlto,
            colorIcono = AzulSecundario,
            titulo = "Pagos Seguros y Flexibles",
            subtitulo = "Yape, Plin, tarjetas bancarias y transferencias BCP"
        )

        Spacer(modifier = Modifier.height(24.dp))

        // 8. Pie de página
        Text(
            text = "RUC: 20601234567 • Lima, Perú",
            fontSize = 12.sp,
            color = Color.Gray
        )
    }
}

@Composable
private fun IndicadorItem(valor: String, etiqueta: String, modifier: Modifier = Modifier) {
    Column(modifier = modifier, horizontalAlignment = Alignment.CenterHorizontally) {
        Text(text = valor, fontSize = 18.sp, fontWeight = FontWeight.Bold, color = AzulSecundario)
        Text(text = etiqueta, fontSize = 11.sp, fontWeight = FontWeight.SemiBold, color = TextoSecundario)
    }
}

@Composable
private fun TarjetaGarantia(
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
            modifier = Modifier.padding(12.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Box(
                modifier = Modifier
                    .size(38.dp)
                    .background(fondoIcono, RoundedCornerShape(8.dp)),
                contentAlignment = Alignment.Center
            ) {
                Icon(icono, contentDescription = null, tint = colorIcono, modifier = Modifier.size(20.dp))
            }
            Column {
                Text(text = titulo, fontSize = 13.sp, fontWeight = FontWeight.Bold, color = TextoOscuro)
                Text(text = subtitulo, fontSize = 12.sp, color = TextoSecundario)
            }
        }
    }
}