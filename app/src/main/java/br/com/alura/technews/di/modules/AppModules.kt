package br.com.alura.technews.di.modules

import androidx.room.Room
import br.com.alura.technews.database.AppDatabase
import br.com.alura.technews.repository.noticiaRepository.NoticiaRepository
import br.com.alura.technews.repository.noticiaRepository.NoticiaRepositoryImpl
import br.com.alura.technews.retrofit.webclient.NoticiaWebClient
import org.koin.dsl.module

private const val NOME_BANCO_DE_DADOS = "news.db"

val appModules = module {
    single {
        Room.databaseBuilder(
            get(),
            AppDatabase::class.java,
            NOME_BANCO_DE_DADOS
        ).build()
    }
    single {
        get<AppDatabase>().noticiaDAO
    }
    single {
        NoticiaWebClient()
    }
    single<NoticiaRepository> {
        NoticiaRepositoryImpl(get(), get())
    }
}
