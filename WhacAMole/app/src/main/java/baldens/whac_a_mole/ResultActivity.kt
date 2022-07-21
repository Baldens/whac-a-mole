package baldens.whac_a_mole

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import baldens.whac_a_mole.session.ScoreResultSession

class ResultActivity : AppCompatActivity() {
    private lateinit var textViewScore: TextView
    private lateinit var resultSession: ScoreResultSession
    private lateinit var buttonPlayAgain: Button
    private lateinit var buttonMenu: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_result)
        initUiObjects()
    }

    fun initUiObjects(){
        buttonPlayAgain = findViewById(R.id.button_playAgain_result)
        buttonMenu = findViewById(R.id.button_menu_result)
        initButtonEvent()

        resultSession = ScoreResultSession(this)
        textViewScore = findViewById(R.id.textView_score_result)
        textViewScore.text = resultSession.getSession().getScore()
    }

    fun initButtonEvent(){
        buttonPlayAgain.setOnClickListener {
            startGameActivity()
        }
        buttonMenu.setOnClickListener {
            mainActivity()
        }
    }

    private fun startGameActivity() {
        val intent = Intent(this, GameActivity::class.java)
        startActivity(intent)
    }

    private fun mainActivity() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }
}