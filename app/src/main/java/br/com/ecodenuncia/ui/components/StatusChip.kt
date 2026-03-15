package br.com.ecodenuncia.ui.components

import androidx.compose.material3.AssistChip
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import br.com.ecodenuncia.model.StatusDenuncia

@Composable
fun StatusChip(status: StatusDenuncia) {
    AssistChip(
        onClick = {},
        enabled = false,
        label = {
            Text(
                text = status.name,
                color = MaterialTheme.colorScheme.primary
            )
        }
    )
}
