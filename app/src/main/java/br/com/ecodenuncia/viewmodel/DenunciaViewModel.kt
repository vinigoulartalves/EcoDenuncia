package br.com.ecodenuncia.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import br.com.ecodenuncia.data.repository.DenunciaRepository
import br.com.ecodenuncia.model.Denuncia
import br.com.ecodenuncia.model.StatusDenuncia
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class DenunciaViewModel(
    private val repository: DenunciaRepository
) : ViewModel() {

    val denuncias: StateFlow<List<Denuncia>> = repository
        .listarDenuncias()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), emptyList())

    fun obterDenuncia(id: Long): StateFlow<Denuncia?> {
        return repository
            .buscarPorId(id)
            .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), null)
    }

    fun criarRascunho(
        titulo: String,
        descricao: String,
        tipoResiduo: String,
        observacoes: String,
        onSalvo: (Long) -> Unit
    ) {
        viewModelScope.launch {
            val id = repository.salvarRascunho(
                Denuncia(
                    titulo = titulo,
                    descricao = descricao,
                    tipoResiduo = tipoResiduo,
                    endereco = "",
                    bairro = "",
                    cidade = "",
                    observacoes = observacoes,
                    fotoUri = null,
                    status = StatusDenuncia.RASCUNHO
                )
            )
            onSalvo(id)
        }
    }

    fun atualizarEndereco(id: Long, endereco: String, bairro: String, cidade: String) {
        viewModelScope.launch {
            val denuncia = repository.buscarPorIdUmaVez(id) ?: return@launch
            repository.atualizar(
                denuncia.copy(
                    endereco = endereco,
                    bairro = bairro,
                    cidade = cidade,
                    status = StatusDenuncia.PENDENTE
                )
            )
        }
    }

    fun enviarDenuncia(denuncia: Denuncia) {
        viewModelScope.launch {
            repository.enviarDenuncia(denuncia)
        }
    }

    fun excluirDenuncia(denuncia: Denuncia) {
        viewModelScope.launch {
            repository.excluir(denuncia)
        }
    }
}

class DenunciaViewModelFactory(
    private val repository: DenunciaRepository
) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return DenunciaViewModel(repository) as T
    }
}
