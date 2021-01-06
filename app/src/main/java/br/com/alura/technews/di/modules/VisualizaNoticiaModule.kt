package br.com.alura.technews.di.modules

import br.com.alura.technews.ui.viewmodels.VisualizaNoticiaViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val visualizaNoticiaModule = module {
    viewModel<VisualizaNoticiaViewModel> {
        VisualizaNoticiaViewModel(get())
    }
}
