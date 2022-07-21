package baldens.whac_a_mole.models

import android.widget.TextView

class ResultScore(score: String) {
    private var score: String

    init {
        this.score = score
    }

    fun getScore(): String {
        return score
    }
}