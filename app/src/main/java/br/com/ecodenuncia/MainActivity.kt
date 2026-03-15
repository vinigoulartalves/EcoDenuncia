package br.com.ecodenuncia

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import br.com.ecodenuncia.data.local.AppDatabase
import br.com.ecodenuncia.data.repository.DenunciaRepository
import br.com.ecodenuncia.navigation.EcoNavGraph
import br.com.ecodenuncia.ui.theme.EcoDenunciaTheme
import br.com.ecodenuncia.viewmodel.DenunciaViewModel
import br.com.ecodenuncia.viewmodel.DenunciaViewModelFactory

class MainActivity : ComponentActivity() {

    private val viewModel: DenunciaViewModel by viewModels {
        val dao = AppDatabase.getInstance(applicationContext).denunciaDao()
        DenunciaViewModelFactory(DenunciaRepository(dao))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            EcoDenunciaTheme {
                EcoNavGraph(viewModel = viewModel)
            }
        }
    }
}
