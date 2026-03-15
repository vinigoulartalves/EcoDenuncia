package br.com.ecodenuncia.navigation

object AppRoutes {
    const val WELCOME = "welcome"
    const val HOME = "home"
    const val NOVA_DENUNCIA = "nova_denuncia"
    const val LOCALIZACAO = "localizacao/{id}"
    const val REVISAO = "revisao/{id}"
    const val LISTA_DENUNCIAS = "lista_denuncias"
    const val DETALHE_DENUNCIA = "detalhe_denuncia/{id}"

    fun localizacao(id: Long): String = "localizacao/$id"

    fun revisao(id: Long): String = "revisao/$id"

    fun detalheDenuncia(id: Long): String = "detalhe_denuncia/$id"
}
