package com.example.todotodone.ui.core

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalConfiguration

@Composable
fun rememberWindowInfo(): WindowSizeClass {
    val config = LocalConfiguration.current
    return when {
        config.screenWidthDp < 600 -> WindowSizeClass.Compact
        config.screenWidthDp < 840 -> WindowSizeClass.Medium
        else -> WindowSizeClass.Expanded
    }
}