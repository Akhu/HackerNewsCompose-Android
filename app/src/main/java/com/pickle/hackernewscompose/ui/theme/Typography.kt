package com.pickle.hackernewscompose.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.pickle.hackernewscompose.R

private val IBMSans = FontFamily(
    Font(R.font.ibm_sans_regular),
    Font(R.font.ibm_sans_medium, FontWeight.W500),
    Font(R.font.ibm_sans_semibold, FontWeight.W600),
    Font(R.font.ibm_sans_bold, FontWeight.W800)
)

private val IBMSerif = FontFamily(
    Font(R.font.ibm_serif_regular)
)

val HNTypography = Typography(
    headlineSmall = TextStyle(
        fontFamily = IBMSans,
        fontWeight = FontWeight.W800,
        fontSize = 16.sp
    ),
    headlineMedium = TextStyle(
        fontFamily = IBMSans,
        fontSize = 20.sp,
        fontWeight = FontWeight.W800
    ),
    headlineLarge = TextStyle(
        fontFamily = IBMSans,
        fontSize = 30.sp,
        fontWeight = FontWeight.W800
    ),

    labelLarge = TextStyle(
        fontFamily = IBMSans,
        fontSize = 24.sp,
        fontWeight = FontWeight.W600
    ),
    labelMedium = TextStyle(
        fontFamily = IBMSans,
        fontSize = 16.sp,
        fontWeight = FontWeight.W600
    ),
    labelSmall = TextStyle(
        fontFamily = IBMSans,
        fontSize = 16.sp,
        fontWeight = FontWeight.W600
    ),
    bodyLarge = TextStyle(
        fontFamily = IBMSerif,
        fontSize = 20.sp,
    ),
    bodyMedium = TextStyle(
        fontFamily = IBMSerif,
        fontSize = 16.sp,
        fontWeight = FontWeight.Normal
    ),
    bodySmall = TextStyle(
        fontFamily = IBMSerif,
        fontSize = 14.sp,
    ),

)