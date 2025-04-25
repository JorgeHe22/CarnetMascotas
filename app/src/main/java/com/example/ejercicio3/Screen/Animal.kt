package com.example.ejercicio3.Screen

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Animal(
    val nombre: String,
    val raza: String,
    val tamano: String,
    val edad: String,
    val foto: String
) : Parcelable
// La anotación @Parcelize convierte automáticamente esta clase en Parcelable,
// permitiendo que se pueda enviar entre pantallas (como ScreenA -> ScreenB)
// a través del sistema de navegación de Android.