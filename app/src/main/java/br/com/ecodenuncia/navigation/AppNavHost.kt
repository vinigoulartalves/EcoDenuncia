package br.com.ecodenuncia.navigation

import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import br.com.ecodenuncia.ui.screens.DetalheDenunciaScreen
import br.com.ecodenuncia.ui.screens.HomeScreen
import br.com.ecodenuncia.ui.screens.ListaDenunciasScreen
import br.com.ecodenuncia.ui.screens.LocalizacaoScreen
import br.com.ecodenuncia.ui.screens.NovaDenunciaScreen
import br.com.ecodenuncia.ui.screens.RevisaoScreen
import br.com.ecodenuncia.ui.screens.WelcomeScreen
import br.com.ecodenuncia.viewmodel.DenunciaViewModel

@Composable
fun AppNavHost(viewModel: DenunciaViewModel) {
    val navController = rememberNavController()
    val denuncias by viewModel.denuncias.collectAsStateWithLifecycle()
    val context = LocalContext.current

    NavHost(navController = navController, startDestination = AppRoutes.WELCOME) {
        composable(AppRoutes.WELCOME) {
            WelcomeScreen(onContinuar = { navController.navigate(AppRoutes.HOME) })
        }

        composable(AppRoutes.HOME) {
            HomeScreen(
                onNovaDenuncia = { navController.navigate(AppRoutes.NOVA_DENUNCIA) },
                onListarDenuncias = { navController.navigate(AppRoutes.LISTA_DENUNCIAS) }
            )
        }

        composable(AppRoutes.NOVA_DENUNCIA) {
            NovaDenunciaScreen(
                onVoltar = { navController.popBackStack() },
                onSalvarRascunho = { id -> navController.navigate(AppRoutes.localizacao(id)) },
                viewModel = viewModel
            )
        }

        composable(
            route = AppRoutes.LOCALIZACAO,
            arguments = listOf(navArgument("id") { type = NavType.LongType })
        ) { backStackEntry ->
            val id = backStackEntry.arguments?.getLong("id") ?: return@composable
            LocalizacaoScreen(
                viewModel = viewModel,
                onVoltar = { navController.popBackStack() },
                onContinuar = { navController.navigate(AppRoutes.revisao(id)) },
                onSalvarLocalizacao = { endereco, bairro, cidade, observacoes ->
                    viewModel.atualizarEndereco(id, endereco, bairro, cidade, observacoes)
                }
            )
        }

        composable(
            route = AppRoutes.REVISAO,
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
                    navController.navigate(AppRoutes.detalheDenuncia(id))
                }
            )
        }

        composable(AppRoutes.LISTA_DENUNCIAS) {
            ListaDenunciasScreen(
                denuncias = denuncias,
                onVoltar = { navController.popBackStack() },
                onDetalhes = { id -> navController.navigate(AppRoutes.detalheDenuncia(id)) },
                onExcluir = viewModel::excluirDenuncia
            )
        }

        composable(
            route = AppRoutes.DETALHE_DENUNCIA,
            arguments = listOf(navArgument("id") { type = NavType.LongType })
        ) { backStackEntry ->
            val id = backStackEntry.arguments?.getLong("id") ?: return@composable
            val denuncia = denuncias.firstOrNull { it.id == id } ?: return@composable

            DetalheDenunciaScreen(
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
