package br.com.alura.technews.di.modules

import br.com.alura.technews.ui.viewmodels.ListaNoticiasViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val listaNoticiasModule = module {
    viewModel<ListaNoticiasViewModel> {
        ListaNoticiasViewModel(get())
    }
}