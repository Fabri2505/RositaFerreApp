package com.grupo2.rositaferreapp.ui.pantallas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
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
import com.grupo2.rositaferreapp.componentes.BotonPrincipal
import com.grupo2.rositaferreapp.componentes.BotonSecundario
import com.grupo2.rositaferreapp.R
import com.grupo2.rositaferreapp.navegacion.ControladorNavegacion
import com.grupo2.rositaferreapp.ui.theme.*

@Composable
fun PantallaBienvenida(navController: ControladorNavegacion) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(FondoPantalla)
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 20.dp, vertical = 24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Etiqueta superior
        Surface(color = ContenedorAlto, shape = CircleShape) {
            Row(
                modifier = Modifier.padding(horizontal = 14.dp, vertical = 6.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(Icons.Default.CheckCircle, contentDescription = null, tint = AzulSecundario, modifier = Modifier.size(16.dp))
                Spacer(modifier = Modifier.width(6.dp))
                Text("DISTRIBUIDOR AUTORIZADO PERÚ", color = AzulSecundario, fontSize = 11.sp, fontWeight = FontWeight.Bold)
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Logo oficial
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

        Text("Rosita", fontSize = 32.sp, fontWeight = FontWeight.ExtraBold, color = TextoOscuro)
        Text("CORPORACIÓN FERRETERÍA S.A.C.", fontSize = 13.sp, fontWeight = FontWeight.Bold, color = AzulSecundario, letterSpacing = 1.2.sp)

        Spacer(modifier = Modifier.height(10.dp))
        Box(modifier = Modifier.width(48.dp).height(4.dp).background(NaranjaPrincipal, CircleShape))
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
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(24.dp))

        // Métricas
        Surface(color = ContenedorBajo, shape = RoundedCornerShape(12.dp), modifier = Modifier.fillMaxWidth()) {
            Row(modifier = Modifier.fillMaxWidth().padding(vertical = 14.dp), verticalAlignment = Alignment.CenterVertically) {
                IndicadorItem("+15k", "Productos", Modifier.weight(1f))
                VerticalDivider(modifier = Modifier.height(32.dp), color = ContenedorAlto)
                IndicadorItem("24h", "Despacho", Modifier.weight(1f))
                VerticalDivider(modifier = Modifier.height(32.dp), color = ContenedorAlto)
                IndicadorItem("100%", "Formales", Modifier.weight(1f))
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Botones usando nuestra clase BotonesRosita
        BotonPrincipal(
            texto = "Explorar Catálogo",
            icono = Icons.Default.ShoppingCart,
            alHacerClick = { navController.irACatalogo("Invitado") }
        )

        Spacer(modifier = Modifier.height(10.dp))

        BotonSecundario(
            texto = "Ya tengo cuenta / Iniciar Sesión",
            icono = Icons.Default.Person,
            alHacerClick = { navController.irALogin() }
        )

        Spacer(modifier = Modifier.height(28.dp))

        // Garantía & Seguridad
        Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.fillMaxWidth()) {
            HorizontalDivider(modifier = Modifier.weight(1f), color = ContenedorAlto)
            Text("  GARANTÍA & SEGURIDAD  ", fontSize = 11.sp, fontWeight = FontWeight.Bold, color = TextoSecundario)
            HorizontalDivider(modifier = Modifier.weight(1f), color = ContenedorAlto)
        }

        Spacer(modifier = Modifier.height(12.dp))

        TarjetaGarantia(Icons.Default.Lock, ContenedorAlto, AzulSecundario, "100% Garantía de Fábrica", "Primeras marcas certificadas para obra")
        Spacer(modifier = Modifier.height(8.dp))
        TarjetaGarantia(Icons.Default.Place, DuraznoClaro, NaranjaOscuro, "Envíos Rápidos en Lima", "Rutas diarias y despacho consolidado a provincias")
        Spacer(modifier = Modifier.height(8.dp))
        TarjetaGarantia(Icons.Default.Star, ContenedorAlto, AzulSecundario, "Pagos Seguros y Flexibles", "Yape, Plin, tarjetas bancarias y transferencias BCP")

        Spacer(modifier = Modifier.height(24.dp))
        Text("RUC: 20601234567 • Lima, Perú", fontSize = 12.sp, color = Color.Gray)
    }
}

@Composable
private fun IndicadorItem(valor: String, etiqueta: String, modifier: Modifier = Modifier) {
    Column(modifier = modifier, horizontalAlignment = Alignment.CenterHorizontally) {
        Text(valor, fontSize = 18.sp, fontWeight = FontWeight.Bold, color = AzulSecundario)
        Text(etiqueta, fontSize = 11.sp, fontWeight = FontWeight.SemiBold, color = TextoSecundario)
    }
}

@Composable
private fun TarjetaGarantia(icono: ImageVector, fondo: Color, tint: Color, titulo: String, subtitulo: String) {
    Surface(color = BlancoTarjeta, shape = RoundedCornerShape(10.dp), shadowElevation = 1.dp, modifier = Modifier.fillMaxWidth()) {
        Row(modifier = Modifier.padding(12.dp), verticalAlignment = Alignment.CenterVertically) {
            Box(modifier = Modifier.size(38.dp).background(fondo, RoundedCornerShape(8.dp)), contentAlignment = Alignment.Center) {
                Icon(icono, contentDescription = null, tint = tint, modifier = Modifier.size(20.dp))
            }
            Spacer(modifier = Modifier.width(12.dp))
            Column {
                Text(titulo, fontSize = 13.sp, fontWeight = FontWeight.Bold, color = TextoOscuro)
                Text(subtitulo, fontSize = 12.sp, color = TextoSecundario)
            }
        }
    }
}