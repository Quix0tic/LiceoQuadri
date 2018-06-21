package com.bortolan.iquadriv2.data.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import com.bortolan.iquadriv2.data.pojos.Classe
import com.bortolan.iquadriv2.data.pojos.Progetto
import com.bortolan.iquadriv2.data.pojos.Studente

@Dao
abstract class QuadriDao {

    @Query("DELETE FROM Studenti")
    abstract fun removeStudenti()

    @Insert
    abstract fun insertStudenti(studenti: List<Studente>)

    @Transaction
    open fun saveStudenti(studenti: List<Studente>) {
        removeStudenti()
        insertStudenti(studenti)
    }

    @Query("SELECT * FROM Studenti WHERE classe=:classe ORDER BY cognome, nome ASC")
    abstract fun getStudenti(classe: String): LiveData<List<Studente>>

    @Query("SELECT grado, COUNT(*) as nStudenti FROM Studenti GROUP BY grado ORDER BY classe ASC")
    abstract fun getClassi(): LiveData<List<Classe>>


    @Query("DELETE FROM Progetti")
    abstract fun removeProgetti()

    @Insert
    abstract fun insertProgetti(progetti: List<Progetto>)

    @Transaction
    open fun saveProgetti(progetti: List<Progetto>) {
        removeProgetti()
        insertProgetti(progetti)
    }

    @Query("SELECT * FROM Progetti")
    abstract fun getProgetti(): LiveData<List<Progetto>>

}
