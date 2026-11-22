package com.grupo2.rositaferreapp.datos

import com.grupo2.rositaferreapp.R

object DatosCatalogo {
    val categorias = listOf("Todos", "Herramientas", "Construcción", "Gasfitería", "Electricidad", "Pinturas")

    val listaProductos = listOf(
        Producto(
            id = 1,
            sku = "HER-001",
            nombre = "Taladro Percutor 1/2\" 650W",
            categoria = "Herramientas",
            marca = "BOSCH",
            subtituloMarca = "Cód. HER-001",
            precio = 249.90,
            precioAnterior = 289.00,
            detalleExtra = "Mayorista: S/ 235.00",
            etiqueta = "Envío Gratis",
            esEtiquetaNaranja = false,
            stock = 15,
            imagenId = R.drawable.port1,
            imagenesCarrusel = listOf(R.drawable.port2, R.drawable.prod_taladro, R.drawable.port3),
            incluyeCaja = listOf(
                "1 Taladro percutor Bosch 650W",
                "1 Mango auxiliar y tope de profundidad",
                "1 Juego de brocas para concreto",
                "1 Maletín rígido de transporte profesional",
                "Manual de usuario y póliza de garantía oficial"
            )
        ),
        Producto(
            id = 2,
            sku = "CON-014",
            nombre = "Cemento Portland Tipo I 42.5kg",
            categoria = "Construcción",
            marca = "CEMENTO SOL",
            subtituloMarca = "42.5 kg",
            precio = 29.50,
            precioAnterior = null,
            detalleExtra = "X Mayor (>50 bolsas): S/ 28.20",
            etiqueta = "Tipo I",
            esEtiquetaNaranja = false,
            stock = 120,
            imagenId = R.drawable.cemento,
            imagenesCarrusel = listOf(R.drawable.cemento, R.drawable.port5, R.drawable.port4),
            incluyeCaja = listOf(
                "1 Bolsa de Cemento Portland Tipo I de 42.5 kg",
                "Empaque resistente de triple pliego certificado"
            )
        ),
        Producto(
            id = 3,
            sku = "GAS-008",
            nombre = "Tubo PVC Agua Fría 1/2\" x 5m",
            categoria = "Gasfitería",
            marca = "PAVCO",
            subtituloMarca = "Clase 10",
            precio = 14.80,
            precioAnterior = null,
            detalleExtra = "Stock: 80 und",
            etiqueta = "Oferta",
            esEtiquetaNaranja = false,
            stock = 80,
            imagenId = R.drawable.tubo,
            imagenesCarrusel = listOf(R.drawable.tubo),
            incluyeCaja = listOf(
                "1 Tubo PVC rígido Pavco de 1/2 pulgada x 5 metros",
                "Extremos roscados de fábrica"
            )
        ),
        Producto(
            id = 4,
            sku = "HER-022",
            nombre = "Amoladora Angular 4-1/2\" 820W",
            categoria = "Herramientas",
            marca = "TRUPER",
            subtituloMarca = "820W",
            precio = 165.00,
            precioAnterior = null,
            detalleExtra = "Stock: 22 und",
            etiqueta = "Top Ventas",
            esEtiquetaNaranja = true,
            stock = 22,
            imagenId = R.drawable.amoladora,
            imagenesCarrusel = listOf(R.drawable.amoladora, R.drawable.amoladora, R.drawable.amoladora),
            incluyeCaja = listOf(
                "1 Amoladora angular Truper 820W",
                "1 Guarda de seguridad metálica",
                "1 Mango lateral y llave de ajuste"
            )
        ),
        Producto(
            id = 5,
            sku = "CON-031",
            nombre = "Fierro Corrugado 1/2\" x 9m",
            categoria = "Construcción",
            marca = "ACEROS AREQUIPA",
            subtituloMarca = "Grado 60",
            precio = 38.90,
            precioAnterior = null,
            detalleExtra = "X Mayor (>100 var): S/ 37.50",
            etiqueta = "Certificado",
            esEtiquetaNaranja = true,
            stock = 200,
            imagenId = R.drawable.fierro,
            imagenesCarrusel = listOf(R.drawable.fierro, R.drawable.fierro, R.drawable.fierro),
            incluyeCaja = listOf(
                "1 Varilla de acero corrugado ASTM A615 Grado 60 (9 metros)"
            )
        ),
        Producto(
            id = 6,
            sku = "ELE-019",
            nombre = "Cable Eléctrico TW 2.5mm (100m)",
            categoria = "Electricidad",
            marca = "INDECO",
            subtituloMarca = "100% Cobre",
            precio = 142.00,
            precioAnterior = 155.00,
            detalleExtra = "Stock: 35 und",
            etiqueta = "Envío Gratis",
            esEtiquetaNaranja = false,
            stock = 35,
            imagenId = R.drawable.cable,
            imagenesCarrusel = listOf(R.drawable.cable, R.drawable.cable, R.drawable.cable),
            incluyeCaja = listOf(
                "1 Rollo sellado de 100 metros Cable Indeco TW-80 2.5mm²"
            )
        ),
        Producto(
            id = 7,
            sku = "PIN-004",
            nombre = "Pintura Látex Satinado Blanco 4L",
            categoria = "Pinturas",
            marca = "VENCEDOR",
            subtituloMarca = "Látex",
            precio = 68.50,
            precioAnterior = null,
            detalleExtra = "Rendimiento: 40m²",
            etiqueta = "4 Litros",
            esEtiquetaNaranja = false,
            stock = 40,
            imagenId = R.drawable.vencedor,
            imagenesCarrusel = listOf(R.drawable.vencedor, R.drawable.vencedor, R.drawable.vencedor),
            incluyeCaja = listOf(
                "1 Galón (4 Litros) de Pintura Látex Vencedor Blanco"
            )
        )
    )

    fun filtrarProductos(categoria: String, busqueda: String): List<Producto> {
        return listaProductos.filter { prod ->
            val coincideCat = (categoria == "Todos") || (prod.categoria == categoria)
            val coincideBusq = busqueda.isBlank() ||
                    prod.nombre.contains(busqueda, ignoreCase = true) ||
                    prod.marca.contains(busqueda, ignoreCase = true)
            coincideCat && coincideBusq
        }
    }
}