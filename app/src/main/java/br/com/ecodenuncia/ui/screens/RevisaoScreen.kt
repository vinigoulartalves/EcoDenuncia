package br.com.ecodenuncia.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import br.com.ecodenuncia.model.Denuncia
import br.com.ecodenuncia.ui.components.StatusChip

@Composable
fun RevisaoScreen(
    denuncia: Denuncia,
    onVoltar: () -> Unit,
    onSalvar: () -> Unit,
    onEnviar: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text("Revisão", style = MaterialTheme.typography.headlineSmall)

        Text("Status atual")
        StatusChip(status = denuncia.status)

        Spacer(modifier = Modifier.height(8.dp))

        Text("Título: ${denuncia.titulo}")
        Text("Descrição: ${denuncia.descricao}")
        Text("Tipo de resíduo: ${denuncia.tipoResiduo}")
        Text("Endereço: ${denuncia.endereco}")
        Text("Bairro: ${denuncia.bairro}")
        Text("Cidade: ${denuncia.cidade}")
        Text("Observações: ${denuncia.observacoes}")
        Text("Foto URI: ${denuncia.fotoUri ?: "Não informada"}")

        Spacer(modifier = Modifier.height(8.dp))

        Button(onClick = onSalvar) {
            Text("Salvar denúncia")
        }

        Button(onClick = onEnviar) {
            Text("Simular envio")
        }

        Button(onClick = onVoltar) {
            Text("Voltar")
        }
    }
}
