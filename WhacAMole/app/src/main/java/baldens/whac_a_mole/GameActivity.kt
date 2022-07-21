package baldens.whac_a_mole

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.SystemClock
import android.view.View
import android.widget.Chronometer
import android.widget.Chronometer.OnChronometerTickListener
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.room.Room
import baldens.whac_a_mole.bdroom.BaseData
import baldens.whac_a_mole.bdroom.models.Score
import baldens.whac_a_mole.session.ScoreResultSession
import com.gusakov.library.PulseCountDown
import java.util.*

class GameActivity : AppCompatActivity() {
    private lateinit var firstMole_FirstLine: ImageButton
    private lateinit var secondMole_FirstLine:ImageButton
    private lateinit var thirdMole_FirstLine:ImageButton
    private lateinit var firstMole_SecondLine:ImageButton
    private lateinit var secondMole_SecondLine:ImageButton
    private lateinit var thirdMole_SecondLine:ImageButton
    private lateinit var firstMole_ThirdLine:ImageButton
    private lateinit var secondMole_ThirdLine:ImageButton
    private lateinit var thirdMole_ThirdLine:ImageButton

    private lateinit var startGameCountDownTimer: PulseCountDown
    private lateinit var mChronometer: Chronometer

    private lateinit var countCatch: TextView
    private lateinit var moleIdArray: IntArray
    private var isStart: Boolean = false
    private lateinit var sessionScore: ScoreResultSession

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game)
        countCatch = findViewById(R.id.textCountCatch_game)
        mChronometer = findViewById(R.id.chronometerTimer_game)
        startGameCountDownTimer = findViewById(R.id.pulseCountDown)

        startPulse()

        mChronometer.onChronometerTickListener = OnChronometerTickListener {
            checkTimerChronometer()
        }

        moleIdArray = setArrayIntegerIdImageButton()
        initUiElements()
        disablePeat()
        fillEnotArrayList(0)
    }

    private fun setArrayIntegerIdImageButton(): IntArray{
        return intArrayOf(
            R.id.button_firstMole_FirstLine,
            R.id.button_secondMole_FirstLine,
            R.id.button_thirdMole_FirstLine,
            R.id.button_firstMole_SecondLine,
            R.id.button_secondMole_SecondLine,
            R.id.button_thirdMole_SecondLine,
            R.id.button_firstMole_ThirdLine,
            R.id.button_secondMole_ThirdLine,
            R.id.button_thirdMole_ThirdLine
        )
    }

    private fun getListMole(): ArrayList<Int> {
        return ArrayList(moleIdArray.toList())
    }

    private fun startPulse() {
        startGameCountDownTimer.start {
            mChronometer.base = SystemClock.elapsedRealtime() + 1000 * 30
            mChronometer.isCountDown = true
            startTimerChronometer()
        }
    }

    private fun startTimerChronometer() {
        mChronometer.start()
        isStart = true
    }

    private fun checkTimerChronometer(){
        val elapsedMillis = (SystemClock.elapsedRealtime()
                - mChronometer.base)
        if(isStart) showMole()
        if (elapsedMillis >= 0L) {
            Toast.makeText(this, "Finish game.", Toast.LENGTH_LONG).show()
            mChronometer.stop()
            sessionScore = ScoreResultSession(this)
            sessionScore.saveSession(countCatch.text.toString())
            connectDataBase()
            openResultActivity()
        }
    }

    private fun connectDataBase(){
        var db: BaseData = Room.databaseBuilder(
            this,
            BaseData::class.java, "database-score"
        ).allowMainThreadQueries().build()

        BaseData.create(this)
        var score: Score? = Score(null, countCatch.text.toString().toInt())
        if (score != null) {
            db.scoreDao().insertAll(score)
        }

    }

    private fun openResultActivity() {
        val intent = Intent(this, ResultActivity::class.java)
        startActivity(intent)
    }

    private fun initUiElements() {
        firstMole_FirstLine = findViewById(R.id.button_firstMole_FirstLine)
        secondMole_FirstLine = findViewById(R.id.button_secondMole_FirstLine)
        thirdMole_FirstLine = findViewById(R.id.button_thirdMole_FirstLine)
        firstMole_SecondLine = findViewById(R.id.button_firstMole_SecondLine)
        secondMole_SecondLine = findViewById(R.id.button_secondMole_SecondLine)
        thirdMole_SecondLine = findViewById(R.id.button_thirdMole_SecondLine)
        firstMole_ThirdLine = findViewById(R.id.button_firstMole_ThirdLine)
        secondMole_ThirdLine = findViewById(R.id.button_secondMole_ThirdLine)
        thirdMole_ThirdLine = findViewById(R.id.button_thirdMole_ThirdLine)
    }

    private fun disablePeat() {
        firstMole_FirstLine.isEnabled = false
        secondMole_FirstLine.isEnabled = false
        thirdMole_FirstLine.isEnabled = false
        firstMole_SecondLine.isEnabled = false
        secondMole_SecondLine.isEnabled = false
        thirdMole_SecondLine.isEnabled = false
        firstMole_ThirdLine.isEnabled = false
        secondMole_ThirdLine.isEnabled = false
        thirdMole_ThirdLine.isEnabled = false
    }

    @SuppressLint("SetTextI18n")
    private fun showMole() {
        val activeMole = findViewById<ImageButton>(
            getListMole().get(
                Random().nextInt(moleIdArray.size)
            )
        )
        activeMole.setImageResource(R.drawable.enot_in_peat)
        activeMole.isEnabled = true

        val handler = Handler()
        handler.postDelayed(createRunnable(activeMole), 500)

        for (index in moleIdArray.indices) {
            checkClickedOnEnot(index, activeMole)
        }
    }

    private fun checkClickedOnEnot(index: Int, activeMole: ImageButton){
        val imageButton = findViewById<ImageButton>(moleIdArray[index])
        imageButton.setOnClickListener(View.OnClickListener {
            imageButton.setImageResource(R.drawable.peat_)
            activeMole.isEnabled = false
            countCatch.setText((countCatch.getText().toString().toInt() + 1).toString())
        })
    }

    private fun createRunnable(activeMole: ImageButton): Runnable {
        val runnable = Runnable {
            getListMole().clear()
            fillEnotArrayList(activeMole.id)
            activeMole.setImageResource(R.drawable.peat_)
            activeMole.isEnabled = false
        }
        return runnable
    }

    private fun fillEnotArrayList(notAvailiableMoleId: Int) {
        for (i in moleIdArray.indices) getListMole().add(moleIdArray[i])
        if (notAvailiableMoleId != 0) getListMole().remove(notAvailiableMoleId)
    }
}