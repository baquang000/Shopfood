package com.example.shopfood.presentation.component

import androidx.annotation.StringRes
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign

@Composable
fun TextCustom(
    modifier: Modifier = Modifier,
    @StringRes text: Int,
    style: TextStyle = MaterialTheme.typography.bodyLarge,
    color: Color = MaterialTheme.colorScheme.outline,
    textAlign: TextAlign = TextAlign.Center
) {
    Text(
        text = stringResource(text),
        modifier = modifier,
        style = style.copy(
            color = color,
            fontWeight = FontWeight.W600
        ),
        textAlign = textAlign
    )
}
