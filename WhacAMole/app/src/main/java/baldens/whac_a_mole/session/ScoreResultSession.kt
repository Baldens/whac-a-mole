package baldens.whac_a_mole.session

import android.content.Context
import android.content.SharedPreferences
import android.content.SharedPreferences.Editor
import baldens.whac_a_mole.GameActivity
import baldens.whac_a_mole.models.ResultScore

class ScoreResultSession(context: Context) {
    private lateinit var score: ResultScore
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var editor: Editor
    private var SHARED_PREF_NAME = "session"
    private var SESSION_KEY = "session_key"
    private var SESSION_SCORE = "session_score"

    init {
        sharedPreferences = context.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE)
        editor = sharedPreferences.edit()
    }

    public fun getStatusSession(): Int{
        return sharedPreferences.getString(SESSION_KEY, null).toString().toInt()
    }

    public fun removeSession(){
        editor.putInt(SESSION_KEY, -1)
        editor.putString(SESSION_SCORE, "")
    }

    public fun saveSession(score: String){
        editor.putString(SESSION_KEY, "1")
        editor.putString(SESSION_SCORE, score)
        editor.commit()
    }

    public fun getSession(): ResultScore{
        score = ResultScore(sharedPreferences.getString(SESSION_SCORE, null).toString())
        return score
    }
}