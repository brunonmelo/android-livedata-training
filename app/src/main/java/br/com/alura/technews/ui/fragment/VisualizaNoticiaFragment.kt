package br.com.alura.technews.ui.fragment

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import br.com.alura.technews.R
import br.com.alura.technews.model.Noticia
import br.com.alura.technews.ui.activity.NOTICIA_ID_CHAVE
import br.com.alura.technews.ui.fragment.extensions.mostraErro
import br.com.alura.technews.ui.viewmodels.VisualizaNoticiaViewModel
import kotlinx.android.synthetic.main.fragment_visualiza_noticia.*
import org.koin.android.viewmodel.ext.android.viewModel
import java.lang.IllegalArgumentException

class VisualizaNoticiaFragment : Fragment() {

    private val mViewModel: VisualizaNoticiaViewModel by viewModel()
    private val noticiaId by lazy {
        arguments?.getLong(NOTICIA_ID_CHAVE) ?: throw  IllegalArgumentException(
            NOTICIA_NAO_ENCONTRADA
        )
    }

    private lateinit var noticia: Noticia
    lateinit var abreFormularioEdicao: (noticiaId: Long) -> Unit

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
        verificaIdDaNoticia()
        buscaNoticiaSelecionada()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_visualiza_noticia, container, false)
    }

    private fun preencheCampos(noticia: Noticia) {
        visualiza_noticia_titulo.text = noticia.titulo
        visualiza_noticia_texto.text = noticia.texto
    }


    private fun verificaIdDaNoticia() {
        if (noticiaId == 0L) {
            mostraErro(NOTICIA_NAO_ENCONTRADA)
            activity?.finish()
        }
    }

    private fun buscaNoticiaSelecionada() {
        mViewModel
            .buscaPorId(noticiaId)
            .observe(this, { noticia ->
                noticia?.let {
                    this.noticia = it
                    preencheCampos(it)
                }
            })
    }


    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.visualiza_noticia_menu, menu)
        return super.onCreateOptionsMenu(menu, inflater)
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.visualiza_noticia_menu_edita -> abreFormularioEdicao(noticiaId)
            R.id.visualiza_noticia_menu_remove -> remove()
        }
        return super.onOptionsItemSelected(item)
    }


    private fun remove() {
        mViewModel
            .remove(noticia)
            .observe(this, { resource ->
                if (resource.error != null) {
                    mostraErro(MENSAGEM_FALHA_REMOCAO)
                } else {
                    activity?.finish()
                }
            })
    }

    companion object {
        private const val MENSAGEM_FALHA_REMOCAO = "Não foi possível remover notícia"
        private const val NOTICIA_NAO_ENCONTRADA = "Notícia não encontrada"
    }


}