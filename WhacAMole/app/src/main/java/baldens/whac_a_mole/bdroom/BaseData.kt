package baldens.whac_a_mole.bdroom

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import baldens.whac_a_mole.bdroom.DAO.ScoreDAO
import baldens.whac_a_mole.bdroom.models.Score

@Database(
    entities = [Score::class],
    version = 1
)
abstract class BaseData : RoomDatabase() {
    abstract fun scoreDao(): ScoreDAO

    companion object {
        lateinit var instance: BaseData

        fun create(context: Context) {
            instance = Room.databaseBuilder(context, BaseData::class.java, "database-score").build()
        }
    }
}