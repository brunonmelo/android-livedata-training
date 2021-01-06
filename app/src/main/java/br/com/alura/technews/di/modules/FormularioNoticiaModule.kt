package br.com.alura.technews.di.modules

import br.com.alura.technews.ui.viewmodels.FormularioNoticiaViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val formularioNoticiaModule = module {
    viewModel<FormularioNoticiaViewModel> {
        FormularioNoticiaViewModel(get())
    }
}
