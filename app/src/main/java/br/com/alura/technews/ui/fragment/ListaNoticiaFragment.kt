package br.com.alura.technews.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import br.com.alura.technews.R
import br.com.alura.technews.model.Noticia
import br.com.alura.technews.ui.fragment.extensions.mostraErro
import br.com.alura.technews.ui.recyclerview.adapter.ListaNoticiasAdapter
import br.com.alura.technews.ui.viewmodels.ListaNoticiasViewModel
import kotlinx.android.synthetic.main.fragment_lista_noticia.*
import org.koin.android.viewmodel.ext.android.viewModel

class ListaNoticiaFragment : Fragment() {

    private val mViewModel: ListaNoticiasViewModel by viewModel()
    private val adapter by lazy {
        context?.let { ListaNoticiasAdapter(context = it) }
            ?: throw IllegalArgumentException("Invalid Context")
    }

    lateinit var abreFormularioModoCriacao: () -> Unit
    lateinit var abreVisualizadorNoticia: (noticia: Noticia) -> Unit

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        buscaNoticias()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = inflater.inflate(R.layout.fragment_lista_noticia, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        configuraRecyclerView()
        configuraFabAdicionaNoticia()
        activity?.title = TITULO_APPBAR
    }

    private fun configuraFabAdicionaNoticia() {
        lista_noticias_fab_salva_noticia.setOnClickListener {
            abreFormularioModoCriacao()
        }
    }

    private fun configuraRecyclerView() {
        val divisor = DividerItemDecoration(context, LinearLayoutManager.VERTICAL)
        lista_noticias_recyclerview.addItemDecoration(divisor)
        lista_noticias_recyclerview.adapter = adapter
        configuraAdapter()
    }

    private fun buscaNoticias() {
        mViewModel
            .buscaTodos()
            .observe(this, { resource ->
                resource.dado?.let { adapter.atualiza(it) }
                resource.error?.let { mostraErro(MENSAGEM_FALHA_CARREGAR_NOTICIAS) }
            })
    }


    private fun configuraAdapter() {
        adapter.quandoItemClicado = abreVisualizadorNoticia
    }


    companion object {
        private const val TITULO_APPBAR = "Notícias"
        private const val MENSAGEM_FALHA_CARREGAR_NOTICIAS =
            "Não foi possível carregar as novas notícias"
    }

}