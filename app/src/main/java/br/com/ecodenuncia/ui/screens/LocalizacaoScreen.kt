package br.com.ecodenuncia.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import br.com.ecodenuncia.viewmodel.CampoFormularioDenuncia
import br.com.ecodenuncia.viewmodel.DenunciaViewModel

@Composable
fun LocalizacaoScreen(
    viewModel: DenunciaViewModel,
    onVoltar: () -> Unit,
    onContinuar: () -> Unit,
    onSalvarLocalizacao: (String, String, String, String) -> Unit
) {
    val formState by viewModel.formState.collectAsStateWithLifecycle()
    val enderecoInvalido = formState.endereco.isBlank()
    val cidadeInvalida = formState.cidade.isBlank()

    Scaffold { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .verticalScroll(rememberScrollState())
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Text(
                text = "Localização",
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold
            )

            Text(
                text = "Preencha o endereço ou um ponto de referência do local.",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )

            OutlinedTextField(
                value = formState.endereco,
                onValueChange = {
                    viewModel.atualizarCampoFormulario(CampoFormularioDenuncia.ENDERECO, it)
                },
                modifier = Modifier.fillMaxWidth(),
                label = { Text("Endereço ou referência") },
                isError = enderecoInvalido,
                supportingText = {
                    if (enderecoInvalido) {
                        Text("Informe o endereço ou uma referência do local.")
                    }
                },
                shape = RoundedCornerShape(12.dp)
            )

            OutlinedTextField(
                value = formState.bairro,
                onValueChange = {
                    viewModel.atualizarCampoFormulario(CampoFormularioDenuncia.BAIRRO, it)
                },
                modifier = Modifier.fillMaxWidth(),
                label = { Text("Bairro") },
                singleLine = true,
                shape = RoundedCornerShape(12.dp)
            )

            OutlinedTextField(
                value = formState.cidade,
                onValueChange = {
                    viewModel.atualizarCampoFormulario(CampoFormularioDenuncia.CIDADE, it)
                },
                modifier = Modifier.fillMaxWidth(),
                label = { Text("Cidade") },
                isError = cidadeInvalida,
                supportingText = {
                    if (cidadeInvalida) {
                        Text("Informe a cidade da ocorrência.")
                    }
                },
                singleLine = true,
                shape = RoundedCornerShape(12.dp)
            )

            OutlinedTextField(
                value = formState.observacoes,
                onValueChange = {
                    viewModel.atualizarCampoFormulario(CampoFormularioDenuncia.OBSERVACOES, it)
                },
                modifier = Modifier.fillMaxWidth(),
                label = { Text("Observações") },
                minLines = 3,
                shape = RoundedCornerShape(12.dp)
            )

            Button(
                onClick = {
                    if (formState.endereco.isNotBlank() && formState.cidade.isNotBlank()) {
                        onSalvarLocalizacao(
                            formState.endereco,
                            formState.bairro,
                            formState.cidade,
                            formState.observacoes
                        )
                        onContinuar()
                    }
                },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp)
            ) {
                Text("Avançar para revisão")
            }

            TextButton(onClick = onVoltar, modifier = Modifier.fillMaxWidth()) {
                Text("Voltar")
            }
        }
    }
}
