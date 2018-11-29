package com.jgchk.spellastory

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.GridLayoutManager
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.alert

class MainActivity : AppCompatActivity(), LetterClickListener {

    companion object {
        private val TAG = MainActivity::class.simpleName
        private const val WORD = "DOG"
    }

    private val letterButtonsAdapter = LetterButtonsAdapter(this)
    private var currentLetterIndex: Int = -1
    private var completedLetters = booleanArrayOf(false, false, false)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        buttons_rv.layoutManager = GridLayoutManager(this, 3)
        buttons_rv.adapter = letterButtonsAdapter

        word_img_l.setOnClickListener { onClickPicture(0) }
        word_img_m.setOnClickListener { onClickPicture(1) }
        word_img_r.setOnClickListener { onClickPicture(2) }
    }

    fun onClickPicture(index: Int) {
        currentLetterIndex = index
        letterButtonsAdapter.useLetter(WORD[currentLetterIndex])
    }

    override fun onClickLetter(letter: Char) {
        if (letter == WORD[currentLetterIndex]) {
            completeLetter()
            letterButtonsAdapter.clear()
        }

        if (wordComplete()) {
            alert("Congratulations!").show()
        }
    }

    fun completeLetter() {
        completedLetters[currentLetterIndex] = true
        when (currentLetterIndex) {
            0 -> word_img_l
            1 -> word_img_m
            2 -> word_img_r
            else -> throw IllegalArgumentException("$currentLetterIndex is not a valid index")
        }.imageAlpha = 255
        when (currentLetterIndex) {
            0 -> word_txt_l
            1 -> word_txt_m
            2 -> word_txt_r
            else -> throw IllegalArgumentException("$currentLetterIndex is not a valid index")
        }.text = WORD[currentLetterIndex].toString()

        currentLetterIndex = -1
    }

    private fun wordComplete() = completedLetters.all { it }
}
