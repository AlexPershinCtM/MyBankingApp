package com.alexpershin.ui.components.dialogs

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.material3.AlertDialog as MaterialDialog

object AlertDialog {

    @Composable
    fun AlertDialog(
        title: String,
        body: String,
        confirmText: String? = null,
        cancelText: String? = null,
        onConfirm: () -> Unit = {},
        onDismiss: () -> Unit = {},
        onDismissRequest: () -> Unit = {},
    ) {

        MaterialDialog(
            onDismissRequest = onDismissRequest,
            confirmButton = {
                if (confirmText != null) {
                    DialogButton(confirmText, onConfirm)
                }
            },
            dismissButton = {
                if (cancelText != null) {
                    DialogButton(cancelText, onDismiss)
                }
            },
            title = {
                Text(text = title, style = MaterialTheme.typography.titleLarge)
            },
            text = {
                Text(text = body, style = MaterialTheme.typography.bodyMedium)
            }
        )


    }

    @Composable
    fun DialogButton(text: String, onClick: () -> Unit) {
        TextButton(onClick = onClick) {
            Text(text = text)
        }
    }

}