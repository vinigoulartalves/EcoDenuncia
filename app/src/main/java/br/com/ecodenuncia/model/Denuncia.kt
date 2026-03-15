package br.com.ecodenuncia.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "denuncias")
data class Denuncia(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val titulo: String,
    val descricao: String,
    val tipoResiduo: String,
    val endereco: String,
    val bairro: String,
    val cidade: String,
    val observacoes: String,
    val fotoUri: String?,
    val status: StatusDenuncia,
    val dataCriacao: Long = System.currentTimeMillis()
)
