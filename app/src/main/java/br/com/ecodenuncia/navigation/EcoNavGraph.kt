package br.com.ecodenuncia.navigation

import androidx.compose.runtime.Composable
import android.widget.Toast
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import br.com.ecodenuncia.ui.screens.DetalhesScreen
import br.com.ecodenuncia.ui.screens.HomeScreen
import br.com.ecodenuncia.ui.screens.ListaDenunciasScreen
import br.com.ecodenuncia.ui.screens.LocalizacaoScreen
import br.com.ecodenuncia.ui.screens.NovaDenunciaScreen
import br.com.ecodenuncia.ui.screens.RevisaoScreen
import br.com.ecodenuncia.ui.screens.WelcomeScreen
import br.com.ecodenuncia.viewmodel.DenunciaViewModel

@Composable
fun EcoNavGraph(viewModel: DenunciaViewModel) {
    val navController = rememberNavController()
    val denuncias by viewModel.denuncias.collectAsStateWithLifecycle()
    val context = LocalContext.current

    NavHost(navController = navController, startDestination = AppDestinations.Welcome.route) {
        composable(AppDestinations.Welcome.route) {
            WelcomeScreen(onContinuar = { navController.navigate(AppDestinations.Home.route) })
        }

        composable(AppDestinations.Home.route) {
            HomeScreen(
                onNovaDenuncia = { navController.navigate(AppDestinations.NovaDenuncia.route) },
                onListarDenuncias = { navController.navigate(AppDestinations.Lista.route) }
            )
        }

        composable(AppDestinations.NovaDenuncia.route) {
            NovaDenunciaScreen(
                onVoltar = { navController.popBackStack() },
                onSalvarRascunho = { id -> navController.navigate(AppDestinations.Localizacao.createRoute(id)) },
                viewModel = viewModel
            )
        }

        composable(
            route = AppDestinations.Localizacao.route,
            arguments = listOf(navArgument("id") { type = NavType.LongType })
        ) { backStackEntry ->
            val id = backStackEntry.arguments?.getLong("id") ?: return@composable
            LocalizacaoScreen(
                onVoltar = { navController.popBackStack() },
                onContinuar = { navController.navigate(AppDestinations.Revisao.createRoute(id)) },
                onSalvarEndereco = { endereco, bairro, cidade ->
                    viewModel.atualizarEndereco(id, endereco, bairro, cidade)
                }
            )
        }

        composable(
            route = AppDestinations.Revisao.route,
            arguments = listOf(navArgument("id") { type = NavType.LongType })
        ) { backStackEntry ->
            val id = backStackEntry.arguments?.getLong("id") ?: return@composable
            val denuncia = denuncias.firstOrNull { it.id == id } ?: return@composable

            RevisaoScreen(
                denuncia = denuncia,
                onVoltar = { navController.popBackStack() },
                onEnviar = {
                    viewModel.enviarDenuncia(denuncia)
                    Toast.makeText(context, "Denúncia enviada com sucesso.", Toast.LENGTH_SHORT).show()
                    navController.navigate(AppDestinations.Detalhes.createRoute(id))
                }
            )
        }

        composable(AppDestinations.Lista.route) {
            ListaDenunciasScreen(
                denuncias = denuncias,
                onVoltar = { navController.popBackStack() },
                onDetalhes = { id -> navController.navigate(AppDestinations.Detalhes.createRoute(id)) },
                onExcluir = viewModel::excluirDenuncia
            )
        }

        composable(
            route = AppDestinations.Detalhes.route,
            arguments = listOf(navArgument("id") { type = NavType.LongType })
        ) { backStackEntry ->
            val id = backStackEntry.arguments?.getLong("id") ?: return@composable
            val denuncia = denuncias.firstOrNull { it.id == id } ?: return@composable

            DetalhesScreen(
                denuncia = denuncia,
                onVoltar = { navController.popBackStack() },
                onEnviar = {
                    viewModel.enviarDenuncia(denuncia)
                    Toast.makeText(context, "Denúncia enviada com sucesso.", Toast.LENGTH_SHORT).show()
                }
            )
        }
    }
}
