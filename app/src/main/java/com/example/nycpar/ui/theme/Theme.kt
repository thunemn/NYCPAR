package com.example.nycpar.ui.theme

import android.annotation.SuppressLint
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.Colors
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable

@Composable
private fun BaseLightPalette(): Colors {
    return lightColors(
        primary = Primary,
        primaryVariant = Primary,
        secondary = Accent,
        secondaryVariant = Accent,
        background = White,
        onPrimary = Black,
        onBackground = Black

        /* Other default colors to override
            surface = Color.White,
            onSecondary = Color.Black,
            onSurface = Color.Black,
        */
    )
}

@SuppressLint("ConflictingOnColor")
@Composable
private fun BaseDarkPalette(): Colors {
    return darkColors(
        primary = PrimaryDark,
        primaryVariant = PrimaryDark,
        secondary = Accent,
        secondaryVariant = Accent,
        background = BackgroundDark,
        onPrimary = White,
        onBackground = White
    )
}

@Composable
fun AppTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colors = if (darkTheme) {
        BaseDarkPalette()
    } else {
        BaseLightPalette()
    }

    MaterialTheme(
        colors = colors,
        typography = Typography,
        shapes = Shapes,
        content = content
    )
}