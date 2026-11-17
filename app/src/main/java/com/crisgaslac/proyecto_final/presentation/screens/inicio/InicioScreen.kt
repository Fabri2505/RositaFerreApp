package com.crisgaslac.proyecto_final.presentation.screens.inicio
import androidx.compose.foundation.layout.Arrangement
import com.crisgaslac.proyecto_final.presentation.common.CuadroInfo
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Book
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.crisgaslac.proyecto_final.presentation.common.LibroItem

@Composable
fun InicioScreen(onDetalleLibro:(String) -> Unit){
    Column(
        modifier = Modifier.fillMaxSize().padding(top = 20.dp, bottom = 10.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth().padding(end = 10.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Spacer(modifier = Modifier.weight(1f))
            CuadroInfo(Icons.Default.Book, "Total: 3 libros registrados")
        }

        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(3) {
                LibroItem(onDetalleClick = {onDetalleLibro("1")})
            }
        }
    }
}