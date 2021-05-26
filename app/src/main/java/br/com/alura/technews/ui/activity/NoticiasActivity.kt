package br.com.alura.technews.ui.activity

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import br.com.alura.technews.R
import br.com.alura.technews.model.Noticia
import br.com.alura.technews.ui.activity.extensions.transacaoFragment
import br.com.alura.technews.ui.fragment.ListaNoticiaFragment
import br.com.alura.technews.ui.fragment.VisualizaNoticiaFragment

class NoticiasActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_noticias)
        if (savedInstanceState == null) {
            abreListaNoticia()
        }
    }

    override fun onAttachFragment(fragment: Fragment) {
        when (fragment) {
            is ListaNoticiaFragment -> configuraListaNoticiaFragment(fragment)
            is VisualizaNoticiaFragment -> configuraVisualizaNoticiaFragment(fragment)
        }
    }

    private fun configuraVisualizaNoticiaFragment(fragment: VisualizaNoticiaFragment) {
        fragment.abreFormularioEdicao = this::abreFormularioEdicao
        fragment.finalizaVizualizaNoticia = this::abreListaNoticia
    }

    private fun configuraListaNoticiaFragment(fragment: ListaNoticiaFragment) {
        fragment.abreFormularioModoCriacao = this::abreFormularioModoCriacao
        fragment.abreVisualizadorNoticia = this::abreVisualizadorNoticia
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

    private fun abreListaNoticia() {
        val listaNoticiaFragment = ListaNoticiaFragment()

        transacaoFragment {
            replace(
                R.id.activity_noticia_container,
                listaNoticiaFragment,
                LISTA_NOTICIA_TAG
            )
        }
    }

    private fun abreVisualizadorNoticia(noticia: Noticia) {
        val fragment = VisualizaNoticiaFragment()
        val bundle = Bundle()
        bundle.putLong(NOTICIA_ID_CHAVE, noticia.id)
        fragment.arguments = bundle

        transacaoFragment {
            replace(R.id.activity_noticia_container, fragment, VISUALIZA_NOTICIA_TAG)
            addToBackStack(LISTA_NOTICIA_TAG)
        }
    }

    companion object {
        private const val LISTA_NOTICIA_TAG = "LISTA_NOTICIA_TAG"
        private const val VISUALIZA_NOTICIA_TAG = "VISUALIZA_NOTICIA_TAG"
    }

}
