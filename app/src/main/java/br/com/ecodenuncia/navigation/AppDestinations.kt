package br.com.ecodenuncia.navigation

sealed class AppDestinations(val route: String) {
    data object Welcome : AppDestinations("welcome")
    data object Home : AppDestinations("home")
    data object NovaDenuncia : AppDestinations("nova_denuncia")
    data object Localizacao : AppDestinations("localizacao/{id}") {
        fun createRoute(id: Long) = "localizacao/$id"
    }
    data object Revisao : AppDestinations("revisao/{id}") {
        fun createRoute(id: Long) = "revisao/$id"
    }
    data object Lista : AppDestinations("lista")
    data object Detalhes : AppDestinations("detalhes/{id}") {
        fun createRoute(id: Long) = "detalhes/$id"
    }
}
