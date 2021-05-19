package br.com.alura.technews.ui.activity

import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import br.com.alura.technews.R
import br.com.alura.technews.model.Noticia
import br.com.alura.technews.ui.activity.extensions.mostraErro
import br.com.alura.technews.ui.viewmodels.FormularioNoticiaViewModel
import kotlinx.android.synthetic.main.activity_formulario_noticia.*
import org.koin.android.viewmodel.ext.android.viewModel

private const val TITULO_APPBAR_EDICAO = "Editando notícia"
private const val TITULO_APPBAR_CRIACAO = "Criando notícia"
private const val MENSAGEM_ERRO_SALVAR = "Não foi possível salvar notícia"

class FormularioNoticiaActivity : AppCompatActivity() {

    private val mViewModel: FormularioNoticiaViewModel by viewModel()
    private val noticiaId: Long by lazy {
        intent.getLongExtra(NOTICIA_ID_CHAVE, 0)
    }
    private val isEdita by lazy { noticiaId > 0 }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_formulario_noticia)
        definindoTitulo()
        preencheFormulario()
    }

    private fun definindoTitulo() {
        title = if (isEdita) {
            TITULO_APPBAR_EDICAO
        } else {
            TITULO_APPBAR_CRIACAO
        }
    }

    private fun preencheFormulario() {
        if(isEdita) {
            mViewModel
                .buscaPorId(noticiaId)
                .observe(this, { resourceNoticia ->
                    resourceNoticia?.let { noticia ->
                        activity_formulario_noticia_titulo.setText(noticia.titulo)
                        activity_formulario_noticia_texto.setText(noticia.texto)
                    }
                })
        }
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
            .observe(this, { resource ->
                Log.d("aqui", resource.toString())
                if (resource.error != null) {
                    mostraErro(MENSAGEM_ERRO_SALVAR)
                } else {
                    finish()
                }
            })
    }

}
