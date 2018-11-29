package com.jgchk.spellastory

import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.letter_button.view.*
import kotlin.properties.Delegates

class LetterButtonsAdapter(private val clickListener: LetterClickListener) :
    RecyclerView.Adapter<LetterButtonsAdapter.ViewHolder>() {

    internal var collection: List<Char> by Delegates.observable(emptyList()) { _, _, _ -> notifyDataSetChanged() }

    fun useLetter(letter: Char) {
        collection = when (letter) {
            'D' -> listOf('W', 'Y', 'R', 'C', 'D', 'P')
            'O' -> listOf('T', 'O', 'S', 'C', 'B', 'M')
            'G' -> listOf('G', 'T', 'U', 'P', 'A', 'J')
            else -> throw IllegalArgumentException("$letter is not a valid letter")
        }
    }

    fun clear() {
        collection = emptyList()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ViewHolder(parent.inflate(R.layout.letter_button))

    override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.bind(collection[position], clickListener)

    override fun getItemCount() = collection.size

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(letter: Char, clickListener: LetterClickListener) {
            itemView.letter_btn.text = letter.toString()
            itemView.letter_btn.setOnClickListener { clickListener.onClickLetter(letter) }
        }
    }
}