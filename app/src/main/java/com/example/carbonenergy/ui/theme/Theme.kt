package com.example.carbonenergy.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable

private val DarkColorPalette = darkColors(
    primary = BlueLight,
    primaryVariant = BlueDark,
    secondary = Orange
)
private val LightColorPalette = lightColors(
    primary = BlueMid,
    primaryVariant = BlueDark,
    secondary = Orange
)

@Composable
fun CarbonEnergyTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colors = if (darkTheme) {
        DarkColorPalette
    } else {
        LightColorPalette
    }
    MaterialTheme(
        colors = colors,
        typography = Typography,
        shapes = Shapes,
        content = content
    )
}