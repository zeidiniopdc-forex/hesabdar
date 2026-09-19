package com.hesabdar.app.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext

private val ColorSoftPrimary = Color(0xFFE3F2FD)

private val LightColors = lightColorScheme(
    primary = HesabdarBlue,
    onPrimary = CardLight,
    primaryContainer = ColorSoftPrimary,
    onPrimaryContainer = HesabdarBlue,
    secondary = HesabdarTeal,
    onSecondary = CardLight,
    tertiary = IncomeGreen,
    error = ExpenseRed,
    background = SurfaceLight,
    onBackground = TextPrimary,
    surface = CardLight,
    onSurface = TextPrimary,
    surfaceVariant = Color(0xFFE8EEF5),
    onSurfaceVariant = TextSecondary,
    outline = Color(0xFFC5CDD8)
)

private val DarkColors = darkColorScheme(
    primary = HesabdarBlueLight,
    onPrimary = SurfaceDark,
    primaryContainer = Color(0xFF0D47A1),
    onPrimaryContainer = Color(0xFFBBDEFB),
    secondary = Color(0xFF4DB6AC),
    onSecondary = SurfaceDark,
    tertiary = Color(0xFF81C784),
    error = Color(0xFFEF9A9A),
    background = SurfaceDark,
    onBackground = TextPrimaryDark,
    surface = CardDark,
    onSurface = TextPrimaryDark,
    surfaceVariant = Color(0xFF2A3038),
    onSurfaceVariant = TextSecondaryDark,
    outline = Color(0xFF5F6B7A)
)

@Composable
fun HesabdarTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }
        darkTheme -> DarkColors
        else -> LightColors
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = HesabdarTypography,
        content = content
    )
}
