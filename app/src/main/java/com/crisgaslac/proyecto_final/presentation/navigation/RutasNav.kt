package com.crisgaslac.proyecto_final.presentation.navigation

object RutasNav {
    const val INICIO = "inicio"
    const val FORMULARIO = "form?id={id}"
    const val BUSCAR = "buscar"
    const val DETALLE = "detalle/{id}"
    fun getTitulo(ruta: String?): String{
        return when{
            ruta == INICIO -> "Catálogo Académico"
            ruta?.startsWith("form") == true -> "Formulario"
            ruta?.startsWith(BUSCAR) == true -> "Buscar"
            ruta?.startsWith("detalle") == true -> "Detalle del Libro"
            else -> "Catálogo Académico"
        }
    }
}