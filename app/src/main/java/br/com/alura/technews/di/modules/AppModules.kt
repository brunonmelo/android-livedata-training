package br.com.alura.technews.di.modules

import androidx.room.Room
import br.com.alura.technews.database.AppDatabase
import br.com.alura.technews.database.dao.NoticiaDAO
import br.com.alura.technews.repository.noticiaRepository.NoticiaRepository
import br.com.alura.technews.repository.noticiaRepository.NoticiaRepositoryImpl
import br.com.alura.technews.retrofit.webclient.NoticiaWebClient
import br.com.alura.technews.ui.viewmodels.FormularioNoticiaViewModel
import br.com.alura.technews.ui.viewmodels.ListaNoticiasViewModel
import br.com.alura.technews.ui.viewmodels.VisualizaNoticiaViewModel
import br.com.alura.technews.ui.viewmodels.factory.FormularioNoticiaViewModelFactory
import br.com.alura.technews.ui.viewmodels.factory.ListaNoticiaViewModelFactory
import br.com.alura.technews.ui.viewmodels.factory.VisualizaNoticiaViewModelFactory
import org.koin.dsl.module

private const val NOME_BANCO_DE_DADOS = "news.db"

val appModules = module {
    single<AppDatabase> {
        Room.databaseBuilder(
            get(),
            AppDatabase::class.java,
            NOME_BANCO_DE_DADOS
        ).build()
    }
    single<NoticiaDAO> {
        get<AppDatabase>().noticiaDAO
    }
    single<NoticiaWebClient> {
        NoticiaWebClient()
    }
    single<NoticiaRepository> {
        NoticiaRepositoryImpl(get(), get())
    }
}

val formularioNoticiaModule = module {
    single<FormularioNoticiaViewModelFactory> {
        FormularioNoticiaViewModelFactory(get())
    }
    single<FormularioNoticiaViewModel> {
        FormularioNoticiaViewModel(get())
    }
}

val listaNoticiasModule = module {
    single<ListaNoticiaViewModelFactory> {
        ListaNoticiaViewModelFactory(get())
    }
    single<ListaNoticiasViewModel> {
        ListaNoticiasViewModel(get())
    }
}

val visualizaNoticiaModule = module {
    single<VisualizaNoticiaViewModelFactory> {
        VisualizaNoticiaViewModelFactory(get())
    }
    single<VisualizaNoticiaViewModel> {
        VisualizaNoticiaViewModel(get())
    }
}