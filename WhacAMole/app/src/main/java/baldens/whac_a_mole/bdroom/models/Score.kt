package baldens.whac_a_mole.bdroom.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Score(
    @PrimaryKey(autoGenerate = true)
    val id: Int?,
    @ColumnInfo(name = "score")
    val score: Int?,
)