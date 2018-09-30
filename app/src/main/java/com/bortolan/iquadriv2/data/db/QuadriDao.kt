package com.bortolan.iquadriv2.data.db

import androidx.lifecycle.LiveData
import androidx.paging.DataSource
import androidx.room.*
import com.bortolan.iquadriv2.data.pojos.*
import java.util.*

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

    @Query("DELETE FROM Notizie")
    abstract fun removeNotizie()

    @Insert
    abstract fun insertNotizie(it: List<Notizie>)

    @Transaction
    open fun saveNotizie(it: List<Notizie>) {
        removeNotizie()
        insertNotizie(it)
    }

    @Query("SELECT * FROM Notizie ORDER BY id ASC")
    abstract fun getNotizie(): LiveData<List<Notizie>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    abstract fun saveCircolari(it: List<Circolari>)

    @Query("SELECT * FROM Circolari ORDER BY date DESC LIMIT :limit")
    abstract fun getCircolari(limit: Int): LiveData<List<Circolari>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    abstract fun saveQuadrinews(it: List<Quadrinews>)

    @Query("SELECT * FROM Quadrinews ORDER BY date DESC LIMIT :limit")
    abstract fun getQuadrinews(limit: Int): LiveData<List<Quadrinews>>

    @Query("SELECT COUNT(*) FROM Circolari WHERE creationDate > :lastChecked")
    abstract fun countUnreadCircolari(lastChecked: Date): Int
}
