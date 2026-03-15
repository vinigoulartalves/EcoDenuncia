package br.com.ecodenuncia.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val DarkColorScheme = darkColorScheme(
    primary = EcoGreen80,
    onPrimary = EcoBackgroundDark,
    primaryContainer = EcoGreenGrey40,
    onPrimaryContainer = EcoGreen80,
    secondary = EcoBlue80,
    onSecondary = EcoBackgroundDark,
    tertiary = EcoGreenGrey80,
    background = EcoBackgroundDark,
    surface = EcoSurfaceDark,
    surfaceVariant = EcoGreenGrey40,
    onSurface = EcoGreenGrey80,
    onSurfaceVariant = EcoGreen80
)

private val LightColorScheme = lightColorScheme(
    primary = EcoGreen40,
    onPrimary = EcoSurfaceLight,
    primaryContainer = EcoGreen80,
    onPrimaryContainer = EcoGreen40,
    secondary = EcoBlue40,
    tertiary = EcoGreenGrey40,
    background = EcoBackgroundLight,
    surface = EcoSurfaceLight,
    surfaceVariant = Color(0xFFDCE9DE),
    onSurface = Color(0xFF1A1C1A),
    onSurfaceVariant = Color(0xFF4E5D50)
)

@Composable
fun EcoDenunciaTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}
