package br.com.alura.technews.ui.activity

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import br.com.alura.technews.R
import br.com.alura.technews.database.AppDatabase
import br.com.alura.technews.model.Noticia
import br.com.alura.technews.model.Resource
import br.com.alura.technews.repository.NoticiaRepository
import br.com.alura.technews.ui.activity.extensions.mostraErro
import br.com.alura.technews.ui.viewmodels.FormularioNoticiaViewModel
import br.com.alura.technews.ui.viewmodels.factory.FormularioNoticiaViewModelFactory
import kotlinx.android.synthetic.main.activity_formulario_noticia.*

private const val TITULO_APPBAR_EDICAO = "Editando notícia"
private const val TITULO_APPBAR_CRIACAO = "Criando notícia"
private const val MENSAGEM_ERRO_SALVAR = "Não foi possível salvar notícia"

class FormularioNoticiaActivity : AppCompatActivity() {

    private val noticiaId: Long by lazy {
        intent.getLongExtra(NOTICIA_ID_CHAVE, 0)
    }

    private val mViewModel by lazy {
        val repository = NoticiaRepository(AppDatabase.getInstance(this).noticiaDAO)
        val factory = FormularioNoticiaViewModelFactory(repository)
        ViewModelProvider(this, factory).get(FormularioNoticiaViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_formulario_noticia)
        definindoTitulo()
        preencheFormulario()
    }

    private fun definindoTitulo() {
        title = if (noticiaId > 0) {
            TITULO_APPBAR_EDICAO
        } else {
            TITULO_APPBAR_CRIACAO
        }
    }

    private fun preencheFormulario() {
        mViewModel
            .buscaPorId(noticiaId)
            .observe(this, Observer { resourceNoticia ->
                resourceNoticia.dado?.let { noticia ->
                    activity_formulario_noticia_titulo.setText(noticia.titulo)
                    activity_formulario_noticia_texto.setText(noticia.texto)
                }
            })
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.formulario_noticia_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            R.id.formulario_noticia_salva -> {
                val titulo = activity_formulario_noticia_titulo.text.toString()
                val texto = activity_formulario_noticia_texto.text.toString()
                salva(Noticia(noticiaId, titulo, texto))
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun salva(noticia: Noticia) {
        mViewModel
            .salva(noticia)
            .observe(this, Observer { resource ->
                if (resource.error != null) {
                    mostraErro(MENSAGEM_ERRO_SALVAR)
                } else {
                    finish()
                }
            })
    }

}
