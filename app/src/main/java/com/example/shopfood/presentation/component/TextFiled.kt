package com.example.shopfood.presentation.component

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import com.example.shopfood.ui.theme.backgroundColorGrayLight

@Composable
fun TextFieldCustom(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    label: String = "",
    placeholder: String = "",
    isSingleLine: Boolean = true,
    trailingIcon: @Composable (() -> Unit)? = null,
    visualTransformation: VisualTransformation = VisualTransformation.None
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        label = {
            if (label.isNotEmpty()) Text(label)
        },
        placeholder = {
            if (placeholder.isNotEmpty()) {
                Text(
                    text = placeholder,
                    style = MaterialTheme.typography.bodyMedium.copy(
                        color = MaterialTheme.colorScheme.outline
                    )
                )
            }
        },
        singleLine = isSingleLine,
        shape = RoundedCornerShape(8.dp),
        textStyle = MaterialTheme.typography.bodyMedium,
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = Color.Transparent,
            unfocusedBorderColor = Color.Transparent,
            cursorColor = MaterialTheme.colorScheme.outline,
            disabledBorderColor = Color.Transparent,
            unfocusedContainerColor = MaterialTheme.colorScheme.secondaryContainer,
            focusedContainerColor = MaterialTheme.colorScheme.secondaryContainer,
            disabledContainerColor = MaterialTheme.colorScheme.secondaryContainer,
            focusedTextColor = MaterialTheme.colorScheme.outline,
            unfocusedTextColor = MaterialTheme.colorScheme.outline,
            disabledTextColor = MaterialTheme.colorScheme.outline
        ),
        trailingIcon = trailingIcon?.let { icon ->
            { icon() }
        },
        visualTransformation = visualTransformation
    )
}

@Composable
fun TextFieldCustomWithSearch(
    modifier: Modifier = Modifier,
    value: String,
    onValueChange: (String) -> Unit,
    enabled: Boolean = true,
    readOnly: Boolean = false,
    searchClick: () -> Unit = {},
    clearClick: () -> Unit = {}
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        modifier = modifier
            .fillMaxWidth(),
        placeholder = {
            Text(
                text = "Search dishes, restaurants",
                style = MaterialTheme.typography.bodyMedium.copy(
                    color = MaterialTheme.colorScheme.outline
                )
            )
        },
        singleLine = true,
        shape = RoundedCornerShape(8.dp),
        textStyle = MaterialTheme.typography.bodyMedium,
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = Color.Transparent,
            unfocusedBorderColor = Color.Transparent,
            cursorColor = MaterialTheme.colorScheme.outline,
            disabledBorderColor = Color.Transparent,
            unfocusedContainerColor = backgroundColorGrayLight,
            focusedContainerColor = backgroundColorGrayLight,
            disabledContainerColor = backgroundColorGrayLight,
            focusedTextColor = Color.DarkGray,
            unfocusedTextColor = Color.DarkGray,
            disabledTextColor = Color.DarkGray,
            disabledLeadingIconColor = Color.DarkGray,
            disabledTrailingIconColor = Color.DarkGray,
            focusedLeadingIconColor = Color.Black,
            focusedTrailingIconColor = Color.Black,
        ),
        trailingIcon = {
            if (value.isNotBlank()) {
                IconButton(
                    onClick = clearClick
                ) {
                    Icon(
                        Icons.Filled.Close, ""
                    )
                }

            } else null
        },
        leadingIcon = {
            IconButton(
                onClick = searchClick
            ) {
                Icon(
                    Icons.Filled.Search, ""
                )
            }

        },
        enabled = enabled,
        readOnly = readOnly,
        keyboardOptions = KeyboardOptions(
            imeAction = ImeAction.Search
        ),
        keyboardActions = KeyboardActions(
            onSearch = {
                searchClick()
            }
        )
    )
}