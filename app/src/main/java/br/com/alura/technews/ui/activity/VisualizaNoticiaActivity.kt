package br.com.alura.technews.ui.activity

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import br.com.alura.technews.R
import br.com.alura.technews.ui.fragment.VisualizaNoticiaFragment

class VisualizaNoticiaActivity : AppCompatActivity() {

    private val noticiaId: Long by lazy { intent.getLongExtra(NOTICIA_ID_CHAVE, 0) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_visualiza_noticia)
        title = TITULO_APPBAR
        setupVisualizaNoticiaFragment()
    }

    override fun onAttachFragment(fragment: Fragment) {
        super.onAttachFragment(fragment)
        when (fragment) {
            is VisualizaNoticiaFragment -> {
                fragment.abreFormularioEdicao = { abreFormularioEdicao(it) }
            }
        }
    }

    private fun setupVisualizaNoticiaFragment() {
        val transaction = supportFragmentManager.beginTransaction()
        val fragment = VisualizaNoticiaFragment()
        val bundle = Bundle()
        bundle.putLong(NOTICIA_ID_CHAVE, noticiaId)
        fragment.arguments = bundle
        transaction.add(R.id.activity_visualiza_noticia_container, fragment)
        transaction.commit()
    }

    private fun abreFormularioEdicao(noticiaId: Long) {
        val intent = Intent(this, FormularioNoticiaActivity::class.java)
        intent.putExtra(NOTICIA_ID_CHAVE, noticiaId)
        startActivity(intent)
    }

    companion object {
        private const val TITULO_APPBAR = "Notícia"
    }

}
