package com.bortolan.iquadriv2.data.db

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import androidx.paging.DataSource
import androidx.room.*
import com.bortolan.iquadriv2.data.pojos.Presenza
import com.bortolan.iquadriv2.data.pojos.Stage
import com.bortolan.iquadriv2.ui.asl.adapter.StageAdapter

@Dao
abstract class MasterstageDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun savePresenze(presenze: List<Presenza>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun saveStageExclusive(stage: Stage): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun saveStagesExclusive(stages: List<Stage>)

    @Transaction
    open fun saveStageInclusive(stage: Stage) {
        saveStageExclusive(stage)
        savePresenze(stage.presenze.map {
            it.stageId = stage.id
            it
        })
    }

    @Transaction
    open fun saveStages(stages: List<Stage>) {
        val presenze = mutableListOf<Presenza>()

        stages.forEach { stage ->
            presenze.addAll(stage.presenze.map {
                it.stageId = stage.id
                it
            })
        }

        savePresenze(presenze)
        saveStagesExclusive(stages)
    }

    @Query("SELECT * FROM Presenze WHERE stageId=:stageId")
    abstract fun getPresenze(stageId: Long): LiveData<List<Presenza>>

    @Query("SELECT * FROM Presenze WHERE stageId=:stageId")
    abstract fun getPresenzeSync(stageId: Long): List<Presenza>

    @Query("SELECT * FROM Presenze")
    abstract fun getPresenze(): LiveData<List<Presenza>>

    @Query("SELECT * FROM Stage ORDER BY dataFine DESC")
    abstract fun getStageExclusive(): LiveData<List<Stage>>

    open fun getStageInclusive(): LiveData<List<Stage>> {
        return Transformations.map(getStageExclusive(), { stages ->
            stages.map { stage ->
                stage.presenze.addAll(getPresenzeSync(stage.id))
                stage
            }
        })
    }

    @Query("SELECT id, luogoSvolgimento as luogo, substr(descrizione,0,200)||\"...\" as descrizione,tutorAziendale, tutorScolastico, ROUND((SELECT SUM(fine-inizio) FROM Presenze WHERE stageId=Stage.id GROUP BY stageId)/3600) as ore FROM Stage ORDER BY dataFine DESC")
    abstract fun getStageDataHolder(): DataSource.Factory<Int, StageAdapter.StageDataHolder>

}