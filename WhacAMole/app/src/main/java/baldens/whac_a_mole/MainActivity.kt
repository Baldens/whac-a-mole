package baldens.whac_a_mole

import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.room.Room
import baldens.whac_a_mole.bdroom.BaseData
import baldens.whac_a_mole.bdroom.models.Score
import baldens.whac_a_mole.bdroom.repositories.ScoreRepositories

class MainActivity : AppCompatActivity() {
    private lateinit var buttonDialog: Button
    private lateinit var playGame:Button
    private lateinit var rulesDialog: Dialog
    private lateinit var listScore: List<Score>
    private lateinit var textViewScore: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initUiObjects()
    }

    private fun initUiObjects(){
        textViewScore = findViewById(R.id.textView_score_main)
        rulesDialog = Dialog(this)
        playGame = findViewById(R.id.buttonStartGame_main)
        buttonDialog = findViewById(R.id.button_showDialog_main)
        initEventButton()
        connectDataBase()
    }

    private fun initEventButton(){
        playGame.setOnClickListener {
            startGameActivity()
        }
        buttonDialog.setOnClickListener {
            showDialogPaneRules()
        }
    }

    private fun startGameActivity() {
        val intent = Intent(this, GameActivity::class.java)
        startActivity(intent)
    }

    private fun showDialogPaneRules(){
        rulesDialog.setContentView(R.layout.dialog_rules)
        rulesDialog.show()

        val closeButton: ImageButton = rulesDialog.findViewById(R.id.button_closeDialog_main)
        closeButton.setOnClickListener{
            rulesDialog.dismiss()
        }
    }

    private fun connectDataBase(){
        var db: BaseData = Room.databaseBuilder(
            this,
            BaseData::class.java, "database-score"
        ).allowMainThreadQueries().build()

        BaseData.create(this)

        listScore = db.scoreDao().getMaxScore()

        var setTextScore = if(listScore.first().score.toString() != null){
            listScore.first().score.toString()
        }else{
            "0"
        }

        textViewScore.text = setTextScore
    }
}