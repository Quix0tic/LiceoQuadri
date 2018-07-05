package com.bortolan.iquadriv2.data.db

import androidx.lifecycle.LiveData
import androidx.paging.DataSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import com.bortolan.iquadriv2.data.pojos.Classe
import com.bortolan.iquadriv2.data.pojos.Orario
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

    @Query("SELECT classe AS grado, COUNT(*) as nStudenti FROM Studenti GROUP BY classe ORDER BY classe ASC")
    abstract fun getClassi(): DataSource.Factory<Int, Classe>

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

    @Insert
    abstract fun insertOrari(orari: List<Orario>)

    @Query("DELETE FROM Orari")
    abstract fun removeOrari()

    @Query("SELECT * FROM Orari WHERE nome=:nome LIMIT 1")
    abstract fun getOrario(nome: String): Orario

    @Transaction
    open fun saveOrari(body: List<Orario>) {
        removeOrari()
        insertOrari(body)
    }

}
