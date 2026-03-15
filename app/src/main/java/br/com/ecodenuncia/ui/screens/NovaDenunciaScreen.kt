package br.com.ecodenuncia.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import br.com.ecodenuncia.viewmodel.CampoFormularioDenuncia
import br.com.ecodenuncia.viewmodel.DenunciaViewModel

@Composable
fun NovaDenunciaScreen(
    viewModel: DenunciaViewModel,
    onVoltar: () -> Unit,
    onSalvarRascunho: (Long) -> Unit
) {
    val formState by viewModel.formState.collectAsStateWithLifecycle()
    var exibirErros by rememberSaveable { mutableStateOf(false) }

    val tituloInvalido = exibirErros && formState.titulo.isBlank()
    val descricaoInvalida = exibirErros && formState.descricao.isBlank()
    val tipoResiduoInvalido = exibirErros && formState.tipoResiduo.isBlank()

    Scaffold { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Text(
                text = "Nova denúncia",
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold
            )

            Text(
                text = "Preencha os dados iniciais para continuar.",
                style = MaterialTheme.typography.bodyMedium
            )

            OutlinedTextField(
                value = formState.titulo,
                onValueChange = {
                    viewModel.atualizarCampoFormulario(CampoFormularioDenuncia.TITULO, it)
                },
                modifier = Modifier.fillMaxWidth(),
                label = { Text("Título") },
                isError = tituloInvalido,
                supportingText = {
                    if (tituloInvalido) {
                        Text("Informe o título da denúncia.")
                    }
                },
                singleLine = true
            )

            OutlinedTextField(
                value = formState.descricao,
                onValueChange = {
                    viewModel.atualizarCampoFormulario(CampoFormularioDenuncia.DESCRICAO, it)
                },
                modifier = Modifier.fillMaxWidth(),
                label = { Text("Descrição") },
                isError = descricaoInvalida,
                supportingText = {
                    if (descricaoInvalida) {
                        Text("Informe a descrição da denúncia.")
                    }
                },
                minLines = 3
            )

            OutlinedTextField(
                value = formState.tipoResiduo,
                onValueChange = {
                    viewModel.atualizarCampoFormulario(CampoFormularioDenuncia.TIPO_RESIDUO, it)
                },
                modifier = Modifier.fillMaxWidth(),
                label = { Text("Tipo de resíduo") },
                isError = tipoResiduoInvalido,
                supportingText = {
                    if (tipoResiduoInvalido) {
                        Text("Informe o tipo de resíduo.")
                    }
                },
                singleLine = true
            )

            OutlinedTextField(
                value = formState.fotoUri.orEmpty(),
                onValueChange = {
                    viewModel.atualizarCampoFormulario(CampoFormularioDenuncia.FOTO_URI, it)
                },
                modifier = Modifier.fillMaxWidth(),
                label = { Text("fotoUri (simulação)") },
                supportingText = {
                    Text("Neste momento, use um texto/URI simulado.")
                },
                singleLine = true
            )

            Button(
                onClick = {
                    exibirErros = true
                    if (formState.titulo.isNotBlank() &&
                        formState.descricao.isNotBlank() &&
                        formState.tipoResiduo.isNotBlank()
                    ) {
                        viewModel.salvarDenuncia(onSalvarRascunho)
                    }
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Continuar")
            }

            TextButton(onClick = onVoltar, modifier = Modifier.fillMaxWidth()) {
                Text("Voltar")
            }
        }
    }
}
