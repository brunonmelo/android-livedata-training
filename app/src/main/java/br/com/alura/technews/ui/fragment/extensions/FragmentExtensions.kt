package br.com.alura.technews.ui.fragment.extensions

import androidx.fragment.app.Fragment
import br.com.alura.technews.ui.activity.extensions.mostraErro

fun Fragment.mostraErro(mensagem: String) {
    activity?.mostraErro(mensagem)
}