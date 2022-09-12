package dev.anthonysierra.devanagarilearner

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import kotlin.random.Random

class MainActivity : AppCompatActivity() {

    private data class LetterPossibility(
        val drawableResourceId: Int,
        val englishLetterEquiavelent: String,
    )

    private val letter_possibilities = listOf<LetterPossibility>(
        LetterPossibility(R.drawable.letter_a, "a")
    )
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun onResume() {
        super.onResume()
        reload()
    }

    private fun reload() {
        val position = Random.nextInt(0, letter_possibilities.size)
        val letter = letter_possibilities[position]
        findViewById<ImageView>(R.id.letter_image).setImageResource(letter.drawableResourceId)
        findViewById<TextView>(R.id.english_equivalent).setText("English letter : " + letter.englishLetterEquiavelent)
    }
}