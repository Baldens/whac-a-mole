package baldens.whac_a_mole.bdroom.DAO

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import baldens.whac_a_mole.bdroom.models.Score

@Dao
interface ScoreDAO {
    @Insert
    fun insertAll(vararg answer: Score)

    @Query("SELECT MAX(score) as score FROM score limit 1")
    fun getMaxScore(): List<Score>

    @Query("SELECT * FROM score")
    fun getAll(): List<Score>
}