package br.com.ecodenuncia.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import br.com.ecodenuncia.model.Denuncia
import br.com.ecodenuncia.model.StatusDenuncia
import br.com.ecodenuncia.ui.components.StatusChip
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetalheDenunciaScreen(
    denuncia: Denuncia,
    onVoltar: () -> Unit,
    onSimularEnvio: () -> Unit,
    onExcluir: () -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Detalhes da denúncia") }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Card(modifier = Modifier.fillMaxWidth()) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    Text(
                        text = denuncia.titulo,
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.SemiBold
                    )
                    StatusChip(status = denuncia.status)
                    CampoDetalhe(label = "ID", valor = denuncia.id.toString())
                    CampoDetalhe(label = "Descrição", valor = denuncia.descricao)
                    CampoDetalhe(label = "Tipo de resíduo", valor = denuncia.tipoResiduo)
                    CampoDetalhe(label = "Endereço", valor = denuncia.endereco)
                    CampoDetalhe(label = "Bairro", valor = denuncia.bairro)
                    CampoDetalhe(label = "Cidade", valor = denuncia.cidade)
                    CampoDetalhe(label = "Observações", valor = denuncia.observacoes)
                    CampoDetalhe(label = "Foto (URI)", valor = denuncia.fotoUri ?: "Não informada")
                    CampoDetalhe(label = "Data de criação", valor = formatarData(denuncia.dataCriacao))
                }
            }

            if (denuncia.status != StatusDenuncia.ENVIADA) {
                Button(
                    onClick = onSimularEnvio,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Simular envio")
                }
            }

            OutlinedButton(
                onClick = onExcluir,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Excluir denúncia")
            }

            OutlinedButton(
                onClick = onVoltar,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Voltar para lista")
            }
        }
    }
}

@Composable
private fun CampoDetalhe(label: String, valor: String) {
    Column(verticalArrangement = Arrangement.spacedBy(2.dp)) {
        Text(
            text = label,
            style = MaterialTheme.typography.labelLarge,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        Text(
            text = valor,
            style = MaterialTheme.typography.bodyLarge
        )
    }
}

private fun formatarData(dataCriacao: Long): String {
    val formatter = SimpleDateFormat("dd/MM/yyyy HH:mm", Locale("pt", "BR"))
    return formatter.format(Date(dataCriacao))
}
