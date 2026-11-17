package com.example.animalsapp.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable

private val AppDarkColorScheme = darkColorScheme(
    primary = NeonYellow,
    secondary = PastelLilac,
    tertiary = CoralOrange,
    background = DarkBackground,
    surface = DarkSurface,
    onPrimary = TextDark,
    onSecondary = TextDark,
    onTertiary = TextDark,
    onBackground = TextWhite,
    onSurface = TextWhite
)

@Composable
fun AnimalsAppTheme(
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = AppDarkColorScheme,
        typography = Typography,
        content = content
    )
}