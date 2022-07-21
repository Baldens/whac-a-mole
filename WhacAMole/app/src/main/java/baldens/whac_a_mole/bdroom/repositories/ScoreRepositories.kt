package baldens.whac_a_mole.bdroom.repositories

import baldens.whac_a_mole.bdroom.BaseData
import baldens.whac_a_mole.bdroom.models.Score

class ScoreRepositories {
    private val somethingDao = BaseData.instance.scoreDao()

    companion object {
        val instance = ScoreRepositories()
    }

    fun getAll() = somethingDao.getAll()
    fun getMaxScore() = somethingDao.getMaxScore()
    fun insertAllScore(model: Score) = somethingDao.insertAll(model)
}