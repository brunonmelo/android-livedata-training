package br.com.alura.technews.ui.activity

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import br.com.alura.technews.R
import br.com.alura.technews.model.Noticia
import br.com.alura.technews.ui.fragment.ListaNoticiaFragment
import br.com.alura.technews.ui.fragment.VisualizaNoticiaFragment

class NoticiasActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_noticias)
        title = TITULO_APPBAR
        abreListaNoticia()
    }

    private fun abreListaNoticia() {
        val transaction = supportFragmentManager.beginTransaction()
        val listaNoticiaFragment = ListaNoticiaFragment()
        transaction.replace(R.id.activity_noticia_container, listaNoticiaFragment, LISTA_NOTICIA_TAG)
        transaction.commit()
    }

    override fun onAttachFragment(fragment: Fragment) {
        super.onAttachFragment(fragment)
        when (fragment) {
            is ListaNoticiaFragment -> {
                fragment.abreFormularioModoCriacao = { abreFormularioModoCriacao() }
                fragment.abreVisualizadorNoticia = { abreVisualizadorNoticia(it) }
            }
            is VisualizaNoticiaFragment -> {
                fragment.abreFormularioEdicao = { abreFormularioEdicao(it) }
                fragment.finalizaVizualizaNoticia = { abreListaNoticia() }
            }
        }
    }


    private fun abreFormularioEdicao(noticiaId: Long) {
        val intent = Intent(this, FormularioNoticiaActivity::class.java)
        intent.putExtra(NOTICIA_ID_CHAVE, noticiaId)
        startActivity(intent)
    }

    private fun abreFormularioModoCriacao() {
        val intent = Intent(this, FormularioNoticiaActivity::class.java)
        startActivity(intent)
    }

    private fun abreVisualizadorNoticia(noticia: Noticia) {
        val transaction = supportFragmentManager.beginTransaction()
        val fragment = VisualizaNoticiaFragment()
        val bundle = Bundle()
        bundle.putLong(NOTICIA_ID_CHAVE, noticia.id)
        fragment.arguments = bundle
        transaction.replace(R.id.activity_noticia_container, fragment, VISUALIZA_NOTICIA_TAG)
        transaction.addToBackStack(LISTA_NOTICIA_TAG)
        transaction.commit()
    }

    companion object {
        private const val TITULO_APPBAR = "Notícias"
        private const val LISTA_NOTICIA_TAG = "LISTA_NOTICIA_TAG"
        private const val VISUALIZA_NOTICIA_TAG = "VISUALIZA_NOTICIA_TAG"
    }

}
