package br.com.ecodenuncia.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import br.com.ecodenuncia.viewmodel.DenunciaViewModel

@Composable
fun NovaDenunciaScreen(
    viewModel: DenunciaViewModel,
    onVoltar: () -> Unit,
    onSalvarRascunho: (Long) -> Unit
) {
    var titulo by remember { mutableStateOf("") }
    var descricao by remember { mutableStateOf("") }
    var tipoResiduo by remember { mutableStateOf("") }
    var observacoes by remember { mutableStateOf("") }
    var erro by remember { mutableStateOf<String?>(null) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text("Nova denúncia")

        OutlinedTextField(value = titulo, onValueChange = { titulo = it }, label = { Text("Título") })
        OutlinedTextField(value = descricao, onValueChange = { descricao = it }, label = { Text("Descrição") })
        OutlinedTextField(value = tipoResiduo, onValueChange = { tipoResiduo = it }, label = { Text("Tipo de resíduo") })
        OutlinedTextField(value = observacoes, onValueChange = { observacoes = it }, label = { Text("Observações") })

        erro?.let { Text(it) }

        Button(onClick = {
            if (titulo.isBlank() || descricao.isBlank() || tipoResiduo.isBlank()) {
                erro = "Preencha título, descrição e tipo de resíduo."
            } else {
                erro = null
                viewModel.criarRascunho(
                    titulo = titulo,
                    descricao = descricao,
                    tipoResiduo = tipoResiduo,
                    observacoes = observacoes,
                    onSalvo = onSalvarRascunho
                )
            }
        }) {
            Text("Continuar")
        }

        Button(onClick = onVoltar) {
            Text("Voltar")
        }
    }
}
