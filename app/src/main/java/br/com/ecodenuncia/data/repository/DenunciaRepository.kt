package br.com.ecodenuncia.data.repository

import br.com.ecodenuncia.data.local.DenunciaDao
import br.com.ecodenuncia.model.Denuncia
import br.com.ecodenuncia.model.StatusDenuncia
import kotlinx.coroutines.flow.Flow

class DenunciaRepository(
    private val dao: DenunciaDao
) {
    fun listar(): Flow<List<Denuncia>> = dao.listar()

    fun buscarPorId(id: Long): Flow<Denuncia?> = dao.buscarPorId(id)

    suspend fun buscarPorIdUmaVez(id: Long): Denuncia? = dao.buscarPorIdUmaVez(id)

    suspend fun inserir(denuncia: Denuncia): Long = dao.inserir(denuncia)

    suspend fun atualizar(denuncia: Denuncia) = dao.atualizar(denuncia)

    suspend fun excluir(denuncia: Denuncia) = dao.excluir(denuncia)

    // Compatibilidade com fluxos já existentes na aplicação.
    fun listarDenuncias(): Flow<List<Denuncia>> = listar()

    suspend fun salvarRascunho(denuncia: Denuncia): Long {
        return inserir(denuncia.copy(status = StatusDenuncia.RASCUNHO))
    }

    suspend fun enviarDenuncia(denuncia: Denuncia) {
        atualizar(denuncia.copy(status = StatusDenuncia.ENVIADA))
    }
}
