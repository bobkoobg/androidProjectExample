package com.example.androidprojectexample.ui.components.dialogs

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview

@Preview(showBackground = true)
@Composable
fun AppConfirmationDialogPreview() {
    AppConfirmationDialog(
        title = "Delete Song",
        message = "Are you sure you want to delete this song?",
        onDismissRequest = {},
        confirmButton = DialogButtonConfig("Approve") {},
        dismissButton = DialogButtonConfig("Decline") {},
        extraButton = DialogButtonConfig("Try Again") {}
    )
}

@Composable
fun AppConfirmationDialog(
    title: String,
    message: String,
    onDismissRequest: () -> Unit,
    confirmButton: DialogButtonConfig? = null,
    dismissButton: DialogButtonConfig? = null,
    extraButton: DialogButtonConfig? = null
) {
    AlertDialog(
        onDismissRequest = onDismissRequest,
        title = { Text(title) },
        text = { Text(message) },
        confirmButton = {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End
            ) {
                extraButton?.let {
                    TextButton(onClick = it.onClick) { Text(it.text) }
                }
                dismissButton?.let {
                    TextButton(onClick = it.onClick) { Text(it.text) }
                }
                confirmButton?.let {
                    TextButton(onClick = it.onClick) { Text(it.text) }
                }
            }
        }
    )
}
